DROP DATABASE IF EXISTS CSgb1w21;
CREATE DATABASE CSgb1w21;
USE CSgb1w21;
DROP USER IF EXISTS CSgb1w21@localhost;

CREATE USER CSgb1w21@'localhost' IDENTIFIED WITH mysql_native_password BY 'odeckoxb' REQUIRE NONE;
CREATE USER CSgb1w21@'%' IDENTIFIED WITH mysql_native_password BY 'odeckoxb' REQUIRE NONE;

GRANT ALL ON CSgb1w21.* TO CSgb1w21@'localhost';

GRANT ALL ON CSgb1w21.* TO CSgb1w21@'%';