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
import java.util.Collections;
import java.util.List;

import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.in.InMemoryTableInput;

/**
 * @author dragos balan
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
	
	public static final List<GroupColumn> GROUPING_COLUMNS = Collections.emptyList(); 
			
	public static final List<DefaultDataColumn> DATA_COLUMNS = Arrays.asList( 
		new DefaultDataColumn.Builder(0).header("Zero or one").build(), 
		new DefaultDataColumn.Builder(2).header("2 multiples").useCalculator(GroupCalculators.SUM).build(), 
		new DefaultDataColumn.Builder(4).header("3 multiples").useCalculator(GroupCalculators.AVG).build(), 
		new DefaultDataColumn.Builder(1).header("Column A").build(), 
		new DefaultDataColumn.Builder(3).header("Column A").build(), 
		new DefaultDataColumn.Builder(5).header("Column A").useCalculator(GroupCalculators.COUNT).build() 
	);
	
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
