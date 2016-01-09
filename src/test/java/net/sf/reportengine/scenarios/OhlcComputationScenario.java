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
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.core.calc.FirstGroupCalculator;
import net.sf.reportengine.core.calc.LastGroupCalculator;
import net.sf.reportengine.core.calc.MaxGroupCalculator;
import net.sf.reportengine.core.calc.MinGroupCalculator;
import net.sf.reportengine.core.calc.SumGroupCalculator;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.in.TextTableInput;
import net.sf.reportengine.util.ReportIoUtils;

/**
 * @author dragos balan
 *
 */
public class OhlcComputationScenario {
	
	public static final TableInput INPUT = new TextTableInput(ReportIoUtils.createInputStreamFromClassPath("2010-1MIN-DATA.tsv"), "\t");
	
	public static final List<DataColumn> DATA_COLUMNS = Arrays.asList(
	new DataColumn[]{
		new DefaultDataColumn("Time",1, GroupCalculators.FIRST),
		
		new DefaultDataColumn("Volume",2, GroupCalculators.SUM),
		
		new DefaultDataColumn("Open",3, GroupCalculators.FIRST),
		new DefaultDataColumn("High",4, GroupCalculators.MAX),
		new DefaultDataColumn("Low",5, 	GroupCalculators.MIN),
		new DefaultDataColumn("Close",6, GroupCalculators.LAST)
	});
	
	public static final List<GroupColumn> GROUPING_COLUMNS = Arrays.asList(
	new GroupColumn[] {
		new DefaultGroupColumn("Date", 0,0)
	});
}
