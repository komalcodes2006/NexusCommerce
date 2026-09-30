# NexusCommerce

NexusCommerce is a scalable e-commerce backend engine built with Java and Spring Boot.

The project is being developed as a backend-focused system to understand how real-world e-commerce applications handle product management, users, authentication, carts, checkout, orders, payments, caching, and concurrency.

## Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA / Hibernate
- PostgreSQL
- Spring Security
- JWT
- Redis
- Maven

## Architecture

NexusCommerce follows a layered architecture:

```text
Client
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
PostgreSQL
