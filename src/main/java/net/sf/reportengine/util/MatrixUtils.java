/**
 * Copyright (C) 2006 Dragos Balan (dragos.balan@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.reportengine.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * class containing methods usefull for working with matrixes
 *  
 * @author dragos balan
 */
public final class MatrixUtils {
    
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MatrixUtils.class);
	
    /**
     * prevent inheritance
     *
     */
    private MatrixUtils(){
        
    }
	
	public static void logMatrix(Object[][] m) {
		logMatrix(null, m);
	}
    
    public static void logMatrix(String comment, Object[][] m){
        if(comment != null){
            LOGGER.debug(comment);
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < m.length; i++) {
        	buffer.append("line : "+i+" length : "+m[i].length +" : ");
            for (int j = 0; j < m[i].length; j++) {
                buffer.append(m[i][j] + "\t");
            }
            LOGGER.debug("{}",buffer);
            buffer.delete(0, buffer.length());
        }
    }
    
    /**
	 * logs an ArrayList to the standars output
	 * @param list a list of Object[] array (i.e. a variable row matrix)
	 */
	public static void logMatrix(List list) {
		int index = 0;
		StringBuffer buffer = new StringBuffer();
		while (index < list.size()) {
		    Object[] row = (Object[]) list.get(index++);
		    buffer.append("line : "+index+" length : "+row.length +" : ");
			for (int i = 0; i < (row.length - 1); i++) {
			    buffer.append(" " + row[i]);
			}
			buffer.append(" " + row[row.length - 1]);
			LOGGER.debug("{}",buffer);
			buffer.delete(0, buffer.length());
		}
	}
	
	
	/**
	 * compares the objects whithin two matrixes
	 * @param matrix1
	 * @param matrix2
	 * @return
	 */
	public static boolean compareMatrices(Object[][] matrix1, Object[][] matrix2){
		boolean result = true;
		if(matrix1.length != matrix2.length){
			throw new IllegalArgumentException("the two matrixes don't have the same number of lines "+matrix1.length+" != "+matrix2.length);
		}
		
		for(int i=0; i<matrix1.length && result; i++){
			if(matrix1[i].length != matrix2[i].length){
				throw new IllegalArgumentException("the two matrixes don't have the same number of columns on line "+i+". "+matrix1[i].length+" <> "+matrix2[i].length);
			}else{
				for(int j=0; j<matrix1[i].length && result; j++){
					if(!matrix1[i][j].equals(matrix2[i][j])){
						result = false;
						LOGGER.info("row="+i+",col="+j);
						LOGGER.info(" 	the object {} is not equal to {}", 	
												matrix1[i][j], 
												matrix2[i][j]);
						LOGGER.info(" 	stopping iteration in matrix");
					}
				}
			}
		}
		return result;
	}
	
	
	/**
	 * compares all values whithin the two matrixes but before comparison it calls the toString method
	 * 
	 * @param matrix1
	 * @param matrix2
	 * 
	 * @return	
	 */
    public static boolean compareMatricesAsStrings(Object[][] matrix1, Object[][] matrix2){
        boolean result = true;
        if(matrix1.length != matrix2.length){
            throw new IllegalArgumentException("the two matrixes don't have the same number of lines");
        }
        
        for(int i=0; i<matrix1.length && result; i++){
            if(matrix1[i].length != matrix2[i].length){
                throw new IllegalArgumentException("the two matrixes don't have the same number of columns on line "+i);
            }else{
                for(int j=0; j<matrix1[i].length && result; j++){
                    if(matrix1[i][j] != null && matrix2[i][j] != null){
                    	String value1 = matrix1[i][j].toString(); 
                    	String value2 = matrix2[i][j].toString(); 
                    	
                        if(!value1.equals(value2)){
                            result = false;
                            //System.out.println(" values "+value1+" and "+value2+" are different");
                        }
                    }else{
                        if( (matrix1[i][j] != null && matrix2[i][j] == null)
                            || 
                            (matrix1[i][j] != null && matrix2[i][j] == null)
                          )
                        {
                            result = false;
                        }else{
                            //both null just continue
                        }
                    }
                }
            }
        }
        return result;
    }
}
