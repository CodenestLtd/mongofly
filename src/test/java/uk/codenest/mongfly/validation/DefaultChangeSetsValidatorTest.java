package uk.codenest.mongfly.validation;

import java.util.ArrayList;
import java.util.List;

import uk.codenest.mongfly.entity.ChangeSet;
import uk.codenest.mongfly.exception.ValidationException;
import org.testng.annotations.Test;

public class DefaultChangeSetsValidatorTest {

    @Test(expectedExceptions = ValidationException.class)
    public void testDetectDuplicate() {
        DefaultChangeSetsValidator validator = new DefaultChangeSetsValidator();

        List<ChangeSet> changeSets = new ArrayList<>();

        changeSets.add(makeChangeSet("20190501-0012"));
        changeSets.add(makeChangeSet("20190501-0013"));
        changeSets.add(makeChangeSet("20190501-0014"));
        changeSets.add(makeChangeSet("20190501-0015"));
        changeSets.add(makeChangeSet("20190501-0012"));
        changeSets.add(makeChangeSet("20190501-0017"));
        changeSets.add(makeChangeSet("20190501-0018"));
        changeSets.add(makeChangeSet("20190501-0019"));
        changeSets.add(makeChangeSet("20190501-0112"));
        changeSets.add(makeChangeSet("20190501-0012"));
        validator.validate(changeSets);
    }

    @Test
    public void testValidateNoDuplicates() {
        DefaultChangeSetsValidator validator = new DefaultChangeSetsValidator();

        List<ChangeSet> changeSets = new ArrayList<>();

        changeSets.add(makeChangeSet("20190501-0012"));
        changeSets.add(makeChangeSet("20190501-0013"));
        changeSets.add(makeChangeSet("20190501-0014"));
        changeSets.add(makeChangeSet("20190501-0015"));
        changeSets.add(makeChangeSet("20190501-0016"));
        changeSets.add(makeChangeSet("20190501-0017"));
        changeSets.add(makeChangeSet("20190501-0018"));
        changeSets.add(makeChangeSet("20190501-0019"));
        changeSets.add(makeChangeSet("20190501-0112"));
        changeSets.add(makeChangeSet("20190501-0212"));
        validator.validate(changeSets);
    }

    private ChangeSet makeChangeSet(String id) {
        return new ChangeSet(id, "Test", id + "2_Test.js", null);
    }

}