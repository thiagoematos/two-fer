CREATE TABLE IF NOT EXISTS "two_fer" (
    id UUID PRIMARY KEY,
    name varchar(50) UNIQUE NOT NULL,
    message varchar(50) NOT NULL
);