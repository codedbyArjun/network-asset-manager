CREATE DATABASE IF NOT EXISTS network_asset_manager CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE network_asset_manager;

-- Login accounts (passwords are stored as salted PBKDF2 hashes, never as plain text)
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(60) NOT NULL,
    username VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Devices on the network
CREATE TABLE IF NOT EXISTS hosts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ip_address VARCHAR(50) NOT NULL,
    hostname VARCHAR(100),
    operating_system VARCHAR(100),
    status VARCHAR(20) DEFAULT 'Active',
    risk_level VARCHAR(20) DEFAULT 'Low',
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Open ports / services found on each host (deleted automatically with the host)
CREATE TABLE IF NOT EXISTS ports (
    id INT AUTO_INCREMENT PRIMARY KEY,
    host_id INT NOT NULL,
    port_number INT NOT NULL,
    service_name VARCHAR(100),
    vulnerability_note TEXT,
    FOREIGN KEY (host_id) REFERENCES hosts(id) ON DELETE CASCADE
);
