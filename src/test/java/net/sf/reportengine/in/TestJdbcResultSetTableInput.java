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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestJdbcResultSetTableInput {
	
	private static Connection testConnection; 
	
	@BeforeClass
	public static void oneTimeSetUp() throws Exception {
		try {
   		 	Class.forName("org.hsqldb.jdbcDriver");
		
   		 	 testConnection = DriverManager.getConnection("jdbc:hsqldb:mem:testdb","sa","");
	         testConnection.setAutoCommit(false);
	         
	         Statement createTable = testConnection.createStatement();
	         createTable.execute("CREATE TABLE testreport(Id INTEGER PRIMARY KEY, country VARCHAR(50),  region VARCHAR(50),  city VARCHAR(50),  sex VARCHAR(10),  religion VARCHAR(50),  value INTEGER)");
	         
	         Statement insertStatement = testConnection.createStatement();
	         insertStatement.addBatch("INSERT INTO testreport VALUES(1,'USA','EAST','New York','Males','Catholic',1001)");
	         insertStatement.addBatch("INSERT INTO testreport VALUES(2,'USA','EAST','New York','Males','Orthodox',2001)");
	         insertStatement.addBatch("INSERT INTO testreport VALUES(3,'USA','EAST','New York','Females','Catholic',1002)");
	         insertStatement.addBatch("INSERT INTO testreport VALUES(4,'USA','EAST','New York','Females','Orthodox',2002)");
	         insertStatement.addBatch("INSERT INTO testreport VALUES(5,'USA','EAST','Chicago','Males','Orthodox',101)");
	         insertStatement.addBatch("INSERT INTO testreport VALUES(6,'USA','EAST','Chicago','Females','Muslim', 101)");
	         insertStatement.executeBatch();
	        
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void oneTimeTearDown() throws Exception {
		Statement createTable = testConnection.createStatement();
        createTable.execute("DROP TABLE testreport");
        
        testConnection.close(); 
	}
	

	@Test
	public void testNextRow() throws Exception{
		PreparedStatement stmt = testConnection.prepareStatement(
				"select id, country, region, city, sex, religion, value from testreport t order by id",   
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
		
		JdbcResultsetTableInput classUnderTest = new JdbcResultsetTableInput(stmt.executeQuery()); 
		Assert.assertNotNull(classUnderTest);
		
		classUnderTest.open(); 
		Assert.assertTrue(classUnderTest.hasNext()); 
		Assert.assertTrue(classUnderTest.hasNext()); 
		List<Object> row = classUnderTest.next(); 
		Assert.assertNotNull(row);
		Assert.assertEquals(7, row.size());
		
		Assert.assertTrue(classUnderTest.hasNext()); 
		Assert.assertTrue(classUnderTest.hasNext()); 
		Assert.assertTrue(classUnderTest.hasNext()); 
		Assert.assertTrue(classUnderTest.hasNext()); 
		
		row = classUnderTest.next(); 
		Assert.assertNotNull(row);
		Assert.assertEquals(7, row.size());
		
		Assert.assertTrue(classUnderTest.hasNext()); 
		Assert.assertTrue(classUnderTest.hasNext()); 
		
		row = classUnderTest.next(); 
		Assert.assertNotNull(row);
		Assert.assertEquals(7, row.size());
		
		Assert.assertTrue(classUnderTest.hasNext()); 
		Assert.assertTrue(classUnderTest.hasNext()); 
		
		row = classUnderTest.next(); 
		Assert.assertNotNull(row);
		Assert.assertEquals(7, row.size());
		
		Assert.assertTrue(classUnderTest.hasNext()); 
		Assert.assertTrue(classUnderTest.hasNext()); 
		
		row = classUnderTest.next(); 
		Assert.assertNotNull(row);
		Assert.assertEquals(7, row.size());
		
		Assert.assertTrue(classUnderTest.hasNext()); 
		Assert.assertTrue(classUnderTest.hasNext()); 
		
		row = classUnderTest.next(); 
		Assert.assertNotNull(row);
		Assert.assertEquals(7, row.size());
		
		Assert.assertFalse(classUnderTest.hasNext()); 
		Assert.assertFalse(classUnderTest.hasNext()); 
		
		classUnderTest.close(); 
		
	}
	
	
	@Test
	public void testReadMetadata() throws Exception{
		
		PreparedStatement stmt = testConnection.prepareStatement(
				"select id, country, region, city, sex, religion, value from testreport t order by id",   
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
		
		JdbcResultsetTableInput classUnderTest = new JdbcResultsetTableInput(stmt.executeQuery()); 
		Assert.assertNotNull(classUnderTest);
		classUnderTest.open();  
    	
    	Assert.assertNotNull(classUnderTest.getColumnMetadata()); 
    	Assert.assertEquals(7, classUnderTest.getColumnMetadata().size());
    	Assert.assertEquals("ID", classUnderTest.getColumnMetadata().get(0).getColumnLabel());
    	
    	classUnderTest.close(); 
    }
	
	
	@Test
	public void testClosingSqlResources() throws Exception{
		PreparedStatement stmt = testConnection.prepareStatement(
				"select id from testreport t where 0=1 ",   
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
		
		JdbcResultsetTableInput classUnderTest = null;
		try{
			classUnderTest = new JdbcResultsetTableInput(stmt.executeQuery()); 
			classUnderTest.open();  
			classUnderTest.next(); 
			
		}finally{
			Assert.assertNotNull(classUnderTest);
			classUnderTest.close(); 
		}
		
		Assert.assertTrue(classUnderTest.hasAllResourcesClosed());
    }
}
