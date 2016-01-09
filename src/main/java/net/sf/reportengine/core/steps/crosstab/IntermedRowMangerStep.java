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

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.CalcIntermResult;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.out.IntermediateCrosstabOutput;
import net.sf.reportengine.util.StepIOKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the manager of the intermediate crosstab row. 
 * The intermediate crosstab row holds CrosstabData (and some other useful info) until
 * the grouping level is changed. At this moment the intermediate manager 
 * is resetting the intermediate row. 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class IntermedRowMangerStep extends AbstractCrosstabStep<IntermediateReportRow,String,String> {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(IntermedRowMangerStep.class);
	
	/**
	 * this is an intermediate line containing values ( plus values meta-data like position of the value relative to headerrows values). 
	 * and totals ( plus totals metadata)
	 * The array is refreshed only when the grouping level is changed
	 *
	 */
	private IntermediateReportRow intermediateRow = new IntermediateReportRow(); 
	
	
	/**
	 * 
	 */
	public StepResult<IntermediateReportRow> init(StepInput stepInput){
		return new StepResult<IntermediateReportRow>(StepIOKeys.INTERMEDIATE_ROW, intermediateRow);
	}
	
	private int getIntermGroupColsLength(StepInput stepInput){
		return ((List<GroupColumn>)stepInput.getContextParam(StepIOKeys.INTERNAL_GROUP_COLS)).size(); 
	}
	
	private int getIntermDataColsLength(StepInput stepInput){
		return ((List<DataColumn>)stepInput.getContextParam(StepIOKeys.INTERNAL_DATA_COLS)).size(); 
	}
	
	/**
     * computes the row number (from the calculators matrix) where the totals are for the given level
     * @param level		the aggregation level
     * @return
     */
    @Override 
    public int computeCalcRowNumberForAggLevel(StepInput stepInput, int level){
    	return getIntermGroupColsLength(stepInput) - level -1;
    }
    
    /**
     * computes the aggregation level for the given row of the calculators matrix
     * @param calcRowNumber
     * @return
     */
    @Override 
    public int computeAggLevelForCalcRowNumber(StepInput stepInput, int calcRowNumber){
    	return getIntermGroupColsLength(stepInput) - calcRowNumber - 1;
    }
	
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.AbstractReportStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)
	 */
	public StepResult<String> execute(NewRowEvent rowEvent, StepInput stepInput) {
		//TODO: try to simplify this class (it is pretty complex as compared to the other step classes)
		int groupingLevel = getGroupingLevel(stepInput); 
		
		if(groupingLevel >= 0){
			//if grouping level changed
			
			CalcIntermResult[][] calcResults = getCalcIntermResultsMatrix(stepInput); 
			int originalGroupColsLength = getGroupColumnsCount(stepInput);//getOriginalCrosstabGroupingColsLength();  
			int originalDataColsLength = getDataColumnsLength(stepInput); //getOriginalCrosstabDataColsLength();
			
			if(groupingLevel < originalGroupColsLength + originalDataColsLength){
				//this is a change in the original group so...
				
				//First we update all remaining totals (if the report contains totals)
				if(getShowTotals(stepInput) || getShowGrandTotal(stepInput)){
					//we don't need all totals. From the groupingColumns we take only the first one
					updateIntermediateTotals(	stepInput, 
												originalGroupColsLength+originalDataColsLength-1, 
												getIntermGroupColsLength(stepInput), //getGroupColumnsLength() , 
												calcResults);
				}
				//Second: we display the intermediate row
				addOriginalGroupAndDataColumnsInfoToIntermRow(stepInput, intermediateRow);
				writeIntermediateRow(stepInput, intermediateRow);
				
				//Third: reset the array
				intermediateRow.emptyRow(); 
			}else{
				//if grouping level changed for the crosstabHeaderRows 
				
				if(getShowTotals(stepInput) || getShowGrandTotal(stepInput)){
					updateIntermediateTotals(	stepInput, 
												groupingLevel, 						//from the current grouping level 
												getIntermGroupColsLength(stepInput), //to the last intermediate grouping col
												calcResults);
				}
			}
		}
		//and finally for each row we add new constructed data 
		intermediateRow.addIntermComputedData(getIntermediateCrosstabDataInfo(stepInput));
		return StepResult.NO_RESULT; 
	}
	
	
	/**
	 * 
	 */
	public StepResult<String> exit(StepInput stepInput){
		if(getShowTotals(stepInput) || getShowGrandTotal(stepInput)){
			int originalGroupingColsLength = getGroupColumnsCount(stepInput);  
			int originalDataColsLength = getDataColumnsLength(stepInput); 
			updateIntermediateTotals(	stepInput, 
										originalGroupingColsLength + originalDataColsLength-1, //from the last original grouping col
										getIntermGroupColsLength(stepInput),//getGroupColumnsLength() ,  //to the last intermediate grouping col (containing also the headers)
										getCalcIntermResultsMatrix(stepInput));
		}
		
		intermediateRow.setLast(true); 
		addOriginalGroupAndDataColumnsInfoToIntermRow(stepInput, intermediateRow);
		writeIntermediateRow(stepInput, intermediateRow); 
		intermediateRow = null;//clean up
		return StepResult.NO_RESULT; 
	}
	
	
	private void updateIntermediateTotals(	StepInput stepInput, 
											int levelFrom, 
											int levelTo, 
											CalcIntermResult[][] calcResults){
		int calculatorMatrixRow = -1;
		Object calculatorResult = null; 
		//int tmpLevelFrom = getOriginalCrosstabGroupingColsLength()+ getOriginalCrosstabDataColsLength();
		int tmpLevelFrom = getGroupColumnsCount(stepInput)+ getDataColumnsLength(stepInput); 
		
		for (int tempGrpLevel = levelFrom; tempGrpLevel < levelTo; tempGrpLevel++) {
			calculatorMatrixRow = computeCalcRowNumberForAggLevel(stepInput, tempGrpLevel); //getGroupingColumnsLength() - tempGrpLevel -1; 
			Object[] totalStrings = getTotalStringForGroupingLevelAndPredecessors(stepInput, tmpLevelFrom, tempGrpLevel);
			int[] position = getPositionOfTotal(stepInput, tmpLevelFrom, tempGrpLevel);
			
			//our intermediate report has only one column containing calculators 
			//(the column containing crosstab data) therefore we have only one column 
			//in the calculator matrix (see the zero below)
			calculatorResult = calcResults[calculatorMatrixRow][0].getResult(); 
			intermediateRow.addIntermTotalsInfo(new IntermediateTotalInfo(	calculatorResult, 
																			position, 
																			totalStrings));
		}
	}
	
	
	private void addOriginalGroupAndDataColumnsInfoToIntermRow(StepInput stepInput, IntermediateReportRow intermediateRow){
//		if(ReportUtils.DEBUG){
//			getReportOutput(stepInput).startDataRow(new RowProps());
//			getReportOutput(stepInput).outputDataCell(new CellProps.Builder("Intermediate row:").build());
//		}
		
		Integer originalGroupingValuesLength = getGroupColumnsCount(stepInput); //getOriginalCrosstabGroupingColsLength();
		Integer originalDataValuesLength = getDataColumnsLength(stepInput); //getOriginalCrosstabDataColsLength(); 
		Object[] previousGroupValues = getPreviousRowOfGroupValues(stepInput); 
		
		LOGGER.debug("first: adding {} grouping values to intermediate row ", originalGroupingValuesLength); 
		//although we have more values in the previous grouping values we display only the original ones
		//because they are further needed in the second iteration
		for (int i=0; i<originalGroupingValuesLength; i++) {
//			if(ReportUtils.DEBUG){
//				getReportOutput(stepInput).outputDataCell(new CellProps.Builder(previousGroupValues[i]).build());
//			}
			intermediateRow.addOrigGroupValue(previousGroupValues[i]);
		}
		LOGGER.debug("second: adding {} data values to intermediate row", originalDataValuesLength);
		for(int i=0; i<originalDataValuesLength; i++){
			Object prevValue = previousGroupValues[originalGroupingValuesLength+i];
			intermediateRow.addOrigDataColValue(prevValue);
		}
		
//		if(ReportUtils.DEBUG){
//			for (IntermediateDataInfo element : intermediateRow.getIntermComputedDataList().getDataList()) {
//				getReportOutput(stepInput).outputDataCell(new CellProps.Builder(element.toString()).build());
//			}
//		
//			for (IntermediateTotalInfo totalInfo : intermediateRow.getIntermComputedTotalsList().getTotalsDataList()) {
//				getReportOutput(stepInput).outputDataCell(new CellProps.Builder(totalInfo.toString()).build());
//			}
//			getReportOutput(stepInput).endDataRow(); 
//		}
	}
	
	
	private void writeIntermediateRow(StepInput stepInput, IntermediateReportRow intermediateRow){
		IntermediateCrosstabOutput output = getIntermCrosstabOutput(stepInput); 
		output.writeIntermRow(intermediateRow); 
	}
}
