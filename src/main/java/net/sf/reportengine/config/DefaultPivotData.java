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
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class DefaultPivotData extends AbstractPivotData {
	
	/**
	 * 
	 */
	private int inputColumnIndex; 
	
	
	/**
	 * 
	 */
	public DefaultPivotData(){
		this(0); 
	}
	
	/**
	 * 
	 * @param inputColumnIndex
	 */
	public DefaultPivotData(int inputColumnIndex){
		this(inputColumnIndex, null); 
	}
	
	/**
	 * 
	 * @param inputColumnIndex
	 * @param calcFactory
	 */
	public DefaultPivotData(int inputColumnIndex, GroupCalculator calc){
		this(inputColumnIndex, calc, null);
	}
	
	/**
	 * 
	 * @param inputColumIndex
	 * @param calcFactory
	 * @param formatter
	 */
	public DefaultPivotData(	int inputColumIndex, 
								GroupCalculator calc, 
								String valuesFormatter){
		this(inputColumIndex, calc, valuesFormatter, HorizAlign.CENTER);
	}
	
	/**
	 * 
	 * @param inputColumIndex
	 * @param calcFactory
	 * @param formatter
	 * @param horizAlign
	 */
	public DefaultPivotData(	int inputColumnIndex, 
								GroupCalculator calc, 
								String valuesFormatter, 
								HorizAlign horizAlign){
		this(inputColumnIndex, calc, valuesFormatter, horizAlign, VertAlign.MIDDLE);
	}
	
	
	/**
	 * 
	 * @param inputColumIndex
	 * @param calcFactory
	 * @param formatter
	 * @param horizAlign
	 * @param vertAlign
	 */
	public DefaultPivotData(	int inputColumIndex, 
								GroupCalculator calc, 
								String valuesFormatter, 
								HorizAlign horizAlign, 
								VertAlign vertAlign){
		super(calc, valuesFormatter, horizAlign, vertAlign);
		setInputColumnIndex(inputColumIndex);
	}	
	
	/**
	 * 
	 * @param builder
	 */
	private DefaultPivotData(Builder builder){
		super(	builder.calculator, 
				builder.valuesFormatter, 
				builder.totalsFormatter, 
				builder.hAlign, 
				builder.vAlign); 
		setInputColumnIndex(builder.columnIndex); 
	}
	
	public Object getValue(NewRowEvent newRowEvent) {
		return newRowEvent.getInputDataRow().get(inputColumnIndex);
	}

	public int getInputColumnIndex() {
		return inputColumnIndex;
	}

	public void setInputColumnIndex(int inputColumnIndex) {
		this.inputColumnIndex = inputColumnIndex;
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DefaultCrosstabData [inputColumnIndex=" + inputColumnIndex
				+ "]";
	}



	public static class Builder{
		private int columnIndex; 
		private HorizAlign hAlign = HorizAlign.CENTER; 
		private VertAlign  vAlign = VertAlign.MIDDLE; 
		private String  valuesFormatter = null;
		private String totalsFormatter = null;
		private GroupCalculator calculator = null; 
		
		public Builder(int columnIndex){
			this.columnIndex = columnIndex; 
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
		
		public Builder useCalculator(GroupCalculator calc){
			this.calculator = calc; 
			return this; 
		}
		
		public Builder useCalculator(GroupCalculator calc, String totalsFormatter){
			this.calculator = calc; 
			this.totalsFormatter = totalsFormatter; 
			return this; 
		}
		
		public DefaultPivotData build(){
			return new DefaultPivotData(this); 
		}

	}
}
