# 🚀 Banking Management System (BMS) - Java Project

A **Banking Management System** that allows users to **create accounts**, **deposit/withdraw money**, **transfer funds**, **check balances**, and **track transactions** through both **CLI** and **GUI** interfaces.

---

## 📋 Project Overview

> A simple yet powerful banking system for basic operations like account management, transactions, and fund transfers.  
> Built using **Java**, **MySQL**, **JDBC**, and **Swing**.

---

## 📌 Project Phases

### ✅ Phase 1: JDBC-Based CLI Application

- 🆔 User Registration & Login
- 🏦 Account Creation (Saving/Current)
- 💰 Deposit & Withdraw Money
- 🏧 Check Balance
- 🔄 Fund Transfer between Accounts
- 📜 Transaction History

---

### ✅ Phase 2: Swing GUI Version

- 🔑 User Login Form
- 📊 Dashboard with Account Balance
- 💰 Deposit & Withdraw Forms
- 🔄 Fund Transfer Screen
- 📜 Transaction History Table

---

## 🗄️ Database Schema (MySQL)

```sql
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE accounts (
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    account_type ENUM('SAVINGS', 'CURRENT'),
    balance DOUBLE DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE transactions (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT,
    type ENUM('DEPOSIT', 'WITHDRAWAL', 'TRANSFER'),
    amount DOUBLE,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id)
);
