Support System Backend

Backend for a client and support ticket management system built with Spring Boot and PostgreSQL.

Technologies





Backend: Spring Boot (Java), Spring Data JPA



Database: PostgreSQL



Testing: JUnit, Mockito

Installation





Install Dependencies:





JDK 17



PostgreSQL 14+



Maven (included with IntelliJ)



Database Setup:





Create a PostgreSQL database:

CREATE USER support_user WITH PASSWORD 'support123';
CREATE DATABASE support_system OWNER support_user;



Run the SQL script (database_schema.sql):

CREATE TABLE clients (
id BIGSERIAL PRIMARY KEY,
name VARCHAR(255) NOT NULL
);

CREATE TABLE tickets (
id BIGSERIAL PRIMARY KEY,
client_id BIGINT NOT NULL,
category VARCHAR(50) NOT NULL,
content TEXT NOT NULL,
status VARCHAR(50) NOT NULL,
FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE
);



Backend Setup:





Open support-system-backend in IntelliJ.



Ensure src/main/resources/application.properties is configured:

spring.datasource.url=jdbc:postgresql://localhost:5432/support_system
spring.datasource.username=support_user
spring.datasource.password=support123
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true



Run the application:

mvn spring-boot:run



Running Tests:





Execute unit tests:

mvn test

API Endpoints





Clients:





GET /api/clients: List all clients



GET /api/clients/{id}: Get client by ID



POST /api/clients: Create a client



PUT /api/clients/{id}: Update a client



DELETE /api/clients/{id}: Delete a client



Tickets:





GET /api/tickets: List all tickets



GET /api/tickets/client/{clientId}: List tickets by client ID



GET /api/tickets/{id}: Get ticket by ID



POST /api/tickets/{clientId}: Create a ticket for a client



PUT /api/tickets/{id}: Update a ticket



DELETE /api/tickets/{id}: Delete a ticket

Notes





The backend is configured to allow CORS requests from http://localhost:3000 (React frontend).



Unit tests cover all service and controller methods.