# Learning Management System (LMS)

A role-based Learning Management System built using Spring Boot.

## Tech Stack

- Java 21
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- MySQL
- Thymeleaf
- JUnit 5
- Mockito
- Maven

## Features

### Admin
- View all users
- Delete users
- Manage platform

### Instructor
- Create courses
- Add lessons
- Upload course materials
- View course details

### Student
- Register and Login
- Browse courses
- Enroll in courses
- Complete lessons
- Track progress

## Security

- JWT Authentication
- Role-Based Authorization
- Spring Security

## Testing

- Controller Tests
- Service Tests
- Repository Tests
- Mockito
- JUnit 5

## Run Locally

Clone the repository:

```bash
git clone https://github.com/Naze-FSD2025/Learning-Management-System.git
```

Configure environment variables:

```properties
SPRING_DATASOURCE_URL=
SPRING_DATASOURCE_USERNAME=
SPRING_DATASOURCE_PASSWORD=
JWT_SECRET=
```

Run:

```bash
mvn spring-boot:run
```

## Project Structure

- Controller Layer
- Service Layer
- Repository Layer
- Entity Layer
- DTO Layer
- Security Layer

## Author

Nazeema Begam
