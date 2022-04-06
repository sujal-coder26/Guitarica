package com.web.guitarapp.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration // using configuration annotations for the configuring purpose
@EnableJpaRepositories(basePackages = {"com.web.guitarapp.dao"}) // using this annotations to enable JPA repository from the mentioned package
@EntityScan(basePackages = {"com.web.guitarapp.entities"}) // using this annotation to scan the entities from the mentioned package
@ComponentScan(basePackages = {"com.web.guitarapp.config"}) // this annotation will enable the component scanning from the mentioned package
public class DataSourceConfig {} // just making a class
