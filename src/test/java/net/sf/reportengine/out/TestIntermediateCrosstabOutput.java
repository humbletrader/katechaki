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
package net.sf.reportengine.out;

import net.sf.reportengine.core.steps.crosstab.IntermediateDataInfo;
import net.sf.reportengine.core.steps.crosstab.IntermediateReportRow;
import net.sf.reportengine.core.steps.crosstab.IntermediateTotalInfo;

import org.junit.Test;

/**
 * @author dragos balan (dragos dot balan at gmail dot com)
 *
 */
public class TestIntermediateCrosstabOutput {
	
	
	@Test
	public void testOutput() {
		IntermediateReportRow mockRow = new IntermediateReportRow(); 
		mockRow.addIntermComputedData(new IntermediateDataInfo("test", 0,0,0)); 
		mockRow.addIntermTotalsInfo(new IntermediateTotalInfo(100, new int[]{0,0,0}, new String[]{"dist1", "dist2", "dist3"}));
		
		IntermediateCrosstabOutput classUnderTest = new IntermediateCrosstabOutput(); 
		classUnderTest.open(); 
		classUnderTest.writeIntermRow(mockRow); 
		classUnderTest.close(); 
	}
}
