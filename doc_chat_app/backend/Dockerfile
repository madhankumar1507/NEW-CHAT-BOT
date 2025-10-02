# Use an official Python runtime as a parent image
FROM python:3.10-slim-buster

# Set the working directory in the container
WORKDIR /app/backend

# Install system dependencies required for docx processing
# RUN apt-get update && apt-get install -y \
#     libgl1-mesa-glx \
#     libgomp1 \
#     && rm -rf /var/lib/apt/lists/*

# Copy the requirements file into the working directory
COPY requirements.txt .

# Install any needed packages specified in requirements.txt
RUN pip install --no-cache-dir -r requirements.txt

# Copy the rest of the application code
COPY . .

# Expose the port the app runs on
EXPOSE 8000

# Define environment variable for admin token
ENV ADMIN_TOKEN="bfd48123-dfa9-481f-b35f-579ccc5b765c"

# Run the Uvicorn server
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]

