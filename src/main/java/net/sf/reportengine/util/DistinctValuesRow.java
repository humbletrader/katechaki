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

import java.util.ArrayList;

/**
 * 
 * 
 * @author dragos balan
 * @since 0.4
 */
public class DistinctValuesRow {
	
	
	/**
	 * the distinct values 
	 */
	private ArrayList<Object> distinctValues;
	
	/**
	 * 
	 * @param level
	 */
	public DistinctValuesRow(){
		this.distinctValues = new ArrayList<Object>(); 
	}
	

	/**
	 * @return the distinctValues
	 */
	public ArrayList<Object> getDistinctValues() {
		return distinctValues;
	}
	
	
	/**
	 * tries to add a distinct value to the list. 
	 * 
	 * @param value		the new distinct value to be added in the list
	 * @return			the index of the value into the list
	 */
	public int addDistinctValueIfNotExists(Object value){
		int index = distinctValues.indexOf(value);
		if(index < 0){
			//the item does not exist in the list
			distinctValues.add(value);
			index = distinctValues.size()-1;
		}
		return index; 
	}
	
	
	public String toString(){
		StringBuffer result = new StringBuffer(); 
		result.append("HeaderDistinctValuesRow[");
		result.append(distinctValues.toString());
		result.append("]");
		return result.toString();
	}
}
