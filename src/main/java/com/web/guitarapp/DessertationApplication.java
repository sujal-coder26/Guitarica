package com.web.guitarapp;

import com.web.guitarapp.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages ={"com.web.guitarapp"}, exclude = { SecurityAutoConfiguration.class } )
@EntityScan(basePackages ={"com.entities"})
@EnableJpaRepositories("com.web.guitarapp.dao")

public class DessertationApplication {

	public static void main(String[] args) {

		SpringApplication.run(DessertationApplication.class, args);
	}

}

