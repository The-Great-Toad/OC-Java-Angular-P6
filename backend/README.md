# Backend MDD - REST API

REST API for the MDD project (Monde de Dév) - A social network for developers.

## Prerequisites

-   JDK 17 or higher
-   Maven 3.8+
-   MySQL 8.0+

## Technologies

-   **Java 17**
-   **Spring Boot 3.5.7**
-   **Spring Data JPA**
-   **Spring Security**
-   **MySQL 8.0**
-   **Lombok**
-   **Maven**

## Project Structure

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/openclassrooms/mdd/
│   │   │   ├── model/           # JPA Entities
│   │   │   ├── repository/      # Spring Data Repositories
│   │   │   ├── service/         # Business Services
│   │   │   │   └── impl/        # Service Implementations
│   │   │   ├── controller/      # REST Controllers
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── exception/       # Exception Handling
│   │   │   ├── config/          # Spring Configurations
│   │   │   └── MddApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── init-db.sql
│   └── test/                    # Unit and Integration Tests
├── pom.xml
└── README.md
```

## 🚀 Installation and Setup

### 1. Configure MySQL

Run the database initialization script:

```bash
mysql -u root -p < src/main/resources/init-db.sql
```

### 2. Configure application.properties

Modify MySQL credentials if necessary:

```properties
spring.datasource.username=mdd_user
spring.datasource.password=password
```

### 3. Clone, Build and Run the Application

```bash
# Clone the project
git clone https://github.com/The-Great-Toad/OC-Java-Angular-P6.git
cd OC-Java-Angular-P6

# Build
cd backend
mvn clean install

# Run the application
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`
