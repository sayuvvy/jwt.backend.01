package com.booksample.site;

import com.booksample.site.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "")
@EnableAsync
public class SiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiteApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner (RoleRepository roleRepository){
		return args -> {
			if (roleRepository.findByName("USER").isEmpty()) {
				roleRepository.save(com.booksample.site.model.Role.builder().name("USER").build());
			}
		};
	}

}
