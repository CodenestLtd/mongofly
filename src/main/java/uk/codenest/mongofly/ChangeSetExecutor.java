package uk.codenest.mongofly;

import java.util.List;

import uk.codenest.mongofly.entity.ChangeSet;
import uk.codenest.mongofly.repository.MongoFlyRepository;
import com.mongodb.MongoClient;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
public class ChangeSetExecutor {

    private MongoFlyRepository mongoFlyRepository;
    private final boolean failOnError;

    public ChangeSetExecutor(final MongoClient mongo, final String databaseName, final String collectionName, boolean failOnError) {
        this.failOnError = failOnError;
        mongoFlyRepository = new MongoFlyRepository(mongo, databaseName, collectionName);
    }

    public void execute(final List<ChangeSet> changeSets) {
        changeSets.stream()
                .forEach(changeSet -> {
                    if (!mongoFlyRepository.wasExecuted(changeSet)) {
                        execute(changeSet);
                        log.info("ChangeSet " + changeSet.getChangeId() + " has been executed");
                    } else {
                        log.info("ChangeSet already executed: " + changeSet.getChangeId());
                    }
                });
    }

    private void execute(final ChangeSet changeSet) {
        try {
            changeSet.getCommand().run(mongoFlyRepository);
        } catch (RuntimeException e) {
            if (this.failOnError) {
                throw e;
            } else {
                log.warn("ChangeSet " + changeSet.getChangeId() + " has failed, but failOnError is set to false", e.getMessage());
            }
        }

        mongoFlyRepository.logChangeSet(changeSet);
    }
}
