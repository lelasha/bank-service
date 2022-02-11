package com.emulator.bankservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BankServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankServiceApplication.class, args);
	}

}
