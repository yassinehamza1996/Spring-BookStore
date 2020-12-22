package com.vermeg.ams;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.vermeg.ams.controllers.BookController;

@SpringBootApplication
//@EntityScan("com.vermeg.ams.entities")
//@EnableJpaRepositories(basePackages= {"com.vermeg.ams.repositories"})
public class TestApplication  implements CommandLineRunner {
	@Autowired
	 private JavaMailSender javaMailSender;
	void sendEmail() {
	 SimpleMailMessage msg = new SimpleMailMessage();
	 msg.setTo("yassinehamza1996@gmail.com");
	 msg.setSubject("Testing from Spring Boot");
	 msg.setText("Hello World \n Spring Boot Email");
	 javaMailSender.send(msg);
	 }
	@Override
	 public void run(String... args) throws MessagingException,IOException {
//	 System.out.println("Sending Email...");
//	 sendEmail();
//	 System.out.println("Done");
	 }

	public static void main(String[] args) {
		new File(BookController.uploadDirectory).mkdir();
		SpringApplication.run(TestApplication.class, args);
	}

}
