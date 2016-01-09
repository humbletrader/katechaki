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

import java.math.BigDecimal;

/**
 * This is an universal max group calculator. 
 * It accepts as new values any object and tries to convert them to BigDecimal ( by new BigDecimal(object.toString())). 
 * The newly constructed BigDecimal is then compared to the previous max so that a new max is obtained. 
 * 
 * @author dragos balan
 */
public class MaxGroupCalculator extends AbstractGroupCalculator<BigDecimal, DefaultCalcIntermResult<BigDecimal>, Object> {

	/**
	 * the default label
	 */
	public static final String LABEL = "Maximum"; 
	
	/**
	 * builds this group calculator with the default label
	 */
	public MaxGroupCalculator(){
		this(LABEL); 
	}
	
	/**
	 * builds this group calculator with a custom label
	 * 
	 * @param label	the label
	 */
	public MaxGroupCalculator(String label){
		super(label); 
	}
	
	/**
	 * returns a BigDecimal based on Double.MIN_VALUE
	 */
	public DefaultCalcIntermResult<BigDecimal> init() {
		return new DefaultCalcIntermResult<BigDecimal>(BigDecimal.valueOf(Double.MIN_VALUE));
	}
	
	/**
	 * checks whether the new value is lower than the previous result
	 */
	public DefaultCalcIntermResult<BigDecimal> compute(
			DefaultCalcIntermResult<BigDecimal> intermResult, Object newValue) {
		DefaultCalcIntermResult<BigDecimal> result = intermResult; 
		BigDecimal newValueAsBD = new BigDecimal(newValue.toString()); 
		if(intermResult.getResult().compareTo(newValueAsBD) < 0){
			result = new DefaultCalcIntermResult<BigDecimal>(newValueAsBD); 
		}
		return result;
	}

	

}
