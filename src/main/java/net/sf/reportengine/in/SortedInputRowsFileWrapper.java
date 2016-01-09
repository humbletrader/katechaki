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
/**
 * 
 */
package net.sf.reportengine.in;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.NewRowEventWrapper;
import net.sf.reportengine.util.ReportIoUtils;

/**
 * Wrapper over a File object with serialized NewRowEventWrapper objects which reads 
 * one by one the values in the file (lazy reading) therefore not impacting the memory. 
 * This class is also a quasi-queue (it has peek() and poll() methods implemented)
 * 
 * @author dragos balan
 */
class SortedInputRowsFileWrapper {
	
	/**
	 * the input stream with the serialized NewRowEventWrapper objects
	 */
	private ObjectInputStream objectInputStream;
	
	/**
	 * the top element of this 
	 */
	private NewRowEventWrapper topElement;
	
	/**
	 * specifies whether the last object read from the file was the last one
	 */
	private boolean lastObjectReadWasTheLastOne = false; 
	
	/**
	 * 
	 * @param inputFile
	 */
	public SortedInputRowsFileWrapper(File inputFile){
		this(ReportIoUtils.createInputStreamFromFile(inputFile)); 
	}
	
	/**
	 * 
	 * @param inputStream
	 */
	protected SortedInputRowsFileWrapper(InputStream inputStream) {
		try {
			objectInputStream = new ObjectInputStream(inputStream);
			loadNext();
		} catch (IOException e) {
			throw new TableInputException(e) ;
		}
	}
	
	/**
	 * returns true if this queue is empty
	 * @return
	 */
	public boolean isEmpty() {
		return topElement == null;
	}
	
	/**
	 * reads the next top element if the wrapped file still contains values
	 */
	private void loadNext() {
		try {
			if(!lastObjectReadWasTheLastOne){
				topElement = (NewRowEventWrapper)objectInputStream.readObject();
				lastObjectReadWasTheLastOne = topElement.isLast(); 
			}else{
				topElement = null; 
			}
		} catch (ClassNotFoundException e) {
			throw new TableInputException(e); 
		} catch (IOException e){
			throw new TableInputException(e); 
		}
	}
	
	/**
	 * closes the stream of serialized objects
	 */
	public void close() {
		try{
			objectInputStream.close();
			topElement = null; 
		}catch(IOException e){
			throw new TableInputException(e); 
		}
	}
	
	/**
	 * returns the top element in this queue
	 * 
	 * @return the top NewRowEvent element
	 */
	public NewRowEvent peek() {
		NewRowEvent result = null; 
		if(topElement !=  null){
			result = topElement.getNewRowEvent();
		}
		return result; 
	}
	
	/**
	 * polls the queue
	 * 
	 * @return
	 */
	public NewRowEvent poll() {
	  NewRowEvent result = peek();
	  loadNext();
	  return result;
	}
}
