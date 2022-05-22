package com.nttdata.bankingInquiries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BankingInquiriesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingInquiriesApplication.class, args);
	}

}
