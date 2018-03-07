CREATE TABLE `workout_users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='workout_users';
CREATE TABLE `workout` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) DEFAULT NULL,
  `cals_burnt` double DEFAULT NULL,
  `unit` varchar(10) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_wk_usr_idx` (`user_id`),
  CONSTRAINT `fk_wk_usr` FOREIGN KEY (`user_id`) REFERENCES `workout_users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='workout';
CREATE TABLE `workout_txn` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `workout_id` bigint(20) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `duration` double DEFAULT NULL,
  `cals_burnt` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_wktxn_wk_idx` (`workout_id`),
  CONSTRAINT `fk_wktxn_wk` FOREIGN KEY (`workout_id`) REFERENCES `workout` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='workout_txn';
