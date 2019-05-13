package uk.codenest.mongfly.repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import uk.codenest.mongfly.entity.ChangeSet;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;

public class MongoFlyRepository {
    private final MongoDatabase database;
    private final String collectionName;

    public MongoFlyRepository(final MongoClient mongo, final String databaseName, final String collectionName) {
        this.collectionName = collectionName;
        this.database = mongo.getDatabase(databaseName);
        this.configure();
    }

    public boolean wasExecuted(final ChangeSet changeSet) {
        BasicDBObject query = new BasicDBObject();
        query.append(ChangeSet.ChangeSetEnum.CHANGE_ID.getValue(), ChangeSet.ChangeSetEnum.CHANGE_ID.getAttributeValue(changeSet));
        return this.getMongoFlyCollection().find(query).first() != null;
    }

    public void runScript(final String script) {
        final BasicDBObject command = new BasicDBObject();
        command.put("eval", script);
        database.runCommand(command);
    }

    public void logChangeSet(final ChangeSet changeSet) {
        final Map<String, Object> map = new HashMap<>();
        for (ChangeSet.ChangeSetEnum attribute : ChangeSet.ChangeSetEnum.values()) {
            map.put(attribute.getValue(), attribute.getAttributeValue(changeSet));
        }

        map.put("createdTs", Instant.now());

        this.getMongoFlyCollection().insertOne(new Document(map));
    }


    private void configure() {
        if (!this.database.listCollectionNames().into(new ArrayList<>()).contains(collectionName)) {
            this.database.createCollection(collectionName);
            this.getMongoFlyCollection().createIndex(new BasicDBObject(ChangeSet.ChangeSetEnum.CHANGE_ID.getValue(), 1), new IndexOptions().unique(true));
        }
    }

    private MongoCollection<Document> getMongoFlyCollection() {
        return this.database.getCollection(collectionName);
    }

}
