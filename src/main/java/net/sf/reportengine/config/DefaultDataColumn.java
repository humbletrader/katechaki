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

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.GroupCalculator;

/**
 * <p>This is the basic implementation for a data column.</p> 
 * <p>
 * The functionality is based on an inputColumnIndex, so this column takes 
 * all its values from an input column (inputColumnIndex). 
 * </p>
 * 
 * Example: <br/>
 * For the input:
 * <table>
 * 		<tr><td>a1</td><td>b1</td><td>c1</td></tr>
 * 		<tr><td>a2</td><td>b2</td><td>c2</td></tr>
 * 		<tr><td>a3</td><td>b3</td><td>c3</td></tr>
 * </table>
 * 
 * I. a default column having inputColumnIndex=0 will display
 * <table>
 * 	<tr><td>a1</td></tr>
 * 	<tr><td>a2</td></tr>
 * 	<tr><td>a3</td></tr>
 * </table>
 * 
 * II. for inputColumnIndex=2 this column will display
 * <table>
 * 	<tr><td>c1</td></tr>
 * 	<tr><td>c2</td></tr>
 * 	<tr><td>c3</td></tr>
 * </table>
 * 
 * Please keep in mind that inputColumnIndex is a zero based index.<br/>
 * The other attributes of this column are : 
 *  1. header
 *  2. valuesFormatter
 *  3. horizontal alignment
 *  4. calculator
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class DefaultDataColumn extends AbstractDataColumn {
	
	
	/**
	 * zero based index of the input column
	 */
	private int inputColumnIndex; 
	
	
	/**
	 * default constructor. 
	 * 
	 * Assumes 
	 * 	inputColumn=0
	 * 	no calculator
	 *  header=Column 0 
	 *  no valuesFormatter
	 */
	public DefaultDataColumn(){
		this(0); 
	}
	
	/**
	 * 
	 * @param inputColumnIndex
	 */
	public DefaultDataColumn(int inputColumnIndex){
		this("Column "+inputColumnIndex, inputColumnIndex);
	}
	
	/**
	 * 
	 * @param header
	 * @param inputColumnIndex
	 */
	public DefaultDataColumn(String header, int inputColumnIndex){
		this(header, inputColumnIndex, null);
	}
	
	/**
	 * 
	 * @param header
	 * @param inputColumnIndex
	 * @param calculatorsFactory
	 */
	public DefaultDataColumn(String header, int inputColumnIndex, GroupCalculator calculator){
		this(header, inputColumnIndex, calculator, null);
	}
	
	/**
	 * 
	 * @param header
	 * @param inputColumnIndex
	 * @param calculator
	 * @param valuesFormatter
	 */
	public DefaultDataColumn(	String header,
								int inputColumnIndex, 
								GroupCalculator calculator, 
								String formatter){
		this(header, inputColumnIndex, calculator, formatter, HorizAlign.CENTER);
	}
	
	
	/**
	 * 
	 * @param header
	 * @param inputColumnIndex
	 * @param calculator
	 * @param valuesFormatter
	 * @param horizAlign	horizontal alignment 
	 */
	public DefaultDataColumn(	String header,
								int inputColumnIndex, 
								GroupCalculator calculator, 
								String formatter, 
								HorizAlign horizAlign){
		this(header, inputColumnIndex, calculator, formatter, horizAlign, NO_SORTING); 
	}
	
	/**
	 * 
	 * @param header
	 * @param inputColumnIndex
	 * @param calculator
	 * @param valuesFormatter
	 * @param horizAlign	horizontal alignment
	 * @param sortLevel 
	 */
	public DefaultDataColumn(	String header,
								int inputColumnIndex, 
								GroupCalculator calculator, 
								String formatter, 
								HorizAlign horizAlign, 
								int sortLevel){
		
		super(header, calculator, formatter, horizAlign, sortLevel);
		setInputColumnIndex(inputColumnIndex);
	}
	
	
	/**
	 * 
	 * @param header
	 * @param inputColumnIndex
	 * @param calculator
	 * @param valuesFormatter
	 * @param horizAlign	the horizontal alignment
	 * @param vertAlign 	the vertical alignment
	 * @param sortLevel 
	 * @param sortType
	 */
	public DefaultDataColumn(	String header,
								int inputColumnIndex, 
								GroupCalculator calculator, 
								String formatter, 
								HorizAlign horizAlign, 
								VertAlign vertAlign, 
								int sortLevel, 
								SortType sortType){
		
		super(header, calculator, formatter, horizAlign, vertAlign, sortLevel, sortType);
		setInputColumnIndex(inputColumnIndex);
	}
	
	/**
	 * 
	 * @param builder
	 */
	private DefaultDataColumn(Builder builder){
		super(	builder.header, 
				builder.calculator, 
				builder.valuesFormatter, 
				builder.totalsFormatter, 
				builder.hAlign, 
				builder.vAlign, 
				builder.sortLevel, 
				builder.sortType); 
		setInputColumnIndex(builder.columnIndex); 
	}
	
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.config.DataColumn#getValue(net.sf.reportengine.core.algorithm.NewRowEvent)
	 */
	public Object getValue(NewRowEvent newRowEvent) {
		return newRowEvent.getInputDataRow().get(inputColumnIndex);
	}

	/**
	 * returns the index of the original (input) column displayed on this column
	 * @return	the input column index
	 */
	public int getInputColumnIndex() {
		return inputColumnIndex;
	}

	/**
	 * sets the index of the original (input) column to be displayed on this column
	 * @param inputColumnIndex
	 */
	public void setInputColumnIndex(int inputColumnIndex) {
		this.inputColumnIndex = inputColumnIndex;
	}
	
	
	public String toString(){
		StringBuilder result = new StringBuilder("DefaultDataColumn[");
		result.append("inputIdx=").append(inputColumnIndex);
		result.append(", header=").append(getHeader());
		result.append(", hAlign=").append(getHorizAlign());
		result.append(", vAlign=").append(getVertAlign()); 
		result.append(", valuesFormatter=").append(getValuesFormatter());
		result.append(", calculator=").append(getCalculator());
		result.append(", sortlevel=").append(getSortLevel());
		result.append(", sortType=").append(getSortType()); 
		result.append("]");
		return result.toString(); 
	}
	
	public static class Builder{
		
		private int columnIndex; 
		private String header = ""; 
		private HorizAlign hAlign = HorizAlign.CENTER; 
		private VertAlign  vAlign = VertAlign.MIDDLE; 
		private String valuesFormatter = null;
		private String totalsFormatter = null; 
		private GroupCalculator calculator = null; 
		private int sortLevel = DataColumn.NO_SORTING; 
		private SortType sortType = SortType.NONE; 
		
		
		public Builder(int columnIndex){
			this.columnIndex = columnIndex; 
		}
		
		public Builder header(String header){
			this.header = header; 
			return this; 
		}
		
		public Builder horizAlign(HorizAlign hAlign){
			this.hAlign = hAlign; 
			return this; 
		}
		
		public Builder vertAlign(VertAlign vAlign){
			this.vAlign = vAlign; 
			return this; 
		}
		
		public Builder valuesFormatter(String format){
			this.valuesFormatter = format; 
			return this; 
		}
		
		public Builder useCalculator(GroupCalculator calc){
			this.calculator = calc; 
			return this; 
		}
		
		public Builder useCalculator(GroupCalculator calc, String totalsFormatter){
			this.calculator = calc; 
			this.totalsFormatter = totalsFormatter; 
			return this; 
		}
		
		public Builder sortAsc(int sortLevel){
			this.sortLevel = sortLevel; 
			this.sortType = SortType.ASC; 
			return this; 
		}
		
		public Builder sortAsc(){
			return this.sortAsc(0);
		}
		
		public Builder sortDesc(int sortLevel){
			this.sortLevel = sortLevel; 
			this.sortType = SortType.DESC; 
			return this;
		}
		
		public Builder sortDesc(){
			return sortDesc(0); 
		}
		
		public DefaultDataColumn build(){
			return new DefaultDataColumn(this); 
		}
	}
}
