# Edu Application

This is a Spring Boot-based educational application designed to manage courses and lessons. It provides RESTful APIs for creating, updating, retrieving, and deleting courses and lessons. The application is secured using OAuth2 with Keycloak as the authentication server.

## Features

- **Course Management**: Create, update, retrieve, and delete courses.
- **Lesson Management**: Add, retrieve, and delete lessons within a course.
- **Security**: OAuth2 with JWT-based authentication and role-based access control.
- **Swagger Documentation**: API documentation using OpenAPI 3.0.
- **Database**: PostgreSQL for data storage.
- **Docker**: Docker Compose setup for easy deployment of PostgreSQL and Keycloak.

## Technologies

- **Spring Boot**: Core framework for building the application.
- **Spring Security**: For securing the application with OAuth2.
- **Spring Data JPA**: For database interactions.
- **PostgreSQL**: Relational database for storing course and lesson data.
- **Keycloak**: Open-source Identity and Access Management for authentication and authorization.
- **Swagger**: For API documentation.
- **Lombok**: For reducing boilerplate code.
- **MapStruct**: For mapping between DTOs and entities.
- **Docker**: For containerizing the application and its dependencies.

## Prerequisites

- Java 21
- Docker
- Docker Compose

## Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-repo/edu-app.git
   cd edu-app
   ```

2. **Set up environment variables**:
   - Copy `.env.copy` to `.env` and fill in the required values:
     ```bash
     cp .env.copy .env
     ```

3. **Start the services**:
   - Use Docker Compose to start PostgreSQL and Keycloak:
     ```bash
     docker-compose up -d
     ```

4. **Run the application**:
   - You can run the application using Gradle:
     ```bash
     ./gradlew bootRun
     ```

## API Documentation

Once the application is running, you can access the Swagger UI at:
```
http://localhost:8080/swagger-ui/index.html#/
```

## Endpoints

### Course Endpoints

- **GET /api/v1/courses**: Get all courses.
- **GET /api/v1/courses/{courseId}**: Get a specific course by ID.
- **POST /api/v1/courses**: Create a new course.
- **PUT /api/v1/courses/{courseId}**: Update an existing course.
- **DELETE /api/v1/courses/{courseId}**: Delete a course.

### Lesson Endpoints

- **GET /api/v1/courses/{courseId}/lessons**: Get all lessons for a specific course.
- **POST /api/v1/courses/{courseId}/lessons**: Create a new lesson for a specific course.
- **DELETE /api/v1/courses/{courseId}/lessons/{lessonId}**: Delete a lesson from a specific course.

## Testing

The application includes unit and integration tests. You can run the tests using Gradle:

```bash
./gradlew test
```
