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
import net.sf.reportengine.scenarios.ct.CtScenario1x3x1;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1With1G1D;

/**
 * @author Administrator
 *
 */
public class TestDistinctValuesHolder extends TestCase {

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * Test method for {@link net.sf.reportengine.util.DefaultDistinctValuesHolder#addValueIfNotExist(int, java.lang.Object)}.
	 */
	public void testAddValueIfNotExist() {
		DefaultDistinctValuesHolder classUnderTest = new DefaultDistinctValuesHolder(CtScenario2x2x1With1G1D.HEADER_ROWS); 
		
		//add on first level
		assertEquals(0, classUnderTest.addValueIfNotExist(0, "level0value0"));
		assertEquals(1, classUnderTest.addValueIfNotExist(0, "level0value1")); 
		assertEquals(2, classUnderTest.addValueIfNotExist(0, "level0value2"));
		assertEquals(3, classUnderTest.addValueIfNotExist(0, "level0value3"));
		
		//second level
		assertEquals(0, classUnderTest.addValueIfNotExist(1, "level1value0"));
		assertEquals(1, classUnderTest.addValueIfNotExist(1, "level1value1"));
		assertEquals(2, classUnderTest.addValueIfNotExist(1, "level1value2"));
		
		//back to first level
		assertEquals(1, classUnderTest.addValueIfNotExist(0, "level0value1"));
		assertEquals(3, classUnderTest.addValueIfNotExist(0, "level0value3"));
		
		//back to second level
		assertEquals(1, classUnderTest.addValueIfNotExist(1, "level1value1"));
		
		//thrird level (should generate an error)
		try{
			assertEquals(0, classUnderTest.addValueIfNotExist(2, "level2value0"));
			fail("this piece of code should not be reached");
		}catch(IllegalArgumentException  ex){
			assertEquals("header row 2 could not be found", ex.getMessage()); 
		}
	}

	/**
	 * Test method for {@link net.sf.reportengine.util.DefaultDistinctValuesHolder#computeCrosstabCoefficients()}.
	 */
	public void testComputeCrosstabCoefficient2x2x1sNoTotals() {
			DefaultDistinctValuesHolder classUnderTest = new DefaultDistinctValuesHolder(CtScenario2x2x1With1G1D.HEADER_ROWS); 
			assertNotNull(classUnderTest);
			
			classUnderTest.addValueIfNotExist(0, "North");
			classUnderTest.addValueIfNotExist(0, "South"); 
			classUnderTest.addValueIfNotExist(0, "East"); 
			classUnderTest.addValueIfNotExist(0, "West"); 
			
			classUnderTest.addValueIfNotExist(1, "M"); 
			classUnderTest.addValueIfNotExist(1, "F");
			
			assertNotNull(classUnderTest.getDistinctValuesForLevel(0));
			assertNotNull(classUnderTest.getDistinctValuesForLevel(1)); 
			
			//test colcount and rowcount
			
			assertEquals(classUnderTest.getRowsCount(), 2);
			
			//test number of distinct values per row
			assertEquals(4, classUnderTest.getDistinctValuesCountForLevel(0));
			assertEquals(2, classUnderTest.getDistinctValuesCountForLevel(1)); 
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.util.DefaultDistinctValuesHolder#computeCrosstabCoefficients()}.
	 */
	public void testComputeCrosstabCoefficient1x3x1sNoTotals() {
			DefaultDistinctValuesHolder classUnderTest = new DefaultDistinctValuesHolder(CtScenario1x3x1.HEADER_ROWS); 
			assertNotNull(classUnderTest);
			
			classUnderTest.addValueIfNotExist(0, "North");
			classUnderTest.addValueIfNotExist(0, "South"); 
			classUnderTest.addValueIfNotExist(0, "East"); 
			classUnderTest.addValueIfNotExist(0, "West"); 
			
			classUnderTest.addValueIfNotExist(1, "M"); 
			classUnderTest.addValueIfNotExist(1, "F");
			
			classUnderTest.addValueIfNotExist(2, "20");
			classUnderTest.addValueIfNotExist(2, "50"); 
			classUnderTest.addValueIfNotExist(2, "80"); 
			
			assertNotNull(classUnderTest.getDistinctValuesForLevel(0));
			assertNotNull(classUnderTest.getDistinctValuesForLevel(1)); 
			assertNotNull(classUnderTest.getDistinctValuesForLevel(2)); 
			
			assertEquals(classUnderTest.getRowsCount(), 3);
			
			//test number of distinct values per row
			assertEquals(4, classUnderTest.getDistinctValuesCountForLevel(0));
			assertEquals(2, classUnderTest.getDistinctValuesCountForLevel(1)); 
			assertEquals(3, classUnderTest.getDistinctValuesCountForLevel(2)); 
	}

}
