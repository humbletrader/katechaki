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

import java.io.Serializable;

/**
 * Intermediate report row. 
 * Each intermediate report row contains information about : 
 *  <ol>
 *  	<li>values belonging to group columns in the final report (declared as group columns by the user)</li>
 *  	<li>values belonging to data columns in the final report (declared as data columns by the user)</li>
 *  	<li>values computed by the intermediate report (coming from CrosstabData) 
 *  	<li>total values computed by intermediate report on CrosstabData and destined for special total-data columns in the final report</li>
 *  </ol>
 * 
 * @author dragos balan
 * @since 0.4
 */
public class IntermediateReportRow implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2809271046992077641L;
	
	/**
	 * the only purpose of this flag is to know when to stop reading the ObjectStream when somebody reads it
	 * as the ObjectStream implementations don't have this feature.
	 */
	private boolean isLast = false; 
	
	private IntermOriginalGroupValuesList intermOrigGroupValuesList;
	private IntermOriginalDataColsList intermOriginalDataValues; 
	private IntermComputedDataList intermComputedDataList; 
	private IntermComputedTotalsList intermComputedTotalsList; 
	
	
	/**
	 * the one and only constructor
	 */
	public IntermediateReportRow(){
		intermOrigGroupValuesList = new IntermOriginalGroupValuesList();
		intermOriginalDataValues = new IntermOriginalDataColsList(); 
		intermComputedDataList = new IntermComputedDataList();
		intermComputedTotalsList = new IntermComputedTotalsList();
	}
	
	public IntermOriginalGroupValuesList getIntermGroupValuesList(){
		return intermOrigGroupValuesList; 
	}
	
	public IntermOriginalDataColsList getIntermOriginalDataValuesList(){
		return intermOriginalDataValues; 
	}
	
	public IntermComputedDataList getIntermComputedDataList() {
		return intermComputedDataList;
	}

	public IntermComputedTotalsList getIntermComputedTotalsList() {
		return intermComputedTotalsList;
	}

	
	/**
	 * adds the given group value to the existing intemediate group values list
	 * @param groupValue
	 */
	public void addOrigGroupValue(Object groupValue){
		this.intermOrigGroupValuesList.addGroupValue(groupValue); 
	}
	
	/**
	 * adds the given value to the data values list
	 * @param dataValue
	 */
	public void addOrigDataColValue(Object dataValue){
		this.intermOriginalDataValues.addDataColumnValue(dataValue); 
	}
	
	/**
	 * adds the given DataInfo to the existing intermediate data list
	 * @param info
	 */
	public void addIntermComputedData(IntermediateDataInfo info){
		this.intermComputedDataList.addData(info);
	}
	
	/**
	 * adds the given total info to the total info list
	 * @param info
	 */
	public void addIntermTotalsInfo(IntermediateTotalInfo info){
		this.intermComputedTotalsList.addTotalsData(info);
	}
	
	/**
	 * clears all 4 lists of values
	 */
	public void emptyRow(){
		intermOrigGroupValuesList.empty(); 
		intermOriginalDataValues.empty(); 
		intermComputedDataList.empty(); 
		intermComputedTotalsList.empty();
	}
	
	/**
	 * returns true if this row is the last
	 * @return
	 */
	public boolean isLast() {
		return isLast;
	}
	
	/**
	 * sets this row as being the last
	 * @param isLast
	 */
	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}
	
	@Override
	public boolean equals(Object another){
		boolean result = false; 
		
		if(another instanceof IntermediateReportRow){
			IntermediateReportRow anotherAsIRR = (IntermediateReportRow)another; 
			result = intermComputedTotalsList.equals(anotherAsIRR.getIntermComputedTotalsList())
					&& intermComputedDataList.equals(anotherAsIRR.getIntermComputedDataList())
					&& intermOrigGroupValuesList.equals(anotherAsIRR.getIntermGroupValuesList())
					&& intermOriginalDataValues.equals(anotherAsIRR.getIntermOriginalDataValuesList())
					; 
		}
		return result; 
	}
	
	@Override
	public String toString(){
		StringBuilder result = new StringBuilder("IRR[isLast=");
		result.append(isLast);
		result.append(", ");
		result.append(intermOrigGroupValuesList); 
		result.append(", ");
		result.append(intermOriginalDataValues);
		result.append(", ");
		result.append(intermComputedDataList); 
		result.append(", ");
		result.append(intermComputedTotalsList); 
		result.append("]");
		return result.toString(); 
	}
}
