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
import net.sf.reportengine.out.AbstractReportOutput;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dragos balan
 *
 */
final class DefaultReport extends AbstractReport<AbstractReportOutput> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultReport.class);

    /**
     * 
     * @param builder
     */
    DefaultReport(AbstractReportOutput reportOutput, List<ReportComponent> components) {
        super(reportOutput, components);
    }

    /**
     * executes the report
     */
    public void execute() {
        AbstractReportOutput reportOutput = getReportOutput();
        try {
            reportOutput.open();
            outputFO(reportOutput);
        } finally {
            reportOutput.close();
        }
    }
}