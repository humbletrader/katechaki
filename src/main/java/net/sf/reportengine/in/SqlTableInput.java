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
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * <p>
 * Report Input implementation for database queries <br/>
 * There are two ways of setting up this data provider :<br/>
 *      Use case 1: providing database url, driver class , user and password<br/> 
 *      Use case 2: providing a connection  
 * </p> 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public class SqlTableInput extends AbstractTableInput implements ColumnMetadataHolder {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SqlTableInput.class);
	
   
    /**
     * database user
     */
    private final String dbUser;
    
    /**
     * database password
     */
    private final String dbPassword;
    
    /**
     * database connection string 
     */
    private final String dbConnString;
    
    /**
     * driver class
     */
    private final String dbDriverClass;
    
    /**
     * the sql statement
     */
    private final String sqlStatement; 
    
    /**
     * a helper table input on which all method request will be delegated
     */
    private SqlConnectionTableInput connTableInput; 
    
    
    /**
     * this is the preferred way to construct this provider
     * @param dbConnString      the database connection string 
     * @param driverClass       the driver fully qualified class name
     * @param dbUser            the database user
     * @param dbPassword        database password
     */
    public SqlTableInput(	String dbConnString, 
                      		String driverClass, 
                      		String dbUser, 
                      		String dbPassword, 
                      		String sqlStatement){
        this.dbConnString = dbConnString;
        this.dbDriverClass = driverClass;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        this.sqlStatement = sqlStatement; 
    }
    
    /**
     * opens the Input in order to start reading from it
     */
    public void open() {
    	super.open();
    	try {
    		Class.forName(dbDriverClass);
            Connection dbConnection = DriverManager.getConnection(dbConnString, dbUser, dbPassword);
            dbConnection.setAutoCommit(false);
            
    		//executing the sql
    		connTableInput = new SqlConnectionTableInput(dbConnection, sqlStatement, true); 
    		connTableInput.open();
    		
		} catch (SQLException e) {
			throw new TableInputException(e); 
		} catch(ClassNotFoundException e){
			throw new TableInputException("The driver class could not be found in classpath", e); 
		}
    }
    
    /**
     * Closes the input meaning : "the reading session it's done !"
     */
    public void close(){
    	try{
    		if(connTableInput != null){
    			connTableInput.close();
    		}
    	}finally{
    		super.close();
    	}
    }
    
    /**
     * returns the next row
     */
    public List<Object> next() {
       return connTableInput.next(); 
    }
    
    /**
     * returns true if there are more rows to read
     */
    public boolean hasNext() {
        return connTableInput.hasNext(); 
        
    }
    
    /**
     * @return Returns the dbConnString.
     */
    public String getDbConnString() {
        return dbConnString;
    }
    
    /**
     * @return Returns the dbDriverClass.
     */
    public String getDbDriverClass() {
        return dbDriverClass;
    }
    
    
    /**
     * @return Returns the dbPassword.
     */
    public String getDbPassword() {
        return dbPassword;
    }
    
    
    /**
     * @return Returns the dbUser.
     */
    public String getDbUser() {
        return dbUser;
    }
    
    /**
     * @return Returns the sqlStatement.
     */
    public String getSqlStatement() {
        return sqlStatement;
    }
    
    /**
     * 
     */
	public List<ColumnMetadata> getColumnMetadata() {
		return connTableInput.getColumnMetadata(); 
	}
	
	/**
	 * debug method which returns true if all sql resources have been released
	 * @return true if all resources have been closed
	 * @throws SQLException
	 */
	public boolean hasAllResourcesClosed() throws SQLException{
    	boolean result = true;
    	if(connTableInput != null){
    		result = result && connTableInput.hasAllResourcesClosed(); 
    	}
    	return result; 
    }
}
