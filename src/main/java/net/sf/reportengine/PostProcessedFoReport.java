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
package net.sf.reportengine;

import java.util.List;

import net.sf.reportengine.components.ReportComponent;
import net.sf.reportengine.out.PostProcessedFoReportOutput;

/**
 * This report is executed in two steps: 
 *  1. output components as fo objects ( see formatting objects from apache.org) 
 *  2. processing the previous fo output into pdf, rtf. etc.
 *  
 * @author dragos balan
 * @since 0.13
 */
class PostProcessedFoReport extends AbstractReport<PostProcessedFoReportOutput> {
	
	/**
	 * the constructor 
	 * 
	 * @param reportOutput	the report output
	 * @param components	the components of the report
	 */
    PostProcessedFoReport(PostProcessedFoReportOutput reportOutput, List<ReportComponent> components) {
        super(reportOutput, components);
    }

    /**
     * executes the report
     */
    public void execute() {
        PostProcessedFoReportOutput reportOutput = getReportOutput();
        try {
            reportOutput.open();
            outputFO(reportOutput);
            processFO(reportOutput);
        } finally {
            reportOutput.close();
        }
    }
    
    /**
     * performs the post processing of the fo report
     * @param reportOutput
     */
    private void processFO(PostProcessedFoReportOutput reportOutput) {
        reportOutput.postProcess();
    }
}
