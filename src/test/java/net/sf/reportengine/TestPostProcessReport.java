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
package net.sf.reportengine;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import net.sf.reportengine.components.EmptyLine;
import net.sf.reportengine.components.FlatTable;
import net.sf.reportengine.components.FlatTableBuilder;
import net.sf.reportengine.components.PivotTable;
import net.sf.reportengine.components.PivotTableBuilder;
import net.sf.reportengine.components.ReportTitle;
import net.sf.reportengine.out.PageSize;
import net.sf.reportengine.out.PdfOutputFormat;
import net.sf.reportengine.out.PdfReportOutput;
import net.sf.reportengine.out.PngReportOutput;
import net.sf.reportengine.out.Status;
import net.sf.reportengine.out.TiffReportOutput;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.scenarios.ScenarioFormatedValues;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1With1G1D;
import net.sf.reportengine.scenarios.ct.CtScenarioFormatting4x3x1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestPostProcessReport {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testTwoComponentsAndPdfOutput() throws IOException {
        new ReportBuilder(new PdfReportOutput(new FileOutputStream("./target/TestTwoComponents.pdf"))).add(new ReportTitle("this is the report title "))
                                                                                                      .add(new EmptyLine())
                                                                                                      .add(new FlatTableBuilder(Scenario1.INPUT).dataColumns(Scenario1.DATA_COLUMNS)
                                                                                                                                                .build())
                                                                                                      .build()
                                                                                                      .execute();
    }

    @Test
    public void testTwoComponentsAndPngOutput() throws IOException {
        new ReportBuilder(new PngReportOutput(new FileOutputStream("./target/TestTwoComponents.png"))).add(new ReportTitle("this is the report title "))
                                                                                                      .add(new EmptyLine())
                                                                                                      .add(new FlatTableBuilder(Scenario1.INPUT).dataColumns(Scenario1.DATA_COLUMNS)
                                                                                                                                                .build())
                                                                                                      .build()
                                                                                                      .execute();
    }

    @Test
    public void testMultipleTablesInSameReport() throws IOException {

        FlatTable flatTable1 =
            new FlatTableBuilder(Scenario1.INPUT).dataColumns(Scenario1.DATA_COLUMNS).build();
        FlatTable flatTable2 =
            new FlatTableBuilder(Scenario1.INPUT).dataColumns(Scenario1.DATA_COLUMNS).build();
        PivotTable pivotTable1 =
            new PivotTableBuilder(CtScenario2x2x1With1G1D.INPUT).groupColumns(CtScenario2x2x1With1G1D.GROUPING_COLUMNS)
                                                                .dataColumns(CtScenario2x2x1With1G1D.DATA_COLUMNS)
                                                                .pivotData(CtScenario2x2x1With1G1D.CROSSTAB_DATA)
                                                                .headerRows(CtScenario2x2x1With1G1D.HEADER_ROWS)
                                                                .showTotals()
                                                                .showGrandTotal()
                                                                .build();
        PivotTable pivotTable2 =
            new PivotTableBuilder(CtScenarioFormatting4x3x1.INPUT).dataColumns(CtScenarioFormatting4x3x1.DATA_COLUMNS)
                                                                  .groupColumns(CtScenarioFormatting4x3x1.GROUP_COLUMNS)
                                                                  .headerRows(CtScenarioFormatting4x3x1.HEADER_ROWS)
                                                                  .pivotData(CtScenarioFormatting4x3x1.CROSSTAB_DATA)
                                                                  .showTotals()
                                                                  .showGrandTotal()
                                                                  .build();

        Report report =
            new ReportBuilder(new PdfReportOutput(new FileOutputStream("./target/ReportWithMultipleTables.pdf"),
                                                  new PdfOutputFormat(PageSize.A1_LANDSCAPE))).add(new ReportTitle("this report contains multiple tables"))
                                                                                              .add(flatTable1)
                                                                                              .add(new EmptyLine())
                                                                                              .add(flatTable2)
                                                                                              .add(new EmptyLine())
                                                                                              .add(pivotTable1)
                                                                                              .add(new EmptyLine())
                                                                                              .add(pivotTable2)
                                                                                              .build();
        report.execute();
    }

    @Ignore
    public void testMemoryLeaksOutputPdf() throws IOException {
        ReportBuilder reportBuilder =
            new ReportBuilder(new PdfReportOutput(new FileOutputStream("./target/TestMemoryLeaks.pdf")));

        reportBuilder.add(new ReportTitle("Testing the Pdf output for memory leaks"));
        // add 1000 flat tables
        for (int i = 0; i < 1000; i++) {

            reportBuilder
                         .add(new FlatTableBuilder(ScenarioFormatedValues.INPUT).showTotals(true)
                                                                                .showGrandTotal(true)
                                                                                .groupColumns(ScenarioFormatedValues.GROUP_COLUMNS)
                                                                                .dataColumns(ScenarioFormatedValues.DATA_COLUMNS)
                                                                                .build());
            reportBuilder.add(new EmptyLine());
        }

        reportBuilder.build().execute();
    }

    @Ignore
    public void testMemoryLeaksOutputPng() throws IOException {
        ReportBuilder reportBuilder =
            new ReportBuilder(new PngReportOutput("./target/TestMemoryLeaks.png"));

        reportBuilder.add(new ReportTitle("Testing the png output for memory leaks"));
        // add 1000 flat tables
        for (int i = 0; i < 1000; i++) {

            reportBuilder
                         .add(new FlatTableBuilder(ScenarioFormatedValues.INPUT).showTotals(true)
                                                                                .showGrandTotal(true)
                                                                                .groupColumns(ScenarioFormatedValues.GROUP_COLUMNS)
                                                                                .dataColumns(ScenarioFormatedValues.DATA_COLUMNS)
                                                                                .build());
            reportBuilder.add(new EmptyLine());
        }

        reportBuilder.build().execute();
    }

    @Ignore
    public void testMemoryLeaksOutputTiff() throws IOException {
        ReportBuilder reportBuilder =
            new ReportBuilder(new TiffReportOutput(new FileOutputStream("./target/TestMemoryLeaks.tiff")));

        reportBuilder.add(new ReportTitle("Testing the tiff output for memory leaks"));
        // add 1000 flat tables
        for (int i = 0; i < 1000; i++) {

            reportBuilder
                         .add(new FlatTableBuilder(ScenarioFormatedValues.INPUT).showTotals(true)
                                                                                .showGrandTotal(true)
                                                                                .groupColumns(ScenarioFormatedValues.GROUP_COLUMNS)
                                                                                .dataColumns(ScenarioFormatedValues.DATA_COLUMNS)
                                                                                .build());
            reportBuilder.add(new EmptyLine());
        }

        reportBuilder.build().execute();
    }

    @Test
    public void testOutputOpenInCaseOfError() throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PdfReportOutput pdfOutput = new PdfReportOutput(outStream);
        try {
            new ReportBuilder(pdfOutput).add(new ReportTitle("test output open"))
                                        .add(new FlatTableBuilder(null).build())
                                        .build()
                                        .execute();
            // TODO: fail here
        } catch (Throwable exc) {
            // expected
        }

        Assert.assertTrue(Status.CLOSED.equals(pdfOutput.getStatus()));
    }
}
