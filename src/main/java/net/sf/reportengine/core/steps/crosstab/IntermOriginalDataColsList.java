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
package net.sf.reportengine.core.steps.crosstab;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class holds values belonging to data columns in the final report (declared as data columns by the user)
 * 
 * the order of the values is the same as the order of data columns declared by the user
 * 
 * @author dragos balan
 * @since 0.4
 */
public class IntermOriginalDataColsList implements Serializable {
	
	/**
	 * serial version
	 */
	private static final long serialVersionUID = -759637117853620406L;
	
	/**
	 * the values list
	 */
	private List<Object> dataValues; 
	
	/**
	 *  the one and only constructor
	 */
	public IntermOriginalDataColsList(){
		this.dataValues = new ArrayList<Object>();
	}
	
	/**
	 * add the provided value to the list
	 * 
	 * @param dataValue
	 */
	public void addDataColumnValue(Object dataValue){
		dataValues.add(dataValue); 
	}
	
	/**
	 * clears the data values list
	 */
	public void empty(){
		dataValues.clear(); 
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Object> getDataValues(){
		return dataValues; 
	}
	
	@Override
	public boolean equals(Object another){
		boolean result = false; 
		if(another instanceof IntermOriginalDataColsList){
			IntermOriginalDataColsList anotherAsIntermDataColumnList = (IntermOriginalDataColsList)another; 
			result = dataValues.equals(anotherAsIntermDataColumnList.getDataValues()); 
		}
		return result; 
	}
	
	@Override
	public String toString(){
		StringBuilder result = new StringBuilder("IntermOriginalCtData["); 
		result.append(dataValues.toString());
		result.append("]"); 
		return result.toString(); 
	}
}
