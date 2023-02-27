package com.management.hotelbooking.config;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;

@RunWith(Cucumber.class)
@CucumberOptions(
        features={"src/test/java/com/management/hotelbooking/features"},
        glue={"com/management/hotelbooking/config","com/management/hotelbooking/stepdefinitions"},
        plugin={"pretty", "json::target/cucumber-reports/cucumber-report.json",
                "junit:target/cucumber-reports/cucumber.xml",
                "html:target/cucumber-reports/report.html"},
        monochrome = true
)
@ComponentScan({"com.management.hotelbooking"})
public class CucumberRunnerTest {
}
