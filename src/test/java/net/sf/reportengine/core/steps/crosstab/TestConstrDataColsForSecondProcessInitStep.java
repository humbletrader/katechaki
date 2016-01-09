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
package net.sf.reportengine.core.steps.crosstab;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1With1G1D;
import net.sf.reportengine.util.CtMetadata;

import org.junit.Test;

/**
 * @author dragos balan
 *
 */
public class TestConstrDataColsForSecondProcessInitStep {

	@Test
	public void testConstructDataColumnsForSecondProcess() {
		ConstrDataColsForSecondProcessInitStep classUnderTest = new ConstrDataColsForSecondProcessInitStep(); 
		
		CtMetadata testMetadata = new CtMetadata(CtScenario2x2x1With1G1D.MOCK_DISTINCT_VALUES_HOLDER); 
		
		List<DataColumn> result = classUnderTest.constructDataColumnsForSecondProcess(testMetadata, 
															CtScenario2x2x1With1G1D.DATA_COLUMNS, 
															CtScenario2x2x1With1G1D.CROSSTAB_DATA, 
															false, 
															false); 
		assertNotNull(result);
		//TODO: continue the test
	}

}
