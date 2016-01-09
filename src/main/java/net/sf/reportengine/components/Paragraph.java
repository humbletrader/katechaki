package net.sf.reportengine.components;

import net.sf.reportengine.out.AbstractReportOutput;

public class Paragraph extends AbstractComponentContainer {
    
    private final static String FM_START_PARAGRAPH = "startParagraph.ftl"; 
    private final static String FM_END_PARAGRAPH = "endParagraph.ftl"; 
    
    private final String paragraphText;
    
    public Paragraph(){
        this(""); 
    }
    
    public Paragraph(String text){
        this.paragraphText = text; 
    }
    
    @Override
    protected void beforeOutputComponents(AbstractReportOutput out) {
        out.output(FM_START_PARAGRAPH, new ParagraphProps(paragraphText));
    }

    @Override
    protected void afterOutputComponents(AbstractReportOutput out) {
        out.output(FM_END_PARAGRAPH);
    }
}
