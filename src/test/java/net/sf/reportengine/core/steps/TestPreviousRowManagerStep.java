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

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.DefaultAlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.scenarios.CalculatedColumnsScenario;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.scenarios.Scenario2;
import net.sf.reportengine.util.StepIOKeys;
import net.sf.reportengine.util.AlgoIOKeys;
import net.sf.reportengine.util.MatrixUtils;


/**
 * @author dragos balan
 *
 */
public class TestPreviousRowManagerStep{
	
	private PreviousRowManagerStep classUnderTest = null; 
	
	@Before
	public void setUp() throws Exception {
		classUnderTest = new PreviousRowManagerStep();
	}
	
	@Test
	public void testExecuteScenario1() {
		AlgoContext testReportContext = new DefaultAlgorithmContext(); 
		Map<AlgoIOKeys, Object> mockAlgoInput = new EnumMap<AlgoIOKeys, Object>(AlgoIOKeys.class); 
		
		//testReportContext.set(ContextKeys.GROUP_COLUMNS, Scenario1.GROUPING_COLUMNS);
		mockAlgoInput.put(AlgoIOKeys.GROUP_COLS, Scenario1.GROUPING_COLUMNS);
		
		classUnderTest.init(new StepInput(mockAlgoInput, testReportContext));
		
		
		//first we check that previous data row is null
		Assert.assertNull(testReportContext.get(StepIOKeys.LAST_GROUPING_VALUES));
		
		for(int i=0; i<Scenario1.RAW_DATA.length; i++){
			
			//first simulate the grouping level detector
			testReportContext.set(StepIOKeys.NEW_GROUPING_LEVEL, Scenario1.AGG_LEVEL[i]);
			NewRowEvent dataRowEvent = new NewRowEvent(Scenario1.RAW_DATA[i]);
			StepResult<Object[]> stepResult = classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, testReportContext));
			
			if(stepResult != null){
				Object[] prevValues = stepResult.getValue(); 
				Assert.assertNotNull(prevValues);
				Assert.assertEquals(Scenario1.PREVIOUS_GROUP_VALUES[i].length, prevValues.length); 
				Assert.assertTrue(Arrays.equals(Scenario1.PREVIOUS_GROUP_VALUES[i], prevValues));
				
				testReportContext.set(StepIOKeys.LAST_GROUPING_VALUES, stepResult.getValue());
			}
		}
	}
	
	@Test
	public void testExecuteScenario2() {
		AlgoContext testReportContext = new DefaultAlgorithmContext(); 
		Map<AlgoIOKeys, Object> mockAlgoInput = new EnumMap<AlgoIOKeys, Object>(AlgoIOKeys.class); 
		
		mockAlgoInput.put(AlgoIOKeys.GROUP_COLS, Scenario2.GROUPING_COLUMNS);
		//testReportContext.set(ContextKeys.GROUP_COLUMNS, Scenario2.GROUPING_COLUMNS);
		classUnderTest.init(new StepInput(mockAlgoInput, testReportContext));
		
		//first we check that previous data row is null
		Assert.assertNull(testReportContext.get(StepIOKeys.LAST_GROUPING_VALUES));
		
		for(int i=0; i<Scenario2.RAW_INPUT.length; i++){
			
			//first simulate the grouping level detector
			testReportContext.set(StepIOKeys.NEW_GROUPING_LEVEL, Scenario2.AGG_LEVEL[i]);
			NewRowEvent dataRowEvent = new NewRowEvent(Scenario2.RAW_INPUT[i]);
			StepResult<Object[]> stepResult = classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, testReportContext));
			
			if(stepResult != null){
				Object[] prevValues = stepResult.getValue(); //(Object[])testReportContext.get(ContextKeys.LAST_GROUPING_VALUES);
				Assert.assertNotNull(prevValues);
				Assert.assertEquals(Scenario2.PREVIOUS_GROUP_VALUES[i].length, prevValues.length); 
				
				Assert.assertTrue(MatrixUtils.compareMatricesAsStrings(new Object[][]{Scenario2.PREVIOUS_GROUP_VALUES[i]}, new Object[][]{prevValues}));
				
				testReportContext.set(StepIOKeys.LAST_GROUPING_VALUES, stepResult.getValue());
			}
		}
	}

	
	@Test
	public void testExecuteCalculatedColumnsScenario() {
		AlgoContext testReportContext = new DefaultAlgorithmContext(); 
		Map<AlgoIOKeys, Object> mockAlgoInput = new EnumMap<AlgoIOKeys, Object>(AlgoIOKeys.class); 
		
		mockAlgoInput.put(AlgoIOKeys.GROUP_COLS, CalculatedColumnsScenario.GROUP_COLUMNS);
		
		//testReportContext.set(ContextKeys.GROUP_COLUMNS, CalculatedColumnsScenario.GROUP_COLUMNS);
		classUnderTest.init(new StepInput(mockAlgoInput, testReportContext));
		
		
		//first we check that previous data row is null
		Assert.assertNull(testReportContext.get(StepIOKeys.LAST_GROUPING_VALUES));
		
		for(int i=0; i<CalculatedColumnsScenario.RAW_DATA.length; i++){
			
			//first simulate the grouping level detector
			testReportContext.set(StepIOKeys.NEW_GROUPING_LEVEL, CalculatedColumnsScenario.AGG_LEVEL[i]);
			NewRowEvent dataRowEvent = new NewRowEvent(CalculatedColumnsScenario.RAW_DATA[i]);
			StepResult<Object[]> stepResult = classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, testReportContext));
			
			if(stepResult != null){
				Object[] prevValues = stepResult.getValue(); 
				Assert.assertNotNull(prevValues);
				Assert.assertEquals(CalculatedColumnsScenario.PREVIOUS_GROUP_VALUES[i].length, prevValues.length); 
				
				Assert.assertTrue(Arrays.equals(CalculatedColumnsScenario.PREVIOUS_GROUP_VALUES[i], prevValues));
				
				testReportContext.set(StepIOKeys.LAST_GROUPING_VALUES, stepResult.getValue());
			}
		}
	}
}
