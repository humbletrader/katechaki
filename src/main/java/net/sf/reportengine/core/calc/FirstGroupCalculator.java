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

/**
 * This calculator will return the first value passed after calling init. 
 * No matter how many times you call {@link #compute(FirstCalcIntermResult, Object)} it will always 
 * return the first value
 * 
 * @author dragos balan
 */
public class FirstGroupCalculator<T> extends AbstractGroupCalculator<T, FirstCalcIntermResult<T>, T> {
	
	/**
	 * the default label
	 */
	public static final String LABEL = "First"; 
	
	/**
	 * builds this group calculator with the default label
	 */
	public FirstGroupCalculator(){
		this(LABEL); 
	}
	
	/**
	 * builds this group calculator with a custom label
	 * @param label	the label
	 */
	public FirstGroupCalculator(String label){
		super(label); 
	}
	
	/**
	 * returns an empty (null) element with the isFirst flag = true
	 */
	public FirstCalcIntermResult<T> init() {
		return new FirstCalcIntermResult<T>(null, true);
	}

	/**
	 * if the previous result is first then it stores the new value inside the intermediate result. 
	 * otherwise it returns the previous intermediate result. 
	 */
	public FirstCalcIntermResult<T> compute(FirstCalcIntermResult<T> intermResult, T newValue) {
		FirstCalcIntermResult<T> result = intermResult; 
		if(intermResult.isFirst()){
			result = new FirstCalcIntermResult<T>(newValue, false); 
		}
		return result;
	}
}
