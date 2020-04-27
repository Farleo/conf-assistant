# **_Conference Assistant Project_**

Дані для тестування проекту:

=======admin=========
Має всі права і може будь-що
smith@gmail.com
123qweasd

=====================

======ConfOwner======

Має права на створення/редагування/видалення власних конференцій

simpson@gmail.com
123qweasd

fray@gmail.com
123qweasd

=====================

=====ConfAdmin======

Має права на редагування конференцій, де він є адміном

j.morris@gmail.com
123qweasd

j.nash@gmail.com
123qweasd

c.barker@gmail.com
123qweasd

===================

=====ConfModer=====

Має права на рівні потоку (stream), де він є модератором

alex@gmail.com
123qweasd

hrechko@gmail.com
123qweasd

maruc@gmail.com
123qweasd

==================



Команди для створення користувача і бази даних. Далі для заповнення бази даних використати файл Data for DB.sql
------------------
CREATE DATABASE confassist CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE USER 'confassist'@'localhost' IDENTIFIED BY 'confassist';
GRANT ALL PRIVILEGES ON * . * TO 'confassist'@'localhost';
FLUSH PRIVILEGES;