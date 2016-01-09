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
import java.util.List;
import java.util.PriorityQueue;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.NewRowComparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Table input built from multiple files of NewRowEventsWrapper. 
 * The files provided in the constructor should have the values sorted.
 * 
 *  A priority queue will be used to store the values read from the list of files. 
 *  The priority queue will sort the values and this input class will provide the NewRowEvent from this priority queue.
 * 
 * @author dragos balan
 */
public class MultipleExternalSortedFilesTableInput implements TableInput {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MultipleExternalSortedFilesTableInput.class);
	
	/**
	 * a priority queue where the rows from external files will be stored
	 */
	private PriorityQueue<SortedInputRowsFileWrapper> externalFilesQueue; 
	
	/**
	 * the list of files containing sorted serialized input rows
	 */
	private List<File> externalSortedFiles; 
	
	/**
	 * 
	 * @param externalSortedFiles  the list of files (each file having the values sorted)
	 * @param newRowComparator     a comparator for new rows
	 */
	public MultipleExternalSortedFilesTableInput(List<File> files, 
	                                             NewRowComparator newRowComparator){
		
		LOGGER.info("building input from {} external sorted files", files.size()); 
		externalSortedFiles = files; 
		externalFilesQueue = new PriorityQueue<SortedInputRowsFileWrapper>(	externalSortedFiles.size(), 
																		new SortedInputRowsFileWrapperComparator(newRowComparator));
		
		for (File file : externalSortedFiles) {
			LOGGER.debug("searching for external file {} ", file.getAbsolutePath());
			this.externalFilesQueue.add(new SortedInputRowsFileWrapper(file)); 
		}
	}
	
	
	/**
	 * empty implementation
	 */
	public void open() {
	}

	/**
	 * empty implementation
	 */
	public void close() {
	}

	/**
	 * next row is taken from the priority queue by polling the priority queue which returns the top FileWrapper. 
	 * On the topmost file wrapper, the poll method will be executed (which returns the top NewRowEvent) and, if not empty, the 
	 * file wrapper is re-introduced into the priority queue (so that he participated in the next newRow() operation )
	 */
	public List<Object> next() {
		List<Object> result = null; 
		if(hasNext()){
			SortedInputRowsFileWrapper inputRowsFileWrapper = externalFilesQueue.poll();
			NewRowEvent newRowEvent = inputRowsFileWrapper.poll();
			
			if(inputRowsFileWrapper.isEmpty()){
				inputRowsFileWrapper.close(); 
			}else{
				externalFilesQueue.add(inputRowsFileWrapper); 
			}
			
			result = newRowEvent.getInputDataRow(); 
		}
		
		return result; 
	}
	
	/**
	 * returns true if the priority queue with the external sorted files is empty
	 */
	public boolean hasNext() {
		return !externalFilesQueue.isEmpty(); 
	}
}
