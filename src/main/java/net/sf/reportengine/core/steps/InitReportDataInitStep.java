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

import net.sf.reportengine.core.algorithm.steps.AbstractInitStep;
import net.sf.reportengine.util.StepIOKeys;

/**
 * @author dragos
 *
 */
public class InitReportDataInitStep extends AbstractInitStep<Integer> {

	public StepResult<Integer> init(StepInput stepInput) {
		//getAlgoContext().set(ContextKeys.DATA_ROW_COUNT, Integer.valueOf(0));
		return new StepResult<Integer>(StepIOKeys.DATA_ROW_COUNT, Integer.valueOf(0)); 
	}
}
