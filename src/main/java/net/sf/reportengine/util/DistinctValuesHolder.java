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
package net.sf.reportengine.util;

import java.util.List;

/**
 * @author Administrator
 *
 */
public interface DistinctValuesHolder {
	
	/**
	 * adds a new value in the list of distinct values
	 * 
	 * @param headerRowId
	 * @param value
	 * @return the index of the new value into the array of distinct values
	 */
	public int addValueIfNotExist(int headerGroupingLevel, Object value);
	
	/**
	 * returns the index of the specified value in the array of distinct values for the given level
	 * 
	 * @param headerGroupingLevel
	 * @param value
	 * @return
	 */
	public int getIndexFor(int headerGroupingLevel, Object value);
	
	/**
	 * 
	 * @param level
	 * @return
	 */
	public List<Object> getDistinctValuesForLevel(int level);
	
	
	/**
	 * 
	 * @param level
	 * @return
	 */
	public int getDistinctValuesCountForLevel(int level);
	
	
	/**
	 * 
	 * @return
	 */
	public int getRowsCount();
}
