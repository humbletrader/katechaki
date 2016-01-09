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
 * This is an immutable wrapper around the value stored as the intermediate result of the group computations. 
 * It is used for SumGroupCalculator, CountGroupCalculator, MaxGroupCalculator and others. 
 * 
 * @author dragos balan
 *
 */
public class DefaultCalcIntermResult<E> implements CalcIntermResult<E> {
	
	/**
	 * the result 
	 */
	private final E result; 
	
	/**
	 * constructs this wrapper based on the value/result
	 * @param initialValue
	 */
	public DefaultCalcIntermResult(E initialValue){
		this.result = initialValue; 
	}
	
	/**
	 * getter for the result
	 */
	public E getResult(){
		return result; 
	}
}
