package esi.buildit9;

import org.apache.log4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;

public class DatabaseUriResolver {

    private final Logger log = Logger.getLogger(DatabaseUriResolver.class);

    public URI getUri() throws URISyntaxException {
        String propertyUri = System.getProperty("DATABASE_URL");
        String envUri = System.getenv("DATABASE_URL");
        if (propertyUri != null) {
            log.info("using database uri from properties");
            return new URI(propertyUri);
        }
        if (envUri != null) {
            log.info("using database uri from environment");
            return new URI(envUri);
        }
        log.warn("using local database");
        return new URI("postgres://postgres:postgres@localhost:5432/rentit9");
    }

}
