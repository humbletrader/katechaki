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
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.in.InMemoryTableInput;
import net.sf.reportengine.in.TableInput;

public class CtUnsortedScenario2x2x1With1G1D {
	
	public static final String[][] RAW_INPUT = new String[][]{
		new String[]{"North",	"M",	"20",	"Sweden", 	"1000"},
		new String[]{"North",	"M",	"50",	"Sweden", 	"10"},
		new String[]{"North",	"M",	"80",	"Sweden", 	"4"},
		new String[]{"North",	"F",	"20",	"Sweden", 	"1"},
		
		//wrong place (south between north )
		new String[]{"South",	"M",	"20",	"Italy", 	"2000"},
		
		new String[]{"North",	"M",	"50",	"Norway", 	"100"}, 
		
		new String[]{"West",	"M",	"80",	"France", 	"3000"}, 
		
		//wrong place (east between west)
		new String[]{"East",	"F",	"50",	"Romania", 	"200"}, 
		new String[]{"West",	"M",	"20",	"France", 	"300"}, 
		new String[]{"West",	"F",	"20",	"France", 	"30"}
    }; 
	
	public final static TableInput INPUT = new InMemoryTableInput(RAW_INPUT);
	
	public static final List<DefaultGroupColumn> GROUPING_COLUMNS = Arrays.asList(
					new DefaultGroupColumn.Builder(0).header("Programatically sorted Region").build()					
	);
	
	public static final List<DefaultDataColumn> DATA_COLUMNS = Arrays.asList(
					new DefaultDataColumn.Builder(3).header("Country").build()
	);
	
	public static final List<PivotHeaderRow> HEADER_ROWS = Arrays.asList(new PivotHeaderRow[]{
		new DefaultPivotHeaderRow(1, null), //Sex 
		new DefaultPivotHeaderRow(2, null)   //Age
	}); 
	
	public static final DefaultPivotData CROSSTAB_DATA = new DefaultPivotData(4, GroupCalculators.SUM); //the count column
}
