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
package net.sf.reportengine.core.calc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.apache.commons.lang.math.NumberUtils;
import org.junit.Test;

/**
 * @author dragos balan
 *
 */
public class TestCalculators {

	@Test
	public void testCountCalculator() {
		CountGroupCalculator calculator = new CountGroupCalculator(); 
		assertTrue(calculator.init() instanceof DefaultCalcIntermResult); 
		assertTrue(calculator.init().getResult() instanceof Integer); 
		assertEquals(calculator.init().getResult(), NumberUtils.INTEGER_ZERO); 
		
		DefaultCalcIntermResult<Integer> testIntermResult = new DefaultCalcIntermResult<Integer>(0); 
		DefaultCalcIntermResult<Integer> newResult = calculator.compute(testIntermResult, "first object"); 
		
		assertNotNull(newResult); 
		assertNotNull(newResult.getResult()); 
		assertEquals(newResult.getResult(), NumberUtils.INTEGER_ONE); 
		
		testIntermResult = new DefaultCalcIntermResult<Integer>(Integer.valueOf(7)); 
		newResult = calculator.compute(testIntermResult, "another object here"); 
		
		assertNotNull(newResult); 
		assertNotNull(newResult.getResult()); 
		assertEquals(newResult.getResult(), Integer.valueOf(8)); 
		
	}
	
	
	@Test
	public void testUniversalSumCalculator() {
		SumGroupCalculator calculator = new SumGroupCalculator(); 
		assertTrue(calculator.init() instanceof DefaultCalcIntermResult); 
		assertTrue(calculator.init().getResult() instanceof BigDecimal); 
		assertEquals(calculator.init().getResult(), BigDecimal.ZERO); 
		
		
		DefaultCalcIntermResult<BigDecimal> testIntermResult = new DefaultCalcIntermResult<BigDecimal>(BigDecimal.ZERO); 
		DefaultCalcIntermResult<BigDecimal> newResult = calculator.compute(testIntermResult, "1"); 
		
		assertNotNull(newResult); 
		assertNotNull(newResult.getResult()); 
		assertEquals(newResult.getResult(), new BigDecimal(1)); 
		
		testIntermResult = newResult; 
		newResult = calculator.compute(testIntermResult, Integer.valueOf(7)); 
		
		assertNotNull(newResult); 
		assertNotNull(newResult.getResult()); 
		assertEquals(newResult.getResult(), new BigDecimal(8));
		
		testIntermResult = new DefaultCalcIntermResult<BigDecimal>(BigDecimal.ZERO); 
		newResult = calculator.compute(testIntermResult, new BigDecimal(1.18907)); 
		
		assertNotNull(newResult); 
		assertNotNull(newResult.getResult()); 
		assertTrue(newResult.getResult().doubleValue()== 1.18907D); 
		
		testIntermResult = new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(1.18907)); 
		newResult = calculator.compute(testIntermResult, new Double(2.3));
		
		assertNotNull(newResult); 
		assertNotNull(newResult.getResult()); 
		assertTrue(newResult.getResult().doubleValue() == 3.48907);
	}
	
	@Test
	public void testUniversalAvgCalculator() {
		AvgGroupCalculator calculator = new AvgGroupCalculator(); 
		assertTrue(calculator.init() instanceof AvgCalcIntermResult); 
		assertTrue(calculator.init().getResult() instanceof BigDecimal); 
		assertEquals(calculator.init().getResult(), BigDecimal.ZERO); 
		assertEquals(calculator.init().getCount(), 0); 
		
		
		AvgCalcIntermResult<BigDecimal> testIntermResult = new AvgCalcIntermResult<BigDecimal>(BigDecimal.ZERO, 0); 
		AvgCalcIntermResult<BigDecimal> newResult = calculator.compute(testIntermResult, "1"); 
		
		assertNotNull(newResult); 
		assertNotNull(newResult.getResult()); 
		assertEquals(newResult.getResult(), new BigDecimal(1));
		assertEquals(newResult.getCount(), 1); 
		
		testIntermResult = newResult; //ie. result=1, count=1
		newResult = calculator.compute(testIntermResult, Integer.valueOf(7)); 
		
		assertNotNull(newResult); 
		assertNotNull(newResult.getResult()); 
		assertEquals(newResult.getResult(), new BigDecimal(4));
		assertEquals(newResult.getCount(), 2); 
		
		//reset
		testIntermResult = new AvgCalcIntermResult<BigDecimal>(BigDecimal.ZERO, 0); 
		newResult = calculator.compute(testIntermResult, "1.1890"); 
		
		assertNotNull(newResult);
		assertNotNull(newResult.getResult()); 
		assertTrue(newResult.getResult().doubleValue()== 1.1890); 
		
		testIntermResult = newResult; 
		newResult = calculator.compute(testIntermResult, new Double(2.3));
		
		assertNotNull(newResult); 
		assertNotNull(newResult.getResult()); 
		assertEquals(newResult.getResult().doubleValue(), 1.7445D, 0/*delta*/);
		assertEquals(newResult.getCount(), 2); 
		
		testIntermResult = newResult; 
		newResult = calculator.compute(testIntermResult, new Double(2.511)); 
		
		assertNotNull(newResult); 
		assertNotNull(newResult.getResult()); 
		assertEquals(newResult.getResult().doubleValue(), 2.0, 0/*delta*/);
	}
}
