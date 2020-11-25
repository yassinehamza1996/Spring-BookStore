package com.vermeg.ams;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vermeg.ams.controllers.BookController;

@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) {
		new File(BookController.uploadDirectory).mkdir();
		SpringApplication.run(TestApplication.class, args);
	}

}
