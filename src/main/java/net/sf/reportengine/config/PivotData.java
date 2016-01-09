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
 * Crosstab data
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public interface PivotData {
	
	/**
	 * 
	 * @param newRowEvent
	 * @return
	 */
	public Object getValue(NewRowEvent newRowEvent);
	
	/**
	 * 
	 * @param unformattedValue
	 * @return
	 */
	public String getFormattedValue(Object unformattedValue);
	
	/**
	 * 
	 */
	public String getFormattedTotal(Object unformattedTotalValue);
	
	/**
	 * 
	 * @return
	 */
	public GroupCalculator getCalculator();
	
	/**
	 * returns the horizontal alignment of the values of this column
	 */
	public HorizAlign getHorizAlign();
	
	/**
	 * returns the vertical alignment of the values of this column
	 * @return
	 */
	public VertAlign getVertAlign();
	
}
