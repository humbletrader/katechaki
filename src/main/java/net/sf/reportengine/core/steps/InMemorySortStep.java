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
/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.util.StepIOKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dragos balan
 *
 */
public class InMemorySortStep extends AbstractReportStep<String, String, List<NewRowEvent>> {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(InMemorySortStep.class);
	
	private PriorityQueue<NewRowEvent> inMemoryResult ; 
	
	
	public StepResult<String> init(StepInput stepInput){
		inMemoryResult = new PriorityQueue<NewRowEvent>(
								100, 
								new NewRowComparator(	getGroupColumns(stepInput), 
														getDataColumns(stepInput))); 
		return StepResult.NO_RESULT; 
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.AbstractReportStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)
	 */
	public StepResult<String> execute(NewRowEvent rowEvent, StepInput stepInput) {
		inMemoryResult.offer(rowEvent);
		return StepResult.NO_RESULT; 
	}
	
	public StepResult<List<NewRowEvent>> exit(StepInput stepInput){
		List<NewRowEvent> arrayResult = new ArrayList<NewRowEvent>(inMemoryResult.size());
		
		while(!inMemoryResult.isEmpty()){
			arrayResult.add(inMemoryResult.poll()); 
		}
		
		//the result is ready for writing
		//getAlgoContext().set(ContextKeys.IN_MEM_SORTED_RESULT, arrayResult); 
		return new StepResult<List<NewRowEvent>>(StepIOKeys.IN_MEM_SORTED_RESULT, arrayResult); 
	}
}
