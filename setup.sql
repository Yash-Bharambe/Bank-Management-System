CREATE DATABASE bankDB;

USE bankDB;

CREATE TABLE accounts (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    balance DOUBLE,
    password VARCHAR(50)
);

CREATE TABLE transactions (
    txn_id INT AUTO_INCREMENT PRIMARY KEY,
    acc_id INT,
    type VARCHAR(20),   -- deposit / withdraw / transfer_in / transfer_out
    amount DOUBLE,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);