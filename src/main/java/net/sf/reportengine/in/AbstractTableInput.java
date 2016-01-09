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
package net.sf.reportengine.in;

import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.out.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Minimal implementation for some ReportInput methods. Use this class as
 * starter for any other ReportInput implementation.
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public abstract class AbstractTableInput implements TableInput {

    /**
     * the one and only logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTableInput.class);

    /**
     * default header for columns
     */
    public final static String DEFAULT_COLUMN_HEADER = "Column";

    /**
     * the status of the input
     */
    private Status status = Status.INIT;

    /**
     * the columns metadata
     */
    private List<ColumnMetadata> columnMetadata = new ArrayList<ColumnMetadata>();

    /**
     * marks the input as open.
     */
    public void open() {
        LOGGER.debug("opening the input...");
        status = Status.OPEN;
    }

    /**
     * marks the input as closed
     */
    public void close() {
        if (!Status.OPEN.equals(status)) {
            throw new IllegalStateException("You cannot close an input which is not open !");
        }
        status = Status.CLOSED;
        LOGGER.debug("report input closed succesfully");
    }

    public Status getStatus() {
        return status;
    }

}
