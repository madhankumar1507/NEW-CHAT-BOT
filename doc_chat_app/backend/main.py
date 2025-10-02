import shutil
import logging

from fastapi import FastAPI, File, UploadFile, HTTPException, Depends, Header
from fastapi.responses import JSONResponse
from pydantic import BaseModel
from typing import List

# ------------------- Torch / CPU Setup -------------------
try:
    import torch
except ImportError:
    raise RuntimeError(
        "PyTorch is required. Install via: pip install torch torchvision torchaudio --index-url https://download.pytorch.org/whl/cpu"
    )

# Force single-threaded CPU to avoid hangs on Windows
os.environ["OMP_NUM_THREADS"] = "1"
os.environ["MKL_NUM_THREADS"] = "1"
torch.set_num_threads(1)

# ------------------- Logging -------------------
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# ------------------- LangChain / HuggingFace Imports -------------------
from sentence_transformers import SentenceTransformer
from langchain_huggingface import HuggingFaceEmbeddings
from langchain_huggingface.llms import HuggingFacePipeline
from langchain_chroma import Chroma
from langchain_community.document_loaders import UnstructuredWordDocumentLoader
from langchain_text_splitters import RecursiveCharacterTextSplitter
from langchain_core.prompts import PromptTemplate
from langchain_core.runnables import RunnablePassthrough, RunnableLambda
from langchain_core.output_parsers import StrOutputParser
from chromadb.config import Settings

# ------------------- Preload Embedding Model -------------------
logger.info("Preloading embedding model (all-MiniLM-L6-v2)...")
try:
    SentenceTransformer("sentence-transformers/all-MiniLM-L6-v2")
    logger.info("Embedding model loaded successfully.")
except Exception as e:
    logger.error(f"Failed to preload embedding model: {e}")
    raise

# ------------------- Configuration -------------------
BASE_DIR = os.path.dirname(__file__)
DOCS_DIR = os.path.join(BASE_DIR, "data", "docs")
VECTOR_DB_DIR = os.path.join(BASE_DIR, "vectordb")
os.makedirs(DOCS_DIR, exist_ok=True)

ADMIN_TOKEN = os.getenv("ADMIN_TOKEN", "bfd48123-dfa9-481f-b35f-579ccc5b765c")

# ------------------- FastAPI App -------------------
app = FastAPI()

from fastapi.middleware.cors import CORSMiddleware
app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:3001"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# ------------------- Admin Auth -------------------
def get_admin_user(x_admin_token: str = Header(...)):
    if x_admin_token != ADMIN_TOKEN:
        raise HTTPException(status_code=401, detail="Invalid Admin Token")
    return {"username": "admin"}

# ------------------- Pydantic Models -------------------
class ChatHistory(BaseModel):
    question: str
    answer: str

class ChatRequest(BaseModel):
    question: str
    history: List[ChatHistory] = []

class ChatResponse(BaseModel):
    answer: str
    sources: List[dict]

# ------------------- Embeddings & LLM -------------------
model_kwargs = {"device": "cpu"}
encode_kwargs = {"normalize_embeddings": False}

logger.info("Loading embeddings model into HuggingFaceEmbeddings...")
embeddings = HuggingFaceEmbeddings(
    model_name="sentence-transformers/all-MiniLM-L6-v2",
    model_kwargs=model_kwargs,
    encode_kwargs=encode_kwargs,
)
logger.info("Embeddings model ready.")

llm = HuggingFacePipeline.from_model_id(
    model_id="google/flan-t5-base",
    task="text2text-generation",
    pipeline_kwargs={"max_new_tokens": 512},  # removed temperature warning
)

# ------------------- Vector Store -------------------
def get_vector_store():
    return Chroma(
        persist_directory=VECTOR_DB_DIR,
        embedding_function=embeddings,
        client_settings=Settings(anonymized_telemetry=False),
    )

# ------------------- Utility Functions -------------------
def format_chat_history(history: List[ChatHistory]) -> str:
    return "\n".join([f"Q: {h.question}\nA: {h.answer}" for h in history])

def format_docs(docs):
    return "\n\n".join(doc.page_content for doc in docs)

# ------------------- API Endpoints -------------------
@app.post("/upload", dependencies=[Depends(get_admin_user)])
async def upload_files(files: List[UploadFile] = File(...)):
    if not files:
        raise HTTPException(status_code=400, detail="No files uploaded.")

    # Optional: Delete old vector DB for fresh start
    if os.path.exists(VECTOR_DB_DIR):
        shutil.rmtree(VECTOR_DB_DIR)
        logger.info("Deleted existing vector DB for fresh upload.")

    vector_store = get_vector_store()
    uploaded_count = 0

    for file in files:
        if not file.filename.endswith(".docx"):
            raise HTTPException(status_code=400, detail="Only .docx files are supported.")

        file_path = os.path.join(DOCS_DIR, file.filename)
        with open(file_path, "wb") as buffer:
            shutil.copyfileobj(file.file, buffer)
        logger.info(f"Uploaded file saved: {file.filename}")

        # Load and split DOCX
        loader = UnstructuredWordDocumentLoader(file_path)
        documents = loader.load()
        if not documents:
            logger.warning(f"No content in {file.filename}, skipping.")
            continue

        text_splitter = RecursiveCharacterTextSplitter(chunk_size=1000, chunk_overlap=200)
        texts = text_splitter.split_documents(documents)

        # Add source metadata
        for i, doc in enumerate(texts):
            doc.metadata["source"] = file.filename
            logger.info(f"Processing chunk {i+1}/{len(texts)} for {file.filename}")

        logger.info(f"Starting embedding generation for {len(texts)} chunk(s)")
        vector_store.add_documents(texts)
        uploaded_count += 1
        logger.info(f"Added {len(texts)} chunks from {file.filename} to vector store")

    vector_store.persist()
    logger.info(f"Vector store persisted at {VECTOR_DB_DIR}")

    return JSONResponse(content={"message": f"{uploaded_count} file(s) uploaded and processed successfully."})


@app.post("/chat", response_model=ChatResponse)
def chat(request: ChatRequest):
    try:
        vector_store = get_vector_store()
        retriever = vector_store.as_retriever(search_kwargs={"k": 5})

        # Use invoke() instead of deprecated get_relevant_documents
        source_docs = retriever.invoke(request.question)

        if not source_docs:
            return ChatResponse(answer="No relevant documents found for your question.", sources=[])

        format_docs_runnable = RunnableLambda(format_docs)
        format_history_runnable = RunnableLambda(lambda x: format_chat_history(x["history"]))

        template = """Answer the question based on the following context and chat history:

Context:
{context}

Chat History:
{chat_history}

Question:
{question}
"""
        prompt = PromptTemplate.from_template(template)

        rag_chain = (
            {
                "context": format_docs_runnable,
                "question": RunnablePassthrough(),
                "chat_history": format_history_runnable,
            }
            | prompt
            | llm
            | StrOutputParser()
        )

        sources = [
            {
                "filename": doc.metadata.get("source", "Unknown"),
                "snippet": doc.page_content[:200] + "..." if len(doc.page_content) > 200 else doc.page_content,
            }
            for doc in source_docs
        ]

        answer = rag_chain.invoke({
            "context": source_docs,
            "question": request.question,
            "history": request.history
        })

        return ChatResponse(answer=answer, sources=sources)

    except Exception as e:
        import traceback
        logger.error(traceback.format_exc())
        raise HTTPException(status_code=500, detail=str(e))


@app.get("/files", dependencies=[Depends(get_admin_user)])
async def list_files():
    files = [f for f in os.listdir(DOCS_DIR) if f.endswith(".docx")]
    return JSONResponse(content={"files": files})


@app.delete("/file/{filename}", dependencies=[Depends(get_admin_user)])
async def delete_file(filename: str):
    file_path = os.path.join(DOCS_DIR, filename)
    if not os.path.exists(file_path):
        raise HTTPException(status_code=404, detail="File not found.")

    os.remove(file_path)

    vector_store = get_vector_store()
    all_docs = vector_store.get()
    if all_docs and all_docs.get("metadatas"):
        ids_to_delete = [
            all_docs["ids"][i]
            for i, meta in enumerate(all_docs["metadatas"])
            if meta.get("source") == filename
        ]
        if ids_to_delete:
            vector_store.delete(ids=ids_to_delete)
            vector_store.persist()

    return JSONResponse(content={"message": f"File '{filename}' deleted successfully."})

# ------------------- Run App -------------------
if __name__ == "__main__":
    import uvicorn
    uvicorn.run("backend.main:app", host="0.0.0.0", port=8000, reload=True)
