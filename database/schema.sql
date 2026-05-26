-- database/schema.sql
CREATE DATABASE IF NOT EXISTS alpha_estimation;
USE alpha_estimation;

-- Users table
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Projects table
CREATE TABLE projects (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'PLANNING',
    total_cost_estimate DECIMAL(15,2) DEFAULT 0,
    total_hours_estimate DECIMAL(10,2) DEFAULT 0,
    created_by_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by_id) REFERENCES users(id)
);

-- Tasks table
CREATE TABLE tasks (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    estimated_hours DECIMAL(8,2) NOT NULL,
    actual_hours DECIMAL(8,2),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    priority VARCHAR(20),
    status VARCHAR(20) DEFAULT 'TODO',
    project_id BIGINT,
    assigned_to_id BIGINT,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
    FOREIGN KEY (assigned_to_id) REFERENCES users(id)
);

-- Resources table
CREATE TABLE resources (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    hourly_rate DECIMAL(10,2) NOT NULL,
    allocated_hours DECIMAL(10,2) NOT NULL,
    type VARCHAR(50),
    project_id BIGINT,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);

-- Insert test data
INSERT INTO users (username, password, email, role) VALUES
('admin', '$2a$10$NkMwpUoGqL8YiBqWUqZJqOQKqL8YiBqWUqZJqOQKqL8YiBqWUqZJq', 'admin@alpha.com', 'ADMIN');

INSERT INTO projects (name, description, start_date, end_date, status, created_by_id) VALUES
('E-commerce Platform', 'Build a full e-commerce solution', CURDATE(), DATE_ADD(CUR