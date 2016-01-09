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

import net.sf.reportengine.in.TableInput;

/**
 * @author dragos balan
 *
 */
public abstract class AbstractTable {

    /**
     * the input of the report
     */
    private final TableInput input;

    /**
     * whether the report will display totals or not
     */
    private boolean showTotals = false;
    // false is assigned initially because the report may not have group columns
    // therefore no totals to display.
    // As soon as you add to the table (via the fluent builder)a data column
    // containing a group calculator
    // the value is re-calculated to true
    // If the data column is not added via fluent api then this value remains
    // the same

    /**
     * whether the report will display grand total
     */
    private boolean showGrandTotal = false;
    // false is assigned initially because the report may not have group columns
    // therefore no totals to display.
    // As soon as you add to the report (via the fluent builder)a data column
    // containing a group calculator
    // the value is re-calculated to true
    // If the data column is not added via fluent api then this value remains
    // the same

    /**
     * whether the report will display normal data rows or not
     */
    private boolean showDataRows = true;

    /**
     * Whether or not data from the input is already sorted. By default we'll
     * assume this flag is set to true because the sorting will slow the
     * reporting process.
     */
    private boolean valuesSorted = true;

    /**
     * 
     * @param input
     * @param showTotals
     * @param showGrandTotal
     * @param showDataRows
     * @param valuesSorted
     */
    public AbstractTable(TableInput input,
                         boolean showTotals,
                         boolean showGrandTotal,
                         boolean showDataRows,
                         boolean valuesSorted) {
        this.input = input;
        this.showTotals = showTotals;
        this.showGrandTotal = showGrandTotal;
        this.showDataRows = showDataRows;
        this.valuesSorted = valuesSorted;
    }

    /**
     * validates the parameters of this table
     */
    protected abstract void validate();

    /**
     * @return the input
     */
    public TableInput getInput() {
        return input;
    }

    /**
     * show totals getter
     * 
     * @return true if this report has to show totals
     */
    public boolean getShowTotals() {
        return showTotals;
    }

    /**
     * show data rows getter
     * 
     * @return true if this report has to show data rows
     */
    public boolean getShowDataRows() {
        return showDataRows;
    }

    /**
     * getter for showGrandTotal flag
     * 
     * @return true if this report will show the grand totals
     */
    public boolean getShowGrandTotal() {
        return showGrandTotal;
    }

    /**
     * @return the valuesSorted
     */
    public boolean hasValuesSorted() {
        return valuesSorted;
    }

}
