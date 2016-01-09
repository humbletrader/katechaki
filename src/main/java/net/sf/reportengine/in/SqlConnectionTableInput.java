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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.sf.reportengine.out.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlConnectionTableInput extends AbstractTableInput implements ColumnMetadataHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlConnectionTableInput.class);

    /**
     * the connection to the database
     */
    private final Connection dbConnection;

    /**
     * if true the lifecycle of dbConnection (closing) is controlled by this
     * class
     */
    private final boolean closeConnectionWhenDone;

    /**
     * the sql statement
     */
    private final String sqlStatement;

    /**
     * the jdbc statement
     */
    private PreparedStatement jdbcPrepStatement;

    /**
     * a helpder table input on which all method request will be delegated
     */
    private JdbcResultsetTableInput resultSetTableInput;

    /**
     * Builds the sql input based on the provided connection which will be
     * managed (closed) externally. If you want to control the lifecycle of the
     * connection you should use the other constructor. Besides the connection
     * you have to provide a query statement using
     * <source>setSqlStatement(String)</source>
     * 
     * @param conn
     *            the connection provided
     */
    public SqlConnectionTableInput(Connection conn, String sqlStatement) {
        this(conn, sqlStatement, false);
    }

    /**
     * Builds the sql input based on the provided connection which will be
     * managed (closed) according to the managedConnection flag. Besides the
     * connection you have to provide a query statement using
     * <source>setSqlStatement(String)</source>
     * 
     * @param conn
     *            the connection provided
     * @param sqlStatement
     *            the sql statement
     * @param managedConnection
     *            if true the connection will be managed (close) by this class
     */
    public SqlConnectionTableInput(Connection conn,
                                        String sqlStatement,
                                        boolean closeConnectionWhenDone) {
        this.dbConnection = conn;
        this.closeConnectionWhenDone = closeConnectionWhenDone;
        this.sqlStatement = sqlStatement;
    }

    /**
     * opens the Input in order to start reading from it
     */
    public void open() {
        super.open();
        try {
            jdbcPrepStatement =
                dbConnection.prepareStatement(sqlStatement,
                                              ResultSet.TYPE_SCROLL_INSENSITIVE,
                                              ResultSet.CONCUR_READ_ONLY);

            resultSetTableInput = new JdbcResultsetTableInput(jdbcPrepStatement.executeQuery());
            resultSetTableInput.open();
        } catch (SQLException e) {
            throw new TableInputException(e);
        }
    }

    /**
     * Closes the input, releases the resources and signals that the reading
     * session is done.
     */
    public void close() {
        try {
            if (resultSetTableInput != null) {
                resultSetTableInput.close();
            }
        } catch (Throwable e) {
            LOGGER.error("error when closing the result set table input", e);
        }

        try {
            if (jdbcPrepStatement != null) {
                jdbcPrepStatement.close();
            }
        } catch (SQLException e) {
            LOGGER.error("error when closing prepared statement", e);
        }

        try {
            if (closeConnectionWhenDone && dbConnection != null && !dbConnection.isClosed()) {
                LOGGER.info("closing the db connection .. ");
                dbConnection.close();
            } else {
                LOGGER.info("external db connection not closed.");
            }
        } catch (SQLException e) {
            LOGGER.error("error when closing db connection", e);
        }

        super.close();
    }

    /**
     * returns the next row
     */
    public List<Object> next() {
        return resultSetTableInput.next();
    }

    /**
     * returns true if there are more rows to read
     */
    public boolean hasNext() {
        return resultSetTableInput.hasNext();
    }

    /**
     * 
     */
    public List<ColumnMetadata> getColumnMetadata() {
        List<ColumnMetadata> result = null;
        if (Status.OPEN.equals(getStatus())) {
            result = resultSetTableInput.getColumnMetadata();
        } else {
            throw new TableInputException("Before calling SqlTableInput.getColumnMetadata() you have to open the input");
        }
        return result;
    }

    boolean hasAllResourcesClosed() throws SQLException {
        boolean result = true;
        if (resultSetTableInput != null) {
            result = resultSetTableInput.hasAllResourcesClosed();
        }
        if (jdbcPrepStatement != null) {
            result = result && jdbcPrepStatement.isClosed();
        }
        if (closeConnectionWhenDone && dbConnection != null) {
            result = result && dbConnection.isClosed();
        }
        return result;
    }
}
