# INDITEX-PRICE-SERVICE

## Project Overview

The Inditex price service is a Spring Boot application designed to manage pricing information for Inditex. It provides RESTful endpoints to retrieve price details based on product, brand, and application date. The system uses a robust and modular architecture for maintainability and scalability.

## Features

REST API for querying applicable prices.

Data persistence using JPA with an in-memory H2 database.

Integration tests to validate repository and service behavior.

Automated quality checks using SonarCloud.

Using lombok for better readability

Using a java class for inserting the data instead of a .sql file because this way we can avoid external files, we have an easier maintenance of the code, and we could use our own logic for specific cases

Using "entity" annotation to crate the database table based on the Entity "Price" instead of doing it manually in a .sql file

Using builder design pattern for a clean and easier way of creating instance of the entity

Developed the logic using TDD,creating the unit tests first for the logic inside PriceServiceImpl

## Architecture

The application follows a layered architecture:

Controller Layer: Handles HTTP requests and responses.

Service Layer: Contains business logic for determining the applicable price.

Repository Layer: Interacts with the database using Spring Data JPA.

Model Layer: Defines the domain entities and DTOs.

## Technology Stack

Java 21: Latest features and improvements.

Spring Boot 3: Framework for rapid application development.

H2 Database: In-memory database for testing purposes.

JUnit 5: For unit and integration testing.

SonarCloud: For code quality and maintainability analysis.

GitHub Actions: For CI/CD and integration with SonarCloud.

## Setup and Installation

### Prerequisites

JDK 21

Maven 3.8+

A SonarCloud account linked to your GitHub repository

### Running the Application Locally

Clone the repository:

git clone <repository-url>
cd <repository-folder>

### Build and run the application:

mvn spring-boot:run

### Access the API at:

http://localhost:8080/swagger-ui.html

## SonarCloud Integration

SonarCloud has been integrated into this project for continuous code quality analysis. The setup ensures that both the develop and main branches are analyzed after each commit or pull request.

## GitHub Actions Workflow

The GitHub Actions workflow file .github/workflows/sonar.yml is configured to run SonarCloud analysis during CI/CD pipelines.

### Key Points:

The workflow triggers on commits to develop and main branches.

Uses Maven to build the project and execute SonarCloud analysis.

## Testing

### Integration Tests

Comprehensive integration tests ensure that the repository methods and service logic behave as expected.

The DatabaseInitializer component preloads test data for accurate test scenarios.

### Running Tests

Use the following command to execute the tests:

mvn test

## Future Improvements

Add support for external databases.

Enhance error handling with custom exceptions.

Expand test coverage for edge cases.

## Conclusion

The Inditex price service is a well-structured application that integrates quality assurance tools like SonarCloud for maintainability and reliability. This setup ensures robust development workflows and high-quality code.

