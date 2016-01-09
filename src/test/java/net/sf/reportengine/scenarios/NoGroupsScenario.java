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

import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.in.InMemoryTableInput;

/**
 * @author Administrator
 *
 */
public final class NoGroupsScenario {
	
	public static final Object[][] RAW_INPUT = new Object[][]{
		new String[]{"1","a","2","b","3","c"},
		new String[]{"1","a","2","b","3","c"},
		new String[]{"0","a","2","b","3","c"},
		new String[]{"0","a","2","b","3","c"},
		new String[]{"0","a","4","b","3","c"},
		new String[]{"0","a","4","b","3","c"},
		new String[]{"0","a","4","b","3","c"},
		new String[]{"0","a","4","b","6","c"},
		new String[]{"0","a","4","b","6","c"},
		new String[]{"0","a","4","b","6","c"}
	};
	
	public final static TableInput INPUT = new InMemoryTableInput(RAW_INPUT);
	
	public static final List<GroupColumn> GROUPING_COLUMNS = null; 
			
	public static final List<DataColumn> DATA_COLUMNS = Arrays.asList( 
			new DataColumn[]{
		new DefaultDataColumn("Zero or one", 0, null), 
		new DefaultDataColumn("2 multiples", 2, GroupCalculators.SUM), 
		new DefaultDataColumn("3 multiples", 4, GroupCalculators.AVG), 
		new DefaultDataColumn("Column A", 1), 
		new DefaultDataColumn("Column A", 3), 
		new DefaultDataColumn("Column A", 5, GroupCalculators.COUNT), 
	});
	
	public final static int[] AGG_LEVEL = new int[]{
		-1, 
		-1,
		-1,
		-1,
		-1,
		-1,
		-1,
		-1,
		-1,
		-1
	};
	
	
}
