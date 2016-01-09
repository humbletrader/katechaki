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
package net.sf.reportengine.core.steps;

import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.components.CellProps;
import net.sf.reportengine.components.RowProps;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.config.VertAlign;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.CalcIntermResult;
import net.sf.reportengine.util.StepIOKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlatTableTotalsOutputStep extends AbstractOutputStep<String, Integer, Integer> {

    /**
     * the one and only logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FlatTableTotalsOutputStep.class);

    /**
     * "Grand " label. To be used in front of Total, Average, Count etc.
     */
    public static final String GRAND_TOTAL_STRING = "Grand ";

    /**
     * local copy of the group columns
     */
    private List<GroupColumn> groupCols;

    /**
     * local copy of the data columns
     */
    private List<DataColumn> dataCols;

    /**
     * 
     */
    private ArrayList<Integer> distribOfCalculatorsInDataColsArray;

    /**
     * the label of the calculators with whitespaces between
     */
    private String calcLabels;

    /**
     * init method
     */
    public StepResult<String> init(StepInput stepInput) {
        groupCols = getGroupColumns(stepInput);
        dataCols = getDataColumns(stepInput);
        distribOfCalculatorsInDataColsArray = getCalculatorDistributionInColumnDataArray(stepInput);
        calcLabels = getLabelsForAllCalculators(stepInput);

        LOGGER.trace("The FlatReportTotalsOutputStep has been initialized. The distribution array is null {}",
                     (distribOfCalculatorsInDataColsArray == null));
        return StepResult.NO_RESULT;
    }

    /**
     * execute
     */
    public StepResult<Integer> execute(NewRowEvent rowEvent, StepInput stepInput) {
        int currentDataRowNumber = getDataRowCount(stepInput).intValue();
        int groupLevel = getGroupingLevel(stepInput);

        // when non simple data row
        if (groupLevel >= 0 && getShowTotals(stepInput)) {
            int totalRowEnd = computeCalcRowNumberForAggLevel(stepInput, groupLevel);
            // TODO: this operation is the opposite of
            // computeAggLevelForCalcRowNumber which is
            // called inside outputTotalRowsFromTo. One of them should be
            // deleted.

            // output totals from level 0 to current grouping level
            outputTotalRowsFromTo(stepInput, 0, totalRowEnd, currentDataRowNumber);
            // the count from 0 to totalRowEnd is totalRowEnd+1
            currentDataRowNumber = currentDataRowNumber + totalRowEnd + 1;
            LOGGER.trace("not displaying totals because current level is {}", groupLevel);
        }
        return new StepResult<Integer>(StepIOKeys.DATA_ROW_COUNT,
                                       Integer.valueOf(currentDataRowNumber));
    }

    /**
     * exit displays the last totals in the calculator matrix buffer and the
     * grand total
     */
    public StepResult<Integer> exit(StepInput stepInput) {
        int localDataRowNbr = getDataRowCount(stepInput).intValue();
        CalcIntermResult[][] calcIntermResults = getCalcIntermResultsMatrix(stepInput);

        if (groupCols != null && getShowTotals(stepInput)) {
            // calculators.length-2 because for levelCalculators.length-1 is a
            // separate call
            // (a call for Grand total see below)
            outputTotalRowsFromTo(stepInput, 0, calcIntermResults.length - 2, localDataRowNbr);
            localDataRowNbr = localDataRowNbr + calcIntermResults.length - 1;
            // length-1 because we output from 0 to length-2 (included)
        }

        // now the grand total
        if (getShowGrandTotal(stepInput)) {
            // outputTotalsRow("Grand Total",
            // calculators[calculators.length-1]);
            outputTotalsRow(stepInput,
                            GRAND_TOTAL_GROUPING_LEVEL,
                            calcIntermResults[calcIntermResults.length - 1],
                            localDataRowNbr);
            localDataRowNbr = localDataRowNbr + 1;
        }
        return new StepResult<Integer>(StepIOKeys.DATA_ROW_COUNT, Integer.valueOf(localDataRowNbr));
    }

    /**
     * outputs the totals existing on the specified rows starting with rowStart
     * and ending with rowEnnd (inclusive)
     * 
     * @param rowStart
     *            the first row to start outputting
     * @param rowEnd
     *            the last row to output
     */
    private void outputTotalRowsFromTo(StepInput stepInput,
                                       int rowStart,
                                       int rowEnd,
                                       int dataRowNumber) {
        LOGGER.trace("output totals from {} to {}", rowStart, rowEnd);
        CalcIntermResult[][] calcResults = getCalcIntermResultsMatrix(stepInput);
        for (int row = rowStart; row <= rowEnd; row++) {
            // based on the row we can compute the aggregation level so that we
            // can determine the
            // column to use from the previous data row
            int aggLevel = computeAggLevelForCalcRowNumber(stepInput, row);

            outputTotalsRow(stepInput, aggLevel, calcResults[row], dataRowNumber);
            dataRowNumber++;
        }
    }

    /**
     * 
     * @param groupLevel
     * @param calcResultForCurrentGrpLevel
     */
    private void outputTotalsRow(StepInput stepInput,
                                 int groupLevel,
                                 CalcIntermResult[] calcResultForCurrentGrpLevel,
                                 int dataRowNumber) {

        // ReportOutput output = getReportOutput(stepInput);
        // output.startDataRow(new RowProps(dataRowNumber));
        outputOneValue(stepInput,
                       DataRowsOutputStep.START_DATA_ROW_TEMPLATE,
                       new RowProps(dataRowNumber));

        if (groupCols != null && groupCols.size() > 0) {
            // prepare and output the Total column
            String totalString = getTotalStringForGroupingLevel(stepInput, calcLabels, groupLevel);
            CellProps cellProps =
                new CellProps.Builder(totalString).horizAlign(HorizAlign.LEFT)
                                                  .vertAlign(VertAlign.MIDDLE)
                                                  .rowNumber(dataRowNumber)
                                                  .build();
            // output.outputDataCell(cellProps);
            outputOneValue(stepInput, DataRowsOutputStep.DATA_CELL_TEMPLATE, cellProps);

            if (groupCols.size() > 1) {
                // for all other grouping columns put white spaces
                // (groupColumns.length-1 colspan because the first column was
                // already filled with the word "Total xxxx"

                // if you want a single cell spanning multiple rows un-comment
                // below
                // output.output(new CellProps.Builder(ReportOutput.WHITESPACE)
                // .colspan(groupCols.size()-1)
                // .build());

                // this is to display an empty cell for every remaining group
                // column
                for (int i = 1; i < groupCols.size(); i++) {
                    CellProps whitespaceCellProps =
                        new CellProps.Builder(CellProps.WHITESPACE).rowNumber(dataRowNumber)
                                                                   .build();
                    // output.outputDataCell(whitespaceCellProps);
                    outputOneValue(stepInput,
                                   DataRowsOutputStep.DATA_CELL_TEMPLATE,
                                   whitespaceCellProps);
                }
            }
        }

        String formattedResult = null;

        // then iterate over data columns to display the totals for those having
        // calculators
        for (int i = 0; i < dataCols.size(); i++) {
            DataColumn column = dataCols.get(i);
            if (column.getCalculator() != null) {
                int calculatorIndex = distribOfCalculatorsInDataColsArray.get(i).intValue();
                Object calculatorResult = calcResultForCurrentGrpLevel[calculatorIndex].getResult();

                // format the computed value
                formattedResult = dataCols.get(i).getFormattedTotal(calculatorResult);
                CellProps dataCellProps =
                    new CellProps.Builder(formattedResult).horizAlign(dataCols.get(i)
                                                                              .getHorizAlign())
                                                          .vertAlign(dataCols.get(i).getVertAlign())
                                                          .rowNumber(dataRowNumber)
                                                          .build();
                // output.outputDataCell(dataCellProps);
                outputOneValue(stepInput, DataRowsOutputStep.DATA_CELL_TEMPLATE, dataCellProps);
            } else {
                // if the column doesn't have a calculator associated
                // then display an empty value (whitespace) with col span 1
                CellProps whitespaceCellProps =
                    new CellProps.Builder(CellProps.WHITESPACE).rowNumber(dataRowNumber).build();
                // output.outputDataCell(whitespaceCellProps);
                outputOneValue(stepInput,
                               DataRowsOutputStep.DATA_CELL_TEMPLATE,
                               whitespaceCellProps);
            }
        }
        // output.endDataRow();
        outputNoValue(stepInput, DataRowsOutputStep.END_DATA_ROW_TEMPLATE);
    }

    /**
     * 
     * @param stepInput
     * @return
     */
    protected String getLabelsForAllCalculators(StepInput stepInput) {
        StringBuilder result = new StringBuilder();
        for (DataColumn dataColumn : dataCols) {
            if (dataColumn.getCalculator() != null) {
                result.append(dataColumn.getCalculator().getLabel()).append(" ");
            }
        }
        return result.toString();
    }
}
