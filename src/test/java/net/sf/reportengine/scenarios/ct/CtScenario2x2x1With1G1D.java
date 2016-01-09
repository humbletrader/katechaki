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
import net.sf.reportengine.config.DefaultGroupColumn;
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
 * 
 * 
 * @author dragos balan
 *
 */
public class CtScenario2x2x1With1G1D {
	
	public static final Object[][] RAW_INPUT = new Object[][]{
		new Object[]{"North",	"M",	"20",	"Sweden", 	1000},
		new Object[]{"North",	"M",	"50",	"Sweden", 	10},
		new Object[]{"North",	"M",	"80",	"Sweden", 	4},
		new Object[]{"North",	"F",	"20",	"Sweden", 	1},
		
		new Object[]{"North",	"M",	"50",	"Norway", 	100}, 
		
		new Object[]{"South",	"M",	"20",	"Italy", 	2000},
		
		new Object[]{"East",	"F",	"50",	"Romania", 	200}, 
		
		new Object[]{"West",	"M",	"80",	"France", 	3000}, 
		new Object[]{"West",	"M",	"20",	"France", 	300}, 
		new Object[]{"West",	"F",	"20",	"France", 	30}
    }; 
	
	public final static TableInput INPUT = new InMemoryTableInput(RAW_INPUT);
	
	public final static int[] AGG_LEVEL = new int[]{-1,
													3,
													3,
													2,
													
													1,
													
													0,
													
													0,
													
													0, 
													3, 
													2};  
	
	public static final List<GroupColumn> GROUPING_COLUMNS = Arrays.asList(
			new GroupColumn[]{
					new DefaultGroupColumn("Region", 0, 0)					
	});
	
	public static final List<DataColumn> DATA_COLUMNS = Arrays.asList(
			new DataColumn[]{
					new DefaultDataColumn("Country", 3)
	});
	
	public static final List<PivotHeaderRow> HEADER_ROWS = Arrays.asList(new PivotHeaderRow[]{
		new DefaultPivotHeaderRow(1, null), //Sex 
		new DefaultPivotHeaderRow(2, null)   //Age
	}); 
	
	public static final DefaultPivotData CROSSTAB_DATA = new DefaultPivotData(4, GroupCalculators.SUM); //the count column
	
	
	public static final IntermediateDataInfo[] INTERMEDIATE_DATA_INFO = new IntermediateDataInfo[]{
		new IntermediateDataInfo(1000, new int[]{0,0}),
		new IntermediateDataInfo(10, new int[]{0,1}),
		new IntermediateDataInfo(4, new int[]{0,2}),
		new IntermediateDataInfo(1, new int[]{1,0}),
		
		new IntermediateDataInfo(100, new int[]{0,1}),
		
		new IntermediateDataInfo(2000, new int[]{0,0}),
		
		new IntermediateDataInfo(200, new int[]{1,1}),
		
		new IntermediateDataInfo(3000, new int[]{0,2}),
		new IntermediateDataInfo(300, new int[]{0,0}),
		new IntermediateDataInfo(30, new int[]{1,0})
	};
	
	
	public static DistinctValuesHolder MOCK_DISTINCT_VALUES_HOLDER = new MockDistinctValuesHolder(
			new String[][]{
		new String[]{"M", "F"}, 
		new String[]{"20", "50", "80"}
	});
}
