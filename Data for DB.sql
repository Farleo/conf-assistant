INSERT INTO confassist.roles (role_id, description, name)
VALUES (1, 'user', 'user');
INSERT INTO confassist.roles (role_id, description, name)
VALUES (2, 'moderator', 'moderator');
INSERT INTO confassist.roles (role_id, description, name)
VALUES (3, 'admin', 'admin');


INSERT INTO confassist.conference (conference_id, alias, description, name)
VALUES (1, 'java_aca', 'java academy', 'java academy');
INSERT INTO confassist.conference (conference_id, alias, description, name)
VALUES (2, 'it_rally_2020', 'IT rally 2020', 'IT event');

INSERT INTO confassist.stream (stream_id, location, name, conference_id)
VALUES (1, 'kimnata 1', 'Java stream', 1);
INSERT INTO confassist.stream (stream_id, location, name, conference_id)
VALUES (2, 'kimnata 2', '.Net ', 1);
INSERT INTO confassist.stream (stream_id, location, name, conference_id)
VALUES (3, 'kimnata 1', 'Ruby', 2);
INSERT INTO confassist.stream (stream_id, location, name, conference_id)
VALUES (4, 'kimnata 2', 'Python', 2);

INSERT INTO confassist.user (user_id, email, first_name, last_name, password, phone_number)
VALUES (1, 'user@gmail.com', 'User1', 'User1_lastname', 'userpass', '');
INSERT INTO confassist.user (user_id, email, first_name, last_name, password, phone_number)
VALUES (2, 'speaker@gmail.com', 'Speker1', 'Speaker1_lastname', 'speakerpass', null);
INSERT INTO confassist.user (user_id, email, first_name, last_name, password, phone_number)
VALUES (3, 'moder@gmail.com', 'moder', 'moder_last', 'moderpass', null);
INSERT INTO confassist.user (user_id, email, first_name, last_name, password, phone_number)
VALUES (4, 'admin@admin.com', 'admin', 'admin', 'adminpass', '380451231212');
INSERT INTO confassist.user (user_id, email, first_name, last_name, password, phone_number)
VALUES (5, 'user2@gmail.com', 'User2', 'User2_lastname', 'userpass', null);

INSERT INTO confassist.user_roles (user_id, role_id)
VALUES (1, 1);
INSERT INTO confassist.user_roles (user_id, role_id)
VALUES (2, 2);
INSERT INTO confassist.user_roles (user_id, role_id)
VALUES (3, 2);
INSERT INTO confassist.user_roles (user_id, role_id)
VALUES (4, 3);


INSERT INTO confassist.presentations (presentation_id, description, end_time, name, start_time, speaker_id, stream_id)
VALUES (1, 'Java for beginner. Speaker Ihor', '2020-03-15 23:52:59', 'Java for beginner', '2020-03-15 23:52:07', 2, 1);
INSERT INTO confassist.presentations (presentation_id, description, end_time, name, start_time, speaker_id, stream_id)
VALUES (2, '.net ', '2020-03-16 00:03:10.000000', 'Dot NET', '2020-03-16 00:02:13.000000', 2, 2);
INSERT INTO confassist.presentations (presentation_id, description, end_time, name, start_time, speaker_id, stream_id)
VALUES (3, 'python', '2020-03-16 00:03:50.000000', 'python starter', '2020-03-16 00:03:48.000000', 2, 4);
INSERT INTO confassist.presentations (presentation_id, description, end_time, name, start_time, speaker_id, stream_id)
VALUES (4, 'ruby', '2020-03-16 00:04:21.000000', 'ruby pro', '2020-03-16 00:03:23.000000', 2, 3);


INSERT INTO confassist.participant_type (type_id, name)
VALUES (1, 'visitor');
INSERT INTO confassist.participant_type (type_id, name)
VALUES (2, 'speaker');
INSERT INTO confassist.participant_type (type_id, name)
VALUES (3, 'moder');

INSERT INTO confassist.question (question_id, question, owner_id, presentation_id)
VALUES (2, 'What are the advantages of Java compared to other programming languages?', 1, 1);
INSERT INTO confassist.question (question_id, question, owner_id, presentation_id)
VALUES (3, 'What is the difference between .Net and Java', 5, 2);
INSERT INTO confassist.question (question_id, question, owner_id, presentation_id)
VALUES (4, 'What is Python interesting for?', 5, 3);

INSERT INTO confassist.likes (question_id, user_id) VALUES (2, 1);
INSERT INTO confassist.likes (question_id, user_id) VALUES (2, 5);
INSERT INTO confassist.likes (question_id, user_id) VALUES (2, 3);
INSERT INTO confassist.likes (question_id, user_id) VALUES (3, 1);
INSERT INTO confassist.likes (question_id, user_id) VALUES (3, 3);
INSERT INTO confassist.likes (question_id, user_id) VALUES (4, 1);
INSERT INTO confassist.likes (question_id, user_id) VALUES (4, 4);

INSERT INTO confassist.participants (user_id, conference_id, type_id) VALUES (1, 1, 1);
INSERT INTO confassist.participants (user_id, conference_id, type_id) VALUES (2, 1, 2);
INSERT INTO confassist.participants (user_id, conference_id, type_id) VALUES (3, 2, 2);
INSERT INTO confassist.participants (user_id, conference_id, type_id) VALUES (5, 2, 1);