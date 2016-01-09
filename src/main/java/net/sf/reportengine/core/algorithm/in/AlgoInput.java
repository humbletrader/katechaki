package net.sf.reportengine.core.algorithm.in;

/**
 * This is the top interface for any algorithm's input 
 * 
 * @author dragos balan (dragos dot balan at gmail.com)
 */
public interface AlgoInput<T> {
    
    /**
     * prepares the input for reading. 
     * Within this method you can open streams, database connections etc.
     */
    public void open();
    
    /**
     * use this method to release all resources used during the reading 
     * of the input lines
     */
    public void close();
    
    /**
     * <p>retrieves the next piece of data</p>
     * 
     * @return the data object
     */
    public T next();
    
    /**
     * returns true if this input data more data left to return
     * 
     * @return  true if the input has more rows to return
     */
    public boolean hasNext();
}
