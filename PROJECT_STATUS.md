# NexusCommerce — Project Status

## Current Phase

Backend development — Order, Checkout and Payment design

---

## Project Goal

Build an e-commerce backend using Spring Boot while understanding the architecture and engineering decisions behind a real-world backend system.

---

## Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA / Hibernate
- PostgreSQL
- Spring Security
- JWT
- Redis
- Maven

---

# Completed

## Project Setup

- Spring Boot project created
- Maven configured
- PostgreSQL configured
- Layered architecture established

## Product Management

- Product entity
- Product CRUD APIs
- Product request/response DTOs
- Product status
- Product validation
- Product-category relationship

## Category Management

- Category entity
- Category APIs
- Category service and repository
- Parent category support
- Category validation

## Validation & Exceptions

- Request validation
- Global exception handling
- ProductNotFoundException
- CategoryNotFoundException
- CartItemNotFoundException
- InsufficientStockException
- ProductUnavailableException
- Standard error response

## Cart

- Cart entity
- CartItem entity
- Cart APIs
- Add item
- Update item
- Remove item
- Retrieve cart

## Authentication

- User entity
- User roles
- Registration
- Login
- JWT infrastructure
- Spring Security configuration

## Address

- Address entity
- Address APIs
- Address service
- Address repository

---

# Currently Working On

## Order + Checkout + Payment

We are designing the complete checkout flow.

The intended high-level flow is:

```text
User
 ↓
Cart
 ↓
Checkout
 ↓
Order
 ↓
Payment
 ↓
Order Confirmation