package de.bannkreis.westgate.server;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;

import de.innovationgate.webgate.api.hsql.WGDatabaseImpl;
import de.innovationgate.westgate.ContentStoreSetup;
import de.innovationgate.westgate.WestGateSetup;

@ApplicationScoped
@Default
public class Bootstrap {

    @Produces
    @Default
    @ApplicationScoped
    public WestGateSetup buildConfiguration() {
        return new WestGateSetup().addContentStore(ContentStoreSetup
                .key("test")
                .design("test")
                .databaseImplClass(WGDatabaseImpl.class)
                .database("hsqlEmbedded", "test"));
    }

}
