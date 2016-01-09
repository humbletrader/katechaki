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
package net.sf.reportengine.config;

import junit.framework.TestCase;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.core.calc.SumGroupCalculator;

/**
 * @author dragos
 *
 */
public class TestDefaultDataColumn extends TestCase {

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link net.sf.reportengine.config.DefaultDataColumn#DefaultDataColumn(java.lang.String, int, net.sf.reportengine.core.calc.GroupCalculator, java.text.Format)}.
	 */
	public void testDefaultDataColumnStringIntICalculatorFormat() {
		DefaultDataColumn classUnderTest = new DefaultDataColumn(); 
		classUnderTest.setHeader("Month"); 
		classUnderTest.setInputColumnIndex(0);
		classUnderTest.setCalculator(new SumGroupCalculator());
	}
	
	
	public void testDefaultDataColumnConstructor() {
		DefaultDataColumn classUnderTest = new DefaultDataColumn("Month", 0, new SumGroupCalculator()); 
	}
}
