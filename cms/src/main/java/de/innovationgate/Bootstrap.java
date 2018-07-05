package de.innovationgate;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;

import de.innovationgate.westgate.WestGateSetup;
import de.innovationgate.wga.config.WGAConfiguration;

@ApplicationScoped
@Default
public class Bootstrap {

    @Produces
    @Default
    @ApplicationScoped
    public WGAConfiguration buildConfiguration() {
        return new WestGateSetup().build();
    }

}
