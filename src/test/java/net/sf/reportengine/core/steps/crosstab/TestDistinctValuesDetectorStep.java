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

import java.util.EnumMap;
import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.DefaultAlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.scenarios.ct.CtScenario1x1x1;
import net.sf.reportengine.scenarios.ct.CtScenario1x3x1;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1With1G1D;
import net.sf.reportengine.util.AlgoIOKeys;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * @author dragos balan
 *
 */
public class TestDistinctValuesDetectorStep  {
	
	/**
	 * 
	 */
	private DistinctValuesDetectorStep classUnderTest = null; 
	
	@Before
	public void setUp() throws Exception {
		classUnderTest = new DistinctValuesDetectorStep(); 
	}

	@Test
	public void testExecuteCtScenario1() {
		AlgoContext reportContext = new DefaultAlgorithmContext(); 
		Map<AlgoIOKeys, Object> mockAlgoInput = new EnumMap<AlgoIOKeys, Object>(AlgoIOKeys.class);
		
		mockAlgoInput.put(	AlgoIOKeys.CROSSTAB_HEADER_ROWS, 
							CtScenario2x2x1With1G1D.HEADER_ROWS);
		mockAlgoInput.put(	AlgoIOKeys.CROSSTAB_DATA, 
							CtScenario2x2x1With1G1D.CROSSTAB_DATA);
		
		classUnderTest.init(new StepInput(mockAlgoInput, reportContext));
		assertNotNull(classUnderTest.getCrosstabHeaderRows(new StepInput(mockAlgoInput, reportContext)));
		
		for (int i = 0; i < CtScenario2x2x1With1G1D.RAW_INPUT.length; i++) {
			
			StepResult<IntermediateDataInfo> stepResult = classUnderTest.execute(
																			new NewRowEvent(CtScenario2x2x1With1G1D.RAW_INPUT[i]), 
																			new StepInput(mockAlgoInput, reportContext));
			
			IntermediateDataInfo intermediateDataInfo = stepResult.getValue(); //(ContextKeys.INTERMEDIATE_CROSSTAB_DATA_INFO);
			assertNotNull(intermediateDataInfo);
			assertEquals(CtScenario2x2x1With1G1D.INTERMEDIATE_DATA_INFO[i], intermediateDataInfo); 
		}
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.core.steps.crosstab.DistinctValuesDetectorStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent, StepInput)}.
	 */
	public void testExecuteCtScenario1x3x1() {
		AlgoContext reportContext = new DefaultAlgorithmContext();  
		Map<AlgoIOKeys, Object> mockAlgoInput = new EnumMap<AlgoIOKeys, Object>(AlgoIOKeys.class);
		
		mockAlgoInput.put(AlgoIOKeys.CROSSTAB_HEADER_ROWS, CtScenario1x3x1.HEADER_ROWS);
		mockAlgoInput.put(AlgoIOKeys.CROSSTAB_DATA, CtScenario1x3x1.CROSSTAB_DATA);
		
		classUnderTest.init(new StepInput(mockAlgoInput, reportContext));
		assertNotNull(classUnderTest.getCrosstabHeaderRows(new StepInput(mockAlgoInput, reportContext)));
		
		for (int i = 0; i < CtScenario1x3x1.RAW_INPUT.length; i++) {
			
			StepResult<IntermediateDataInfo> stepResult = classUnderTest.execute(new NewRowEvent(CtScenario1x3x1.RAW_INPUT[i]), new StepInput(mockAlgoInput, reportContext));
			
			IntermediateDataInfo intermediateDataInfo = stepResult.getValue(); //.get(ContextKeys.INTERMEDIATE_CROSSTAB_DATA_INFO);
			assertNotNull(intermediateDataInfo);
			assertEquals(CtScenario1x3x1.INTERMEDIATE_DATA_INFO[i], intermediateDataInfo); 
		}
	}
	
	
	/**
	 * Test method for {@link net.sf.reportengine.core.steps.crosstab.DistinctValuesDetectorStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent, StepInput)}.
	 */
	public void testExecuteCtScenario1x1x1() {
		AlgoContext reportContext = new DefaultAlgorithmContext(); 
		Map<AlgoIOKeys, Object> mockAlgoInput = new EnumMap<AlgoIOKeys, Object>(AlgoIOKeys.class);
		
		mockAlgoInput.put(AlgoIOKeys.CROSSTAB_HEADER_ROWS, CtScenario1x1x1.ROW_HEADERS);
		mockAlgoInput.put(AlgoIOKeys.CROSSTAB_DATA, CtScenario1x1x1.CROSSTAB_DATA_NO_TOTALS);
		
		classUnderTest.init(new StepInput(mockAlgoInput, reportContext));
		assertNotNull(classUnderTest.getCrosstabHeaderRows(new StepInput(mockAlgoInput, reportContext)));
		
		for (int i = 0; i < CtScenario1x1x1.RAW_INPUT.length; i++) {
			
			StepResult<IntermediateDataInfo> stepResult =  classUnderTest.execute(new NewRowEvent(CtScenario1x1x1.RAW_INPUT[i]), new StepInput(mockAlgoInput, reportContext));
			
			IntermediateDataInfo intermediateDataInfo = stepResult.getValue(); //.get(ContextKeys.INTERMEDIATE_CROSSTAB_DATA_INFO);
			assertNotNull(intermediateDataInfo);
			assertEquals(CtScenario1x1x1.INTERMEDIATE_DATA_INFO[i], intermediateDataInfo); 
		}
	}
}
