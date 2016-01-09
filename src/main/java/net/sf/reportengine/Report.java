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
package net.sf.reportengine;

import net.sf.reportengine.components.ReportComponent;

/**
 * <p>The main interface for reports execution</p>
 * <p>
 * The typical usage is:
 * <pre>
 *  Report report = new ReportBuilder(new HtmlReportOutput(new FileWriter("/temp/test.html")))
 *           .add(.. report title..)
 *           .add(.. table...)
 *           .add(.. paragraph ..)
 *           .add( .. other report components..)
 *            ...
 *            .build(); 
 *  report.execute(); 
 * </pre>
 * </p>
 * <p>
 * Any implementation of {@link ReportComponent} can be added to the report using the {@code ReportBuilder#add(ReportComponent)} method
 * </p>
 * 
 * @see ReportBuilder
 * @see ReportComponent
 * 
 * @author dragos balan
 * @since 0.13
 */
public interface Report {

	/**
	 * runs the report
	 */
	public void execute();

}
