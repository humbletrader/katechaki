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
package net.sf.reportengine.util;

/**
 * This is a utility enumeration for default boolean values which might interfere with the user's choice. 
 * Obviously, the user have has always priority
 * 
 * Example: 
 * 		A flat report doesn't show by default the totals (showTotals = FALSE_NOT_REQUESTED_BY_USER) 
 * 		The user adds a dataColumn (with a groupCalculator) -> the report builder decides it can show totals (showTotals = TRUE_NOT_REQUESTED_BY_USER)
 * 		The user wants to hide the totals (showTotals = TRUE_REQUESTED_BY_USER)
 * 		The user adds another dataColumn with a groupCalculator -> the report builder is blocked to the previously set value
 * 		
 * @author dragos balan
 *
 */
public enum UserProvidedBoolean {
	
	//this is the default true without being provided by the user
	TRUE_NOT_PROVIDED_BY_USER(true, false), 
	
	//default false without being provided by the user
	FALSE_NOT_PROVIDED_BY_USER(false, false), 
	
	//true value provided by the user
	TRUE_PROVIDED_BY_USER(true, true), 
	
	//false value provided by the user
	FALSE_PROVIDED_BY_USER(false, true);
	
	/**
	 * this is the boolean value 
	 */
	private boolean value; 
	
	/**
	 * flags the value as being provided by the user (true) or being the default value provided by this api
	 */
	private boolean isValueProvidedByUser; 
	
	/**
	 * 
	 * @param value
	 * @param isProvidedByUser
	 */
	private UserProvidedBoolean(boolean value, boolean isProvidedByUser){
		this.value = value; 
		this.isValueProvidedByUser = isProvidedByUser; 
	}
	
	public boolean getValue(){
		return value; 
	}
	
	public boolean isValueProvidedByUser(){
		return isValueProvidedByUser; 
	}
}
