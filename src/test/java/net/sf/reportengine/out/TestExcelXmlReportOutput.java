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

import static org.junit.Assert.*;

import java.io.IOException;

import net.sf.reportengine.components.ParagraphProps;
import net.sf.reportengine.out.ExcelXmlOutputFormat;
import net.sf.reportengine.out.ExcelXmlReportOutput;
import net.sf.reportengine.out.ReportProps;
import net.sf.reportengine.util.ReportIoUtils;

import org.junit.Before;
import org.junit.Test;

public class TestExcelXmlReportOutput {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testUtf8Characters() throws IOException {
		String OUTPUT_PATH = "target/TestExcelXmlReportOutputUtf8.xml";
		ExcelXmlReportOutput testOutput = new ExcelXmlReportOutput(
				ReportIoUtils.createWriterFromPath(OUTPUT_PATH)); 
		testOutput.open();
		
		//calling startreport.ftl and endreport.ftl is important for pdf because 
		//the resulting xml does not have a root and a well-defined namespace
		testOutput.output("startReport.ftl", new ReportProps(new ExcelXmlOutputFormat()));
		testOutput.output("title.ftl", new ParagraphProps("На берегу пустынных волн")); 
		testOutput.output("title.ftl", new ParagraphProps("Τη γλώσσα μου έδωσαν ελληνική"));
		testOutput.output("endReport.ftl");
		testOutput.close(); 	
	}

}
