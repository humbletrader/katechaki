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

import net.sf.reportengine.core.algorithm.steps.AbstractExitStep;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.util.StepIOKeys;

/**
 * @author dragos balan
 *
 */
public abstract class AbstractReportExitStep<T> extends AbstractExitStep<T> {

	protected TableInput getReportInput(StepInput stepInput){
		return (TableInput)stepInput.getContextParam(StepIOKeys.LOCAL_REPORT_INPUT); 
	}
	
//	protected ReportOutput getReportOutput(StepInput stepInput){
//		return (ReportOutput)stepInput.getContextParam(ContextKeys.LOCAL_REPORT_OUTPUT); 
//	}
	
}
