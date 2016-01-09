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
package net.sf.reportengine.core.algorithm.report;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.algorithm.steps.AlgorithmMainStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.in.InMemoryTableInput;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.util.AlgoIOKeys;
import net.sf.reportengine.util.StepIOKeys;

import org.apache.commons.lang.math.NumberUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author dragos balan
 *
 */
public class TestDefaultLoopThroughTableInputAlgo {

    private TableInput testInput =
        new InMemoryTableInput(new Object[][] { new String[] { "1", "2", "3" }, 
                                                new String[] { "4", "5", "6" } });

   

    private AlgorithmMainStep testMainStep = new AlgorithmMainStep<Integer, Integer, String>() {

        public StepResult<Integer> init(StepInput stepInput) {
            return new StepResult(StepIOKeys.DATA_ROW_COUNT, NumberUtils.INTEGER_ZERO);
        }

        public StepResult<Integer> execute(NewRowEvent dataRowEvent, StepInput stepInput) {
            Integer executionCounts = (Integer) stepInput.getContextParam(StepIOKeys.DATA_ROW_COUNT);
            return new StepResult<Integer>(StepIOKeys.DATA_ROW_COUNT,
                                           executionCounts + 1);
        }

        public StepResult<String> exit(StepInput stepInput) {
            return StepResult.NO_RESULT;
        }
    };

    private LoopThroughTableInputAlgo classUnderTest = null;

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Before
    public void setUp() throws Exception {
        classUnderTest = new LoopThroughTableInputAlgo("Test Loop Through TableInput", new HashMap<StepIOKeys, AlgoIOKeys>(){{put(StepIOKeys.DATA_ROW_COUNT, AlgoIOKeys.TEST_KEY);}});

        classUnderTest.addMainStep(testMainStep);
    }

    /**
     * Test method for
     * {@link net.sf.reportengine.core.algorithm.report.LoopThroughTableInputAlgo#execute()}
     * .
     */
    @Test
    public void testExecuteAlgorithm() {
        Map<AlgoIOKeys, Object> mockAlgoInput = new EnumMap<AlgoIOKeys, Object>(AlgoIOKeys.class);
        mockAlgoInput.put(AlgoIOKeys.TABLE_INPUT, testInput);
        // mockAlgoInput.put(IOKeys.REPORT_OUTPUT, testOut);

        Map<AlgoIOKeys, Object> algoResult = classUnderTest.execute(mockAlgoInput);
        Assert.assertEquals(Integer.valueOf(2), (Integer) algoResult.get(AlgoIOKeys.TEST_KEY));
    }
}
