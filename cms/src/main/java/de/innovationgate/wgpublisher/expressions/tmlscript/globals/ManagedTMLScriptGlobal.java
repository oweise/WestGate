/*******************************************************************************
 * Copyright 2009, 2010 Innovation Gate GmbH. All Rights Reserved.
 * 
 * This file is part of the OpenWGA server platform.
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

package de.innovationgate.wgpublisher.expressions.tmlscript.globals;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import de.innovationgate.webgate.api.WGException;
import de.innovationgate.wga.server.api.WGA;
import de.innovationgate.wgpublisher.expressions.tmlscript.ManagedTMLScriptGlobalDefinition;
import de.innovationgate.wgpublisher.expressions.tmlscript.TMLScriptGlobal;
import de.innovationgate.wgpublisher.so.ScopeObjectRegistry.ScopeObject;

public class ManagedTMLScriptGlobal extends TMLScriptGlobal {

    public ManagedTMLScriptGlobal(String name, Object ref) {
        super(name, ref);
    }

    @Override
    public Object provide(WGA wga) {
        ManagedTMLScriptGlobalDefinition managedGlobal = (ManagedTMLScriptGlobalDefinition) getRef();
        try {
            ScopeObject so = managedGlobal.provideGlobal(wga, true);
            if (so != null) {
                return so.getObject();
            }
            throw new EvaluatorException("Managed global '" + getName() + "' could not be instantiated");
        }
        catch (WGException e) {
            throw Context.throwAsScriptRuntimeEx(e);
        }
    }
    
    @Override
    public void afterProvisioning(WGA wga) {
        ManagedTMLScriptGlobalDefinition managedGlobal = (ManagedTMLScriptGlobalDefinition) getRef();
        try {
            ScopeObject so = managedGlobal.provideGlobal(wga, true);
            if (so != null) {
                so.afterUsage();
            }
        }
        catch (WGException e) {
            throw Context.throwAsScriptRuntimeEx(e);
        }
    }
    
    

}
