package br.com.clinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class ClinicSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(ClinicSystemApplication.class, args);
	}
}
