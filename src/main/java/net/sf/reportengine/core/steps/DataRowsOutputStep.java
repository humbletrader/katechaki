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

import java.util.List;

import net.sf.reportengine.components.CellProps;
import net.sf.reportengine.components.RowProps;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.util.StepIOKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dragos balan
 *
 */
public class DataRowsOutputStep extends AbstractOutputStep<String,Integer,String> {
	
	
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DataRowsOutputStep.class);
	
	
	public static final String DATA_CELL_TEMPLATE = "dataCell.ftl";
	public static final String START_DATA_ROW_TEMPLATE = "startDataRow.ftl";
	public static final String END_DATA_ROW_TEMPLATE = "endDataRow.ftl";
	
	
    /**
     * 
     */
	private int finalReportGroupCount = -1;
	private List<GroupColumn> groupCols = null;
	private List<DataColumn> dataColumns = null; 	
	
	
	/**
	 * this step's init method
	 */
	public StepResult<String> init(StepInput stepInput){
		groupCols = getGroupColumns(stepInput);
		dataColumns = getDataColumns(stepInput);
		finalReportGroupCount = getGroupColumnsCount(stepInput);
		return StepResult.NO_RESULT; 
	}
    
	/**
     * Constructs a cell for each value and sends it to output
     */
    public StepResult<Integer> execute(NewRowEvent newRowEvent, StepInput stepInput) {
    	Object[] previousRowGrpValues = getPreviousRowOfGroupValues(stepInput);
		
		//start the row
    	//getReportOutput(stepInput).startDataRow(new RowProps(getDataRowCount(stepInput)));
    	outputOneValue(stepInput, START_DATA_ROW_TEMPLATE, new RowProps(getDataRowCount(stepInput)));
		
		CellProps.Builder cellPropsBuilder = null;
		
		//handle the grouping columns first
		GroupColumn currentGrpCol = null; 
		for(int i=0; i<finalReportGroupCount; i++){
			currentGrpCol = groupCols.get(i);
			Object valueForCurrentColumn = currentGrpCol.getValue(newRowEvent);
			
			if(	currentGrpCol.showDuplicates() 
				|| previousRowGrpValues == null    //it's too early and we don't have prevGroupValues set
				|| getGroupingLevel(stepInput) > -1 			//immediately after a total row
				|| !valueForCurrentColumn.equals(previousRowGrpValues[i])//if this value is different from the prev
				){
				cellPropsBuilder = new CellProps.Builder(
						currentGrpCol.getFormattedValue(valueForCurrentColumn));
			}else{
				cellPropsBuilder = new CellProps.Builder(CellProps.WHITESPACE);
			}
			cellPropsBuilder.horizAlign(currentGrpCol.getHorizAlign())
							.vertAlign(currentGrpCol.getVertAlign())
							.rowNumber(getDataRowCount(stepInput)); 
			//getReportOutput(stepInput).outputDataCell(cellPropsBuilder.build()); 
			outputOneValue(stepInput, DATA_CELL_TEMPLATE, cellPropsBuilder.build()); 
		}
		
		//then handle the data columns
		for(DataColumn dataColumn : dataColumns){
			Object valueForCurrentColumn = dataColumn.getValue(newRowEvent);
			cellPropsBuilder = new CellProps.Builder(dataColumn.getFormattedValue(valueForCurrentColumn))
				.horizAlign(dataColumn.getHorizAlign())
				.vertAlign(dataColumn.getVertAlign()); 
			
			//getReportOutput(stepInput).outputDataCell(cellPropsBuilder.build()); 
			outputOneValue(stepInput, DATA_CELL_TEMPLATE, cellPropsBuilder.build());
		}
    	
		//end row
		//getReportOutput(stepInput).endDataRow();
		outputNoValue(stepInput, END_DATA_ROW_TEMPLATE);
		
		//incrementDataRowNbr(stepInput);
		return new StepResult(StepIOKeys.DATA_ROW_COUNT, getDataRowCount(stepInput)+1); 
    }
    
    public StepResult<String> exit(StepInput stepInput){
    	return StepResult.NO_RESULT; 
    }

}
