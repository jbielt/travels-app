package com.pim.projects.besttravel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:configs/api-currency.properties")
public class PropertiesConfig {
}
