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
package net.sf.reportengine.core.calc;


/**
 * utility collection of calculators 
 * 
 * @author dragos balan 
 */
public final class GroupCalculators {
    
    /**
     * 
     */
    public static final SumGroupCalculator SUM = new SumGroupCalculator(); 
	
    /**
     * 
     */
    public static final CountGroupCalculator COUNT = new CountGroupCalculator(); 
    
    
    /**
     * 
     */
    public static final AvgGroupCalculator AVG = new AvgGroupCalculator(); 
    
    /**
     * 
     */
    public static final MinGroupCalculator MIN = new MinGroupCalculator(); 
    
    /**
     * 
     */
    public static final MaxGroupCalculator MAX = new MaxGroupCalculator(); 
    
    /**
     * 
     */
    public static final FirstGroupCalculator<Object> FIRST = new FirstGroupCalculator<Object>(); 
    
    /**
     * 
     */
    public static final LastGroupCalculator<Object> LAST = new LastGroupCalculator<Object>();
    
    /**
     * just to prevent inheritance
     */
    private GroupCalculators(){
    }
}
