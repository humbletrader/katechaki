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
package net.sf.reportengine.config;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.GroupCalculator;
import net.sf.reportengine.core.steps.crosstab.IntermComputedTotalsList;

/**
 * this is only for internal use
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class SecondProcessTotalColumn implements DataColumn {
	
	/**
	 * 
	 */
	private int[] positionRelativeToHeader; 
	
	/**
	 * the original crosstab data 
	 */
	private PivotData originalCtData; 
	
	/**
	 * 
	 * @param header
	 * @param calculator
	 * @param formatter
	 */
	public SecondProcessTotalColumn(int[] positionRelativeToHeader, 
									PivotData originalCtData) {
		//super(debugHeader +" "+Arrays.toString(positionRelativeToHeader), calc, formatter);
		this.positionRelativeToHeader = positionRelativeToHeader; 
		this.originalCtData = originalCtData; 
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.config.DataColumn#getValue(net.sf.reportengine.core.algorithm.NewRowEvent)
	 */
	public Object getValue(NewRowEvent newRowEvent) {
		//according to the contract the forth object in each row array is an 
		//instance of IntermCtDataList
		List<Object> newRow = newRowEvent.getInputDataRow(); 
		IntermComputedTotalsList intermTotalsList = (IntermComputedTotalsList)newRow.get(3); 
		Object result = intermTotalsList.getValueFor(positionRelativeToHeader); 
		Object toReturn = BigDecimal.ZERO; 
		if(result != null ){
			toReturn = result; 
		}
		return toReturn; 
	}
	
	public int[] getPosition(){
		return positionRelativeToHeader; 
	}

	public String getHeader() {
		return Arrays.toString(positionRelativeToHeader);
	}

	public String getFormattedValue(Object value) {
		return originalCtData.getFormattedValue(value);
	}
	
	public String getFormattedTotal(Object value){
		return originalCtData.getFormattedTotal(value); 
	}
	
	public GroupCalculator getCalculator() {
		return originalCtData.getCalculator();
	}

	public HorizAlign getHorizAlign() {
		return originalCtData.getHorizAlign();
	}

	public VertAlign getVertAlign() {
		return originalCtData.getVertAlign();
	}

	public int getSortLevel() {
		return NO_SORTING;
	}

	public SortType getSortType() {
		return SortType.NONE;
	}
}
