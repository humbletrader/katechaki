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
import java.io.IOException;

import net.sf.reportengine.components.ParagraphProps;
import net.sf.reportengine.util.ReportIoUtils;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import org.xml.sax.SAXException;

public class TestPdfReportOutput {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testUtf8Characters() throws IOException {
        String OUTPUT_PATH = "target/TestPdfReportOutputUtf8.pdf";
        PdfReportOutput testOutput =
            new PdfReportOutput(ReportIoUtils.createOutputStreamFromPath(OUTPUT_PATH));
        testOutput.open();

        // calling startreport.ftl and endreport.ftl is important for pdf
        // because
        // the resulting xml does not have a root and a well-defined namespace
        testOutput.output("startReport.ftl", new ReportProps(new PdfOutputFormat()));
        testOutput.output("title.ftl", new ParagraphProps("На берегу пустынных волн"));
        testOutput.output("title.ftl", new ParagraphProps("Τη γλώσσα μου έδωσαν ελληνική"));
        testOutput.output("endReport.ftl");
        testOutput.postProcess();
        testOutput.close();

        File pdfFile = new File(OUTPUT_PATH);
        Assert.assertNotNull(pdfFile);
        Assert.assertNotNull(pdfFile.exists());
        Assert.assertTrue(pdfFile.isFile());
        Assert.assertTrue(pdfFile.length() != 0);
    }

    @Ignore
    public void testCustomConfig() throws IOException, ConfigurationException, SAXException {
        String OUTPUT_PATH = "target/TestPdfReportOutputCustomConfig.pdf";

        Configuration fopConfig =
            new DefaultConfigurationBuilder().build("./input/fopConfigForUnitTests.xml");

        PdfReportOutput testOutput =
            new PdfReportOutput(ReportIoUtils.createOutputStreamFromPath(OUTPUT_PATH),
                                new PdfOutputFormat(PageSize.A3_PORTRAIT),
                                fopConfig);
        testOutput.open();

        // calling startreport.ftl and endreport.ftl is important for pdf
        // because the resulting xml does not have a root and a well-defined
        // namespace
        testOutput.output("startReport.ftl", new ReportProps(new PdfOutputFormat()));
        testOutput.output("title.ftl", new ParagraphProps("This is a report with custom configuration"));
        testOutput.output("endReport.ftl");
        testOutput.postProcess();
        testOutput.close();

        File pdfFile = new File(OUTPUT_PATH);
        Assert.assertNotNull(pdfFile);
        Assert.assertNotNull(pdfFile.exists());
        Assert.assertTrue(pdfFile.isFile());
        Assert.assertTrue(pdfFile.length() != 0);
    }
}
