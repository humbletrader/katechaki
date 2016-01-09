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
package net.sf.reportengine.core.steps.intermed;

import java.util.List;

import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.PreviousRowManagerStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.util.StepIOKeys;

/**
 * @author dragos balan
 *
 */
public class IntermedPreviousRowManagerStep extends PreviousRowManagerStep {
	
	/**
	 * 
	 */
	public List<GroupColumn> getGroupColumns(StepInput stepInput){
		return (List<GroupColumn>)stepInput.getContextParam(StepIOKeys.INTERNAL_GROUP_COLS); 
	}
	
	/**
	 * executes the super only if the report has group columns
	 */
	@Override
	public StepResult<Object[]> execute(NewRowEvent newRow, StepInput stepInput){
		StepResult<Object[]> stepResult = null; 
		if(getGroupColumnsCount(stepInput) > 0 ){
			stepResult = super.execute(newRow, stepInput); 
		}
		
		return stepResult; 
	}
}
