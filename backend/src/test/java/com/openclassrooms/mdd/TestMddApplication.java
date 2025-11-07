package com.openclassrooms.mdd;

import org.springframework.boot.SpringApplication;

public class TestMddApplication {

	public static void main(String[] args) {
		SpringApplication.from(MddApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
