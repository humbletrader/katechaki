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
package net.sf.reportengine.out;

import java.io.IOException;
import java.io.Writer;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateException;

/**
 * <p> Base abstract class for most of your output classes </p>
 * 
 * This abstract class should be used for any freemarker based report output which doesn't need
 * a second processing step. If your report needs another processing step of the freemarker output
 * please consult {@code PostProcessedFoReportOutput}.
 * 
 * @author dragos balan
 */
public abstract class AbstractFreemarkerReportOutput extends AbstractReportOutput {

    /**
     * the freemarker configuration
     */
    private final Configuration fmConfig;

    /**
     * the output writer
     */
    private final Writer writer;

    /**
     * if true the writer will be closed when the output is done if false the
     * user of this class is responsible of closing the writer
     */
    private final boolean closeWriterWhenOutputReady;

    /**
     * creates a freemarker based output and configures all freemarker needed 
     * classes and properties
     * 
     * @param writer    
     *            the writer to which the output will be sent
     * @param closeWriterWhenDone
     *            if true the writer will be closed when the output is done
     *            otherwise the user of this class is responsible of closing it
     * @param outFormat
     *            the output format parameters
     */
    public AbstractFreemarkerReportOutput(Writer writer,
                                          boolean closeWriterWhenDone,
                                          OutputFormat outFormat) {
        super(outFormat);
        this.writer = writer;
        this.closeWriterWhenOutputReady = closeWriterWhenDone;

        fmConfig = new Configuration();
        fmConfig.setObjectWrapper(new DefaultObjectWrapper());
        fmConfig.setTemplateLoader(new ClassTemplateLoader(getClass(), getTemplatesClasspath()));
    }
    
    /**
     * by using this method the subclasses can return the classpath of the templates
     * 
     * @return  the classpath of the templates used by this output
     */
    public abstract String getTemplatesClasspath();

    /**
	 * calls the freemarker template engine to output the given model/data using the writer 
	 * of this report output
	 */
    public <T> void output(String templateName, T model) {
        try {
            fmConfig.getTemplate(templateName).process(model, writer);
        } catch (TemplateException e) {
            throw new ReportOutputException(e);
        } catch (IOException e) {
            throw new ReportOutputException(e);
        }
    }
    
    /**
     * flushes and closes the writer 
     */
    public void close() {
        try {
            writer.flush();
            if (closeWriterWhenOutputReady) {
                writer.close();
            }
        } catch (IOException e) {
            throw new ReportOutputException(e);
        } finally {
            super.close();
        }
    }
}
