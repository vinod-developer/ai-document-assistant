# AI Document Assistant

An AI-powered document assistant built with Spring Boot, PostgreSQL, pgvector, and Gemini AI.

The application implements a Retrieval-Augmented Generation (RAG) pipeline that enables users to upload PDF documents and ask natural language questions. It retrieves semantically relevant document chunks using vector search and generates context-aware answers using Gemini.

---

## Features

* Upload and process PDF documents
* Extract text using Apache PDFBox
* Store documents and metadata in PostgreSQL
* Automatically chunk large documents
* Generate embeddings using Gemini Embedding Model
* Store embeddings as pgvector vectors
* Perform native vector similarity search using pgvector
* Retrieve top relevant document chunks
* Generate answers using Gemini LLM
* Dockerized Spring Boot backend
* Docker Compose support
* Environment variable-based configuration
* Interactive API documentation with Swagger/OpenAPI

---

## Architecture

PDF Upload
↓
Text Extraction
↓
Document Storage
↓
Document Chunking
↓
Gemini Embedding Generation
↓
PostgreSQL + pgvector Storage
↓
Vector Similarity Search
↓
Context Retrieval
↓
Gemini Answer Generation

---

## Retrieval Flow

Question
↓
Question Embedding
↓
pgvector Similarity Search
↓
Top 3 Relevant Chunks
↓
Context Construction
↓
Gemini LLM
↓
Answer

---

## Tech Stack

### Backend

* Java 21
* Spring Boot 3
* Spring Web
* Spring Data JPA
* Maven

### Database

* PostgreSQL 17
* pgvector

### AI

* Gemini 2.x
* Gemini Embeddings
* Retrieval-Augmented Generation (RAG)

### Containerization

* Docker
* Docker Compose

### Documentation

* Swagger / OpenAPI

### Libraries

* Apache PDFBox

---

## API Endpoints

### Upload Document

POST /api/documents/upload

Uploads a PDF document and generates embeddings for document chunks.

### Ask Question

POST /api/documents/question

Accepts a document identifier and a natural language question, retrieves relevant chunks using pgvector similarity search, and generates an answer using Gemini.

### Swagger UI

GET /swagger-ui/index.html

Interactive API documentation.

---

## Configuration

The application uses environment variables for sensitive configuration.

Required variables:

SPRING_DATASOURCE_URL

SPRING_DATASOURCE_USERNAME

SPRING_DATASOURCE_PASSWORD

GEMINI_API_KEY


---

## Running Locally

Build the application:

mvn clean package -DskipTests

Run using Docker Compose:

docker compose up --build

Access Swagger:

http://localhost:8080/swagger-ui/index.html

---

## Current Capabilities

* PDF ingestion
* Document chunking
* Embedding generation
* Vector storage with pgvector
* Semantic search
* Retrieval-Augmented Generation (RAG)
* Dockerized deployment
* Docker Compose orchestration


