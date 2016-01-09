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
package net.sf.reportengine.core.algorithm.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.core.algorithm.AbstractMultiStepAlgo;
import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.DefaultAlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep;
import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.core.algorithm.steps.AlgorithmMainStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.util.AlgoIOKeys;
import net.sf.reportengine.util.StepIOKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * This multi step algorithm performs the following operations : 
 *  1. transfer the algo input into the context 
 *  2. opens the input 
 *  3. loops through the reportInput and executes the algorithm steps 
 *  4. closes the input 
 *  5. transfers the context into algo output (only those specified in the stepToAlgoMapping)
 * </p>
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 */
public class LoopThroughTableInputAlgo extends AbstractMultiStepAlgo {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoopThroughTableInputAlgo.class);
    
    /**
     * 
     * @param algorithmName
     */
    public LoopThroughTableInputAlgo(String algorithmName){
        this(algorithmName, new HashMap<StepIOKeys, AlgoIOKeys>());
    }
    
    /**
     * 
     * @param algorithmName
     * @param stepToAlgoOutputMapping
     */
    public LoopThroughTableInputAlgo(String algorithmName, 
                                     Map<StepIOKeys, AlgoIOKeys> stepToAlgoOutputMapping) {
        super(algorithmName, stepToAlgoOutputMapping); 
    }

    /**
     * executes the report
     */
    public Map<AlgoIOKeys, Object> execute(Map<AlgoIOKeys, Object> inputParams) {

        Map<AlgoIOKeys, Object> algoResult = null; 

        LOGGER.debug("creating the context");
        AlgoContext context = new DefaultAlgorithmContext();
        TableInput tableInput = null; 
        try {
            LOGGER.debug("opening report input");
            tableInput = buildTableInput(inputParams);
            tableInput.open();

            LOGGER.debug("start looping through the input");
            StepResult stepResult = null;

            // execution of the init steps
            for (AlgorithmInitStep initStep : getInitSteps()) {
                stepResult = initStep.init(new StepInput(inputParams, context));
                addStepResultToContext(context, stepResult);
            }

            // call init for each step
            for (AlgorithmMainStep mainStep : getMainSteps()) {
                stepResult = mainStep.init(new StepInput(inputParams, context));
                addStepResultToContext(context, stepResult);
            }

            // iteration through input data (row by row)
            while (tableInput.hasNext()) {

                // get the current data row
                List<Object> currentRow = tableInput.next();
                LOGGER.trace("executing algo steps for input row {}", currentRow);

                // then we pass the dataRow through all the report steps
                for (AlgorithmMainStep algoStep : getMainSteps()) {
                    stepResult = algoStep.execute(new NewRowEvent(currentRow), 
                                                  new StepInput(inputParams, context));
                    addStepResultToContext(context, stepResult);
                }
            }

            //call exit
            for (AlgorithmMainStep mainStep : getMainSteps()) {
                stepResult = mainStep.exit(new StepInput(inputParams, context));
                addStepResultToContext(context, stepResult);
            }

            // calling the exit for all registered steps
            for (AlgorithmExitStep exitStep : getExitSteps()) {
                stepResult = exitStep.exit(new StepInput(inputParams, context));
                addStepResultToContext(context, stepResult);
            }
            
            algoResult = transferStepResultsToAlgoResults(context); 
            
        } finally {
            LOGGER.debug("finished looping through the input");
            tableInput.close();
        }
        
        return algoResult;
    }
    
    
    /**
     * builds the report input from the input parameters
     * 
     * @param inputParams
     *            the map of input parameters
     * @return the input stream which will be looped
     */
    protected TableInput buildTableInput(Map<AlgoIOKeys, Object> inputParams) {
        return (TableInput) inputParams.get(AlgoIOKeys.TABLE_INPUT);
    }
    
    /**
     * 
     * @param context
     * @param stepResult
     */
    private void addStepResultToContext(AlgoContext context, StepResult stepResult) {
        if (stepResult != null && !StepResult.NO_RESULT.equals(stepResult)) {
            context.set(stepResult.getKey(), stepResult.getValue());
        }
    }
}
