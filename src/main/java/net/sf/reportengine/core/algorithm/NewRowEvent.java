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
package net.sf.reportengine.core.algorithm;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author dragos balan
 * @version $Revision$
 * $log$
 */
public final class NewRowEvent  implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -1450602765872099843L;
	
	/**
	 * 
	 */
	private final List<Object> inputData;
    
	/**
	 * 
	 * @param inputDataRow
	 * @deprecated 
	 */
	public NewRowEvent(Object[] inputDataRow){
		this(Arrays.asList(inputDataRow)); 
	}
	
	/**
	 * 
	 * @param inputDataRow
	 */
    public NewRowEvent(List<Object> inputDataRow){
        this.inputData = inputDataRow; 
    }
    
    /**
     * 
     * @return
     */
    public List<Object> getInputDataRow(){
        return inputData;
    }
    
    /**
     * 
     */
    public String toString(){
        return new StringBuilder("NewRowEvent:").append(inputData).toString();
    }
    
    /**
     * 
     */
    public boolean equals(Object another){
    	boolean result = false; 
    	if(another instanceof NewRowEvent){
    		NewRowEvent anotherAsNRE = (NewRowEvent)another; 
    		result = inputData.equals(anotherAsNRE.getInputDataRow());
    	}
    	return result; 
    }
    
    public int hashCode(){
    	return inputData.hashCode(); 
    }
}