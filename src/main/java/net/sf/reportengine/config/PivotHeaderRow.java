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


/**
 * This is part of the header of the final pivot table that gets its data from an input column. 
 * 
 * As an example: 
 * For an input like 
 * <pre>
 * 	N.America, Penguins, 100
 * 	N.America, Lions, 200
 * 	N.America, Camels, 10
 * 	N.America, Lions, 200
 * 	S.America, Penguins, 300
 * 	S.America, Camels,5
 * 	S.America, Lions, 150, 
 * 	Europe, Penguins, 0, 
 * </pre>			
 * If you define the second column as the source for the header row, you can get a pivot table like: 
 * <pre>
 * -----------------------------------------
 * Continent | Penguins | Lions  | Camels  |
 * -----------------------------------------
 * N. America|  100     |  200  |  10      |
 * -----------------------------------------
 * S. America|  200     |  300  |  5       |
 * -----------------------------------------
 * Europe ...
 * -----------------------------------------
 * 	</pre>
 * where 
 * <pre>
 * ------------------------------
 * | Penguins | Lions  | Camels  |
 * ------------------------------
 *</pre>
 *is the crosstab header row 
 * 
 * @see DefaultPivotHeaderRow
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 *
 */
public interface PivotHeaderRow {
	
	/**
	 * this method returns the value for the current data row (taken from newRowEvent)
	 * 
	 * @param newRowEvent
	 * @return
	 */
	public Object getValue(NewRowEvent newRowEvent); 
	
}
