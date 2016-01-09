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

import java.util.EnumMap;

import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.util.StepIOKeys;

/**
 * this class holds important values of the current algorithm. 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 */
public class DefaultAlgorithmContext implements AlgoContext{
    
	/**
     * an enumMap containing the context data
     */
    private EnumMap<StepIOKeys, Object> contextData;
    
    /**
     * constructor of the class
     */
    public DefaultAlgorithmContext(){
        contextData = new EnumMap<StepIOKeys, Object>(StepIOKeys.class);
    }
    
    /**
     * gets the value whithin the context having the specified identifier
     * 
     * @return the object associated with the key
     */
    public Object get(StepIOKeys key){
        return contextData.get(key);
    }
    
    /**
     * @see net.sf.reportengine.core.algorithm.AlgoContext#get(String)
     */
    public void set(StepIOKeys key, Object value){
        contextData.put(key,value);
    }
    
    /**
     * 
     */
    public void add(StepResult stepResult){
    	contextData.put(stepResult.getKey(), stepResult.getValue()); 
    }
}
