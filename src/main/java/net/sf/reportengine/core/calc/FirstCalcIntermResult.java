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
 * @author dragos balan
 *
 */
public class FirstCalcIntermResult<T> extends DefaultCalcIntermResult<T> {
	
	/**
	 * flat for the first value passed to the calculator
	 */
	private final boolean isFirst; 
	
	
	/**
	 * 
	 * @param initialValue
	 * @param isFirst
	 */
	public FirstCalcIntermResult(T initialValue, boolean isFirst){
		super(initialValue); 
		this.isFirst = isFirst; 
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isFirst(){
		return this.isFirst; 
	}
}
