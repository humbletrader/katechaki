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

import org.apache.commons.lang.ArrayUtils;

import junit.framework.TestCase;

/**
 * @author dragos
 *
 */
public class TestCoefficients extends TestCase {
	
	private CrossTabCoefficients classUnderTest = null;
	
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
	 * Test method for {@link net.sf.reportengine.util.CrossTabCoefficients#Coefficients(java.lang.String[][], boolean)}.
	 */
	public void testCoefficientsNoTotals1() {
		String[][] distinctValues = new String[][]{
				new String[]{"North","South","East","West"},
				new String[]{"M","F"}
		};
		
		classUnderTest = new CrossTabCoefficients(distinctValues, false);
		assertNotNull(classUnderTest);
		
		//test colcount and rowcount
		assertEquals(classUnderTest.getTemplateColumnCount(), 8);
		assertEquals(classUnderTest.getTemplateRowCount(), 2);
		
		//test number of distinct values per row
		int[] result = classUnderTest.getDistValuesCntInHeaderRow();
		assertNotNull(result);
		System.out.println("Distinct Values : "+ArrayUtils.toString(result));
		assertTrue(ArrayUtils.isEquals(result, new int[]{4,2}));
		
		//test number of spaces
		result = classUnderTest.getSpacesCntInHeaderRow();
		assertNotNull(result);
		System.out.println("Spaces Cnt (if totals were displayed): "+ArrayUtils.toString(result));
		assertTrue(ArrayUtils.isEquals(result, new int[]{0,0}));
		
		//test number of totals
		result = classUnderTest.getTotalsCntInHeaderRow();
		assertNotNull(result);
		System.out.println("Totals Cnt : "+ArrayUtils.toString(result));
		assertTrue(ArrayUtils.isEquals(result, new int[]{0,0}));
		
		//test number of totals
		result = classUnderTest.getColspanPerRow();
		assertNotNull(result);
		System.out.println("Distance between distinct header values : "+ArrayUtils.toString(result));
		assertTrue(ArrayUtils.isEquals(result, new int[]{2,1}));
	}
	
	
	/**
	 * Test method for {@link net.sf.reportengine.util.CrossTabCoefficients#Coefficients(java.lang.String[][], boolean)}.
	 */
	public void testCoefficientsWithTotals1() {
		String[][] distinctValues = new String[][]{
				new String[]{"North","South","East","West"},
				new String[]{"M","F"}
		};
		
		classUnderTest = new CrossTabCoefficients(distinctValues, true);
		assertNotNull(classUnderTest);
		
		//test colcount and rowcount
		assertEquals(classUnderTest.getTemplateColumnCount(), 13);
		assertEquals(classUnderTest.getTemplateRowCount(), 2);
		
		//test number of distinct values per row
		int[] result = classUnderTest.getDistValuesCntInHeaderRow();
		assertNotNull(result);
		System.out.println("Distinct Values : "+ArrayUtils.toString(result));
		assertTrue(ArrayUtils.isEquals(result, new int[]{4,2}));
		
		//test number of spaces
		result = classUnderTest.getSpacesCntInHeaderRow();
		assertNotNull(result);
		System.out.println("Spaces Cnt (if totals were displayed): "+ArrayUtils.toString(result));
		assertTrue(ArrayUtils.isEquals(result, new int[]{0,1}));
		
		//test number of totals
		result = classUnderTest.getTotalsCntInHeaderRow();
		assertNotNull(result);
		System.out.println("Totals Cnt : "+ArrayUtils.toString(result));
		assertTrue(ArrayUtils.isEquals(result, new int[]{1,4}));
		
		//test number of totals
		result = classUnderTest.getColspanPerRow();
		assertNotNull(result);
		System.out.println("Distance between distinct header values : "+ArrayUtils.toString(result));
		assertTrue(ArrayUtils.isEquals(result, new int[]{3,1}));
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.util.CrossTabCoefficients#Coefficients(java.lang.String[][], boolean)}.
	 */
	public void testCoefficientsNoTotals2() {
		String[][] distinctValues = new String[][]{
				new String[]{"North","South","East","West"},
				new String[]{"M","F"},
				new String[]{"20", "50", "80"}
		};
		
		classUnderTest = new CrossTabCoefficients(distinctValues, false);
		assertNotNull(classUnderTest);
		
		//test colcount and rowcount
		assertEquals(classUnderTest.getTemplateColumnCount(), 24);
		assertEquals(classUnderTest.getTemplateRowCount(), 3);
		
		//test number of distinct values per row
		int[] result = classUnderTest.getDistValuesCntInHeaderRow();
		assertNotNull(result);
		System.out.println("Distinct Values : "+ArrayUtils.toString(result));
		assertTrue(ArrayUtils.isEquals(result, new int[]{4,2,3}));
		
		//test number of spaces
		result = classUnderTest.getSpacesCntInHeaderRow();
		assertNotNull(result);
		System.out.println("Spaces Cnt (if totals were displayed): "+ArrayUtils.toString(result));
		assertTrue(ArrayUtils.isEquals(result, new int[]{0,0,0}));
		
		//test number of totals
		result = classUnderTest.getTotalsCntInHeaderRow();
		assertNotNull(result);
		System.out.println("Totals Cnt : "+ArrayUtils.toString(result));
		assertTrue(ArrayUtils.isEquals(result, new int[]{0,0,0}));
		
		//test number of totals
		result = classUnderTest.getColspanPerRow();
		assertNotNull(result);
		System.out.println("Distance between distinct header values : "+ArrayUtils.toString(result));
		assertTrue(ArrayUtils.isEquals(result, new int[]{6,3,1}));
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.util.CrossTabCoefficients#Coefficients(java.lang.String[][], boolean)}.
	 */
	public void testCoefficientsWithTotals2() {
		String[][] distinctValues = new String[][]{
				new String[]{"North","South","East","West"},
				new String[]{"M","F"},
				new String[]{"20", "50", "80"}
		};
		
		classUnderTest = new CrossTabCoefficients(distinctValues, true);
		assertNotNull(classUnderTest);
		
		//test colcount and rowcount
		assertEquals(classUnderTest.getTemplateColumnCount(), 37);
		assertEquals(classUnderTest.getTemplateRowCount(), 3);
		
		//test number of distinct values per row
		int[] result = classUnderTest.getDistValuesCntInHeaderRow();
		assertNotNull(result);
		System.out.println("Distinct Values : "+ArrayUtils.toString(result));
		assertTrue(ArrayUtils.isEquals(result, new int[]{4,2,3}));
		
		//test number of spaces
		result = classUnderTest.getSpacesCntInHeaderRow();
		assertNotNull(result);
		System.out.println("Spaces Cnt (if totals were displayed): "+ArrayUtils.toString(result));
		assertTrue(ArrayUtils.isEquals(result, new int[]{0,1,5}));
		
		//test number of totals
		result = classUnderTest.getTotalsCntInHeaderRow();
		assertNotNull(result);
		System.out.println("Totals Cnt : "+ArrayUtils.toString(result));
		assertTrue(ArrayUtils.isEquals(result, new int[]{1,4,8}));
		
		//test number of totals
		result = classUnderTest.getColspanPerRow();
		assertNotNull(result);
		System.out.println("Distance between distinct header values : "+ArrayUtils.toString(result));
		assertTrue(ArrayUtils.isEquals(result, new int[]{9,4,1}));
	}
}
