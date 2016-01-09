package net.sf.reportengine.components;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;

import static org.apache.commons.lang.SystemUtils.LINE_SEPARATOR;
import org.junit.Test;

import net.sf.reportengine.out.MockReportOutput;

public class TestParagraph {
    
    private final static String EXPECTED_OUTPUT_EMPTY_PARAGRAPH = 
            "paragraph" +LINE_SEPARATOR +
            LINE_SEPARATOR +
            "end paragraph"+LINE_SEPARATOR; 
    
    private final static String EXPECTED_OUTPUT_PARAGRAPH_WITH_TEXT = 
            "paragraph" + LINE_SEPARATOR +
            "unit test" + LINE_SEPARATOR +
            "end paragraph" + LINE_SEPARATOR;
    
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
    
    private Paragraph componentUnderTest; 
    
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
}
