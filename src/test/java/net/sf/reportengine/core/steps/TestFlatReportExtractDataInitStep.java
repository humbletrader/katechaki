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
package net.sf.reportengine.core.steps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.DefaultAlgorithmContext;
import net.sf.reportengine.core.calc.AvgGroupCalculator;
import net.sf.reportengine.core.calc.CountGroupCalculator;
import net.sf.reportengine.core.calc.SumGroupCalculator;
import net.sf.reportengine.util.StepIOKeys;
import net.sf.reportengine.util.AlgoIOKeys;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Administrator
 *
 */
public class TestFlatReportExtractDataInitStep {
	
	private static List<DefaultDataColumn> TEST_DATA_COLUMNS = Arrays.asList(
		new DefaultDataColumn.Builder(0).header("No calculator column").build(),
		new DefaultDataColumn.Builder(1).header("Count Column").useCalculator(new CountGroupCalculator()).build(), 
		new DefaultDataColumn.Builder(2).header("Sum Column").useCalculator(new SumGroupCalculator()).build(), 
		new DefaultDataColumn.Builder(3).header("We don't care about this one").build(), 
		new DefaultDataColumn.Builder(4).header("Another column without calculator").build(), 
		new DefaultDataColumn.Builder(5).header("Last calculator column").useCalculator(new AvgGroupCalculator()).build()
	);
	
	

	/**
	 * Test method for {@link net.sf.reportengine.core.steps.FlatReportExtractTotalsDataInitStep#init(net.sf.reportengine.core.algorithm.AlgoContext)}.
	 */
	@Test
	public void testInit() {
		FlatReportExtractTotalsDataInitStep classUnderTest = new FlatReportExtractTotalsDataInitStep(); 
		
		AlgoContext reportContext = new DefaultAlgorithmContext(); 
		Map<AlgoIOKeys, Object> mockAlgoInput = new EnumMap<AlgoIOKeys, Object>(AlgoIOKeys.class); 
		
		//reportContext.set(ContextKeys.DATA_COLUMNS, TEST_DATA_COLUMNS); 
		mockAlgoInput.put(AlgoIOKeys.DATA_COLS, TEST_DATA_COLUMNS);
		
		StepResult stepResult = classUnderTest.init(new StepInput(mockAlgoInput, reportContext)); 
		reportContext.set(stepResult.getKey(), stepResult.getValue());
		
		ArrayList<Integer> result = (ArrayList<Integer>)reportContext.get(StepIOKeys.DISTRIBUTION_OF_CALCULATORS);
		Assert.assertNotNull(result);
		Assert.assertEquals(TEST_DATA_COLUMNS.size(), result.size());
		
		
		Assert.assertEquals(FlatReportExtractTotalsDataInitStep.NO_CALCULATOR_ON_THIS_POSITION, result.get(0)); 
		Assert.assertEquals(Integer.valueOf(0), result.get(1));
		Assert.assertEquals(Integer.valueOf(1), result.get(2));
		Assert.assertEquals(FlatReportExtractTotalsDataInitStep.NO_CALCULATOR_ON_THIS_POSITION, result.get(3));
		Assert.assertEquals(FlatReportExtractTotalsDataInitStep.NO_CALCULATOR_ON_THIS_POSITION, result.get(4));
		Assert.assertEquals(Integer.valueOf(2), result.get(5));
	}
}
