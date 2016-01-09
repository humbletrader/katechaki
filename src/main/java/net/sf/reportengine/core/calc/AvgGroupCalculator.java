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
import java.math.MathContext;

/**
 * Universal average calculator. 
 * This calculator is not optimized because: 
 * <ul>
 * <li>it accepts any values (i.e java.lang.Object objects and tries to convert them into BigDecimals 
 * (by using the conversion  new BigDecimal(object.toString()). </li>
 * <li>The whole internal computation is based on BigDecimals. </li>
 * </ul>
 * You are strongly encouraged to write your own avg calculator based on your needs and using your own types . 
 * 
 * @author dragos balan
 *
 */
public class AvgGroupCalculator extends AbstractGroupCalculator<BigDecimal, AvgCalcIntermResult<BigDecimal>, Object> {
	
	
	/**
	 * the label for this calculator
	 */
	public static final String LABEL = "Average";
	
	/**
	 * the math context to be used when executing BigDecimal operations
	 */
	private final MathContext mathContext; 
	
	/**
	 * Default constructor (
	 *  label = "Average" 
	 *  number of exact decimals = 32 )
	 */
	public AvgGroupCalculator(){
		this(LABEL);
	}
	
	/**
	 * average group calculator constructor on which you can set the label 
	 * ( number of exact decimal = 32)
	 * 
	 * @param label the label to be displayed on each totals row
	 */
	public AvgGroupCalculator(String label){
		this(label, MathContext.DECIMAL32); 
	}
	
	/**
	 * use this constructor if you want to control the number exact decimals
	 * @param label			the label to be displayed on each total row 
	 * @param mathContext	the math context for BigDecimal operations
	 */
	public AvgGroupCalculator(String label, MathContext mathContext){
		super(label); 
		this.mathContext = mathContext; 
	}
	
	
	
	/**
	 * init method required by the GroupCalculator
	 */
	public AvgCalcIntermResult<BigDecimal> init() {
		return new AvgCalcIntermResult<BigDecimal>(BigDecimal.ZERO, 0); 
	}

	/**
	 * this compute method is based on the following formula: 
	 *  AVG[n+1] = (n * AVG[n] + element[n+1] ) / (n+1)
	 */
	public AvgCalcIntermResult<BigDecimal> compute(	AvgCalcIntermResult<BigDecimal> intermResult, 
													Object newValue) {
		BigDecimal newValueAsBD = new BigDecimal(newValue.toString()); 
		int count = intermResult.getCount(); 
		BigDecimal avg = intermResult.getResult()
				.multiply(new BigDecimal(count),mathContext)
				.add(newValueAsBD, mathContext)
				.divide(new BigDecimal(count+1), mathContext);  
		return new AvgCalcIntermResult<BigDecimal>(avg, count+1);
	}
}
