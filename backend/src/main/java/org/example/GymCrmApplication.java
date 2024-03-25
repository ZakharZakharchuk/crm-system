package org.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Hello world!
 */
@SpringBootApplication
@EntityScan(basePackages = {"org.example.data.entity"})
@EnableJpaRepositories
public class GymCrmApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(GymCrmApplication.class, args);
    }
}
