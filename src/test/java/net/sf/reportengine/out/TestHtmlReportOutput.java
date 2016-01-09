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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import net.sf.reportengine.components.ParagraphProps;
import net.sf.reportengine.util.ReportIoUtils;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * @author dragos balan
 *
 */
public class TestHtmlReportOutput {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test method for
     * {@link net.sf.reportengine.out.AbstractFreemarkerReportOutput#close()}.
     */
    @Test
    public void testCloseWriterException() {
        try {
            FileWriter fileWriter = new FileWriter("./target/TestClosingWriterException.html");
            HtmlReportOutput classUnderTest = new HtmlReportOutput(fileWriter, true);
            classUnderTest.open();
            classUnderTest.output("emptyLine.ftl");
            classUnderTest.close();

            // at this point the writer should be already closed
            fileWriter.write("if you see this the test has failed");
            fail("An IOException should have been thrown at this point");
        } catch (IOException e) {
            assertEquals("Stream closed", e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.reportengine.out.AbstractFreemarkerReportOutput#close()}.
     */
    @Test
    public void testNonClosedWriter() throws IOException {
        final String OUTPUT_PATH = "./target/TestClosingWriter.html";
        FileWriter fileWriter = new FileWriter(OUTPUT_PATH);
        HtmlReportOutput classUnderTest = new HtmlReportOutput(fileWriter, false);
        classUnderTest.open();
        classUnderTest.output("emptyLine.ftl");
        classUnderTest.close();

        // at this point the writer should be already closed
        fileWriter.write("\nthis text has been added after HtmlReportOutput has been closed");
        fileWriter.flush();
        fileWriter.close();

        List<String> lines = IOUtils.readLines(new FileReader(OUTPUT_PATH));
        assertEquals(3, lines.size());
        assertEquals("<br/>", lines.get(0));
        assertEquals("<br/>", lines.get(1));
        assertEquals("this text has been added after HtmlReportOutput has been closed",
                     lines.get(2));
    }

    @Test
    public void testUtf8Characters() throws IOException {
        String OUTPUT_PATH = "target/TestHtmlReportOutputUtf8.html";
        HtmlReportOutput testOutput =
            new HtmlReportOutput(ReportIoUtils.createWriterFromPath(OUTPUT_PATH));
        testOutput.open();
        testOutput.output("title.ftl", new ParagraphProps("На берегу пустынных волн"));
        testOutput.output("title.ftl", new ParagraphProps("Τη γλώσσα μου έδωσαν ελληνική"));
        testOutput.close();

        List<String> lines = IOUtils.readLines(ReportIoUtils.createReaderFromPath(OUTPUT_PATH));
        assertEquals(2, lines.size());
        assertEquals("<p style=\"text-align: center; vertical-align: middle\">На берегу пустынных волн</p>",
                     lines.get(0));
        assertEquals("<p style=\"text-align: center; vertical-align: middle\">Τη γλώσσα μου έδωσαν ελληνική</p>",
                     lines.get(1));

    }
}
