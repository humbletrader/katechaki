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

/**
 * 
 * Grouping Column based on a single input column
 * 
 * @author dragos balan
 * @since 0.4
 */
public class DefaultGroupColumn extends AbstractGroupColumn {
	
	/**
	 * the index of the input column
	 */
	private int inputColumnIndex; 
	
	/**
	 * 
	 * @param builder
	 */
	private DefaultGroupColumn(Builder builder){
		super(	builder.header, 
				builder.groupLevel, 
				builder.valuesFormatter, 
				builder.hAlign, 
				builder.vAlign, 
				builder.showDuplicateValues, 
				builder.sortType); 
		this.inputColumnIndex = builder.columnIndex; 
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.config.GroupColumn#getValue(net.sf.reportengine.core.algorithm.NewRowEvent)
	 */
	public Object getValue(NewRowEvent newRowEvent) {
		return newRowEvent.getInputDataRow().get(inputColumnIndex);
	}
	
	/**
	 * 
	 * @return
	 */
	public int getInputColumnIndex() {
		return inputColumnIndex;
	}
	
	/**
	 * returns a string interpretation of this object 
	 */
	public String toString(){
		return new StringBuilder("DefaultGroupColumn[")
			.append("inputIdx=").append(inputColumnIndex)
			.append(", groupLevel=").append(getGroupingLevel())
			.append(", header=").append(getHeader())
			.append(", hAlign=").append(getHorizAlign())
			.append(", vAlign=").append(getVertAlign())
			.append(", formatter=").append(getValuesFormatter())
			.append(", sortType=").append(getSortType())
			.append("]")
		.toString(); 
	}
	
	/**
	 * a fluent builder for default group columns
	 * 
	 * @author dragos balan
	 */
	public static class Builder{
		private int columnIndex; 
		private String header; 
		private HorizAlign hAlign = HorizAlign.CENTER; 
		private VertAlign  vAlign = VertAlign.MIDDLE; 
		private String valuesFormatter = null; 
		private SortType sortType = SortType.NONE; 
		
		private int groupLevel = 0; 
		private boolean showDuplicateValues = false; 
		
		public Builder(int columnIndex){
			this.columnIndex = columnIndex; 
			this.groupLevel = columnIndex; 
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
		
		public Builder valuesFormatter(String formatter){
			this.valuesFormatter = formatter; 
			return this; 
		}
		
		public Builder sortAsc(){
			this.sortType = SortType.ASC; 
			return this;
		}
		
		public Builder sortDesc(){
			this.sortType = SortType.DESC; 
			return this; 
		}
		
		public Builder level(int groupLevel){
			this.groupLevel = groupLevel; 
			return this; 
		}
		
		public Builder showDuplicateValues(){
			this.showDuplicateValues = true; 
			return this; 
		}
		
		public DefaultGroupColumn build(){
			return new DefaultGroupColumn(this); 
		}
	}
}
