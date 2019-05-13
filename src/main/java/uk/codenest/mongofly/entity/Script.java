package uk.codenest.mongofly.entity;

import uk.codenest.mongofly.repository.MongoFlyRepository;
import lombok.Data;

@Data
public class Script {
    private final String body;

    public void run(MongoFlyRepository mongoFlyRepository) {
        mongoFlyRepository.runScript(body);
    }
}
