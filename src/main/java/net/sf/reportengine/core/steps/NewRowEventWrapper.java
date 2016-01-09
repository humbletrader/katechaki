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
package net.sf.reportengine.core.steps;

import java.io.Serializable;

import net.sf.reportengine.core.algorithm.NewRowEvent;

/**
 * 
 * helper class for serialization. 
 * It contains a isLast member helping with de-serialization (detect end of the objectStream)
 * 
 * @author dragos balan
 *
 */
public final class NewRowEventWrapper implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1530225702536578647L;
	
	/**
	 * 
	 */
	private boolean isLast = false; 
	
	/**
	 * 
	 */
	private NewRowEvent newRowEvent; 
	
	
	/**
	 * 
	 * @param newRow
	 * @param isLast
	 */
	public NewRowEventWrapper(NewRowEvent newRow, boolean isLast){
		this.newRowEvent = newRow; 
		this.isLast = isLast; 
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isLast(){
		return isLast; 
	}
	
	/**
	 * 
	 * @return
	 */
	public NewRowEvent getNewRowEvent(){
		return newRowEvent; 
	}
	
	
	public boolean equals(Object another){
		boolean result = false; 
		if(another instanceof NewRowEventWrapper){
			NewRowEventWrapper anotherAsNREW = (NewRowEventWrapper)another; 
			return this.isLast() == anotherAsNREW.isLast() && this.newRowEvent.equals(anotherAsNREW.getNewRowEvent()); 
		}
		
		return result; 
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder("NewRowEventWrapper[isLast="); 
		builder.append(isLast).append(", newRowEvent=").append(newRowEvent.toString());
		return builder.toString(); 
	}
	
	public int hashCode(){
		int result = 17; 
		result = 31 * result + (isLast ? 0 : 1); 
		result = 31 * result + newRowEvent.hashCode(); 
		return result; 
	}
}
