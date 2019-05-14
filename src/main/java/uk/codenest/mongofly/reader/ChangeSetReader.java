package uk.codenest.mongofly.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import uk.codenest.mongofly.entity.ChangeSet;
import uk.codenest.mongofly.entity.Script;
import uk.codenest.mongofly.exception.ValidationException;

@Slf4j
public class ChangeSetReader {

    private static final String FILE_PATTERN = "V[0-9]{4}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])-(2[0-3]|[01][0-9])[0-5][0-9]_([a-zA-Z0-9_-]){3,100}.js";
    private static Pattern PATTERN = Pattern.compile(FILE_PATTERN);

    public ChangeSet getChangeSet(final Resource resource) {
        final String filename = resource.getFilename();
        if (!PATTERN.matcher(filename).matches()) {
            throw new ValidationException("File pattern must be Vyyyymmdd-hhMM_Description.js");
        }

        final String changeId = filename.substring(0, filename.indexOf("_"));
        final String description = filename.substring(filename.indexOf("_") + 1, filename.indexOf(".js"));
        final Script script = this.getScripts(resource);

        if (log.isTraceEnabled()) {
            log.trace("Changeset");
            log.trace("id: " + changeId);
            log.trace("description: " + description);
            log.trace("script");
            log.trace(script.getBody());
        }

        return new ChangeSet(changeId, description, filename, script);
    }

    private Script getScripts(final Resource resource) {
        try {
            InputStream inputStream = resource.getInputStream();
            StringBuilder str = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()))) {
                str.append(br.lines().collect(Collectors.joining(System.lineSeparator())));
            }

            return new Script(str.toString());
        } catch (IOException e) {
            log.error("Cannot read file", e);
            return null;
        }
    }
}
