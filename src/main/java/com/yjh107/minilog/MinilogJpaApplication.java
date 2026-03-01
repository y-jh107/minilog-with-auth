package com.yjh107.minilog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MinilogJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinilogJpaApplication.class, args);
    }

}
