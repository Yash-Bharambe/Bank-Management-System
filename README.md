# 🏦 Bank Management System

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)


A comprehensive, console-based Bank Management System built with Java, MySQL, and JDBC. This project simulates real-world banking operations, featuring secure authentication, ACID-compliant transactions, and a robust layered architecture.

## 📋 Table of Contents
- [Features](#-features)
- [Tech Stack](#%EF%B8%8F-tech-stack)
- [Architecture](#%EF%B8%8F-architecture)
- [Database Schema](#-database-schema)
- [Getting Started](#-getting-started)
- [Usage](#-usage)
- [Portfolio Highlights](#-portfolio-highlights)

## ✨ Features

- 🔐 **Secure Authentication**: Password-protected login system for accounts.
- ➕ **Account Management**: Create new accounts with initial balances or delete existing ones (Admin functions).
- 💰 **Core Banking**: Deposit and withdraw funds with strict balance validation.
- 🔄 **Fund Transfers**: Securely transfer money between accounts using ACID-compliant database transactions.
- 📜 **Transaction History**: Comprehensive logging and viewing of all account activities.
- 👀 **Directory**: Search specific accounts by name or view the entire account registry.
- 🛡️ **Robust Security**: SQL injection prevention via `PreparedStatement` and reliable exception handling with rollback mechanisms.

## 🛠️ Tech Stack

- **Language:** Java 11+
- **Database:** MySQL Server
- **Database Connectivity:** JDBC

## 🏗️ Architecture

The application follows a clean, layered architecture to separate business logic from data access:

- **`Main.java`**: The presentation layer handling the user interface and menu navigation.
- **`AccountService.java`**: The business logic layer handling validations, calculations, and coordinating operations.
- **`AccountDAO.java`**: The Data Access Object layer strictly responsible for executing MySQL queries.
- **Models (`Account.java`, `Transaction.java`)**: Plain Old Java Objects (POJOs) representing database entities.
- **`DBConnection.java`**: Singleton-patterned database connection management.

## 🗄️ Database Schema

### `Accounts` Table
```sql
CREATE TABLE Accounts (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    balance DOUBLE,
    password VARCHAR(50)
);
```

## `Transactions` Table

```sql
CREATE TABLE Transactions (
    txn_id INT AUTO_INCREMENT PRIMARY KEY,
    acc_id INT,
    type VARCHAR(20),
    amount DOUBLE,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (acc_id)
    REFERENCES Accounts(id)
    ON DELETE CASCADE
);
````

### Transaction Types

* `deposit`
* `withdraw`
* `transfer_in`
* `transfer_out`

---

# 🚀 Getting Started

## Prerequisites

Make sure the following are installed:

* Java Development Kit (JDK 11 or higher)
* MySQL Server
* Apache Maven

---

# 📥 Installation & Setup

## 1. Clone the Repository

```bash
git clone https://github.com/Yash-Bharambe/Bank-Management-System.git
cd Bank-Management-System
```

---

## 2. Database Configuration

Open MySQL Command Line or MySQL Workbench and run:

```sql
source setup.sql;
```

---

## 3. Configure Database Credentials

Open:

```text
src/main/java/.../DBConnection.java
```

Update credentials:

```java
private static final String USER = "root";
private static final String PASS = "<your_password>";
```

---

## 4. Build the Project

```bash
mvn clean compile
```

---

## 5. Run the Application

```bash
mvn exec:java
```

---

# 💻 Usage

After running the application:

1. **Login**

   * Enter account ID and password.

2. **Perform Banking Operations**

   * Deposit money
   * Withdraw money
   * Transfer funds
   * Check balance
   * View transaction history

3. **Admin Features**

   * View all accounts
   * Search account by name
   * Delete accounts

---

# 🎯 Portfolio Highlights

This project demonstrates strong software engineering fundamentals and backend development skills.

## Key Concepts Demonstrated

### ✅ Object-Oriented Programming (OOP)

* Encapsulation using Service and DAO layers
* Clean separation of concerns

### ✅ Database Design

* Relational schema design
* Foreign key constraints
* Cascading delete support

### ✅ Transaction Management

Implementation of **ACID Properties**:

* **Atomicity**

  * Transfers complete fully or rollback entirely.

* **Consistency**

  * Database remains valid after every operation.

* **Isolation**

  * Concurrent transactions remain independent.

* **Durability**

  * Committed transactions are permanently stored.

### ✅ Security Best Practices

* SQL Injection prevention with `PreparedStatement`
* Secure exception handling
* Transaction rollback mechanisms

### ✅ JDBC Integration

* Real-world database connectivity
* CRUD operations using JDBC

---

# 📌 Future Improvements

Possible enhancements for future versions:

* GUI using JavaFX or Swing
* Role-based authentication
* OTP verification
* REST API integration
* Docker deployment
* Spring Boot migration
* Unit testing with JUnit

---

# 👨‍💻 Author

**Yash Bharambe**

GitHub Repository:

```text
https://github.com/Yash-Bharambe/Bank-Management-System
```

---

# ⚠️ Troubleshooting

## MySQL Connection Error

If you encounter connection issues:

* Ensure MySQL server is running.
* Verify username and password in `DBConnection.java`.
* Check database name and port configuration.
* Ensure JDBC driver dependency is correctly added in `pom.xml`.

---

# 📄 License

This project is for educational and portfolio purposes.

---

```
```

