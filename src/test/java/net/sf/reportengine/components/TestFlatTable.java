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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.sql.SQLException;

import static org.apache.commons.io.FileUtils.contentEquals;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.config.VertAlign;
import net.sf.reportengine.core.calc.SumGroupCalculator;
import net.sf.reportengine.in.SqlTableInput;
import net.sf.reportengine.in.TableInputException;
import net.sf.reportengine.in.TextTableInput;
import net.sf.reportengine.out.AbstractReportOutput;
import net.sf.reportengine.out.HtmlReportOutput;
import net.sf.reportengine.scenarios.NoGroupsScenario;
import net.sf.reportengine.scenarios.OhlcComputationScenario;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.scenarios.Scenario2x3x1;
import net.sf.reportengine.scenarios.ScenarioFormatedValues;
import net.sf.reportengine.scenarios.ScenarioSort;
import net.sf.reportengine.scenarios.SortScenarioOnlyDataColsCount;
import net.sf.reportengine.util.ReportIoUtils;

public class TestFlatTable {
    
    /**
     * this report has no grouping columns but it has a grand total at the end
     * because the data columns have group calculators added
     * 
     * @throws IOException
     */
    @Test
    public void testExecuteReportWithoutGroupColumns() throws IOException {
        String CURRENT_TEST_OUTPUT_PATH = "./target/FlatTableWithoutGroupColumns.html"; 
        String EXPECTED_RESULT_PATH = "./expected/FlatTableWithoutGroupColumns.html"; 
        
        FlatTable flatTable = new FlatTableBuilder(Scenario1.INPUT)
                               .dataColumns(Scenario1.DATA_COLUMNS)
                               .build();

        // test the computed values
        assertTrue(flatTable instanceof DefaultFlatTable);
        assertTrue(((DefaultFlatTable) flatTable).getShowDataRows());
        assertTrue(((DefaultFlatTable) flatTable).getShowTotals());
        assertTrue(((DefaultFlatTable) flatTable).getShowGrandTotal());

        //output the table in Html on disk
        AbstractReportOutput htmlDiskOutput = new HtmlReportOutput(new FileWriter(CURRENT_TEST_OUTPUT_PATH));
        htmlDiskOutput.open();
        flatTable.output(htmlDiskOutput);
        htmlDiskOutput.close();
        
        //check if the current output is equal to the expected output
        assertTrue(contentEquals(new File(CURRENT_TEST_OUTPUT_PATH), 
                                 new File(EXPECTED_RESULT_PATH)));  
    }
    
    /**
     * test of a simple report with 3 grouping columns and 3 data columns
     * the grouping columns don't require any sorting
     * 
     * @throws IOException
     */
    @Test
    public void testExecuteScenario1() throws IOException {
        String CURRENT_TEST_OUTPUT_PATH = "./target/FlatTableScenario1.html"; 
        String EXPECTED_RESULT_PATH = "./expected/FlatTableScenario1.html";
        
        AbstractReportOutput reportOutput = 
            new HtmlReportOutput(new FileWriter(CURRENT_TEST_OUTPUT_PATH));
        reportOutput.open();
        FlatTable flatTable =
            new FlatTableBuilder(Scenario1.INPUT).dataColumns(Scenario1.DATA_COLUMNS)
                                                 .groupColumns(Scenario1.GROUPING_COLUMNS)
                                                 .build();

        // check the default values
        assertTrue(flatTable instanceof DefaultFlatTable);
        assertTrue(((DefaultFlatTable) flatTable).getShowDataRows());
        assertTrue(((DefaultFlatTable) flatTable).getShowTotals());
        assertTrue(((DefaultFlatTable) flatTable).getShowGrandTotal());

        // execute the report
        flatTable.output(reportOutput);
        reportOutput.close();

        assertTrue(contentEquals(new File(EXPECTED_RESULT_PATH), 
                                 new File(CURRENT_TEST_OUTPUT_PATH)));

    }

    @Test
    public void testExecuteScenarioOhlc() throws IOException {
        AbstractReportOutput reportOutput =
            new HtmlReportOutput(new FileWriter("./target/TestFlatTableOHLC.html"));
        reportOutput.open();

        InputStream testStream =
            ReportIoUtils.createInputStreamFromClassPath("EURUSD_2007-2009_FirstHours.txt");
        assertNotNull(testStream);

        new FlatTableBuilder(new TextTableInput(testStream, "\t")).dataColumns(OhlcComputationScenario.DATA_COLUMNS)
                                                                  .groupColumns(OhlcComputationScenario.GROUPING_COLUMNS)
                                                                  .showTotals(true)
                                                                  .showDataRows(false)
                                                                  .build()
                                                                  .output(reportOutput);

        reportOutput.close();
        // TODO: test the final output here
    }

    @Test
    public void testExecute2x3x1() throws IOException {
        AbstractReportOutput reportOutput =
            new HtmlReportOutput(new FileWriter("./target/TestFlatTable2x3x1.html"));
        reportOutput.open();

        InputStream inputStream = ReportIoUtils.createInputStreamFromClassPath("2x3x1.txt");
        assertNotNull(inputStream);

        new FlatTableBuilder(new TextTableInput(inputStream)).groupColumns(Scenario2x3x1.GROUP_COLUMNS)
                                                             .dataColumns(Scenario2x3x1.DATA_COLUMNS)
                                                             .showTotals(true)
                                                             .build()
                                                             .output(reportOutput);

        reportOutput.close();

        // TODO: assert here
    }

    @Test
    public void testExecuteNoGroupingColumnsReport() throws IOException {
        AbstractReportOutput reportOutput =
            new HtmlReportOutput(new FileWriter("./target/TestFlatTableNoGroupings.html"));
        reportOutput.open();

        new FlatTableBuilder(NoGroupsScenario.INPUT).groupColumns(NoGroupsScenario.GROUPING_COLUMNS)
                                                    .dataColumns(NoGroupsScenario.DATA_COLUMNS)
                                                    .showTotals(true)
                                                    .showGrandTotal(true)
                                                    .build()
                                                    .output(reportOutput);

        reportOutput.close();
        // TODO: assert here
    }

    @Test
    public void testFlatTableUtf8PdfOutput() throws IOException {
        Writer testWriter = ReportIoUtils.createWriterFromPath("./target/TestFlatTableUtf8Output.html");
        testWriter.write("<head><meta charset=\"UTF-8\"></head><body>");

        AbstractReportOutput reportOutput = new HtmlReportOutput(testWriter, false);
        reportOutput.open();

        InputStream inputStream = ReportIoUtils.createInputStreamFromClassPath("Utf8Input.txt");
        assertNotNull(inputStream);

        new FlatTableBuilder(new TextTableInput(inputStream, ",", "UTF-8"))
            .addDataColumn(new DefaultDataColumn(0))
            .addDataColumn(new DefaultDataColumn(1))
            .addDataColumn(new DefaultDataColumn(2))
            .addDataColumn(new DefaultDataColumn(3))
            .build()
            .output(reportOutput);

        reportOutput.close();

        testWriter.write("</body>");
        // TODO: assert here
    }

    @Test
    public void testHugeInputHtmlOut() throws IOException {
        AbstractReportOutput reportOutput =
            new HtmlReportOutput(new FileWriter("./target/TestHugeFlatTable.html"));
        reportOutput.open();

        new FlatTableBuilder(OhlcComputationScenario.INPUT)
            .groupColumns(OhlcComputationScenario.GROUPING_COLUMNS)
            .dataColumns(OhlcComputationScenario.DATA_COLUMNS)
            .build()
            .output(reportOutput);

        reportOutput.close();
        // TODO: assert here
    }

    @Test
    public void testFlatReportWithFormattedValues() throws IOException {
        AbstractReportOutput reportOutput =
            new HtmlReportOutput(new FileWriter("./target/TestFlatTableFormattedValues.html"));
        reportOutput.open();

        new FlatTableBuilder(ScenarioFormatedValues.INPUT)
            .showTotals(true)
            .showGrandTotal(true)
            .groupColumns(ScenarioFormatedValues.GROUP_COLUMNS)
            .dataColumns(ScenarioFormatedValues.DATA_COLUMNS)
            .build()
            .output(reportOutput);

        reportOutput.close();
        // TODO: assert here
    }

    @Test
    public void testExecuteWithSorting() throws IOException {
        AbstractReportOutput reportOutput =
            new HtmlReportOutput(new FileWriter("./target/FlatTableSortedProgramatically.html"));
        reportOutput.open();

        new FlatTableBuilder(ScenarioSort.INPUT)
            .sortValues()
            .dataColumns(ScenarioSort.DATA_COLUMNS)
            .groupColumns(ScenarioSort.GROUPING_COLUMNS)
            .showTotals(false)
            .showDataRows(true)
            .showGrandTotal(true)
            .build()
            .output(reportOutput);

        // Assert.assertTrue(MatrixUtils.compareMatrices(
        // mockOut.getDataCellMatrix(),
        // ScenarioSort.EXPECTED_OUTPUT_SORTED));

        reportOutput.close();
    }

    @Test
    public void testExecuteWithSortingOnDataCols() throws IOException {
        AbstractReportOutput reportOutput =
            new HtmlReportOutput(new FileWriter("./target/TestFlatTableSortingOnDataCols.html"));
        reportOutput.open();

        FlatTable flatTable = new FlatTableBuilder(SortScenarioOnlyDataColsCount.INPUT)
                .showTotals(false)
                .dataColumns(SortScenarioOnlyDataColsCount.DATA_COLUMNS)
                .groupColumns(SortScenarioOnlyDataColsCount.GROUPING_COLUMNS)
                .build();

        // check some of the computed values + default ones
        assertTrue(flatTable instanceof DefaultFlatTable);
        assertTrue(((DefaultFlatTable) flatTable).getShowDataRows());
        assertFalse(((DefaultFlatTable) flatTable).getShowTotals());
        assertTrue(((DefaultFlatTable) flatTable).getShowGrandTotal());
        assertTrue(((DefaultFlatTable) flatTable).hasValuesSorted());

        flatTable.output(reportOutput);

        reportOutput.close();
    }

    @Test
    public void testSqlInput() throws IOException {
        AbstractReportOutput reportOutput =
            new HtmlReportOutput(new FileWriter("./target/TestFlatTableWithSqlInput.html"));
        reportOutput.open();

        SqlTableInput input =
            new SqlTableInput("jdbc:hsqldb:file:./src/test/resources/databases/testdb",
                              "org.hsqldb.jdbcDriver",
                              "SA",
                              "",
                              "select country, region, city, sex, religion, value from testreport t order by country, region, city");

        new FlatTableBuilder(input).addGroupColumn(new DefaultGroupColumn.Builder(0).header("COUNTRY")
                                                                                    .horizAlign(HorizAlign.RIGHT)
                                                                                    .vertAlign(VertAlign.TOP)
                                                                                    .build())
                                   .addGroupColumn(new DefaultGroupColumn.Builder(1).header("REGION")
                                                                                    .build())
                                   .addGroupColumn(new DefaultGroupColumn.Builder(2).header("CITY")
                                                                                    .build())
                                   .addDataColumn(new DefaultDataColumn.Builder(5).header("Value")
                                                                                  .useCalculator(new SumGroupCalculator(),
                                                                                                 "%f $")
                                                                                  .valuesFormatter("%d $")
                                                                                  .build())
                                   .build()
                                   .output(reportOutput);

        reportOutput.close();

    }

    @Test
    public void testSqlException() throws Exception {
        AbstractReportOutput reportOutput =
            new HtmlReportOutput(new FileWriter("./target/TestSqlException.html"));
        reportOutput.open();

        SqlTableInput input =
            new SqlTableInput("jdbc:hsqldb:file:./src/test/resources/databases/testdb",
                              "org.hsqldb.jdbcDriver",
                              "SA",
                              "",
                              "select WRONG_COLUMN, region, city, sex, religion, value from testreport t order by country, region, city");
        try {
            new FlatTableBuilder(input).addGroupColumn(new DefaultGroupColumn.Builder(0).header("COUNTRY")
                                                                                        .horizAlign(HorizAlign.RIGHT)
                                                                                        .vertAlign(VertAlign.TOP)
                                                                                        .build())
                                       .addGroupColumn(new DefaultGroupColumn.Builder(1).header("REGION")
                                                                                        .build())
                                       .addGroupColumn(new DefaultGroupColumn.Builder(2).header("CITY")
                                                                                        .build())
                                       .addDataColumn(new DefaultDataColumn.Builder(5).header("Value")
                                                                                      .useCalculator(new SumGroupCalculator(),
                                                                                                     "%f $")
                                                                                      .valuesFormatter("%d $")
                                                                                      .build())
                                       .build()
                                       .output(reportOutput);
        } catch (TableInputException tie) {
            assertTrue(tie.getCause() instanceof SQLException);
        } finally {
            reportOutput.close();
        }
        assertTrue(input.hasAllResourcesClosed());
    }
    
    
    @Ignore("not ready yet. this is just a copy of the sorting example")
    public void deleteTemporaryFilesNeededForSorting() throws IOException {
        AbstractReportOutput reportOutput = new HtmlReportOutput(new FileWriter("./target/DeleteTemporaryFilesNeededForSorting.html"));
        reportOutput.open();

        new FlatTableBuilder(ScenarioSort.INPUT)
            .sortValues()
            .dataColumns(ScenarioSort.DATA_COLUMNS)
            .groupColumns(ScenarioSort.GROUPING_COLUMNS)
            .showTotals(false)
            .showDataRows(true)
            .showGrandTotal(true)
            .build()
            .output(reportOutput);

        // Assert.assertTrue(MatrixUtils.compareMatrices(
        // mockOut.getDataCellMatrix(),
        // ScenarioSort.EXPECTED_OUTPUT_SORTED));

        reportOutput.close();
    }

}
