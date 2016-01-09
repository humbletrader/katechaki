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
import net.sf.reportengine.components.CellProps;
import net.sf.reportengine.components.RowProps;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.config.SecondProcessDataColumn;
import net.sf.reportengine.config.SecondProcessDataColumnFromOriginalDataColumn;
import net.sf.reportengine.config.SecondProcessTotalColumn;
import net.sf.reportengine.core.steps.AbstractOutputInitStep;
import net.sf.reportengine.core.steps.ColumnHeaderOutputInitStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.util.StepIOKeys;
import net.sf.reportengine.util.CtMetadata;
import net.sf.reportengine.util.AlgoIOKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * displays the column headers for the crosstab report
 * 
 * @author dragos balan
 * @since 0.4
 */
public class CrosstabHeaderOutputInitStep extends AbstractOutputInitStep<String>{
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CrosstabHeaderOutputInitStep.class);
	
	/**
	 * 
	 */
	public StepResult<String> init(StepInput stepInput){
//		outputTitle(getReportTitle(stepInput), 
//					getDataColumnsLength(stepInput) + getGroupColumnsLength(stepInput), 
//					getReportOutput(stepInput));
		outputHeaderRows(	stepInput, 
							getCrosstabMetadata(stepInput), 
							getDataColumns(stepInput), 
							getGroupColumns(stepInput),
							getCrosstabData(stepInput)); 
		return StepResult.NO_RESULT; 
	}
	
	protected CtMetadata getCrosstabMetadata(StepInput stepInput){
		return (CtMetadata)stepInput.getContextParam(StepIOKeys.CROSSTAB_METADATA);
	}
	
	public PivotData getCrosstabData(StepInput stepInput){
		 return (PivotData)stepInput.getAlgoInput(AlgoIOKeys.CROSSTAB_DATA); 
	}
	
	@Override 
	public List<DataColumn> getDataColumns(StepInput stepInput){
		return (List<DataColumn>)stepInput.getContextParam(StepIOKeys.INTERNAL_DATA_COLS); 
	}
	
	@Override 
	public List<GroupColumn> getGroupColumns(StepInput stepInput){
		return (List<GroupColumn>)stepInput.getContextParam(StepIOKeys.INTERNAL_GROUP_COLS); 
	}
	
	/**
	 * 
	 * @param title
	 * @param reportOutput
	 */
//	private void outputTitle(	String reportTitle, 
//								int colspan,  
//								ReportOutput reportOutput){
//		if(reportTitle != null){
//        	reportOutput.outputTitle(new TitleProps(reportTitle, colspan));
//        }
//	}
	
	/**
	 * 
	 * @param reportOutput
	 * @param ctMetadata
	 * @param dataCols
	 * @param groupCols
	 */
	private void outputHeaderRows(	StepInput stepInput, 
									CtMetadata ctMetadata, 
									List<DataColumn> dataCols, 
									List<GroupColumn> groupCols, 
									PivotData ctData){
		//loop through all header rows
		for (int currHeaderRow = 0; currHeaderRow < ctMetadata.getHeaderRowsCount(); currHeaderRow++) {
			//reportOutput.startHeaderRow(new RowProps(currHeaderRow)); 
			 outputOneValue(stepInput, 
					 		ColumnHeaderOutputInitStep.START_HEADER_ROW_TEMPLATE, 
					 		new RowProps(currHeaderRow));
			 
			boolean isLastHeaderRow = currHeaderRow == ctMetadata.getHeaderRowsCount()-1; 
			
			//1. handle grouping columns header first 
			displayHeaderForGroupingCols(	stepInput, 
											groupCols, 
											isLastHeaderRow, 
											currHeaderRow);
			
			//2. now loop through data columns
			int currentColumn = 0; 
			while(currentColumn < dataCols.size()){
				DataColumn currentDataColumn = dataCols.get(currentColumn);
				
				//if this column is a column created during 
				if(currentDataColumn instanceof SecondProcessDataColumn){
					int colspan = displayDataColumnHeader(	stepInput,
															(SecondProcessDataColumn)currentDataColumn,
															ctMetadata, 
															currHeaderRow, 
															isLastHeaderRow);
					currentColumn += colspan; 
				}else{
					if(currentDataColumn instanceof SecondProcessTotalColumn){
						displayHeaderForTotalColumn(stepInput,
													(SecondProcessTotalColumn)currentDataColumn,
													ctMetadata, 
													currHeaderRow, 
													ctData.getCalculator().getLabel());
						currentColumn++;
					}else{
						if(currentDataColumn instanceof SecondProcessDataColumnFromOriginalDataColumn){
							displayHeaderForOriginalDataColumn(	stepInput,
																currentDataColumn, 
																isLastHeaderRow, 
																currHeaderRow);
							currentColumn++; 
						}else{
							//no other type of data column is accepted
							throw new IllegalArgumentException("there's no handler for "+currentDataColumn.getClass());
						}
					}
				}
			}//end while 
			
			//reportOutput.endHeaderRow(); 
			outputNoValue(stepInput, ColumnHeaderOutputInitStep.END_HEADER_ROW_TEMPLATE); 
		}
	}

	/**
	 * displays the headers for group columns
	 * @param groupCols
	 * @param reportOutput
	 * @param isLastHeaderRow
	 */
	private void displayHeaderForGroupingCols(	StepInput stepInput, 
												List<GroupColumn> groupCols,
												boolean isLastHeaderRow,
												int rowNumber) {
		//if last header row write the normal column headers
		if(groupCols != null && groupCols.size() > 0){
			if(isLastHeaderRow){
				//for group columns only the last header row will contain something 
				// the first will be empty	
				for (int i = 0; i < groupCols.size(); i++) {
					CellProps cellProps = new CellProps.Builder(groupCols.get(i).getHeader()).colspan(1).horizAlign(HorizAlign.CENTER).rowNumber(rowNumber).build();
					//reportOutput.outputHeaderCell(cellProps);
					outputOneValue(	stepInput, 
									ColumnHeaderOutputInitStep.HEADER_CELL_TEMPLATE, 
									cellProps); 
				}
			}else{
				//first header rows will contain only spaces (for group headers):
				for (int i = 0; i < groupCols.size(); i++) {
					CellProps cellProps = new CellProps.Builder(CellProps.WHITESPACE).rowNumber(rowNumber).build();
					//reportOutput.outputDataCell(cellProps); 
					outputOneValue(	stepInput, 
									ColumnHeaderOutputInitStep.HEADER_CELL_TEMPLATE, 
									cellProps); 
				}
			}
		}else{
			LOGGER.debug("no group columns headers found"); 
		}		
	}

	/**
	 * displays the header for the original data columns
	 * @param currentDataColumn
	 * @param reportOutput
	 * @param isLastHeaderRow
	 */
	private void displayHeaderForOriginalDataColumn(	StepInput stepInput,
														DataColumn currentDataColumn, 
														boolean isLastHeaderRow, 
														int rowNumber) {
		//only on the last header row we display the header values for the original data columns
		if(isLastHeaderRow){
			SecondProcessDataColumnFromOriginalDataColumn originalDataColumn = (SecondProcessDataColumnFromOriginalDataColumn)currentDataColumn;
			CellProps cellProps = new CellProps.Builder(originalDataColumn.getHeader()).colspan(1).horizAlign(HorizAlign.CENTER).rowNumber(rowNumber).build();
			//reportOutput.outputHeaderCell(cellProps); 
			outputOneValue(	stepInput, 
							ColumnHeaderOutputInitStep.HEADER_CELL_TEMPLATE, 
							cellProps);
		}else{
			//first header rows will contain empty cells
			//reportOutput.outputDataCell(new CellProps.Builder(ReportOutput.WHITESPACE).rowNumber(rowNumber).build());
			outputOneValue(	stepInput, 
							ColumnHeaderOutputInitStep.HEADER_CELL_TEMPLATE, 
							new CellProps.Builder(CellProps.WHITESPACE).rowNumber(rowNumber).build());
		}
	}

	/**
	 * displays the headers for data columns of type SecondProcessTotalColumn
	 * 
	 * @param secondProcessTotalCol
	 * @param reportOutput
	 * @param ctMetadata
	 * @param currHeaderRow
	 */
	private void displayHeaderForTotalColumn(	StepInput stepInput, 
												SecondProcessTotalColumn secondProcessTotalCol,
												CtMetadata ctMetadata, 
												int currHeaderRow, 
												String totalLabel) {
		int[] position = secondProcessTotalCol.getPosition();
		
		if(position != null){
			if(currHeaderRow < position.length){
				Object value = ctMetadata.getDistincValueFor(currHeaderRow, position[currHeaderRow]);
				CellProps cellProps = new CellProps.Builder(value).colspan(1).horizAlign(secondProcessTotalCol.getHorizAlign()).rowNumber(currHeaderRow).build();
				//reportOutput.outputHeaderCell(cellProps);
				outputOneValue(	stepInput, 
								ColumnHeaderOutputInitStep.HEADER_CELL_TEMPLATE, 
								cellProps); 
			}else{
				//if there's no position for this header row then this is a hard-coded "TOTAL" 
				if(currHeaderRow == position.length){
					CellProps cellProps = new CellProps.Builder(totalLabel).horizAlign(HorizAlign.CENTER).rowNumber(currHeaderRow).build();
					//reportOutput.outputHeaderCell(cellProps);
					outputOneValue(	stepInput, 
									ColumnHeaderOutputInitStep.HEADER_CELL_TEMPLATE, 
									cellProps);
				}else{
					//reportOutput.outputDataCell(new CellProps.Builder(ReportOutput.WHITESPACE).rowNumber(currHeaderRow).build());
					outputOneValue(	stepInput, 
									ColumnHeaderOutputInitStep.HEADER_CELL_TEMPLATE, 
									new CellProps.Builder(CellProps.WHITESPACE).rowNumber(currHeaderRow).build());
				}
			}
		}else{
			//the only data column that has null positions is the grand total column
			if(currHeaderRow == 0){
				CellProps cellProps = new CellProps.Builder("Grand "+totalLabel).horizAlign(HorizAlign.LEFT).rowNumber(currHeaderRow).build();
				//reportOutput.outputHeaderCell(cellProps);
				outputOneValue(	stepInput, 
								ColumnHeaderOutputInitStep.HEADER_CELL_TEMPLATE, 
								cellProps);
			}else{
				//reportOutput.outputDataCell(CellProps.EMPTY_CELL);
				outputOneValue(	stepInput, 
								ColumnHeaderOutputInitStep.HEADER_CELL_TEMPLATE, 
								CellProps.buildEmptyCell());
			}
		}
	}

	/**
	 * displays the column header for objects of type SecondProcessDataColumn
	 * 
	 * @param secondProcDataColumn
	 * @param reportOutput
	 * @param ctMetadata
	 * @param currHeaderRow
	 * @param isLastHeaderRow
	 * 
	 * @return	the colspan
	 */
	private int displayDataColumnHeader(StepInput stepInput, 
										SecondProcessDataColumn secondProcDataColumn,
										CtMetadata ctMetadata, 
										int currHeaderRow, 
										boolean isLastHeaderRow ) {
		int colspan = 1;
		if(!isLastHeaderRow){
			//for all rows except the last header row we read the colspan
			colspan = ctMetadata.getColspanForLevel(ctMetadata.getHeaderRowsCount()-2);
		}
		
		Object value = ctMetadata.getDistincValueFor(currHeaderRow, secondProcDataColumn.getPosition()[currHeaderRow]);
		CellProps cellProps = new CellProps.Builder(value).colspan(colspan).horizAlign(secondProcDataColumn.getHorizAlign()).rowNumber(currHeaderRow).build(); 
		//reportOutput.outputHeaderCell(cellProps);
		outputOneValue(	stepInput, 
						ColumnHeaderOutputInitStep.HEADER_CELL_TEMPLATE, 
						cellProps);
		return colspan;
	}
}