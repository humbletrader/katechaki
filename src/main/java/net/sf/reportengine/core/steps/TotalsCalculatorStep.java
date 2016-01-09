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

import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.CalcIntermResult;
import net.sf.reportengine.util.CalculatorIntermResultsMatrix;
import net.sf.reportengine.util.StepIOKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * this step is responsible for :
 * 	1. initializing / reinitializing the totals
 * 	2. computing the totals
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public class TotalsCalculatorStep extends AbstractReportStep<CalcIntermResult[][], String, String>{
    
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TotalsCalculatorStep.class);
	
	/**
     * the calculators matrix
     */
    private CalculatorIntermResultsMatrix calculatorMatrix;
    
    /**
     * 
     */
    private int groupColsCnt = -1;
    
    
    /**
     * init
     */
    public StepResult<CalcIntermResult[][]> init(StepInput stepInput){
        groupColsCnt = getGroupColumnsCount(stepInput); 
        
        if(groupColsCnt == 0){
        	LOGGER.warn("Dangerous setting: TotalsCalculatorStep was set in a report not having group columns");
        }
        
        //groupColsCnt +1 (for Grand Total)
        calculatorMatrix = new CalculatorIntermResultsMatrix(groupColsCnt + 1, getDataColumns(stepInput));
        calculatorMatrix.initAll();
        
        //getAlgoContext().set(ContextKeys.CALC_INTERM_RESULTS, calculatorMatrix.getIntermResultsMatrix());
        return new StepResult(StepIOKeys.CALC_INTERM_RESULTS, calculatorMatrix.getIntermResultsMatrix()); 
    }
    
    /**
     * execute
     */
    public StepResult<String> execute(NewRowEvent newRowEvent, StepInput stepInput){
		int aggLevel = getGroupingLevel(stepInput);
		if(aggLevel >= 0){
			//if the current row is not a simple data row but contains a change in the grouping level 
			int rowsToBeReinitialized = groupColsCnt - aggLevel ;
			LOGGER.trace(	"reinitializing totals for {} rows in the totals table (aggLevel = {}",
							rowsToBeReinitialized, aggLevel);
			calculatorMatrix.initFirstXRows(rowsToBeReinitialized);
		}    		
		//add values to all calculators
		calculatorMatrix.addValuesToEachRow(newRowEvent);
		return StepResult.NO_RESULT; 
    } 
    
    public StepResult<String> exit(StepInput stepInput){
    	return StepResult.NO_RESULT; 
    }
}
