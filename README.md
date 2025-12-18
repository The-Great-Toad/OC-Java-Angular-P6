# MDD - Monde de Dév

Social network for developers to subscribe to topics, publish articles, and comment.

## Tech Stack

**Backend**:

-   Java 17 LTS
-   Spring Boot 3.4.0
-   Spring Security + JWT
-   MySQL 8.0

**Frontend**:

-   Angular 20.3.0
-   TypeScript 5.7

## Prerequisites

-   **Backend**: JDK 17+, Maven 3.8+, MySQL 8.0+
-   **Frontend**: Node.js 18+, npm 10+

## Installation and Setup

### 1. Backend

```bash
cd backend

# Create database
mysql -u root -p < src/main/resources/init-db.sql

# Run API
mvn spring-boot:run
```

API available at `http://localhost:8080`

### 2. Frontend

```bash
cd frontend

# Install dependencies
npm install

# Run application
npm start
```

Application available at `http://localhost:4200`

## Project Structure

```
.
├── backend/           # Spring Boot REST API
├── frontend/          # Angular application
└── README.md
```

## Documentation

-   [Backend README](backend/README.md) - Configuration and API endpoints
-   [Frontend README](frontend/README.md) - Architecture and commands

## Features

-   JWT Authentication
-   Personalized news feed
-   Article creation and viewing
-   Comment system
-   Topic subscription
-   User profile management
