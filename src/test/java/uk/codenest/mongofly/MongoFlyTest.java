package uk.codenest.mongofly;

import com.mongodb.MongoClient;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoDatabase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test
public class MongoFlyTest {
    private String dbName = "mongofly_test";
    private MongoClient mongo;
    private MongoDatabase mongoDatabase;

    @BeforeMethod
    protected void setUp() {
        mongo = new MongoClient();
        mongoDatabase = mongo.getDatabase(dbName);
        mongoDatabase.drop();
    }

    private MongoFly create() {
        return new MongoFly(mongo, dbName);
    }

    @Test(groups = "repository")
    public void testMongofly() {
        MongoFly mongoFly = create();

        mongoFly.process();

        assertEquals(mongoDatabase.getCollection("mongofly").count(), 3);
        assertEquals(mongoDatabase.getCollection("organization").count(), 2);
        assertEquals(mongoDatabase.getCollection("user").count(), 2);
    }

    @Test(groups = "repository", expectedExceptions = MongoWriteException.class)
    public void testRunTwice() {
        testMongofly();
        testMongofly();
    }
}
