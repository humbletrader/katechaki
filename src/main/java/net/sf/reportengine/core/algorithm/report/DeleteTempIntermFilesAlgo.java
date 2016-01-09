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
import java.util.Map;

import net.sf.reportengine.core.algorithm.AbstractAlgo;
import net.sf.reportengine.util.AlgoIOKeys;
import net.sf.reportengine.util.ReportIoUtils;

/**
 * @author dragos balan
 *
 */
public class DeleteTempIntermFilesAlgo extends AbstractAlgo {

    public DeleteTempIntermFilesAlgo() {
        super("Temporary intermediate files cleaner");
    }

    /* (non-Javadoc)
     * @see net.sf.reportengine.core.algorithm.Algorithm#execute(java.util.Map)
     */
    public Map<AlgoIOKeys, Object> execute(Map<AlgoIOKeys, Object> input) {
        File tempIntermFile = (File) input.get(AlgoIOKeys.INTERMEDIATE_OUTPUT_FILE);
        ReportIoUtils.deleteTempFileIfNotDebug(tempIntermFile);
        //return an empty result
        return new HashMap<AlgoIOKeys, Object>();
    }

}
