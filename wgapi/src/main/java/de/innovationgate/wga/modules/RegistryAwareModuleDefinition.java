/*******************************************************************************
 * Copyright 2009, 2010 Innovation Gate GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package de.innovationgate.wga.modules;

/**
 * Interface to be used by a {@link ModuleDefinition} that needs a reference to the {@link ModuleRegistry} to work.
 * The registry is injected by calling {@link #injectRegistry(ModuleRegistry)} when the definition is added to the registry (before calling any other method)
 */
public interface RegistryAwareModuleDefinition {
    
    public void injectRegistry(ModuleRegistry registry);

}