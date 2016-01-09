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

import java.util.List;



/**
 * This is the top interface for report table input 
 * It works for both FlatTable and PivotTable.
 * 
 * @author dragos balan (dragos dot balan at gmail.com)
 * @since 0.2
 */
public interface TableInput {
	
	/**
     * prepares the input for reading. 
     * With this method you can open streams, database connections etc.
     */
    public void open();
    
    /**
     * use this method to release all resources used during the reading 
     * of the input lines
     */
    public void close();
	
    /**
     * <p>retrieves the next row of data </p>
     * This method should always return the same number of objects otherwise 
     * the framework will throw an IllegaArgumentException
     * @return an array of data objects
     */
    public List<Object> next();
    
    /**
     * returns true if there are rows left to read otherwise false
     * @return  true if the input has more rows to return
     */
    public boolean hasNext();
}
