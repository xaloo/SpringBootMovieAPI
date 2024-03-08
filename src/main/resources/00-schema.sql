
--Table employee
CREATE TABLE IF NOT EXISTS employee (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    age INT NOT NULL
);

--Table content
CREATE TABLE IF NOT EXISTS content (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL UNIQUE,
    year INT NOT NULL,
    director VARCHAR(255) NOT NULL,
    genre VARCHAR(50) NOT NULL,
    length_minutes INT,
    seasons INT,
    register_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    rating FLOAT NOT NULL,
    id_proposed_by_employee INT REFERENCES employee(id),
    id_registered_by_employee INT REFERENCES employee(id)
);
