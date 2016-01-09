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
package net.sf.reportengine.in;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import net.sf.reportengine.util.ReportIoUtils;

public class TestTextReportInput extends TestCase {
    
    
    private String TEST_FILE = "2x3x1.txt";    
    
    private final Object[][] EXPECTED_RESULT = 
        new Object[][]{
           {"Europe","North","Sweden","Males","under 30","1"},
           {"Europe","North","Sweden","Males","between 31 and 60","2"},
           {"Europe","North","Sweden","Males","over 61","3"},
           {"Europe","North","Sweden","Females","under 30","4"},
           {"Europe","North","Sweden","Females","between 31 and 60","5"},
           {"Europe","North","Sweden","Females","over 61","6"},
           {"Europe","North","Finland","Males","under 30","7"},
           {"Europe","North","Finland","Males","between 31 and 60","8"},
           {"Europe","North","Finland","Males","over 61","9"},
           {"Europe","North","Finland","Females","under 30","10"},
           {"Europe","North","Finland","Females","between 31 and 60","11"},
           {"Europe","North","Finland","Females","over 61","12"},
           {"Europe","South","Italy","Males","under 30","13"},
           {"Europe","South","Italy","Males","between 31 and 60","14"},
           {"Europe","South","Italy","Males","over 61","15"},
           {"Europe","South","Italy","Females","under 30","16"},
           {"Europe","South","Italy","Females","under 30","17"},
           {"Europe","South","Italy","Females","between 31 and 60","18"},
           {"Europe","South","Italy","Females","over 61","19"},
           {"Asia","South","Thailand","Males","under 30","20"},
           {"Asia","South","Thailand","Males","between 31 and 60","21"},
           {"Asia","South","Thailand","Males","over 61","22"},
           {"Asia","South","Thailand","Females","under 30","23"},
           {"Asia","South","Thailand","Females","between 31 and 60","24"},
           {"Asia","South","Thailand","Females","over 61","25"},
    };
    
    private TextTableInput classUnderTest;

    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * Test method for 'net.sf.reportengine.in.FileDataProvider.nextRow()'
     */
    public void testNextRow(){
        try {
            classUnderTest = new TextTableInput();
            classUnderTest.setInputReader(ReportIoUtils.createReaderFromClassPath(TEST_FILE));
            classUnderTest.setSeparator(",");
            
            int index = 0;
            List<Object> data;
            classUnderTest.open();
            while (classUnderTest.hasNext()) {
                data = classUnderTest.next();
                boolean arraysAreEqual = Arrays.equals(EXPECTED_RESULT[index++], data.toArray(new Object[]{}));
                assertTrue(arraysAreEqual);
            }

            assertEquals(index, 25);
        } catch (Throwable exc) {
            exc.printStackTrace();
            fail(exc.getMessage());
        }finally{
        	classUnderTest.close(); 
        }
        
        
    }

    public void testHasMoreRows() {
        int rowsCount = 0;
        try{
            classUnderTest = new TextTableInput(ReportIoUtils.createReaderFromClassPath(TEST_FILE));
            classUnderTest.open();
            while(classUnderTest.hasNext()){
                classUnderTest.next();
                rowsCount ++;
            }
        }catch(TableInputException ioEx){
            ioEx.printStackTrace();
            fail(ioEx.getMessage());
        }catch(Exception ex){
            ex.printStackTrace();
            fail(ex.getMessage());
        }finally{
        	classUnderTest.close(); 
        }
        
        assertEquals(rowsCount, 25);
    }

    /*
     * Test method for 'net.sf.reportengine.in.FileDataProvider.getColumnsCount()'
     */
    public void testGetColumnsCount1() {
        int result = -1;
        try {
            classUnderTest = new TextTableInput(ReportIoUtils.createInputStreamFromClassPath((TEST_FILE)));
            classUnderTest.open();
            result = classUnderTest.getColumnMetadata().size();
        } catch (Throwable e) {
            e.printStackTrace();
            fail(e.getMessage()); 
        } finally{
        	classUnderTest.close(); 
        }
        assertEquals(result, 6);
    }
    
    /*
     * Test method for 'net.sf.reportengine.in.FileDataProvider.getColumnsCount()'
     */
    public void testGetColumnsCount2() {
        
        try {
            classUnderTest = new TextTableInput(ReportIoUtils.createInputStreamFromClassPath(TEST_FILE));
            classUnderTest.open();
            classUnderTest.next();
            classUnderTest.next();
            classUnderTest.next();
            classUnderTest.next();
            classUnderTest.next();
            classUnderTest.next();
            classUnderTest.next();
            
            assertEquals(classUnderTest.getColumnMetadata().size(), 6);
            
        } catch (Throwable e) {
            e.printStackTrace();
            fail(e.getMessage());
        }finally{
        	classUnderTest.close(); 
        }
    }
    
   
    
    
    public void testFirstRow(){
        try {
            classUnderTest = new TextTableInput();
            classUnderTest.setInputReader(ReportIoUtils.createReaderFromClassPath(TEST_FILE));
            
            classUnderTest.open();
            //classUnderTest.first();
            List<Object> firstRow = classUnderTest.next();
            assertTrue(firstRow.equals(Arrays.asList(EXPECTED_RESULT[0])));
            
            assertTrue(classUnderTest.hasNext());
            List<Object> secondRow = classUnderTest.next();
            assertTrue(secondRow.equals(Arrays.asList(EXPECTED_RESULT[1])));
            
            
            assertTrue(classUnderTest.hasNext());
            List<Object> thirdRow = classUnderTest.next();
            assertTrue(thirdRow.equals(Arrays.asList(EXPECTED_RESULT[2])));            
            
        } catch (TableInputException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }finally{
        	classUnderTest.close(); 
        }
    }
    
    public void testExceptionNonExistentFile(){
        try {
            classUnderTest = new TextTableInput("inexistent.txt");
            classUnderTest.open();
            fail("an exception should have been thrown by the method above");
        } catch (TableInputException e) {
        	assertEquals(e.getCause().getClass(), FileNotFoundException.class);
        }                
    }
    
    
    public void testExceptionNoWriterSet(){
        try {
            classUnderTest = new TextTableInput();
            classUnderTest.open();
            fail("an exception should have been thrown by the method above");
        } catch (TableInputException e) {
        	assertEquals(TextTableInput.NO_WRITER_SET_ERROR_MESSAGE, e.getMessage());
        }                
    }
    
    /**
     * testing hasMoreRows method for an empty file 
     */
    public void testHasMoreRowsOnEmptyFile(){
        boolean hasMoreRows = true;
        try {
            classUnderTest = new TextTableInput(ReportIoUtils.createInputStreamFromClassPath("empty.txt"));
            classUnderTest.open();
            hasMoreRows = classUnderTest.hasNext();
        } catch (TableInputException e) {
            e.printStackTrace();
            fail(e.getMessage()); 
        }finally{
        	classUnderTest.close(); 
        }
        
        assertFalse(hasMoreRows);
        
    }
    
    /**
     * testing nextRows method for an empty file 
     */
    public void testNextRowOnEmptyFile(){
        List<Object> nextRow = null;
        try {
            classUnderTest = new TextTableInput(ReportIoUtils.createInputStreamFromClassPath("empty.txt"));
            classUnderTest.open();
            nextRow = classUnderTest.next();
        } catch (TableInputException e) {
            e.printStackTrace();
            fail(e.getMessage()); 
        }finally{
        	classUnderTest.close(); 
        }
        
        assertNull(nextRow);
        
    }
    
    /**
     * testing first method for an empty file 
     */
    public void testFirstOnEmptyFile(){
        List<Object> nextRow = null;
        boolean hasMoreRows = true;
        try {
            classUnderTest = new TextTableInput(ReportIoUtils.createInputStreamFromClassPath("empty.txt"));
            classUnderTest.open();
            //classUnderTest.first();
            
            nextRow = classUnderTest.next();
            assertNull(nextRow);
            
            hasMoreRows = classUnderTest.hasNext();
            assertFalse(hasMoreRows);
            
        } catch (TableInputException e) {
            e.printStackTrace();
            fail("An error occured "+e.getMessage()); 
        }finally{
        	classUnderTest.close(); 
        }
    }
    
    
    public void testUtf8Characters(){
    	int linesCount = 0;
    	classUnderTest = new TextTableInput(ReportIoUtils.createInputStreamFromClassPath("Utf8Input.txt"), ",", "UTF-8");
    	classUnderTest.open(); 
    	List<Object> row = null; 
    	while(classUnderTest.hasNext()){
    		row  = classUnderTest.next();
    		assertNotNull(row);
    		assertEquals(4, row.size());
    		linesCount++;
    		
    		if(linesCount == 1){
    			assertEquals("Действията", row.get(0));
    		}else{
    			if(linesCount == 2){
    				assertEquals("и канализация са от", row.get(1)); 
    			}else{
    				if(linesCount == 6){
    					assertEquals("устойчиви резултати.", row.get(3));
    				}
    			}
    		}
    	}
    	classUnderTest.close(); 
    	assertEquals(6,linesCount);
    }
    
    public void testSkipFirstLines(){
    	int rowsCount = 0;
    	InputStream inputStream = ReportIoUtils.createInputStreamFromClassPath("2x3x1WithColumnHeaders.txt");
    	classUnderTest = new TextTableInput(inputStream); 
    	classUnderTest.setFirstLineHeader(true);
    	classUnderTest.open(); 
    	
    	assertTrue(classUnderTest.hasNext()); 
    	
    	while(classUnderTest.hasNext()){
             List<Object> row = classUnderTest.next();
             assertNotNull(row); 
             assertEquals(6, row.size()); 
             
             rowsCount++;
        }
    	assertEquals(25, rowsCount);
    	assertNotNull(classUnderTest.getColumnMetadata()); 
    	assertEquals(6, classUnderTest.getColumnMetadata().size()); 
    	
    	classUnderTest.close(); 
    }
}
