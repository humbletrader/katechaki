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

import net.sf.reportengine.config.PivotHeaderRow;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.DefaultPivotData;
import net.sf.reportengine.config.DefaultPivotHeaderRow;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.in.InMemoryTableInput;
import net.sf.reportengine.in.TableInput;

/**
 * @author dragos balan
 *
 */
public class CtUnsortedScenario2x2x1With2G0D {
	
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
		new String[]{"West",	"France", 	"F",	"20",	"30"},
		
		//wrong place
		new String[]{"South",	"Greece", 	"M",	"20",	"500"},
		
		//wrong place
		new String[]{"North",	"Norway", 	"F",	"20",	"400"},
		
		//wrong place
		new String[]{"East",	"Hungary", 	"F",	"50",	"300"}, 
    }; 
	
	public final static TableInput INPUT = new InMemoryTableInput(RAW_INPUT);
	
	public static final List<GroupColumn> GROUPING_COLUMNS = Arrays.asList(
			new GroupColumn[]{
					new DefaultGroupColumn("Region", 0, 0/*first group/sort level*/), 
					new DefaultGroupColumn("Country", 1, 1/*second group/sort level*/)
			}); 
	
	public static final List<DataColumn> DATA_COLUMNS = null; 
	
	public static final List<PivotHeaderRow> HEADER_ROWS = Arrays.asList(new PivotHeaderRow[]{
			new DefaultPivotHeaderRow(2, null), //Sex 
			new DefaultPivotHeaderRow(3, null)   //Age
		}); 
		
	public static final DefaultPivotData CROSSTAB_DATA = new DefaultPivotData(4, GroupCalculators.SUM); //the count column

}
