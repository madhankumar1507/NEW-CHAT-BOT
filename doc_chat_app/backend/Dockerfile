# Builder Stage
FROM python:3.9-slim-buster AS builder

WORKDIR /app

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Runtime Stage
FROM python:3.9-slim-buster

WORKDIR /app

# Copy only the installed packages and application code from the builder stage
COPY --from=builder /usr/local/lib/python3.9/site-packages /usr/local/lib/python3.9/site-packages
COPY . .

# Expose the port FastAPI runs on (default is 8000)
EXPOSE 8000

# Command to run the application with Gunicorn and Uvicorn workers
# Assuming your FastAPI app instance is named 'app' in main.py
CMD ["gunicorn", "main:app", "--workers", "4", "--worker-class", "uvicorn.workers.UvicornWorker", "--bind", "0.0.0.0:8000"]