/**
 * Copyright (C) 2006 Dragos Balan (dragos.balan@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.reportengine.core.algorithm;

import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.util.StepIOKeys;


/**
 * The algorithm context is a local-to-the-algorithm storage used for sharing information among algorithm steps. 
 *
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public interface AlgoContext {
    
	/**
	 * adds the result of a step into the context
	 * 
	 * @param stepResult
	 */
	public void add(StepResult stepResult);
	
    /**
     * adds the pair (key, object) in the report context
     * 
     * @param key		the identifier of the given object
     * @param obj		the object to be registered
     */
    public void set(StepIOKeys key, Object obj);
    
    /**
     * gets a value from the context
     * @param key	the indentifier 
     * @return		the requested value
     */
    public Object get(StepIOKeys key);
}
