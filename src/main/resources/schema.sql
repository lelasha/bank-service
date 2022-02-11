CREATE TABLE `user_entity` (
  `account_number` varchar(255) NOT NULL,
  `pin` varchar(255) NOT NULL,
  `balance` decimal(19,2) DEFAULT NULL,
  `card_number` varchar(255) DEFAULT NULL,
  `finger_print` varchar(255) NOT NULL,
  `version` int DEFAULT NULL,
  PRIMARY KEY (`account_number`),
  UNIQUE KEY `UK_tk407glav1pcs0i6edgu01bpb` (`card_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE `user_transaction_entity` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) DEFAULT NULL,
  `operation_type` varchar(255) DEFAULT NULL,
  `transaction_date` datetime(6) DEFAULT NULL,
  `user_account_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhajp8jtm5gj45w7608gutuwes` (`user_account_id`),
  CONSTRAINT `FKhajp8jtm5gj45w7608gutuwes` FOREIGN KEY (`user_account_id`) REFERENCES `user_entity` (`account_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
