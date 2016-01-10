package net.sf.reportengine.components;

import static org.apache.commons.lang.SystemUtils.LINE_SEPARATOR;
import static org.junit.Assert.assertEquals;

import java.io.StringWriter;

import org.junit.Test;

import net.sf.reportengine.out.AbstractReportOutput;
import net.sf.reportengine.out.ExcelXmlReportOutput;
import net.sf.reportengine.out.FoReportOutput;
import net.sf.reportengine.out.HtmlReportOutput;
import net.sf.reportengine.out.MockReportOutput;

public class TestParagraph {
    
    private Paragraph componentUnderTest; 
    
    private final static String EXPECTED_OUTPUT_EMPTY_PARAGRAPH = 
            "paragraph" +LINE_SEPARATOR +
            LINE_SEPARATOR +
            "end paragraph"+LINE_SEPARATOR; 
    
    @Test
    public void testOutputEmptyParagraph() {
        StringWriter testWriter = new StringWriter(); 
        MockReportOutput testOutput = new MockReportOutput(testWriter); 
        componentUnderTest = new Paragraph();
        testOutput.open();
        componentUnderTest.output(testOutput);
        testOutput.close();
        assertEquals(EXPECTED_OUTPUT_EMPTY_PARAGRAPH, testWriter.getBuffer().toString());
    }
    
    
    private final static String EXPECTED_OUTPUT_PARAGRAPH_WITH_TEXT = 
            "paragraph" + LINE_SEPARATOR +
            "unit test" + LINE_SEPARATOR +
            "end paragraph" + LINE_SEPARATOR;
    
    @Test
    public void testOutputParagraphWithText() {
        StringWriter testWriter = new StringWriter(); 
        MockReportOutput testOutput = new MockReportOutput(testWriter); 
        componentUnderTest = new Paragraph("unit test");
        testOutput.open();
        componentUnderTest.output(testOutput);
        testOutput.close();
        assertEquals(EXPECTED_OUTPUT_PARAGRAPH_WITH_TEXT, testWriter.getBuffer().toString());
    }
    
    private final static String EXPECTED_OUTPUT_ENCLOSED_EMPTY_PARAGRAPHS = 
            "paragraph" +LINE_SEPARATOR +
            LINE_SEPARATOR +
            "paragraph" +LINE_SEPARATOR +
            LINE_SEPARATOR +
            "end paragraph"+LINE_SEPARATOR+
            "paragraph" +LINE_SEPARATOR +
            LINE_SEPARATOR +
            "end paragraph"+LINE_SEPARATOR+
            "end paragraph"+LINE_SEPARATOR;
    
    @Test
    public void testOutputEnclosedEmptyParagraphs() {
        StringWriter testWriter = new StringWriter(); 
        MockReportOutput testOutput = new MockReportOutput(testWriter); 
        componentUnderTest = new Paragraph();
        componentUnderTest.addComponent(new Paragraph());
        componentUnderTest.addComponent(new Paragraph());
        testOutput.open();
        componentUnderTest.output(testOutput);
        testOutput.close();
        assertEquals(EXPECTED_OUTPUT_ENCLOSED_EMPTY_PARAGRAPHS, testWriter.getBuffer().toString());
    }
    
    private final static String EXPECTED_OUTPUT_ENCLOSED_PARAGRAPHS_WITH_TEXT = 
            "paragraph" +LINE_SEPARATOR +
            LINE_SEPARATOR +
            "paragraph" +LINE_SEPARATOR +
            "unit test line 1"+LINE_SEPARATOR +
            "end paragraph"+LINE_SEPARATOR+
            "paragraph" +LINE_SEPARATOR +
            "unit test line 2" +LINE_SEPARATOR +
            "end paragraph"+LINE_SEPARATOR+
            "end paragraph"+LINE_SEPARATOR;
    
    @Test
    public void testOutputEnclosedParagraphsWithText() {
        StringWriter testWriter = new StringWriter(); 
        MockReportOutput testOutput = new MockReportOutput(testWriter); 
        componentUnderTest = new Paragraph();
        componentUnderTest.addComponent(new Paragraph("unit test line 1"));
        componentUnderTest.addComponent(new Paragraph("unit test line 2"));
        testOutput.open();
        componentUnderTest.output(testOutput);
        testOutput.close();
        assertEquals(EXPECTED_OUTPUT_ENCLOSED_PARAGRAPHS_WITH_TEXT, testWriter.getBuffer().toString());
    }
    
    public final static String EXPECTED_HTML_OUT = 
            "<p>"+LINE_SEPARATOR+
            "unit test"+LINE_SEPARATOR+
            "</p>";
    
    public final static String EXPECTED_FO_OUT = 
            "<fo:block>"+LINE_SEPARATOR+
            "unit test"+LINE_SEPARATOR+
            "</fo:block>";
    
    public final static String EXPECTED_EXCEL_OUT = 
            "<Row>"+LINE_SEPARATOR+
            "<Cell><Data ss:Type=\"String\">unit test</Data></Cell>"+LINE_SEPARATOR+
            "</Row>";
    
    @Test
    public void testHtmlFoAndExcelParagraphWithText(){
        StringWriter htmlWriter = new StringWriter(); 
        StringWriter foWriter = new StringWriter(); 
        StringWriter excelWriter = new StringWriter(); 
        
        AbstractReportOutput htmlOutput = new HtmlReportOutput(htmlWriter);
        AbstractReportOutput foOutput = new FoReportOutput(foWriter);
        AbstractReportOutput excelOutput = new ExcelXmlReportOutput(excelWriter); 
        
        componentUnderTest = new Paragraph("unit test");
        
        htmlOutput.open();
        foOutput.open();
        excelOutput.open();
        
        componentUnderTest.output(htmlOutput);
        componentUnderTest.output(foOutput); 
        componentUnderTest.output(excelOutput);
        
        htmlOutput.close();
        foOutput.close();
        excelOutput.close();
        
        assertEquals(EXPECTED_HTML_OUT, htmlWriter.getBuffer().toString()); 
        assertEquals(EXPECTED_FO_OUT, foWriter.getBuffer().toString()); 
        assertEquals(EXPECTED_EXCEL_OUT, excelWriter.getBuffer().toString()); 
    }
}
