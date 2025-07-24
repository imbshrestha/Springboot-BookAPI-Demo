# Book API - A RESTful Service with Java and Spring Boot

This project is a complete, industry-standard RESTful API for managing a collection of books. It is built with Java and the Spring Boot framework, demonstrating best practices such as a layered architecture, dependency injection, global exception handling, and comprehensive unit/integration testing.

## Features

*   **CRUD Operations:** Full support for Creating, Reading, Updating, and Deleting books.
*   **Layered Architecture:** Clear separation of concerns between Controllers (API Layer), Services (Business Logic), and Repositories (Data Access).
*   **Robust Error Handling:** A centralized exception handler provides consistent and informative JSON error responses for different scenarios (e.g., `404 Not Found`).
*   **In-Memory Database:** Uses H2, an in-memory SQL database, for easy development and testing without external database setup.
*   **Comprehensive Testing:** Includes unit tests for the service layer (using Mockito) and integration tests for the controller/web layer (using `MockMvc`).

## Technologies Used

*   **Java 17:** The core programming language.
*   **Spring Boot:** Framework for building stand-alone, production-grade applications.
*   **Spring Web:** For building RESTful web applications.
*   **Spring Data JPA:** For simplifying data access and ORM.
*   **H2 Database:** An in-memory database for development and testing.
*   **Maven:** For project dependency management and build automation.
*   **JUnit 5:** The standard for writing tests in the Java ecosystem.
*   **Mockito:** A mocking framework for creating test doubles in unit tests.

## Getting Started

Follow these instructions to get a copy of the project up and running on your local machine for development and testing.

### Prerequisites

*   **Java Development Kit (JDK)** - Version 17 or later.
*   **Apache Maven** - For building the project and managing dependencies.
*   **Git** - For cloning the repository.

### Installation & Running the Application

1.  **Clone the repository:**
    ```sh
    git clone <your-repository-url>
    cd book-api
    ```

2.  **Run the application using Maven:**
    ```sh
    mvn spring-boot:run
    ```

3.  The API will start and be available at `http://localhost:8080`.

### H2 Database Console

This project uses an in-memory H2 database. You can access its web console to view the data and schema while the application is running.

*   **URL:** `http://localhost:8080/h2-console`
*   **Settings:**
    *   **Driver Class:** `org.h2.Driver`
    *   **JDBC URL:** `jdbc:h2:mem:testdb`
    *   **User Name:** `sa`
    *   **Password:** (leave blank)

Click **Connect** to access the database.

## API Endpoints Documentation

The base URL for all endpoints is `/api/books`.

---

#### 1. Create a Book

Adds a new book to the collection.

*   **Request:** `POST /api/books`
*   **Headers:** `Content-Type: application/json`
*   **Request Body:**
    ```json
    {
        "title": "The Lord of the Rings",
        "author": "J.R.R. Tolkien",
        "isbn": "978-0618640157"
    }
    ```
*   **Success Response:** `201 Created`
    ```json
    {
        "id": 1,
        "title": "The Lord of the Rings",
        "author": "J.R.R. Tolkien",
        "isbn": "978-0618640157"
    }
    ```

---

#### 2. Get All Books

Retrieves a list of all books.

*   **Request:** `GET /api/books`
*   **Success Response:** `200 OK`
    ```json
    [
        {
            "id": 1,
            "title": "The Lord of the Rings",
            "author": "J.R.R. Tolkien",
            "isbn": "978-0618640157"
        },
        {
            "id": 2,
            "title": "The Hobbit",
            "author": "J.R.R. Tolkien",
            "isbn": "978-0345339683"
        }
    ]
    ```

---

#### 3. Get a Single Book by ID

Retrieves a single book by its unique ID.

*   **Request:** `GET /api/books/{id}`
*   **Success Response:** `200 OK`
    ```json
    {
        "id": 1,
        "title": "The Lord of the Rings",
        "author": "J.R.R. Tolkien",
        "isbn": "978-0618640157"
    }
    ```
*   **Error Response:** `404 Not Found` if the book ID does not exist.
    ```json
    {
        "timestamp": "2025-07-24T18:30:00.000Z",
        "message": "Book not found with id: 99",
        "details": "uri=/api/books/99"
    }
    ```

---

#### 4. Update a Book

Updates the details of an existing book.

*   **Request:** `PUT /api/books/{id}`
*   **Headers:** `Content-Type: application/json`
*   **Request Body:**
    ```json
    {
        "title": "The Hobbit: An Unexpected Journey",
        "author": "J.R.R. Tolkien",
        "isbn": "978-0345339683"
    }
    ```
*   **Success Response:** `200 OK` with the updated book object.
*   **Error Response:** `404 Not Found` if the book ID does not exist.

---

#### 5. Delete a Book

Deletes a book from the collection by its ID.

*   **Request:** `DELETE /api/books/{id}`
*   **Success Response:** `204 No Content`
*   **Error Response:** `404 Not Found` if the book ID does not exist.

## Running the Tests

This project has a suite of unit and integration tests to ensure the code is working correctly. To run the tests, execute the following Maven command from the project root:

```sh
mvn test
```

A test report will be generated in the `target/surefire-reports` directory.

## Running with Docker

This application is fully containerized using Docker, providing a consistent and isolated environment. Follow these steps to build the Docker image and run the application as a container.

### Prerequisites

*   **Docker Desktop** must be installed and running on your local machine.

### Build & Run with Docker Commands

This is the standard, universal way to run the application using Docker commands from your terminal.

#### 1. Build the Docker Image

First, you need to build the image from the `Dockerfile`. This command packages the application and all its dependencies into a single, portable image.

Navigate to the project's root directory in your terminal and run:

```sh
docker build -t book-api:latest .
