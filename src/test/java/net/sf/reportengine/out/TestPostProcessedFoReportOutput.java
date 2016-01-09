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

import java.io.File;

import net.sf.reportengine.components.ParagraphProps;
import net.sf.reportengine.util.ReportIoUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestPostProcessedFoReportOutput {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testPostProcess() {
        String OUTPUT_PATH = "./target/TestPostProcessedFoReportOutput.pdf";
        PdfOutputFormat format = new PdfOutputFormat();

        PostProcessedFoReportOutput classUnderTest =
            new PostProcessedFoReportOutput(ReportIoUtils.createOutputStreamFromPath(OUTPUT_PATH),
                                            format,
                                            "application/pdf",
                                            null,
                                            null);

        classUnderTest.open();

        classUnderTest.output("startReport.ftl", new ReportProps(format));
        classUnderTest.output("title.ftl", new ParagraphProps("Hello from a post processed FO report"));
        classUnderTest.output("endReport.ftl");

        classUnderTest.postProcess();
        classUnderTest.close();

        File pdfResult = new File(OUTPUT_PATH);
        Assert.assertTrue(pdfResult.exists());
        Assert.assertTrue(pdfResult.isFile());
        Assert.assertTrue(pdfResult.length() > 0);
    }

}
