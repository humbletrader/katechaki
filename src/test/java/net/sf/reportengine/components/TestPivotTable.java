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
package net.sf.reportengine.components;

import java.io.FileWriter;
import java.io.IOException;

import junit.framework.TestCase;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.DefaultPivotData;
import net.sf.reportengine.config.DefaultPivotHeaderRow;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.core.calc.SumGroupCalculator;
import net.sf.reportengine.in.TextTableInput;
import net.sf.reportengine.out.AbstractReportOutput;
import net.sf.reportengine.out.HtmlReportOutput;
import net.sf.reportengine.scenarios.ct.CtScenario1x1x1;
import net.sf.reportengine.scenarios.ct.CtScenario1x3x1;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1With0G2D;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1With1G1D;
import net.sf.reportengine.scenarios.ct.CtScenarioFormatting4x3x1;
import net.sf.reportengine.scenarios.ct.CtUnsortedScenario2x2x1With0G2D;
import net.sf.reportengine.scenarios.ct.CtUnsortedScenario2x2x1With1G1D;
import net.sf.reportengine.scenarios.ct.CtUnsortedScenario2x2x1With2G0D;
import net.sf.reportengine.util.ReportIoUtils;

/**
 * @author dragos balan
 *
 */
public class TestPivotTable extends TestCase {

    public void testExecute1x1x1xT() throws IOException {

        AbstractReportOutput reportOutput =
            new HtmlReportOutput(new FileWriter("target/TestPivot1x1x1xT.html"));
        reportOutput.open();

        PivotTable classUnderTest =
            new PivotTableBuilder(CtScenario1x1x1.INPUT).groupColumns(CtScenario1x1x1.GROUP_COLUMNS)
                                                        .dataColumns(CtScenario1x1x1.DATA_COLUMNS)
                                                        .pivotData(CtScenario1x1x1.CROSSTAB_DATA_WITH_TOTALS)
                                                        .headerRows(CtScenario1x1x1.ROW_HEADERS)
                                                        .showTotals(true)
                                                        .showGrandTotal(true)
                                                        .build();
        classUnderTest.output(reportOutput);
        reportOutput.close();

    }

    public void testExecute1x1x1() throws IOException {
        AbstractReportOutput reportOutput =
            new HtmlReportOutput(new FileWriter("target/Pivot1x1x1.html"));
        reportOutput.open();

        new PivotTableBuilder(CtScenario1x1x1.INPUT).groupColumns(CtScenario1x1x1.GROUP_COLUMNS)
                                                    .dataColumns(CtScenario1x1x1.DATA_COLUMNS)
                                                    .pivotData(CtScenario1x1x1.CROSSTAB_DATA_NO_TOTALS)
                                                    .headerRows(CtScenario1x1x1.ROW_HEADERS)
                                                    .showTotals(false)
                                                    .showGrandTotal(false)
                                                    .build()
                                                    .output(reportOutput);

        reportOutput.close();

    }

    public void testExecute2x2x1xT() throws IOException {
        AbstractReportOutput reportOutput =
            new HtmlReportOutput(new FileWriter("target/Pivot2x2x1xT.html"));
        reportOutput.open();

        new PivotTableBuilder(CtScenario2x2x1With1G1D.INPUT).groupColumns(CtScenario2x2x1With1G1D.GROUPING_COLUMNS)
                                                            .dataColumns(CtScenario2x2x1With1G1D.DATA_COLUMNS)
                                                            .pivotData(CtScenario2x2x1With1G1D.CROSSTAB_DATA)
                                                            .headerRows(CtScenario2x2x1With1G1D.HEADER_ROWS)
                                                            .showTotals()
                                                            .showGrandTotal()
                                                            .build()
                                                            .output(reportOutput);

        reportOutput.close();
    }

    public void testExecute2x2x1() throws IOException {
        AbstractReportOutput reportOutput =
            new HtmlReportOutput(new FileWriter("./target/Pivot2x2x1.html"));
        reportOutput.open();

        new PivotTableBuilder(CtScenario2x2x1With1G1D.INPUT)

        .groupColumns(CtScenario2x2x1With1G1D.GROUPING_COLUMNS)
                                                            .dataColumns(CtScenario2x2x1With1G1D.DATA_COLUMNS)
                                                            .pivotData(CtScenario2x2x1With1G1D.CROSSTAB_DATA)
                                                            .headerRows(CtScenario2x2x1With1G1D.HEADER_ROWS)

                                                            .showTotals(false)
                                                            .showGrandTotal(false)
                                                            .build()
                                                            .output(reportOutput);

        reportOutput.close();
    }

    public void testProgramaticSorting2x2x1() throws IOException {
        AbstractReportOutput reportOutput =
            new HtmlReportOutput(new FileWriter("./target/PivotProgramaticallySorted2x2x1.html"));
        reportOutput.open();

        new PivotTableBuilder(CtUnsortedScenario2x2x1With1G1D.INPUT).sortValues()

                                                                    .groupColumns(CtUnsortedScenario2x2x1With1G1D.GROUPING_COLUMNS)
                                                                    .dataColumns(CtUnsortedScenario2x2x1With1G1D.DATA_COLUMNS)
                                                                    .pivotData(CtUnsortedScenario2x2x1With1G1D.CROSSTAB_DATA)
                                                                    .headerRows(CtUnsortedScenario2x2x1With1G1D.HEADER_ROWS)

                                                                    .showTotals(false)
                                                                    .showGrandTotal(false)
                                                                    .build()
                                                                    .output(reportOutput);

        reportOutput.close();
    }

    public void testExecute1x3x1xT() throws IOException {
        AbstractReportOutput reportOutput =
            new HtmlReportOutput(new FileWriter("target/Pivot1x3x1xT.html"));
        reportOutput.open();

        new PivotTableBuilder(CtScenario1x3x1.INPUT).groupColumns(CtScenario1x3x1.GROUP_COLUMNS)
                                                    .dataColumns(CtScenario1x3x1.DATA_COLUMNS)
                                                    .pivotData(CtScenario1x3x1.CROSSTAB_DATA)
                                                    .headerRows(CtScenario1x3x1.HEADER_ROWS)
                                                    .showTotals(true)
                                                    .showGrandTotal(true)
                                                    .build()
                                                    .output(reportOutput);

        reportOutput.close();
    }

    public void testExecute1x3x1() throws IOException {
        AbstractReportOutput reportOutput =
            new HtmlReportOutput(new FileWriter("target/Pivot1x3x1.html"));
        reportOutput.open();

        new PivotTableBuilder(CtScenario1x3x1.INPUT).groupColumns(CtScenario1x3x1.GROUP_COLUMNS)
                                                    .dataColumns(CtScenario1x3x1.DATA_COLUMNS)
                                                    .pivotData(CtScenario1x3x1.CROSSTAB_DATA)
                                                    .headerRows(CtScenario1x3x1.HEADER_ROWS)
                                                    .showTotals(false)
                                                    .showGrandTotal(false)
                                                    .build()
                                                    .output(reportOutput);

        reportOutput.close();
    }

    public void testExecute3x2x1xT() throws IOException {
        AbstractReportOutput reportOutput =
            new HtmlReportOutput(new FileWriter("target/Pivot3x2x1xT.html"));
        reportOutput.open();

        new PivotTableBuilder(new TextTableInput(ReportIoUtils.createInputStreamFromClassPath("3x2x1.txt"),
                                                 ",")).addGroupColumn(new DefaultGroupColumn.Builder(0).header("Country")
                                                                                                       .build())
                                                      .addGroupColumn(new DefaultGroupColumn.Builder(1).header("Region")
                                                                                                       .build())

                                                      .addDataColumn(new DefaultDataColumn("City",
                                                                                           2,
                                                                                           GroupCalculators.COUNT))

                                                      .addHeaderRow(new DefaultPivotHeaderRow(3))
                                                      .addHeaderRow(new DefaultPivotHeaderRow(4))

                                                      .pivotData(new DefaultPivotData(5,
                                                                                      GroupCalculators.SUM))

                                                      .showTotals(true)
                                                      .showGrandTotal(true)
                                                      .build()
                                                      .output(reportOutput);

        reportOutput.close();
    }

    // TODO
    public void testExecute3x2x1xTHavingNoGroupColumns() throws IOException {
        AbstractReportOutput reportOutput =
            new HtmlReportOutput(new FileWriter("target/Pivot3x2x1xTxNoGroupColumns.html"));
        reportOutput.open();

        new PivotTableBuilder(new TextTableInput(ReportIoUtils.createInputStreamFromClassPath("3x2x1.txt"),
                                                 ","))

        .addDataColumn(new DefaultDataColumn("Country", 0))
        .addDataColumn(new DefaultDataColumn("Region", 1))
        .addDataColumn(new DefaultDataColumn("City", 2))

        .addHeaderRow(new DefaultPivotHeaderRow(3))
        .addHeaderRow(new DefaultPivotHeaderRow(4))
        
        .pivotData(new DefaultPivotData(5, new SumGroupCalculator()))

        .showTotals(false)
        .showGrandTotal(false)
        .build()
        .output(reportOutput);

        reportOutput.close();
    }

    public void testExecute2x2x1xTHavingNoGroupColumns() throws IOException {
        AbstractReportOutput reportOutput =
            new HtmlReportOutput(new FileWriter("target/Pivot2x2x1With0G2D.html"));
        reportOutput.open();

        new PivotTableBuilder(CtScenario2x2x1With0G2D.INPUT).dataColumns(CtScenario2x2x1With0G2D.DATA_COLUMNS)
                                                            .headerRows(CtScenario2x2x1With0G2D.HEADER_ROWS)
                                                            .pivotData(CtScenario2x2x1With0G2D.CROSSTAB_DATA)
                                                            .showTotals(false)
                                                            .showGrandTotal(false)
                                                            .build()
                                                            .output(reportOutput);

        reportOutput.close();
    }

    public void testProgramaticSortingFor2x2x1xTHavingNoGroups() throws IOException {
        AbstractReportOutput reportOutput =
            new HtmlReportOutput(new FileWriter("target/PivotProgramaticallySorted2x2x1With0G2D.html"));
        reportOutput.open();

        new PivotTableBuilder(CtUnsortedScenario2x2x1With0G2D.INPUT).dataColumns(CtUnsortedScenario2x2x1With0G2D.DATA_COLUMNS)
                                                                    .headerRows(CtUnsortedScenario2x2x1With0G2D.HEADER_ROWS)
                                                                    .pivotData(CtUnsortedScenario2x2x1With0G2D.CROSSTAB_DATA)
                                                                    .showTotals(false)
                                                                    .showGrandTotal(false)
                                                                    .build()
                                                                    .output(reportOutput);

        reportOutput.close();
    }

    public void testProgramaticSortingFor2x2x1xTHavingNoDataCols() throws IOException {
        AbstractReportOutput reportOutput =
            new HtmlReportOutput(new FileWriter("target/PivotProgramaticallySorted2x2x1With2G0D.html"));
        reportOutput.open();

        new PivotTableBuilder(CtUnsortedScenario2x2x1With2G0D.INPUT).groupColumns(CtUnsortedScenario2x2x1With2G0D.GROUPING_COLUMNS)
                                                                    // /*dataCols
                                                                    // is null*/
                                                                    .headerRows(CtUnsortedScenario2x2x1With2G0D.HEADER_ROWS)
                                                                    .pivotData(CtUnsortedScenario2x2x1With2G0D.CROSSTAB_DATA)
                                                                    // .showTotals(true)
                                                                    // .showGrandTotal(true)
                                                                    .sortValues()
                                                                    .build()
                                                                    .output(reportOutput);

        reportOutput.close();
    }

    public void testFormatting() throws IOException {
        AbstractReportOutput reportOutput =
            new HtmlReportOutput(new FileWriter("./target/PivotFormatting.html"));
        reportOutput.open();

        new PivotTableBuilder(CtScenarioFormatting4x3x1.INPUT).dataColumns(CtScenarioFormatting4x3x1.DATA_COLUMNS)
                                                              .groupColumns(CtScenarioFormatting4x3x1.GROUP_COLUMNS)
                                                              .headerRows(CtScenarioFormatting4x3x1.HEADER_ROWS)
                                                              .pivotData(CtScenarioFormatting4x3x1.CROSSTAB_DATA)
                                                              .showTotals()
                                                              .showGrandTotal()
                                                              .build()
                                                              .output(reportOutput);

        reportOutput.close();
    }

    // public void testWrongConfiguration1(){
    // try{
    // CrossTabReport classUnderTest = new CrossTabReport();
    // //don't set any input nor output
    // classUnderTest.execute();
    // fail("this test should have thrown an exception in the method above");
    // }catch(ConfigValidationException configExc){
    // assertEquals(configExc.getMessage(), "The report has no input");
    // }catch(Throwable other){
    // fail("Expected config validation exception but found "+other.getClass());
    // }
    // }
    //
    // public void testWrongConfiguration2(){
    // try{
    // CrossTabReport classUnderTest = new CrossTabReport();
    // classUnderTest.setIn(CtScenario1x3x1.INPUT);
    // //don't set any output
    //
    // classUnderTest.execute();
    // fail("this test should have thrown an exception in the method above");
    // }catch(ConfigValidationException configExc){
    // assertEquals(configExc.getMessage(), "The report has no output");
    // }catch(Throwable other){
    // fail("Expected config validation exception but found "+other.getClass());
    // }
    // }
}
