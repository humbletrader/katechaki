package net.sf.reportengine;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.lang.SystemUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import net.sf.reportengine.components.EmptyLine;
import net.sf.reportengine.components.FlatTable;
import net.sf.reportengine.components.FlatTableBuilder;
import net.sf.reportengine.components.Paragraph;
import net.sf.reportengine.components.PivotTable;
import net.sf.reportengine.components.PivotTableBuilder;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.out.ExcelXmlReportOutput;
import net.sf.reportengine.out.FoOutputFormat;
import net.sf.reportengine.out.FoReportOutput;
import net.sf.reportengine.out.HtmlReportOutput;
import net.sf.reportengine.out.MockReportOutput;
import net.sf.reportengine.out.PageSize;
import net.sf.reportengine.out.Status;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.scenarios.ScenarioFormatedValues;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1With1G1D;
import net.sf.reportengine.scenarios.ct.CtScenarioFormatting4x3x1;

import static net.sf.reportengine.util.ReportIoUtils.FM_TEMPLATES_LINE_SEPARATOR;

public class TestDefaultReport {

    @Before
    public void setUp() throws Exception {
    }
    
    private static final String EXPECTED_OUTPUT_TEST_TWO_COMPONENTS = 
            "startReport" + FM_TEMPLATES_LINE_SEPARATOR + 
            "paragraph unit test" + FM_TEMPLATES_LINE_SEPARATOR + 
            "start table" + FM_TEMPLATES_LINE_SEPARATOR + 
            "start header row" + FM_TEMPLATES_LINE_SEPARATOR + 
            "[HeaderCell cspan=1 value=col 3 hAlign=CENTER vAlign=MIDDLE][HeaderCell cspan=1 value=col 4 hAlign=CENTER vAlign=MIDDLE][HeaderCell cspan=1 value=col 5 hAlign=CENTER vAlign=MIDDLE]" + FM_TEMPLATES_LINE_SEPARATOR + 
            "end header row" + FM_TEMPLATES_LINE_SEPARATOR + 
            "startRow 0" + FM_TEMPLATES_LINE_SEPARATOR + 
            "[Cell cspan=1 value=4][Cell cspan=1 value=5][Cell cspan=1 value=6]" + FM_TEMPLATES_LINE_SEPARATOR + 
            "endRow" + FM_TEMPLATES_LINE_SEPARATOR + 
            "startRow 1" + FM_TEMPLATES_LINE_SEPARATOR + 
            "[Cell cspan=1 value=3][Cell cspan=1 value=3][Cell cspan=1 value=3]" + FM_TEMPLATES_LINE_SEPARATOR + 
            "endRow" + FM_TEMPLATES_LINE_SEPARATOR + 
            "startRow 2" + FM_TEMPLATES_LINE_SEPARATOR + 
            "[Cell cspan=1 value=2][Cell cspan=1 value=2][Cell cspan=1 value=2]" + FM_TEMPLATES_LINE_SEPARATOR + 
            "endRow" + FM_TEMPLATES_LINE_SEPARATOR + 
            "startRow 3" + FM_TEMPLATES_LINE_SEPARATOR + 
            "[Cell cspan=1 value=1][Cell cspan=1 value=1][Cell cspan=1 value=1]" + FM_TEMPLATES_LINE_SEPARATOR + 
            "endRow" + FM_TEMPLATES_LINE_SEPARATOR + 
            "startRow 4" + FM_TEMPLATES_LINE_SEPARATOR + 
            "[Cell cspan=1 value=1][Cell cspan=1 value=1][Cell cspan=1 value=1]" + FM_TEMPLATES_LINE_SEPARATOR + 
            "endRow" + FM_TEMPLATES_LINE_SEPARATOR + 
            "startRow 5" + FM_TEMPLATES_LINE_SEPARATOR + 
            "[Cell cspan=1 value=1][Cell cspan=1 value=7][Cell cspan=1 value=1]" + FM_TEMPLATES_LINE_SEPARATOR + 
            "endRow" + FM_TEMPLATES_LINE_SEPARATOR + 
            "startRow 6" + FM_TEMPLATES_LINE_SEPARATOR + 
            "[Cell cspan=1 value= ][Cell cspan=1 value=6][Cell cspan=1 value=14]" + FM_TEMPLATES_LINE_SEPARATOR + 
            "endRow" + FM_TEMPLATES_LINE_SEPARATOR + 
            "end table" + FM_TEMPLATES_LINE_SEPARATOR + 
            "end report";
    
    @Test
    public void testTwoComponents() throws IOException {
        StringWriter testWriter = new StringWriter();
        MockReportOutput mockOutput = new MockReportOutput(testWriter);
        new ReportBuilder(mockOutput).add(new Paragraph("unit test", HorizAlign.CENTER))
                                     .add(new FlatTableBuilder(Scenario1.INPUT)
                                              .dataColumns(Scenario1.DATA_COLUMNS)
                                              .build())
                                     .build()
                                     .execute();

        Assert.assertEquals(EXPECTED_OUTPUT_TEST_TWO_COMPONENTS,
                            testWriter.getBuffer().toString());
    }

    @Test
    public void testTwoComponentsAndFoOutput() throws IOException {
        new ReportBuilder(new FoReportOutput(new FileWriter("./target/TestTwoComponents.fo"),
                                             true,
                                             new FoOutputFormat(PageSize.A3_PORTRAIT)))
        .add(new Paragraph("this is the report title", HorizAlign.CENTER))
        .add(new FlatTableBuilder(Scenario1.INPUT).dataColumns(Scenario1.DATA_COLUMNS).build())
        .build()
        .execute();
    }

    @Test
    public void testTwoComponentsAndExcelXmlOutput() throws IOException {
        new ReportBuilder(new ExcelXmlReportOutput(new FileWriter("./target/TestTwoComponents.xml"),
                                                   true))
            .add(new Paragraph("this is the report title", HorizAlign.CENTER))
            .add(new FlatTableBuilder(Scenario1.INPUT)
                          .dataColumns(Scenario1.DATA_COLUMNS)
                          .build())
             .add(new EmptyLine())
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

        Report report = new ReportBuilder(new ExcelXmlReportOutput(new FileWriter("./target/ReportWithMultipleTables.xml")))
                .add(new Paragraph("this report contains multiple tables", HorizAlign.CENTER))
                .add(new Paragraph("below is a first flat table"))
                .add(flatTable1)
                .add(new EmptyLine())
                .add(new Paragraph("below is the second flat table"))
                .add(flatTable2)
                .add(new EmptyLine())
                .add(new Paragraph("below is the first pivot table")) 
                .add(pivotTable1)
                .add(new EmptyLine())
                .add(new Paragraph("below is the last pivot table"))
                .add(pivotTable2)
                .build();
        report.execute();
    }

    @Ignore
    public void testMemoryLeaksOutputHtml() throws IOException {
        ReportBuilder reportBuilder =
            new ReportBuilder(new HtmlReportOutput(new FileWriter("./target/TestMemoryLeaks.html")));

        reportBuilder.add(new Paragraph("Testing the html output for memory leaks"));
        // add 1000 flat tables
        for (int i = 0; i < 1000; i++) {

            reportBuilder.add(new FlatTableBuilder(ScenarioFormatedValues.INPUT).showTotals(true)
                                                                                .showGrandTotal(true)
                                                                                .groupColumns(ScenarioFormatedValues.GROUP_COLUMNS)
                                                                                .dataColumns(ScenarioFormatedValues.DATA_COLUMNS)
                                                                                .build());
            reportBuilder.add(new EmptyLine());
        }

        reportBuilder.build().execute();
    }

    @Ignore
    public void testMemoryLeaksOutputFo() throws IOException {
        ReportBuilder reportBuilder =
            new ReportBuilder(new FoReportOutput(new FileWriter("./target/TestMemoryLeaks.fo"),
                                                 true,
                                                 new FoOutputFormat()));

        reportBuilder.add(new Paragraph("Testing the fo output for memory leaks"));
        // add 1000 flat tables
        for (int i = 0; i < 1000; i++) {
            reportBuilder.add(new FlatTableBuilder(ScenarioFormatedValues.INPUT)
                         .showTotals(true)
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
        StringWriter testWriter = new StringWriter();
        MockReportOutput mockOutput = new MockReportOutput(testWriter);
        try {
            new ReportBuilder(mockOutput).add(new Paragraph("test output open", HorizAlign.CENTER))
                                         .add(new FlatTableBuilder(null).build())
                                         .build()
                                         .execute();
            // TODO: fail here
        } catch (Throwable exc) {
            // expected
        }

        Assert.assertTrue(Status.CLOSED.equals(mockOutput.getStatus()));
    }
}
