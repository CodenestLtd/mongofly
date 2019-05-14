package uk.codenest.mongofly.reader;

import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Slf4j
public class ClasspathFileScanChangeSetFileProvider implements ChangeSetFileProvider {

    private final String migrationFolder;

    public ClasspathFileScanChangeSetFileProvider(final String migrationFolder) {
        this.migrationFolder = migrationFolder;
    }

    @Override
    public List<Resource> getChangeSetFiles() {
        return this.getResourceFolderFiles(migrationFolder);
    }

    private List<Resource> getResourceFolderFiles(final String folder) {
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath:" + folder + "/*.js");
            return Arrays.asList(resources);
        } catch (Exception e) {
            log.error("Failed to find migration folder", e);
            throw new RuntimeException(e.getCause());
        }
    }
}
