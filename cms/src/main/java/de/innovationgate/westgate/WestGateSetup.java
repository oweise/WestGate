package de.innovationgate.westgate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

import javax.enterprise.inject.Alternative;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import de.innovationgate.wga.config.ContentStore;
import de.innovationgate.wga.config.DatabaseServer;
import de.innovationgate.wga.config.Design;
import de.innovationgate.wga.config.WGAConfiguration;
import de.innovationgate.wga.config.WGAConfigurationFactory;
import de.innovationgate.wgpublisher.WGAVersion;

@Alternative
public class WestGateSetup {

     private List<DatabaseServerSetup> databaseServerSetupList = new ArrayList<>();
     private List<ContentStoreSetup> contentStoreSetupList = new ArrayList<>();


    public WestGateSetup addServer(DatabaseServerSetup databaseServerSetup) {
        databaseServerSetupList.add(databaseServerSetup);
        return this;
    }

    public WestGateSetup addContentStore(ContentStoreSetup contentStoreSetup) {
        contentStoreSetupList.add(contentStoreSetup);
        return this;
    }

    private DatabaseServer buildServer(DatabaseServerSetup databaseServerSetup) {

        DatabaseServer databaseServer = new DatabaseServer();
        databaseServer.setUid(databaseServerSetup.getServerId());

        Config config = ConfigProvider.getConfig();
        databaseServer.setImplClassName(config.getValue("cms.databaseServer." + databaseServer.getUid() + ".implClass", String.class));
        gatherOptions(databaseServer.getOptions(), "cms.databaseServer." + databaseServer.getUid() + ".options.");

        return databaseServer;
    }

    private void gatherOptions(final Map<String,String> options, final String configPrefix) {

        Config config = ConfigProvider.getConfig();
        StreamSupport.stream(config.getPropertyNames().spliterator(), true)
                .filter(name -> name.startsWith(configPrefix))
                .forEach(name -> {
                    String optionName = name.substring(configPrefix.length());
                    options.put(optionName, config.getValue(name, String.class));
                });
    }

    private ContentStore buildContentStore(ContentStoreSetup contentStoreSetup) {

        ContentStore contentStore = new ContentStore();
        contentStore.setKey(contentStoreSetup.getDbKey());

        File designDir = new File("cms/designs/" + contentStoreSetup.getDesign());
        Design design = new Design();
        design.setName(designDir.getName());
        design.setSource("fs");
        contentStore.setDesign(design);
        contentStore.setDomain("default");

        contentStore.setDbServer(contentStoreSetup.getServerId());

        contentStore.setImplClassName(contentStoreSetup.getImplClass());
        ConfigProvider.getConfig()
                .getOptionalValue("cms.contentstore." + contentStore.getKey() + ".implClass", String.class)
                .ifPresent(value -> contentStore.setImplClassName(value));

        contentStore.getPublisherOptions().putAll(contentStoreSetup.getPublisherOptions());
        gatherOptions(contentStore.getPublisherOptions(), "cms.contentstore." + contentStore.getKey() + ".publisher.options.");

        contentStore.getDatabaseOptions().putAll(contentStoreSetup.getDbOptions());
        gatherOptions(contentStore.getDatabaseOptions(), "cms.contentstore." + contentStore.getKey() + ".db.options.");

        return contentStore;
    }

    public WGAConfiguration buildWgaConfig() {

        WGAConfiguration wgaConfiguration = new WGAConfigurationFactory().createDefaultConfig(WGAVersion.toCsConfigVersion());

        databaseServerSetupList.forEach(dbServerSetup -> wgaConfiguration.add(buildServer(dbServerSetup)));
        contentStoreSetupList.forEach(contentStoreSetup -> wgaConfiguration.add(buildContentStore(contentStoreSetup)));

        return wgaConfiguration;
    }
}
