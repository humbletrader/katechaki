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
package net.sf.reportengine.out;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.reportengine.util.ReportIoUtils;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * This is a two step report output: 
 *  1. the first step creates a .fo file, 
 *  2. the second step transforms the .fo file with FOP 
 * (see https://xmlgraphics.apache.org/fop/)
 * 
 * @author dragos balan
 */
public class PostProcessedFoReportOutput extends AbstractReportOutput {
    
    /**
     * the one and only logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PostProcessedFoReportOutput.class);
    
    /**
     * the class path of the fo config file
     */
    private static final String DEFAULT_FO_CONFIG_CLASSPATH = "net/sf/reportengine/fop/fop.xconf";
    
    /**
     * the output stream (the result of this output)
     */
    private final OutputStream outStream;
    
    /**
     * the mime type of the output (this is needed by fop)
     */
    private final String mimeType;
    
    /**
     * the properties of the fop user agent (needed by fop engine)
     */
    private final FopUserAgentProperties fopUserAgentProps;
    
    /**
     * the configuration of the fop engine
     */
    private final Configuration fopConfiguration;
    
    /**
     * the result of the first step which is going to be parsed by the fop engine for 
     * the final output
     */
    private final File foFile;
    
    /**
     * this is the report output of the first step
     */
    private final FoReportOutput foReportOutput;

    /**
     * 
     * @param outStream
     * @param outFormat
     * @param mimeType
     * @param userAgentProps
     */
    public PostProcessedFoReportOutput(OutputStream outStream,
                                       FoOutputFormat outFormat,
                                       String mimeType,
                                       Configuration fopConfiguration,
                                       FopUserAgentProperties userAgentProps) {

        super(outFormat);

        this.foFile = ReportIoUtils.createTempFile("report", ".fo");
        this.foReportOutput =
            new FoReportOutput(ReportIoUtils.createWriterFromFile(foFile), true, outFormat);

        this.outStream = outStream;
        this.mimeType = mimeType;
        this.fopConfiguration =
            fopConfiguration == null ? buildDefaultConfiguration() : fopConfiguration;
        this.fopUserAgentProps = userAgentProps;
    }

    public OutputStream getOutputStream() {
        return outStream;
    }

    public FopUserAgentProperties getFopUserAgentProps() {
        return fopUserAgentProps;
    }

    public Configuration getFopConfiguration() {
        return fopConfiguration;
    }

    public String getMimeType() {
        return mimeType;
    }

    public <T> void output(String templateName, T model) {
        foReportOutput.output(templateName, model);
    }

    public void postProcess() {
        // transform the fo file
        try {
            LOGGER.info("transforming temporary fo file {} to {}", foFile, mimeType);

            FopFactory fopFactory = FopFactory.newInstance();
            fopFactory.setUserConfig(fopConfiguration);

            // custom configuration for fop (e.g. author of the document, custom
            // renderers etc)
            Fop fop = null;
            if (fopUserAgentProps != null) {
                FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
                foUserAgent.setAuthor(fopUserAgentProps.getAuthor());

                // this is useful when outputting PNG in multiple files (by
                // default fop outputs only one file)
                // check src/java/org/apache/fop/render/bitmap/PNGRenderer.java
                foUserAgent.setOutputFile(new File(fopUserAgentProps.getFilePath()));

                fop = fopFactory.newFop(mimeType, foUserAgent, outStream);
            } else {
                fop = fopFactory.newFop(mimeType, outStream);
            }

            TransformerFactory transformFactory = TransformerFactory.newInstance();
            Transformer transformer = transformFactory.newTransformer();

            Source foSource = new StreamSource(ReportIoUtils.createReaderFromFile(foFile));
            Result result = new SAXResult(fop.getDefaultHandler());
            transformer.transform(foSource, result);

            LOGGER.info("succesful tansformation to {}", mimeType);
        } catch (TransformerConfigurationException e) {
            throw new ReportOutputException(e);
        } catch (TransformerException e) {
            throw new ReportOutputException(e);
        } catch (FOPException e) {
            throw new ReportOutputException(e);
        }
    }

    public void close() {
        try {
            foReportOutput.close();
            ReportIoUtils.deleteTempFileIfNotDebug(foFile);
        } finally {
            super.close();
        }
    }

    /**
     * constructs and returns the default fop configuration
     * 
     * @return the default fop configuration
     */
    private Configuration buildDefaultConfiguration() {
        DefaultConfigurationBuilder configBuilder = new DefaultConfigurationBuilder();
        Configuration configuration;
        try {
            configuration =
                configBuilder.build(ClassLoader.getSystemResourceAsStream(DEFAULT_FO_CONFIG_CLASSPATH));

        } catch (ConfigurationException e) {
            throw new ReportOutputException(e);
        } catch (SAXException e) {
            throw new ReportOutputException(e);
        } catch (IOException e) {
            throw new ReportOutputException(e);
        }

        return configuration;
    }
}
