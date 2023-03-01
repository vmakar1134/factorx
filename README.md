# Multitenant Application

This is a Spring Boot application that uses a multitenant architecture to allow multiple tenants to use the same application with their own isolated data.

## Architecture
The application is composed of several modules that work together to provide the desired functionality. The modules are:

- Registry: Creates and manages schemas for each tenant.
- Tenant: Manages the modules that belong to each tenant.
- Super Admin: Manages all tenants and their data.
- Mobile: Provides a mobile interface for the application.

## Features
The application includes the following features:

- User management: Allows the super admin to manage all users across all tenants.
- Tenant management: Allows the super admin to create, update, and delete tenants.
- Schema creation: Allows the registry module to create new schemas for tenants.
- Module management: Allows tenants to manage the modules that belong to them.
- RabbitMQ: Uses an event-driven approach with RabbitMQ to allow modules to communicate without direct coupling.

## Getting Started
To get started with the application, follow these steps:

1. Clone the repository to your local machine.
2. Run `mvn clean install` in the root directory to build the application.
3. Run `java -jar target/multitenant-app.jar` to start the application.
4. Navigate to `http://localhost:8080` in your web browser to access the application.

## Dependencies
The application uses the following dependencies:

- Spring Boot 2.5.4
- PostgreSQL 13.4
- RabbitMQ 3.9.7
- Liquibase 4.4.3
  
