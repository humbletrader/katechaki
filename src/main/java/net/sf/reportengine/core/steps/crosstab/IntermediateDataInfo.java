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
 * @author Administrator
 *
 */
public class IntermediateDataInfo implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6779896030883957024L;
	
	private Object value; 
	private int[] positionRelativeToHeader; 
	
	public IntermediateDataInfo(Object value, int... position){
		this.value = value; 
		this.positionRelativeToHeader = position;
	}
	
	public Object getValue(){
		return value; 
	}
	
	public int[] getPositionRelativeToHeaderRows(){
		return positionRelativeToHeader;
	}
	
	public String toString(){
		return ""+value+"["+Arrays.toString(positionRelativeToHeader)+"]";
	}
	
	public boolean equals(Object another){
		boolean result = false; 
		if(another instanceof IntermediateDataInfo){
			IntermediateDataInfo anotherAsICDI = (IntermediateDataInfo)another;
			result = anotherAsICDI.getValue().equals(value) && Arrays.equals(positionRelativeToHeader, anotherAsICDI.getPositionRelativeToHeaderRows());
		}
		return result; 
	}
}