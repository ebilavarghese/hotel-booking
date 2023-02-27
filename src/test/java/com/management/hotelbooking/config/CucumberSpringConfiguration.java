package com.management.hotelbooking.config;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = com.management.hotelbooking.config.CucumberRunnerTest.class)
public class CucumberSpringConfiguration {
}
