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
package net.sf.reportengine.out;

import net.sf.reportengine.core.ReportEngineRuntimeException;

public class ReportOutputException extends ReportEngineRuntimeException{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 8977998834641107249L;
	
	/**
	 * 
	 * @param message
	 */
	public ReportOutputException(String message){
        super(message);
    }
    
	/**
	 * 
	 * @param cause
	 */
    public ReportOutputException(Throwable cause){
        super(cause);
    }
    
    /**
     * 
     * @param message
     * @param cause
     */
    public ReportOutputException(String message, Throwable cause){
        super(message, cause);
    }

}
