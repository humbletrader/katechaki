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

import static net.sf.reportengine.util.UserProvidedBoolean.FALSE_PROVIDED_BY_USER;
import static net.sf.reportengine.util.UserProvidedBoolean.TRUE_NOT_PROVIDED_BY_USER;
import static net.sf.reportengine.util.UserProvidedBoolean.TRUE_PROVIDED_BY_USER;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.util.UserProvidedBoolean;

/**
 * <p>Builder for a {@link FlatTable} component</p>
 * <p>
 * The typical usage is: 
 * <pre>
 * {@code
 *     FlatTable flatTable = new FlatTableBuilder(new TextTableInput("./input/employees.csv", ","))
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
	
	private UserProvidedBoolean showTotals = UserProvidedBoolean.FALSE_NOT_PROVIDED_BY_USER;

    private UserProvidedBoolean showGrandTotal = UserProvidedBoolean.FALSE_NOT_PROVIDED_BY_USER;

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
    
    /**
     * instructs the builder to show the totals in accordance with the show parameter
     * 
     * @param show	true or false
     * @return	this builder
     */
    public FlatTableBuilder showTotals(boolean show) {
        this.showTotals = show ? TRUE_PROVIDED_BY_USER : FALSE_PROVIDED_BY_USER;
        return this;
    }
    
    /**
     * shows the totals
     * 
     * @return
     */
    public FlatTableBuilder showTotals() {
        return showTotals(true);
    }
    
    /**
     * shows the grand total in accordance with the show parameter
     * 
     * @param show	true or false
     * @return
     */
    public FlatTableBuilder showGrandTotal(boolean show) {
        this.showGrandTotal = show ? TRUE_PROVIDED_BY_USER : FALSE_PROVIDED_BY_USER;
        return this;
    }
    
    /**
     * shows the grand total
     * 
     * @return
     */
    public FlatTableBuilder showGrandTotal() {
        return showGrandTotal(true);
    }
    
    /**
     * shows the data rows as per the given flag
     * 
     * @param show
     * @return
     */
    public FlatTableBuilder showDataRows(boolean show) {
        this.showDataRows = show;
        return this;
    }
    
    /**
     * shows the data rows
     * @return
     */
    public FlatTableBuilder showDataRows() {
        return showDataRows(true);
    }
    
    /**
     * sorts the values 
     * @return
     */
    public FlatTableBuilder sortValues() {
        this.valuesSorted = false;
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
            // if the user didn't request to show totals
            if (!showTotals.isValueProvidedByUser()) {
                this.showTotals = TRUE_NOT_PROVIDED_BY_USER;
            }
            // else the user requested a specific value for the showTotals
            // and we keep that value

            // if the user didn't requested to show the grand total
            if (!showGrandTotal.isValueProvidedByUser()) {
                this.showGrandTotal = TRUE_NOT_PROVIDED_BY_USER;
            }// else we keep the value provided by the user
        }
    }
    
    /**
     * adds a new column to the table based on the information found on the input column having the columnIndex index
     * 
     * @param columnIndex	the index of the column (counted from zero in the input)
     * @return
     */
    public FlatTableBuilder addDataColumn(int columnIndex){
    	return addDataColumn(new DefaultDataColumn.Builder(columnIndex).build());
    }
    
    /**
     * adds a new column tot he table based on the provided input column and having the specified header 
     * 
     * @param columnIndex
     * @param columnHeader
     * @return
     */
    public FlatTableBuilder addDataColumn(int columnIndex, String columnHeader){
    	return addDataColumn(new DefaultDataColumn.Builder(columnIndex).header(columnHeader).build()); 
    }
    
    /**
     * adds the given data column to the table
     * 
     * @param dataCol
     * @return
     */
    public FlatTableBuilder addDataColumn(DataColumn dataCol) {
        internalAddDataColumn(dataCol);
        return this;
    }
    
    /**
     * adds the given list of columns to the table
     * 
     * @param dataCols
     * @return
     */
    @Deprecated
    public FlatTableBuilder dataColumns(List<? extends DataColumn> dataCols) {
        for (DataColumn dataColumn : dataCols) {
            internalAddDataColumn(dataColumn);
        }
        return this;
    }
    
    /**
     * adds the given columns to the table
     * @param groupCols
     * @return
     */
    @Deprecated
    public FlatTableBuilder groupColumns(List<? extends GroupColumn> groupCols) {
        for(GroupColumn col: groupCols){
        	this.groupColumns.add(col);
        }
        return this;
    }
    
    /**
     * adds a group column to the table (the column is referenced by its zero-based index)
     * 
     * @param columnIndex
     * @return
     */
    public FlatTableBuilder addGroupColumn(int columnIndex){
    	return addGroupColumn(new DefaultGroupColumn.Builder(columnIndex).build()); 
    }
    
    /**
     * adds the given group column to the report
     * @param groupCol
     * @return
     */
    public FlatTableBuilder addGroupColumn(GroupColumn groupCol) {
        this.groupColumns.add(groupCol);
        return this;
    }
    
    /**
     * builds the flat table based on the parameters provided by the user of this class
     * @return
     */
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
