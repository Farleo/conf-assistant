Команди для створення користувача і бази даних. Далі для заповнення бази даних використати файл Data for DB.sql
------------------
CREATE DATABASE confassist CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE USER 'confassist'@'localhost' IDENTIFIED BY 'confassist';
GRANT ALL PRIVILEGES ON * . * TO 'confassist'@'localhost';
FLUSH PRIVILEGES;