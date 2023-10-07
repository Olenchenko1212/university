package ua.foxminded.universitycms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ua.foxminded.universitycms")
public class UniversityCmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniversityCmsApplication.class, args);
	}
}