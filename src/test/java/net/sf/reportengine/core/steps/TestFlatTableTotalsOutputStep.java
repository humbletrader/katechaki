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

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.DefaultAlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.out.MockReportOutput;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.StepIOKeys;
import net.sf.reportengine.util.AlgoIOKeys;

import org.junit.Test;


/**
 * @author dragos balan
 *
 */
public class TestFlatTableTotalsOutputStep  {
	

	@Test
	public void testExecuteScenario1() {
		FlatTableTotalsOutputStep classUnderTest = new FlatTableTotalsOutputStep();
		AlgoContext mockReportContext = new DefaultAlgorithmContext(); 
		Map<AlgoIOKeys, Object> mockAlgoInput = new EnumMap<AlgoIOKeys, Object>(AlgoIOKeys.class);
		
		StringWriter testWriter = new StringWriter(); 
		MockReportOutput mockOutput = new MockReportOutput(testWriter); 
		
		//mockAlgoInput.put(IOKeys.REPORT_OUTPUT, mockOutput);
        mockAlgoInput.put(AlgoIOKeys.GROUP_COLS, Scenario1.GROUPING_COLUMNS); 
        mockAlgoInput.put(AlgoIOKeys.DATA_COLS, Scenario1.DATA_COLUMNS); 
        mockAlgoInput.put(AlgoIOKeys.SHOW_GRAND_TOTAL, Scenario1.SHOW_GRAND_TOTAL); 
        mockAlgoInput.put(AlgoIOKeys.SHOW_TOTALS, true); 
        mockAlgoInput.put(AlgoIOKeys.NEW_REPORT_OUTPUT, mockOutput); 
        
        //mockReportContext.set(ContextKeys.NEW_LOCAL_REPORT_OUTPUT, mockOutput); 
        mockReportContext.set(StepIOKeys.DISTRIBUTION_OF_CALCULATORS, Scenario1.DISTRIBUTION_OF_CALCULATOR_IN_DATA_ROW_ARRAY); 
        mockReportContext.set(StepIOKeys.DATA_ROW_COUNT, 0); 
        mockReportContext.set(StepIOKeys.LOCAL_REPORT_INPUT, Scenario1.INPUT); 
        //mockAlgoInput.put(IOKeys.REPORT_INPUT, Scenario1.INPUT); 
        
		classUnderTest.init(new StepInput(mockAlgoInput, mockReportContext));
		
		NewRowEvent dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_1);
		
    	//simulate the level detector
		mockReportContext.set(StepIOKeys.NEW_GROUPING_LEVEL, Scenario1.ROW_1_AGG_LEVEL);
		mockReportContext.set(StepIOKeys.CALC_INTERM_RESULTS, null);
    	classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, mockReportContext));
    	mockReportContext.set(StepIOKeys.LAST_GROUPING_VALUES, Scenario1.PREVIOUS_GROUP_VALUES[0]);
		
    	dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_2);
    	mockReportContext.set(StepIOKeys.NEW_GROUPING_LEVEL, Scenario1.ROW_2_AGG_LEVEL);
    	mockReportContext.set(StepIOKeys.CALC_INTERM_RESULTS, Scenario1.ROW_1_CALCULATORS_RESULTS);
		classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, mockReportContext));
		mockReportContext.set(StepIOKeys.LAST_GROUPING_VALUES, Scenario1.PREVIOUS_GROUP_VALUES[1]);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_3);
		mockReportContext.set(StepIOKeys.NEW_GROUPING_LEVEL, Scenario1.ROW_3_AGG_LEVEL);
		mockReportContext.set(StepIOKeys.CALC_INTERM_RESULTS, Scenario1.ROW_2_CALCULATORS_RESULTS);
		classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, mockReportContext));
		mockReportContext.set(StepIOKeys.LAST_GROUPING_VALUES, Scenario1.PREVIOUS_GROUP_VALUES[2]);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_4);
		mockReportContext.set(StepIOKeys.NEW_GROUPING_LEVEL, Scenario1.ROW_4_AGG_LEVEL);
		mockReportContext.set(StepIOKeys.CALC_INTERM_RESULTS, Scenario1.ROW_3_CALCULATORS_RESULTS);
		classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, mockReportContext));
		mockReportContext.set(StepIOKeys.LAST_GROUPING_VALUES, Scenario1.PREVIOUS_GROUP_VALUES[3]);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_5);
		mockReportContext.set(StepIOKeys.NEW_GROUPING_LEVEL, Scenario1.ROW_5_AGG_LEVEL);
		mockReportContext.set(StepIOKeys.CALC_INTERM_RESULTS, Scenario1.ROW_4_CALCULATORS_RESULTS);
		classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, mockReportContext));
		mockReportContext.set(StepIOKeys.LAST_GROUPING_VALUES, Scenario1.PREVIOUS_GROUP_VALUES[4]);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_6);
		mockReportContext.set(StepIOKeys.NEW_GROUPING_LEVEL, Scenario1.ROW_6_AGG_LEVEL);
		mockReportContext.set(StepIOKeys.CALC_INTERM_RESULTS, Scenario1.ROW_5_CALCULATORS_RESULTS);
		classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, mockReportContext));
		mockReportContext.set(StepIOKeys.LAST_GROUPING_VALUES, Scenario1.PREVIOUS_GROUP_VALUES[5]);
		
		mockReportContext.set(StepIOKeys.CALC_INTERM_RESULTS, Scenario1.ROW_6_CALCULATORS_RESULTS);
		classUnderTest.exit(new StepInput(mockAlgoInput, mockReportContext));
		
		String result = testWriter.getBuffer().toString();
		//Assert.assertTrue(MatrixUtils.compareMatrices(Scenario1.OUTPUT_TOTALS, resultCellMatrix));
		System.out.println(result);
	}
	
	//TODO
	public void testExecuteComputedColumnsScenario(){
//		IAlgorithmContext reportContext = getTestContext(); 
//		
//		//simulate the level detector
//		reportContext.set(AbstractReport.CONTEXT_KEY_GROUPING_COLUMNS, CalculatedColumnsScenario.GROUP_COLUMNS); 
//		reportContext.set(FlatReport.CONTEXT_KEY_DATA_COLUMNS, CalculatedColumnsScenario.DATA_COLUMNS);
//        reportContext.set(FlatReport.CONTEXT_KEY_SHOW_GRAND_TOTAL, true);
//        reportContext.set(FlatReportExtractTotalsDataInitStep.CONTEXT_KEY_DISTRIBUTION_OF_CALCULATORS, 
//        		CalculatedColumnsScenario.DISTRIBUTION_OF_CALCULATOR_IN_DATA_ROW_ARRAY);
//        
//		getTestContext().setInput(CalculatedColumnsScenario.INPUT);
//		classUnderTest.init(getTestContext());
//		
//		for(int i=0; i<CalculatedColumnsScenario.RAW_DATA.length; i++){
//			NewRowEvent dataRowEvent = new NewRowEvent(CalculatedColumnsScenario.RAW_DATA[i]);
//		
//			//simulate the preceeding steps
//			setAggLevel(CalculatedColumnsScenario.AGG_LEVEL[i]);
//			setCalculatorMatrix(CalculatedColumnsScenario.CALCULATORS_RESULTS);
//			setComputedInputValues(CalculatedColumnsScenario.COMPUTED_VALUES[i]);
//			classUnderTest.execute(dataRowEvent);
//			setPreviousGroupValues(CalculatedColumnsScenario.PREVIOUS_GROUP_VALUES[i]);
//		}
	}

}
