package uk.codenest.mongfly.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(of = "changeId")
@Data
public class ChangeSet {
    private final String changeId;
    private final String description;
    private final String file;
    private final Script command;

    @Getter
    @AllArgsConstructor
    public enum ChangeSetEnum {
        FILE("file") {
            public String getAttributeValue(ChangeSet changeSet) {
                return changeSet.getFile();
            }
        }, CHANGE_ID("changeId") {
            public String getAttributeValue(ChangeSet changeSet) {
                return changeSet.getChangeId();
            }
        }, DESCRIPTION("description") {
            public String getAttributeValue(ChangeSet changeSet) {
                return changeSet.getDescription();
            }
        };

        private final String value;

        public abstract String getAttributeValue(ChangeSet changeSet);
    }
}
