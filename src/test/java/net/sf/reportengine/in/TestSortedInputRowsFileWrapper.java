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

import java.io.ObjectInputStream;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.NewRowEventWrapper;
import net.sf.reportengine.core.steps.TestExternalSortStep;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.ReportIoUtils;

import org.junit.Ignore;
import org.junit.Test;

/**
 * 
 * This test uses the output from {@link TestExternalSortStep}
 * @author dragos
 *
 */
public class TestSortedInputRowsFileWrapper {

	/**
	 * Test method for {@link net.sf.reportengine.in.SortedInputRowsFileWrapper#isEmpty()}.
	 */
	@Test
	public void testIsEmpty() {
		SortedInputRowsFileWrapper classUnderTest = new SortedInputRowsFileWrapper(ReportIoUtils.createInputStreamFromClassPath("ExternalSortedAndSerializedFile1.tmp")); 
		
		assertFalse(classUnderTest.isEmpty());
		
		assertNotNull(classUnderTest.poll()); 
		assertFalse(classUnderTest.isEmpty());
		
		assertNotNull(classUnderTest.poll()); 
		assertFalse(classUnderTest.isEmpty());
		
		assertNotNull(classUnderTest.poll()); 
		assertFalse(classUnderTest.isEmpty());
		
		assertNotNull(classUnderTest.poll());
		assertTrue(classUnderTest.isEmpty()); 
		
		assertTrue(classUnderTest.isEmpty()); 
		assertTrue(classUnderTest.isEmpty()); 
		assertTrue(classUnderTest.isEmpty()); 
		
		classUnderTest.close();
	}

	/**
	 * Test method for {@link net.sf.reportengine.in.SortedInputRowsFileWrapper#close()}.
	 */
	@Test
	public void testClose() {
		SortedInputRowsFileWrapper classUnderTest = new SortedInputRowsFileWrapper(ReportIoUtils.createInputStreamFromClassPath("ExternalSortedAndSerializedFile1.tmp")); 
		
		assertNotNull(classUnderTest.poll()); 
		assertNotNull(classUnderTest.poll()); 
		
		//closing while still elements are in the queue
		classUnderTest.close();
	}

	/**
	 * Test method for {@link net.sf.reportengine.in.SortedInputRowsFileWrapper#peek()}.
	 */
	@Test
	public void testPeek() {
		SortedInputRowsFileWrapper classUnderTest = new SortedInputRowsFileWrapper(ReportIoUtils.createInputStreamFromClassPath("ExternalSortedAndSerializedFile1.tmp")); 
		
		NewRowEvent newRow = classUnderTest.peek();
		assertNotNull(newRow); 
		assertTrue(Scenario1.ROW_OF_DATA_4.equals(newRow.getInputDataRow()));
		
		classUnderTest.poll(); 
		newRow = classUnderTest.peek(); 
		assertTrue(Scenario1.ROW_OF_DATA_3.equals(newRow.getInputDataRow()));
		
		classUnderTest.poll(); 
		newRow = classUnderTest.peek(); 
		assertTrue(Scenario1.ROW_OF_DATA_1.equals(newRow.getInputDataRow()));
		
		classUnderTest.poll(); 
		newRow = classUnderTest.peek(); 
		assertTrue(Scenario1.ROW_OF_DATA_2.equals(newRow.getInputDataRow()));
	}

	/**
	 * Test method for {@link net.sf.reportengine.in.SortedInputRowsFileWrapper#poll()}.
	 */
	@Test
	public void testPoll1() {
		SortedInputRowsFileWrapper classUnderTest = new SortedInputRowsFileWrapper(ReportIoUtils.createInputStreamFromClassPath("ExternalSortedAndSerializedFile1.tmp")); 
		
		assertNotNull(classUnderTest.poll()); 
		assertNotNull(classUnderTest.poll());
		assertNotNull(classUnderTest.poll());
		assertNotNull(classUnderTest.poll()); 
		
		assertTrue(classUnderTest.poll() == null); 
		assertTrue(classUnderTest.poll() == null); 
		assertTrue(classUnderTest.poll() == null); 
		assertTrue(classUnderTest.poll() == null); 
		
		classUnderTest.close(); 
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.in.SortedInputRowsFileWrapper#poll()}.
	 */
	@Test
	public void testPoll2() {
		SortedInputRowsFileWrapper classUnderTest = new SortedInputRowsFileWrapper(ReportIoUtils.createInputStreamFromClassPath("ExternalSortedAndSerializedFile1.tmp")); 
		
		assertTrue(Scenario1.ROW_OF_DATA_4.equals(classUnderTest.poll().getInputDataRow()));
		assertTrue(Scenario1.ROW_OF_DATA_3.equals(classUnderTest.poll().getInputDataRow()));
		assertTrue(Scenario1.ROW_OF_DATA_1.equals(classUnderTest.poll().getInputDataRow()));
		assertTrue(Scenario1.ROW_OF_DATA_2.equals(classUnderTest.poll().getInputDataRow()));
		
		classUnderTest.close(); 
	}
	
	/**
	 * show the contents of the test file
	 * @throws Exception
	 */
	@Ignore
	public void caca() throws Exception{
		ObjectInputStream is = new ObjectInputStream(ReportIoUtils.createInputStreamFromClassPath("ExternalSortedAndSerializedFile1.tmp"));
		NewRowEventWrapper obj = (NewRowEventWrapper)is.readObject();
		System.out.println(obj);
		obj = (NewRowEventWrapper)is.readObject();
		System.out.println(obj);
		obj = (NewRowEventWrapper)is.readObject();
		System.out.println(obj);
		obj = (NewRowEventWrapper)is.readObject();
		System.out.println(obj);
	}

}
