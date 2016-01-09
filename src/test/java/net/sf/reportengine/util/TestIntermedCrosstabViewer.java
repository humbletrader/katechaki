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
/**
 * 
 */
package net.sf.reportengine.util;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

/**
 * @author dragos balan
 *
 */
public class TestIntermedCrosstabViewer {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link net.sf.reportengine.util.IntermedCrosstabViewer#exportToHtml()}.
	 */
	@Test
	public void testExportToHtml() {
		File intermInput = ReportIoUtils.createFileFromClassPath("/TestIntermediateInput2x2x1xT.rep");
		IntermedCrosstabViewer viewer = new IntermedCrosstabViewer(intermInput, "./target/intermReportView.html");
		viewer.exportToHtml(); 
	}
}
