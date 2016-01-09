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

import java.util.List;

import net.sf.reportengine.config.PivotData;
import net.sf.reportengine.config.PivotHeaderRow;
import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.out.IntermediateCrosstabOutput;
import net.sf.reportengine.util.StepIOKeys;
import net.sf.reportengine.util.DefaultDistinctValuesHolder;
import net.sf.reportengine.util.AlgoIOKeys;

/**
 * @author dragos balan
 *
 */
public abstract class AbstractCrosstabStep<T,U,V> extends AbstractReportStep<T,U,V> {
	
	public List<PivotHeaderRow> getCrosstabHeaderRows(StepInput stepInput){
		return (List<PivotHeaderRow>)stepInput.getAlgoInput(AlgoIOKeys.CROSSTAB_HEADER_ROWS); 
	}
	 
	 public int getCrosstabHeaderRowsLength(StepInput stepInput){
		 return getCrosstabHeaderRows(stepInput).size();
	 }
		
	 public PivotData getCrosstabData(StepInput stepInput){
		 return (PivotData)stepInput.getAlgoInput(AlgoIOKeys.CROSSTAB_DATA); 
	 }
	 
	 public DefaultDistinctValuesHolder getDistinctValuesHolder(StepInput stepInput){
		 return (DefaultDistinctValuesHolder)stepInput.getContextParam(StepIOKeys.INTERMEDIATE_DISTINCT_VALUES_HOLDER); 
	 }
	 
	 public IntermediateDataInfo getIntermediateCrosstabDataInfo(StepInput stepInput){
		 return (IntermediateDataInfo)stepInput.getContextParam(StepIOKeys.INTERMEDIATE_CROSSTAB_DATA_INFO);
	 }
	 
	 public IntermediateReportRow getIntermediateRow(StepInput stepInput){
		 return (IntermediateReportRow)stepInput.getContextParam(StepIOKeys.INTERMEDIATE_ROW); 
	 }
	 
	 public boolean getShowTotalsInHeader(){
		 return true; //TODO: come back here
	 }
	
	 
	 public int[] getPositionOfTotal(StepInput stepInput, int from, int groupingLevel) {
    	int[] result = null; 
    	if(groupingLevel >= from){
    		result = new int[groupingLevel-from+1];
    		DefaultDistinctValuesHolder ctMetadata = getDistinctValuesHolder(stepInput); 
    		Object[] prevDataRow = getPreviousRowOfGroupValues(stepInput); 
    		if(prevDataRow != null){
    			for(int i=from; i < groupingLevel+1; i++){
    				result[i-from] = ctMetadata.getIndexFor(i-from, prevDataRow[i]);
    			}
    		}else{
    			throw new IllegalArgumentException("Cannot determine the previous grouping values"); 
    		}
    	}
		return result;
	 }
	 
	 /**
	     * 
	     * @param stepInput
	     * @return
	     */
	    public IntermediateCrosstabOutput getIntermCrosstabOutput(StepInput stepInput){
	    	return (IntermediateCrosstabOutput)stepInput.getContextParam(StepIOKeys.INTERMEDIATE_CROSSTAB_OUTPUT); 
	    }
}
