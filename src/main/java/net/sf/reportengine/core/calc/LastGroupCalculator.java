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
 * This group calculator always stores the last value passed to the {@link #compute(DefaultCalcIntermResult, Object)} method
 * 
 * @author dragos balan
 */
public class LastGroupCalculator<T> extends AbstractGroupCalculator<T, DefaultCalcIntermResult<T>, T> {

	/**
	 * the default label
	 */
	public static final String LABEL = "Last"; 
	
	/**
	 * builds this group calculator with the default label
	 */
	public LastGroupCalculator(){
		this(LABEL); 
	}
	
	/**
	 * builds this group calculator with the custom label 
	 * @param label	the label
	 */
	public LastGroupCalculator(String label){
		super(label); 
	}
	
	/**
	 * returns an empty intermediate calculator result ( empty means that it contains a null value)
	 */
	public DefaultCalcIntermResult<T> init() {
		//TODO come back here when java 8 will be the target platform
		//and add an optional inside DefaultCalcIntermResult
		return new DefaultCalcIntermResult<T>(null); 
	}
	
	/**
	 * stores the last value passes to this method as @param newValue
	 * 
	 * @param intermResult	the previous result
	 * @param newValue		the new value	
	 */
	public DefaultCalcIntermResult<T> compute(
			DefaultCalcIntermResult<T> intermResult, T newValue) {
		return new DefaultCalcIntermResult<T>(newValue);
	}


}
