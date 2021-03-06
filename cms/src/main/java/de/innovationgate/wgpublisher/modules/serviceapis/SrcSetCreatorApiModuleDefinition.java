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

package de.innovationgate.wgpublisher.modules.serviceapis;

import java.util.Locale;

import de.innovationgate.utils.WGUtils;
import de.innovationgate.wga.modules.KeyBasedModuleDefinition;
import de.innovationgate.wga.modules.LocalisationBundleLoader;
import de.innovationgate.wga.modules.ModuleDefinition;
import de.innovationgate.wga.modules.ModuleDependencyException;
import de.innovationgate.wga.modules.ModuleType;
import de.innovationgate.wga.modules.OptionDefinitionsMap;
import de.innovationgate.wga.modules.types.ServiceApiProperties;
import de.innovationgate.wga.modules.types.ServiceApiType;
import de.innovationgate.wga.server.api.WGA;
import de.innovationgate.wgpublisher.webtml.utils.SrcSetCreator;

public class SrcSetCreatorApiModuleDefinition implements ModuleDefinition, KeyBasedModuleDefinition {
    
    protected LocalisationBundleLoader _bundleLoader = new LocalisationBundleLoader(WGUtils.getPackagePath(getClass()) + "/apis", getClass().getClassLoader());

    @Override
    public String getTitle(Locale locale) {
        return _bundleLoader.getBundle(locale).getString("scrset.title");
    }

    @Override
    public String getDescription(Locale locale) {
        return _bundleLoader.getBundle(locale).getString("scrset.description");
    }

    @Override
    public OptionDefinitionsMap getOptionDefinitions() {
        return null;
    }

    @Override
    public Class<? extends ModuleType> getModuleType() {
        return ServiceApiType.class;
    }

    @Override
    public Class<? extends Object> getImplementationClass() {
        return SrcSetCreator.class;
    }

    @Override
    public void testDependencies() throws ModuleDependencyException {
    }

    @Override
    public Object getProperties() {
        ServiceApiProperties props = new ServiceApiProperties();
        props.setImplementable(false);
        props.setDefaultImplementation(SrcSetCreator.class);
        return props;
    }

    @Override
    public String getRegistrationKey() {
        return SrcSetCreator.class.getName();
    }

}
