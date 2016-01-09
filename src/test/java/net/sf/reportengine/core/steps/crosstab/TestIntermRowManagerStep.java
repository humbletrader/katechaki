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
package net.sf.reportengine.core.steps.crosstab;

import static net.sf.reportengine.util.AlgoIOKeys.DATA_COLS;
import static net.sf.reportengine.util.AlgoIOKeys.GROUP_COLS;
import static net.sf.reportengine.util.AlgoIOKeys.SHOW_GRAND_TOTAL;
import static net.sf.reportengine.util.AlgoIOKeys.SHOW_TOTALS;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.DefaultAlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.out.IntermediateCrosstabOutput;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.StepIOKeys;
import net.sf.reportengine.util.AlgoIOKeys;

import org.junit.Test;

/**
 * @author dragos balan
 *
 */
public class TestIntermRowManagerStep {

	/**
	 * Test method for {@link net.sf.reportengine.core.steps.crosstab.IntermedRowMangerStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent, StepInput)}.
	 */
	@Test
	public void testExecute() {
		AlgoContext context = new DefaultAlgorithmContext();
		Map<AlgoIOKeys, Object> mockInput = new EnumMap<AlgoIOKeys, Object>(AlgoIOKeys.class);
		
		mockInput.put(DATA_COLS, Scenario1.DATA_COLUMNS); 
		mockInput.put(GROUP_COLS, Scenario1.GROUPING_COLUMNS); 
		//mockInput.put(REPORT_OUTPUT, new CellPropsArrayOutput()); 
		mockInput.put(SHOW_TOTALS, false); 
		mockInput.put(SHOW_GRAND_TOTAL, false); 
		
		context.set(StepIOKeys.NEW_GROUPING_LEVEL, Integer.valueOf(1)); 
		context.set(StepIOKeys.LAST_GROUPING_VALUES, new String[]{"1", "2", "3", "4", "5", "6"}); 
		
		IntermediateCrosstabOutput mockReportOutput = new IntermediateCrosstabOutput(); 
		context.set(StepIOKeys.INTERMEDIATE_CROSSTAB_OUTPUT, mockReportOutput);
		
		mockReportOutput.open(); 
		
		IntermedRowMangerStep classUnderTest = new IntermedRowMangerStep(); 
		classUnderTest.init(new StepInput(mockInput, context)); 
		
		classUnderTest.execute(	new NewRowEvent(Arrays.asList(new Object[]{"2", "3", "4", "5", "6", "7"})), 
								new StepInput(mockInput, context)); 
		
		classUnderTest.exit(new StepInput(mockInput, context)); 
		
		mockReportOutput.close(); 
		
	}

}
