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
package net.sf.reportengine.core.algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.util.AlgoIOKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A list of algorithms which itself implements the {@link Algorithm} interface.
 * The execute method calls all Algorithm.execute() methods and passes the
 * result of each one as input for the next one.
 * 
 * 
 * @author dragos balan
 *
 */
public class AlgorithmContainer extends AbstractAlgo {

    /**
     * the one and only logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AlgorithmContainer.class);

    /**
     * the list of algorithms
     */
    private List<AbstractAlgo> algos = new ArrayList<AbstractAlgo>();

    /**
     * constructor for an algorithm container
     */
    public AlgorithmContainer(String algoName) {
        super(algoName); 
    }

    public void addAlgo(AbstractAlgo newAlgo) {
        algos.add(newAlgo);
    }

    /**
     * calls the execute method of each algorithm and passes the results from
     * one to the other until the end of the algorithm list is reached
     */
    public Map<AlgoIOKeys, Object> execute(Map<AlgoIOKeys, Object> input) {
        Map<AlgoIOKeys, Object> result = null;

        for (Iterator<AbstractAlgo> algoIterator = algos.iterator(); algoIterator.hasNext();) {
            AbstractAlgo algo = algoIterator.next();

            // execution of the algorithm
            LOGGER.debug("executing {} ...", algo.getName());
            result = algo.execute(input);
            LOGGER.debug("algorithm {} ended succesfully with result {}", algo.getName(), result);

            if (algoIterator.hasNext() && !result.isEmpty()) {
                // transfer this algo's output in next algo's input
                LOGGER.debug("transferring results {} of {} to the next algorithm", result, algo.getName());
                input.putAll(result);
            }
        }

        LOGGER.debug("algo container final result {}", result);
        return result;
    }
}
