package uk.codenest.mongofly;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import uk.codenest.mongofly.entity.ChangeSet;
import uk.codenest.mongofly.reader.ChangeSetFileProvider;
import uk.codenest.mongofly.reader.ChangeSetReader;
import uk.codenest.mongofly.reader.ClasspathFileScanChangeSetFileProvider;
import uk.codenest.mongofly.validation.ChangeSetsValidator;
import uk.codenest.mongofly.validation.DefaultChangeSetsValidator;
import com.mongodb.MongoClient;
import lombok.Setter;

@Setter
public class MongoFly {

    private ChangeSetFileProvider changeSetFileProvider;
    private ChangeSetsValidator changeSetsValidator;
    private ChangeSetExecutor changeSetExecutor;
    private ChangeSetReader changeSetReader = new ChangeSetReader();

    private String migrationFolderPath = "db/migration";
    private String collectionName = "mongofly";
    private boolean failOnError = true;

    public MongoFly(final MongoClient mongo, final String databaseName) {
        this.changeSetFileProvider = new ClasspathFileScanChangeSetFileProvider(migrationFolderPath);
        this.changeSetsValidator = new DefaultChangeSetsValidator();
        this.changeSetExecutor = new ChangeSetExecutor(mongo, databaseName, collectionName, failOnError);
    }

    public void process() {
        changeSetExecutor.execute(this.getChangeSets());
    }

    private List<ChangeSet> getChangeSets() {
        List<ChangeSet> changeSets =
                changeSetFileProvider.getChangeSetFiles()
                        .stream()
                        .map(file -> changeSetReader.getChangeSet(file))
                        .sorted(Comparator.comparing(ChangeSet::getChangeId))
                        .collect(Collectors.toList());

        changeSetsValidator.validate(changeSets);
        return changeSets;
    }
}
