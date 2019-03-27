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
public interface DataColumn extends TableColumn{
	
	/**
	 * no sorting flag
	 */
	int NO_SORTING = -1;
	
	/**
	 * returns the formatted value of the total as it will be displayed in the report
	 * 
	 * @param totalValue
	 * @return	the formatted total as a string
	 */
	String getFormattedTotal(Object totalValue);
	

	/**
	 * returns the calculator (if any) to be used on this column 
	 * 
	 * @return the group calculator
	 */
	GroupCalculator getCalculator();

    /**
     * The sorting priority of this column
     *
     * @return the sorting priority
     */
    int getSortLevel();
	

	

}
