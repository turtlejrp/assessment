-- Drop tables if they exist
DROP TABLE IF EXISTS lottery CASCADE;
DROP TABLE IF EXISTS user_ticket CASCADE;

CREATE TABLE lottery (
                         ticket VARCHAR(6) UNIQUE PRIMARY KEY,
                         price INTEGER NOT NULL,
                         amount INTEGER NOT NULL
);

CREATE TABLE user_ticket (
                             id SERIAL PRIMARY KEY,
                             ticket_id VARCHAR(6) REFERENCES lottery(ticket) ON DELETE CASCADE NOT NULL,
                             user_id VARCHAR(10) NOT NULL
);

-- Initial data
INSERT INTO lottery(ticket, price, amount) VALUES('000000', 80,1);
INSERT INTO lottery(ticket, price, amount) VALUES('000001', 80,1);
INSERT INTO lottery(ticket, price, amount) VALUES('123456', 100,1);
INSERT INTO user_ticket(ticket_id, user_id) VALUES('000000','0123456789');

