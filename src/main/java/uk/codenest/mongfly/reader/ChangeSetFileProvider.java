package uk.codenest.mongfly.reader;

import java.io.File;
import java.util.List;

public interface ChangeSetFileProvider {
    List<File> getChangeSetFiles();
}
