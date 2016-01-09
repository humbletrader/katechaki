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

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.config.SortType;
import net.sf.reportengine.config.VertAlign;
import net.sf.reportengine.core.calc.GroupCalculator;

/**
 * column preferences
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.8
 */
public class ColumnPreferences {
	
	/**
	 * 
	 */
	public static final int DEFAULT_SORT_LEVEL = Integer.MAX_VALUE; 
	
	/**
	 * the column header
	 */
	private String header = null; 
	
	/**
	 * group flag
	 */
	private boolean isGroup = false; 
	
	/**
	 * horizontal alignment
	 */
	private HorizAlign horizAlign = null;
	
	/**
	 * vertical alignment
	 */
	private VertAlign vertAlign = null ;
	
	/**
	 * the formatter of the column
	 */
	private String valuesFormatter = null; 
	
	/**
	 * the formatter for the totals
	 */
	private String totalsFormatter = null;
	
	/**
	 * the calculator
	 */
	private GroupCalculator calculator = null; 
	
	/**
	 * 
	 */
	private int sortLevel = DataColumn.NO_SORTING; 
	
	/**
	 * 
	 */
	private SortType sortType = null; 
	
	
	/**
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}
	/**
	 * @param header the header to set
	 */
	public ColumnPreferences header(String header) {
		this.header = header;
		return this; 
	}
	
	/**
	 * @return the isGroup
	 */
	public boolean isGroup() {
		return isGroup;
	}
	
	/**
	 * @param isGroup the isGroup to set
	 */
	public ColumnPreferences group() {
		this.isGroup = true;
		return this; 
	}
	/**
	 * @return the horizAlign
	 */
	public HorizAlign getHorizAlign() {
		return horizAlign;
	}
	
	/**
	 * 
	 * @return the vertical align
	 */
	public VertAlign getVertAlign(){
		return vertAlign; 
	}
	
	/**
	 * @param horizAlign the horizAlign to set
	 */
	public ColumnPreferences horizAlign(HorizAlign horizAlign) {
		this.horizAlign = horizAlign;
		return this; 
	}
	
	/**
	 * @param vertAlign the horizAlign to set
	 */
	public ColumnPreferences vertAlign(VertAlign vertAlign) {
		this.vertAlign = vertAlign;
		return this; 
	}
	
	/**
	 * @return the formatter
	 */
	public String getValuesFormatter() {
		return valuesFormatter;
	}
	
	/**
	 * @param formatter the formatter to set
	 */
	public ColumnPreferences valuesFormatter(String formatter) {
		this.valuesFormatter = formatter;
		return this; 
	}
	
	/**
	 * @return the formatter of the totals
	 */
	public String getTotalsFormatter() {
		return totalsFormatter;
	}
	
	/**
	 * @return the calculator
	 */
	public GroupCalculator getCalculator() {
		return calculator;
	}
	
	/**
	 * @param calculator the calculator to set
	 */
	public ColumnPreferences useCalculator(GroupCalculator calculator) {
		this.calculator = calculator;
		return this; 
	} 
	
	public ColumnPreferences useCalculator(GroupCalculator calculator, String totalsFormatter) {
		this.calculator = calculator;
		this.totalsFormatter = totalsFormatter; 
		return this; 
	} 
	
	public ColumnPreferences sortAsc(int sortLevel){
		this.sortLevel = sortLevel; 
		this.sortType = SortType.ASC; 
		return this; 
	}
	
	public ColumnPreferences sortAsc(){
		return this.sortAsc(DEFAULT_SORT_LEVEL); 
	}
	
	public ColumnPreferences sortDesc(int sortLevel){
		this.sortLevel = sortLevel;
		this.sortType = SortType.DESC; 
		return this; 
	}
	
	public ColumnPreferences sortDesc(){
		return this.sortDesc(DEFAULT_SORT_LEVEL); 
	}
	
	public int getSortLevel(){
		return sortLevel; 
	}
	
	public SortType getSortType(){
		return sortType; 
	}
	
	/**
	 * 
	 */
	public String toString(){
		StringBuilder result = new StringBuilder("ColumnPreferences[");
		result.append("header=").append(header); 
		result.append(", group=").append(isGroup); 
		result.append(", sortLevel=").append(sortLevel); 
		result.append("]");
		return result.toString(); 
	}
}