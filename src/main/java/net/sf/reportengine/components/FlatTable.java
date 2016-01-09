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

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.out.ReportOutput;

/**
 * <p>This is a normal tabular report (don't get confused by its name)</p> 
 * <p> The layout of a flat table looks like:
 * <table border="1">
 * <tr>
 *      <td><b>Column 1</b></td>
 *      <td><b>Column 2</b></td>
 *      <td><b>column 3</b></td>
 *      <td><b>column 4</b></td>
 *      <td><b>column 5</b></td>
 * </tr>
 * <tr>
 *      <td>data 11</td>
 *      <td>data 12</td>
 *      <td>data 13</td>
 *      <td>data 14</td>
 *      <td>data 15</td>
 * </tr>
 * <tr>
 *      <td>data 21</td>
 *      <td>data 22</td>
 *      <td>data 23</td>
 *      <td>data 24</td>
 *      <td>data 25</td>
 * <tr>
 * <tr>
 *      <td>data 31</td>
 *      <td>data 32</td>
 *      <td>data 33</td>
 *      <td>data 34</td>
 *      <td>data 35</td>
 * <tr>
 * </table>
 *</p>
 *<p>
 * The mandatory settings of a flat table are:
 * <ol>
 *  <li>data columns</li>
 *  <li>table input</li>
 * </ol>
 * </p>
 * <p>
 * The optional settings are: 
 * <ol>
 *  <li>group columns</li>
 *  <li>sorting values</li>
 *  <li>showing data</li>
 *  <li>showing totals</li>
 *  <li>showing grand total</li>
 * </ol>
 * </p>
 * <p>
 * Usually the flat table is used as a component of a report:
 * <pre>
 * {@code
 * FlatTable flatTable = new FlatTableBuilder()
 *    .input(new TextInput("./inputData/population.csv",","))
 *    .addDataColumn(new DefaultDataColumn.Builder(0).header("Country").build())
 *    .addDataColumn(new DefaultDataColumn.Builder(1).header("City").build())
 *    .addDataColumn(new DefaultDataColumn.Builder(2).header("Population").build())
 *    .build();
 *    
 * Report report = new ReportBuilder(new DefaultReportOutput(new FileWriter("/tmp/report.html")));
 * report.add(flatTable); 
 * report.execute(); 
 *}
 *</pre> 
 * but it can be used also as a stand alone component
 * 
 * <pre>
 *  {@code
 *  FlatTable flatTable = new FlatTableBuilder()
 *     .input(new TextInput("./inputData/population.csv",","))
 *     .addDataColumn(new DefaultDataColumn.Builder(0).header("Country").build())
 *     .addDataColumn(new DefaultDataColumn.Builder(1).header("City").build())
 *     .addDataColumn(new DefaultDataColumn.Builder(2).header("Population").build())
 *     .build();
 *     
 *  ReportOutput reportOutput = new DefaultReportOutput(new FileWriter("/tmp/testFlatTable.html"))
 *  reportOutput.open(); 
 *  flatTable.output(reportOutput); 
 *  reportOuptut.close(); 
 *  }
 * </pre>
 * </p>
 * 
 * @see TableInput
 * @see ReportOutput
 * @see DataColumn
 * @see GroupColumn
 * 
 * @author dragos balan
 */
public interface FlatTable extends ReportComponent {

}
