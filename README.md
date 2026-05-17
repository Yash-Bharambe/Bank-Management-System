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
