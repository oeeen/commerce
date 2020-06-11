package dev.smjeon.commerce;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CommerceApplication {
	private static final String OAUTH = "spring.config.location=classpath:oauth.yml";
	private static final String CONFIG = "spring.config.location=classpath:application.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(CommerceApplication.class)
				.properties(OAUTH, CONFIG)
				.run(args);
	}

}
