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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * this class holds values computed by the intermediate report (coming from CrosstabData) and arranged as a single object
 * in the intermediate row
 * 
 * The order of the objects is not important because any IntermediateDataInfo object has the position specified as an array
 * 
 * @author dragos balan
 * @since 0.4
 */
public class IntermComputedDataList implements Serializable{
	
	
	/**
	 * serial version id
	 */
	private static final long serialVersionUID = -308220872583199439L;
	
	/**
	 * the values list
	 */
	private List<IntermediateDataInfo> dataList; 
	
	
	/**
	 * the one and only constructor
	 */
	public IntermComputedDataList(){
		dataList = new ArrayList<IntermediateDataInfo>();
	}
	
	/**
	 * adds the specified data into the list
	 * @param info
	 */
	public void addData(IntermediateDataInfo info){
		dataList.add(info);
	}
	
	/**
	 * returns the data list
	 * @return
	 */
	public List<IntermediateDataInfo> getDataList(){
		return dataList; 
	}
	
	/**
	 * clears the values of data
	 */
	public void empty(){
		dataList.clear(); 
	}
	
	/**
	 * returns the value for the given position
	 * 
	 * @param headerRowIndex	the position relative to header
	 * @return
	 */
	public Object getValueFor(int...headerRowIndex){
		Object result = null; 
		boolean allPositionsAreEqual =  true; 
		
		//iterate over dataList
		for (IntermediateDataInfo dataInfo : dataList) {
			int[] positionRelativeToHeader = dataInfo.getPositionRelativeToHeaderRows();
			if(	positionRelativeToHeader != null 
				&& positionRelativeToHeader.length == headerRowIndex.length){
				
				allPositionsAreEqual =  true; 
				
				//iterate over positions
				for (int i = 0; i < positionRelativeToHeader.length && allPositionsAreEqual; i++) {
					if(positionRelativeToHeader[i] != headerRowIndex[i]){
						
						//if found one position not equal to the header row index then 
						//mark not all positions are equal in order to skip this 
						//IntermediateDataInfo and pass to the next one
						allPositionsAreEqual = false; 
					}
				}
				
				if(allPositionsAreEqual){
					//the first time we find that all positions are equal we exit the 
					//dataInfo loop and return
					result = dataInfo.getValue(); 
					break; 
				}
			}else{
				throw new IllegalArgumentException("Invalid position array : "+Arrays.toString(headerRowIndex));
			}
		}
		return result;
	}
	
	@Override
	public boolean equals(Object another){
		boolean result = false;
		if(another instanceof IntermComputedDataList){
			result = this.dataList.equals(((IntermComputedDataList)another).getDataList()); 
		}
		return result; 
	}
	
	@Override
	public String toString(){
		StringBuffer buffer = new StringBuffer("IntermComputedDataList");
		buffer.append(dataList.toString());
		return buffer.toString(); 
	}
}
