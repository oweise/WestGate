package de.innovationgate.westgate;

import java.util.Optional;

public class DatabaseServerSetup {

    private final String serverId;

    public DatabaseServerSetup(final String id) {
        this.serverId = id;
    }

    public static DatabaseServerSetup serverId(String id) {
        return new DatabaseServerSetup(id);
    }

    /**
     * Gets serverId
     *
     * @return value of serverId
     */
    public String getServerId() {
        return Optional.of(serverId).orElseThrow(()-> new IllegalArgumentException("You must specify a databaseServer id"));
    }
}
