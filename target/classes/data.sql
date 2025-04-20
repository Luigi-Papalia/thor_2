CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    email VARCHAR(50),
    password VARCHAR(50)
);

INSERT INTO users (name, email, password) VALUES ('Alice', 'alice@example.com', 'password1');
INSERT INTO users (name, email, password) VALUES ('Bob', 'bob@example.com', 'password2');
INSERT INTO users (name, email, password) VALUES ('Charlie', 'charlie@example.com', 'password3');