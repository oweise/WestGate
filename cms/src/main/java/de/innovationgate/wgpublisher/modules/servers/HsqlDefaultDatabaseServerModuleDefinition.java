/*******************************************************************************
 * Copyright 2009, 2010 Innovation Gate GmbH. All Rights Reserved.
 * 
 * This file is part of the OpenWGA databaseServer platform.
 * 
 * OpenWGA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * In addition, a special exception is granted by the copyright holders
 * of OpenWGA called "OpenWGA plugin exception". You should have received
 * a copy of this exception along with OpenWGA in file COPYING.
 * If not, see <http://www.openwga.com/gpl-plugin-exception>.
 * 
 * OpenWGA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with OpenWGA in file COPYING.
 * If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package de.innovationgate.wgpublisher.modules.servers;

import java.util.Locale;

import de.innovationgate.utils.WGUtils;
import de.innovationgate.webgate.api.jdbc.modules.dbs.HsqlDatabaseServerModuleDefinition;
import de.innovationgate.webgate.api.modules.servers.DatabaseServerProperties;
import de.innovationgate.wga.config.WGAConfiguration;
import de.innovationgate.wga.modules.LocalisationBundleLoader;
import de.innovationgate.wga.modules.ModuleDefinition;
import de.innovationgate.wga.modules.OptionDefinitionsMap;
import de.innovationgate.wgpublisher.servers.HsqlDefaultDatabaseServer;

public class HsqlDefaultDatabaseServerModuleDefinition extends HsqlDatabaseServerModuleDefinition implements ModuleDefinition {
    
    public Class<? extends Object> getImplementationClass() {
        return HsqlDefaultDatabaseServer.class;
    }

    public OptionDefinitionsMap getOptionDefinitions() {
        OptionDefinitionsMap options = new OptionDefinitionsMap();
        return options;
    }

    public Object getProperties() {
        DatabaseServerProperties props = new DatabaseServerProperties();
        props.setSingleton(true);
        props.setSingletonTitle("defaultserver.title");
        props.setSingletonTitleBundleLoader(_bundleLoader);
        props.setSingletonUID("hsqlEmbedded");
        return props;
    }
   
    

}
