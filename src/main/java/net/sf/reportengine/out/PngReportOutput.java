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

import java.io.OutputStream;

import net.sf.reportengine.util.ReportIoUtils;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.xmlgraphics.util.MimeConstants;

public class PngReportOutput extends PostProcessedFoReportOutput {

    public PngReportOutput(OutputStream outStream) {
        this(outStream, new PngOutputFormat());
    }

    public PngReportOutput(OutputStream outStream, PngOutputFormat outputFormat) {
        this(outStream, outputFormat, null);
    }

    public PngReportOutput(OutputStream outStream,
                           PngOutputFormat outputFormat,
                           Configuration fopConfig) {
        super(outStream, outputFormat, MimeConstants.MIME_PNG, fopConfig, null);
    }

    public PngReportOutput(String filePath) {
        this(filePath, new PngOutputFormat());
    }

    public PngReportOutput(String filePath, PngOutputFormat outputFormat) {
        this(filePath, outputFormat, null);
    }

    public PngReportOutput(String filePath, PngOutputFormat outputFormat, Configuration fopConfig) {
        super(ReportIoUtils.createOutputStreamFromPath(filePath),
              outputFormat,
              MimeConstants.MIME_PNG,
              fopConfig,
              new FopUserAgentProperties("net.sf.reportengine", filePath));
    }
}
