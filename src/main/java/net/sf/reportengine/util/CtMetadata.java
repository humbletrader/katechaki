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
package net.sf.reportengine.util;

import java.util.Arrays;
import java.util.List;

/**
 * @author dragos balan
 * @since 0.4
 */
public final class CtMetadata {
	
	
	/**
	 * this is the number of data columns 
	 * ( without taking into account the total columns even if the total column is also a DataColumn)
	 */
	private int dataColumnsCount = -1; 
	
	/**
	 * 
	 */
	private int[] distinctValuesPerLevel = null;
	
	private int[] spanPerLevel = null;
	
	private int[] spanWhenTotalsPerLevel = null; 
	
	private int headerRowsCount = -1; 
	
	private DistinctValuesHolder distinctValuesHolder = null ; 
	
	public CtMetadata(DistinctValuesHolder distValuesHolder){
		this.distinctValuesHolder = distValuesHolder; 
		computeCoefficients(); 
	}
	
	private void computeCoefficients(){
		
		//first we compute the number of columns
		dataColumnsCount = 1; 
		headerRowsCount = distinctValuesHolder.getRowsCount();
		distinctValuesPerLevel = new int[headerRowsCount];
		spanPerLevel = new int[headerRowsCount];
		spanWhenTotalsPerLevel = new int[headerRowsCount]; 
		
		//step 0 done manually
		int distValueCntInCurrentRow = distinctValuesHolder.getDistinctValuesCountForLevel(0);
		dataColumnsCount *= distValueCntInCurrentRow; 
		distinctValuesPerLevel[0] = distValueCntInCurrentRow; 
		spanPerLevel[headerRowsCount-1] = 1;
		spanWhenTotalsPerLevel[headerRowsCount-1] = 1; 
		
		//steps 1 to headerRowsCount
		int auxDistinctValues = 0; 
		for(int i=1; i< headerRowsCount; i++){
			int distValuesCnt = distinctValuesHolder.getDistinctValuesCountForLevel(i);
			dataColumnsCount *= distValuesCnt; 
			distinctValuesPerLevel[i] = distValuesCnt;
					
			//the distance between same values is inversely calculated (bottom to top)
			auxDistinctValues = distinctValuesHolder.getDistinctValuesCountForLevel(headerRowsCount-i);
			spanPerLevel[headerRowsCount-i-1] = spanPerLevel[headerRowsCount-i] * auxDistinctValues;
			
			spanWhenTotalsPerLevel[headerRowsCount-i-1] = (auxDistinctValues * spanWhenTotalsPerLevel[headerRowsCount-i]) + 1;
		}
	}
	
	public int getDataColumnCount(){
		return dataColumnsCount; 
	}
	
	public int[] getColspan(){
		return spanPerLevel; 
	}
	
	public int getColspanForLevel(int level){
		return spanPerLevel[level];
	}
	
	public int getColspanWhenTotals(int level){
		return spanWhenTotalsPerLevel[level];
	}
	
	public int getHeaderRowsCount(){
		return headerRowsCount; 
	}
	
	public int getDistinctValuesCountForLevel(int level){
		return distinctValuesPerLevel[level];
	}
	
	public List<Object> getDistinctValuesForLevel(int level){
		return distinctValuesHolder.getDistinctValuesForLevel(level);
	}
	
	public Object getDistincValueFor(int level, int distValuePosition){
		return distinctValuesHolder.getDistinctValuesForLevel(level).get(distValuePosition); 
	}
	
	/**
	 * 
	 */
	public String toString(){
		StringBuffer result = new StringBuffer(); 
		result.append("CtMetadata[");
		result.append("distValuesCnt=");
		result.append(Arrays.toString(distinctValuesPerLevel));
		result.append(", span=");
		result.append(Arrays.toString(spanPerLevel)); 
		result.append("]");
		return result.toString();
	}
}
