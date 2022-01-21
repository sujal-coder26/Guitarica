package com.web.guitarapp.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.web.guitarapp.dao"})
@EntityScan(basePackages = {"com.web.guitarapp.entities"})
@ComponentScan(basePackages = {"com.web.guitarapp.config"})
public class DataSourceConfig {}
