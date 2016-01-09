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
package net.sf.reportengine.out;

/**
 * <p> Abstract parent for the report output classes with basic status and format features </p>
 * 
 * @author dragos balan
 */
public abstract class AbstractReportOutput implements ReportOutput {

    /**
     * the output format
     */
    private final OutputFormat format;
    
    /**
     * the status of the output
     */
    private Status status;

    /**
     * marks the report status as INIT and keeps a reference to the format for internal use
     * 
     * @param format    the output format for this output
     */
    public AbstractReportOutput(OutputFormat format) {
        this.format = format;
        this.status = Status.INIT;
    }

    /**
     * marks the report as OPEN. If classes inheriting from this want to
     * override this method, they should call super.open() in order to mark the
     * report as open and then add their own implementation
     */
    public void open() {
        status = Status.OPEN;
    }

    /**
     * a simple delegation to the implementation of {@code output(templateName, null}
     * This method should be used for calling templates which don't need any input. 
     *  
     * @param templateName  the name of the template
     */
    public void output(String templateName) {
        output(templateName, null);
    }

    /**
     * marks the report as CLOSED. If classes inheriting from this want to
     * override this method, they should call super.close() at the end of their
     * own implementation of close()
     */
    public void close() {
        status = Status.CLOSED;
    }
    
    /**
     * getter for the output format of this report output
     */
    public OutputFormat getFormat() {
        return format;
    }
    
    /**
     * getter for the status
     */
    public Status getStatus() {
        return status;
    }

}
