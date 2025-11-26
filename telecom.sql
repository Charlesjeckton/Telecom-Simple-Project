DROP DATABASE IF EXISTS telecom;
CREATE DATABASE telecom;
USE telecom;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";

-- ================================
-- USERS TABLE
-- ================================
DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(100) NOT NULL,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(50) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (username)
) ENGINE=InnoDB;

-- Insert 3 sample users
INSERT INTO users (username, password, role) VALUES
('admin_user', 'admin123', 'ADMIN'),
('mary_kimani', 'pass123', 'CUSTOMER'),
('john_doe', 'pass123', 'CUSTOMER');


-- ================================
-- CUSTOMERS TABLE
-- ================================
DROP TABLE IF EXISTS customers;
CREATE TABLE customers (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  phone_number VARCHAR(20) NOT NULL,
  email VARCHAR(100) NOT NULL,
  registration_date DATE NOT NULL,
  user_id INT NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (email),
  UNIQUE (user_id),
  FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB;

-- Insert 3 sample customers
INSERT INTO customers (name, phone_number, email, registration_date, user_id) VALUES
('Mary Kimani', '0798765432', 'mary@example.com', '2025-01-10', 2),
('Charles Jeckton', '0748064530', 'charles@example.com', '2025-02-05', 3),
('James Kariuki', '0712345678', 'james@example.com', '2025-03-15', 1);


-- ================================
-- SERVICES TABLE
-- ================================
DROP TABLE IF EXISTS services;
CREATE TABLE services (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100),
  description TEXT,
  monthly_fee DECIMAL(10,2),
  active TINYINT(1) DEFAULT 1,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Insert 3 sample services
INSERT INTO services (name, description, monthly_fee, active) VALUES
('Madaraka Life', 'Live free for 50 bob!', 50.00, 1),
('Freedom Friday 50GB', 'Friday bundle: 50GB', 1000.00, 1),
('Voice & SMS Plan', 'Talk & text bundle', 500.00, 1);


-- ================================
-- SUBSCRIPTIONS TABLE
-- ================================
DROP TABLE IF EXISTS subscriptions;
CREATE TABLE subscriptions (
  id INT NOT NULL AUTO_INCREMENT,
  customer_id INT NOT NULL,
  service_id INT NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE DEFAULT NULL,
  status VARCHAR(20) DEFAULT 'ACTIVE',
  PRIMARY KEY (id),
  FOREIGN KEY (customer_id) REFERENCES customers(id),
  FOREIGN KEY (service_id) REFERENCES services(id)
) ENGINE=InnoDB;

-- Insert 3 sample subscriptions
INSERT INTO subscriptions (customer_id, service_id, start_date, end_date, status) VALUES
(1, 1, '2025-01-01', '2025-12-31', 'ACTIVE'),
(2, 2, '2025-02-15', NULL, 'ACTIVE'),
(3, 3, '2025-03-20', '2025-06-20', 'EXPIRED');


-- ================================
-- BILLING TABLE
-- ================================
DROP TABLE IF EXISTS billing;
CREATE TABLE billing (
  id INT NOT NULL AUTO_INCREMENT,
  customer_id INT NOT NULL,
  service_id INT NOT NULL,
  amount DOUBLE DEFAULT NULL,
  billing_date DATE DEFAULT NULL,
  paid TINYINT(1) DEFAULT 0,
  PRIMARY KEY (id),
  FOREIGN KEY (customer_id) REFERENCES customers(id),
  FOREIGN KEY (service_id) REFERENCES services(id)
) ENGINE=InnoDB;

-- Insert 3 sample billing records
INSERT INTO billing (customer_id, service_id, amount, billing_date, paid) VALUES
(1, 1, 1000, '2025-10-01', 1),
(2, 2, 1250, '2025-10-15', 0),
(3, 3, 500, '2025-11-01', 1);


COMMIT;
