package uk.codenest.mongfly.reader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import uk.codenest.mongfly.entity.ChangeSet;
import uk.codenest.mongfly.entity.Script;
import uk.codenest.mongfly.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChangeSetReader {

    private static final String FILE_PATTERN = "V[0-9]{4}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])-(2[0-3]|[01][0-9])[0-5][0-9]_([a-zA-Z0-9_-]){3,20}.js";
    private static Pattern PATTERN = Pattern.compile(FILE_PATTERN);

    public ChangeSet getChangeSet(final File file) {
        final String filename = file.getName();
        if (!PATTERN.matcher(filename).matches()) {
            throw new ValidationException("File pattern must be Vyyyymmdd-hhMM_Description.js");
        }

        final String changeId = filename.substring(0, filename.indexOf("_"));
        final String description = filename.substring(filename.indexOf("_") + 1, filename.indexOf(".js"));
        final Script script = this.getScripts(file);

        if (log.isTraceEnabled()) {
            log.trace("Changeset");
            log.trace("id: " + changeId);
            log.trace("description: " + description);
            log.trace("script");
            log.trace(script.getBody());
        }

        return new ChangeSet(changeId, description, filename, script);
    }

    private Script getScripts(final File file) {
        try {
            final String script = new String(Files.readAllBytes(Paths.get(file.toURI())));
            return new Script(script);
        } catch (IOException e) {
            log.error("Cannot read file", e);
            return null;
        }
    }
}
