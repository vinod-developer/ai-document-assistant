# AI Document Assistant

An AI-powered document assistant built with Spring Boot, PostgreSQL, and Gemini AI. The application implements a Retrieval-Augmented Generation (RAG) pipeline that enables users to upload PDF documents and ask natural language questions, returning context-aware answers based on document content.

## Features

* Upload and process PDF documents
* Extract text using Apache PDFBox
* Store documents and metadata in PostgreSQL
* Automatically chunk large documents for efficient retrieval
* Generate semantic embeddings using Gemini AI
* Store and manage document embeddings
* Perform semantic similarity search using cosine similarity
* Implement Retrieval-Augmented Generation (RAG)
* Answer natural language questions using retrieved document context
* Interactive API documentation with Swagger/OpenAPI

## Architecture

PDF Upload
→ Text Extraction
→ Document Storage
→ Document Chunking
→ Gemini Embeddings
→ Embedding Storage
→ Similarity Search
→ Context Retrieval
→ Gemini Answer Generation

## Tech Stack

Backend

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* Maven

Database

* PostgreSQL

AI

* Gemini AI
* Embeddings
* Retrieval-Augmented Generation (RAG)

Documentation

* Swagger / OpenAPI

Libraries

* Apache PDFBox

## API Endpoints

POST /api/documents/upload
Upload a PDF document

POST /api/documents/question
Ask questions about an uploaded document

GET /swagger-ui.html
Interactive API documentation
