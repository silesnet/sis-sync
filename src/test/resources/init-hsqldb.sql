DROP TABLE items IF EXISTS;
DROP TABLE invoices IF EXISTS;
DROP TABLE customers IF EXISTS;

CREATE TABLE customers (
    id BIGINT IDENTITY NOT NULL PRIMARY KEY,  
    symbol VARCHAR(9),
    name VARCHAR(45),
    city VARCHAR(50),
    updated TIMESTAMP NOT NULL,
    synchronized TIMESTAMP
);

CREATE TABLE invoices (
    id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    customer_id BIGINT,
    number VARCHAR(20),
    synchronized TIMESTAMP
);

CREATE TABLE items (
    id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    invoice_id BIGINT,
    name VARCHAR(80),
    net DOUBLE PRECISION 
);

