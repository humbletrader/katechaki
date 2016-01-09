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
package net.sf.reportengine.core.steps.intermed;

import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.PivotData;
import net.sf.reportengine.core.steps.FlatTableTotalsOutputStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.util.AlgoIOKeys;
import net.sf.reportengine.util.StepIOKeys;

/**
 * displays the intermediate totals
 * 
 * @author dragos balan
 *
 */
public class IntermedTotalsOutputStep extends FlatTableTotalsOutputStep {

    /**
     * ATTENTION : changing the implementation of this method will have effect
     * on the following methods: {@link #getDataColumns()}
     * {@link #getDataColumnsLength()}
     * 
     * @return
     */
    @Override
    public List<DataColumn> getDataColumns(StepInput stepInput) {
        return (List<DataColumn>) stepInput.getContextParam(StepIOKeys.INTERNAL_DATA_COLS);
    }

    /**
     * ATTENTION : changing the implementation of this method will have effect
     * on the following methods: {@link #getGroupColumns()}
     * {@link #getGroupColumnsCount()}
     * {@link #computeAggLevelForCalcRowNumber(int)}
     * {@link #computeCalcRowNumberForAggLevel(int)}
     * 
     * @return
     */
    @Override
    public List<GroupColumn> getGroupColumns(StepInput stepInput) {
        return (List<GroupColumn>) stepInput.getContextParam(StepIOKeys.INTERNAL_GROUP_COLS);
    }

    public List<DataColumn> getInitialDataColumns(StepInput stepInput) {
        return (List<DataColumn>) stepInput.getAlgoInput(AlgoIOKeys.DATA_COLS);
    }

    @Override
    protected String getLabelsForAllCalculators(StepInput stepInput) {
        StringBuilder result = new StringBuilder();

        // first we iterate through data columns
        for (DataColumn dataColumn : getInitialDataColumns(stepInput)) {
            if (dataColumn.getCalculator() != null) {
                result.append(dataColumn.getCalculator().getLabel()).append(" ");
            }
        }

        // then we also check the pivotData
        PivotData pivotData = (PivotData) stepInput.getAlgoInput(AlgoIOKeys.CROSSTAB_DATA);
        if (pivotData.getCalculator() != null) {
            result.append(pivotData.getCalculator().getLabel()).append(" ");
        }

        return result.toString();
    }
}
