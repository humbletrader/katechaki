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
package net.sf.reportengine.scenarios.ct;

import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.PivotHeaderRow;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.DefaultPivotData;
import net.sf.reportengine.config.DefaultPivotHeaderRow;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.core.calc.SumGroupCalculator;
import net.sf.reportengine.in.InMemoryTableInput;
import net.sf.reportengine.in.TableInput;

/**
 * in this scenario the data columns have sortLevel set. The report doesn't need any other configuration
 * 
 * @author dragos balan
 *
 */
public class CtUnsortedScenario2x2x1With0G2D {
	
	public static final String[][] RAW_INPUT = new String[][]{
		new String[]{"North",	"Sweden", 	"M",	"20",	"1000"},
		new String[]{"North",	"Sweden", 	"M",	"50",	"10"},
		new String[]{"North",	"Sweden", 	"M",	"80",	"4"},
		new String[]{"North",	"Sweden", 	"F",	"20",	"1"},
		//wrong place
		new String[]{"South",	"Italy", 	"M",	"20",	"2000"},
				
		new String[]{"North",	"Norway", 	"M",	"50",	"100"}, 
				
		new String[]{"West",	"France", 	"M",	"80",	"3000"}, 
		//wrong place
		new String[]{"East",	"Romania", 	"F",	"50",	"200"}, 
		new String[]{"West",	"France", 	"M",	"20",	"300"}, 
		new String[]{"West",	"France", 	"F",	"20",	"30"}

    }; 
	
	public final static TableInput INPUT = new InMemoryTableInput(RAW_INPUT);
	
	public static final List<GroupColumn> GROUPING_COLUMNS = null; 
	
	public static final List<DefaultDataColumn> DATA_COLUMNS = Arrays.asList(
			new DefaultDataColumn.Builder(0).header("Region").horizAlign(HorizAlign.CENTER).sortAsc(0).build(), 
			new DefaultDataColumn.Builder(1).header("Country").horizAlign(HorizAlign.CENTER).sortAsc(1).build()
	);
	
	public static final List<PivotHeaderRow> HEADER_ROWS = Arrays.asList(new PivotHeaderRow[]{
			new DefaultPivotHeaderRow(2, null), //Sex 
			new DefaultPivotHeaderRow(3, null)   //Age
		}); 
		
	public static final DefaultPivotData CROSSTAB_DATA = new DefaultPivotData(4, new SumGroupCalculator()); //the count column
	
}
