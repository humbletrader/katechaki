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

import java.io.File;

import net.sf.reportengine.core.steps.AbstractReportExitStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.out.IntermediateCrosstabOutput;
import net.sf.reportengine.util.StepIOKeys;

/**
 * @author dragos balan
 *
 */
public class IntermedSetResultsExitStep extends AbstractReportExitStep<File> {

	public StepResult<File> exit(StepInput stepInput) {
		IntermediateCrosstabOutput output = 
		        (IntermediateCrosstabOutput)stepInput.getContextParam(StepIOKeys.INTERMEDIATE_CROSSTAB_OUTPUT);  
		StepResult<File> result = new StepResult<File>(StepIOKeys.INTERMEDIATE_SERIALIZED_FILE, 
						((IntermediateCrosstabOutput)output).getSerializedOutputFile()); 
		return result; 
	}
}
