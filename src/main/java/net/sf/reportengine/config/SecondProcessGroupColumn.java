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
package net.sf.reportengine.config;

import java.util.List;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.crosstab.IntermOriginalGroupValuesList;

/**
 * This is only for internal use.
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class SecondProcessGroupColumn implements GroupColumn {
	
	/**
	 * the original group column on which this new column is based
	 */
	private GroupColumn originalGroupColumn; 
	
	/**
	 * 
	 * @param anotherGroupColumn
	 */
	public SecondProcessGroupColumn(GroupColumn anotherGroupColumn){
		this.originalGroupColumn = anotherGroupColumn;  
	}
	

	/* (non-Javadoc)
	 * @see net.sf.reportengine.config.GroupColumn#getValue(net.sf.reportengine.core.algorithm.NewRowEvent)
	 */
	public Object getValue(NewRowEvent newRowEvent) {
		//according to the contract the first object in each row array is an 
		//instance of IntermOrigGroupValuesList
		List<Object> newRow = newRowEvent.getInputDataRow(); 
		IntermOriginalGroupValuesList intermGroupValues = (IntermOriginalGroupValuesList)newRow.get(0); 
		
		//we get the group value according to the group level
		return intermGroupValues.getGroupValues().get(getGroupingLevel()); 
	}


	public String getHeader() {
		return originalGroupColumn.getHeader(); 
	}


	public int getGroupingLevel() {
		return originalGroupColumn.getGroupingLevel();
	}


	public String getFormattedValue(Object object) {
		return originalGroupColumn.getFormattedValue(object);
	}


	public HorizAlign getHorizAlign() {
		return originalGroupColumn.getHorizAlign(); 
	}
	
	public VertAlign getVertAlign() {
		return originalGroupColumn.getVertAlign(); 
	}
	
	public boolean showDuplicates(){
		return originalGroupColumn.showDuplicates(); 
	}
	
	public SortType getSortType(){
		return originalGroupColumn.getSortType(); 
	}
}
