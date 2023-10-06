# Spring Boot Order Management System

This is a Spring Boot application for managing orders for various goods. It provides REST API endpoints for creating orders, listing available goods, and marking orders as paid.
The system supports roles for both managers and clients.

## Features

- **Manager Features:**
  - Add goods to the database.
  - View the list of available goods with prices and quantities.

- **Client Features:**
  - Place orders for goods.
  - Pay for orders.
  - View the list of available goods with prices and quantities.

- **Automatic Order Management:**
  - Automatically deletes unpaid orders after 10 minutes of creation.

## Technologies Used

- Java 11
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- MySQL Database
- Maven

## Getting Started

1. Clone the repository:
   
   git clone [https://github.com/kavasak119999/Teamvoy.git](https://github.com/kavasak119999/Teamvoy.git)

2. Configure the database:

    Edit application.yaml to set the database URL, username, and password.
    Also, you will need to insert data manually into the tables, as this project does not use Flyway or similar specific tools for database migrations.

   # roles data:
       INSERT INTO roles (name) VALUES ('MANAGER');
       INSERT INTO roles (name) VALUES ('CLIENT');

3. Build the project:

    mvn clean install

4. Run the application:

    mvn spring-boot:run



# API Endpoints

    /api/goods: List available goods (GET).
    /api/goods/add-new: Add a new good (POST).
    /api/orders/place-order: Place a new order (POST).
    /api/orders/mark-paid/{orderId}: Mark an order as paid (POST).

# Security

  The application uses Spring Security for authentication and authorization.
  Two roles are available: MANAGER and CLIENT.
