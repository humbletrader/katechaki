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

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.GroupCalculator;

/**
 * Data column for flat and pivot tables. 
 * 
 * Data columns are normal report columns used for displaying data and totals.  The configurations supported by a data column are:  
 * <ol>
 * <li>header</li>
 * <li>values to be displayed </li>
 * <li>group calculator (if totals of any kind  are needed )</li>
 * <li>data formatter</li>
 * <li>horizontal/vertical alignment of text</li>
 * <li>sorting </li>
 * </ol>
 * Usage: 
 * After building it, a data column should be set to the table as in the example below: 
 *  <pre>
 *      FlatTable table = new FlatTableBuilder()
 *          ...
 *          .addDataColumn( ... data column here ... )
 *          .addDataColumn(... data column here ... )
 *          ...
 *          .build(); 
 *  </pre>
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public interface DataColumn {
	
	/**
	 * no sorting flag
	 */
	public static final int NO_SORTING = -1; 
	
	/**
	 * returns the header of the column. 
	 * The header will be displayed in the final report as the header of this column and 
	 * it shouldn't be confused with the column name in case the report input is an SQL query
	 * 
	 * @return the header of the report column
	 */
	public String getHeader();
	
	
	/**
	 * returns the formatted value ready to be displayed in the report
	 * 
	 * @param value	the unformatted value
	 * @return	the formatted value
	 */
	public String getFormattedValue(Object value);
	
	/**
	 * returns the formatted value of the total as it will be displayed in the report
	 * 
	 * @param totalValue
	 * @return	the formatted total as a string
	 */
	public String getFormattedTotal(Object totalValue); 
	
	/**
	 * retrieves the value for this column. 
	 * This is the most important method as it retrieves the data for the row-column combination. 
	 *  
	 * @param newRowEvent the event containing the new row of data as an array
	 * @return	the computed value for this column
	 */
	public Object getValue(NewRowEvent newRowEvent); 
	
	
	/**
	 * returns the calculator (if any) to be used on this column 
	 * 
	 * @return the group calculator
	 */
	public GroupCalculator getCalculator();
	
	/**
	 * returns the horizontal alignment of the values of this column
	 * 
	 * @return the horizontal alignment of this column
	 */
	public HorizAlign getHorizAlign();
	
	/**
	 * returns the vertical alignment of the values of this column
	 * 
	 * @return the vertical alignment
	 */
	public VertAlign getVertAlign(); 
	
	/**
	 * The sorting priority of this column
	 * 
	 * @return the sorting priority
	 */
	public int getSortLevel(); 
	
	/**
	 * getter for the sort type (asc or desc)
	 * 
	 * @return the sort type 
	 */
	public SortType getSortType(); 
}
