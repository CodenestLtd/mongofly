package uk.codenest.mongofly.reader;

import java.io.File;
import java.util.List;

import org.springframework.core.io.Resource;

public interface ChangeSetFileProvider {
    List<Resource> getChangeSetFiles();
}
