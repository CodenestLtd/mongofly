package uk.codenest.mongofly.examples;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class Application {

    @Bean
    public MongoFly mongoFly() {

    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
