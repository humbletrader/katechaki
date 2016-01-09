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
import java.util.List;

import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.util.StepIOKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * this is the manager of the previous row. Normally this should be the last 
 * step in your algorithm
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 *
 */
public class PreviousRowManagerStep extends AbstractReportStep<String, Object[], String> {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PreviousRowManagerStep.class);
	
	/**
	 * reference to the last row (this is very helpful since this class makes 
	 * comparisons between the current row and the last row
	 */
	private Object[] previousRowOfGroupingColumnValues;
    
	/**
	 * 
	 */
	public StepResult<Object[]> execute(NewRowEvent rowEvent, StepInput stepInput) {
		
		StepResult<Object[]> result = null; 
		//first pass : initialize the last column values
		if(previousRowOfGroupingColumnValues == null){
			previousRowOfGroupingColumnValues = new Object[getGroupColumnsCount(stepInput)];
			copyGroupingValuesToPrevRowOfGrpValues(getGroupColumns(stepInput), rowEvent);
			
			//getAlgoContext().set(ContextKeys.LAST_GROUPING_VALUES, previousRowOfGroupingColumnValues);
			result = new StepResult(StepIOKeys.LAST_GROUPING_VALUES, previousRowOfGroupingColumnValues); 
		}else{
			if(getGroupingLevel(stepInput) > -1){
				copyGroupingValuesToPrevRowOfGrpValues(getGroupColumns(stepInput), rowEvent);
			}
		}
		
		//cache the previous grouping level
    	LOGGER.trace("previousRowOfGroupingValues {}", Arrays.toString(previousRowOfGroupingColumnValues));
    	return result; 
    	
	}
	
	/**
	 * copies the current row values into the previousValues array
	 * @param groupingCols
	 * @param newRowEvent
	 */
	private void copyGroupingValuesToPrevRowOfGrpValues(List<GroupColumn> groupingCols, 
														NewRowEvent newRowEvent){
    	for (int i = 0; i < groupingCols.size(); i++) {
    		previousRowOfGroupingColumnValues[i] = groupingCols.get(i).getValue(newRowEvent);
    	}    	
    }

	public StepResult<String> init(StepInput stepInput) {
		return StepResult.NO_RESULT;
	}

	public StepResult<String> exit(StepInput stepInput) {
		return StepResult.NO_RESULT;
	}
}
