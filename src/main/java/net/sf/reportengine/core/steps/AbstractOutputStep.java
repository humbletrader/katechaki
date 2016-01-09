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

import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.AlgoIOKeys;

/**
 * @author dragos balan
 *
 */
public abstract class AbstractOutputStep<T,U,V> extends AbstractReportStep<T,U,V> {

	/**
	 * 
	 */
	public <K> void  outputOneValue(StepInput stepInput, String templateName, K value){
		getNewReportOutput(stepInput).output(templateName, value); 
	}
	
	public void outputNoValue(StepInput stepInput, String templateName){
		getNewReportOutput(stepInput).output(templateName, null); 
	}
	
	/**
     * getter for the output of the report
     * @return
     */
    public ReportOutput getNewReportOutput(StepInput stepInput){
    	//return (NewReportOutput)stepInput.getContextParam(ContextKeys.NEW_LOCAL_REPORT_OUTPUT); 
    	return (ReportOutput)stepInput.getAlgoInput(AlgoIOKeys.NEW_REPORT_OUTPUT); 
    }

}
