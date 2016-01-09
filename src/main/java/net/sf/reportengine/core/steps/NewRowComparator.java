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

import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import net.sf.reportengine.config.AbstractDataColumn;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.algorithm.NewRowEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dragos balan
 *
 */
public class NewRowComparator implements Comparator<NewRowEvent> {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(NewRowComparator.class);
	
	/**
	 * 
	 */
	private List<GroupColumn> groupCols; 
	
	
	/**
	 * 
	 */
	private SortedSet<DataColumn> dataColsHavingOrdering = new TreeSet<DataColumn>(
			new Comparator<DataColumn>(){
				public int compare(DataColumn col1, DataColumn col2) {
					int result = 0;//they are equal
					if(col1.getSortLevel() > col2.getSortLevel()){
						result = 1;//the higher the order level, the lower the ordering priority 
					}else{
						if(col1.getSortLevel() <  col2.getSortLevel()){
							result = -1;
						}else{
							LOGGER.warn("Two columns having the same order priority found. Wrong configuration !"); 
						}
					}
					return result; 
				}
			}
		); 
	
	/**
	 * 
	 * @param groupCols
	 * @param dataCols
	 */
	public NewRowComparator(List<GroupColumn> groupCols, 
							List<DataColumn> dataCols){
		this.groupCols = groupCols; 
		for (DataColumn dataColumn : dataCols) {
			if(dataColumn.getSortLevel() > AbstractDataColumn.NO_SORTING){
				dataColsHavingOrdering.add(dataColumn); 
			}
		}
		LOGGER.info("after construction the dataColumnsHavingOrdering is {}", dataColsHavingOrdering);  
	}
	
	/**
	 * 
	 */
	public int compare(NewRowEvent row1, NewRowEvent row2) {
		int result = 0;
		//LOGGER.debug("comparing rows {} and {}", row1, row2); 
		if(groupCols != null){
			for (GroupColumn groupCol : groupCols) {
				//LOGGER.debug("for group column {}", groupCol); 
				Comparable valueRow1 = (Comparable)groupCol.getValue(row1);
				Comparable valueRow2 = (Comparable)groupCol.getValue(row2); 
				
				result = valueRow1.compareTo(valueRow2) * groupCol.getSortType().getSignum(); 
				//LOGGER.debug("comparing row1 value {} with row2 value {} resulted in {} ", valueRow1, valueRow2, result); 
				if(result != 0){
					break; 
				}
			}
		}
		
		if(result == 0 && !dataColsHavingOrdering.isEmpty()){
			//LOGGER.debug("group cols values are equals ...now comparing data columns"); 
			for (DataColumn dataCol : dataColsHavingOrdering) {
				Comparable valueRow1 = (Comparable)dataCol.getValue(row1); 
				Comparable valueRow2 = (Comparable)dataCol.getValue(row2); 
				
				result = valueRow1.compareTo(valueRow2) * dataCol.getSortType().getSignum(); 
				//LOGGER.trace("comparing row 1 value {} with row2 value {} resulted in {}", valueRow1, valueRow2, result); 
				if(result != 0){
					break; 
				}
			}
		}
		return result; 
	}
}
