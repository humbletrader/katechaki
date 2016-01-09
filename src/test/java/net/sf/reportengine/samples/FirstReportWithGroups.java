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
 * The first report containing a group column. The month column is declared as a
 * group column so after each change of a month a total will be displayed on the
 * Amount column where the calculator has been added
 */
public class FirstReportWithGroups {

    public static void main(String[] args) throws IOException {

        // constructing a flat table with 3 columns: first is declared as a
        // group column
        // the third contains the group calculator (in this case an SUM)
        FlatTable flatTable =
            new FlatTableBuilder(new TextTableInput("./input/expenses.csv", ",")).addGroupColumn(new DefaultGroupColumn.Builder(0).header("Month")
                                                                                                                                  .horizAlign(HorizAlign.LEFT)
                                                                                                                                  .build())
                                                                                 .addDataColumn(new DefaultDataColumn.Builder(1).header("On What?")
                                                                                                                                .build())
                                                                                 .addDataColumn(new DefaultDataColumn.Builder(2).header("Amount")
                                                                                                                                .useCalculator(GroupCalculators.SUM)
                                                                                                                                .horizAlign(HorizAlign.RIGHT)
                                                                                                                                .build())
                                                                                 .build();

        // building and executing the report
        new ReportBuilder(new HtmlReportOutput(new FileWriter("./target/MonthlyExpensesUsingGroups.html"))).add(new ReportTitle("Monthly Expenses"))
                                                                                                           .add(flatTable)
                                                                                                           .build()
                                                                                                           .execute();
    }
}
