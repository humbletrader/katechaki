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
package net.sf.reportengine.samples;

import java.io.FileWriter;
import java.io.IOException;

import net.sf.reportengine.ReportBuilder;
import net.sf.reportengine.components.EmptyLine;
import net.sf.reportengine.components.FlatTableBuilder;
import net.sf.reportengine.components.ReportTitle;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.in.InMemoryTableInput;
import net.sf.reportengine.out.HtmlReportOutput;

/**
 * @author dragos balan
 *
 */
public class MultipleReportComponents {

    public static void main(String[] args) throws IOException {
        new ReportBuilder(new HtmlReportOutput(new FileWriter("./target/MultipleComponents.html"))).add(new ReportTitle("this is the title of the report (a report component)"))
                                                                                                   .add(new FlatTableBuilder(new InMemoryTableInput(new String[][] { new String[] { "First name", "Last Name", "age" }, new String[] { "John", "Doe", "34" }, new String[] { "Jane", "Doe", "32" } })).addDataColumn(new DefaultDataColumn(0))
                                                                                                                                                                                                                                                                                                      .addDataColumn(new DefaultDataColumn(1))
                                                                                                                                                                                                                                                                                                      .addDataColumn(new DefaultDataColumn(2))
                                                                                                                                                                                                                                                                                                      .build())
                                                                                                   .add(new EmptyLine())
                                                                                                   .add(new ReportTitle("this is a paragraph (another report component)"))

                                                                                                   .build()
                                                                                                   .execute();
    }
}
