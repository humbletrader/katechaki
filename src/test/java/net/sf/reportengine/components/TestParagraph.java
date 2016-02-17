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
    
    private final static String EXPECTED_OUTPUT_EMPTY_PARAGRAPH = "paragraph ";
    
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
    
    
    private final static String EXPECTED_OUTPUT_PARAGRAPH_WITH_TEXT = "paragraph unit test"; 
    
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
            "<p style=\"text-align:left\">"+LINE_SEPARATOR+
            "unit test"+LINE_SEPARATOR+
            "</p>"+LINE_SEPARATOR;
    
    public final static String EXPECTED_FO_OUT = 
            "<fo:block text-align=\"left\">"+LINE_SEPARATOR+
            "unit test"+LINE_SEPARATOR+
            "</fo:block>"+LINE_SEPARATOR ;
    
    public final static String EXPECTED_EXCEL_OUT = 
            "<Row>"+LINE_SEPARATOR+
            "<Cell><Data ss:Type=\"String\">unit test</Data></Cell>"+LINE_SEPARATOR+
            "</Row>"+LINE_SEPARATOR;
    
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
