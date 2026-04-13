# Todo List API with JWT Authentication

This is a RESTful API to-do list management system developed based on **Java + Spring Boot 3**.

The project implements a complete **Spring Security and JWT (JSON Web Token)** authentication mechanism, ensuring that each user "can only access and manage their own to-do lists," and features a highly secure stateless authorization architecture.

## Core Features

* **User Registration and Login**: Automatically checks for duplicate accounts and uses `BCrypt` for one-way password encryption protection.
* **JWT Authentication**: Issues a JWT Token upon successful login. Subsequent API requests are intercepted and verified through `JwtRequestFilter`, preventing unauthorized access (403 Forbidden).
* **Todo CRUD Operations**: Provides functions for adding, querying, modifying, and deleting to-do lists.
* **Data Isolation Protection**: Obtains the identity of the currently logged-in user through SecurityContext, strictly preventing unauthorized access to others' data.
* **Security Details:** Sensitive information (such as encrypted passwords) is prevented from being leaked during API feedback using `@JsonIgnore`.

## Tech Stack

* **Language:** Java 21
* **Framework:** Spring Boot 3.x
* **Security:** Spring Security, JWT (jjwt)
* **Database Operations:** Spring Data JPA (Hibernate)
* **Database:** MySQL (with a compatible H2 memory database for quick testing)
* **Tools:** Maven, Lombok, Postman/cURL

## 🔗 API Endpoints

| HTTP Method | Endpoint | Description | Requires JWT |
| :--- | :--- | :--- | :---: |
| **POST** | `/api/auth/register` | Register a new user | ❌ No |
| **POST** | `/api/auth/login` | User login (Returns Token) | ❌ No |
| **GET** | `/api/todos` | Get all todos for the current user | 🔒 Required |
| **POST** | `/api/todos` | Create a new todo | 🔒 Required |
| **PUT** | `/api/todos/{id}` | Update a specific todo | 🔒 Required |
| **DELETE** | `/api/todos/{id}` | Delete a specific todo | 🔒 Required |


## Local Server Tutorial (Getting Started)
### 1. System Requirements

Please ensure your development environment has the following installed:
* JDK 21 or higher
* Maven
* MySQL (with a pre-created empty database named `todo_jwt_db`)

### 2. Environment Settings
Open In `src/main/resources/application.properties`, modify the settings to match your database:

`properties`
* spring.datasource.url=jdbc:mysql://localhost:3306/todo_jwt_db
* spring.datasource.username=root
* spring.datasource.password=your password
* spring.jpa.hibernate.ddl-auto=update


# Start project
## 下載相依套件並編譯
mvn clean install
## 啟動 Spring Boot 應用程式
mvn spring-boot:run

# Future Improvements
* Integrate Swagger UI/SpringDoc for automatic API file generation
* Add JUnit and Mockito for unit and integration testing
* Implement a Global Exception Handler to standardize error posting format