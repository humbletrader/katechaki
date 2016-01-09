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

import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.config.PivotData;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.SecondProcessDataColumn;
import net.sf.reportengine.config.SecondProcessDataColumnFromOriginalDataColumn;
import net.sf.reportengine.config.SecondProcessTotalColumn;
import net.sf.reportengine.core.steps.AbstractCrosstabInitStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.util.StepIOKeys;
import net.sf.reportengine.util.CtMetadata;

/**
 * @author dragos balan
 *
 */
public class ConstrDataColsForSecondProcessInitStep extends AbstractCrosstabInitStep<List<DataColumn>>{

	
	public StepResult<List<DataColumn>> init(StepInput stepInput) {
		List<DataColumn> newDataCols = constructDataColumnsForSecondProcess(getCrosstabMetadata(stepInput), 
				getDataColumns(stepInput),
				getCrosstabData(stepInput), 
				getShowTotals(stepInput), 
				getShowGrandTotal(stepInput));
		//getAlgoContext().set(ContextKeys.INTERNAL_DATA_COLS, newDataCols);
		return new StepResult<List<DataColumn>>(StepIOKeys.INTERNAL_DATA_COLS, newDataCols); 
	}
	
	/**
	 * creates a list of DataColumn objects from 
	 * 
	 * 1. original data columns 
	 * 2. columns needed for displaying the values under the header values ( computed form crosstab-data) 
	 * 3. (if needed ) columns needed to display the totals and grand total 
	 * 
	 * @param crosstabMetadata
	 * @param originalDataColumns
	 * @param hasTotals
	 * @param hasGrandTotal
	 * @return	a list data columns necessary to the second process
	 */
	protected List<DataColumn> constructDataColumnsForSecondProcess(	CtMetadata crosstabMetadata, 
																		List<DataColumn> originalDataColumns,
																		PivotData originalCtData, 
																		boolean hasTotals, 
																		boolean hasGrandTotal){
		int dataColsCount = crosstabMetadata.getDataColumnCount(); 
		int headerRowsCount = crosstabMetadata.getHeaderRowsCount(); 
		
		List<DataColumn> resultDataColsList = new ArrayList<DataColumn>();
		
		//first we add the original data columns 
		//those declared initially by the user in his configuration
		for(int i=0; i < originalDataColumns.size(); i++){
			resultDataColsList.add(new SecondProcessDataColumnFromOriginalDataColumn(originalDataColumns.get(i), i));
		}

		// then we construct the columns 
		for(int column=0; column < dataColsCount; column++){
		
			//construct total columns 
			if(hasTotals){
				//we start bottom to top (from last header row -1 to first header row) 
				for(int row=headerRowsCount-2; row >= 0; row--){
					int colspan = crosstabMetadata.getColspanForLevel(row); 
				
					if(column != 0 && (column % colspan)==0){
						int[] positionForCurrentTotal = new int[row+1]; //new int[headerRowsCount-1];
					
						for(int j=0; j < positionForCurrentTotal.length; j++){
							positionForCurrentTotal[j] = ((column-1) / crosstabMetadata.getColspanForLevel(j)) % crosstabMetadata.getDistinctValuesCountForLevel(j);
						}
		
						resultDataColsList.add(
								//"Total column="+column+ ",colspan= "+colspan
								new SecondProcessTotalColumn(	positionForCurrentTotal, originalCtData));
					}
				}//end for
			}//end if has totals

			//data columns coming from crosstab data
			int[] positionForCurrentColumn = new int[headerRowsCount];
			for(int j=0; j < headerRowsCount; j++){
				positionForCurrentColumn[j] = (column / crosstabMetadata.getColspanForLevel(j)) % crosstabMetadata.getDistinctValuesCountForLevel(j);
			}
		
			resultDataColsList.add(new SecondProcessDataColumn(positionForCurrentColumn, originalCtData)); 
		}//end for columns
		
		//at the end we add one more total 
		if(hasTotals){
			//final total columns
			for(int row=headerRowsCount-2; row >= 0; row--){
				int colspan = crosstabMetadata.getColspanForLevel(row); 
			
				if(dataColsCount!= 0 && (dataColsCount % colspan)==0){
					int[] positionForCurrentTotal = new int[row+1];
			
					for(int j=0; j < positionForCurrentTotal.length; j++){
						positionForCurrentTotal[j] = ((dataColsCount-1) / crosstabMetadata.getColspanForLevel(j)) % crosstabMetadata.getDistinctValuesCountForLevel(j);
					}
					//"Total column="+(dataColsCount)+ ",colspan= "+colspan
					resultDataColsList.add(new SecondProcessTotalColumn(positionForCurrentTotal, originalCtData));
				}
			}
		}//end if has totals

		// .. and finally the grand total
		if(hasGrandTotal){
			//"Grand Total"
			resultDataColsList.add(new SecondProcessTotalColumn(null, originalCtData)); 
		}

		return resultDataColsList; 
	}

}
