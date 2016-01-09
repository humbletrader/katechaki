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
package net.sf.reportengine.samples;

import java.io.FileWriter;
import java.io.IOException;

import net.sf.reportengine.ReportBuilder;
import net.sf.reportengine.components.PivotTable;
import net.sf.reportengine.components.PivotTableBuilder;
import net.sf.reportengine.components.ReportTitle;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.DefaultPivotData;
import net.sf.reportengine.config.DefaultPivotHeaderRow;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.in.TextTableInput;
import net.sf.reportengine.out.ExcelXmlReportOutput;

/**
 * Sample Pivot table with groupings and sub-totals by year
 */
public class YearlyExpensesPivotTable {

    public static void main(String[] args) throws IOException {
        PivotTable pivotTable =
            new PivotTableBuilder(new TextTableInput("./input/yearlyExpenses.txt", "\t")).addGroupColumn(new DefaultGroupColumn("Year",
                                                                                                                                0,
                                                                                                                                0))
                                                                                         .addDataColumn(new DefaultDataColumn("Month",
                                                                                                                              1))
                                                                                         .addHeaderRow(new DefaultPivotHeaderRow(2))
                                                                                         .pivotData(new DefaultPivotData.Builder(3).useCalculator(GroupCalculators.SUM,
                                                                                                                                                  "%.2f")
                                                                                                                                   .build())
                                                                                         .showGrandTotal()
                                                                                         .showTotals()
                                                                                         .build();

        new ReportBuilder(new ExcelXmlReportOutput(new FileWriter("./target/YearlyPivotWithGroupingsAndSubtotals.xml"))).add(new ReportTitle("Yearly expenses arranged as a pivot table"))
                                                                                                                        .add(pivotTable)
                                                                                                                        .build()
                                                                                                                        .execute();
    }
}