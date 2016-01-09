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

import java.io.Writer;

/**
 * FO (Formatting Objects) output for reports.
 * 
 * @author dragos balan
 *
 */
public class FoReportOutput extends AbstractFreemarkerReportOutput {

    /**
     * the default FO templates class path
     */
    public final static String DEFAULT_FO_TEMPLATES_CLASS_PATH = "/net/sf/reportengine/fo";

    /**
     * 
     * @param writer
     */
    public FoReportOutput(Writer writer) {
        this(writer, true, new FoOutputFormat());
    }

    /**
     * 
     * @param writer
     * @param closeWriterWhenDone
     */
    public FoReportOutput(Writer writer, boolean closeWriterWhenDone) {
        this(writer, closeWriterWhenDone, new FoOutputFormat());
    }

    /**
     * 
     * @param writer
     * @param closeWriterWhenDone
     * @param outputFormat
     */
    public FoReportOutput(Writer writer, boolean closeWriterWhenDone, FoOutputFormat outputFormat) {
        super(writer, closeWriterWhenDone, outputFormat);
    }

    @Override
    public String getTemplatesClasspath() {
        return DEFAULT_FO_TEMPLATES_CLASS_PATH;
    }

}
