# SecurePay Online Payment Portal

## Overview

SecurePay is an online payment portal that provides customers with the ability to buy goods and securely pay via the web. This documentation provides an overview of the design, technologies used, and instructions for starting the project.

## Design Overview

SecurePay consists of two main components: Customer Management and Payment Processing.

- **Customer Management**: Allows users to register new customers. Includes validations to ensure unique email addresses and unique credit card numbers per customer.

- **Payment Processing**: Enables users to place new payments and query payments by criteria such as card number or customer number. It also allows listing all payments within a specified date interval.

## Tech Stack

The technology stack used in SecurePay includes:

- **Java 17**: The core programming language.
- **Spring Boot**: Framework for building robust and scalable applications.
- **Spring Security**: Provides authentication, authorization, and other security features for Spring applications.
- **Hibernate**: Object-relational mapping library for database interactions.
- **PostgreSQL Database**: Relational database for storing application data.
- **RESTful Endpoints**: For communication between the frontend and backend.
- **JUnit & Mockito**: Testing frameworks for unit and integration testing.
- **Swagger**: OpenAPI specification for documenting RESTful APIs.

## How to Start the Project

To start the SecurePay project, follow these steps:

1. **Clone the Repository**: Clone the project repository from GitHub.

`git clone https://github.com/KeremShtyn/Firisbe.git`


2. **Navigate to Project Directory**: Move to the project directory.

`cd Firisbe`


3. **Set Up PostgreSQL Database**: Ensure that you have a PostgreSQL database instance running. Update the database configuration in the `application.properties` file to connect to your PostgreSQL database.

```properties
# Database
datasource:
driver-class-name: org.postgresql.Driver
url: jdbc:postgresql://localhost:5438/postgres
username: postgres
password: postgres 
```
4. **Database Connection with Docker Compose**:

```
version: '3.7'

services:
firisbe:
image: postgres:10.5
restart: always
environment:
- POSTGRES_USER=postgres
- POSTGRES_PASSWORD=postgres
logging:
options:
max-size: 10m
max-file: "3"
ports:
- '5438:5432'
volumes:
- /path/to/your/local/directory:/var/lib/postgresql/firisbe
```

This Docker Compose file starts a container with PostgreSQL 10.5 version. It uses a volume (/var/lib/postgresql/firisbe) to persist the database content. Also, it maps port 5438 on your local machine to port 5432 inside the container.
Please replace /path/to/your/local/directory with your actual local directory path. This path should indicate a local directory used to store persistent data for the PostgreSQL database.

Review and update this Docker Compose file according to any specific configurations you have.

5. **Build and Run the Application**: Build the project using Maven and run the Spring Boot application.

`
mvn clean install
java -jar target/Firisbe.jar
`

6. **Access the API Documentation**: Open your web browser and navigate to `http://localhost:8080/swagger-ui.html` to access the Swagger UI and explore the RESTful APIs.

##Conclusion
SecurePay is a secure and efficient online payment portal designed to provide customers with a seamless purchasing experience. Built with modern technologies and best practices, it ensures reliability, scalability, and security for both customers and merchants.