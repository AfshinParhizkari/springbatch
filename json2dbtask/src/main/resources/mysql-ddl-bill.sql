CREATE TABLE test_general.bill_statement (
   bill_id int auto_increment NOT NULL,
   first_name varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_persian_ci NULL,
   last_name varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_persian_ci NULL,
   minutes int NULL,
   data_usage int NULL,
   bill_amount decimal(10,2) NULL,
   create_date date NULL,
   CONSTRAINT `PRIMARY` PRIMARY KEY (bill_id)
)
    ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_persian_ci
COMMENT='';

SELECT * FROM test_general.bill_statement;