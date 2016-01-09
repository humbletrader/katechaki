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
	
//	public static final List<Object> ROW_OF_DATA_1 = Arrays.asList(new Object[]{"1","2","3",    "4",  "5","6"});
//	public static final List<Object> ROW_OF_DATA_2 = Arrays.asList(new Object[]{"1","2","3",    "3",  "3","3"});
//	public static final List<Object> ROW_OF_DATA_3 = Arrays.asList(new Object[]{"1","2","2",    "2",  "2","2"});
//	public static final List<Object> ROW_OF_DATA_4 = Arrays.asList(new Object[]{"1","1","1",    "1",  "1","1"});
//	public static final List<Object> ROW_OF_DATA_5 = Arrays.asList(new Object[]{"1","1","1",    "1",  "1","1"});
//	public static final List<Object> ROW_OF_DATA_6 = Arrays.asList(new Object[]{"7","1","1",    "1",  "7","1"});
	
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
	public static final List<DataColumn> DATA_COLUMNS = Arrays.asList(
		new DataColumn[]{
			new DefaultDataColumn("col 3", 3), //no ordering
			new DefaultDataColumn("col 4", 4, new CountGroupCalculator(), null, HorizAlign.CENTER, VertAlign.MIDDLE, 1, SortType.ASC), 
			new DefaultDataColumn("col 5", 5, new SumGroupCalculator(), null, HorizAlign.CENTER, VertAlign.MIDDLE, 0, SortType.ASC) //higher order priority
	});
}
