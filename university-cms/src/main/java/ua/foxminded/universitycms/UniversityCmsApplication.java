package ua.foxminded.universitycms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(scanBasePackages = "ua.foxminded.universitycms", exclude = { SecurityAutoConfiguration.class })
public class UniversityCmsApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(UniversityCmsApplication.class, args);
	}
}