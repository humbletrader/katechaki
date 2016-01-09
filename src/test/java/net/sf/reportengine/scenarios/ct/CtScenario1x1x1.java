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
import net.sf.reportengine.components.CellProps;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.ReportContent;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.core.steps.crosstab.IntermediateDataInfo;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.in.InMemoryTableInput;
import net.sf.reportengine.util.CrossTabCoefficients;

/**
 * @author dragos
 *
 */
public class CtScenario1x1x1 {
	
	public static final String[][] DISTINCT_VALUES = new String[][]{
		new String[]{"East",	"North",	"South",	"West"}
	};
	
	public static final Boolean HAS_HEADER_TOTALS = new Boolean(false);
	
	public static final String[][] HEADER_TEMPLATE = new String[][]{
		new String[]{"East",	"North",	"South",	"West"}
	};
	
	public static final Object[][] RAW_INPUT = new Object[][]{
		new Object[]{"Asia",			"North", 	1},
		new Object[]{"Asia",			"South",	100},
		new Object[]{"Europe",			"West", 	2},
		new Object[]{"North America",	"East",		200},
		new Object[]{"North America",	"South",	3}
	};
	
	
	public static final List<GroupColumn> GROUP_COLUMNS = null; 
//		new GroupColumn[]{
//		new DefaultGroupColumn("Continent", 0, 0)
//	};
	
	public static final List<DataColumn> DATA_COLUMNS = Arrays.asList( 
			new DataColumn[]{
					new DefaultDataColumn("Continent", 0)
	});
	
	public static final List<PivotHeaderRow> ROW_HEADERS = Arrays.asList( 
			new PivotHeaderRow[]{
		new DefaultPivotHeaderRow(1)//Region - second column
	});
	
	public static final PivotData CROSSTAB_DATA_WITH_TOTALS = new DefaultPivotData(2, GroupCalculators.SUM);
	public static final PivotData CROSSTAB_DATA_NO_TOTALS = new DefaultPivotData(2);
	
	
	public final static TableInput INPUT = new InMemoryTableInput(RAW_INPUT);
    
	
	public final static int[] AGG_LEVEL = new int[]{
		-1,
		1,
		0,
		0
	};
	
	public static final IntermediateDataInfo[] INTERMEDIATE_DATA_INFO = new IntermediateDataInfo[]{
		new IntermediateDataInfo(1, 	new int[]{0}),
		new IntermediateDataInfo(100, 	new int[]{1}),
		new IntermediateDataInfo(2, 	new int[]{2}),
		new IntermediateDataInfo(200, 	new int[]{3}),
		new IntermediateDataInfo(3, 	new int[]{1})
	};
	
	
//	public final static CellProps[][] EXPECTED_OUTPUT_DATA = new CellProps[][]{
//	    new CellProps[]{
//	            new CellProps.Builder("East").contentType(ReportContent.COLUMN_HEADER).build(), 
//	            new CellProps.Builder("North").contentType(ReportContent.COLUMN_HEADER).build(), 
//	            new CellProps.Builder("South").contentType(ReportContent.COLUMN_HEADER).build(), 
//	            new CellProps.Builder("West").contentType(ReportContent.COLUMN_HEADER).build()},
//	    
//	};
	
	public static final CrossTabCoefficients COEFFICIENTS = new CrossTabCoefficients(DISTINCT_VALUES, HAS_HEADER_TOTALS);
}
