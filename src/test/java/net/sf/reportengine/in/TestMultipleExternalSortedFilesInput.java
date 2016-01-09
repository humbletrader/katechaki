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
package net.sf.reportengine.in;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.core.steps.NewRowComparator;
import net.sf.reportengine.core.steps.NewRowEventWrapper;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.ReportIoUtils;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author dragos
 *
 */
public class TestMultipleExternalSortedFilesInput {
	
	private List<File> testFiles; 
	private NewRowComparator testComparator; 
	
	
	@Before
	public void setUp(){
		testFiles = new ArrayList<File>(); 
		testFiles.add(ReportIoUtils.createFileFromClassPath("/ExternalSortedAndSerializedFile1.tmp"));
		testFiles.add(ReportIoUtils.createFileFromClassPath("/ExternalSortedAndSerializedFile2.tmp"));
		
		testComparator = new NewRowComparator(Scenario1.GROUPING_COLUMNS_WITH_SORTING, Scenario1.DATA_COLUMNS); 
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.in.MultipleExternalSortedFilesTableInput#next()}.
	 */
	@Test
	public void testNextRow() {
		MultipleExternalSortedFilesTableInput classUnderTest = 
				new MultipleExternalSortedFilesTableInput(testFiles, testComparator);
		
		List<Object> newRow = classUnderTest.next(); 
		assertNotNull(newRow); 
		assertTrue(Scenario1.ROW_OF_DATA_4.equals(newRow)); 
		
		newRow = classUnderTest.next(); 
		assertNotNull(newRow);
		assertTrue(Scenario1.ROW_OF_DATA_5.equals(newRow));  
		
		newRow = classUnderTest.next(); 
		assertNotNull(newRow);
		assertTrue(Scenario1.ROW_OF_DATA_3.equals(newRow)); 
		
		newRow = classUnderTest.next(); 
		assertNotNull(newRow);
		assertTrue(Scenario1.ROW_OF_DATA_1.equals(newRow)); 
		
		newRow = classUnderTest.next(); 
		assertNotNull(newRow);
		assertTrue(Scenario1.ROW_OF_DATA_2.equals(newRow)); 
		
		newRow = classUnderTest.next(); 
		assertNotNull(newRow);
		assertTrue(Scenario1.ROW_OF_DATA_6.equals(newRow));  
		
		newRow = classUnderTest.next(); 
		assertTrue(newRow == null);
		
		newRow = classUnderTest.next(); 
		assertTrue(newRow == null);
	}

	/**
	 * Test method for {@link net.sf.reportengine.in.MultipleExternalSortedFilesTableInput#hasNext()}.
	 */
	@Test
	public void testHasMoreRows() {
		MultipleExternalSortedFilesTableInput classUnderTest = 
				new MultipleExternalSortedFilesTableInput(testFiles, testComparator);
		
		assertTrue(classUnderTest.hasNext());
		classUnderTest.next(); 
		
		assertTrue(classUnderTest.hasNext());
		classUnderTest.next(); 
		
		assertTrue(classUnderTest.hasNext());
		classUnderTest.next(); 
		
		assertTrue(classUnderTest.hasNext());
		classUnderTest.next(); 
		
		assertTrue(classUnderTest.hasNext());
		classUnderTest.next(); 
		
		assertTrue(classUnderTest.hasNext());
		classUnderTest.next(); 
		
		assertFalse(classUnderTest.hasNext());
		assertFalse(classUnderTest.hasNext());
		assertFalse(classUnderTest.hasNext());
	}
	
	/**
	 * show the contents of the test file
	 * @throws Exception
	 */
	@Ignore
	public void caca() throws Exception{
		ObjectInputStream is = new ObjectInputStream(new FileInputStream(testFiles.get(0)));
		NewRowEventWrapper obj = (NewRowEventWrapper)is.readObject();
		System.out.println(obj);
		obj = (NewRowEventWrapper)is.readObject();
		System.out.println(obj);
		obj = (NewRowEventWrapper)is.readObject();
		System.out.println(obj);
		obj = (NewRowEventWrapper)is.readObject();
		System.out.println(obj);
		
		
		is = new ObjectInputStream(new FileInputStream(testFiles.get(1)));
		obj = (NewRowEventWrapper)is.readObject();
		System.out.println(obj);
		obj = (NewRowEventWrapper)is.readObject();
		System.out.println(obj);
	}

}
