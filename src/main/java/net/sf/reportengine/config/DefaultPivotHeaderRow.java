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

import java.text.Format;

import net.sf.reportengine.config.DefaultGroupColumn.Builder;
import net.sf.reportengine.core.algorithm.NewRowEvent;


/**
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class DefaultPivotHeaderRow extends AbstractPivotHeaderRow {

	/**
	 * 
	 */
	private int inputColumnIndex; 
	
	/**
	 * 
	 * @param inputColumnIndex
	 */
	public DefaultPivotHeaderRow(int inputColumnIndex){
		this(inputColumnIndex, null);
	}
	
	/**
	 * 
	 * @param inputColumnIndex
	 * @param formatter
	 */
	public DefaultPivotHeaderRow(int inputColumnIndex, 
									Format formatter){
		super(formatter); 
		setInputColumnIndex(inputColumnIndex);
	}
	
	/**
	 * 
	 * @param builder
	 */
	private DefaultPivotHeaderRow(Builder builder){
		super(builder.formatter); 
		setInputColumnIndex(builder.columnIndex); 
	}
	
	public int getInputColumnIndex() {
		return inputColumnIndex;
	}
	
	public void setInputColumnIndex(int index){
		this.inputColumnIndex = index; 
	}
	
	public Object getValue(NewRowEvent newRowEvent){
		return newRowEvent.getInputDataRow().get(inputColumnIndex);
	}
	
	
	public static class Builder{
		private int columnIndex; 
		private Format formatter = null; 
		
		
		public Builder(int columnIndex){
			this.columnIndex = columnIndex; 
		}
		
		public Builder useFormatter(Format format){
			this.formatter = format; 
			return this; 
		}
		
		public DefaultPivotHeaderRow build(){
			return new DefaultPivotHeaderRow(this); 
		}
	}
}
