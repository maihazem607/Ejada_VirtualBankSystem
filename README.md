# Virtual Bank System

A microservices-based virtual banking system featuring a Backend for Frontend (BFF) and WSO2 API Gateway. This project demonstrates a modern, scalable application architecture.

## Core Architecture

- **Microservices**: The system is divided into independent services for Users, Accounts, and Transactions, each built with Java Spring Boot.
- **Backend for Frontend (BFF)**: An aggregator service that simplifies frontend interactions by orchestrating calls to backend microservices.
- **WSO2 API Gateway**: The single entry point for all external requests, handling routing, security (OAuth2, API Keys), and rate limiting.
- **Logging**: A dedicated microservice consumes logs from a Kafka topic for centralized monitoring and auditing.

## Microservices

### 1. User Service

Manages user registration, authentication, and profiles.

- `POST /users/register`
- `POST /users/login`
- `GET /users/{userId}/profile`

### 2. Account Service

Manages user bank accounts, balances, and status.

- `POST /accounts`
- `GET /accounts/{accountId}`
- `GET /users/{userId}/accounts`
- `PUT /accounts/transfer`
- **Scheduled Job**: Inactivates accounts with no transactions for more than 24 hours (runs hourly).

### 3. Transaction Service

Handles financial transactions and history.

- `POST /transactions/transfer/initiation`
- `POST /transactions/transfer/execution`
- `GET /accounts/{accountId}/transactions`


### 4. BFF Service

Aggregates data from other services to provide a consolidated view for the frontend.

- `GET /bff/dashboard/{userId}`: Fetches a user's profile, accounts, and recent transactions in a single call.

## Technology Stack

- **Language**: Java 21
- **Framework**: Spring Boot
- **Build Tool**: Maven
- **API Gateway**: WSO2 API Manager
- **Database**: MySQL or PostgreSQL
- **API Testing**: Postman

---


## Setup Steps

1.  **Database Setup**: Ensure you have a running instance of MySQL (or your chosen database).
2.  **Create Databases**: For each microservice (`user-service`, `account-service`, `transaction-service`), create a corresponding database.
    - Example: `CREATE DATABASE user_service_db;`
3.  **Configure Each Service**: For each microservice (`userService`, `accountService`, etc.):
    - Navigate to the service's directory.
    - Create the folder `src/main/resources` if it doesn't exist.
    - Inside `resources`, create a file named `application.properties`.
    - Add the following configuration, adjusting the values for each service.

**`application.properties` Template (for MySQL):**

```properties
# Service Configuration
spring.application.name=user-service # Change for each service (e.g., account-service)
server.port=8090 # Change for each service (user: 8090, account: 8091, transaction: 8092, bff: 8093)

# Datasource Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/user_service_db?useSSL=false # Change DB name for each service
spring.datasource.username=root
spring.datasource.password=your_password # Replace with your MySQL password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Hibernate settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Kafka Consumer
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=logging-group
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer
logging.kafka.topic=log-topic
```

## Postman Endpoints for Testing

For all `POST` and `PUT` requests, set the `Content-Type` header to `application/json`.

Note: The request/response bodies shown below are only examples to illustrate the structure. You will need to replace the placeholder IDs with the actual generated IDs when running the commands.

### User Service (Port: 8090)

- **Register User**: `POST` `http://localhost:8090/users/register`
  **Body:**

  ```json
  {
    "username": "john.doe",
    "password": "securePassword123",
    "email": "john.doe@example.com",
    "firstName": "John",
    "lastName": "Doe"
  }
  ```

- **Login User**: `POST` `http://localhost:8090/users/login`
  **Body:**

  ```json
  {
    "username": "john.doe",
    "password": "securePassword123"
  }
  ```

- **Get User Profile**: `GET` `http://localhost:8090/users/{userId}/profile`

### Account Service (Port: 8091)

- **Create Account**: `POST` `http://localhost:8091/accounts`
  **Body:**

  ```json
  {
    "userId": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
    "accountType": "CREDIT",
    "initialBalance": 100.0
  }
  ```

- **Get Account Details**: `GET` `http://localhost:8091/accounts/{accountId}`
- **Get User Accounts**: `GET` `http://localhost:8091/users/{userId}/accounts`

- **Transfer Funds**: `PUT` `http://localhost:8091/accounts/transfer`
  **Body:**
  ```json
  {
    "fromAccountId": "f1e2d3c4-b5a6-9876-5432-10fedcba9876",
    "toAccountId": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
    "amount": 100.0
  }
  ```

### Transaction Service (Port: 8092)

- **Initiate Transfer**: `POST` `http://localhost:8092/transactions/transfer/initiation`
  **Body:**

  ```json
  {
    "fromAccountId": "f1e2d3c4-b5a6-9876-5432-10fedcba9876",
    "toAccountId": "g7h8i9j0-k1l2-3456-7890-abcdef123456",
    "amount": 30.0,
    "description": "Transfer to checking account"
  }
  ```

- **Execute Transfer**: `POST` `http://localhost:8092/transactions/transfer/execution`
  **Body:**

  ```json
  {
    "transactionId": "t1r2a3n4-s5a6-7890-1234-567890abcdef"
  }
  ```

- **Get Account Transactions**: `GET` `http://localhost:8092/accounts/{accountId}/transactions`

### BFF Service (Port: 8093)

- **Get Dashboard**: `GET` `http://localhost:8093/bff/dashboard/{userId}`


