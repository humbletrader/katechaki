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
import java.util.Arrays;

/**
 * @author dragos balan
 * @since 0.4
 */
public class IntermediateTotalInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8034845051062664366L;
	
	
	private Object computedValue;
	
	private int[] positionRelativeToHeader; 
	
	
	/**
	 * this represents the header distinct values for which this total is computed
	 */
	private Object[] distinctValues; //TODO this is just for debug. please remove
	
	/**
	 * 
	 * @param computedValue
	 * @param position
	 * @param distinctValues
	 */
	public IntermediateTotalInfo(	Object computedValue, 
									int[] position, 
									Object[] distinctValues){
		
		this.computedValue = computedValue; 
		this.positionRelativeToHeader = position; 
		this.distinctValues = distinctValues; 
	}
	
	public Object getComputedValue() {
		return computedValue;
	}

	public void setValue(Object computedValue) {
		this.computedValue = computedValue;
	}
	
	
	public int[] getPositionRelativeToHeader(){
		return positionRelativeToHeader; 
	}
	
	public boolean equals(Object another){
		boolean result = false; 
		if(another instanceof IntermediateTotalInfo){
			IntermediateTotalInfo anotherAsITI = (IntermediateTotalInfo)another; 
			result = computedValue.equals(anotherAsITI.getComputedValue()) 
					&& Arrays.equals(this.positionRelativeToHeader, anotherAsITI.getPositionRelativeToHeader());
		}
		return result; 
	}
	
	public String toString(){
		StringBuffer result = new StringBuffer("Total");
		result.append("[value=").append(computedValue);
		result.append(", position=");
		result.append(Arrays.toString(positionRelativeToHeader));
		result.append(",distinctValues=");
		result.append(Arrays.toString(distinctValues)); 
		result.append("]");
		return result.toString();
	}
	
}
