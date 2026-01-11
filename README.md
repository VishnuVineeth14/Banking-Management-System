

# 🏦 Banking Management System (Java – OOP Based)

## 📌 Overview

This project is a **console-based Banking Management System** developed using **Java Object-Oriented Programming (OOP) principles**.
It simulates core banking operations such as customer management, account handling, transactions, and exception handling, following a well-defined **UML-based design**.

The system demonstrates real-world application of Java OOP concepts including **abstraction, inheritance, polymorphism, encapsulation, interfaces, custom exceptions, collections, generics, and file handling**.

---

## 🎯 Problem Statement

To design and implement a **Simple Banking Management System** using Java OOP concepts that models entities such as:

* Bank
* Branch
* Customer
* Account
* Transaction

The system follows the UML structure provided in the project documentation.

---

## ✨ Features

* Create and manage customers
* Create **Savings** and **Current** accounts
* Deposit and withdraw money
* Transfer funds between accounts
* Freeze and unfreeze accounts
* View account details and transaction history
* Handle invalid operations using **custom exceptions**
* Display meaningful messages for all operations

---

## 🧪 Example Workflow

1. Create a Bank
2. Add Branches to the Bank
3. Assign a Clerk and Manager to each Branch
4. Clerk creates Customers
5. Manager creates Accounts for Customers
6. Perform banking operations:

   * Deposit
   * Withdraw
   * Transfer
   * Freeze / Unfreeze
7. View account details and transaction history
8. Handle invalid scenarios gracefully using exceptions

---

## 📁 Project Structure

```text
Banking Management System/
├── README.md
├── README.ipynb
├── customers.csv
├── classDiagram.jpg
│
├── src/
│   ├── accounts/
│   │   ├── Account.java (abstract)
│   │   ├── SavingsAccount.java
│   │   ├── CurrentAccount.java
│   │   └── Transaction.java
│   │
│   ├── bank/
│   │   ├── Bank.java
│   │   ├── BankingSystem.java (entry point)
│   │   ├── BankConsole.java
│   │   ├── Branch.java
│   │   ├── Manager.java
│   │   └── Clerk.java
│   │
│   ├── user/
│   │   ├── Customer.java
│   │   └── CustomerConsole.java
│   │
│   ├── bankexceptions/
│   │   ├── InvalidAmountException.java
│   │   ├── InsufficientFundsException.java
│   │   ├── AccountFrozenException.java
│   │   ├── AccountNotFoundException.java
│   │   └── CustomerNotFoundException.java
│   │
│   ├── interfaces/
│   │   ├── Freezable.java
│   │   ├── Transactable.java
│   │   └── AccountViewer.java
│   │
│   └── database/
│       ├── DataStorage.java
│       └── FileHandler.java
│
├── database/
│   └── (compiled classes)
│
└── data/
    └── (data files)
    
```

---

## ▶️ Compilation & Execution

### Compile all source files

```bash
javac $(find src -name "*.java")
```

### Run the application

```bash
java -cp src bank.BankingSystem
```

---

## 🧩 Packages & Responsibilities

### 1️⃣ interfaces

* **Freezable** – Handles freeze and unfreeze operations
* **Transactable** – Defines transaction-related operations
* **AccountViewer** – Exposes account details and transaction history

---

### 2️⃣ bankexceptions

Custom exception classes extending `Exception`:

* AccountNotFoundException
* AccountFrozenException
* CustomerNotFoundException
* InsufficientFundsException
* InvalidAmountException

---

### 3️⃣ account

* **Account (abstract)** – Base class for all accounts
* **SavingsAccount** – Supports interest calculation
* **CurrentAccount** – No interest calculation
* **Transaction** – Stores transaction details

---

### 4️⃣ user

* **Customer** – Stores customer details and associated accounts

---

### 5️⃣ bank

* **Clerk** – Creates customers
* **Manager** – Creates accounts
* **Branch** – Manages branch-level data
* **Bank** – Holds bank-level information

---

## 🧠 OOP Concepts Demonstrated

### ✔ Classes & Objects

* Meaningful domain classes like `Customer`, `Account`, `Bank`, `Transaction`

### ✔ Encapsulation

* Private fields with controlled access using getters and setters

### ✔ Inheritance

* `Account` as base class
* `SavingsAccount` and `CurrentAccount` as derived classes

### ✔ Polymorphism

* Method overloading and overriding
* Dynamic method dispatch using `Account` references

### ✔ Abstraction

* Abstract classes and interfaces to hide implementation details

### ✔ Constructors

* Default and parameterized constructors
* Constructor overloading and chaining (`this()` / `super()`)

### ✔ Access Modifiers

* Proper use of `private`, `protected`, `public`, and default access

### ✔ Packages

* Modular structure using user-defined packages

### ✔ Exception Handling

* Custom exceptions
* `try-catch-finally` blocks for robust error handling

### ✔ Generics

* Bounded generics (e.g., `<T extends Account>`)

### ✔ Collections Framework

* `ArrayList` for managing customers and accounts
* Sorting and filtering using Java utilities and lambdas

### ✔ File Handling

* Read/write customer and account data using CSV files

---




