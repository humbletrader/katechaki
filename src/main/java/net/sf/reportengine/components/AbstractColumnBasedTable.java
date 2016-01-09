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
package net.sf.reportengine.components;

import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.in.TableInput;

/**
 * @author dragos balan
 *
 */
public abstract class AbstractColumnBasedTable extends AbstractTable {


	/**
     * the data columns
     */
    private List<DataColumn> dataColsAsList = new ArrayList<DataColumn>();
    
    /**
     * grouping columns
     */
    private List<GroupColumn> groupColsAsList = new ArrayList<GroupColumn>(); 
    
    /**
     * 
     * @param input
     * @param dataCols
     * @param groupCols
     * @param showTotals
     * @param showGrandTotal
     * @param showDataRows
     * @param valuesSorted
     */
    public AbstractColumnBasedTable(TableInput input, List<DataColumn> dataCols, List<GroupColumn> groupCols, boolean showTotals, boolean showGrandTotal, boolean showDataRows, boolean valuesSorted){
    	super(input, showTotals, showGrandTotal, showDataRows, valuesSorted); 
    	this.dataColsAsList = dataCols; 
    	this.groupColsAsList = groupCols; 
    }
    
    /**
	 * getter for data colums of this report
	 * @return an ordered list of data columns
	 */
	public List<DataColumn> getDataColumns() {
		return dataColsAsList; 
	}

	
	/**
	 * getter for the list of group columns
	 * @return an ordered list of group columns
	 */
	public List<GroupColumn> getGroupColumns() {
		return groupColsAsList; 
	}
	
}
