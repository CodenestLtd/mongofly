package uk.codenest.mongfly.entity;

import uk.codenest.mongfly.repository.MongoFlyRepository;
import lombok.Data;

@Data
public class Script {
    private final String body;

    public void run(MongoFlyRepository mongoFlyRepository) {
        mongoFlyRepository.runScript(body);
    }
}
