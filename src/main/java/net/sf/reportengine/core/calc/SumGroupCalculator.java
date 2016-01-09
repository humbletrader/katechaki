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

import java.math.BigDecimal;

/**
 * computes the sum of the values passed through the compute method. 
 * This implementation is not optimized because 
 * 	1.it uses the BigDecimal as result type 
 * 	2.it tries to work with any kind of object by converting it to BigDecimal (i.e new BigDecimal(object.toString())) 
 * 
 * @author dragos balan
 *
 */
public class SumGroupCalculator extends AbstractGroupCalculator<BigDecimal, DefaultCalcIntermResult<BigDecimal>,  Object> {
	
	
	/**
	 * the default label
	 */
	public static final String LABEL = "Total";
	
	/**
	 * builds this group calculator with the default label
	 */
	public SumGroupCalculator(){
		this(LABEL); 
	}
	
	/**
	 * builds this group calculator with the given label
	 * 
	 * @param label	the label
	 */
	public SumGroupCalculator(String label){
		super(label); 
	}
	
	/**
	 * initializes with zero the intermediate result ( to start the sum computation)
	 */
	public DefaultCalcIntermResult<BigDecimal> init() {
		return new DefaultCalcIntermResult<BigDecimal>(BigDecimal.ZERO);
	}
	
	/**
	 * adds the new value to the previous intermediate result by converting it to BigDecimal.
	 */
	public DefaultCalcIntermResult<BigDecimal> compute(	DefaultCalcIntermResult<BigDecimal> intermResult, 
														Object newValue) {
		return new DefaultCalcIntermResult<BigDecimal>(
				intermResult.getResult().add(new BigDecimal(newValue.toString())));
	}

	

	
	
	
}
