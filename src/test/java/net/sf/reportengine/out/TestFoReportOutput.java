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

import java.io.FileWriter;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import net.sf.reportengine.components.ParagraphProps;

public class TestFoReportOutput {

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testOutputInDifferentPageSizes() throws IOException {
        FoReportOutput classUnderTest =
            new FoReportOutput(new FileWriter("./target/TestOutputA4Portrait.fo"));
        classUnderTest.open();
        classUnderTest.output("startReport.ftl", new ReportProps(new FoOutputFormat()));
        classUnderTest.output("title.ftl",
                              new ParagraphProps("this string has been outputed from a unit test"));
        classUnderTest.output("endReport.ftl");
        classUnderTest.close();
    }

}
