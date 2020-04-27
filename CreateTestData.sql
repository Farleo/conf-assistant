insert into roles (role) values
('ROLE_ADMIN'),
('ROLE_CONFOWNER'),
('ROLE_USER');

INSERT INTO participant_type (type_id, name) VALUES
(1, 'visitor'),
(2, 'speaker'),
(3, 'moder'),
(4, 'admin');

# Insert `admin` users
insert into user (user_id,  first_name, last_name, password, email, is_active, is_deleted) values
 (1, 'Ivan', 'Smith', '$2a$10$jbI6s.FAIGRzurrzf9p4Ief/QfWAhzrcMIh.VHbwN111ykpI1O1T.', 'smith@gmail.com', 1, 0);
insert into user_roles(roles_id, user_id) values
 (1, 1);

# Insert `confOwner` users
insert into user (user_id,  first_name, last_name, password, email, is_active,is_deleted) values
(10, 'John', 'Simpson', '$2a$10$jbI6s.FAIGRzurrzf9p4Ief/QfWAhzrcMIh.VHbwN111ykpI1O1T.', 'simpson@gmail.com', 1, 0),
(11, 'Fray', 'Bullok', '$2a$10$jbI6s.FAIGRzurrzf9p4Ief/QfWAhzrcMIh.VHbwN111ykpI1O1T.', 'fray@gmail.com', 1, 0);
insert into user_roles(roles_id, user_id) values
(2, 10), (2, 11);

# Insert conferences
insert into conference(name, alias, begin_date, finish_date, info, cover_photo, venue, owner_id,is_deleted) values
('5G in 2020', '5G20', '2020-02-09', '2020-02-13', 'The 2020 edition of The European 5G Conference will take place in Brussels, Belgium on 29 & 30 January. Now in its 4th year, the European 5G Conference has an established reputation as Brussels’ leading meeting place for discussion on 5G policy.
5G is here. All around the world, commercial deployment has begun. As we move from planning and preparation to deployment and launch, this year’s conference will look at early experiences that are being seen, and the challenges and opportunities that are ahead. How can Member States and Industry work together with the new Commission to deliver on the 5G vision?', '/picture/img/conf/1.jpg', 'Hungary, Budapesh st. ST. George 15', 10,0),

('IT in 2020', 'IT20', '2020-02-04', '2020-02-20', 'This year the event will be fully devoted to the preparation of the new generation of cross-border cooperation programmes under the NDICI Instrument. Most important, it will represent the first forum for common exchange on the draft “Joint paper on NDICI Interreg Strategic Programming 2021 – 2027”.', '/picture/img/conf/2.jpg', 'Ukraine, Lviv st. Zelena 185', 10,0),

('Busines Psychology', 'BP', '2020-05-04', '2020-05-25', 'Society of Consulting Psychology members are contributing to the definition of consulting psychology and the methods used by consultants. Former CE Chair DeWayne Kurpius explained that consultation helps individuals and organizations “become more efficient and effective” (1978). Consultants develop a climate for interdependent problem-solving, or they share their expertise in solving a specific problem.
Later, Edgar Schein (1989) elaborated the process and systemic approach: “As the relationship between the consultant and organization evolves, the concept of who is the client comes gradually to be broadened so that the consultant may be working with individuals, groups, and organizational units at different times.”',
 '/picture/img/conf/Screenshot-2019-11-19-at-13.41.30.jpg', 'France, Paris st. St. Loiu83', 11,0);

# Insert `conf admin` users
insert into user(user_id,  first_name, last_name, password, email, is_active, is_deleted) values
(20, 'Joe', 'Morris', '$2a$10$jbI6s.FAIGRzurrzf9p4Ief/QfWAhzrcMIh.VHbwN111ykpI1O1T.', 'j.morris@gmail.com', 1, 0),
(21, 'James', 'Nash', '$2a$10$jbI6s.FAIGRzurrzf9p4Ief/QfWAhzrcMIh.VHbwN111ykpI1O1T.', 'j.nash@gmail.com', 1, 0),
(22, 'Colin', 'Barker', '$2a$10$jbI6s.FAIGRzurrzf9p4Ief/QfWAhzrcMIh.VHbwN111ykpI1O1T.', 'c.barker@gmail.com', 1, 0);

insert into user_roles(roles_id, user_id) values
(3, 20), (3, 21), (3, 22);

INSERT INTO participants (user_id, conference_id, type_id) VALUES
(20, 1, 4), (21, 2, 4), (22, 3, 4); 

# Insert `conf moder` users
insert into user(user_id,  first_name, last_name, password, email, is_active, is_deleted) values
(30, 'Alex', 'Fox', '$2a$10$jbI6s.FAIGRzurrzf9p4Ief/QfWAhzrcMIh.VHbwN111ykpI1O1T.', 'alex@gmail.com', 1, 0),
(31, 'Olena', 'Hrechko', '$2a$10$jbI6s.FAIGRzurrzf9p4Ief/QfWAhzrcMIh.VHbwN111ykpI1O1T.', 'hrechko@gmail.com', 1, 0),
(32, 'Maruv', 'Pavl', '$2a$10$jbI6s.FAIGRzurrzf9p4Ief/QfWAhzrcMIh.VHbwN111ykpI1O1T.', 'maruc@gmail.com', 1, 0);

insert into user_roles(roles_id, user_id) values
(3, 30), (3, 31), (3, 32);

INSERT INTO participants (user_id, conference_id, type_id) VALUES
(30, 1, 3), (31, 2, 3), (32, 3, 3); 

insert into stream(name, conference_id, moderator_id) VALUES
('Conference Hall №1', 1, 30),
('Conference Hall №2', 2, 31),
('Conference Hall №3', 3, 32);

# Insert `conf speaker` users
insert into user(user_id,  first_name, last_name, password, info, photo, email, is_active, is_deleted) VALUES
(40, 'Carla', 'Walton', '$2a$10$jbI6s.FAIGRzurrzf9p4Ief/QfWAhzrcMIh.VHbwN111ykpI1O1T.', 'Again, we configured the relationship before. Hence, we only need to tell JPA, where can it find that configuration. Note, that we could use this solution to address the previous problem: students rating courses. However, it feels weird to create a dedicated primary key unless we have to. Moreover, from an RDBMS perspective, in the relationship', '/picture/img/user/1.jpg', 'walton@gmail.com', 1, 0),
(41, 'Radgesh', 'Kutrapali', '$2a$10$jbI6s.FAIGRzurrzf9p4Ief/QfWAhzrcMIh.VHbwN111ykpI1O1T.', 'In this case, there''re multiple connections between the same student-course pairs, or multiple rows, with the same student_id-course_id pairs. We can''t model it using any of the previous solutions because all primary keys must be unique. Therefore, we need to use a separate primary key.', '/picture/img/user/2.jpg', 'radgesh@gmail.com', 1, 0),
(42, 'Hovard', 'Volovits', '$2a$10$jbI6s.FAIGRzurrzf9p4Ief/QfWAhzrcMIh.VHbwN111ykpI1O1T.', 'Moreover, this solution has an additional feature we didn''t mention yet. The simple many-to-many solution creates a relationship between two entities. Therefore, we cannot expand the relationship to more entities. However, in this solution we don''t have this limit: we can model relationships between any number of entity types.', '/picture/img/user/3.jpg', 'hovard@gmail.com', 1, 0);

insert into user_roles(roles_id, user_id) values
(3, 40), (3, 41), (3, 42);

INSERT INTO participants (user_id, conference_id, type_id) VALUES
(40, 1, 2), (41, 2, 2), (42, 3, 2);

insert into topic(name, date, begin_time, finish_time, info, is_allowed_question, stream_id, speaker_id, cover_photo) VALUES
# Conf 1 Stream 1
('New in Spring', '2020-02-09', '17:00:00', '19:00:00', 'Why were we able to do this? If we inspect the tables closely in the previous case, we can see, that it contained two many-to-one relationships. In other words, there isn''t any many-to-many relationship in an RDBMS. We call the structures we create with join tables many-to-many relationships because that''s what we model.
Besides, it''s more clear if we talk about many-to-many relationships, because that''s our intention. Meanwhile, a join table is just an implementation detail; we don''t really care about it.', 1, 1, 40, '/picture/img/topic/2.jpg'),

# Conf 2 Stream 2
('New in Gradel', '2020-02-09', '20:00:00', '21:00:00', 'Let''s say we want to let students register to courses. Also, we need to store the point when a student registered for a specific course. On top of that, we also want to store what grade she received in the course.
In an ideal world, we could solve this with the previous solution, when we had an entity with a composite key. However, our world is far from ideal and students don''t always accomplish a course on the first try.', 1, 2, 41, '/picture/img/topic/5.jpg'),

('Problem and solutions in busines psychology', '2020-02-11', '17:00:00', '19:00:00', 'The implementation of a simple many-to-many relationship was rather straightforward. The only problem is that we cannot add a property to a relationship that way, because we connected the entities directly. Therefore, we had no way to add a property to the relationship itself.
Of course, every JPA entity needs a primary key. Because our primary key is a composite key, we have to create a new class, which will hold the different parts of the key:', 1, 2, 41, '/picture/img/topic/2283_PowerPoint_Design_Service_Slides_Slide_1_Presentation_Title.jpg'),

# Conf 3 Stream 3
('Future of mobile internet', '2020-02-12', '17:00:00', '19:00:00', 'Again, we configured the relationship before. Hence, we only need to tell JPA, where can it find that configuration.
Note, that we could use this solution to address the previous problem: students rating courses. However, it feels weird to create a dedicated primary key unless we have to. ', 1, 3, 42, '/picture/img/topic/FF0234-01-multi-purpose-powerpoint-template-1.jpg'),

('5G in Europe', '2020-02-13', '17:00:00', '19:00:00', 'A relationship is a connection between two types of entities. In case of a many-to-many relationship, both sides can relate to multiple instances of the other side.
Note, that it''s possible for entity types to be in a relationship with themselves.', 1, 3, 42, '/picture/img/topic/maxresdefault.jpg');

# Insert `conf visitor` users
insert into user(user_id, email, password, is_active, created, is_deleted) values
(50, '1email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0), (51, '2email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0), (52, '3email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0), (53, '4email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0), (54, '5email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0), (55, '6email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0),
(56, '7email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0), (57, '8email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0), (58, '9email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0), (59, '10email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0), (60, '11email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0), (61, '12email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0),
(62, '13email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0), (63, '14email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0), (64, '15email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0), (65, '16email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0), (66, '17email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0), (67, '18email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0),
(68, '19email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0), (69, '20email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0), (70, '21email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0), (71, '22email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0), (72, '23email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0), (73, '24email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0),
(74, '25email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0), (75, '26email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0), (76, '27email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0), (77, '28email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0), (78, '29email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0), (79, '30email@gmail.com', '$2a$10$pWd6DyHtanJpO4H/KkDXcu1LuqLGdB0W9VR.B2rfBbo0F32tUC0D.', 1, '2020-05-04', 0);

insert into user_roles(roles_id, user_id) values
(3, 50), (3, 51), (3, 52), (3, 53), (3, 54), (3, 55), (3, 56), (3, 57), (3, 58), (3, 59), (3, 60),
(3, 61), (3, 62), (3, 63), (3, 64), (3, 65), (3, 66), (3, 67), (3, 68),
(3, 69), (3, 70), (3, 71), (3, 72), (3, 73), (3, 74), (3, 75), (3, 76),
(3, 77), (3, 78), (3, 79);

INSERT INTO participants (user_id, conference_id, type_id) VALUES
(50, 1, 1), (51, 1, 1), (52, 1, 1), (53, 1, 1), (54, 1, 1), (55, 1, 1), (56, 1, 1), (57, 1, 1), (58, 1, 1), (59, 1, 1), 
(60, 1, 1), (61, 2, 1), (62, 2, 1), (63, 2, 1), (64, 2, 1), (65, 2, 1), (66, 2, 1), (67, 2, 1), (68, 2, 1), (69, 2, 1),
(70, 3, 1), (71, 3, 1), (72, 3, 1), (73, 3, 1), (74, 3, 1), (75, 3, 1), (76, 3, 1), (77, 3, 1), (78, 3, 1), (79, 3, 1);

insert into question(question, topic_id, user_id, created) VALUES
# Conf 1 Stream 1 Topic 1
('You will build an application that store?', 1, 50, '00:18:11'),
(' POJOs (Plain Old Java Objects) in a memory-based?', 1, 51, '00:45:11'),
('When you finish, you can check?', 1, 52, '00:18:13'),
(' import org.springframewo?', 1, 53, '00:18:11'),
('your results against the code in?', 1, 54, '00:18:14'),
(' POJOoot to start adding beans based o-based?', 1, 55, '00:47:11'),
('ll Spring applications, you should start ?', 1, 56, '00:18:15'),
(' POJOis annotation flags the applicaased?', 1, 57, '00:48:11'),
('The Initializr offers a fast way to?', 1, 58, '00:18:16'),
(' he main() method uses Spring Boot’s SpringAppli?', 1, 59, '00:49:11'),
('pull in all the dependencies you need for?', 1, 50, '00:18:17'),
(' Pneed to modify the simple class that thesed?', 1, 51, '00:50:11'),
('and does a lot of the set up for you?', 1, 52, '00:18:18'),
(' POpository.save(new Customer("Chloe", "O''Brian"));?', 1, 53, '00:31:11'),
(' Application includes a demo() method that puts the?', 1, 54, '00:32:11'),
('Like most Spring Getting Started guides?', 1, 55, '00:18:12'),
# Conf 2 Stream 2 Topic 2
('The following listing shows the pom.xml file create?', 2, 60, '00:18:19'),
(' Create an Application Class?', 2, 61, '00:46:11'),
('In this example, you store?', 2, 62, '00:11:11'),
(' Then it saves a handful of Customer objects, demonstrating the?', 2, 63, '00:33:11'),
('Here you have a Customer class with?', 2, 64, '00:12:11'),
(' Build an executable JAR?', 2, 65, '00:34:11'),
('Yindicating that it is a JPA entity?', 2, 66, '00:13:11'),
(' e JAR file that contains all the necessary?', 2, 67, '00:35:11'),
('re left unannotated. It is assumed that?', 2, 68, '00:14:11'),
(' Pt lifecycle, across different environments, and?', 2, 69, '00:36:11'),
('The convenient toString() method print outs?', 2, 60, '00:15:11'),
(' build the JAR file by using ./gradlew build and then?', 2, 61, '00:37:11'),
('Create Simple Queries?', 2, 62, '00:16:11'),
(' If you use Maven, you can run the application by using?', 2, 63, '00:38:11'),
('To see how this works, create a repository interfac?', 2, 64, '00:00:11'),
('You will build an application that stor?', 2, 65, '00:19:11'),
# Conf 2 Stream 2 Topic 3
(' When you run your application, you should see output similar to the followi?', 3, 66, '00:39:11'),
(' PCongratulations! You have written a simple applicad?', 3, 67, '00:21:11'),
(' and fetch them from a database?', 3, 68, '00:22:11'),
(' If you want to expose JPA repositories with a hypermedia?', 3, 69, '00:23:11'),
(' Want to write a new guide or contribute to?', 3, 60, '00:24:11'),
(' All guides are released with an ASLv2 license for the c?', 3, 61, '00:25:11'),
('Spring Runtime offers support and?', 3, 62, '00:26:11'),
# Conf 3 Stream 3 Topic 4
('that works with Customer entities as the?', 4, 70, '00:18:11'),
(' focuses on using JPA to stor?', 4, 71, '00:28:11'),
('feature is the ability to create repository implementations?', 4, 72, '00:18:11'),
('operty is annotated with ?', 4, 73, '00:48:11'),
('g JPA to store data in a relational da?', 4, 74, '00:58:11'),
('create repository implementations a?', 4, 75, '00:18:21'),
('  for (Customer bauer : repository.findByLastName("Bauer")?', 4, 76, '00:27:11'),
(' uide walks you through the process of bu?', 4, 77, '00:28:11'),
(' Papplication that stores Customer POJOs (Plain Old Ja?', 4, 78, '00:29:11'),
(' bjects) in a memory-based database?', 4, 79, '00:30:11'),
# Conf 3 Stream 3 Topic 5
('CustomerRepository extends the CrudRepository?', 5, 70, '00:18:31'),
('By extending CrudRepository, CustomerRepository inherits several methods for working with?', 5, 71, '00:18:41'),
('ypical Java application, you m?', 5, 72, '00:18:02'),
('Spring Data JPA also lets you define other query methods?', 5, 73, '00:18:51'),
(' About 15 minutes?', 5, 74, '00:31:11'),
('r, that is what makes Spring Data JPA so pow?', 5, 75, '00:18:03'),
(' ke most Spring Getting Sta?', 5, 76, '00:32:32'),
('You need not write an implementation of the repository interface. Spring Data JPA creates an implementation?', 5, 77, '00:18:04'),
(' o start from scratch, move on to Starting withry-based?', 5, 78, '00:18:11'),
('ow you can wire up this example and see what it l?', 5, 79, '00:18:05'),
(' ic setup steps that ar?', 5, 70, '00:59:11');

insert into likes(question_id, user_id) VALUES
# Conf 1 Stream 1 Topic 1
(1, 51),
(1, 52),
(1, 53),
(1, 54),
(1, 55),
(2, 56),
(2, 57),
(2, 58),
(3, 50),
(4, 50),
(5, 50),
(6, 59),
(7, 59),
# Conf 2 Stream 2 Topic 2
(17, 61),
(17, 62),
(17, 63),
(17, 64),
(18, 65),
(18, 66),
(18, 67),
(19, 68),
(19, 69),
(19, 60),
# Conf 2 Stream 2 Topic 3
(33, 61),
(33, 62),
(33, 63),
(33, 64),
(34, 65),
(34, 66),
(34, 67),
(35, 68),
(35, 69),
(36, 60),
# Conf 3 Stream 3 Topic 4
(40, 71),
(40, 72),
(40, 73),
(41, 74),
(41, 75),
(41, 76),
(42, 77),
(42, 78),
(43, 79),
(43, 70),
# Conf 3 Stream 3 Topic 5
(50, 71),
(50, 72),
(50, 73),
(51, 74),
(51, 75),
(52, 76),
(52, 77),
(53, 78),
(53, 79),
(54, 70);