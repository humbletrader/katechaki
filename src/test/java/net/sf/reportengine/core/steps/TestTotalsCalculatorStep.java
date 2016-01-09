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
package net.sf.reportengine.core.steps;

import java.util.EnumMap;
import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.DefaultAlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.CalcIntermResult;
import net.sf.reportengine.scenarios.CalculatedColumnsScenario;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.scenarios.Scenario2;
import net.sf.reportengine.util.StepIOKeys;
import net.sf.reportengine.util.AlgoIOKeys;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author dragos balan (dragos.balan@gmail.com)
 *
 * used to test the changes made in any of the below classes :
 * ThirdCalculatorStep, CalculatorsPack, GroupCalculator
 * 
 */
public class TestTotalsCalculatorStep {
    
    private TotalsCalculatorStep classUnderTest;
    
    
    @Test	
    public void testExecuteScenario1() {
    	AlgoContext reportContext = new DefaultAlgorithmContext(); 
    	Map<AlgoIOKeys, Object> mockAlgoInput = new EnumMap<AlgoIOKeys, Object>(AlgoIOKeys.class); 
    	
    	classUnderTest = new TotalsCalculatorStep();
         
        //simulate the level detector
    	mockAlgoInput.put(AlgoIOKeys.DATA_COLS, Scenario1.DATA_COLUMNS); 
    	mockAlgoInput.put(AlgoIOKeys.GROUP_COLS, Scenario1.GROUPING_COLUMNS); 
    	
    	reportContext.set(StepIOKeys.NEW_GROUPING_LEVEL, Scenario1.AGG_COLUMNS_INDEX);
        
    	StepResult<CalcIntermResult[][]> initStepResult = classUnderTest.init(new StepInput(mockAlgoInput, reportContext)); 
    	reportContext.set(StepIOKeys.CALC_INTERM_RESULTS, initStepResult.getValue());
    	   	
    	//simulate the level detector
    	reportContext.set(StepIOKeys.NEW_GROUPING_LEVEL, Scenario1.ROW_1_AGG_LEVEL);
    	
    	NewRowEvent dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_1);
    	//reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_1);
		classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, reportContext));
		
		CalcIntermResult[][] calcResults = initStepResult.getValue(); //(CalcIntermResult[][])reportContext.get(ContextKeys.CALC_INTERM_RESULTS);
		Assert.assertNotNull(calcResults);
		Assert.assertEquals(calcResults.length, Scenario1.AGG_COLUMNS_INDEX.length + 1 /* for Grand total*/);
		
		assertEqualsCalculatorResults(Scenario1.ROW_1_CALCULATORS_RESULTS, 
				(CalcIntermResult[][])reportContext.get(StepIOKeys.CALC_INTERM_RESULTS));
		
		//simulate level detector
		reportContext.set(StepIOKeys.NEW_GROUPING_LEVEL, Scenario1.ROW_2_AGG_LEVEL);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_2);
		//reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_2);
		classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, reportContext));
		
		assertEqualsCalculatorResults(Scenario1.ROW_2_CALCULATORS_RESULTS, 
				(CalcIntermResult[][])reportContext.get(StepIOKeys.CALC_INTERM_RESULTS));
		
		//simulate level detector
		reportContext.set(StepIOKeys.NEW_GROUPING_LEVEL, Scenario1.ROW_3_AGG_LEVEL);
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_3);
		//reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_3);
		classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, reportContext));
		
		assertEqualsCalculatorResults(Scenario1.ROW_3_CALCULATORS_RESULTS, 
				(CalcIntermResult[][])reportContext.get(StepIOKeys.CALC_INTERM_RESULTS));
		
		//simulate level detector
		reportContext.set(StepIOKeys.NEW_GROUPING_LEVEL, Scenario1.ROW_4_AGG_LEVEL);
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_4);
		//reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_4);
		classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, reportContext));
		
		assertEqualsCalculatorResults(Scenario1.ROW_4_CALCULATORS_RESULTS, 
				(CalcIntermResult[][])reportContext.get(StepIOKeys.CALC_INTERM_RESULTS));
		
		//simulate level detector
		reportContext.set(StepIOKeys.NEW_GROUPING_LEVEL, Scenario1.ROW_5_AGG_LEVEL);
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_5);
		//reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_5);
		classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, reportContext));
		
		assertEqualsCalculatorResults(Scenario1.ROW_5_CALCULATORS_RESULTS, 
				(CalcIntermResult[][])reportContext.get(StepIOKeys.CALC_INTERM_RESULTS));
		
		//simulate level detector
		reportContext.set(StepIOKeys.NEW_GROUPING_LEVEL, Scenario1.ROW_6_AGG_LEVEL);
		//reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_6);
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_6);
		classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, reportContext));
		
		assertEqualsCalculatorResults(Scenario1.ROW_6_CALCULATORS_RESULTS, 
				(CalcIntermResult[][])reportContext.get(StepIOKeys.CALC_INTERM_RESULTS));
    }   
    
    @Test
    public void testExecuteScenario2(){
    	AlgoContext reportContext = new DefaultAlgorithmContext();
    	Map<AlgoIOKeys, Object> mockAlgoInput = new EnumMap<AlgoIOKeys, Object>(AlgoIOKeys.class); 
    	
    	classUnderTest = new TotalsCalculatorStep();
         
        //simulate the level detector
    	//reportContext.set(ContextKeys.DATA_COLUMNS, Scenario2.DATA_COLUMNS);
    	//reportContext.set(ContextKeys.GROUP_COLUMNS, Scenario2.GROUPING_COLUMNS);
    	mockAlgoInput.put(AlgoIOKeys.DATA_COLS, Scenario2.DATA_COLUMNS); 
    	mockAlgoInput.put(AlgoIOKeys.GROUP_COLS, Scenario2.GROUPING_COLUMNS);
    	
    	StepResult<CalcIntermResult[][]> initStepResult = classUnderTest.init(new StepInput(mockAlgoInput, reportContext)); 
    	reportContext.set(StepIOKeys.CALC_INTERM_RESULTS, initStepResult.getValue());
    	 
        for(int i=0; i<CalculatedColumnsScenario.RAW_DATA.length; i++){
        	//simulate the level detector and all other preceeding steps
        	reportContext.set(StepIOKeys.NEW_GROUPING_LEVEL,Scenario2.AGG_LEVEL[i]);
        	//reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario2.COMPUTED_INPUT[i]);
        	
        	//call execute
        	NewRowEvent dataRowEvent = new NewRowEvent(Scenario2.RAW_INPUT[i]);
    		classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, reportContext));
        }
        
        assertEqualsCalculatorResults(Scenario2.CALCULATORS_RESULTS, 
        		(CalcIntermResult[][])reportContext.get(StepIOKeys.CALC_INTERM_RESULTS));
    }
    
    @Test
    public void testExecuteCalculatedColumnsScenario(){
    	AlgoContext reportContext = new DefaultAlgorithmContext();
    	Map<AlgoIOKeys, Object> mockAlgoInput = new EnumMap<AlgoIOKeys, Object>(AlgoIOKeys.class); 
    	
    	classUnderTest = new TotalsCalculatorStep();
         
        //simulate the level detector
    	mockAlgoInput.put(AlgoIOKeys.DATA_COLS, CalculatedColumnsScenario.DATA_COLUMNS); 
    	mockAlgoInput.put(AlgoIOKeys.GROUP_COLS, CalculatedColumnsScenario.GROUP_COLUMNS);
    	
    	StepResult<CalcIntermResult[][]> initStepResult = classUnderTest.init(new StepInput(mockAlgoInput, reportContext)); 
    	reportContext.set(StepIOKeys.CALC_INTERM_RESULTS, initStepResult.getValue());     
    	 
        for(int i=0; i<CalculatedColumnsScenario.RAW_DATA.length; i++){
        	//simulate the level detector and all other preceeding steps
        	reportContext.set(StepIOKeys.NEW_GROUPING_LEVEL, CalculatedColumnsScenario.AGG_LEVEL[i]);
        	//reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, CalculatedColumnsScenario.COMPUTED_VALUES[i]);
        	
        	//call execute
        	NewRowEvent dataRowEvent = new NewRowEvent(CalculatedColumnsScenario.RAW_DATA[i]);
    		classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, reportContext));
        }
        
        assertEqualsCalculatorResults(CalculatedColumnsScenario.CALCULATORS_RESULTS, 
        		(CalcIntermResult[][])reportContext.get(StepIOKeys.CALC_INTERM_RESULTS));
    }
    
    private void assertEqualsCalculatorResults(CalcIntermResult[][] expectedValues, CalcIntermResult[][] actualCalcMatrix){
		//CalcIntermResult[][] calcMatrix = (CalcIntermResult[][])TEST_REPORT_CONTEXT.get(ContextKeys.CALC_INTERM_RESULTS);
		Assert.assertEquals(expectedValues.length, actualCalcMatrix.length);
		for(int i=0; i< expectedValues.length; i++){
			Assert.assertEquals(expectedValues[i].length, actualCalcMatrix[i].length);
			for(int j=0; j < expectedValues[i].length; j++){
				Assert.assertEquals(expectedValues[i][j].getResult(), actualCalcMatrix[i][j].getResult());
			}
		}
	}
}
