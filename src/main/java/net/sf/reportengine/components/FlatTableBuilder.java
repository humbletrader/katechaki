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

import static net.sf.reportengine.util.UserRequestedBoolean.FALSE_REQUESTED_BY_USER;
import static net.sf.reportengine.util.UserRequestedBoolean.TRUE_NOT_REQUESTED_BY_USER;
import static net.sf.reportengine.util.UserRequestedBoolean.TRUE_REQUESTED_BY_USER;

import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.util.UserRequestedBoolean;

/**
 * <p>Builder for a {@link FlatTable} component</p>
 * <p>
 * The typical usage is: 
 * <pre>
 * {@code
 *     FlatTable flatTable = new FlatTableBuilder(new TextTableInput("./input/blah.csv", ","))
 *              .addGroupColumn(...)
 *              .addGroupColumn(...)    
 *              .addDataColumn(...)
 *              .addDataColumn(...)
 *              .addDataColumn(...)
 *              .build();
 *}
 * </pre>
 * </p>
 * @author dragos balan
 * @see FlatTable
 * @since 0.13
 */
public class FlatTableBuilder {

    private UserRequestedBoolean showTotals = UserRequestedBoolean.FALSE_NOT_REQUESTED_BY_USER;

    private UserRequestedBoolean showGrandTotal = UserRequestedBoolean.FALSE_NOT_REQUESTED_BY_USER;

    private boolean showDataRows = true;

    private boolean valuesSorted = true;

    private TableInput tableInput = null;

    private List<DataColumn> dataColumns = new ArrayList<DataColumn>();

    private List<GroupColumn> groupColumns = new ArrayList<GroupColumn>();

    /**
     * constructs the builder for a FlatTable component
     * 
     * @param input
     *            the input for this component
     */
    public FlatTableBuilder(TableInput input) {
        this.tableInput = input;
    }

    public FlatTableBuilder showTotals(boolean show) {
        this.showTotals = show ? TRUE_REQUESTED_BY_USER : FALSE_REQUESTED_BY_USER;
        return this;
    }

    public FlatTableBuilder showTotals() {
        return showTotals(true);
    }

    public FlatTableBuilder showGrandTotal(boolean show) {
        this.showGrandTotal = show ? TRUE_REQUESTED_BY_USER : FALSE_REQUESTED_BY_USER;
        return this;
    }

    public FlatTableBuilder showGrandTotal() {
        return showGrandTotal(true);
    }

    public FlatTableBuilder showDataRows(boolean show) {
        this.showDataRows = show;
        return this;
    }

    public FlatTableBuilder showDataRows() {
        return showDataRows(true);
    }

    public FlatTableBuilder sortValues() {
        this.valuesSorted = false;
        return this;
    }

    @Deprecated
    public FlatTableBuilder input(TableInput input) {
        this.tableInput = input;
        return this;
    }

    /**
     * adds the data column to the internal list of data columns and checks if
     * there is any calculator assigned to it. If there is a calculator then the
     * showTotals is recomputed taking into account the groupCalculators and the
     * user's choice
     * 
     * @param dataCol
     */
    private void internalAddDataColumn(DataColumn dataCol) {
        this.dataColumns.add(dataCol);
        if (dataCol.getCalculator() != null) {

            // if the user didn't requested to show totals
            if (!showTotals.isRequestedByUser()) {
                this.showTotals = TRUE_NOT_REQUESTED_BY_USER;
            }
            // else ( the user requested a specific value for the
            // showTotals)
            // we keep that value

            // if the user didn't requested to show the grand total
            if (!showGrandTotal.isRequestedByUser()) {
                this.showGrandTotal = TRUE_NOT_REQUESTED_BY_USER;
            }
            // else we keep the user value
        }
    }

    public FlatTableBuilder addDataColumn(DataColumn dataCol) {
        internalAddDataColumn(dataCol);
        return this;
    }

    public FlatTableBuilder dataColumns(List<DataColumn> dataCols) {
        for (DataColumn dataColumn : dataCols) {
            internalAddDataColumn(dataColumn);
        }
        return this;
    }

    public FlatTableBuilder groupColumns(List<GroupColumn> groupCols) {
        this.groupColumns = groupCols;
        return this;
    }

    public FlatTableBuilder addGroupColumn(GroupColumn groupCol) {
        this.groupColumns.add(groupCol);
        return this;
    }

    public FlatTable build() {
        return new DefaultFlatTable(tableInput,
                                    dataColumns,
                                    groupColumns,
                                    showTotals.getValue(),
                                    showGrandTotal.getValue(),
                                    showDataRows,
                                    valuesSorted);
    }
}
