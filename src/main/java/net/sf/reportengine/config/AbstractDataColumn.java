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

import net.sf.reportengine.core.calc.GroupCalculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract implementation for a data column. 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3 
 * @see DataColumn
 * @see DefaultDataColumn
 */
/**
 * @author dragos balan
 *
 */
public abstract class AbstractDataColumn implements DataColumn {
	
	/**
	 *  the column header
	 */
	private String header; 
	
	/**
	 * the formatter for the values of this column
	 */
	private String valuesFormatter; 
	
	/**
	 * the formatter for the totals in this column
	 */
	private String totalFormatter; 
	
	/**
	 * the calculator of this column
	 */
	private GroupCalculator calculator; 
	
	/**
	 * the horizontal alignment
	 */
	private HorizAlign horizAlign; 
	
	/**
	 * the vertical align
	 */
	private VertAlign vertAlign; 
	
	/**
	 * the sorting level 
	 */
	private int sortLevel = NO_SORTING;
	
	/**
	 * sort type (default ascending)
	 */
	private SortType sortType = SortType.ASC; 
	
	/**
	 * 
	 * @param header
	 */
	public AbstractDataColumn(String header){
		this(header, null); 
	}
	
	/**
	 * 
	 * @param header
	 * @param calculator
	 */
	public AbstractDataColumn(String header, GroupCalculator calculator){
		this(header, calculator, null); 
	}
	
	/**
	 * 
	 * @param header
	 * @param calculator
	 * @param valuesFormatter
	 */
	public AbstractDataColumn(	String header, 
								GroupCalculator calculator, 
								String valuesFormatter){
		this(header, calculator, valuesFormatter, HorizAlign.CENTER);
	}
	
	
	/**
	 * 
	 * @param header
	 * @param calculator
	 * @param valuesFormatter
	 * @param horizAlign
	 */
	public AbstractDataColumn(	String header, 
								GroupCalculator calculator, 
								String valuesFormatter, 
								HorizAlign horizAlign){
		this(header, calculator, valuesFormatter, horizAlign, NO_SORTING); 
	}
	
	
	/**
	 * 
	 * @param header
	 * @param calculator
	 * @param valuesFormatter
	 * @param horizAlign
	 * @param sortLevel
	 */
	public AbstractDataColumn(	String header, 
								GroupCalculator calculator, 
								String valuesFormatter, 
								HorizAlign horizAlign, 
								int sortLevel){
		this(header, calculator, valuesFormatter, horizAlign, VertAlign.MIDDLE, sortLevel);  
	}
	
	
	/**
	 * 
	 * @param header		the header of this column
	 * @param calculator	the calculator
	 * @param valuesFormatter		the valuesFormatter 
	 * @param horizAlign	the horizontal alignment
	 * @param vertAlign		the vertical alignment
	 * @param sortLevel	the sorting level
	 */
	public AbstractDataColumn(	String header, 
								GroupCalculator calculator, 
								String valuesFormatter, 
								HorizAlign horizAlign,
								VertAlign vertAlign, 
								int sortLevel){
		this(header, calculator, valuesFormatter, horizAlign, vertAlign, sortLevel, SortType.ASC);  
	}
	
	
	/**
	 * 
	 * @param header		the header of this column
	 * @param calculator	the calculator
	 * @param valuesFormatter		the valuesFormatter 
	 * @param horizAlign	the horizontal alignment
	 * @param vertAlign		the vertical alignment
	 * @param sortLevel	the sorting level 
	 * @param sortType		the sorting type (asc, desc)
	 */
	public AbstractDataColumn(	String header, 
								GroupCalculator calculator, 
								String valuesFormatter,
								HorizAlign horizAlign,
								VertAlign vertAlign, 
								int sortLevel, 
								SortType sortType){
		this(header, calculator, valuesFormatter, null, horizAlign, vertAlign, sortLevel, sortType); 
	}
	
	
	/**
	 * 
	 * @param header		the header of this column
	 * @param calculator	the calculator
	 * @param valuesFormatter		the valuesFormatter 
	 * @param totalsFormatter		the valuesFormatter 
	 * @param horizAlign	the horizontal alignment
	 * @param vertAlign		the vertical alignment
	 * @param sortLevel	the sorting level 
	 * @param sortType		the sorting type (asc, desc)
	 */
	public AbstractDataColumn(	String header, 
								GroupCalculator calculator, 
								String valuesFormatter,
								String totalsFormatter, 
								HorizAlign horizAlign,
								VertAlign vertAlign, 
								int sortLevel, 
								SortType sortType){
		this.header = header; 
		this.valuesFormatter = valuesFormatter; 
		this.totalFormatter = totalsFormatter;
		this.calculator = calculator; 
		this.horizAlign = horizAlign; 
		this.vertAlign = vertAlign;
		this.sortLevel = sortLevel;
		this.sortType = sortType;
	}
	
	/**
	 * getter for the column header
	 */
	public String getHeader() {
		return header;
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.config.DataColumn#getFormattedValue(java.lang.Object)
	 */
	public String getFormattedValue(Object value) {
		String result = "";
		if(value != null){
			if(valuesFormatter != null){
				//LOGGER.trace("formatting "+value+" as class "+value.getClass().getSimpleName());
				result = String.format(valuesFormatter, value);
			}else{
				result = value.toString();
			}
		}
		return result; 
	}
	
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.config.DataColumn#getFormattedTotal(java.lang.Object)
	 */
	public String getFormattedTotal(Object totalValue){
		String result = "";
		if(totalValue != null){
			if(totalFormatter != null){
				result = String.format(totalFormatter, totalValue); 
			}else{
				result = totalValue.toString(); 
			}
		}
		return result; 
	}
	
	
	
	/**
	 * getter for this column's calculator (if any)
	 */
	public GroupCalculator getCalculator() {
		return calculator;
	}
	
	
	/**
	 * getter for the formater of this column's values
	 * @return the formatter 
	 */
	public String getValuesFormatter(){
		return valuesFormatter; 
	}
	
	/**
	 * getter for formatter of the totals
	 * 
	 * @return the formatter of the total values
	 */
	public String getTotalsFormatter(){
		return totalFormatter; 
	}
	
	
	/**
	 * @return the horizontal alignment of this column
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
	 * getter for the sorting priority
	 * @return the sorting priority
	 */
	public int getSortLevel(){
		return sortLevel;
	}
	
	/**
	 * Asc or Desc
	 * @return the sort type for this column
	 */
	public SortType getSortType(){
		return sortType; 
	}
}
