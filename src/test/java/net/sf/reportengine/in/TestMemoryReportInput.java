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
package net.sf.reportengine.in;

import java.util.Arrays;

import junit.framework.TestCase;

/**
 * @author dragos
 *
 */
public class TestMemoryReportInput extends TestCase {
	
	private Object[][] DATA = new Object[][]{
			new String[]{"a","b","c","d"},
			new String[]{"1","2","3","4"},
			new String[]{"x","y","z","t"}
	};
	
	private InMemoryTableInput classUnderTest = null;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		classUnderTest = new InMemoryTableInput(DATA);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link net.sf.reportengine.in.InMemoryTableInput#hasNext()}.
	 */
	public void testHasMoreRows() {
		classUnderTest.open();
		assertTrue(classUnderTest.hasNext());
		
		assertTrue(Arrays.asList(DATA[0]).equals(classUnderTest.next()));
		
		assertTrue(classUnderTest.hasNext());
		assertTrue(Arrays.asList(DATA[1]).equals(classUnderTest.next()));
		
		assertTrue(classUnderTest.hasNext());
		assertTrue(Arrays.asList(DATA[2]).equals(classUnderTest.next()));
		
		assertFalse(classUnderTest.hasNext());
	}
}
