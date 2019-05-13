package uk.codenest.mongofly.validation;

import java.util.List;

import uk.codenest.mongofly.entity.ChangeSet;
import uk.codenest.mongofly.exception.ValidationException;

public interface ChangeSetsValidator {
    void validate(List<ChangeSet> changeSets) throws ValidationException;
}
