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
package net.sf.reportengine.core.steps;

import java.io.StringWriter;
import java.util.EnumMap;
import java.util.Map;

import junit.framework.Assert;
import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.DefaultAlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.out.MockReportOutput;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.StepIOKeys;
import net.sf.reportengine.util.AlgoIOKeys;

import org.apache.commons.lang.math.NumberUtils;
import org.junit.Test;

/**
 * @author dragos balan
 *
 */
public class TestDataRowsOutputStep {
	
	@Test
	public void testExecuteScenario1() {
		DataRowsOutputStep classUnderTest = new DataRowsOutputStep();
		
		AlgoContext reportContext = new DefaultAlgorithmContext(); 
		Map<AlgoIOKeys, Object> mockAlgoInput = new EnumMap<AlgoIOKeys, Object>(AlgoIOKeys.class);
		
		StringWriter testWriter = new StringWriter(); 
		MockReportOutput mockOutput = new MockReportOutput(testWriter); 
		
		reportContext.set(StepIOKeys.LOCAL_REPORT_INPUT, Scenario1.INPUT);
		//reportContext.set(ContextKeys.NEW_LOCAL_REPORT_OUTPUT, mockOutput); 
		
		mockAlgoInput.put(AlgoIOKeys.DATA_COLS, Scenario1.DATA_COLUMNS); 
		mockAlgoInput.put(AlgoIOKeys.GROUP_COLS, Scenario1.GROUPING_COLUMNS); 
		mockAlgoInput.put(AlgoIOKeys.NEW_REPORT_OUTPUT, mockOutput); 
		
		reportContext.set(StepIOKeys.DATA_ROW_COUNT, 0); 
		classUnderTest.init(new StepInput(mockAlgoInput, reportContext)); 
		
		NewRowEvent dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_1);
		StepResult<Integer> stepResult = classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, reportContext));
		
		Assert.assertNotNull(stepResult);
		Assert.assertEquals(NumberUtils.INTEGER_ONE, stepResult.getValue()); //(ContextKeys.DATA_ROW_COUNT));
		reportContext.set(StepIOKeys.DATA_ROW_COUNT, stepResult.getValue());
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_2);
		stepResult = classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, reportContext));
		
		Assert.assertNotNull(stepResult);
		Assert.assertEquals(Integer.valueOf(2), stepResult.getValue());
		reportContext.set(StepIOKeys.DATA_ROW_COUNT, stepResult.getValue());
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_3);
		stepResult = classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, reportContext));
		
		Assert.assertNotNull(stepResult);
		Assert.assertEquals(Integer.valueOf(3), stepResult.getValue());
		reportContext.set(StepIOKeys.DATA_ROW_COUNT, stepResult.getValue());
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_4);
		stepResult = classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, reportContext));
		
		Assert.assertNotNull(stepResult);
		Assert.assertEquals(Integer.valueOf(4), stepResult.getValue());
		reportContext.set(StepIOKeys.DATA_ROW_COUNT, stepResult.getValue());
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_5);
		stepResult = classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, reportContext));
		
		Assert.assertNotNull(stepResult);
		Assert.assertEquals(Integer.valueOf(5), stepResult.getValue());
		reportContext.set(StepIOKeys.DATA_ROW_COUNT, stepResult.getValue());
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_6);
		stepResult = classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, reportContext));
		
		Assert.assertNotNull(stepResult);
		Assert.assertEquals(Integer.valueOf(6), stepResult.getValue());
		reportContext.set(StepIOKeys.DATA_ROW_COUNT, stepResult.getValue());
		
		//CellProps[][] resultCellMatrix = Scenario1.OUTPUT.getDataCellMatrix();
		//Assert.assertTrue(MatrixUtils.compareMatrices(Scenario1.EXPECTED_OUTPUT_DATA, resultCellMatrix));
		System.out.println(testWriter.getBuffer());
	}
}
