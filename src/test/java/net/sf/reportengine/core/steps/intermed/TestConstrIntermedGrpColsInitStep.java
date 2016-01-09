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
package net.sf.reportengine.core.steps.intermed;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.scenarios.ct.CtScenario1x1x1;
import net.sf.reportengine.scenarios.ct.CtScenario1x3x1;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1With1G1D;
import net.sf.reportengine.scenarios.ct.CtScenario4x3x1;

import org.junit.Test;

/**
 * @author dragos balan
 *
 */
public class TestConstrIntermedGrpColsInitStep {

	@Test
	public void testTransfomCrosstabColsInIntermediateColsScenario2x2x1(){
		ConstrIntermedGrpColsInitStep classUnderTest = new ConstrIntermedGrpColsInitStep(); 
		List<GroupColumn> result = classUnderTest
			.transformGroupingCrosstabConfigInFlatReportConfig(	CtScenario2x2x1With1G1D.GROUPING_COLUMNS, 
																CtScenario2x2x1With1G1D.DATA_COLUMNS, 
																CtScenario2x2x1With1G1D.HEADER_ROWS);
		assertNotNull(result); 
		assertEquals(3, result.size()); 
		assertTrue(result.get(0) instanceof DefaultGroupColumn); 
		assertEquals(0, result.get(0).getGroupingLevel()); 
		assertTrue(result.get(1) instanceof ConstrIntermedGrpColsInitStep.IntermGroupColFromCtDataCol); 
		assertEquals(1, result.get(1).getGroupingLevel()); 
		assertTrue(result.get(2) instanceof ConstrIntermedGrpColsInitStep.IntermGroupColFromHeaderRow);
		assertEquals(2, result.get(2).getGroupingLevel()); 
	}
	
	@Test
	public void testTransfomCrosstabColsInIntermediateColsScenario1x3x1(){
		ConstrIntermedGrpColsInitStep classUnderTest = new ConstrIntermedGrpColsInitStep();  
		List<GroupColumn> result = classUnderTest.transformGroupingCrosstabConfigInFlatReportConfig(
				CtScenario1x3x1.GROUP_COLUMNS, 
				CtScenario1x3x1.DATA_COLUMNS, 
				CtScenario1x3x1.HEADER_ROWS);
		
		assertNotNull(result); 
		assertEquals(3, result.size()); 
		assertTrue(result.get(0) instanceof ConstrIntermedGrpColsInitStep.IntermGroupColFromCtDataCol); 
		assertEquals(0, result.get(0).getGroupingLevel()); 
		assertTrue(result.get(1) instanceof ConstrIntermedGrpColsInitStep.IntermGroupColFromHeaderRow); 
		assertEquals(1, result.get(1).getGroupingLevel()); 
		assertTrue(result.get(2) instanceof ConstrIntermedGrpColsInitStep.IntermGroupColFromHeaderRow);
		assertEquals(2, result.get(2).getGroupingLevel()); 
	}
	
	@Test
	public void testTransfomCrosstabColsInIntermediateColsScenario4x3x1(){
		ConstrIntermedGrpColsInitStep classUnderTest = new ConstrIntermedGrpColsInitStep(); 
		List<GroupColumn> result = classUnderTest.transformGroupingCrosstabConfigInFlatReportConfig(
				CtScenario4x3x1.GROUP_COLUMNS, 
				CtScenario4x3x1.DATA_COLUMNS, 
				CtScenario4x3x1.HEADER_ROWS);
		
		assertNotNull(result); 
		assertEquals(6, result.size()); 
		
		assertTrue(result.get(0) instanceof DefaultGroupColumn); 
		assertEquals(0, result.get(0).getGroupingLevel());
		
		assertTrue(result.get(1) instanceof DefaultGroupColumn); 
		assertEquals(1, result.get(1).getGroupingLevel());
		
		assertTrue(result.get(2) instanceof DefaultGroupColumn); 
		assertEquals(2, result.get(2).getGroupingLevel());
		
		assertTrue(result.get(3) instanceof ConstrIntermedGrpColsInitStep.IntermGroupColFromCtDataCol); 
		assertEquals(3, result.get(3).getGroupingLevel());
		
		assertTrue(result.get(4) instanceof ConstrIntermedGrpColsInitStep.IntermGroupColFromHeaderRow); 
		assertEquals(4, result.get(4).getGroupingLevel()); 
		
		assertTrue(result.get(5) instanceof ConstrIntermedGrpColsInitStep.IntermGroupColFromHeaderRow);
		assertEquals(5, result.get(5).getGroupingLevel()); 
	}
	
	@Test
	public void testTransfomCrosstabColsInIntermediateColsScenario1x1x1(){
		ConstrIntermedGrpColsInitStep classUnderTest = new ConstrIntermedGrpColsInitStep(); 
		List<GroupColumn> result = classUnderTest.transformGroupingCrosstabConfigInFlatReportConfig(
				CtScenario1x1x1.GROUP_COLUMNS, 
				CtScenario1x1x1.DATA_COLUMNS, 
				CtScenario1x1x1.ROW_HEADERS);
		
		assertNotNull(result); 
		assertEquals(1, result.size()); 
		
		assertTrue(result.get(0) instanceof ConstrIntermedGrpColsInitStep.IntermGroupColFromCtDataCol); 
		assertEquals(0, result.get(0).getGroupingLevel());
	}

}
