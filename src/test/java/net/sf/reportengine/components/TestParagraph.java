package net.sf.reportengine.components;

import static net.sf.reportengine.util.ReportIoUtils.FM_TEMPLATES_LINE_SEPARATOR;
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
    
    private final static String EXPECTED_OUTPUT_EMPTY_PARAGRAPH = "paragraph "+FM_TEMPLATES_LINE_SEPARATOR;
    
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
    
    
    private final static String EXPECTED_OUTPUT_PARAGRAPH_WITH_TEXT = "paragraph unit test"+FM_TEMPLATES_LINE_SEPARATOR; 
    
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
    
    public final static String EXPECTED_HTML_OUT = 
            "<p style=\"text-align:left\">"+FM_TEMPLATES_LINE_SEPARATOR+
            "unit test"+FM_TEMPLATES_LINE_SEPARATOR+
            "</p>"+FM_TEMPLATES_LINE_SEPARATOR;
    
    @Test
    public void testHtmlParagraphWithText(){
        StringWriter htmlWriter = new StringWriter(); 
        AbstractReportOutput htmlOutput = new HtmlReportOutput(htmlWriter);
        componentUnderTest = new Paragraph("unit test");
        
        htmlOutput.open();
        componentUnderTest.output(htmlOutput);
        htmlOutput.close();
        
        assertEquals(EXPECTED_HTML_OUT, htmlWriter.getBuffer().toString()); 
    }
    
    public final static String EXPECTED_FO_OUT = 
    		"<fo:block color=\"black\" font-family=\"NotoSans\" font-size=\"12pt\" font-style=\"normal\" font-weight=\"normal\" margin-left=\"10pt\" text-align=\"left\">"+FM_TEMPLATES_LINE_SEPARATOR+
            "unit test"+FM_TEMPLATES_LINE_SEPARATOR+
            "</fo:block>"+FM_TEMPLATES_LINE_SEPARATOR;
    
    @Test
    public void testFoParagraphWithText(){
        StringWriter foWriter = new StringWriter(); 
        AbstractReportOutput foOutput = new FoReportOutput(foWriter);
        componentUnderTest = new Paragraph("unit test");
        
        foOutput.open();
        componentUnderTest.output(foOutput); 
        foOutput.close();
        
        assertEquals(EXPECTED_FO_OUT, foWriter.getBuffer().toString()); 
    }
    
    
    public final static String EXPECTED_EXCEL_OUT = 
            "<Row>"+FM_TEMPLATES_LINE_SEPARATOR+
            "<Cell><Data ss:Type=\"String\">unit test</Data></Cell>"+FM_TEMPLATES_LINE_SEPARATOR+
            "</Row>"+FM_TEMPLATES_LINE_SEPARATOR;
    
    @Test
    public void testExcelParagraphWithText(){
        StringWriter excelWriter = new StringWriter(); 
        AbstractReportOutput excelOutput = new ExcelXmlReportOutput(excelWriter); 
        componentUnderTest = new Paragraph("unit test");
        
        excelOutput.open();
        componentUnderTest.output(excelOutput);
        excelOutput.close();
        
        assertEquals(EXPECTED_EXCEL_OUT, excelWriter.getBuffer().toString()); 
    }
}
