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


/**
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public abstract class AbstractPivotHeaderRow implements PivotHeaderRow {
	
	/**
	 * the formatter of the value
	 */
	private Format outFormat = null;
	
	
	/**
	 * 
	 * @param format			the formatter
	 */
	public AbstractPivotHeaderRow(Format format){
		setFormatter(format);
	}


	public Format getFormatter(){
		return outFormat; 
	}
	
	public void setFormatter(Format formatter){
		this.outFormat = formatter; 
	}
}
