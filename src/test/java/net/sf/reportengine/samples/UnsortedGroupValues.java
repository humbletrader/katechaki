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
import net.sf.reportengine.components.FlatTable;
import net.sf.reportengine.components.FlatTableBuilder;
import net.sf.reportengine.components.ReportTitle;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.in.TextTableInput;
import net.sf.reportengine.out.HtmlReportOutput;

/**
 * When using group columns, the data in those columns needs to be sorted
 * (otherwise the reportengine will see a change of group everywhere) In this
 * example, the input data for the flat table is not sorted and the reportengine
 * is informed about this by using the sortValues() method in the FlatTable
 * builder class.
 */
public class UnsortedGroupValues {

    public static void main(String[] args) throws IOException {

        FlatTable table =
            new FlatTableBuilder(new TextTableInput("./input/unsortedExpenses.csv", ","))
            // // inform reportengine that it has to sort the values
            .sortValues()

                                                                                         .addGroupColumn(new DefaultGroupColumn.Builder(0).header("Month")
                                                                                                                                          .horizAlign(HorizAlign.LEFT)
                                                                                                                                          .build())
                                                                                         .addDataColumn(new DefaultDataColumn.Builder(1).header("On What?")
                                                                                                                                        .horizAlign(HorizAlign.LEFT)
                                                                                                                                        .build())
                                                                                         .addDataColumn(new DefaultDataColumn.Builder(2).header("Amount")
                                                                                                                                        .useCalculator(GroupCalculators.SUM)
                                                                                                                                        .horizAlign(HorizAlign.RIGHT)
                                                                                                                                        .build())
                                                                                         .build();

        // build and execute the report
        new ReportBuilder(new HtmlReportOutput(new FileWriter("./target/MonthlyExpensesFromUnsortedInput.html"))).add(new ReportTitle("Monthly Expenses"))
                                                                                                                 .add(table)
                                                                                                                 .build()
                                                                                                                 .execute();
    }
}
