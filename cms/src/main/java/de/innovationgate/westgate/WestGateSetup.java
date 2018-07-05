package de.innovationgate.westgate;

import java.io.IOException;
import java.util.Map;

import de.innovationgate.utils.Base64;
import de.innovationgate.webgate.api.WGFactory;
import de.innovationgate.wga.config.WGAConfiguration;
import de.innovationgate.wga.config.WGAConfigurationFactory;
import de.innovationgate.wga.modules.ModuleDefinition;
import de.innovationgate.wga.modules.ModuleRegistry;
import de.innovationgate.wga.modules.options.OptionDefinition;
import de.innovationgate.wga.modules.options.PasswordOptionEncoder;
import de.innovationgate.wga.modules.types.ContentDatabasePublisherOptionsModuleType;
import de.innovationgate.wga.modules.types.ContentStorePublisherOptionsModuleType;
import de.innovationgate.wga.modules.types.FileAnnotatorModuleType;
import de.innovationgate.wga.modules.types.FilterConfigModuleType;
import de.innovationgate.wga.modules.types.HTMLHeadInclusionModuleType;
import de.innovationgate.wga.modules.types.PasswordEncodingType;
import de.innovationgate.wga.modules.types.WGAServerOptionsModuleType;
import de.innovationgate.wga.modules.types.WGAWebServiceModuleType;
import de.innovationgate.wga.server.api.WGA;
import de.innovationgate.wgpublisher.WGACore;
import de.innovationgate.wgpublisher.WGAModuleRegistry;
import de.innovationgate.wgpublisher.WGAVersion;
import de.innovationgate.wgpublisher.modules.poptions.ContentDatabasePublisherOptionsCollector;
import de.innovationgate.wgpublisher.modules.poptions.ContentStorePublisherOptionsCollector;

public class WestGateSetup {

    private final WGAConfiguration wgaConfiguration;
    private ModuleRegistry moduleRegistry;

    public WestGateSetup() {
        wgaConfiguration = new WGAConfigurationFactory().createDefaultConfig(WGAVersion.toCsConfigVersion());
    }

    public WGAConfiguration build() {
        return wgaConfiguration;
    }


}
