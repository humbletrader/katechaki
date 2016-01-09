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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.DefaultAlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.AlgoIOKeys;

import org.junit.Test;

/**
 * @author dragos balan
 *
 */
public class TestInMemorySortStep {
	
	/**
	 * Test method for {@link net.sf.reportengine.core.steps.InMemorySortStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent, StepInput)}.
	 */
	@Test
	public void testExecute() {
		InMemorySortStep classUnderTest = new InMemorySortStep(); 
		
		AlgoContext mockContext = new DefaultAlgorithmContext(); 
		//mockContext.set(ContextKeys.DATA_COLUMNS, Scenario1.DATA_COLUMNS);
		//mockContext.set(ContextKeys.GROUP_COLUMNS, Scenario1.GROUPING_COLUMNS); 
		
		Map<AlgoIOKeys, Object> mockAlgoInput = new EnumMap<AlgoIOKeys, Object>(AlgoIOKeys.class); 
		mockAlgoInput.put(AlgoIOKeys.DATA_COLS, Scenario1.DATA_COLUMNS); 
		mockAlgoInput.put(AlgoIOKeys.GROUP_COLS, Scenario1.GROUPING_COLUMNS_WITH_SORTING); 
		
		classUnderTest.init(new StepInput(mockAlgoInput, mockContext)); 
		
		//mockContext.set(ContextKeys.ARRANGED_FOR_SORT_ROW, Scenario1.ROW_OF_DATA_6); 
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_6), new StepInput(mockAlgoInput, mockContext)); 
		
		//mockContext.set(ContextKeys.ARRANGED_FOR_SORT_ROW, Scenario1.ROW_OF_DATA_1); 
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_1), new StepInput(mockAlgoInput, mockContext)); 
		
		//mockContext.set(ContextKeys.ARRANGED_FOR_SORT_ROW, Scenario1.ROW_OF_DATA_2); 
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_2), new StepInput(mockAlgoInput, mockContext)); 
		
		//mockContext.set(ContextKeys.ARRANGED_FOR_SORT_ROW, Scenario1.ROW_OF_DATA_3); 
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_3), new StepInput(mockAlgoInput, mockContext)); 
		
		//mockContext.set(ContextKeys.ARRANGED_FOR_SORT_ROW, Scenario1.ROW_OF_DATA_4); 
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_4), new StepInput(mockAlgoInput, mockContext)); 
		
		//mockContext.set(ContextKeys.ARRANGED_FOR_SORT_ROW, Scenario1.ROW_OF_DATA_5); 
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_5), new StepInput(mockAlgoInput, mockContext)); 
		
		
		//
		StepResult<List<NewRowEvent>>exitStepResult = classUnderTest.exit(new StepInput(mockAlgoInput, mockContext)); 
		
		List<NewRowEvent> result = exitStepResult.getValue(); //(List<NewRowEvent>)mockContext.get(ContextKeys.IN_MEM_SORTED_RESULT); 
		assertNotNull(result); 
		assertEquals(6, result.size());
		
		System.out.println("result :"+ result);
		assertTrue(new NewRowEvent(Scenario1.ROW_OF_DATA_4).equals(result.get(0)));
		assertTrue(new NewRowEvent(Scenario1.ROW_OF_DATA_5).equals(result.get(1)));
		assertTrue(new NewRowEvent(Scenario1.ROW_OF_DATA_3).equals(result.get(2)));
		assertTrue(new NewRowEvent(Scenario1.ROW_OF_DATA_1).equals(result.get(3)));
		assertTrue(new NewRowEvent(Scenario1.ROW_OF_DATA_2).equals(result.get(4)));
		assertTrue(new NewRowEvent(Scenario1.ROW_OF_DATA_6).equals(result.get(5)));
		
	}
}
