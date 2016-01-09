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
/**
 * 
 */
package net.sf.reportengine.core.algorithm.steps;

import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;

/**
 * <p>
 *   Definition interface for an algorithm init step
 * </p>
 * @author dragos balan(dragos.balan@gmail.com)
 * @since 0.2
 */
public interface AlgorithmInitStep<U> {
    
    /**
     * this method is called only once for a report 
     * and represents the construction step where you can define the keys and 
     * values used inside the execute method.
     * <b>Warning: this is the only place where you can add keys to the report context
     *  
     * @param algoInput		the input parameters 	
     */
    public StepResult<U> init(StepInput input);
    
}