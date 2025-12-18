# Backend MDD - REST API

REST API for MDD social network (Monde de Dév).

## Tech Stack

-   **Java 17 LTS**
-   **Spring Boot 3.4.0**
-   **Spring Security + JWT**
-   **Spring Data JPA**
-   **MySQL 8.0**
-   **Maven**

## Prerequisites

-   JDK 17+
-   Maven 3.8+
-   MySQL 8.0+

## Installation

```bash
# 1. Create database
mysql -u root -p < src/main/resources/init-db.sql

# 2. Configure application.properties (if needed)
# spring.datasource.username=mdd_user
# spring.datasource.password=password

# 3. Run application
mvn spring-boot:run
```

API available at `http://localhost:8080`

## Main Endpoints

-   `POST /api/auth/register` - User registration
-   `POST /api/auth/login` - User login
-   `GET /api/feed` - News feed
-   `GET /api/topics` - List topics
-   `POST /api/topics/{id}/subscribe` - Subscribe to topic
-   `GET /api/posts/{id}` - Article details
-   `POST /api/posts` - Create article
-   `GET /api/users/me` - User profile
