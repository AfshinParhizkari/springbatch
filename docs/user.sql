-- ********************* mysql db config **********************************************
-- user:
-- '<user>'@'<host:The host that the user is connecting from>'
-- User@'%' would allow access from all locations except localhost.(TCP/IP socket)
-- User@'localhost' would only allow access from localhost.UNIX socket (or windows pipes )
DROP user 'admin';
create user 'admin'@'%' IDENTIFIED WITH mysql_native_password by '123456'
create user 'admin'@'localhost' IDENTIFIED WITH mysql_native_password by '123456'
ALTER USER 'admin'@'%' IDENTIFIED WITH mysql_native_password BY '123456';
select host, user from mysql.user;
-- PRIVILEGES:
flush PRIVILEGES;
REVOKE ALL ON *.* FROM 'admin'@'localhost';
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'%';
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'localhost' with grant option;
GRANT USAGE ON *.* TO `admin`@`%`;
SHOW GRANTS FOR 'admin'@'%';
