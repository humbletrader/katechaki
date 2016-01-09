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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class TestSqlInput  {
	
	private static Connection testConnection;
	
	@BeforeClass
	public static void oneTimeSetUp() throws Exception {
	    try {
   		 	Class.forName("org.hsqldb.jdbcDriver");
		
	         testConnection = DriverManager.getConnection("jdbc:hsqldb:mem:testdb","sa","");
	         testConnection.setAutoCommit(false);
	         
	         Statement createTable = testConnection.createStatement();
	         createTable.execute("CREATE TABLE testreport(Id INTEGER PRIMARY KEY, country VARCHAR(50),  region VARCHAR(50),  city VARCHAR(50),  sex VARCHAR(10),  religion VARCHAR(50),  value INTEGER)");
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
    public void testNonQuery() throws SQLException{
    	SqlTableInput tableInput = null; 
    	try{
    		tableInput = new SqlTableInput(
    				"jdbc:hsqldb:mem:testdb",
    				"org.hsqldb.jdbcDriver",
    				"sa",
    				"",
    				"INSERT INTO testreport VALUES(1,'USA','EAST','New York','Males','Catholic',1001)");
    		tableInput.open(); 
    		
    		fail("an error should have been thrown by the statement above"); 
    	}catch(TableInputException e){
    		assertTrue(e.getCause() instanceof java.sql.SQLException);
    		assertEquals("statement does not generate a result set", e.getCause().getMessage()); 
    	}finally{
    		tableInput.close(); 
    	}
    	
    	assertTrue(tableInput.hasAllResourcesClosed()); 
    }
    
    @Test
    public void testClosingResources() throws SQLException{
    	SqlTableInput tableInput = null; 
    	try{
    		tableInput = new SqlTableInput(
    				"jdbc:hsqldb:mem:testdb",
    				"org.hsqldb.jdbcDriver",
    				"sa",
    				"",
    				"select wrong_column from wrong_table");
    		tableInput.open(); 
    		fail("an error should have been thrown by the statement above"); 
    	}catch(TableInputException e){
    		assertTrue(e.getCause() instanceof java.sql.SQLException);
    		assertEquals("user lacks privilege or object not found: WRONG_TABLE", e.getCause().getMessage()); 
    	}finally{
    		tableInput.close(); 
    	}
    	
    	assertTrue(tableInput.hasAllResourcesClosed()); 
    }
    
    @Test
    public void testWrongDriverClass() throws SQLException {
    	SqlTableInput tableInput = null; 
    	try{
    		tableInput = new SqlTableInput(
    				"jdbc:hsqldb:mem:testdb",
    				"WRONG_CLASS",
    				"sa",
    				"",
    				"select id from testreport");
    		tableInput.open(); 
    		fail("an error should have been thrown by the statement above"); 
    	}catch(TableInputException e){
    		assertTrue(e.getCause() instanceof ClassNotFoundException);
    	}finally{
    		tableInput.close(); 
    	}
    	
    	assertTrue(tableInput.hasAllResourcesClosed()); 
    }
}
