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
package net.sf.reportengine.core.steps.intermed;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.reportengine.config.PivotHeaderRow;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.config.SortType;
import net.sf.reportengine.config.VertAlign;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.AbstractReportInitStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.util.StepIOKeys;
import net.sf.reportengine.util.AlgoIOKeys;

public class ConstrIntermedGrpColsInitStep extends AbstractReportInitStep<List<GroupColumn>>{
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ConstrIntermedGrpColsInitStep.class);
	
	/**
	 * 
	 */
	public StepResult<List<GroupColumn>> init(StepInput input) {
		List<DataColumn> originalCtDataCols = getDataColumns(input); 
		List<GroupColumn> originalCtGroupingCols = getGroupColumns(input); 
		List<PivotHeaderRow> originalCtHeaderRows = 
				(List<PivotHeaderRow>)input.getAlgoInput(AlgoIOKeys.CROSSTAB_HEADER_ROWS);
		
		
		List<GroupColumn> newGroupCols = transformGroupingCrosstabConfigInFlatReportConfig(
					originalCtGroupingCols, 
					originalCtDataCols, 
					originalCtHeaderRows); 
		
		return new StepResult<List<GroupColumn>>(StepIOKeys.INTERNAL_GROUP_COLS, newGroupCols);		
	}
	
	/**
	 * transforms the original grouping + data + header columns into intermediate group columns
	 * 
	 * 1. from 0 to original groupCols.length we copy the original group columns
	 * 2. from groupCols.length to groupCols.lenght + dataCols.length we copy construct group columns from data columns
	 * 3. then we copy the header rows (of course transformed as groupCols)
	 * 
	 * @param originalCtGroupingCols
	 * @param originalCtDataCols
	 * @param originalCtHeaderRows
	 * @return
	 */
	List<GroupColumn> transformGroupingCrosstabConfigInFlatReportConfig(	List<? extends GroupColumn> originalCtGroupingCols, 
																			List<? extends DataColumn> originalCtDataCols, 
																			List<? extends PivotHeaderRow> originalCtHeaderRows){
		
		int originalGroupColsLength = originalCtGroupingCols != null ? originalCtGroupingCols.size(): 0;
		int originalDataColsLength = originalCtDataCols != null ? originalCtDataCols.size() : 0 ; 
		
		LOGGER.debug("transforming grouping crosstab config into flat intermediary report: ");
		LOGGER.debug("origCtGroupingCols={}", originalCtGroupingCols);
		LOGGER.debug("originalCtDataRows={}", originalCtDataCols);
		LOGGER.debug("originalCtHeaderRows={}",originalCtHeaderRows);
		
		int intermedGroupColsLength = originalGroupColsLength + originalDataColsLength + originalCtHeaderRows.size() -1;
		List<GroupColumn> result = new ArrayList<GroupColumn>(intermedGroupColsLength);
		
		//from 0 to original groupCols.length we copy the original group columns
		if(originalGroupColsLength > 0){
			//System.arraycopy(originalCtGroupingCols, 0, result, 0, originalCtGroupingCols.size());
			for(int i=0; i<originalCtGroupingCols.size(); i++){
				//result[i] = originalCtGroupingCols.get(i);
				result.add(originalCtGroupingCols.get(i));
			}
		}
		
		//from groupCols.length to groupCols.lenght + dataCols.length we copy construct group columns from data columns
		for(int i=0; i < originalDataColsLength; i++){
			//result[originalGroupColsLength+i] = new IntermGroupColFromCtDataCol(originalCtDataCols.get(i), originalGroupColsLength+i);
			result.add(new IntermGroupColFromCtDataCol(originalCtDataCols.get(i), originalGroupColsLength+i));
		}
		
		//then we copy the header rows (of course transformed as groupCols)
		//we don't need any grouping for the last header row (that's why we have headerRows.length-1 below
		for (int i = 0; i < originalCtHeaderRows.size()-1; i++) {
			//result[originalGroupColsLength+originalDataColsLength+i] =
			result.add(
					new IntermGroupColFromHeaderRow(	originalCtHeaderRows.get(i), 
														originalGroupColsLength+originalDataColsLength+i));
		}
		
		return result; 
	}
	
	/**
	 * 
	 * @author dragos balan
	 *
	 */
	static class IntermGroupColFromCtDataCol implements GroupColumn{
		
		private DataColumn dataColumn; 
		private int groupingLevel; 
		
		public IntermGroupColFromCtDataCol(DataColumn dataColumn, int groupLevel){
			this.dataColumn = dataColumn; 
			this.groupingLevel = groupLevel; 
		}
		
		public String getFormattedValue(Object object) {
			return dataColumn.getFormattedValue(object); 
		}

		public int getGroupingLevel() {
			return groupingLevel;
		}

		public String getHeader() {
			return dataColumn.getHeader(); 
		}

		public Object getValue(NewRowEvent newRowEvent) {
			return dataColumn.getValue(newRowEvent);
		}

		public HorizAlign getHorizAlign() {
			return dataColumn.getHorizAlign(); 
		}
		
		public VertAlign getVertAlign(){
			return dataColumn.getVertAlign(); 
		}
		
		public boolean showDuplicates(){
			return false; //TODO: check if this is used
		}
		
		public SortType getSortType() {
			return SortType.ASC; 
		}
	}
	
	/**
	 * 
	 * @author dragos balan
	 *
	 */
	static class IntermGroupColFromHeaderRow implements GroupColumn {

		private PivotHeaderRow headerRow; 
	
		private int groupingLevel = -1; 
	
		public IntermGroupColFromHeaderRow(PivotHeaderRow headerRow, int groupingLevel){
			this.headerRow = headerRow; 
			this.groupingLevel = groupingLevel;
		}
	
		/* (non-Javadoc)
		 * @see net.sf.reportengine.config.GroupColumn#getHeader()
		 */
		public String getHeader() {
			return "not used";
		}
	
	
		/* (non-Javadoc)
		 * @see net.sf.reportengine.config.GroupColumn#getGroupingLevel()
		 */
		public int getGroupingLevel() {
			return groupingLevel;
		}
	
		/* (non-Javadoc)
		 * @see net.sf.reportengine.config.GroupColumn#getValue(net.sf.reportengine.core.algorithm.NewRowEvent)
		 */
		public Object getValue(NewRowEvent newRowEvent) {
			return headerRow.getValue(newRowEvent);
		}
	
		/* (non-Javadoc)
		 * @see net.sf.reportengine.config.GroupColumn#getFormattedValue(java.lang.Object)
		 */
		public String getFormattedValue(Object object) {
			String result = "";
			if(object != null){
				result = object.toString();
				//TODO: come back here and return a formatted value
			}
			return result; 
		}

		public HorizAlign getHorizAlign() {
			return HorizAlign.CENTER; //TODO: check if this is used
		}
		
		public VertAlign getVertAlign(){
			return VertAlign.MIDDLE; //TODO: check if this is used
		}
		
		public boolean showDuplicates(){
			return false; //this is not used
		}
		
		public SortType getSortType() {
			return SortType.ASC; 
		}
	}

}
