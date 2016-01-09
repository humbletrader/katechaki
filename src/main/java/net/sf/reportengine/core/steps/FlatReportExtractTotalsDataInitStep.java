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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.util.StepIOKeys;

/**
 * extracts some useful data to be used by the next steps in the report. 
 * 
 * 1.The distribution of calculators among data columns
 * 
 * this step constructs the distribution of calculators array which keeps track of the distribution of calculators in the dataColumns array. 
 * For example: We have a report with 6 data columns but only 3 of them have calculators (let's say the 2nd and the 4th and the 5th) 
 * In order to be able to retrieve the right calculator value for a given dataColumn index ( as set in the configuration of the report)
 * we will fill this array with 
 * 
 * array index:		0				1		2				3		4		5
 * array value: 	NO_CALCULATOR	0		NO_CALCULATOR	1		2		NO_CALCULATOR
 * 
 * Next time when we have the data column index and we need the result of the calculator (in a specific row of the matrix)
 * we will call  calculatorsDistributionInDataColumnsArray[dataColumIndex] and we will get the index of the column in the calculator matrix
     
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class FlatReportExtractTotalsDataInitStep extends AbstractReportInitStep<ArrayList<Integer>> {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FlatReportExtractTotalsDataInitStep.class);
	
	/**
	 * 
	 */
	public final static Integer NO_CALCULATOR_ON_THIS_POSITION = Integer.valueOf(-1); 
	
	
    
	
    
	public StepResult<ArrayList<Integer>> init(StepInput stepInput) {
		ArrayList<Integer> calculatorsDistributionInDataColumnsArray = 
				extractDistributionOfCalculatorsAcrossColumns(getDataColumns(stepInput)); 
		//getAlgoContext().set(	ContextKeys.DISTRIBUTION_OF_CALCULATORS, calculatorsDistributionInDataColumnsArray);
		return new StepResult<ArrayList<Integer>>(StepIOKeys.DISTRIBUTION_OF_CALCULATORS, calculatorsDistributionInDataColumnsArray); 
	}
	
	/**
	 * 
	 * @param dataCols
	 * @return
	 */
	private ArrayList<Integer> extractDistributionOfCalculatorsAcrossColumns(List<DataColumn> dataCols){
		ArrayList<Integer> result = new ArrayList<Integer>(dataCols.size());
    	int columnWithCalculatorsCount = 0;
    	
    	//for each data column 
    	for(int i=0; i<dataCols.size(); i++){
    		
    		//check if there's a group calculator attached to this column
    		if(dataCols.get(i).getCalculator() != null){
    			//if there is a group calculator
    			result.add(i, Integer.valueOf(columnWithCalculatorsCount)); 
    			columnWithCalculatorsCount++;
    		}else{
    			//if no calculator is assigned to this column
    			result.add(i, NO_CALCULATOR_ON_THIS_POSITION); 
    		}
    	}
		return result; 
	}
}
