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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.CalcIntermResult;
import net.sf.reportengine.core.calc.GroupCalculators;

import org.apache.commons.lang.math.NumberUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author dragos balan
 *
 */
public class TestCalculatorIntermResultMatrix {
	
	/**
	 * the class to be tested
	 */
	private CalculatorIntermResultsMatrix classUnderTest; 
	
	private static List<Object> TEST_VALUES = Arrays.asList(new Object[]{1,2,3,4,5,6,7});
	
	private static List<DataColumn> TEST_DATA_COLUMNS = Arrays.asList(
			new DataColumn[]{
					new DefaultDataColumn("No calculator column", 0, null),
					new DefaultDataColumn("Count Column", 1, GroupCalculators.COUNT), 
					new DefaultDataColumn("Sum Column", 2, GroupCalculators.SUM), 
					new DefaultDataColumn("We don't care about this one", 3)
	});
	
	@Test
	public void testExtractCalculatorsData(){
		classUnderTest = new CalculatorIntermResultsMatrix(3, TEST_DATA_COLUMNS);
		classUnderTest.initAll(); 
		
		Assert.assertNotNull(classUnderTest);
		Assert.assertNotNull(classUnderTest.getDataColumnsHavingCalculators());
		Assert.assertNotNull(classUnderTest.getCalculatorPrototypes());
		Assert.assertNotNull(classUnderTest.getIndexOfColumnsHavingCalculators()); 
		
		Assert.assertEquals(2, classUnderTest.getDataColumnsHavingCalculators().size());
		Assert.assertEquals(2, classUnderTest.getIndexOfColumnsHavingCalculators().size());
		Assert.assertEquals(2, classUnderTest.getCalculatorPrototypes().size());
		
		Assert.assertEquals(Integer.valueOf(1), classUnderTest.getIndexOfColumnsHavingCalculators().get(0));
		Assert.assertEquals(Integer.valueOf(2), classUnderTest.getIndexOfColumnsHavingCalculators().get(1));
		
	}
	
	
	/**
	 * Test method for {@link net.sf.reportengine.util.CalculatorIntermResultsMatrix#initRow(int)}.
	 */
	@Test
	public final void testInitRow() {
		classUnderTest = new CalculatorIntermResultsMatrix(1, TEST_DATA_COLUMNS);
		
		//call the method under test
		classUnderTest.initRow(0);
		
		//check the interm results matrix
		Assert.assertNotNull(classUnderTest);
		Assert.assertNotNull(classUnderTest.getIntermResultsMatrix());
		Assert.assertEquals(classUnderTest.getIntermResultsMatrix().length, 1);
		
		//the first row should be re-initialized
		CalcIntermResult[] row1 = classUnderTest.getIntermResultsMatrix()[0];
		Assert.assertNotNull(row1);
		Assert.assertEquals(row1.length, 2);
		Assert.assertEquals(row1[0].getResult(), NumberUtils.INTEGER_ZERO);
		Assert.assertEquals(row1[1].getResult(), BigDecimal.ZERO);
	}
	
	@Test
	public void testInitFirstXRows() {
		classUnderTest = new CalculatorIntermResultsMatrix(3, TEST_DATA_COLUMNS);
		classUnderTest.initAll(); 
		
		classUnderTest.addValuesToEachRow(new NewRowEvent(TEST_VALUES));
		
		//reinit the first two columns
		classUnderTest.initFirstXRows(2);
		
		//check the interm results matrix
		Assert.assertNotNull(classUnderTest);
		Assert.assertNotNull(classUnderTest.getIntermResultsMatrix());
		Assert.assertEquals(classUnderTest.getIntermResultsMatrix().length, 3);
		
		//the first row should be re-initialized
		CalcIntermResult[] row1 = classUnderTest.getIntermResultsMatrix()[0];
		Assert.assertNotNull(row1);
		Assert.assertEquals(row1.length, 2);
		Assert.assertEquals(row1[0].getResult(), NumberUtils.INTEGER_ZERO);
		Assert.assertEquals(row1[1].getResult(), BigDecimal.ZERO);
		
		//the second row should be also re-initialized
		CalcIntermResult[] row2 = classUnderTest.getIntermResultsMatrix()[1];
		Assert.assertNotNull(row2);
		Assert.assertEquals(row2.length, 2);
		Assert.assertEquals(row2[0].getResult(), NumberUtils.INTEGER_ZERO);
		Assert.assertEquals(row2[1].getResult(), BigDecimal.ZERO);
		
		//the third row should not be re-initialized
		CalcIntermResult[] row3 = classUnderTest.getIntermResultsMatrix()[2];
		Assert.assertNotNull(row3);
		Assert.assertEquals(row3.length, 2);
		Assert.assertNotSame(row3[0].getResult(), NumberUtils.INTEGER_ZERO);
		Assert.assertNotSame(row3[1].getResult(), BigDecimal.ZERO);
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.util.CalculatorIntermResultsMatrix#initAllCalculators()}.
	 */
	@Test
	public final void testInitAll() {
		int TEST_ROW_COUNT = 7; 
		classUnderTest = new CalculatorIntermResultsMatrix(TEST_ROW_COUNT, TEST_DATA_COLUMNS);
		classUnderTest.initAll(); 
		
		Assert.assertNotNull(classUnderTest);
		Assert.assertNotNull(classUnderTest.getIntermResultsMatrix());
		Assert.assertEquals(classUnderTest.getIntermResultsMatrix().length, TEST_ROW_COUNT);
		
		CalcIntermResult[] row = null; 
		for(int i=0; i< TEST_ROW_COUNT; i++){
			row = classUnderTest.getIntermResultsMatrix()[i];
			Assert.assertNotNull(row);
			Assert.assertEquals(row.length, 2);
			
			//test the column with COUNT
			Assert.assertNotNull(row[0]);
			Object result = row[0].getResult();
			Assert.assertTrue(result instanceof Integer);
			Assert.assertEquals(result, 0);
			
			//test the column with SUM
			Assert.assertNotNull(row[1]);
			result = row[1].getResult();
			Assert.assertTrue(result instanceof BigDecimal);
			Assert.assertEquals(((BigDecimal)result).intValue(), 0);
		}
	}

	@Test
	public void testAddValuesToEachRow() {
		classUnderTest = new CalculatorIntermResultsMatrix(3, TEST_DATA_COLUMNS);
		classUnderTest.initAll(); 
		
		classUnderTest.addValuesToEachRow(new NewRowEvent(TEST_VALUES));
		
		Assert.assertNotNull(classUnderTest);
		Assert.assertNotNull(classUnderTest.getIntermResultsMatrix());
		Assert.assertEquals(classUnderTest.getIntermResultsMatrix().length, 3);
		
		CalcIntermResult[] row = null; 
		for(int i=0; i< 3; i++){
			row = classUnderTest.getIntermResultsMatrix()[i];
			Assert.assertNotNull(row);
			Assert.assertEquals(row.length, 2);
			
			//test the column with COUNT
			Assert.assertNotNull(row[0]);
			Object result = row[0].getResult();
			Assert.assertTrue(result instanceof Integer);
			Assert.assertEquals(result, 1);
			
			//test the column with SUM
			Assert.assertNotNull(row[1]);
			result = row[1].getResult();
			Assert.assertTrue(result instanceof BigDecimal);
			Assert.assertEquals(((BigDecimal)result).intValue(), 3);
		}
		
		//add values once more
		classUnderTest.addValuesToEachRow(new NewRowEvent(TEST_VALUES));
		Assert.assertNotNull(classUnderTest);
		Assert.assertNotNull(classUnderTest.getIntermResultsMatrix()); 
		Assert.assertEquals(classUnderTest.getIntermResultsMatrix().length, 3);
		
		//TEST FIRST ROW
		row = classUnderTest.getIntermResultsMatrix()[0];
		Assert.assertNotNull(row);
		Assert.assertEquals(row.length, 2);
		
		//count on first row first column
		Assert.assertNotNull(row[0]);
		Object result = row[0].getResult();
		Assert.assertTrue(result instanceof Integer);
		Assert.assertEquals(result, 2);
		
		//sum on the first row second column
		Assert.assertNotNull(row[1]);
		result = row[1].getResult();
		Assert.assertTrue(result instanceof BigDecimal);
		Assert.assertEquals(((BigDecimal)result).intValue(), 6);
		
		//TEST SECOND ROW
		row = classUnderTest.getIntermResultsMatrix()[1];
		Assert.assertNotNull(row);
		Assert.assertEquals(row.length, 2);
		
		//count on first row first column
		Assert.assertNotNull(row[0]);
		result = row[0].getResult();
		Assert.assertTrue(result instanceof Integer);
		Assert.assertEquals(result, 2);
		
		//sum on the first row second column
		Assert.assertNotNull(row[1]);
		result = row[1].getResult();
		Assert.assertTrue(result instanceof BigDecimal);
		Assert.assertEquals(((BigDecimal)result).intValue(), 6);
		
		
		//TEST THIRD ROW
		row = classUnderTest.getIntermResultsMatrix()[2];
		Assert.assertNotNull(row);
		Assert.assertEquals(row.length, 2);
		
		//count on third row first column
		Assert.assertNotNull(row[0]);
		result = row[0].getResult();
		Assert.assertTrue(result instanceof Integer);
		Assert.assertEquals(result, 2);
		
		//sum on the third row second column
		Assert.assertNotNull(row[1]);
		result = row[1].getResult();
		Assert.assertTrue(result instanceof BigDecimal);
		Assert.assertEquals(((BigDecimal)result).intValue(), 6);
		
	}

}
