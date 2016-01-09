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
package net.sf.reportengine.components;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.ConfigValidationException;
import net.sf.reportengine.core.algorithm.AbstractAlgo;
import net.sf.reportengine.core.algorithm.AbstractMultiStepAlgo;
import net.sf.reportengine.core.algorithm.AlgorithmContainer;
import net.sf.reportengine.core.algorithm.report.DeleteTempSortedFilesAlgo;
import net.sf.reportengine.core.algorithm.report.LoopThroughTableInputAlgo;
import net.sf.reportengine.core.algorithm.report.MultipleSortedFilesInputAlgo;
import net.sf.reportengine.core.steps.ColumnHeaderOutputInitStep;
import net.sf.reportengine.core.steps.DataRowsOutputStep;
import net.sf.reportengine.core.steps.EndTableExitStep;
import net.sf.reportengine.core.steps.ExternalSortPreparationStep;
import net.sf.reportengine.core.steps.FlatReportExtractTotalsDataInitStep;
import net.sf.reportengine.core.steps.FlatTableTotalsOutputStep;
import net.sf.reportengine.core.steps.GroupLevelDetectorStep;
import net.sf.reportengine.core.steps.InitReportDataInitStep;
import net.sf.reportengine.core.steps.PreviousRowManagerStep;
import net.sf.reportengine.core.steps.StartTableInitStep;
import net.sf.reportengine.core.steps.TotalsCalculatorStep;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.out.AbstractReportOutput;
import net.sf.reportengine.util.AlgoIOKeys;
import net.sf.reportengine.util.ReportUtils;
import net.sf.reportengine.util.StepAlgoKeyMapBuilder;
import net.sf.reportengine.util.StepIOKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * default implementation for FlatTable
 * 
 * @author dragos balan
 *
 */
final class DefaultFlatTable extends AbstractColumnBasedTable implements FlatTable {

    /**
     * the one and only logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultFlatTable.class);

    /**
     * the container for all algorithms used by this table component: 1. the
     * sorting algorithm 2. the reporting algorithm 3. the FO post processor
     * algorithm
     */
    private AlgorithmContainer tableAlgoContainer = new AlgorithmContainer("Flat Table Algorithm Container");


    /**
     * the one and only constructor based on a builder
     * 
     * @param builder
     *            the helper builder for this class
     */
    DefaultFlatTable(TableInput input,
                     List<DataColumn> dataColumns,
                     List<GroupColumn> groupColumns,
                     boolean showTotals,
                     boolean showGrandTotal,
                     boolean showDataRows,
                     boolean valuesSorted) {
        super(input,
              dataColumns,
              groupColumns,
              showTotals,
              showGrandTotal,
              showDataRows,
              valuesSorted);
    }

    /**
     * configuration of algorithms used by this flat table component
     */
    protected void config() {
        LOGGER.trace("configuring flat report");

        boolean needsProgramaticSorting = !hasValuesSorted() 
                                          || ReportUtils.isSortingInColumns(getGroupColumns(), getDataColumns());
        LOGGER.info("programatic sorting needed {} ", needsProgramaticSorting);

        if (needsProgramaticSorting) {
            tableAlgoContainer.addAlgo(configSortingAlgo());
        }
        tableAlgoContainer.addAlgo(configReportAlgo(needsProgramaticSorting));
        if(needsProgramaticSorting){
            tableAlgoContainer.addAlgo(new DeleteTempSortedFilesAlgo()); 
        }
    }

    /**
     * configuration of the sorting algorithm
     * 
     * @return
     */
    private AbstractAlgo configSortingAlgo() {
        // TODO: this sorting algo does not have multiple steps
        AbstractMultiStepAlgo sortingAlgo = 
                new LoopThroughTableInputAlgo("Sorting Algorithm", 
                                                     new StepAlgoKeyMapBuilder()
                                                        .add(StepIOKeys.FILES_WITH_SORTED_VALUES, AlgoIOKeys.SORTED_FILES)
                                                        .build());
        // main steps
        sortingAlgo.addMainStep(new ExternalSortPreparationStep());

        // exit steps
        return sortingAlgo;
    }

    /**
     * configures the algorithm which loops through each row of the input and
     * creates the final (?) output
     * 
     * @return the algorithm
     */
    private AbstractAlgo configReportAlgo(final boolean hasBeenPreviouslySorted) {
        AbstractMultiStepAlgo reportAlgo = null; 
        if(hasBeenPreviouslySorted){
            // if the input has been previously sorted then the sorting algorithm (the previous) has created
            // external sorted files which will serve as input from this point on
            reportAlgo = new MultipleSortedFilesInputAlgo("Main algorithm"); 
        }else{
            reportAlgo = new LoopThroughTableInputAlgo("Main algorithm"); 
        }
        
        reportAlgo.addInitStep(new InitReportDataInitStep());
        // TODO: only when report has totals
        reportAlgo.addInitStep(new FlatReportExtractTotalsDataInitStep());
        reportAlgo.addInitStep(new StartTableInitStep());
        reportAlgo.addInitStep(new ColumnHeaderOutputInitStep());

        // then we add the main steps
        reportAlgo.addMainStep(new GroupLevelDetectorStep());

        if (getShowTotals() || getShowGrandTotal()) {
            reportAlgo.addMainStep(new FlatTableTotalsOutputStep());
            reportAlgo.addMainStep(new TotalsCalculatorStep());
        }

        if (getShowDataRows()) {
            reportAlgo.addMainStep(new DataRowsOutputStep());
        }

        if (getGroupColumns() != null && getGroupColumns().size() > 0) {
            reportAlgo.addMainStep(new PreviousRowManagerStep());
        }

        reportAlgo.addExitStep(new EndTableExitStep());

        return reportAlgo;
    }
    
    
    /**
     * validation of configuration
     */
    protected void validate() {
        LOGGER.trace("validating flat report");

        List<DataColumn> dataColumns = getDataColumns();
        if (dataColumns == null || dataColumns.size() == 0) {
            throw new ConfigValidationException("The report needs at least one data column to work properly");
        }

        // if totals are needed then check if any GroupCalculators have been
        // added to ALL DataColumns
        if ((getShowTotals() || getShowGrandTotal()) && !ReportUtils.atLeastOneDataColumHasCalculators(dataColumns)) {
            throw new ConfigValidationException("Please configure a GroupCalculator to at least one DataColumn in order to display totals");
        }
    }

    /**
     * Call this method for execution of the report<br>
     * What this method does: <br>
     * <ul>
     * <li>validates the configuration - validateConfig() method call</li>
     * <li>opens the output - output.open()</li>
     * <li>runs each reportAlgo execute() method</li>
     * <li>closes the output - output.close()</li>
     * </ul>
     */
    public void output(AbstractReportOutput reportOutput) {
        validate();
        config();

        // preparing the context of the report reportAlgo
        Map<AlgoIOKeys, Object> inputParams = new EnumMap<AlgoIOKeys, Object>(AlgoIOKeys.class);
        inputParams.put(AlgoIOKeys.TABLE_INPUT, getInput());
        inputParams.put(AlgoIOKeys.NEW_REPORT_OUTPUT, reportOutput);
        inputParams.put(AlgoIOKeys.DATA_COLS, getDataColumns());
        inputParams.put(AlgoIOKeys.GROUP_COLS, getGroupColumns());
        inputParams.put(AlgoIOKeys.SHOW_TOTALS, getShowTotals());
        inputParams.put(AlgoIOKeys.SHOW_GRAND_TOTAL, getShowGrandTotal());

        Map<AlgoIOKeys, Object> result = tableAlgoContainer.execute(inputParams);
        LOGGER.info("flat table output ended with result {}", result);
    }
}
