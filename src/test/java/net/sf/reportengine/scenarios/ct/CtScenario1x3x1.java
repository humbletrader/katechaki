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
package net.sf.reportengine.scenarios.ct;

import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.DefaultPivotData;
import net.sf.reportengine.config.DefaultPivotHeaderRow;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.PivotData;
import net.sf.reportengine.config.PivotHeaderRow;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.core.steps.crosstab.IntermediateDataInfo;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.in.InMemoryTableInput;
import net.sf.reportengine.util.DistinctValuesHolder;
import net.sf.reportengine.util.MockDistinctValuesHolder;

/**
 * @author dragos balan
 *
 */
public class CtScenario1x3x1 {
	
	public static final String[][] DISTINCT_VALUES = new String[][]{
		new String[]{"North","South","East","West"},
		new String[]{"M","F"},
		new String[]{"20", "50", "80"}
	};
	
	public static final Boolean HAS_HEADER_TOTALS = new Boolean(true);
	
	
	public static final Object[][] RAW_INPUT = new Object[][]{
		
		new Object[]{"Norway",	"North",	"M",	"50",	100}, 
		new Object[]{"Sweden", 	"North",	"M",	"20",	1000},
		new Object[]{"Sweden", 	"North",	"M",	"50",	10},
		new Object[]{"Sweden", 	"North",	"M",	"80",	4},
		new Object[]{"Sweden", 	"North",	"F",	"20",	1},
		new Object[]{"Italy",	"South",	"M",	"20",	2000},
		new Object[]{"Romania",	"East",		"F",	"50",	200}, 
		new Object[]{"Romania",	"West",		"F",	"50",	2},
		new Object[]{"France",	"East",		"M",	"80",	3000}, 
		new Object[]{"France",	"West",		"M",	"20",	300}, 
		new Object[]{"France",	"West",		"F",	"20",	30}
    }; 
	
	public final static List<GroupColumn> GROUP_COLUMNS = null; 
		//new GroupColumn[]{
		//new DefaultGroupColumn("Country",  0, 0) 
	//};
	
	public final static List<DataColumn> DATA_COLUMNS = Arrays.asList(
		new DataColumn[]{
				new DefaultDataColumn("Country", 0)
	});
	
	public final static List<PivotHeaderRow> HEADER_ROWS = Arrays.asList( 
			new PivotHeaderRow[]{
		new DefaultPivotHeaderRow(1), 	//Region
		new DefaultPivotHeaderRow(2),	//Sex
		new DefaultPivotHeaderRow(3)		//Age
	}); 
	
	public final static PivotData CROSSTAB_DATA = new DefaultPivotData(4, GroupCalculators.SUM);
	
	public final static TableInput INPUT = new InMemoryTableInput(RAW_INPUT);
	
	public final static int[] AGG_LEVEL = new int[]{
		-1,
		
		0,
		3,
		3,		
		2,
		
		0,
		
		0,
		1,
		
		0, 
		1, 
		2};  

	
	public static final IntermediateDataInfo[] INTERMEDIATE_DATA_INFO = new IntermediateDataInfo[]{
		new IntermediateDataInfo(100, new int[]{0,0,0}),
		
		new IntermediateDataInfo(1000, new int[]{0,0,1}),
		new IntermediateDataInfo(10, new int[]{0,0,0}),
		new IntermediateDataInfo(4, new int[]{0,0,2}),
		new IntermediateDataInfo(1, new int[]{0,1,1}),
		
		new IntermediateDataInfo(2000, new int[]{1,0,1}),
		
		new IntermediateDataInfo(200, new int[]{2,1,0}),
		new IntermediateDataInfo(2, new int[]{3,1,0}),
		
		new IntermediateDataInfo(3000, new int[]{2,0,2}),
		new IntermediateDataInfo(300, new int[]{3,0,1}),
		new IntermediateDataInfo(30, new int[]{3,1,1})
	};
	
	public static DistinctValuesHolder MOCK_DISTINCT_VALUES_HOLDER = new MockDistinctValuesHolder(new String[][]{
			new String[]{"North", "South", "East", "West"},
			new String[]{"M", "F"},
			new String[]{"20","50","80"}
	});
}
