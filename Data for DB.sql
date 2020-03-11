insert into mydb.user(first_name, last_name, password, email, phone_number, user_user_id) values
('Ivan', 'Kvych', '123qweasd', 'ikvuch@gmail.com', '+380952144815', 1),
('Sasha', 'Zoriy', 'dsaewq321', 'zoriy@gmail.com', '+380958644404', 1),
('Slavik', 'Fedoriv', '1qasw23ed', 'slavik@gmail.com', '+380955347470', 1);

insert into conference(name, alias, begin_date, finish_date, info, user_user_id) values
('IT in 2020', 'IT20', '2020-02-09', '2020-02-13', 'Conference about situation in IT in 2020 year', 3),
('Oil and Gas', 'O&G', '2020-02-04', '2020-02-20', 'Conference about oil and gas in Ukraine', 2),
('Solar Panel', 'SP', '2020-05-04', '2020-05-25', 'Conference about solar panel in Europe', 2);

insert into stream(name, conference_conference_id) VALUES
('stream1', 1),
('stream2', 2),
('stream3', 3);

insert into speaker(first_name, last_name, password, info, photo, email, phone_number) VALUES
('Maruv', 'Pavl', '147852369', 'student at NTUONAG', 'none', 'maruv@gmail.com', '+380950320556'),
('Vova', 'Shalamay', 'qweasd321', 'work at ViTech', 'none', 'vova@gmail.com', '+380959865756'),
('Vasia', 'Ambroziak', '][pl;', 'founder of AIW', 'none', 'vasia@gmail.com', '+380658734581');

insert into topic(name, date, begin_time, finish_time, info, is_active, stream_stream_id, speaker_speaker_id) VALUES
('New in Spring', '2020-02-09', '17:00:00', '19:00:00', 'About what new is in Spring', 1, 1, 1),
('New in Gradel', '2020-02-09', '20:00:00', '21:00:00', 'How to use new gradle', 1, 2, 2),
('Problem and solutions in oil and Gas industries', '2020-02-11', '17:00:00', '19:00:00', 'How to solve problem in oil and gas industries ', 1, 2, 2),
('Future of solar panel', '2020-02-12', '17:00:00', '19:00:00', 'What will be soon in solar industries', 1, 3, 3),
('Mono crystal', '2020-02-13', '17:00:00', '19:00:00', 'Advantages of mono crystal solar panel', 1, 3, 3);

insert into guest(email) values
('1email@gmail.com'), ('2email@gmail.com'), ('3email@gmail.com'), ('4email@gmail.com'), ('5email@gmail.com'), ('6email@gmail.com'),
('7email@gmail.com'), ('8email@gmail.com'), ('9email@gmail.com'), ('10email@gmail.com'), ('11email@gmail.com'), ('12email@gmail.com'),
('13email@gmail.com'), ('14email@gmail.com'), ('15email@gmail.com'), ('16email@gmail.com'), ('17email@gmail.com'), ('18email@gmail.com'),
('19email@gmail.com'), ('20email@gmail.com'), ('21email@gmail.com'), ('22email@gmail.com'), ('23email@gmail.com'), ('24email@gmail.com'),
('25email@gmail.com'), ('26email@gmail.com'), ('27email@gmail.com'), ('28email@gmail.com'), ('29email@gmail.com'), ('30email@gmail.com');

insert into question(question, topic_topic_id, guest_guest_id) VALUES
('You will build an application that stores', 1, 1), (' POJOs (Plain Old Java Objects) in a memory-based', 1, 1),
('Like most Spring Getting Started guides', 1, 2), (' Create an Application Class', 2, 2),
('When you finish, you can check', 1, 3), (' import org.springframewo', 1, 3),
('your results against the code in', 1, 4), (' POJOoot to start adding beans based o-based', 1, 4),
('ll Spring applications, you should start ', 1, 5), (' POJOis annotation flags the applicaased', 1, 5),
('The Initializr offers a fast way to', 1, 6), (' he main() method uses Spring Boot’s SpringAppli', 1, 6),
('pull in all the dependencies you need for', 1, 7), (' Pneed to modify the simple class that thesed', 1, 7),
('and does a lot of the set up for you. ', 1, 8), (' POpository.save(new Customer("Chloe", "O''Brian"));', 1, 8),
('The following listing shows the pom.xml file create', 2, 9), (' Application includes a demo() method that puts the', 1, 9),
('In this example, you store', 2, 10), (' Then it saves a handful of Customer objects, demonstrating the', 2, 10),
('Here you have a Customer class with', 2, 11), (' Build an executable JAR', 2, 11),
('Yindicating that it is a JPA entity', 2, 12), (' e JAR file that contains all the necessary ', 2, 12),
('re left unannotated. It is assumed that', 2, 13), (' Pt lifecycle, across different environments, and', 2, 13),
('The convenient toString() method print outs', 2, 14), (' build the JAR file by using ./gradlew build and then', 2, 14),
('Create Simple Queries', 2, 15), (' If you use Maven, you can run the application by using', 2, 15),
('To see how this works, create a repository interfac', 2, 16), (' When you run your application, you should see output similar to the followi', 3, 16),
('You will build an application that stores', 2, 17), (' PCongratulations! You have written a simple applicad', 3, 17),
('that works with Customer entities as the', 4, 18), (' and fetch them from a database, ', 3, 18),
(' focuses on using JPA to stor', 4, 19), (' If you want to expose JPA repositories with a hypermedia-', 3, 19),
('feature is the ability to create repository implementations', 4, 20), (' Want to write a new guide or contribute to ', 3, 20),
('operty is annotated with @', 4, 21), (' All guides are released with an ASLv2 license for the c', 3, 21),
('g JPA to store data in a relational da', 4, 22), ('Spring Runtime offers support and', 3, 22),
('create repository implementations a', 4, 23), ('  for (Customer bauer : repository.findByLastName("Bauer")', 4, 23),
('CustomerRepository extends the CrudRepository ', 5, 24), (' uide walks you through the process of bu', 4, 24),
('By extending CrudRepository, CustomerRepository inherits several methods for working with', 5, 25), (' Papplication that stores Customer POJOs (Plain Old Ja', 4, 25),
('Spring Data JPA also lets you define other query methods ', 5, 26), (' bjects) in a memory-based database.', 4, 26),
('ypical Java application, you m', 5, 27), (' About 15 minutes', 5, 27),
('r, that is what makes Spring Data JPA so pow', 5, 28), (' ke most Spring Getting Sta', 5, 28),
('You need not write an implementation of the repository interface. Spring Data JPA creates an implementation ', 5, 29), (' o start from scratch, move on to Starting withry-based', 5, 29),
('ow you can wire up this example and see what it l', 5, 30), (' ic setup steps that ar', 5, 30);
