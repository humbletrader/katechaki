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

import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.components.ReportComponent;
import net.sf.reportengine.out.AbstractReportOutput;
import net.sf.reportengine.out.PostProcessedFoReportOutput;
import net.sf.reportengine.out.ReportOutput;

/**
 * <p>The builder for any kind of report</p>
 * <p>
 * Typical usage: 
 * <pre>
 *  Report report = new ReportBuilder(new HtmlReportOutput(new FileWriter("/temp/test.html")))
 *                      .add(new Paragraph("My first report"))
 *                      .add(...)
 *                      .add(other report components here)
 *                      .build(); 
 *  report.execute(); 
 * </pre>
 * </p>
 * 
 * @see Report
 * @see ReportComponent
 * @see ReportOutput
 * 
 * @author dragos balan
 * @since 0.13
 */
public class ReportBuilder {

    /**
	 * a list of components of this report
	 */
    private List<ReportComponent> components;

    /**
	 * the output of this report
	 */
    private final AbstractReportOutput reportOutput;
    
    /**
     * helper flag to determine if the built report should 
     * be a PostProcessedReport or a simple DefaultReport
     */
    private final boolean reportNeedsPostProcessing; 

    /**
     * constructor of this builder
     * 
     * @param output
     */
    public ReportBuilder(AbstractReportOutput output) {
        this(output, false); 
    }
    
    /**
     * constructor of this builder for post processed output formats
     * 
     * @param output
     */
    public ReportBuilder(PostProcessedFoReportOutput output) {
        this(output, true); 
    }
    
    /**
     * private constructor 
     * 
     * @param out					the output of the resulting report
     * @param needsPostProcessing	whether or not the resulting report needs post processing
     */
    private ReportBuilder(AbstractReportOutput out, boolean needsPostProcessing){
        this.reportOutput = out;
        this.components = new ArrayList<ReportComponent>();
        this.reportNeedsPostProcessing = needsPostProcessing;
    }
    
    /**
     * adds a new component to the report
     * 
     * @param newComponent 		the component to be added
     */
    public ReportBuilder add(ReportComponent newComponent) {
        components.add(newComponent);
        return this;
    }
    
    /**
     * returns the output set into this report builder
     */
    public AbstractReportOutput getOutput() {
        return reportOutput;
    }
    
    /**
     * returns the components of this report
     */
    public List<ReportComponent> getComponents() {
        return components;
    }
    
    /**
     * builds a new Report
     * 
     * @return	a new Report instance
     */
    public Report build() {
        Report result = null;
        if (reportNeedsPostProcessing) {
            result = new PostProcessedFoReport((PostProcessedFoReportOutput) reportOutput, components);
        } else {
            result = new DefaultReport(reportOutput, components);
        }
        return result;
    }
}
