DROP TABLE bill_items IF EXISTS;
DROP TABLE bills IF EXISTS;
DROP TABLE customers IF EXISTS;

CREATE TABLE customers (
    id BIGINT IDENTITY NOT NULL PRIMARY KEY,  
    symbol VARCHAR(9),
    name VARCHAR(45),
    city VARCHAR(50),
    is_active BOOLEAN,
    country INT,
    updated TIMESTAMP NOT NULL,
    synchronized TIMESTAMP
);

CREATE TABLE bills (
    id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    number VARCHAR(20),
    billing_date TIMESTAMP,
    customer_id BIGINT,
    synchronized TIMESTAMP
);

CREATE TABLE bill_items (
    id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    bill_id BIGINT,
    text VARCHAR(80),
    net DOUBLE PRECISION 
);

