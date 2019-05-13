package uk.codenest.mongfly.validation;

import java.util.List;

import uk.codenest.mongfly.entity.ChangeSet;
import uk.codenest.mongfly.exception.ValidationException;

public interface ChangeSetsValidator {
    void validate(List<ChangeSet> changeSets) throws ValidationException;
}
