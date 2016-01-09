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

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.steps.NewRowComparator;
import net.sf.reportengine.in.MultipleExternalSortedFilesTableInput;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.util.AlgoIOKeys;
import net.sf.reportengine.util.StepIOKeys;

/**
 * 
 * @author dragos balan
 *
 */
public class MultipleSortedFilesInputAlgo extends LoopThroughTableInputAlgo {
    
    /**
     * 
     * @param algorithmName
     */
    public MultipleSortedFilesInputAlgo(String algorithmName){
        this(algorithmName, new HashMap<StepIOKeys, AlgoIOKeys>()); 
    }
    
    /**
     * 
     * @param algorithmName
     * @param stepToAlgoOutputMapping
     */
    public MultipleSortedFilesInputAlgo(String algorithmName, Map<StepIOKeys, AlgoIOKeys> stepToAlgoOutputMapping) {
        super(algorithmName, stepToAlgoOutputMapping);
    }
    
    /**
     * 
     */
    protected TableInput buildTableInput(Map<AlgoIOKeys, Object> inputParams) {
        return new MultipleExternalSortedFilesTableInput(
                       (List<File>) inputParams.get(AlgoIOKeys.SORTED_FILES),
                       new NewRowComparator((List<GroupColumn>) inputParams.get(AlgoIOKeys.GROUP_COLS),
                                            (List<DataColumn>) inputParams.get(AlgoIOKeys.DATA_COLS)));
    }
}
