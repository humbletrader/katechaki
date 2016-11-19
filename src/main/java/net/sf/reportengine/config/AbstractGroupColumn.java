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
package net.sf.reportengine.config;



/**
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public abstract class AbstractGroupColumn implements GroupColumn {
	
	/**
	 * the grouping priority of this column
	 */
	private int groupingLevel; 
	
	/**
	 * the header of the column
	 */
	private String header; 
	
	/**
	 * the formatter of this column's values
	 */
	private String valuesFormatter; 
	
	/**
	 * the horizontal alignment
	 */
	private HorizAlign horizAlign; 
	
	/**
	 * the vertical alignment
	 */
	private VertAlign vertAlign;
	
	/**
	 * whether or not this column with show duplicates
	 */
	private boolean showDuplicates; 
	
	/**
	 * sort type (default none)
	 */
	private SortType sortType = SortType.NONE; 
	
	/**
	 * 
	 * @param header
	 * @param groupingLevel
	 * @param valuesFormatter
	 * @param horizAlign
	 * @param showDuplicates
	 */
	public AbstractGroupColumn(	String header, 
								int groupingLevel, 
								String valuesFormatter, 
								HorizAlign horizAlign, 
								boolean showDuplicates){
		this(header, groupingLevel, valuesFormatter, horizAlign, VertAlign.MIDDLE, showDuplicates); 
	}
	
	/**
	 * 
	 * @param header
	 * @param groupingLevel
	 * @param valuesFormatter
	 * @param horizAlign
	 * @param vertAlign
	 * @param showDuplicates
	 */
	public AbstractGroupColumn(	String header, 
								int groupingLevel, 
								String valuesFormatter, 
								HorizAlign horizAlign, 
								VertAlign vertAlign, 
								boolean showDuplicates){
		this(header, groupingLevel, valuesFormatter, horizAlign, vertAlign, showDuplicates, SortType.NONE/*no sorting*/); 
	}
	
	
	/**
	 * 
	 * @param header
	 * @param groupingLevel
	 * @param valuesFormatter
	 * @param horizAlign
	 * @param vertAlign
	 * @param showDuplicates
	 * @param sortType
	 */
	public AbstractGroupColumn(	String header, 
								int groupingLevel, 
								String valuesFormatter, 
								HorizAlign horizAlign, 
								VertAlign vertAlign, 
								boolean showDuplicates, 
								SortType sortType){
		this.header = header;
		this.groupingLevel = groupingLevel; 
		this.valuesFormatter = valuesFormatter;
		this.horizAlign = horizAlign;
		this.vertAlign = vertAlign;
		this.showDuplicates = showDuplicates;
		this.sortType = sortType; 
	}
	
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.config.GroupColumn#getGroupingLevel()
	 */
	public int getGroupingLevel() {
		return groupingLevel;
	}
	
	/**
	 * 
	 */
	public String getHeader() {
		return header;
	}
	
	/**
	 * 
	 */
	public String getFormattedValue(Object value) {
		String result = "";
		if(value != null){
			if(valuesFormatter != null){
				result = String.format(valuesFormatter, value);
			}else{
				result = value.toString();
			}
		}
		return result; 
	}
	
	/**
	 * 
	 * @return the formatter of this column's values
	 */
	public String getValuesFormatter() {
		return valuesFormatter;
	}
	
	/**
	 * @return the horizontal alignment
	 */
	public HorizAlign getHorizAlign() {
		return horizAlign;
	}

	/**
	 * @return the vertical alignment
	 */
	public VertAlign getVertAlign() {
		return vertAlign;
	}

	/**
	 * 
	 */
	public boolean showDuplicates(){
		return showDuplicates; 
	}
	
	/**
	 * 
	 */
	public SortType getSortType(){
		return sortType; 
	}
}