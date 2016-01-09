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

import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.algorithm.steps.AbstractInitStep;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.util.StepIOKeys;
import net.sf.reportengine.util.AlgoIOKeys;

/**
 * @author dragos balan
 *
 */
public abstract class AbstractReportInitStep<U> extends AbstractInitStep<U> {
	
	/**
	 * 
	 * @return
	 */
	public TableInput getReportInput(StepInput stepInput){
		return (TableInput)stepInput.getContextParam(StepIOKeys.LOCAL_REPORT_INPUT); 
	}
	
    /**
     * ATTENTION : changing the implementation of this method will have effect on the 
     * following methods: 
     * {@link #getDataColumnsLength()}
     * @return
     */
    public List<DataColumn> getDataColumns(StepInput stepInput){
    	return (List<DataColumn>)stepInput.getAlgoInput(AlgoIOKeys.DATA_COLS); 
    }
    
    /**
     * 
     * @return
     */
    public int getDataColumnsLength(StepInput stepInput){
    	List<DataColumn> dataCols = getDataColumns(stepInput); 
    	return dataCols != null ? dataCols.size() : 0; 
    }
    
    
    /**
     * ATTENTION : changing the implementation of this method will have effect on the 
     * following methods: 
     * {@link #getGroupColumns()}
     * 
     * @return
     */
    public List<GroupColumn> getGroupColumns(StepInput stepInput){
    	return (List<GroupColumn>)stepInput.getAlgoInput(AlgoIOKeys.GROUP_COLS); 
    }
    
    public int getGroupColumnsLength(StepInput stepInput){
    	List<GroupColumn> groupColumns = getGroupColumns(stepInput); 
    	return groupColumns != null ? groupColumns.size() : 0; 
    }
    
    public Boolean getShowTotals(StepInput stepInput){
    	return (Boolean)stepInput.getAlgoInput(AlgoIOKeys.SHOW_TOTALS); 
    }
    
    public Boolean getShowGrandTotal(StepInput stepInput){
    	return (Boolean)stepInput.getAlgoInput(AlgoIOKeys.SHOW_GRAND_TOTAL); 
    }
}
