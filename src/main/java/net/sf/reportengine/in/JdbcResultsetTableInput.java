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

import static java.sql.Types.BIGINT;
import static java.sql.Types.DECIMAL;
import static java.sql.Types.DOUBLE;
import static java.sql.Types.FLOAT;
import static java.sql.Types.INTEGER;
import static java.sql.Types.LONGNVARCHAR;
import static java.sql.Types.NCHAR;
import static java.sql.Types.NUMERIC;
import static java.sql.Types.NVARCHAR;
import static java.sql.Types.REAL;
import static java.sql.Types.SMALLINT;
import static java.sql.Types.TINYINT;
import static java.sql.Types.VARCHAR;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.config.HorizAlign;

/**
 * @author dragos balan
 *
 */
public class JdbcResultsetTableInput extends AbstractTableInput implements ColumnMetadataHolder {

    /**
     * the result set holding query results
     */
    private final ResultSet resultSet;

    /**
     * next row with data
     */
    private List<Object> nextRow;

    /**
     * hasMoreRows flag
     */
    private boolean hasMoreRows = false;

    /**
     * columns count
     */
    private int columnsCount = Integer.MIN_VALUE;

    /**
     * the metadata for the columns
     */
    private List<ColumnMetadata> columnMetadata;

    /**
     * 
     * @param resultSet
     */
    public JdbcResultsetTableInput(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    /**
     * opens the Input in order to start reading from it
     */
    public void open() {
        super.open();
        try {
            // now reading the metadata
            columnMetadata = readMetaData(resultSet);

            hasMoreRows = resultSet.first();
        } catch (SQLException e) {
            throw new TableInputException("Exception opening the report table input", e);
        }
    }

    /**
     * Closes the input meaning : "the reading session it's done !"
     */
    public void close() throws TableInputException {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            throw new TableInputException("Exception closing the resultset", e);
        } finally {
            super.close();
        }
    }

    /**
     * returns the next row
     */
    public List<Object> next() throws TableInputException {
        try {
            if (hasNext()) {
                nextRow = new ArrayList<Object>(columnsCount);
                for (int i = 0; i < columnsCount; i++) {
                    nextRow.add(resultSet.getObject(i + 1));
                }
            } else {
                nextRow = null;
            }

            // moving the pointer to the next row
            hasMoreRows = resultSet.next();

        } catch (SQLException e) {
            throw new TableInputException("An SQL Exception occured !", e);
        }
        return nextRow;
    }

    /**
     * returns true if there are more rows to read
     */
    public boolean hasNext() {
        return hasMoreRows;

    }

    public List<ColumnMetadata> getColumnMetadata() {
        return columnMetadata;
    }

    /**
     * reads the metadata from the result set for getting the column names,
     * column types and other
     * 
     * @throws TableInputException
     */
    private List<ColumnMetadata> readMetaData(ResultSet rs) throws SQLException {
        columnsCount = rs.getMetaData().getColumnCount();
        return extractMetadata(resultSet.getMetaData());
    }

    /**
     * 
     * @param rsMetadata
     * @return
     * @throws SQLException
     */
    private List<ColumnMetadata> extractMetadata(ResultSetMetaData rsMetadata) throws SQLException {
        List<ColumnMetadata> columnMetadata =
            new ArrayList<ColumnMetadata>(rsMetadata.getColumnCount());
        // looping through columns to get their type
        for (int i = 0; i < columnsCount; i++) {
            columnMetadata.add(new ColumnMetadata(rsMetadata.getColumnName(i + 1),
                                                  rsMetadata.getColumnLabel(i + 1),
                                                  getAlignmentFromColumnType(rsMetadata.getColumnType(i + 1))));
        }
        return columnMetadata;
    }

    /**
     * 
     * @param colType
     * @return
     */
    private HorizAlign getAlignmentFromColumnType(int colType) {
        HorizAlign result = null;
        if (colType == INTEGER || colType == BIGINT || colType == DECIMAL || colType == DOUBLE || colType == NUMERIC || colType == FLOAT || colType == REAL || colType == SMALLINT || colType == TINYINT) {
            result = HorizAlign.RIGHT;
        } else {
            if (colType == LONGNVARCHAR || colType == NCHAR || colType == NVARCHAR || colType == VARCHAR) {
                result = HorizAlign.LEFT;
            } else {
                result = HorizAlign.CENTER;
            }
        }

        return result;
    }

    boolean hasAllResourcesClosed() throws SQLException {
        boolean result = true;
        if (resultSet != null) {
            result = resultSet.isClosed();
        }
        return result;
    }
}
