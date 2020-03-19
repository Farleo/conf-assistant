insert into user(first_name, last_name, password, email) values
('Ivan', 'Smith', '123qweasd', 'smith@gmail.com'),
('John', 'Simpson', 'dsaewq321', 'simpson@gmail.com'),
('Fray', 'Bullok', '1qasw23ed', 'fray@gmail.com');

insert into conference(name, alias, begin_date, finish_date, info, cover_photo, venue) values
('5G in 2020', '5G20', '2020-02-09', '2020-02-13', 'The 2020 edition of The European 5G Conference will take place in Brussels, Belgium on 29 & 30 January. Now in its 4th year, the European 5G Conference has an established reputation as Brussels’ leading meeting place for discussion on 5G policy.
5G is here. All around the world, commercial deployment has begun. As we move from planning and preparation to deployment and launch, this year’s conference will look at early experiences that are being seen, and the challenges and opportunities that are ahead. How can Member States and Industry work together with the new Commission to deliver on the 5G vision?', '/picture/img/conf/1.jpg', 'Hungary, Budapesh st. ST. George 15'),

('IT in 2020', 'IT20', '2020-02-04', '2020-02-20', 'This year the event will be fully devoted to the preparation of the new generation of cross-border cooperation programmes under the NDICI Instrument. Most important, it will represent the first forum for common exchange on the draft “Joint paper on NDICI Interreg Strategic Programming 2021 – 2027”.', '/picture/img/conf/2.jpg', 'Ukraine, Lviv st. Zelena 185'),

('Busines Psychology', 'BP', '2020-05-04', '2020-05-25', 'Society of Consulting Psychology members are contributing to the definition of consulting psychology and the methods used by consultants. Former CE Chair DeWayne Kurpius explained that consultation helps individuals and organizations “become more efficient and effective” (1978). Consultants develop a climate for interdependent problem-solving, or they share their expertise in solving a specific problem.
Later, Edgar Schein (1989) elaborated the process and systemic approach: “As the relationship between the consultant and organization evolves, the concept of who is the client comes gradually to be broadened so that the consultant may be working with individuals, groups, and organizational units at different times.”',
 '/picture/img/conf/Screenshot-2019-11-19-at-13.41.30.jpg', 'France, Paris st. St. Loiu83');

insert into stream(name, conference_id) VALUES
('Conference Hall №1', 1),
('Conference Hall №2', 2),
('Conference Hall №3', 3);

insert into user(first_name, last_name, password, info, photo, email) VALUES
('Carla', 'Walton', '147852369', 'Again, we configured the relationship before. Hence, we only need to tell JPA, where can it find that configuration. Note, that we could use this solution to address the previous problem: students rating courses. However, it feels weird to create a dedicated primary key unless we have to. Moreover, from an RDBMS perspective, in the relationship', '/picture/img/user/1.jpg', 'walton@gmail.com'),
('Radgesh', 'Kutrapali', 'qweasd321', 'In this case, there''re multiple connections between the same student-course pairs, or multiple rows, with the same student_id-course_id pairs. We can''t model it using any of the previous solutions because all primary keys must be unique. Therefore, we need to use a separate primary key.', '/picture/img/user/2.jpg', 'radgesh@gmail.com'),
('Hovard', 'Volovits', '][pl;', 'Moreover, this solution has an additional feature we didn''t mention yet. The simple many-to-many solution creates a relationship between two entities. Therefore, we cannot expand the relationship to more entities. However, in this solution we don''t have this limit: we can model relationships between any number of entity types.', '/picture/img/user/3.jpg', 'hovard@gmail.com');

insert into topic(name, date, begin_time, finish_time, info, is_active, stream_id, speaker_id, cover_photo) VALUES
('New in Spring', '2020-02-09', '17:00:00', '19:00:00', 'Why were we able to do this? If we inspect the tables closely in the previous case, we can see, that it contained two many-to-one relationships. In other words, there isn''t any many-to-many relationship in an RDBMS. We call the structures we create with join tables many-to-many relationships because that''s what we model.
Besides, it''s more clear if we talk about many-to-many relationships, because that''s our intention. Meanwhile, a join table is just an implementation detail; we don''t really care about it.', 1, 2, 4, '/picture/img/topic/2.jpg'),

('New in Gradel', '2020-02-09', '20:00:00', '21:00:00', 'Let''s say we want to let students register to courses. Also, we need to store the point when a student registered for a specific course. On top of that, we also want to store what grade she received in the course.
In an ideal world, we could solve this with the previous solution, when we had an entity with a composite key. However, our world is far from ideal and students don''t always accomplish a course on the first try.', 1, 2, 5, '/picture/img/topic/5.jpg'),

('Problem and solutions in busines psychology', '2020-02-11', '17:00:00', '19:00:00', 'The implementation of a simple many-to-many relationship was rather straightforward. The only problem is that we cannot add a property to a relationship that way, because we connected the entities directly. Therefore, we had no way to add a property to the relationship itself.
Of course, every JPA entity needs a primary key. Because our primary key is a composite key, we have to create a new class, which will hold the different parts of the key:', 1, 3, 5, '/picture/img/topic/2283_PowerPoint_Design_Service_Slides_Slide_1_Presentation_Title.jpg'),

('Future of mobile internet', '2020-02-12', '17:00:00', '19:00:00', 'Again, we configured the relationship before. Hence, we only need to tell JPA, where can it find that configuration.
Note, that we could use this solution to address the previous problem: students rating courses. However, it feels weird to create a dedicated primary key unless we have to. ', 1, 1, 6, '/picture/img/topic/FF0234-01-multi-purpose-powerpoint-template-1.jpg'),

('5G in Europe', '2020-02-13', '17:00:00', '19:00:00', 'A relationship is a connection between two types of entities. In case of a many-to-many relationship, both sides can relate to multiple instances of the other side.
Note, that it''s possible for entity types to be in a relationship with themselves.', 1, 1, 6, '/picture/img/topic/maxresdefault.jpg');


insert into user(email, password) values
('1email@gmail.com', ''), ('2email@gmail.com', ''), ('3email@gmail.com', ''), ('4email@gmail.com', ''), ('5email@gmail.com', ''), ('6email@gmail.com', ''),
('7email@gmail.com', ''), ('8email@gmail.com', ''), ('9email@gmail.com', ''), ('10email@gmail.com', ''), ('11email@gmail.com', ''), ('12email@gmail.com', ''),
('13email@gmail.com', ''), ('14email@gmail.com', ''), ('15email@gmail.com', ''), ('16email@gmail.com', ''), ('17email@gmail.com', ''), ('18email@gmail.com', ''),
('19email@gmail.com', ''), ('20email@gmail.com', ''), ('21email@gmail.com', ''), ('22email@gmail.com', ''), ('23email@gmail.com', ''), ('24email@gmail.com', ''),
('25email@gmail.com', ''), ('26email@gmail.com', ''), ('27email@gmail.com', ''), ('28email@gmail.com', ''), ('29email@gmail.com', ''), ('30email@gmail.com', '');

insert into question(question, topic_id, user_id) VALUES
('You will build an application that store?', 1, 1), (' POJOs (Plain Old Java Objects) in a memory-based?', 1, 1),
('Like most Spring Getting Started guides?', 1, 2), (' Create an Application Class?', 2, 2),
('When you finish, you can check?', 1, 3), (' import org.springframewo?', 1, 3),
('your results against the code in?', 1, 4), (' POJOoot to start adding beans based o-based?', 1, 4),
('ll Spring applications, you should start ?', 1, 5), (' POJOis annotation flags the applicaased?', 1, 5),
('The Initializr offers a fast way to?', 1, 6), (' he main() method uses Spring Boot’s SpringAppli?', 1, 6),
('pull in all the dependencies you need for?', 1, 7), (' Pneed to modify the simple class that thesed?', 1, 7),
('and does a lot of the set up for you?', 1, 8), (' POpository.save(new Customer("Chloe", "O''Brian"));?', 1, 8),
('The following listing shows the pom.xml file create?', 2, 9), (' Application includes a demo() method that puts the?', 1, 9),
('In this example, you store?', 2, 10), (' Then it saves a handful of Customer objects, demonstrating the?', 2, 10),
('Here you have a Customer class with?', 2, 11), (' Build an executable JAR?', 2, 11),
('Yindicating that it is a JPA entity?', 2, 12), (' e JAR file that contains all the necessary?', 2, 12),
('re left unannotated. It is assumed that?', 2, 13), (' Pt lifecycle, across different environments, and?', 2, 13),
('The convenient toString() method print outs?', 2, 14), (' build the JAR file by using ./gradlew build and then?', 2, 14),
('Create Simple Queries?', 2, 15), (' If you use Maven, you can run the application by using?', 2, 15),
('To see how this works, create a repository interfac?', 2, 16), (' When you run your application, you should see output similar to the followi?', 3, 16),
('You will build an application that stor?', 2, 17), (' PCongratulations! You have written a simple applicad?', 3, 17),
('that works with Customer entities as the?', 4, 18), (' and fetch them from a database?', 3, 18),
(' focuses on using JPA to stor?', 4, 19), (' If you want to expose JPA repositories with a hypermedia?', 3, 19),
('feature is the ability to create repository implementations?', 4, 20), (' Want to write a new guide or contribute to?', 3, 20),
('operty is annotated with ?', 4, 21), (' All guides are released with an ASLv2 license for the c?', 3, 21),
('g JPA to store data in a relational da?', 4, 22), ('Spring Runtime offers support and?', 3, 22),
('create repository implementations a?', 4, 23), ('  for (Customer bauer : repository.findByLastName("Bauer")?', 4, 23),
('CustomerRepository extends the CrudRepository?', 5, 24), (' uide walks you through the process of bu?', 4, 24),
('By extending CrudRepository, CustomerRepository inherits several methods for working with?', 5, 25), (' Papplication that stores Customer POJOs (Plain Old Ja?', 4, 25),
('Spring Data JPA also lets you define other query methods?', 5, 26), (' bjects) in a memory-based database?', 4, 26),
('ypical Java application, you m?', 5, 27), (' About 15 minutes?', 5, 27),
('r, that is what makes Spring Data JPA so pow?', 5, 28), (' ke most Spring Getting Sta?', 5, 28),
('You need not write an implementation of the repository interface. Spring Data JPA creates an implementation?', 5, 29), (' o start from scratch, move on to Starting withry-based?', 5, 29),
('ow you can wire up this example and see what it l?', 5, 30), (' ic setup steps that ar?', 5, 30);

insert into roles (role) values
('Admin'),
('Moderator'),
('Speaker'),
('Guest');

insert into user_roles(roles_id, user_id) values
(1, 1),
(2, 2),
(2, 3),
(3, 4),
(3, 5),
(3, 6),
(4, 7), (4, 8), (4, 9), (4, 10), (4, 11), (4, 12), (4, 13), (4, 14),
(4, 15), (4, 16), (4, 17), (4, 18), (4, 19), (4, 20), (4, 21), (4, 22),
(4, 23), (4, 24), (4, 25), (4, 26), (4, 27), (4, 28), (4, 29), (4, 30),
(4, 31), (4, 32), (4, 33), (4, 34), (4, 35), (4, 36);

INSERT INTO participant_type (type_id, name)
VALUES (1, 'visitor');
INSERT INTO participant_type (type_id, name)
VALUES (2, 'speaker');
INSERT INTO participant_type (type_id, name)
VALUES (3, 'moder');
INSERT INTO participant_type (type_id, name)
VALUES (4, 'admin');

INSERT INTO participants (user_id, conference_id, type_id) VALUES
(1, 1, 4), (1, 2, 4), (1, 3, 4),
(2, 1, 3), (3, 2, 3), (3, 3, 3),
(4, 1, 2), (5, 2, 2), (6, 3, 2),
(7, 1, 1), (8, 1, 1), (9, 1, 1), (10, 1, 1), (11, 1, 1), (12, 1, 1), (13, 1, 1), (14, 1, 1), (15, 1, 1), (16, 1, 1),
(17, 2, 1), (18, 2, 1), (19, 2, 1), (20, 2, 1), (21, 2, 1), (22, 2, 1), (23, 2, 1), (24, 2, 1), (25, 2, 1), (26, 2, 1),
(27, 3, 1), (28, 3, 1), (29, 3, 1), (30, 3, 1), (31, 3, 1), (32, 3, 1), (33, 3, 1), (34, 3, 1), (35, 3, 1), (36, 3, 1);

