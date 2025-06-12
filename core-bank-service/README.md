# 💰 Core Banking - Savings Account Module (PoC)

This is a backend proof-of-concept (PoC) for a **Savings Account** module simulating core banking functionalities. The focus is on **design**, **security**, **performance**, and **best practices** using **Java**, **Spring Boot**, and **Maven**.

## 📦 Features Implemented

### ✅ Online Registration
- Register with email, password, and personal info.
- Enforces uniqueness of citizen ID.
- Only unregistered PERSON can register.

### ✅ Account Creation
- TELLER can create accounts for both PERSON and CUSTOMER.
- Generates a unique 7-digit account number.
- Initial deposit optional.

### ✅ Money Deposit
- Only TELLERs can deposit (≥ 1 THB).
- Concurrency-safe and idempotent to prevent duplicated postings.

### ✅ Account Information
- CUSTOMER can login and view only **their** accounts.
- Enforced access control: no cross-account visibility.

### ✅ Money Transfer
- CUSTOMER can transfer money to any other account (PIN confirmation required).
- Enforces positive balance constraint (no overdrafts).

### ✅ Bank Statement
- Monthly statement with date, type, channel, debit/credit, balance, and remarks.
- Sorted from past to present.

## 🚧 Tech Stack

- **Java 17+**
- **Spring Boot**
- **Maven**
- **H2 (in-memory DB for PoC)**
- **PostgreSQL**
- **JUnit & Mockito (for testing)**
- **Docker & Docker Compose**

## 🛡️ Critical Requirements Handling

- ✅ **No duplicate transactions**: Uses transactional control and idempotency checks to prevent duplicate record processing.
- ✅ **No access control flaw**: All endpoints are securely scoped to authenticated users.

## 🧪 Testing

- Unit tests written using JUnit 5.
- Tests are parallelizable and stable (non-flaky).
- Run via standard Maven lifecycle:

```bash
mvn clean install
```

## 🐳 Run with Docker

Ensure you have Docker installed. To run:

```bash
docker compose up
```


## 🚀 Startup  start the application with Shell Script:
```sh
.startup.sh
```

## 🛑 Stop the application with Shell Script:
```sh
.stop.sh
```



## 📐 Architecture Overview

### Components
- **User Service**: Registration, login, PIN validation
- **Account Service**: Deposits, account creation, Account management
- **Transaction Service**: Transfers and statements
- **Database**: Relational store with account, transaction, and user data

### Design Principles
- Stateless backend
- Role-based access control
- Layered architecture (Controller, Service, Repository)
- Separation of concerns and scalability in mind

## 📄 License

This repository is released as a PoC for evaluation purposes only.
