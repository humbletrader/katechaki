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
package net.sf.reportengine.core.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.core.algorithm.DefaultAlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.AlgoIOKeys;

import org.junit.Test;

public class TestExternalSortStep {
	
	private static final int MAX_ROWS_IN_MEMORY = 4;
	
	@Test
	public void testExecute() throws Exception{
		
		DefaultAlgorithmContext mockContext = new DefaultAlgorithmContext(); 
		Map<AlgoIOKeys, Object> mockAlgoInput = new EnumMap<AlgoIOKeys, Object>(AlgoIOKeys.class);
		
		mockAlgoInput.put(AlgoIOKeys.DATA_COLS, Scenario1.DATA_COLUMNS); 
		mockAlgoInput.put(AlgoIOKeys.GROUP_COLS, Scenario1.GROUPING_COLUMNS_WITH_SORTING); 
		
		ExternalSortPreparationStep classUnderTest = new ExternalSortPreparationStep(MAX_ROWS_IN_MEMORY); 
		StepResult<List<File>> initStepResult = classUnderTest.init(new StepInput(mockAlgoInput, mockContext)); 
		
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_1), new StepInput(mockAlgoInput, mockContext));
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_2), new StepInput(mockAlgoInput, mockContext));
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_3), new StepInput(mockAlgoInput, mockContext));
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_4), new StepInput(mockAlgoInput, mockContext));
		
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_5), new StepInput(mockAlgoInput, mockContext));
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_6), new StepInput(mockAlgoInput, mockContext));
		
		classUnderTest.exit(new StepInput(mockAlgoInput, mockContext)); 
		
		List<File> filesResultedFromExternalSorting = initStepResult.getValue();//(List<File>)mockContext.get(ContextKeys.SORTED_FILES); 
		assertNotNull(filesResultedFromExternalSorting); 
		assertEquals(2, filesResultedFromExternalSorting.size());
		
		
		ObjectInputStream input = new ObjectInputStream(new FileInputStream(filesResultedFromExternalSorting.get(0))); 
		NewRowEventWrapper newRowEventWrapper = (NewRowEventWrapper)input.readObject() ;
		System.out.println("row read from file "+ newRowEventWrapper+ " is compared with "+Scenario1.ROW_OF_DATA_4);
		assertNotNull(newRowEventWrapper);
		assertFalse(newRowEventWrapper.isLast()); 
		assertTrue(Scenario1.ROW_OF_DATA_4.equals(newRowEventWrapper.getNewRowEvent().getInputDataRow()));
		
		newRowEventWrapper = (NewRowEventWrapper)input.readObject() ;
		System.out.println("row read from file "+ newRowEventWrapper+ " is compared with "+Scenario1.ROW_OF_DATA_3);
		assertNotNull(newRowEventWrapper);
		assertFalse(newRowEventWrapper.isLast()); 
		assertTrue(Scenario1.ROW_OF_DATA_3.equals(newRowEventWrapper.getNewRowEvent().getInputDataRow()));
		
		newRowEventWrapper = (NewRowEventWrapper)input.readObject() ;
		System.out.println("row read from file "+ newRowEventWrapper+ " is compared with "+Scenario1.ROW_OF_DATA_1);
		assertNotNull(newRowEventWrapper);
		assertFalse(newRowEventWrapper.isLast()); 
		assertTrue(Scenario1.ROW_OF_DATA_1.equals(newRowEventWrapper.getNewRowEvent().getInputDataRow()));
		
		newRowEventWrapper = (NewRowEventWrapper)input.readObject();
		System.out.println("row read from file "+ newRowEventWrapper+ " is compared with "+Scenario1.ROW_OF_DATA_2);
		assertNotNull(newRowEventWrapper);
		assertTrue(newRowEventWrapper.isLast()); 
		assertTrue(Scenario1.ROW_OF_DATA_2.equals(newRowEventWrapper.getNewRowEvent().getInputDataRow()));
		
		
		//second file 
		input = new ObjectInputStream(new FileInputStream(filesResultedFromExternalSorting.get(1))); 
		newRowEventWrapper = (NewRowEventWrapper)input.readObject() ;
		assertNotNull(newRowEventWrapper);
		assertFalse(newRowEventWrapper.isLast()); 
		assertTrue(Scenario1.ROW_OF_DATA_5.equals(newRowEventWrapper.getNewRowEvent().getInputDataRow()));
		
		newRowEventWrapper = (NewRowEventWrapper)input.readObject() ;
		assertNotNull(newRowEventWrapper);
		assertTrue(newRowEventWrapper.isLast()); 
		assertTrue(Scenario1.ROW_OF_DATA_6.equals(newRowEventWrapper.getNewRowEvent().getInputDataRow()));
		
	}
}
