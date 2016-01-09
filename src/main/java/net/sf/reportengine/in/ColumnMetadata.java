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

import net.sf.reportengine.config.HorizAlign;

/**
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.8
 */
public final class ColumnMetadata {
	
	/**
	 * the label of the column (also known as column header)
	 */
	private final String columnLabel; 
	
	/**
	 * the column identifier
	 */
	private final String columnId; 
	
	/**
	 * the horizontal alignment
	 */
	private final HorizAlign horizontalAlign; 
	
	/**
	 * WARNING: this class is using the upper case of column Id
	 * 
	 * @param columnId
	 */
	public ColumnMetadata(String columnId){
		this(columnId, null); 
	}
	
	/**
	 * WARNING: this class is using the upper case of column Id
	 * 
	 * @param columnId
	 * @param columnLabel
	 */
	public ColumnMetadata(String columnId, String columnLabel){
		this(columnId, columnLabel, null); 
	}
	
	/**
	 * 
	 * WARNING: this class is using the upper case of column Id
	 * 
	 * @param columnId
	 * @param columnLabel
	 * @param horizAlign
	 */
	public ColumnMetadata(	String columnId, 
							String columnLabel, 
							HorizAlign horizAlign){
		this.columnId = columnId.toUpperCase();
		this.columnLabel = columnLabel; 
		this.horizontalAlign = horizAlign; 
	}
	
	/**
	 * @return the columnLabel
	 */
	public String getColumnLabel() {
		return columnLabel;
	}

	/**
	 * @return the columnId
	 */
	public String getColumnId() {
		return columnId;
	}

	/**
	 * @return the horizontalAlign
	 */
	public HorizAlign getHorizontalAlign() {
		return horizontalAlign;
	}
	
	/**
	 * 
	 */
	public String toString(){
		StringBuilder result = new StringBuilder("ColumnMetadata[");
		result.append("columnId=").append(columnId);
		result.append(", columnLabel=").append(columnLabel);
		result.append(", hAlign=").append(horizontalAlign);
		result.append("]"); 
		return result.toString(); 
	}
}
