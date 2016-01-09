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
 * Group column for flat and pivot tables. 
 * 
 * Group columns are helpful when one needs to group rows in order to show sub totals 
 * for each group or just a better display of the data. A correct group configuration consists of: 
 * <ol>
 *  <li>adding a group column to the table</li> 
 *  <li>and setting a {@link GroupCalculator} (like SUM, AVG, MIN, MAX etc.) to at least one of the data columns of the table</li>
 * </ol>
 * Note: there's no limit to the number of data columns that can have {@link GroupCalculator}s
 * 
 * Let’s see an example with my monthly expenses. For the input:
 * <pre>
 *  August      food    500$ 
 *  August      gas     300$ 
 *  September   food    567$ 
 *  September   gas     154$ 
 *  September   fun     200$ 
 * </pre>
 * 
 * If we declare the first column as a group column and we add a Sum calculator to the last data column
 * then ReportEngine will make sure to display the totals at each change in the values of the first 
 * column:
 * 
 * <pre>
 *  August      food    500$ 
 *  August      gas     300$ 
 *  Total August        800$
 *  September   food    567$ 
 *  September   gas     154$ 
 *  September   fun     200$ 
 *  Total September     921$
 *  Grand Total         1721$
 * </pre>
 * 
 * Other settings available for the group columns are: 
 * <ol>
 *  <li> setting the priority of this group column (useful when more group columns are set to the table)</li>
 *  <li> setting the header of the column </li>
 *  <li> setting the format of the displayed values </li>
 *  <li> setting the horizontal and the vertical alignment </li>
 *  <li> displaying the duplicated values</li>
 * </ol>
 * Usage: 
 * After building it, a group column should be set to the table as in the example below: 
 *  <pre>
 *      FlatTable table = new FlatTableBuilder()
 *          ...
 *          .addGroupColumn( ... group column here ...)
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
public interface GroupColumn {
	
	/**
	 * this appears in the final report as the title/header of the column
	 * @return the header of this column
	 */
	public String getHeader(); 
	
	
	/**
	 * getter for the priority of this column 
	 * The grouping levels start with 0 (the most important), 1, 2, 3 ..
	 *  
	 * @return an integer representing the priority of this group column
	 */
	public int getGroupingLevel();
	
	/**
	 * getter for the current value on this column
	 * 
	 * @param newRowEvent  the new row event
	 * @return the value of this column
	 */
	public Object getValue(NewRowEvent newRowEvent); 
	
	/**
	 * returns the formatted value for the given object according to the 
	 * formatting rules of this grouping column
	 * 
	 * @param object   an unformatted value
	 */
	public String getFormattedValue(Object object);
	
	/**
	 * returns the horizontal alignment of the values of this column
	 */
	public HorizAlign getHorizAlign();
	
	/**
	 * returns the vertical alignment of the values of this column
	 * 
	 * @return     the vertical alignment for this column
	 */
	public VertAlign getVertAlign();
	
	/**
	 * whether or not this group column should display duplicate group values. 
	 * Usually there are many group values displayed one after the other 
	 * 
	 * <table>
	 * <tr>
	 * 	<td>Europe</td><td>South</td><td>100</td>
	 * </tr>
	 * <tr>
	 * 	<td>Europe</td><td>North</td><td>207</td>
	 * </tr>
	 * <tr>
	 * 	<td>Europe</td><td>East</td><td>103</td>
	 * </tr>
	 * <tr>
	 * 	<td>Europe</td><td>West</td><td>120</td>
	 * </tr>
	 * </table>
	 * 
	 * This method controls whether the duplicated Europe value should be displayed multiple times or not: 
	 * 
	 * <table>
	 * <tr>
	 * 	<td>Europe</td><td>South</td><td>100</td>
	 * </tr>
	 * <tr>
	 * 	<td>&nbsp;</td><td>North</td><td>207</td>
	 * </tr>
	 * <tr>
	 * 	<td>&nbsp;</td><td>East</td><td>103</td>
	 * </tr>
	 * <tr>
	 * 	<td>&nbsp;</td><td>West</td><td>120</td>
	 * </tr>
	 * <tr>
	 * 	<td>Asia</td><td>South</td><td>300</td>
	 * </tr>
	 * <tr>
	 * 	<td>&nbsp;</td><td>North</td><td>267</td>
	 * </tr>
	 * <tr>
	 * 	<td>&nbsp;</td><td>East</td><td>564</td>
	 * </tr>
	 * <tr>
	 * 	<td>&nbsp;</td><td>West</td><td>122</td>
	 * </tr>
	 * </table>
	 * 
	 * @return
	 */
	public boolean showDuplicates(); 
	
	/**
	 * whether you want to sort this ascending or descending
	 * 
	 * @return
	 */
	public SortType getSortType(); 
}
