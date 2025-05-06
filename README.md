# RemoTech E-shop Backend API

## Current version: 1.1v (06.05.2025)

Added Spring Security framework into project; Now you need to pass appropriate credentials in order to access API endpoints.

## Overview

This repository contains the backend REST API for the RemoTech E-shop, an online store specializing in tech accessories for remote workers, students, and everyday users, with headphones as the primary product category. The backend is built using Spring Boot, Hibernate ORM, and MySQL database.

## Table of Contents

- [Technology Stack](#technology-stack)
- [System Architecture](#system-architecture)
- [API Endpoints](#api-endpoints)
- [Database Schema](#database-schema)
- [Project Structure](#project-structure)
- [Setup and Installation](#setup-and-installation)

## Technology Stack

- **Java 19**
- **Spring Boot 3.4.5** - Main framework providing dependency injection, web MVC, security, etc.
- **Spring Data JPA/Hibernate** - ORM for database interactions
- **Spring Security** - For authentication and authorization
- **MySQL 8.0** - Relational database
- **Maven** - Dependency management and build tool

## System Architecture

The application follows a layered architecture:

1. **Controller Layer** - REST endpoints that receive HTTP requests and return responses
2. **Service Layer** - Business logic implementation
3. **Repository Layer** - Data access layer using Spring Data JPA
4. **Model Layer** - Entity definitions and DTOs
5. **Security Layer** - Authentication and authorization using Spring Security and JWT

## API Endpoints

See current REST API endpoints in file "api-docs.pdf" in "docs".

## Database Schema (OUTDATED)

### Main Entities:

1. **User**

   - id (PK)
   - firstName
   - lastName
   - password (hashed)
   - email (unique)
   - phone
   - createdAt
   - updatedAt
   - enabled
   - role (customer, employee, admin)

2. **Product**

   - id (PK)
   - name
   - description
   - price
   - stockQuantity
   - category_id (FK)
   - createdAt
   - updatedAt

3. **Category**

   - id (PK)
   - name
   - description

4. **Order**

   - id (PK)
   - user_id (FK)
   - totalAmount
   - status (PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED)
   - shippingAddress
   - billingAddress
   - paymentMethod
   - createdAt
   - updatedAt

5. **OrderItem**

   - id (PK)
   - order_id (FK)
   - product_id (FK)
   - quantity
   - priceAtPurchase

6. **Cart**

   - id (PK)
   - user_id (FK)
   - createdAt
   - updatedAt

7. **CartItem**

   - id (PK)
   - cart_id (FK)
   - product_id (FK)
   - quantity

## Project Structure (OUTDATED)

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── remotech/
│   │           ├── RemoTechApplication.java
│   │           ├── config/
│   │           │   ├── SecurityConfig.java
│   │           │   ├── SwaggerConfig.java
│   │           │   └── WebConfig.java
│   │           ├── controller/
│   │           │   ├── AuthController.java
│   │           │   ├── CartController.java
│   │           │   ├── CategoryController.java
│   │           │   ├── OrderController.java
│   │           │   ├── ProductController.java
│   │           │   ├── UserController.java
│   │           │   └── AnalyticsController.java
│   │           ├── dto/
│   │           │   ├── request/
│   │           │   │   ├── LoginRequest.java
│   │           │   │   ├── RegisterRequest.java
│   │           │   │   ├── ProductRequest.java
│   │           │   │   └── OrderRequest.java
│   │           │   └── response/
│   │           │       ├── ApiResponse.java
│   │           │       ├── JwtResponse.java
│   │           │       ├── ProductResponse.java
│   │           │       └── OrderResponse.java
│   │           ├── exception/
│   │           │   ├── GlobalExceptionHandler.java
│   │           │   ├── ResourceNotFoundException.java
│   │           │   ├── BadRequestException.java
│   │           │   └── UnauthorizedException.java
│   │           ├── model/
│   │           │   ├── User.java
│   │           │   ├── Product.java
│   │           │   ├── Category.java
│   │           │   ├── Order.java
│   │           │   ├── OrderItem.java
│   │           │   ├── Cart.java
│   │           │   ├── CartItem.java
│   │           │   └── Review.java
│   │           ├── repository/
│   │           │   ├── UserRepository.java
│   │           │   ├── ProductRepository.java
│   │           │   ├── CategoryRepository.java
│   │           │   ├── OrderRepository.java
│   │           │   ├── OrderItemRepository.java
│   │           │   ├── CartRepository.java
│   │           │   ├── CartItemRepository.java
│   │           │   └── ReviewRepository.java
│   │           ├── security/
│   │           │   ├── JwtTokenProvider.java
│   │           │   ├── JwtAuthenticationFilter.java
│   │           │   ├── UserDetailsServiceImpl.java
│   │           │   └── UserPrincipal.java
│   │           ├── service/
│   │           │   ├── impl/
│   │           │   │   ├── UserServiceImpl.java
│   │           │   │   ├── ProductServiceImpl.java
│   │           │   │   ├── CategoryServiceImpl.java
│   │           │   │   ├── OrderServiceImpl.java
│   │           │   │   ├── CartServiceImpl.java
│   │           │   │   ├── ReviewServiceImpl.java
│   │           │   │   └── AnalyticsServiceImpl.java
│   │           │   ├── UserService.java
│   │           │   ├── ProductService.java
│   │           │   ├── CategoryService.java
│   │           │   ├── OrderService.java
│   │           │   ├── CartService.java
│   │           │   ├── ReviewService.java
│   │           │   └── AnalyticsService.java
│   │           └── util/
│   │               ├── AppConstants.java
│   │               └── PaginationUtil.java
│   └── resources/
│       ├── application.properties
│       ├── application-dev.properties
│       ├── application-prod.properties
│       └── db/
│           └── migration/
│               ├── V1__schema_init.sql
│               └── V2__sample_data.sql
└── test/
    └── java/
        └── com/
            └── remotech/
                ├── controller/
                ├── service/
                └── repository/
```

## Setup and Installation

### Prerequisites

- JDK 17 or higher
- Maven 3.6 or higher
- MySQL 8.0

### Database Setup

1. Create a MySQL database:

```sql
CREATE DATABASE remotech;
CREATE USER 'remotech_admin'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON remotech_db.* TO 'remotech_admin'@'localhost';
FLUSH PRIVILEGES;
```

### Application Setup

1. Clone the repository
2. Configure database properties in `application.properties`
3. Build the application: `mvn clean install`
4. Run the application: `mvn spring-boot:run -Dspring-boot.run.profiles=dev`

## Authentication and Authorization (OUTDATED)

The API uses JWT (JSON Web Tokens) for authentication. Users need to register and login to receive a token, which should be included in the `Authorization` header for subsequent requests.

### User Roles and Permissions (TODO after Frontend)

- **CUSTOMER**: Can browse products, manage their cart, place orders, and view their order history
- **ADMIN**: Has full access to all endpoints
- **EMPLOYEE**: Can manage products and categories
