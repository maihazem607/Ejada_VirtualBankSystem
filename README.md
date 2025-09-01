# Virtual Bank System

A microservices-based virtual banking system featuring a Backend for Frontend (BFF) and WSO2 API Gateway. This project demonstrates a modern, scalable application architecture.

## Core Architecture

- **Microservices**: The system is divided into independent services for Users, Accounts, and Transactions, each built with Java Spring Boot.
- **Backend for Frontend (BFF)**: An aggregator service that simplifies frontend interactions by orchestrating calls to backend microservices.
- **WSO2 API Gateway**: The single entry point for all external requests, handling routing, security (OAuth2, API Keys), and rate limiting.
- **Logging**: A dedicated microservice consumes logs from a Kafka topic for centralized monitoring and auditing.

## Microservices

### 1. User Service (Port: 8090)

Manages user registration, authentication, and profiles.

- `POST /users/register`
- `POST /users/login`
- `GET /users/{userId}/profile`

### 2. Account Service (Port: 8091)

Manages user bank accounts, balances, and status.

- `POST /accounts`
- `GET /accounts/{accountId}`
- `GET /users/{userId}/accounts`
- `PUT /accounts/transfer`
- **Scheduled Job**: Inactivates accounts with no transactions for more than 24 hours (runs hourly).

### 3. Transaction Service (Port: 8092)

Handles financial transactions and history.

- `POST /transactions/transfer/initiation`
- `POST /transactions/transfer/execution`
- `GET /accounts/{accountId}/transactions`


### 4. BFF Service (Port: 8093)

Aggregates data from other services to provide a consolidated view for the frontend.

- `GET /bff/dashboard/{userId}`: Fetches a user's profile, accounts, and recent transactions in a single call.
  

### 5. Logging Service (Port: 8096)

A dedicated microservice consumes logs from a Kafka topic for centralized monitoring and auditing.

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

## Postman Collections

To simplify API testing, ready-made **Postman collections** are provided in the  
[`postman-collections/`](./postman-collections/) folder.

### Available Collections
- `User Service.postman_collection.json` → User Service endpoints  
- `Account Service.postman_collection.json` → Account Service endpoints  
- `Transaction Service.postman_collection.json` → Transaction Service endpoints  
- `BFF Service.postman_collection.json` → BFF Service endpoints  

### Import Instructions
1. Open **Postman**.  
2. Click **Import** → select the `.json` file(s) from the `postman-collections/` folder.  
3. The collections will appear grouped by service.  

Each collection includes example requests with placeholder IDs.  
Replace these with actual IDs once your services are running.
