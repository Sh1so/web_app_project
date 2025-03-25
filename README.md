# RemoTech E-shop Backend API

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

- **Java 17**
- **Spring Boot 3.2.x** - Main framework providing dependency injection, web MVC, security, etc.
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

### Products API

- `GET /api/v1/products` - Get all products with pagination and filtering
- `GET /api/v1/products/{id}` - Get a specific product by ID
- `GET /api/v1/products/categories/{categoryId}` - Get products by category
- `POST /api/v1/products` - Create a new product (Admin/Product Manager only)
- `PUT /api/v1/products/{id}` - Update a product (Admin/Product Manager only)
- `DELETE /api/v1/products/{id}` - Delete a product (Admin/Product Manager only)
- `GET /api/v1/products/recommendations/{productId}` - Get product recommendations

### Categories API

- `GET /api/v1/categories` - Get all categories
- `GET /api/v1/categories/{id}` - Get a specific category
- `POST /api/v1/categories` - Create a new category (Admin/Product Manager only)
- `PUT /api/v1/categories/{id}` - Update a category (Admin/Product Manager only)
- `DELETE /api/v1/categories/{id}` - Delete a category (Admin/Product Manager only)

### Users API

- `POST /api/v1/auth/register` - Register a new customer
- `POST /api/v1/auth/login` - Login for all users
- `GET /api/v1/users/profile` - Get current user profile
- `PUT /api/v1/users/profile` - Update user profile
- `GET /api/v1/users` - Get all users (Admin only)
- `GET /api/v1/users/{id}` - Get user by ID (Admin only)
- `PUT /api/v1/users/{id}/role` - Update user role (Admin only)

### Orders API

- `POST /api/v1/orders` - Create a new order
- `GET /api/v1/orders` - Get all orders for current user (or all orders for Admin)
- `GET /api/v1/orders/{id}` - Get specific order details
- `PUT /api/v1/orders/{id}/status` - Update order status (Admin only)

### Cart API

- `GET /api/v1/cart` - Get current user's cart
- `POST /api/v1/cart/items` - Add item to cart
- `PUT /api/v1/cart/items/{id}` - Update cart item quantity
- `DELETE /api/v1/cart/items/{id}` - Remove item from cart
- `DELETE /api/v1/cart/clear` - Clear the cart

### Analytics API (Admin and Data Analyst only)

- `GET /api/v1/analytics/sales/daily` - Get daily sales data
- `GET /api/v1/analytics/sales/monthly` - Get monthly sales data
- `GET /api/v1/analytics/products/top` - Get top-selling products
- `GET /api/v1/analytics/customers/top` - Get top customers

## Database Schema

### Main Entities:

1. **User**

   - id (PK)
   - email (unique)
   - password (hashed)
   - firstName
   - lastName
   - address
   - phone
   - role (CUSTOMER, ADMIN, PRODUCT_MANAGER, DATA_ANALYST)
   - createdAt
   - updatedAt

2. **Product**

   - id (PK)
   - name
   - description
   - price
   - stockQuantity
   - category_id (FK)
   - specifications (JSON object)
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

## Project Structure

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
CREATE DATABASE remotech_db;
CREATE USER 'remotech_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON remotech_db.* TO 'remotech_user'@'localhost';
FLUSH PRIVILEGES;
```

### Application Setup

1. Clone the repository
2. Configure database properties in `application-dev.properties`
3. Build the application: `mvn clean install`
4. Run the application: `mvn spring-boot:run -Dspring-boot.run.profiles=dev`

## Authentication and Authorization

The API uses JWT (JSON Web Tokens) for authentication. Users need to register and login to receive a token, which should be included in the `Authorization` header for subsequent requests.

### User Roles and Permissions

- **CUSTOMER**: Can browse products, manage their cart, place orders, and view their order history
- **ADMIN**: Has full access to all endpoints
- **PRODUCT_MANAGER**: Can manage products and categories
- **DATA_ANALYST**: Can access analytics endpoints
