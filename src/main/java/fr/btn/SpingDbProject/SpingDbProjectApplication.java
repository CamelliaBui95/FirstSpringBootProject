package fr.btn.SpingDbProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
public class SpingDbProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpingDbProjectApplication.class, args);
	}

}
