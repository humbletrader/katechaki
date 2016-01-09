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
package net.sf.reportengine.core.steps.crosstab;

import java.util.List;

import net.sf.reportengine.config.PivotHeaderRow;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.util.StepIOKeys;
import net.sf.reportengine.util.DefaultDistinctValuesHolder;
import net.sf.reportengine.util.DistinctValuesHolder;
import net.sf.reportengine.util.AlgoIOKeys;


/**
 * Mainly this step detects the distinct values appearing on crosstabHeadersRows.
 * Another task of this step is to construct the intermediate crosstab data info object 
 * containing the value of the crosstabData and the position of the crosstabData 
 * relative to the crosstab header rows. Few details about this array below: 
 *
 * this is an array holding the coordinates for the current crosstabData 
 * relative to the rows of the header. For instance, let's assume we have the following 
 * row header: 
 * 		level 0: key=Region		distinctValues= North, South, East, West
 * 		level 1: key=Sex		distinctValues=	Males, Females
 * 
 * and let's assume the current row is : Argentina, East, Females, 100 
 * (where Argentina is on a group column and North and Females are declared on headerRows, 100 - crosstab data) 
 * 
 * for this row the  relative position to the row header will be : 
 * 	index 0: 2 ( because East is the third in the Region distinct values)
 *  index 1: 1 ( because Females is the second in the Sex distinct values)
 *  
 *  so the array will look like [2,1]
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class DistinctValuesDetectorStep extends AbstractCrosstabStep<DistinctValuesHolder, IntermediateDataInfo, String> {
	
	
	/**
	 * the distinct values holder
	 */
	private DistinctValuesHolder distinctValuesHolder = null; 
	
	
    public StepResult<DistinctValuesHolder> init(StepInput stepInput){
        List<PivotHeaderRow> headerRows = getCrosstabHeaderRows(stepInput);
        distinctValuesHolder = new DefaultDistinctValuesHolder(headerRows);
        return new StepResult<DistinctValuesHolder>(StepIOKeys.INTERMEDIATE_DISTINCT_VALUES_HOLDER, 
        											distinctValuesHolder); 
    }
	
	/**
	 * 
	 */
	public StepResult<IntermediateDataInfo> execute(NewRowEvent newRowEvent, StepInput stepInput) {
		List<PivotHeaderRow> headerRows = getCrosstabHeaderRows(stepInput);
		
		//first we take care of the distinct values that might occur 
		int indexAfterInsertion = -1; 
		int[] currDataValueRelativePositionToHeaderValues = new int[headerRows.size()]; 
		
		for (int i = 0; i < headerRows.size(); i++) {
			PivotHeaderRow headerRow = headerRows.get(i);
			
			//add value even if it's not a different value we call this method 
			//for getting the index of the value in the distinct values array
			indexAfterInsertion = distinctValuesHolder.addValueIfNotExist(i, headerRow.getValue(newRowEvent));
			
			//once we have the position we add it in the relative position array
			currDataValueRelativePositionToHeaderValues[i] = indexAfterInsertion; 
		}
		
		IntermediateDataInfo intermDataInfo = new IntermediateDataInfo(getCrosstabData(stepInput).getValue(newRowEvent), 
																		currDataValueRelativePositionToHeaderValues); 
//		getAlgoContext().set(ContextKeys.INTERMEDIATE_CROSSTAB_DATA_INFO, 
//						new IntermediateDataInfo(getCrosstabData(stepInput).getValue(newRowEvent), 
//												currDataValueRelativePositionToHeaderValues)); 
		return new StepResult<IntermediateDataInfo>(StepIOKeys.INTERMEDIATE_CROSSTAB_DATA_INFO, intermDataInfo); 
	}
	
	public StepResult<String> exit(StepInput stepInput) {
		return StepResult.NO_RESULT;
	}
	
	
}