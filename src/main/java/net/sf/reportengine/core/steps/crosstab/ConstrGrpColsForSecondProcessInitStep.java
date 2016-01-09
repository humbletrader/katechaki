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

import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.SecondProcessGroupColumn;
import net.sf.reportengine.core.steps.AbstractCrosstabInitStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.util.StepIOKeys;

/**
 * @author dragos balan
 *
 */
public class ConstrGrpColsForSecondProcessInitStep extends AbstractCrosstabInitStep<List<GroupColumn>>{
	
	
	public StepResult<List<GroupColumn>> init(StepInput stepInput) {
		List<GroupColumn> newGroupCols = constructGroupColumnsForSecondProcess(getGroupColumns(stepInput)); 
		//getAlgoContext().set(ContextKeys.INTERNAL_GROUP_COLS, newGroupCols); 
		
		return new StepResult<List<GroupColumn>>(StepIOKeys.INTERNAL_GROUP_COLS, newGroupCols); 
	}

	/**
	 * creates a list of group columns for the second report based on the original group columns
	 * 
	 * @param originalGroupCols
	 * @return	a list of group columns necessary to the second processing
	 */
	protected List<GroupColumn> constructGroupColumnsForSecondProcess(List<GroupColumn> originalGroupCols){
		List<GroupColumn> result = null; 
		if(originalGroupCols != null && originalGroupCols.size() > 0){
			result = new ArrayList<GroupColumn>(originalGroupCols.size());
			for (GroupColumn originalGroupColumn : originalGroupCols) {
				result.add(new SecondProcessGroupColumn(originalGroupColumn));
			}
		}
		return result; 
	}
}
