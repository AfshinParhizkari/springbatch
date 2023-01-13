DROP TABLE IF EXISTS BILL_STATEMENTS;
CREATE TABLE IF NOT EXISTS BILL_STATEMENTS
(
    bill_id INT NOT NULL IDENTITY PRIMARY KEY,
    first_name varchar(50),
    last_name varchar(50),
    minutes int,
    data_usage int,
    bill_amount decimal(10,2),
    create_date Date
    );