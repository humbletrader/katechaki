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

import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.util.StepIOKeys;
import net.sf.reportengine.util.AlgoIOKeys;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * this computes the aggregation level for the current dataEvent. 
 * The protocol is : 
 * <ul>
 * 		<li>
 * 			when returning the level -1 then a simple data row has been found 
 * 			( none of the aggregation columns has been changed )
 * 		</li>
 * 		<li>
 * 			any level > 0 returned is the index of the first element changed in the 
 * 			aggregation columns.
 * 		</li>
 * </ul>
 * 
 * Example: <br>
 * Let's assume that we have a data matrix like : <br/>
 * <table>
 * 	<tr><td>1</td><td>2</td><td>3</td><td>4</td></tr>
 * 	<tr><td>1</td><td>2</td><td>3</td><td>5</td></tr>
 * 	<tr><td>1</td><td>1</td><td>6</td><td>7</td></tr>
 *  <tr><td>2</td><td>8</td><td>6</td><td>7</td></tr>
 * </table>
 * 
 * and all the columns {0,1,2} as grouping columns
 * 
 * then : <br/>
 * 	when passing the first row to the execute method the result level will be -1 <br>
 *  when passing the second row to the same method the result will be -1 (because none of the columns 0,1,2 have changed)<br>
 *  when passing the third  row to the same method the result will be 1 (because this is the index of the first changed column)<br>
 *  when passing the second row to the same method the result will be 0  <br>
 * </p>
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class GroupLevelDetectorStep extends AbstractReportStep<String, Integer, String>{
    
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupLevelDetectorStep.class);
    
    /**
     * the aggregation level
     */
	private Integer groupLevel = Integer.valueOf(-1); 
	
    
	/**
	 * execute method
	 */
	public StepResult<Integer> execute(NewRowEvent newRowEvent, StepInput stepInput) {
        
		//first time we cannot make any comparison so the return level is zero
		if(getPreviousRowOfGroupValues(stepInput) == null){
			//handle last row for grouping columns 
			groupLevel = NumberUtils.INTEGER_MINUS_ONE;
		}else{
			groupLevel = checkLevelChangedInGroupingColumns(getGroupColumns(stepInput), 
															getPreviousRowOfGroupValues(stepInput), 
															newRowEvent); 
			LOGGER.trace("For newRow={} the grouping level found is {}", newRowEvent, groupLevel);
		}
		
		//set the result in context
		//getAlgoContext().set(ContextKeys.NEW_GROUPING_LEVEL, groupLevel);
		return new StepResult<Integer>(StepIOKeys.NEW_GROUPING_LEVEL, groupLevel); 
	}
    
    
    private Integer checkLevelChangedInGroupingColumns(	List<GroupColumn> groupingColumns, 
    												Object[] lastRowOfGroupingValues, 
    												NewRowEvent newRowEvent){
		boolean newGroupingLevelFound = false;
		int i = 0;
		
		//TODO: groupings assumed ordered by grouping order: make sure they are ordered
		//iterate through last row for comparison with the new row of data
		GroupColumn currentGroupColumn = null; 
		while (!newGroupingLevelFound && i < groupingColumns.size()){
			currentGroupColumn = groupingColumns.get(i); 
			Object valueToBeCompared = currentGroupColumn.getValue(newRowEvent);
			//LOGGER.trace("checking column {} "+currentGroupColumn.getClass()); 
			//LOGGER.trace(" 		having grouping order {}", currentGroupColumn.getGroupingLevel()); 
			//LOGGER.trace(" 		and value {} against {}", valueToBeCompared,lastRowOfGroupingValues[i]);
			if (!lastRowOfGroupingValues[i].equals(valueToBeCompared)) {
				//condition to exit from for loop
				newGroupingLevelFound = true;
			}else{
				i++;
			}           
		}// end while
		return newGroupingLevelFound ? Integer.valueOf(i) : NumberUtils.INTEGER_MINUS_ONE;  
    }


	public StepResult<String> init(StepInput stepInput) {
		return StepResult.NO_RESULT;
	}


	public StepResult<String> exit(StepInput stepInput) {
		return StepResult.NO_RESULT;
	}
}