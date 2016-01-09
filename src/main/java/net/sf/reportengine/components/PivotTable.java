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
import net.sf.reportengine.config.PivotData;
import net.sf.reportengine.config.PivotHeaderRow;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.out.ReportOutput;

/**
 * <p>Pivot Table interface</p>
 * 
 * <p>
 * A pivot table has a layout like:
 * <table border="1">
 *  <tr>
 *      <td colspan="1" rowspan="1" ></td>
 *      <td colspan="1" rowspan="1" ></td>
 *      <td colspan="3" rowspan="1" align="center">Males</td>
 *      <td colspan="3" rowspan="1" align="center">Females</td>
 *  </tr>
 *  <tr>
 *      <td colspan="1" rowspan="1" >Region</td>
 *      <td colspan="1" rowspan="1" >Country</td>
 *      <td colspan="1" rowspan="1" >Elephants</td>
 *      <td colspan="1" rowspan="1" >Rhinos</td>
 *      <td colspan="1" rowspan="1" >Squirrels</td>
 *      <td colspan="1" rowspan="1" >Elephants</td>
 *      <td colspan="1" rowspan="1" >Rhinos</td>
 *      <td colspan="1" rowspan="1" >Squirrels</td>
 * </tr>
 * <tr>
 *      <td colspan="1" rowspan="1" >North</td>
 *      <td colspan="1" rowspan="1" >Sweden</td>
 *      <td colspan="1" rowspan="1" >1000</td>
 *      <td colspan="1" rowspan="1" >10</td>
 *      <td colspan="1" rowspan="1" >4</td>
 *      <td colspan="1" rowspan="1" >1</td>
 *      <td colspan="1" rowspan="1" >0</td>
 *      <td colspan="1" rowspan="1" >0</td>
 * </tr>
 * <tr>
 *      <td colspan="1" rowspan="1" ></td>
 *      <td colspan="1" rowspan="1" >Norway</td>
 *      <td colspan="1" rowspan="1" >0</td>
 *      <td colspan="1" rowspan="1" >100</td>
 *      <td colspan="1" rowspan="1" >0</td>
 *      <td colspan="1" rowspan="1" >0</td>
 *      <td colspan="1" rowspan="1" >0</td>
 *      <td colspan="1" rowspan="1" >0</td>
 * </tr>
 * <tr>
 *      <td colspan="1" rowspan="1" >South</td>
 *      <td colspan="1" rowspan="1" >Italy</td>
 *      <td colspan="1" rowspan="1" >2000</td>
 *      <td colspan="1" rowspan="1" >0</td>
 *      <td colspan="1" rowspan="1" >0</td>
 *      <td colspan="1" rowspan="1" >0</td>
 *      <td colspan="1" rowspan="1" >0</td>
 *      <td colspan="1" rowspan="1" >0</td>
 * </tr>
 * <tr>
 *      <td colspan="1" rowspan="1" >East</td>
 *      <td colspan="1" rowspan="1" >Romania</td>
 *      <td colspan="1" rowspan="1" >0</td>
 *      <td colspan="1" rowspan="1" >0</td>
 *      <td colspan="1" rowspan="1" >0</td>
 *      <td colspan="1" rowspan="1" >0</td>
 *      <td colspan="1" rowspan="1" >200</td>
 *      <td colspan="1" rowspan="1" >0</td>
 * </tr>
 * <tr>
 *      <td colspan="1" rowspan="1" >West</td>
 *      <td colspan="1" rowspan="1" >France</td>
 *      <td colspan="1" rowspan="1" >300</td>
 *      <td colspan="1" rowspan="1" >0</td>
 *      <td colspan="1" rowspan="1" >3000</td>
 *      <td colspan="1" rowspan="1" >30</td>
 *      <td colspan="1" rowspan="1" >0</td>
 *      <td colspan="1" rowspan="1" >0</td>
 * </tr>
 * </table>
 * </p>
 * <p>
 * The pivot table accepts all settings of a {@link FlatTable} (input,  data columns, group columns, sorting values) and it introduces 
 * two more mandatory settings: .
 * <ol>
 *      <li>the header rows</li>
 *      <li>the pivot data</li>
 * </ol>        
 * </p>
 * <p>
 * The typical usage is: 
 * <pre>
 *       PivotTable table = new PivotTableBuilder(new TextTableInput("./input/expenses.csv", ","))
 *                      .addDataColumn(new DefaultDataColumn("Month",0))
 *                      .addHeaderRow(new DefaultPivotHeaderRow(1))
 *                      .pivotData(new DefaultPivotData(2))
 *                      .build();
 *       Report report = new ReportBuilder(new DefaultReportOutput(new FileWriter("/tmp/report.html")));
 *       report.add(table); 
 *       report.execute(); 
 * </pre> 
 * but the pivot table can be also used without a report: 
 * <pre>
 *       PivotTable table = new PivotTableBuilder(new TextTableInput("./input/expenses.csv", ","))
 *                      .addDataColumn(new DefaultDataColumn("Month",0))
 *                      .addHeaderRow(new DefaultPivotHeaderRow(1))
 *                      .pivotData(new DefaultPivotData(2))
 *                      .build();
 *                      
 *      ReportOutput reportOutput = new DefaultReportOutput(new FileWriter("/tmp/testFlatTable.html"))
 *      reportOutput.open(); 
 *      flatTable.output(table); 
 *      reportOuptut.close(); 
 * </pre>
 * </p>
 * @see PivotTableBuilder
 * @see TableInput
 * @see DataColumn
 * @see GroupColumn
 * @see PivotData
 * @see PivotHeaderRow 
 * @see ReportOutput
 * 
 * @author dragos balan
 * @since 0.13
 */
public interface PivotTable extends ReportComponent {

}
