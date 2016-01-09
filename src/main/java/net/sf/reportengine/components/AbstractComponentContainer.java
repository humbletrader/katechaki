package net.sf.reportengine.components;

import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.out.AbstractReportOutput;

/**
 * <p>Abstract container of components</p>
 *   
 * @author dragos balan
 */
public abstract class AbstractComponentContainer implements ReportComponent {
    
    /**
     * a list of components
     */
    private List<ReportComponent> components = new ArrayList<ReportComponent>(); 
    
    
    /**
     * adds a component to this container
     * 
     * @param component
     */
    public void addComponent(ReportComponent component){
        components.add(component);
    }
    
    /**
     * this method is called before the output on all other components is called. 
     * 
     * @param out   the report output
     */
    protected abstract void beforeOutputComponents(AbstractReportOutput out); 
    
    /**
     * this method is called after the output of all other components was called.
     * 
     * @param out   the report output
     */
    protected abstract void afterOutputComponents(AbstractReportOutput out); 
    
    /**
     * output method. 
     * 
     * Calls the beforeOutput 
     * then the output of each component
     * then the afterOutput method
     */
    public void output(AbstractReportOutput out) {
        beforeOutputComponents(out);
        for(ReportComponent comp: components){
            comp.output(out);
        }
        afterOutputComponents(out);
    }
}
