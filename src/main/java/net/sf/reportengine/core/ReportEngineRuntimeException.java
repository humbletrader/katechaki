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
package net.sf.reportengine.core;


/**
 * the root exception for all report engine API
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public class ReportEngineRuntimeException extends RuntimeException {

    /**
	 * serial version id
	 */
	private static final long serialVersionUID = -7645378899218415130L;

	public ReportEngineRuntimeException(){
        super();
    }
    
    public ReportEngineRuntimeException(Throwable source){
        super(source);
    }
    
    public ReportEngineRuntimeException(String message){
        super(message);
    }
    
    public ReportEngineRuntimeException(String message, Throwable cause){
        super(message, cause);
    }
    

}
