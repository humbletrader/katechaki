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
package net.sf.reportengine.scenarios;

import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.config.SortType;
import net.sf.reportengine.config.VertAlign;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.core.calc.CountGroupCalculator;
import net.sf.reportengine.core.calc.SumGroupCalculator;

/**
 * @author dragos
 *
 */
public class SortScenarioWithGroupAndDataCols {
	
	/**
	 * 
	 */
	public static final List<GroupColumn> GROUPING_COLUMNS = Arrays.asList(
		new GroupColumn[]{
				new DefaultGroupColumn("col 0", 0, 0, null, HorizAlign.CENTER, VertAlign.MIDDLE, true, SortType.ASC), 
				new DefaultGroupColumn("col 1", 1, 1, null, HorizAlign.CENTER, VertAlign.MIDDLE, true, SortType.ASC), 
				new DefaultGroupColumn("col 2", 2, 2, null, HorizAlign.CENTER, VertAlign.MIDDLE, true, SortType.ASC)
	});
		
	/**
	 * 
	 */
	public static final List<? extends DataColumn> DATA_COLUMNS = Arrays.asList(
			new DefaultDataColumn.Builder(3).header("col 3").build(), //no ordering
			new DefaultDataColumn.Builder(4).header("col 4").useCalculator(new CountGroupCalculator()).horizAlign(HorizAlign.CENTER).vertAlign(VertAlign.MIDDLE).sortAsc(1).build(), 
			new DefaultDataColumn.Builder(5).header("col 5").useCalculator(new SumGroupCalculator()).horizAlign(HorizAlign.CENTER).vertAlign(VertAlign.MIDDLE).sortAsc(0).build() //higher order priority
	);
}
