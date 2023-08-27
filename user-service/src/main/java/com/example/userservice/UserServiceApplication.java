package com.example.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication {

	/* 유레카 서버가 먼저 실행되고 있어야 한다.
	 * */
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
		System.out.println("* * * EUREKA CLIENT * * * USER SERVICE * * *");
	}

}
