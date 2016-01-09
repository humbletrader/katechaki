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

import static org.junit.Assert.*;

import java.util.Arrays;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.scenarios.SortScenarioOnlyDataColsCount;
import net.sf.reportengine.scenarios.SortScenarioWithGroupAndDataCols;

import org.junit.Test;

public class TestNewRowComparator {

	@Test
	public void testCompareGroupColumns() {
		//first 3 cols are group cols, last 3 are data cols
		//for data columns only the last two columns need ordering 
		//the last one has a higher order priority than one before last
		NewRowComparator classUnderTest = new NewRowComparator(
													SortScenarioWithGroupAndDataCols.GROUPING_COLUMNS, 
													SortScenarioWithGroupAndDataCols.DATA_COLUMNS);
		
		//same grouping columns but, the third data column has a diff value (3 vs 6)
		NewRowEvent row1 = new NewRowEvent(Arrays.asList(new Object[]{"1","2","3", "4","5","6"})); 
		NewRowEvent row2 = new NewRowEvent(Arrays.asList(new Object[]{"1","2","3", "3","3","3"})); 
		assertTrue(classUnderTest.compare(row1, row2) > 0);
		
		//same grouping columns but, the second data column has a diff value (3 vs 5)
		row1 = new NewRowEvent(Arrays.asList(new Object[]{"1","1","1", "2","5","2"})); 
		row2 = new NewRowEvent(Arrays.asList(new Object[]{"1","1","1", "2","3","2"})); 
		assertTrue(classUnderTest.compare(row1, row2) > 0);
		
		//same grouping columns but, the third data column has a diff value (1 vs 2)
		row1 = new NewRowEvent(Arrays.asList(new Object[]{"1","1","1", "1","1","2"})); 
		row2 = new NewRowEvent(Arrays.asList(new Object[]{"1","1","1", "1","3","1"})); 
		assertTrue(classUnderTest.compare(row1, row2) > 0);
		
		//the third grouping column is different
		row1 = new NewRowEvent(Arrays.asList(new Object[]{"1","2","3", "4","5","6"})); 
		row2 = new NewRowEvent(Arrays.asList(new Object[]{"1","2","2", "2","2","2"})); 
		assertTrue(classUnderTest.compare(row1, row2) > 0);
		
		//first group column is different (1 vs 2)
		row1 = new NewRowEvent(Arrays.asList(new Object[]{"2","2","2", "4","5","6"})); 
		row2 = new NewRowEvent(Arrays.asList(new Object[]{"1","2","2", "2","2","2"})); 
		assertTrue(classUnderTest.compare(row1, row2) > 0);
		
		//same values on the rows that matter ( the first data row doesn't matter)
		row1 = new NewRowEvent(Arrays.asList(new Object[]{"1","2","2", "1000","2","2"})); 
		row2 = new NewRowEvent(Arrays.asList(new Object[]{"1","2","2", "2000","2","2"})); 
		assertEquals(0, classUnderTest.compare(row1, row2));
		
		
		//the third group column is different
		row1 = new NewRowEvent(Arrays.asList(new Object[]{"1","2","1", "2","2","2"})); 
		row2 = new NewRowEvent(Arrays.asList(new Object[]{"1","2","2", "2","2","2"})); 
		assertTrue(classUnderTest.compare(row1, row2) < 0);
		
		//the second group column is different
		row1 = new NewRowEvent(Arrays.asList(new Object[]{"1","1","1", "2","2","2"})); 
		row2 = new NewRowEvent(Arrays.asList(new Object[]{"1","2","2", "2","2","2"})); 
		assertTrue(classUnderTest.compare(row1, row2) < 0);
		
		//the second data column is different
		row1 = new NewRowEvent(Arrays.asList(new Object[]{"1","1","1", "2","1","2"})); 
		row2 = new NewRowEvent(Arrays.asList(new Object[]{"1","1","1", "2","2","2"})); 
		assertTrue(classUnderTest.compare(row1, row2) < 0);
		
		//
		row1 = new NewRowEvent(Arrays.asList(new Object[]{"1","1","1", "1","1","1"})); 
		row2 = new NewRowEvent(Arrays.asList(new Object[]{"1","1","1", "1","1","7"})); 
		assertTrue(classUnderTest.compare(row1, row2) < 0);
	}
	
	@Test
	public void testCompareDataColumns() {
		//first 3 cols are group cols, last 3 are data cols
		//for data columns only the last two columns need ordering 
		//the last one has a higher order priority than one before last
		NewRowComparator classUnderTest = new NewRowComparator(
													SortScenarioOnlyDataColsCount.GROUPING_COLUMNS, 
													SortScenarioOnlyDataColsCount.DATA_COLUMNS);
		
		//same grouping columns but the last data column (the most important among data cols) 
		//has a different value then the previous one
		NewRowEvent row1 = new NewRowEvent(Arrays.asList(new Object[]{"1","1","1", "4",	"5","0"})); 
		NewRowEvent row2 = new NewRowEvent(Arrays.asList(new Object[]{"1","1","1", "3",	"3","7"})); 
		assertTrue(classUnderTest.compare(row1, row2) > 0);//the first row is better due to the DESC type in the last column
		
		//same grouping columns but the one before last data column has a different value then the previous one
		row1 = new NewRowEvent(Arrays.asList(new Object[]{"1","1","1", "4",	"5","0"})); 
		row2 = new NewRowEvent(Arrays.asList(new Object[]{"1","1","1", "3",	"3","0"})); 
		assertTrue(classUnderTest.compare(row1, row2) > 0);//the second row is better due to the ASC type in the last column
		
		//same grouping columns but the last data column (the most important among data cols) 
		//has a different value then the previous one
		row1 = new NewRowEvent(Arrays.asList(new Object[]{"1","1","1", "4",	"5","2"})); 
		row2 = new NewRowEvent(Arrays.asList(new Object[]{"1","1","1", "3",	"3","1"})); 
		assertTrue(classUnderTest.compare(row1, row2) < 0);//the second row is better due to the DESC type in the last column
		
		
	}
}
