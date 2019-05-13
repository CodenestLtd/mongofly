package uk.codenest.mongofly.examples;

import com.mongodb.MongoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import uk.codenest.mongofly.MongoFly;

@Slf4j
@SpringBootApplication
public class ExampleApplication {

    @Primary
    @Bean(initMethod = "process")
    public MongoFly mongoFly(MongoClient mongoClient) {
        return new MongoFly(mongoClient, "test");
    }

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }
}
