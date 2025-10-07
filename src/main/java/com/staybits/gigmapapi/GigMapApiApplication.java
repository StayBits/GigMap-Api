package com.staybits.gigmapapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GigMapApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GigMapApiApplication.class, args);
    }

}
