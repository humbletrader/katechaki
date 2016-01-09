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

/**
 * <p>
 *  Helper class for Cross Tab reports holding some important coefficients like : 
 *  1. the number of total columns
 *  2. the numnber of spaces per row
 *  3. the number of totals per row
 *  4. the colspanPerRow for non-space and non-total values
 * </p>
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 * @since 0.2
 */
public class CrossTabCoefficients {
	
	/**
	 * the template of the report header (the result)
	 */
	//private String[][] template;
	
	/**
	 * the number of distinct values for each header row
	 */
	private int[] distValuesCntInHeaderRow;
	
	/**
	 * the number of totals for each header row
	 */
	private int[] totalsCntInHeaderRow;
	
	/**
	 * the colspanPerRow for non-total and non-space columns 
	 * (valid only when report doesn't have totals in header)
	 */
	private int[] colspanPerRow;
	
	/**
	 * the number of spaces per header row
	 */
	private int[] spaceCntInHeaderRow;
	
	/**
	 * the column count of the header 
	 */
	private int templateColumnCount;
	
	/**
	 * the row count of the header template
	 */
	private int templateRowCount;
	
	/**
	 * 
	 */
	private boolean headerHasTotals;
	
	/**
	 * 
	 */
	private String[][] distinctHeaderValues;
	
    /**
     * empty constructor
     *
     */
	public CrossTabCoefficients(String[][] distinctValues, boolean hasTotals) {
		this.templateRowCount = distinctValues.length; 
		this.headerHasTotals = hasTotals;
		this.distinctHeaderValues = distinctValues;
		
		computeCoeficients();
	}

	/**
	 * initializing the coefficients ...	 
	 */	
	public void computeCoeficients() {
		
		int[] distValuesCnt = new int[templateRowCount];	//number of distinct values for each headerrow
		int[] colspan = new int[templateRowCount]; 			//colspan for each header row
		int[] totalCnt = new int[templateRowCount];			//number of totals for each row
		int[] spacesCnt = new int[templateRowCount];		
		//int[] dPrim = new int[templateRowCount];

		//step 0
		distValuesCnt[0] = distinctHeaderValues[0].length;	//the number of distinct values on first row
		colspan[templateRowCount - 1] = 1;     					//the distance between values on the last row is 1
		int headerColumnCnt = distValuesCnt[0];				//first step in calculating the header column count
		if(headerHasTotals){
			totalCnt[0] = 1; 						//one total on first row
			spacesCnt[0] = 0; 						//zero spaces on first row
			//dPrim[templateRowCount - 1] = 1; 	//even if the totals are displayed the distance on the last row is 1 
		}

		//all next steps
		for (int i = 1; i < templateRowCount; i++) {
			distValuesCnt[i] = distinctHeaderValues[i].length;
			
			//the distance between same values is inversely calculated (bottom to top)
			colspan[templateRowCount-i-1] = colspan[templateRowCount-i] * (distinctHeaderValues[templateRowCount-i].length);
			
			//the header column count
			headerColumnCnt *= distValuesCnt[i];
			
			if(headerHasTotals){
				//the totals on each row is the totals on previous row times the number of distinct values on prev row
				totalCnt[i] = totalCnt[i - 1] * distValuesCnt[i - 1];
			
				//the spaces on each row is the spaces on prev row plus totals on previous row
				//under each prev total we should put a space
				spacesCnt[i] = spacesCnt[i - 1] + totalCnt[i - 1];
				
				//distance (when totals) 
				//dPrim[templateRowCount-i-1] = (distinctHeaderValues[templateRowCount-i].length * dPrim[templateRowCount-i]) + 1;
				colspan[templateRowCount-i-1]++;
			}
		}

		if (headerHasTotals) {
			headerColumnCnt += (totalCnt[totalCnt.length - 1] + spacesCnt[spacesCnt.length - 1]);
		}
		
		setColspanPerRow(colspan);
		setDistValuesCntInHeaderRow(distValuesCnt);
		//setColspanWhenTotals(dPrim);
		setTotalsCntInHeaderRow(totalCnt);
		setSpacesCntInHeaderRow(spacesCnt);
		setTemplateColumnCount(headerColumnCnt);
	}
	
	
	public int getColspanForRow(int i){
	    return colspanPerRow[i];
	}
	
	public int[] getColspanPerRow() {
		return colspanPerRow;
	}

	public void setColspanPerRow(int[] d) {
		this.colspanPerRow = d;
	}

	@Deprecated
	public int[] getColspanWhenTotals() {
		return colspanPerRow;
	}

	public int[] getDistValuesCntInHeaderRow() {
		return distValuesCntInHeaderRow;
	}

	public void setDistValuesCntInHeaderRow(int[] n) {
		this.distValuesCntInHeaderRow = n;
	}

	public int[] getTotalsCntInHeaderRow() {
		return totalsCntInHeaderRow;
	}

	public void setTotalsCntInHeaderRow(int[] t) {
		this.totalsCntInHeaderRow = t;
	}

	/*public String[][] getTemplate() {
		return template;
	}*/

	/*public void setTemplate(String[][] template) {
		this.template = template;
	}*/

	public int getTemplateColumnCount() {
		return templateColumnCount;
	}

	public void setTemplateColumnCount(int templateColumnCount) {
		this.templateColumnCount = templateColumnCount;
	}

	public int[] getSpacesCntInHeaderRow() {
		return spaceCntInHeaderRow;
	}

	public void setSpacesCntInHeaderRow(int[] s) {
		this.spaceCntInHeaderRow = s;
	}

	/**
	 * @return the templateRowCount
	 */
	public int getTemplateRowCount() {
		return templateRowCount;
	}

	/**
	 * @param templateRowCount the templateRowCount to set
	 */
	public void setTemplateRowCount(int templateRowCount) {
		this.templateRowCount = templateRowCount;
	}
	
	public String[][] getDistinctHeaderValues(){
		return distinctHeaderValues;
	}
}

