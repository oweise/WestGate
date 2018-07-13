package de.innovationgate.westgate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import de.innovationgate.webgate.api.WGDatabaseCore;
import de.innovationgate.wga.common.Constants;
import de.innovationgate.wga.config.ContentStore;

public class ContentStoreSetup {

    private final String dbKey;

    /**
     * Gets publisherOptions
     *
     * @return value of publisherOptions
     */
    public Map<String, String> getPublisherOptions() {
        return publisherOptions;
    }

    /**
     * Gets dbOptions
     *
     * @return value of dbOptions
     */
    public Map<String, String> getDbOptions() {
        return dbOptions;
    }

    private Map<String,String> publisherOptions = new HashMap<>();
    private Map<String,String> dbOptions = new HashMap<>();

    /**
     * Gets design
     *
     * @return value of design
     */
    public Object getDesign() {
        return Optional.ofNullable(design).orElseThrow(()-> new IllegalArgumentException("You must specify a design"));
    }

    private Object design;
    private String serverId;

    /**
     * Gets implClass
     *
     * @return value of implClass
     */
    public String getImplClass() {
        return implClass;
    }

    private String implClass;

    private ContentStoreSetup(final String key) {
        this.dbKey = key;
    }

    public static ContentStoreSetup key(String key) {
        return new ContentStoreSetup(key);
    }

    public ContentStoreSetup databaseImplClass(Class<? extends WGDatabaseCore> implClass) {
        this.implClass = implClass.getName();
        return this;
    }

    public ContentStoreSetup design(String designName) {
        this.design = designName;
        return this;
    }

    public ContentStoreSetup databaseServer(String serverId) {
        this.serverId = serverId;
        return this;
    }

    public ContentStoreSetup database(String dbServerId, String dbPath) {
        this.serverId = dbServerId;
        this.dbOptions.put(ContentStore.OPTION_PATH, dbPath);
        return this;
    }

    public ContentStoreSetup publisherOption(String name, String value) {
        this.publisherOptions.put(name, value);
        return this;
    }

    public ContentStoreSetup publisherOptions(Map<String,String> options) {
        this.publisherOptions.putAll(options);
        return this;
    }

    public ContentStoreSetup dbOption(String name, String value) {
        this.dbOptions.put(name, value);
        return this;
    }

    public ContentStoreSetup dbOptions(Map<String,String> options) {
        this.dbOptions.putAll(options);
        return this;
    }

    /**
     * Gets key
     *
     * @return value of key
     */
    public String getDbKey() {
        return Optional.of(dbKey).orElseThrow(()-> new IllegalArgumentException("You must specify a database key"));
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
