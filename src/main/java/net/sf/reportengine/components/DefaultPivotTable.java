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

import static net.sf.reportengine.util.AlgoIOKeys.CROSSTAB_DATA;
import static net.sf.reportengine.util.AlgoIOKeys.CROSSTAB_HEADER_ROWS;
import static net.sf.reportengine.util.AlgoIOKeys.DATA_COLS;
import static net.sf.reportengine.util.AlgoIOKeys.GROUP_COLS;
import static net.sf.reportengine.util.AlgoIOKeys.NEW_REPORT_OUTPUT;
import static net.sf.reportengine.util.AlgoIOKeys.SHOW_GRAND_TOTAL;
import static net.sf.reportengine.util.AlgoIOKeys.SHOW_TOTALS;
import static net.sf.reportengine.util.AlgoIOKeys.TABLE_INPUT;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.PivotData;
import net.sf.reportengine.config.PivotHeaderRow;
import net.sf.reportengine.core.ConfigValidationException;
import net.sf.reportengine.core.algorithm.AbstractAlgo;
import net.sf.reportengine.core.algorithm.AbstractMultiStepAlgo;
import net.sf.reportengine.core.algorithm.AlgorithmContainer;
import net.sf.reportengine.core.algorithm.report.DeleteTempIntermFilesAlgo;
import net.sf.reportengine.core.algorithm.report.DeleteTempSortedFilesAlgo;
import net.sf.reportengine.core.algorithm.report.LoopThroughTableInputAlgo;
import net.sf.reportengine.core.algorithm.report.MultipleSortedFilesInputAlgo;
import net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep;
import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.core.steps.EndTableExitStep;
import net.sf.reportengine.core.steps.ExternalSortPreparationStep;
import net.sf.reportengine.core.steps.InitReportDataInitStep;
import net.sf.reportengine.core.steps.StartTableInitStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.core.steps.crosstab.ConstrDataColsForSecondProcessInitStep;
import net.sf.reportengine.core.steps.crosstab.ConstrGrpColsForSecondProcessInitStep;
import net.sf.reportengine.core.steps.crosstab.CrosstabHeaderOutputInitStep;
import net.sf.reportengine.core.steps.crosstab.DistinctValuesDetectorStep;
import net.sf.reportengine.core.steps.crosstab.GenerateCrosstabMetadataInitStep;
import net.sf.reportengine.core.steps.crosstab.IntermedRowMangerStep;
import net.sf.reportengine.core.steps.intermed.ConfigIntermedReportOutputInitStep;
import net.sf.reportengine.core.steps.intermed.ConstrIntermedDataColsInitStep;
import net.sf.reportengine.core.steps.intermed.ConstrIntermedGrpColsInitStep;
import net.sf.reportengine.core.steps.intermed.IntermedDataRowsOutputStep;
import net.sf.reportengine.core.steps.intermed.IntermedGroupLevelDetectorStep;
import net.sf.reportengine.core.steps.intermed.IntermedPreviousRowManagerStep;
import net.sf.reportengine.core.steps.intermed.IntermedReportExtractTotalsDataInitStep;
import net.sf.reportengine.core.steps.intermed.IntermedSetResultsExitStep;
import net.sf.reportengine.core.steps.intermed.IntermedTotalsCalculatorStep;
import net.sf.reportengine.core.steps.intermed.IntermedTotalsOutputStep;
import net.sf.reportengine.in.IntermediateCrosstabReportTableInput;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.out.AbstractReportOutput;
import net.sf.reportengine.out.IntermediateCrosstabOutput;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.AlgoIOKeys;
import net.sf.reportengine.util.ReportUtils;
import net.sf.reportengine.util.StepAlgoKeyMapBuilder;
import net.sf.reportengine.util.StepIOKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * This is the main class to be used for Cross tab reports (or Pivot tables).
 * The layout of pivot tables will look like: <br/>
 * <table border="1">
 * <tr>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td colspan="4" align="center"><b>Row header 1</b></td>
 * </tr>
 * <tr>
 * <td><b>Column 1</b></td>
 * <td><b>Column 2</b></td>
 * <td colspan="4" align="center"><b>Row header 2</b></td>
 * </tr>
 * <tr>
 * <td>value 1</td>
 * <td>value 2</td>
 * <td>ct data 11</td>
 * <td>ct data 12</td>
 * <td>ct data 13</td>
 * <td>ct data 14</td>
 * </tr>
 * <tr>
 * <td>value 3</td>
 * <td>value 4</td>
 * <td>ct data 21</td>
 * <td>ct data 22</td>
 * <td>ct data 23</td>
 * <td>ct data 24</td>
 * </tr>
 * <tr>
 * <td>value 5</td>
 * <td>value 6</td>
 * <td>ct data 31</td>
 * <td>ct data 32</td>
 * <td>ct data 33</td>
 * <td>ct data 34</td>
 * </tr>
 * </table>
 * <br/>
 * where the values from Row header 1, Row header 2, etc. are taken from the
 * input
 * 
 * <p>
 * Each pivot table report needs at least the following elements configured:
 * <ul>
 * <li>input</li>
 * <li>data columns configuration</li>
 * <li>row headers configuration</li>
 * <li>crosstab data</li>
 * </ul>
 * </p>
 * Normally, the pivot table is used as a component of a report
 * 
 * <pre>
 * PivotTabl pivotTable = new PivotTableBuilder(new TextInput(&quot;./inputData/expenses.csv&quot;, &quot;,&quot;))
 *                           .addDataColumn(new DefaultDataColumn(&quot;Month&quot;, 0))
 *                           .addHeaderRow(new DefaultCrosstabHeaderRow(1))
 *                           .crosstabData(new DefaultCrosstabData(2))
 *                           .build();
 * 
 *     Report report = new Report(new HtmlReportOutput(new FileWriter(&quot;/tmp/testPivot.html&quot;)));
 *     report.add(pivotTable);
 *     report.execute();
 * }
 * </pre>
 * 
 * but it can also be used as a stand alone component:
 * 
 * <pre>
 *  ReportOutput reportOutput = new DefaultReportOutput(new FileWriter("/tmp/testPivot.html"))
 *  reportOutput.open(); 
 * 	PivotTable pivotTable = new PivotTableBuilder((new TextInput("./inputData/expenses.csv", ","))
 * 	            .addDataColumn(new DefaultDataColumn("Month", 0))
 * 	            .addHeaderRow(new DefaultCrosstabHeaderRow(1))
 * 	            .crosstabData(new DefaultCrosstabData(2))
 * 	            .build();
 * 
 * 	pivotTable.output(reportOutput);
 *  reportOutput.close(); 
 * </pre>
 * 
 * </p>
 * 
 * @see TableInput
 * @see ReportOutput
 * @see PivotHeaderRow
 * @see DataColumn
 * @see GroupColumn
 * @see PivotData
 * 
 * @author dragos balan
 */
final class DefaultPivotTable extends AbstractColumnBasedTable implements PivotTable {

    /**
     * the one and only logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPivotTable.class);

    /**
     * the container for potential algorithms : the sorting algorithm , the
     * intermediate algorithm and the reporting algorithm
     */
    private AlgorithmContainer reportAlgoContainer = new AlgorithmContainer("Pivot Table Algorithm Container");

    /**
     * the crosstab header rows
     */
    private List<PivotHeaderRow> crosstabHeaderRowsAsList = new ArrayList<PivotHeaderRow>();

    /**
     * the crosstab data
     */
    private PivotData pivotData;

    /**
     * whether or not this report needs programatic sorting
     */
    private boolean needsProgramaticSorting = false;

    /**
     * constructs a crosstab report based on the builder values
     * 
     * @param builder
     */
    DefaultPivotTable(TableInput input,
                      List<DataColumn> dataColumns,
                      List<GroupColumn> groupColumns,
                      PivotData pivotData,
                      List<PivotHeaderRow> headerRows,
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
        this.pivotData = pivotData;
        this.crosstabHeaderRowsAsList = headerRows;
    }

    /**
     * validates the configuration
     */
    @Override
    protected void validate() {
        LOGGER.trace("validating pivot table configuration...");

        // crosstab data existence check
        PivotData ctData = getPivotData();
        if (ctData == null) {
            throw new ConfigValidationException("Crosstab reports need crosstab data configured");
        }

        // crosstab header validation
        List<PivotHeaderRow> ctHeader = getHeaderRows();
        if (ctHeader == null || ctHeader.size() == 0) {
            throw new ConfigValidationException("Crosstab reports need header rows configured");
        }

        // the crosstab report needs at least one group column or data column
        List<DataColumn> dataColumns = getDataColumns();
        List<GroupColumn> groupColumns = getGroupColumns();
        if ((dataColumns == null || dataColumns.size() == 0) && (groupColumns == null || groupColumns.size() == 0)) {
            throw new ConfigValidationException("Crosstab reports need data and/or group columns configured");
        }

        // if totals are needed then check if any GroupCalculators have been
        // added to ALL DataColumns
        if ((getShowTotals() || getShowGrandTotal()) && ctData.getCalculator() == null) {
            throw new ConfigValidationException("Please configure a GroupCalculator to CrosstabData to display totals");
        }
    }

    /**
     * configures the first intermediate report. Keep in mind that another
     * method is needed to configure the second report
     * 
     * @see #configSecondAlgo()
     */
    protected void config() {
        LOGGER.trace("configuring crosstab report ...");

        needsProgramaticSorting =
            !hasValuesSorted() || ReportUtils.isSortingInColumns(getGroupColumns(),
                                                                 getDataColumns());

        LOGGER.info("programatic sorting needed {} ", needsProgramaticSorting);

        if (needsProgramaticSorting) {
            reportAlgoContainer.addAlgo(configSortingAlgo());
        }

        reportAlgoContainer.addAlgo(configIntermedAlgo(needsProgramaticSorting));
        reportAlgoContainer.addAlgo(configSecondAlgo(needsProgramaticSorting));
        reportAlgoContainer.addAlgo(configCleaningAlgo(needsProgramaticSorting));
    }

    /**
     * configures the sorting algorithm
     * 
     * @return
     */
    private AbstractAlgo configSortingAlgo() {

        // TODO: improve here (this sorting algo doesn't have multiple steps)
        AbstractMultiStepAlgo sortingAlgo =
            new LoopThroughTableInputAlgo("External Sort Algorithm", 
                                           new StepAlgoKeyMapBuilder()
                                                .add(StepIOKeys.FILES_WITH_SORTED_VALUES, AlgoIOKeys.SORTED_FILES)
                                                .build());

        // main steps
        sortingAlgo.addMainStep(new ExternalSortPreparationStep());

        return sortingAlgo;
    }

    /**
     * configures the first algorithm responsible for : 1. discovering the
     * distinct values for the header 2. arranging the initial crosstab data
     * into rows for the second iteration 3. computing totals on the crosstab
     * data.
     * 
     * @return the intermediate algorithm
     */
    private AbstractAlgo configIntermedAlgo(final boolean hasBeenPreviouslySorted) {

        AbstractMultiStepAlgo algorithm = null; 
        Map<StepIOKeys, AlgoIOKeys> stepToAlgoKeysMapping = new StepAlgoKeyMapBuilder()
                                        .add(StepIOKeys.INTERMEDIATE_DISTINCT_VALUES_HOLDER, AlgoIOKeys.DISTINCT_VALUES_HOLDER)
                                        .add(StepIOKeys.INTERMEDIATE_SERIALIZED_FILE, AlgoIOKeys.INTERMEDIATE_OUTPUT_FILE)
                                        .build();
        if(hasBeenPreviouslySorted){
            algorithm = new MultipleSortedFilesInputAlgo("Intermediate Algorithm", stepToAlgoKeysMapping); 
        }else{
            algorithm = new LoopThroughTableInputAlgo("Intermediate Algorithm", stepToAlgoKeysMapping); 
        }

        // initial steps
        // algorithm.addInitStep(new ConfigIntermedColsInitStep());
        algorithm.addInitStep(new ConstrIntermedDataColsInitStep());
        algorithm.addInitStep(new ConstrIntermedGrpColsInitStep());

        // if(!needsProgramaticSorting){
        // algorithm.addInitStep(new ConfigIntermedIOInitStep());
        // }else{
        // algorithm.addInitStep(new
        // ConfigMultiExternalFilesInputForIntermReportInitStep());
        // }
        algorithm.addInitStep(new ConfigIntermedReportOutputInitStep());

        algorithm.addInitStep(new AlgorithmInitStep<String>() {
            public StepResult<String> init(StepInput stepInput) {
                ((IntermediateCrosstabOutput) stepInput.getContextParam(StepIOKeys.INTERMEDIATE_CROSSTAB_OUTPUT)).open();
                return StepResult.NO_RESULT;
            }
        });

        // TODO: only when totals add the step below
        algorithm.addInitStep(new IntermedReportExtractTotalsDataInitStep());
        // only for debug
        // algorithm.addInitStep(new ColumnHeaderOutputInitStep("Intermediate report"));

        // main steps
        algorithm.addMainStep(new DistinctValuesDetectorStep());
        algorithm.addMainStep(new IntermedGroupLevelDetectorStep());

        // only for debug
        // if( getShowTotals() || getShowGrandTotal()){
        // algorithm.addMainStep(new FlatReportTotalsOutputStep());
        // }
        algorithm.addMainStep(new IntermedRowMangerStep());

        if (getShowTotals() || getShowGrandTotal()) {
            algorithm.addMainStep(new IntermedTotalsCalculatorStep());
        }

        // only for debug
        // algorithm.addMainStep(new DataRowsOutputStep());

        // if( intermediateGroupCols.size() > 0){
        algorithm.addMainStep(new IntermedPreviousRowManagerStep());
        // }

        algorithm.addExitStep(new AlgorithmExitStep<String>() {
            public StepResult<String> exit(StepInput stepInput) {
                ((IntermediateCrosstabOutput) stepInput.getContextParam(StepIOKeys.INTERMEDIATE_CROSSTAB_OUTPUT)).close();
                return StepResult.NO_RESULT;
            }
        });

        algorithm.addExitStep(new IntermedSetResultsExitStep());

        return algorithm;
    }

    /**
     * the second algorithm is in charge of: 1. getting the input from the
     * intermediate algo 2. computing the totals on data rows 3. outputting to
     * the output
     */
    private AbstractAlgo configSecondAlgo(boolean hasBeenPreviouslySorted) {
        AbstractMultiStepAlgo algorithm = new LoopThroughTableInputAlgo("Pivot table algo") {
            @Override
            protected TableInput buildTableInput(Map<AlgoIOKeys, Object> inputParams) {
                File previousAlgoSerializedOutput =
                    (File) inputParams.get(AlgoIOKeys.INTERMEDIATE_OUTPUT_FILE);
                return new IntermediateCrosstabReportTableInput(previousAlgoSerializedOutput);
            }
        };

        // adding steps to the algorithm
        algorithm.addInitStep(new GenerateCrosstabMetadataInitStep());
        algorithm.addInitStep(new ConstrDataColsForSecondProcessInitStep());
        algorithm.addInitStep(new ConstrGrpColsForSecondProcessInitStep());

        // algorithm.addInitStep(new ConfigReportOutputInitStep());

        // algorithm.addInitStep(new OpenReportOutputInitStep());

        algorithm.addInitStep(new InitReportDataInitStep());

        algorithm.addInitStep(new IntermedReportExtractTotalsDataInitStep());

        // algorithm.addInitStep(new StartReportInitStep());
        algorithm.addInitStep(new StartTableInitStep());
        algorithm.addInitStep(new CrosstabHeaderOutputInitStep());

        algorithm.addMainStep(new IntermedGroupLevelDetectorStep());

        if (getShowTotals() || getShowGrandTotal()) {
            algorithm.addMainStep(new IntermedTotalsOutputStep());
            algorithm.addMainStep(new IntermedTotalsCalculatorStep());
        }

        algorithm.addMainStep(new IntermedDataRowsOutputStep());

        if (getGroupColumns() != null && getGroupColumns().size() > 0) {
            algorithm.addMainStep(new IntermedPreviousRowManagerStep());
        }

        // algorithm.addExitStep(new EndReportExitStep());
        algorithm.addExitStep(new EndTableExitStep());
        
        return algorithm;
    }
    
    private AbstractAlgo configCleaningAlgo(boolean hasBeenPreviouslySorted){
        AlgorithmContainer cleaningAlgo = new AlgorithmContainer("Cleaning Algo"); 
        if(hasBeenPreviouslySorted){
            cleaningAlgo.addAlgo(new DeleteTempSortedFilesAlgo());
        }
        cleaningAlgo.addAlgo(new DeleteTempIntermFilesAlgo());
        return cleaningAlgo;
    }

    public void output(AbstractReportOutput reportOutput) {
        // validation of the configuration
        validate();

        // configuration of the first report
        config();

        Map<AlgoIOKeys, Object> inputParams = new EnumMap<AlgoIOKeys, Object>(AlgoIOKeys.class);

        // setting the input/output
        inputParams.put(TABLE_INPUT, getInput());
        inputParams.put(NEW_REPORT_OUTPUT, reportOutput);

        // context keys specific to a flat report
        inputParams.put(DATA_COLS, getDataColumns());
        inputParams.put(GROUP_COLS, getGroupColumns());
        inputParams.put(CROSSTAB_HEADER_ROWS, crosstabHeaderRowsAsList);
        inputParams.put(CROSSTAB_DATA, pivotData);

        inputParams.put(SHOW_TOTALS, Boolean.valueOf(getShowTotals()));
        inputParams.put(SHOW_GRAND_TOTAL, Boolean.valueOf(getShowGrandTotal()));

        Map<AlgoIOKeys, Object> result = reportAlgoContainer.execute(inputParams);
        LOGGER.debug("pivot table output ended with {}", result);
    }

    /**
     * getter method for cross table header rows
     * 
     * @return a list of header rows
     */
    public List<PivotHeaderRow> getHeaderRows() {
        return crosstabHeaderRowsAsList;
    }

    /**
     * getter for crosstab data
     * 
     * @return the crosstab data
     */
    public PivotData getPivotData() {
        return pivotData;
    }
}
