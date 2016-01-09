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
package net.sf.reportengine.core.steps;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.ReportEngineRuntimeException;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.util.ReportIoUtils;
import net.sf.reportengine.util.StepIOKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dragos balan
 *
 */
public class ExternalSortPreparationStep extends AbstractReportStep<List<File>, String, String>{
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExternalSortPreparationStep.class);
	
	/**
	 * 
	 */
	private static final int DEFAULT_MAX_ROWS_IN_MEMORY = 1000; 
	
	/**
	 * 
	 */
	private NewRowComparator COMPARATOR; 
	
	/**
	 * 
	 */
	private int maxRowsInMemory;
	
	/**
	 * the list of sorted files
	 */
	private List<File> sortedFiles = new ArrayList<File>();
	
	/**
	 * in memory holder of new row events 
	 */
	private List<NewRowEvent> tempValuesHolderList =  new ArrayList<NewRowEvent>();
	
	/**
	 * 
	 */
	public ExternalSortPreparationStep(){
		this(DEFAULT_MAX_ROWS_IN_MEMORY); 
	}
	
	/**
	 * 
	 * @param maxRowsInMemory
	 */
	public ExternalSortPreparationStep(int maxRowsInMemory){
		this.maxRowsInMemory = maxRowsInMemory; 
	}
	
	/**
	 * 
	 */
	public StepResult<List<File>> init(StepInput stepInput){
		COMPARATOR = new NewRowComparator(getGroupColumns(stepInput), getDataColumns(stepInput)); 
		return new StepResult<List<File>>(StepIOKeys.FILES_WITH_SORTED_VALUES, sortedFiles); 
	}
	
	/**
	 * 
	 */
	public StepResult<String> execute(NewRowEvent newRow, StepInput stepInput){
		try{
			if(tempValuesHolderList.size() >= maxRowsInMemory){ 
				LOGGER.info("in memory list of rows has reached the maximum allowed {} items ", tempValuesHolderList.size());
				//first sorting in-memory list
				Collections.sort(tempValuesHolderList, COMPARATOR);
				//then save the sorted list into file
				sortedFiles.add(saveToFile(tempValuesHolderList));
				tempValuesHolderList.clear();
			}
			tempValuesHolderList.add(newRow);			  
		}catch(IOException ioExc){
			throw new ReportEngineRuntimeException(ioExc); 
		}
		return StepResult.NO_RESULT; 
	}
	
	/**
	 * 
	 */
	public StepResult<String> exit(StepInput stepInput){
		try{
			if(!tempValuesHolderList.isEmpty()){
				//first sorting in-memory list
				Collections.sort(tempValuesHolderList, COMPARATOR);
				sortedFiles.add(saveToFile(tempValuesHolderList)); 
			}
		}catch(IOException ioExc){
			throw new ReportEngineRuntimeException(ioExc); 
		}
		
		tempValuesHolderList.clear();
		tempValuesHolderList = null; 
		
		//getAlgoContext().set(ContextKeys.SORTED_FILES, sortedFiles); 
		
		//it seems that Context.SORTED_FILES and IOKeys.SORTED_FILES represent the same thing
		//return new StepResult<List<File>>(ContextKeys.SORTED_FILES,  sortedFiles); 
		return StepResult.NO_RESULT; 
	}
	
	/**
	 * saves the given list into a temporary file
	 * 
	 * @param rowsList     list of input rows
	 * @return a file containing the input rows
	 * @throws IOException when serialization IOException occurs
	 */
	public File saveToFile(List<NewRowEvent> rowsList) throws IOException  {
		//
		File tempFile = ReportIoUtils.createTempFile("sorted-obj");
		
		LOGGER.info("saving the sorted rows list into {}", tempFile.getName());
		ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(tempFile));
		
		try {
			for(int i=0; i < rowsList.size()-1; i++) {
				outputStream.writeObject(new NewRowEventWrapper(rowsList.get(i), false));
			}
			
			//separate writing of last one
			outputStream.writeObject(new NewRowEventWrapper(rowsList.get(rowsList.size()-1), true));
		} finally {
			outputStream.close();
		}
		
		LOGGER.info("{} items saved in file {}", rowsList.size(), tempFile.getAbsolutePath());
		return tempFile; 
	}
}