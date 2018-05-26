DROP TABLE invoicings IF EXISTS;
DROP TABLE bill_items IF EXISTS;
DROP TABLE bills IF EXISTS;
DROP TABLE customers IF EXISTS;
DROP TABLE settings IF EXISTS;

CREATE TABLE customers (
    id BIGINT IDENTITY NOT NULL PRIMARY KEY,  
    symbol VARCHAR(9),
    name VARCHAR(45),
    supplementary_name VARCHAR(45),
    contact_name VARCHAR(45),
    city VARCHAR(50),
    street VARCHAR(50),
    postal_code VARCHAR(6),
    public_id VARCHAR(10),
    dic VARCHAR(15),
    phone VARCHAR(30),
    email VARCHAR(50),
    variable INT,
    account_no VARCHAR(17),
    bank_no VARCHAR(4),
    is_active BOOLEAN,
    country INT,
    updated TIMESTAMP NOT NULL,
    synchronized TIMESTAMP
);

CREATE TABLE bills (
    id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    number VARCHAR(20),
    billing_date TIMESTAMP,
    purge_date TIMESTAMP,
    customer_id BIGINT,
    invoicing_id BIGINT,
    customer_name VARCHAR(80),
    period_from TIMESTAMP,
    period_to TIMESTAMP,
    vat INT     ,
    hash_code VARCHAR(50),
    synchronized TIMESTAMP
);

CREATE TABLE bill_items (
    bill_id BIGINT,
    text VARCHAR(100),
    amount FLOAT, 
    price INT,
    is_display_unit BOOLEAN,
    dph BOOLEAN
);

CREATE TABLE invoicings (
  id BIGINT NOT NULL,
  name varchar(80) NOT NULL,
  country BIGINT,
  numberingbase varchar(15),
  invoicing_date timestamp
);

CREATE TABLE settings
(
  id BIGINT NOT NULL,
  name VARCHAR(50) NOT NULL,
  value VARCHAR(250)
);
