SecurePay Online Payment Portal
Overview
SecurePay is an online payment portal that provides customers with the ability to buy goods and securely pay via the web. This documentation provides an overview of the design, technologies used, and instructions for starting the project.

Design Overview
SecurePay consists of two main components: Customer Management and Payment Processing.

Customer Management: Allows users to register new customers. Includes validations to ensure unique email addresses and unique credit card numbers per customer.
Payment Processing: Enables users to place new payments and query payments by criteria such as card number or customer number. It also allows listing all payments within a specified date interval.
Tech Stack
The technology stack used in SecurePay includes:

Java 17: The core programming language.
Spring Boot: Framework for building robust and scalable applications.
Hibernate: Object-relational mapping library for database interactions.
PostgreSQL Database: Relational database for storing application data.
RESTful Endpoints: For communication between the frontend and backend.
JUnit & Mockito: Testing frameworks for unit and integration testing.
Swagger: OpenAPI specification for documenting RESTful APIs.
How to Start the Project
To start the SecurePay project, follow these steps:

Clone the Repository: Clone the project repository from GitHub.
bash
Copy code
git clone https://github.com/KeremShtyn/Firisbe.git
Navigate to Project Directory: Move to the project directory.
bash
Copy code
cd Firisbe
Build the Project: Build the project using Maven.
Copy code
mvn clean install
Run the Application: Run the Spring Boot application.
bash
Copy code
java -jar target/Firisbe.jar
Access the API Documentation: Open your web browser and navigate to http://localhost:8080/swagger-ui.html to access the Swagger UI and explore the RESTful APIs.
Conclusion
SecurePay is a secure and efficient online payment portal designed to provide customers with a seamless purchasing experience. Built with modern technologies and best practices, it ensures reliability, scalability, and security for both customers and merchants.
 
