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
package net.sf.reportengine.samples;

import java.io.FileWriter;
import java.io.IOException;

import net.sf.reportengine.ReportBuilder;
import net.sf.reportengine.components.FlatTable;
import net.sf.reportengine.components.FlatTableBuilder;
import net.sf.reportengine.components.ReportTitle;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.in.SqlTableInput;
import net.sf.reportengine.out.HtmlReportOutput;

/**
 * Normal report having as input an SQL query
 * @author dragos balan
 *
 */
public class FlatReportFromSqlQuery {

	
	public static void main(String[] args) throws IOException{
		
		//input configuration
		SqlTableInput input = new SqlTableInput("jdbc:hsqldb:file:E:/projects/java/reportengine-trunk/reportengine/src/test/resources/databases/testdb", 
				"org.hsqldb.jdbcDriver", 
				"SA", 
				"",
				"select country, region, city, sex, religion, value from testreport t order by country, region, city");
		
		
		FlatTable table = new FlatTableBuilder(input)
			//the configuration below skips some columns so it doesn't make too much sense 
			//but it's just for demo purposes
			.addDataColumn(new DefaultDataColumn("country", 0))
			.addDataColumn(new DefaultDataColumn("city", 2)) 
			.addDataColumn(new DefaultDataColumn("sex", 3))
			.addDataColumn(new DefaultDataColumn("value", 5))
		.build(); 
		
		//the one and only execute
		new ReportBuilder(new HtmlReportOutput(new FileWriter("./target/ReportFromDbQuery.html")))
			.add(new ReportTitle("Report from SQL input"))
			.add(table)
			.build()
		.execute();
	}
}
