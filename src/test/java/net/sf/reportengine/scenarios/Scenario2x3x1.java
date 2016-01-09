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
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.core.calc.SumGroupCalculator;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.in.TextTableInput;

/**
 * @author dragos
 *
 */
public class Scenario2x3x1 {
	
	public static final TableInput INPUT = new TextTableInput(Scenario2x3x1.class.getClassLoader().getResourceAsStream("2x3x1.txt"),",");
	
	public static final List<GroupColumn> GROUP_COLUMNS = Arrays.asList(
		new GroupColumn[]{
				new DefaultGroupColumn("Continent", 0, 0),
				new DefaultGroupColumn("Direction", 1, 1),
				new DefaultGroupColumn("Country", 2, 2)
	});
	
	public static final List<DataColumn> DATA_COLUMNS = Arrays.asList(
		new DataColumn[]{
				new DefaultDataColumn("Sex", 3),
				new DefaultDataColumn("Age", 4),
				new DefaultDataColumn("Count", 5, new SumGroupCalculator())
	});
	
}
