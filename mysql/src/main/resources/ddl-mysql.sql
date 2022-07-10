Drop table bill.bill_statement;
CREATE TABLE IF NOT EXISTS bill.bill_statement
(
    bill_id int not null auto_increment,
    first_name varchar(50),
    last_name varchar(50),
    minutes int,
    data_usage int,
    bill_amount decimal(10,2),
    create_date Date,
    primary key (bill_id)
)ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_persian_ci;

SELECT * FROM bill.bill_statement;