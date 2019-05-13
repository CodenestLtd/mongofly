package uk.codenest.mongofly.validation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.codenest.mongofly.entity.ChangeSet;
import uk.codenest.mongofly.exception.ValidationException;

public class DefaultChangeSetsValidator implements ChangeSetsValidator {

    @Override
    public void validate(List<ChangeSet> changeSetList) throws ValidationException {
        this.changeSetIdsNotUnique(changeSetList);
    }

    private void changeSetIdsNotUnique(List<ChangeSet> changeSets) {
        Set<String> idSet = new HashSet<>();
        for (ChangeSet changeSet : changeSets) {
            if (idSet.contains(changeSet.getChangeId())) {
                throw new ValidationException("ChangeSetId " + changeSet.getChangeId() + " is not unique.");
            }

            idSet.add(changeSet.getChangeId());
        }
    }
}
