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
package net.sf.reportengine.scenarios;

import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.GroupColumn;
import static net.sf.reportengine.config.HorizAlign.CENTER;
import net.sf.reportengine.config.SortType;
import static net.sf.reportengine.config.VertAlign.MIDDLE;
import net.sf.reportengine.core.calc.CountGroupCalculator;
import net.sf.reportengine.core.calc.SumGroupCalculator;
import net.sf.reportengine.in.InMemoryTableInput;
import net.sf.reportengine.in.TableInput;

public class SortScenarioOnlyDataColsCount {
	
	public static final Object[][] RAW_DATA = new Object[][]{
		new Object[]{"1","1","1",    "4",  5,3}, 
		new Object[]{"1","1","1",    "3",  4,5},
		new Object[]{"1","1","1",    "2",  3,4},
		new Object[]{"1","1","1",    "1",  2,2}, 
		new Object[]{"1","1","1",    "1",  1,1}, 
		new Object[]{"1","1","1",    "1",  0,1}
	};
	
	/**
	 * even if the group columns seem to count they don't because the group values are equal
	 */
	public static final List<DefaultGroupColumn> GROUPING_COLUMNS = Arrays.asList(
				new DefaultGroupColumn.Builder(0).header("col 0").horizAlign(CENTER).vertAlign(MIDDLE).showDuplicateValues().sortAsc().build(), 
				new DefaultGroupColumn.Builder(1).header("col 1").horizAlign(CENTER).vertAlign(MIDDLE).showDuplicateValues().sortAsc().build(), 
				new DefaultGroupColumn.Builder(2).header("col 2").horizAlign(CENTER).vertAlign(MIDDLE).showDuplicateValues().sortAsc().build()
	);
		
	/**
	 * 
	 */
	public static final List<DefaultDataColumn> DATA_COLUMNS = Arrays.asList(
			new DefaultDataColumn.Builder(3).header("no sorting").build(), //no ordering
			new DefaultDataColumn.Builder(4).header("asc. sorted with sorting level 1")
											.useCalculator(new CountGroupCalculator())
											.horizAlign(CENTER)
											.vertAlign(MIDDLE)
											.sortAsc(1).build(), 
			new DefaultDataColumn.Builder(5).header("desc sorted with sorting level 0")
											.useCalculator(new SumGroupCalculator())
											.horizAlign(CENTER)
											.vertAlign(MIDDLE)
											.sortDesc(0).build() //higher order priority
	);
	
	public final static TableInput INPUT = new InMemoryTableInput(RAW_DATA);
}
