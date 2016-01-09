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
package net.sf.reportengine.core.calc;

import org.apache.commons.lang.math.NumberUtils;

/**
 * Universal count calculator.
 * Counts the objects passed through {@code #compute(DefaultCalcIntermResult, Object)} method using the an immutable
 * {@code DefaultCalcIntermResult} object. 
 * 
 * @author dragos balan
 */
public class CountGroupCalculator extends AbstractGroupCalculator<Integer, DefaultCalcIntermResult<Integer>, Object> {
	
	/**
	 * the label
	 */
	public static final String LABEL = "Count"; 
	
	/**
	 * constructor (sets the label = "Count")
	 */
	public CountGroupCalculator(){
		this(LABEL); 
	}
	
	/**
	 * Use this constructor when you want to control the label
	 * 
	 * @param label	the label
	 */
	public CountGroupCalculator(String label){
		super(label); 
	}
	
	/**
	 * initializes the counter to zero
	 */
	public DefaultCalcIntermResult<Integer> init() {
		return new DefaultCalcIntermResult<Integer>(NumberUtils.INTEGER_ZERO);
	}
	
	/**
	 * increases the count 
	 */
	public DefaultCalcIntermResult<Integer> compute(DefaultCalcIntermResult<Integer> intermResult, Object newValue) {
		return new DefaultCalcIntermResult<Integer>(Integer.valueOf(intermResult.getResult().intValue()+ 1)); 
	}
}
