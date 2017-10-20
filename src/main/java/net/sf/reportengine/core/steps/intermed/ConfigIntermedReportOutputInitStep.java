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
package net.sf.reportengine.core.steps.intermed;

import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.out.IntermediateCrosstabOutput;
import net.sf.reportengine.util.StepIOKeys;

import static net.sf.reportengine.util.StepIOKeys.*;

public class ConfigIntermedReportOutputInitStep implements AlgorithmInitStep<IntermediateCrosstabOutput> {

	public StepResult<IntermediateCrosstabOutput> init(StepInput stepInput) {
		IntermediateCrosstabOutput intermCrosstabOutput = new IntermediateCrosstabOutput(); 
		return new StepResult<>(INTERMEDIATE_CROSSTAB_OUTPUT, intermCrosstabOutput);
	}

}
