CREATE TABLE customers (
   id SERIAL PRIMARY KEY,
   first_name VARCHAR(20) NOT NULL,
   last_name VARCHAR(50) NOT NULL,
   created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);