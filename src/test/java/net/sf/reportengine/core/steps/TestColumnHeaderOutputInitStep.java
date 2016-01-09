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

import java.io.StringWriter;
import java.util.EnumMap;
import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.DefaultAlgorithmContext;
import net.sf.reportengine.core.steps.ColumnHeaderOutputInitStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.out.AbstractFreemarkerReportOutput;
import net.sf.reportengine.out.MockReportOutput;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.StepIOKeys;
import net.sf.reportengine.util.AlgoIOKeys;

import org.junit.Test;

/**
 * @author dragos
 *
 */
public class TestColumnHeaderOutputInitStep  {
	
	@Test
	public void testInitScenario1() {
		AlgoContext mockContext = new DefaultAlgorithmContext(); 
		
		StringWriter testWriter = new StringWriter(); 
		MockReportOutput mockOutput = new MockReportOutput(testWriter);  
		mockOutput.open(); 
		
		Map<AlgoIOKeys, Object> mockAlgoInput = new EnumMap<AlgoIOKeys, Object>(AlgoIOKeys.class);  
		mockAlgoInput.put(AlgoIOKeys.DATA_COLS, Scenario1.DATA_COLUMNS); 
		mockAlgoInput.put(AlgoIOKeys.GROUP_COLS, Scenario1.GROUPING_COLUMNS) ;
		mockAlgoInput.put(AlgoIOKeys.NEW_REPORT_OUTPUT, mockOutput); 
		
		ColumnHeaderOutputInitStep classUnderTest = new ColumnHeaderOutputInitStep(); 
		classUnderTest.init(new StepInput(mockAlgoInput, mockContext));
		
		mockOutput.close();
		System.out.println(testWriter.getBuffer());
		
//		Assert.assertTrue(MatrixUtils.compareMatrices(Scenario1.EXPECTED_REPORT_COLUMNS_HEADER, 
//												mockOutput.getHeaderCellMatrix()));
	}
	
	
}
