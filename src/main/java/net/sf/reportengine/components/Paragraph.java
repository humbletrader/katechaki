package net.sf.reportengine.components;

import net.sf.reportengine.config.HorizAlign;

/**
 * <p>paragraph component </p> 
 * This it the equivalent of a html paragraph &lt;p&gt;<br/>
 * Typical usage: 
 * <pre>
 *  Paragraph paragraph = new Paragraph("this is the text", HorizAlign.CENTER); 
 * </pre>
 * 
 * @author dragos balan
 * @since 0.14.0
 */
public class Paragraph extends DefaultReportComponent<ParagraphProps> {
    
    /**
     * the default freemarker template
     */
    private final static String FM_PARAGRAPH = "paragraph.ftl"; 
    
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
        super(FM_PARAGRAPH, new ParagraphProps(text, alignment)); 
    }
}
