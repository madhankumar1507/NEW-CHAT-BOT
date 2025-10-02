
# Doc Chat App

This is a full-stack application that allows users to chat with their .docx files. It uses a RAG (Retrieval-Augmented Generation) architecture to provide answers based on the content of the uploaded documents.

## Tech Stack

*   **Backend:** Python, FastAPI, LangChain, ChromaDB, Hugging Face
*   **Frontend:** React, TailwindCSS, Axios

## Project Structure

```
doc_chat_app/
├── backend/
│   ├── data/docs/      # Uploaded .docx files are stored here
│   ├── vectordb/       # Persistent ChromaDB data
│   ├── main.py         # FastAPI application
│   └── requirements.txt
├── frontend/
│   ├── public/
│   ├── src/            # React components
│   └── ...
└── README.md
```

## Setup and Running the Application

### Prerequisites

*   Python 3.7+
*   Node.js and npm
*   A Hugging Face API Token

### 1. Backend Setup

1.  **Navigate to the backend directory:**
    ```bash
    cd doc_chat_app/backend
    ```

2.  **Create a virtual environment and activate it:**
    ```bash
    # For Windows
    python -m venv venv
    venv\Scripts\activate

    # For macOS/Linux
    python3 -m venv venv
    source venv/bin/activate
    ```

3.  **Install the required Python packages:**
    ```bash
    pip install -r requirements.txt
    ```

4.  **Set up your environment variables:**

    *   **Hugging Face API Token:** Create a `.env` file in the `backend` directory and add your Hugging Face API token:
        ```
        HUGGINGFACEHUB_API_TOKEN="your_hf_api_token_here"
        ```
        The application will read this key from the environment.

    *   **Admin Token:** Open `main.py` and change the `ADMIN_TOKEN` variable to a secure token of your choice.

5.  **Run the FastAPI server:**
    ```bash
    uvicorn main:app --reload
    ```
    The backend will be running at `http://localhost:8000`.

### 2. Frontend Setup

1.  **Navigate to the frontend directory in a new terminal:**
    ```bash
    cd doc_chat_app/frontend
    ```

2.  **Install the required npm packages:**
    ```bash
    npm install
    ```

3.  **Update the Admin Token:**
    Open `src/AdminDashboard.js` and update the `ADMIN_TOKEN` variable to match the one you set in the backend.

4.  **Start the React development server:**
    ```bash
    npm start
    ```
    The frontend will be running at `http://localhost:3000`.

## How to Use

1.  **Admin Dashboard:**
    *   Open your browser and go to `http://localhost:3000`.
    *   Click on the **Admin** button in the navigation bar.
    *   Use the form to upload one or more `.docx` files.
    *   The uploaded files will appear in the table below.
    *   You can delete files by clicking the **Delete** button.

2.  **Chat Interface:**
    *   Click on the **Chat** button in the navigation bar.
    *   Type your question in the input box and press Enter or click **Send**.
    *   The AI will generate an answer based on the content of the uploaded documents.
    *   The sources (filename and snippet) used to generate the answer will be displayed below the response.
