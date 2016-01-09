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
package net.sf.reportengine.core.steps.crosstab;

import net.sf.reportengine.core.algorithm.steps.AbstractInitStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.util.StepIOKeys;
import net.sf.reportengine.util.CtMetadata;
import net.sf.reportengine.util.DistinctValuesHolder;
import net.sf.reportengine.util.AlgoIOKeys;

/**
 * @author dragos balan
 *
 */
public class GenerateCrosstabMetadataInitStep extends AbstractInitStep<CtMetadata> {

	public StepResult<CtMetadata> init(StepInput stepInput) {
		DistinctValuesHolder distinctValuesHolder = 
				(DistinctValuesHolder)stepInput.getAlgoInput(AlgoIOKeys.DISTINCT_VALUES_HOLDER); 
		CtMetadata ctMetadata = new CtMetadata(distinctValuesHolder); 
		//getAlgoContext().set(ContextKeys.CROSSTAB_METADATA, ctMetadata); 
		return new StepResult<CtMetadata>(StepIOKeys.CROSSTAB_METADATA, ctMetadata); 
	}
}
