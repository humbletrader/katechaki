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

import junit.framework.TestCase;

/**
 * @author Administrator
 *
 */
public class TestHeaderDistinctValuesDescriptor extends TestCase {
	
	private DistinctValuesRow classUnderTest; 
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		classUnderTest = new DistinctValuesRow();
	}

	/**
	 * Test method for {@link net.sf.reportengine.util.DistinctValuesRow#addDistinctValueIfNotExists(java.lang.Object)}.
	 */
	public void testAddDistinctValueIfNotExists1() {
		int result = classUnderTest.addDistinctValueIfNotExists("One");
		assertEquals(result, 0);
		
		result = classUnderTest.addDistinctValueIfNotExists("Two");
		assertEquals(result, 1);
		
		result = classUnderTest.addDistinctValueIfNotExists("Three");
		assertEquals(result, 2);
		
		result = classUnderTest.addDistinctValueIfNotExists("Two");
		assertEquals(result, 1);
		
		result = classUnderTest.addDistinctValueIfNotExists("One");
		assertEquals(result, 0);
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.util.DistinctValuesRow#addDistinctValueIfNotExists(java.lang.Object)}.
	 */
	public void testAddDistinctValueIfNotExists2() {
		int result = classUnderTest.addDistinctValueIfNotExists(1);
		assertEquals(result, 0);
		
		result = classUnderTest.addDistinctValueIfNotExists(2);
		assertEquals(result, 1);
		
		result = classUnderTest.addDistinctValueIfNotExists(1);
		assertEquals(result, 0);
		
		result = classUnderTest.addDistinctValueIfNotExists(3);
		assertEquals(result, 2);
				
		result = classUnderTest.addDistinctValueIfNotExists(2);
		assertEquals(result, 1);
	}

}
