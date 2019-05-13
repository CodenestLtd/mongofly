package uk.codenest.mongfly.reader;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClasspathFileScanChangeSetFileProvider implements ChangeSetFileProvider {

    private final String migrationFolder;

    public ClasspathFileScanChangeSetFileProvider(final String migrationFolder) {
        this.migrationFolder = migrationFolder;
    }

    @Override
    public List<File> getChangeSetFiles() {
        return this.getResourceFolderFiles(migrationFolder);
    }

    private List<File> getResourceFolderFiles(final String folder) {
        try {
            Path path = Paths.get(getClass().getClassLoader().getResource(folder).toURI());
            final File file = path.toFile();
            return Arrays.asList(file.listFiles());
        } catch (URISyntaxException e) {
            log.error("Failed to find migration folder", e);
            throw new RuntimeException(e.getCause());
        }
    }
}
