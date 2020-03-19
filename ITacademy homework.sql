select * from employee e join employee m on e.chief_id=m.id where e.salary > m.salary;

select * from employee where (department_id, salary) in (select department_id, MAX(salary) from employee group by department_id);
select * from employee em where em.salary in (select MAX(es.salary) from employee es where es.department_id = em.department_id);

select department_id, COUNT(department_id)  from employee group by department_id having COUNT(department_id) <= 3;
select department_id, COUNT(department_id) count from employee group by department_id having count <= 3;

select m.department_id, e.* from employee e join employee m on e.chief_id=m.id where e.department_id != m.department_id;

select department_id, sum(salary) sm from employee group by department_id order by sm desc limit 1;



insert into itacademy.employee(name, salary, department_id, chief_id)
values ('ivan',  10000, 1, 1),

       ('vasia',  8000, 2, 1),
       ('maria',  8000, 2, 1),
       ('vasia',  7000, 2, 1),
       ('galia',  7500, 2, 1),

       ('bogdan', 8000, 3, 2),
       ('slavik', 9000, 3, 2),

       ('dmytro', 9000, 4, 3),
       ('oleh',   8500, 4, 3),
       ('nazar', 15000, 5, 4),
       ('sasha', 10000, 5, 4);

insert into itacademy.department(id, name) values
(1, 'accounting'),
(2, 'management'),
(3, 'Human Resources Department'),
(4, 'workers'),
(5, 'delivery');


truncate itacademy.employee
