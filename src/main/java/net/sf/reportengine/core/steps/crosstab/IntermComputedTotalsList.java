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
import java.util.List;

/**
 * this class holds total values computed by intermediate report on CrosstabData and 
 * destined for special total-data columns in the final report
 * 
 * The order is not important because all IntermediateTotalInfo objects have a position inside
 * 
 * @author dragos balan
 * @since 0.4
 */
public class IntermComputedTotalsList implements Serializable{
	
	
	/**
	 * the serial version id
	 */
	private static final long serialVersionUID = 3388048740067218435L;
	
	/**
	 * the total values holder
	 */
	private List<IntermediateTotalInfo> totalsDataList;
	
	/**
	 * the one and only constructor
	 */
	public IntermComputedTotalsList(){
		totalsDataList = new ArrayList<IntermediateTotalInfo>(); 
	}
	
	/**
	 * adds the specified totalsData into the existing list
	 * @param totalsData
	 */
	public void addTotalsData(IntermediateTotalInfo totalsData){
		totalsDataList.add(totalsData); 
	}
	
	/**
	 * retrieves the totalInfo for the specified position (relative to the header)
	 * 
	 * @param position	an array of indexes ( position relative to header)
	 * @return
	 */
	public Object getValueFor(int...position){
		Object result = null; 
		boolean allPositionsAreEqual =  true; 
		
		if(position != null){
			//iterate over the totals data List
			for (IntermediateTotalInfo totalInfo : totalsDataList) {
				int[] positionRelativeToHeader = totalInfo.getPositionRelativeToHeader();
			
				if(	positionRelativeToHeader != null 
					&& positionRelativeToHeader.length == position.length){
				
					allPositionsAreEqual =  true; 
					
					//iterate over positions
					for (int i = 0; i < positionRelativeToHeader.length && allPositionsAreEqual; i++) {
						if(positionRelativeToHeader[i] != position[i]){
							
							//if found one position not equal to the header row index then 
							//mark not all positions are equal in order to skip this 
							//IntermediateDataInfo and pass to the next one
							allPositionsAreEqual = false; 
						}
					}
				
					if(allPositionsAreEqual){
						//the first time we find that all positions are equal we exit the 
						//dataInfo loop and return
						result = totalInfo.getComputedValue(); 
						break; 
					}
				}else{
					//if position relative to header is null then 
					//this is a grand total
				}
			}//end loop totalsInfo 
		}else{
			//position is null
			for (IntermediateTotalInfo totalInfo : totalsDataList) {
				if(totalInfo.getPositionRelativeToHeader() == null){
					result = totalInfo.getComputedValue(); 
					break; 
				}
			}
		}
		return result;
	}
	
	/**
	 * clears the list 
	 */
	public void empty(){
		totalsDataList.clear(); 
	}
	
	/**
	 * returns the last value in the list
	 * @return
	 */
	public Object getLastValueInTotalList(){
		return totalsDataList.get(totalsDataList.size()-1);
	}
	
	/**
	 * retrieves the list behind this class
	 * @return
	 */
	public List<IntermediateTotalInfo> getTotalsDataList(){//TODO: try to remove this
		return totalsDataList; 
	}
	
	@Override
	public boolean equals(Object another){
		boolean result = false;
		if(another instanceof IntermComputedTotalsList){
			result = this.totalsDataList.equals(((IntermComputedTotalsList)another).getTotalsDataList()); 
		}
		return result; 
	}
	
	@Override
	public String toString(){
		StringBuffer result = new StringBuffer("IntermCtTotalList"); 
		result.append(totalsDataList.toString());
		return result.toString(); 
	}
}
