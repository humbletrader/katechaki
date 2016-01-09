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

import static net.sf.reportengine.util.UserRequestedBoolean.FALSE_NOT_REQUESTED_BY_USER;
import static net.sf.reportengine.util.UserRequestedBoolean.FALSE_REQUESTED_BY_USER;
import static net.sf.reportengine.util.UserRequestedBoolean.TRUE_NOT_REQUESTED_BY_USER;
import static net.sf.reportengine.util.UserRequestedBoolean.TRUE_REQUESTED_BY_USER;

import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.PivotData;
import net.sf.reportengine.config.PivotHeaderRow;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.util.UserRequestedBoolean;

/**
 * <p>Builder for a {@link PivotTable} component</p>
 * <p> 
 * The typical usage is: 
 * <pre>
 *       PivotTable table = new PivotTableBuilder(new TextTableInput("./input/expenses.csv", ","))
 *             .addDataColumn(new DefaultDataColumn("Month",0))
 *             .addHeaderRow(new DefaultPivotHeaderRow(1))
 *             .pivotData(new DefaultPivotData(2))
 *             .build();
 * </pre>
 * </p>
 * @author dragos balan
 * @see PivotTable
 */
public class PivotTableBuilder {

    private UserRequestedBoolean showTotals = FALSE_NOT_REQUESTED_BY_USER;
    private UserRequestedBoolean showGrandTotal = FALSE_NOT_REQUESTED_BY_USER;

    private boolean showDataRows = true;
    private boolean valuesSorted = true;

    private TableInput tableInput = null;

    private List<DataColumn> dataColumns = new ArrayList<DataColumn>();
    private List<GroupColumn> groupColumns = new ArrayList<GroupColumn>();
    private List<PivotHeaderRow> headerRows = new ArrayList<PivotHeaderRow>();
    private PivotData pivotData = null;

    /**
     * constructor for this builder based on the provided input
     * 
     * @param input
     *            the input for this component
     */
    public PivotTableBuilder(TableInput input) {
        this.tableInput = input;
    }

    public PivotTableBuilder showTotals(boolean show) {
        this.showTotals = show ? TRUE_REQUESTED_BY_USER : FALSE_REQUESTED_BY_USER;
        return this;
    }

    public PivotTableBuilder showTotals() {
        return showTotals(true);
    }

    public PivotTableBuilder showGrandTotal(boolean show) {
        this.showGrandTotal = show ? TRUE_REQUESTED_BY_USER : FALSE_REQUESTED_BY_USER;
        return this;
    }

    public PivotTableBuilder showGrandTotal() {
        return showGrandTotal(true);
    }

    public PivotTableBuilder showDataRows(boolean show) {
        this.showDataRows = show;
        return this;
    }

    public PivotTableBuilder showDataRows() {
        return showDataRows(true);
    }

    public PivotTableBuilder sortValues() {
        this.valuesSorted = false;
        return this;
    }

    @Deprecated
    public PivotTableBuilder input(TableInput input) {
        this.tableInput = input;
        return this;
    }

    public PivotTableBuilder dataColumns(List<DataColumn> dataCols) {
        // if(dataCols != null){
        for (DataColumn dataColumn : dataCols) {
            internalAddDataColumn(dataColumn);
        }
        // }
        return this;
    }

    private void internalAddDataColumn(DataColumn dataCol) {
        this.dataColumns.add(dataCol);
        if (dataCol.getCalculator() != null) {
            if (!showTotals.isRequestedByUser()) {
                this.showTotals = TRUE_NOT_REQUESTED_BY_USER;
            }
            if (!showGrandTotal.isRequestedByUser()) {
                this.showGrandTotal = TRUE_NOT_REQUESTED_BY_USER;
            }
        }
    }

    public PivotTableBuilder addDataColumn(DataColumn dataCol) {
        internalAddDataColumn(dataCol);
        return this;
    }

    public PivotTableBuilder groupColumns(List<GroupColumn> groupCols) {
        this.groupColumns = groupCols;
        return this;
    }

    public PivotTableBuilder addGroupColumn(GroupColumn groupCol) {
        this.groupColumns.add(groupCol);
        return this;
    }

    public PivotTableBuilder headerRows(List<PivotHeaderRow> headerRows) {
        this.headerRows = headerRows;
        return this;
    }

    public PivotTableBuilder addHeaderRow(PivotHeaderRow headerRow) {
        this.headerRows.add(headerRow);
        return this;
    }

    public PivotTableBuilder pivotData(PivotData data) {
        this.pivotData = data;
        return this;
    }

    public PivotTable build() {

        return new DefaultPivotTable(tableInput,
                                     dataColumns,
                                     groupColumns,
                                     pivotData,
                                     headerRows,
                                     showTotals.getValue(),
                                     showGrandTotal.getValue(),
                                     showDataRows,
                                     valuesSorted);
    }
}
