#CREATE DATABASE confassist CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `roles` (
   `roles_id` bigint(20) NOT NULL AUTO_INCREMENT,
   `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
   `role` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
   PRIMARY KEY (`roles_id`),
   UNIQUE KEY `UK_g50w4r0ru3g9uf6i6fr4kpro8` (`role`)
 ); 
 
 CREATE TABLE `participant_type` (
   `type_id` bigint(20) NOT NULL AUTO_INCREMENT,
   `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
   PRIMARY KEY (`type_id`),
   UNIQUE KEY `UK_mkyp545lt7foumugm96jmygro` (`name`)
 );

 CREATE TABLE `user` (
   `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
   `is_active` bit(1) NOT NULL,
   `activ_code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
   `is_deleted` bit(1) DEFAULT NULL,
   `created`    date        COLLATE utf8mb4_unicode_ci DEFAULT NULL,
   `email` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
   `first_name` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
   `info` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
   `last_name` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
   `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
   `photo` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
   PRIMARY KEY (`user_id`),
   UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
 );
 
 CREATE TABLE `user_roles` (
   `user_id` bigint(20) NOT NULL,
   `roles_id` bigint(20) NOT NULL,
   PRIMARY KEY (`user_id`,`roles_id`),
   KEY `FKdbv8tdyltxa1qjmfnj9oboxse` (`roles_id`),
   CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
   CONSTRAINT `FKdbv8tdyltxa1qjmfnj9oboxse` FOREIGN KEY (`roles_id`) REFERENCES `roles` (`roles_id`)
 );
 
CREATE TABLE `conference` (
   `conference_id` bigint(20) NOT NULL AUTO_INCREMENT,
   `alias` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
   `begin_date` datetime NOT NULL,
   `cover_photo` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
   `finish_date` datetime NOT NULL,
   `info` varchar(1000) COLLATE utf8mb4_unicode_ci NOT NULL,
   `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
   `venue` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
   `owner_id` bigint(20) NOT NULL,
   `is_deleted` bit(1) DEFAULT NULL,
   PRIMARY KEY (`conference_id`),
   UNIQUE KEY `UK_afqxob6ksrs2084w6if59wrfn` (`alias`),
   UNIQUE KEY `UK_ch9kpcpm8yvkxfmx3u8mrjhus` (`name`),
   KEY `FK38drjgkfcnv6a4m4ba3n41krm` (`owner_id`),
   CONSTRAINT `FK38drjgkfcnv6a4m4ba3n41krm` FOREIGN KEY (`owner_id`) REFERENCES `user` (`user_id`)
 );
 
 CREATE TABLE `stream` (
   `stream_id` bigint(20) NOT NULL AUTO_INCREMENT,
   `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
   `conference_id` bigint(20) NOT NULL,
   `moderator_id` bigint(20) NOT NULL,
   PRIMARY KEY (`stream_id`),
   UNIQUE KEY `UK_7vsmjmtlorpe00onlxxacaohl` (`name`),
   KEY `FKqcjyg2dyytxo5gt0c2mscjo3p` (`conference_id`),
   KEY `FKfi7bemk9oiksli2s3m37hohdh` (`moderator_id`),
   CONSTRAINT `FKfi7bemk9oiksli2s3m37hohdh` FOREIGN KEY (`moderator_id`) REFERENCES `user` (`user_id`),
   CONSTRAINT `FKqcjyg2dyytxo5gt0c2mscjo3p` FOREIGN KEY (`conference_id`) REFERENCES `conference` (`conference_id`)
 );
 
 CREATE TABLE `topic` (
   `topic_id` bigint(20) NOT NULL AUTO_INCREMENT,
   `begin_time` time NOT NULL,
   `cover_photo` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
   `date` date NOT NULL,
   `finish_time` time NOT NULL,
   `info` varchar(2000) COLLATE utf8mb4_unicode_ci NOT NULL,
   `is_allowed_question` bit(1) NOT NULL,
   `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
   `speaker_id` bigint(20) NOT NULL,
   `stream_id` bigint(20) NOT NULL,
   PRIMARY KEY (`topic_id`),
   UNIQUE KEY `UK_mbunn9erv8nmf5lk1r2nu0nex` (`name`),
   KEY `FKq83rwxv4ch1rvow2vwqjnkyx9` (`speaker_id`),
   KEY `FK3xrte29a6ubrga1b79meh35q8` (`stream_id`),
   CONSTRAINT `FK3xrte29a6ubrga1b79meh35q8` FOREIGN KEY (`stream_id`) REFERENCES `stream` (`stream_id`),
   CONSTRAINT `FKq83rwxv4ch1rvow2vwqjnkyx9` FOREIGN KEY (`speaker_id`) REFERENCES `user` (`user_id`)
 );
 
 CREATE TABLE `participants` (
   `user_id` bigint(20) NOT NULL,
   `conference_id` bigint(20) NOT NULL,
   `type_id` bigint(20) NOT NULL,
   PRIMARY KEY (`conference_id`,`type_id`,`user_id`),
   KEY `FKlygocmohuh11svfduxxw8ykk6` (`user_id`),
   KEY `FK2p06tbe3gmeshxkoj4g6hooa3` (`type_id`),
   CONSTRAINT `FK2p06tbe3gmeshxkoj4g6hooa3` FOREIGN KEY (`type_id`) REFERENCES `participant_type` (`type_id`),
   CONSTRAINT `FKd7isg61se0ws9nc7ladub87n0` FOREIGN KEY (`conference_id`) REFERENCES `conference` (`conference_id`),
   CONSTRAINT `FKlygocmohuh11svfduxxw8ykk6` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
 );
 
 CREATE TABLE `question` (
   `question_id` bigint(20) NOT NULL AUTO_INCREMENT,
   `created` time NOT NULL,
   `deleted` tinyint(1) NOT NULL DEFAULT '0',
   `question` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
   `selected` tinyint(1) NOT NULL DEFAULT '0',
   `topic_id` bigint(20) NOT NULL,
   `user_id` bigint(20) NOT NULL,
   PRIMARY KEY (`question_id`),
   KEY `FK9h1t7swdq9eej6qf9yxtc8g9w` (`topic_id`),
   KEY `FK4ekrlbqiybwk8abhgclfjwnmc` (`user_id`),
   CONSTRAINT `FK4ekrlbqiybwk8abhgclfjwnmc` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
   CONSTRAINT `FK9h1t7swdq9eej6qf9yxtc8g9w` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`topic_id`)
 );
 
 CREATE TABLE `likes` (
   `question_id` bigint(20) NOT NULL,
   `user_id` bigint(20) NOT NULL,
   PRIMARY KEY (`question_id`,`user_id`),
   KEY `FKi2wo4dyk4rok7v4kak8sgkwx0` (`user_id`),
   CONSTRAINT `FKi2wo4dyk4rok7v4kak8sgkwx0` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
   CONSTRAINT `FKnbhujedlj8a26j6mj51fkelve` FOREIGN KEY (`question_id`) REFERENCES `question` (`question_id`)
 );
 create table persistent_logins (username varchar(64) not null,
                                series varchar(64) primary key,
                                token varchar(64) not null,
                                last_used timestamp not null);
 