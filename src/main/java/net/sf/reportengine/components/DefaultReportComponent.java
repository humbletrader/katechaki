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
package net.sf.reportengine.components;

import net.sf.reportengine.out.AbstractReportOutput;

/**
 * Report component backed by one freemarker template and a model
 *
 * @author dragos balan
 */
public class DefaultReportComponent<T> implements ReportComponent {
    
	/**
	 *  the name of the template backing this component
	 */
    private final String templateName; 
    
    /**
     * the data behind this component
     */
    private final T model; 
    
    /**
     * 
     * @param templateName
     * @param model
     */
    public DefaultReportComponent(String templateName, T model){
        this.templateName = templateName; 
        this.model = model; 
    }
    
    public String getTemplate(){
        return templateName;
    }
    
    public T getModel(){
        return model; 
    }
    
    /**
     * calls the output method of the output with the 
     * template name and the model of this class
     */
    public void output(AbstractReportOutput out){
       out.output(getTemplate(), getModel()); 
    }
}
