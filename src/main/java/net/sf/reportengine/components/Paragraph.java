package net.sf.reportengine.components;

import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.out.AbstractReportOutput;

/**
 * <p>paragraph component </p> 
 * This it the equivalent of a html paragraph &lt;p&gt; inside which multiple other sub-components 
 * can be added.<br/>
 * Typical usage: 
 * <pre>
 *  Paragraph paragraph = new Paragraph("this is the text", HorizAlign.CENTER); 
 * </pre>
 *  A paragraph with more components inside is: 
 *  <pre>
 *  Paragraph paragraph = new Paragraph("this is a paragraph with a table inside", HorizAlign.CENTER);
 *  paragraph.addComponent( new FlatTable(..));  
 *  </pre
 * 
 * @author dragos balan
 * @since 0.14.0
 */
public class Paragraph extends AbstractComponentContainer {
    
    private final static String FM_START_PARAGRAPH = "startParagraph.ftl"; 
    private final static String FM_END_PARAGRAPH = "endParagraph.ftl"; 
    
    /**
     * the properties of this paragraph
     */
    private final ParagraphProps paragraphProperties ;
    
    /**
     * empty text paragraph
     */
    public Paragraph(){
        this(""); 
    }
    
    /**
     * left aligned text paragraph
     * 
     * @param text  the text in the paragraph
     */
    public Paragraph(String text){
        this(text, HorizAlign.LEFT);
    }
    
    /**
     * Paragraph with control over horizontal alignment of text
     * 
     * @param text      the text
     * @param alignment the alignment of the text
     */
    public Paragraph(String text, HorizAlign alignment){
        this.paragraphProperties = new ParagraphProps(text, alignment); 
    }
    
    @Override
    protected void beforeOutputComponents(AbstractReportOutput out) {
        out.output(FM_START_PARAGRAPH, paragraphProperties);
    }

    @Override
    protected void afterOutputComponents(AbstractReportOutput out) {
        out.output(FM_END_PARAGRAPH);
    }
}
