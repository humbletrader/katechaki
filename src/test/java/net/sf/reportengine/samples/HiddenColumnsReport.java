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
import net.sf.reportengine.in.TextTableInput;
import net.sf.reportengine.out.HtmlReportOutput;

/**
 * This report shows how you can display only some columns. The input names.txt
 * contains 4 columns but the final report will contain only 2 of them
 * 
 * @author dragos balan
 *
 */
public class HiddenColumnsReport {

    public static void main(String[] args) throws IOException {

        // this input has more than two columns
        FlatTable table = new FlatTableBuilder(new TextTableInput("./input/names.txt", "\t"))
        // even though the input has more than two columns we only declare
        // two of them: the second (index 1) and the fourth (index 3)
            .addDataColumn(new DefaultDataColumn.Builder(1).header("FirstName").build())
            .addDataColumn(new DefaultDataColumn.Builder(3).header("LastName").build())
            .build();

        new ReportBuilder(new HtmlReportOutput(new FileWriter("./target/hiddenCols.html")))
            .add(new ReportTitle("This report shows only 2 out of 4 columns"))
            .add(table)
            .build()
        .execute();
    }
}
