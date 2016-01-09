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
package net.sf.reportengine.util;

import java.io.File;
import java.util.List;

import net.sf.reportengine.components.CellProps;
import net.sf.reportengine.components.RowProps;
import net.sf.reportengine.in.IntermediateCrosstabReportTableInput;
import net.sf.reportengine.out.HtmlReportOutput;
import net.sf.reportengine.out.ReportProps;

/**
 * @author dragos balan
 *
 */
public class IntermedCrosstabViewer {

	/**
	 * we take advantage of the existing input to get access 
	 * to each line of the intermediate report
	 */
	private final IntermediateCrosstabReportTableInput intermCtInput; 
	
	/**
	 * we take advantage of the existing outputs
	 */
	private final HtmlReportOutput output ; 
	
	/**
	 * 
	 * @param inputFilePath
	 * @param outputFilePath
	 */
	public IntermedCrosstabViewer(File inputFile, String outputFilePath){
		intermCtInput = new IntermediateCrosstabReportTableInput(inputFile);
		output = new HtmlReportOutput(ReportIoUtils.createWriterFromPath(outputFilePath)); 
	}
	
	/**
	 * 
	 */
	public void exportToHtml(){
		intermCtInput.open(); 
		output.open();
		//output.startReport(new ReportProps()); 
		output.output("startReport.ftl", new ReportProps());
		output.output("startTable.ftl");
		int rowNbr = 0;
		while(intermCtInput.hasNext()){
			// we get a list with 4 objects
			List<Object> intermLine = intermCtInput.next(); 
			//output.startDataRow(new RowProps(rowNbr));
			output.output("startDataRow.ftl", new RowProps(rowNbr));
			
			
			// result[0] is an instance of IntermGroupValuesList
			// result[1] is an instance of OriginalDataValueList
			// result[2] is an instance of IntermComputedDataList
			// result[3] an instance of IntermComputedTotalsList
			
			//output.outputDataCell(new CellProps.Builder(intermLine.get(0).toString()).build()); 
			output.output("dataCell.ftl", new CellProps.Builder(intermLine.get(0).toString()).build()); 
			
			//output.outputDataCell(new CellProps.Builder(intermLine.get(1).toString()).build()); 
			output.output("dataCell.ftl", new CellProps.Builder(intermLine.get(1).toString()).build()); 
			
			//output.outputDataCell(new CellProps.Builder(intermLine.get(2).toString()).build());
			output.output("dataCell.ftl", new CellProps.Builder(intermLine.get(2).toString()).build());
			
			//output.outputDataCell(new CellProps.Builder(intermLine.get(3).toString()).build());
			output.output("dataCell.ftl", new CellProps.Builder(intermLine.get(3).toString()).build());
			
			//output.endDataRow();
			output.output("endDataRow.ftl");
			rowNbr++;
		}
		
		output.output("endTable.ftl");
		//output.endReport(); 
		output.output("endReport.ftl");
		output.close(); 
		intermCtInput.close(); 
	}
	
}
