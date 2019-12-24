DROP TABLE IF EXISTS recharge;
DROP TABLE IF EXISTS payment;
DROP TABLE IF EXISTS wallet;

CREATE TABLE wallet(
    wallet_id INT AUTO_INCREMENT PRIMARY KEY,
    balance FLOAT(6,2),
    creation_date TIMESTAMP,
    update_date TIMESTAMP
);

CREATE TABLE recharge(
    recharge_id INT AUTO_INCREMENT PRIMARY KEY,
    wallet_id INT NOT NULL,
    amount FLOAT(6,2) NOT NULL,
    creation_date TIMESTAMP,
    update_date TIMESTAMP,
    FOREIGN KEY wallet_id REFERENCES wallet(wallet_id)
);

CREATE TABLE payment(
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    wallet_id INT NOT NULL,
    amount FLOAT(6,2) NOT NULL,
    creation_date TIMESTAMP,
    update_date TIMESTAMP,
    FOREIGN KEY wallet_id REFERENCES wallet(wallet_id)
);

DROP SEQUENCE IF EXISTS wallet_seq;
DROP SEQUENCE IF EXISTS payment_seq;
DROP SEQUENCE IF EXISTS recharge_seq;

CREATE SEQUENCE wallet_seq;
CREATE SEQUENCE payment_seq;
CREATE SEQUENCE recharge_seq;