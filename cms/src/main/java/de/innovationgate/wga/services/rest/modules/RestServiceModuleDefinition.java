package de.innovationgate.wga.services.rest.modules;

import java.util.Locale;

import javax.servlet.ServletContext;

import de.innovationgate.wga.modules.KeyBasedModuleDefinition;
import de.innovationgate.wga.modules.ModuleDefinition;
import de.innovationgate.wga.modules.ModuleDependencyException;
import de.innovationgate.wga.modules.ModuleRegistry;
import de.innovationgate.wga.modules.ModuleType;
import de.innovationgate.wga.modules.OptionDefinitionsMap;
import de.innovationgate.wga.modules.RegistryAwareModuleDefinition;
import de.innovationgate.wga.modules.types.WGAWebServiceModuleType;
import de.innovationgate.wga.modules.types.WGAWebServiceProperties;
import de.innovationgate.wga.server.api.WGA;
import de.innovationgate.wga.services.rest.RestService;
import de.innovationgate.wgpublisher.WGACore;
import de.innovationgate.wgpublisher.services.WGAServiceServletConfig;

public class RestServiceModuleDefinition implements ModuleDefinition, KeyBasedModuleDefinition,
        RegistryAwareModuleDefinition {

    private ModuleRegistry _reg;

    public String getTitle(Locale locale) {
        return "OpenWGA REST/ROA web service";
    }

    public String getDescription(Locale locale) {
        return "A REST-ful web service with resource oriented architecture (ROA), serving and managing OpenWGA content as XML or JSON";
    }

    @Override
    public void injectRegistry(final ModuleRegistry registry) {
        _reg = registry;
    }

    public OptionDefinitionsMap getOptionDefinitions() {
        return null;
    }

    public Class<? extends ModuleType> getModuleType() {
        return WGAWebServiceModuleType.class;
    }

    public Class<?> getImplementationClass() {
        return RestService.class;
    }

    public void testDependencies() throws ModuleDependencyException {
    }

    public Object getProperties() {
        WGAWebServiceProperties props = new WGAWebServiceProperties() {
            
            @Override
            public WGAServiceServletConfig buildServletConfig(ServletContext servletContext, ModuleDefinition modDef) {
                
//                try {
                    WGA wga = (WGA) _reg.getContextObjects().get(WGA.class);
                    
                    WGAServiceServletConfig cfg = super.buildServletConfig(servletContext, modDef);
//                    cfg.setInitParameter("api.version", "1.0.0");
//                    cfg.setInitParameter("swagger.api.basepath", wga.databaseServer().getBaseURL() + "/services/rest/v1");
                    return cfg;
//                }
//                catch (WGException e) {
//                    throw new RuntimeException("Exception initializing Rest Service Config", e);
//                }
                
            }
            
        };
        return props;
    }
    
    @Override
    public String getRegistrationKey() {
        return "rest";
    }

}
