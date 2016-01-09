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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.config.SortType;
import net.sf.reportengine.config.VertAlign;
import net.sf.reportengine.in.ColumnMetadata;
import net.sf.reportengine.in.ColumnPreferences;

/**
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.8
 */
public final class ReportUtils {

    /**
     * the one and only logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportUtils.class);

    /**
     * debug flag
     */
    public static final boolean DEBUG = Boolean.parseBoolean(System.getProperty("report.debug",
                                                                                "false"));

    /**
     * the private constructor
     */
    private ReportUtils() {
    }

    /**
     * check if all provided data columns have calculators assigned
     * 
     * @return
     */
    public static boolean atLeastOneDataColumHasCalculators(List<DataColumn> dataColumns) {
        boolean atLeastOneCalculator = false;
        for (DataColumn dataColumn : dataColumns) {
            if (dataColumn.getCalculator() != null) {
                atLeastOneCalculator = true;
                break;
            }
        }
        return atLeastOneCalculator;
    }

    /**
     * 
     * @param columnIndex
     * @param prefs
     * @param metadata
     * @return
     */
    public static DataColumn createDataColumn(int columnIndex,
                                              ColumnPreferences prefs,
                                              ColumnMetadata metadata) {
        DefaultDataColumn result = new DefaultDataColumn();
        result.setHeader(prefs.getHeader() != null
                                                  ? prefs.getHeader()
                                                  : metadata.getColumnLabel() != null
                                                                                     ? metadata.getColumnLabel()
                                                                                     : "Column " + columnIndex);
        result.setHorizAlign(prefs.getHorizAlign() != null
                                                          ? prefs.getHorizAlign()
                                                          : metadata.getHorizontalAlign() != null
                                                                                                 ? metadata.getHorizontalAlign()
                                                                                                 : HorizAlign.CENTER);
        result.setVertAlign(prefs.getVertAlign() != null ? prefs.getVertAlign() : VertAlign.MIDDLE);
        result.setInputColumnIndex(columnIndex);
        result.setCalculator(prefs.getCalculator());
        result.setValuesFormatter(prefs.getValuesFormatter());
        result.setTotalsFormatter(prefs.getTotalsFormatter());

        if (prefs.getSortLevel() > DataColumn.NO_SORTING) {
            LOGGER.debug("computing sorting for column={} from prefs={} and colIndex={}",
                         result.getHeader(),
                         prefs.getSortLevel(),
                         columnIndex);
            result.setSortLevel(prefs.getSortLevel() == ColumnPreferences.DEFAULT_SORT_LEVEL
                                                                                            ? columnIndex
                                                                                            : prefs.getSortLevel());
            result.setSortType(prefs.getSortType());
        } else {
            LOGGER.debug("no sorting found for column {} ", result.getHeader());
        }

        return result;
    }

    /**
     * 
     * @param columnIndex
     * @param metadata
     * @return
     */
    public static DataColumn createDataColumn(int columnIndex, ColumnMetadata metadata) {
        DefaultDataColumn result = new DefaultDataColumn();
        result.setHeader(metadata.getColumnLabel() != null ? metadata.getColumnLabel()
                                                          : "Column " + columnIndex);
        result.setHorizAlign(metadata.getHorizontalAlign() != null ? metadata.getHorizontalAlign()
                                                                  : HorizAlign.CENTER);
        result.setInputColumnIndex(columnIndex);
        return result;
    }

    /**
     * 
     * @param columnIndex
     * @param groupingLevel
     * @param prefs
     * @param metadata
     * @return
     */
    public static GroupColumn createGroupColumn(int columnIndex,
                                                int groupingLevel,
                                                ColumnPreferences prefs,
                                                ColumnMetadata metadata) {
        DefaultGroupColumn result = new DefaultGroupColumn();
        result.setHeader(prefs.getHeader() != null
                                                  ? prefs.getHeader()
                                                  : metadata.getColumnLabel() != null
                                                                                     ? metadata.getColumnLabel()
                                                                                     : "Column " + columnIndex);
        result.setHorizAlign(prefs.getHorizAlign() != null
                                                          ? prefs.getHorizAlign()
                                                          : metadata.getHorizontalAlign() != null
                                                                                                 ? metadata.getHorizontalAlign()
                                                                                                 : HorizAlign.CENTER);
        result.setVertAlign(prefs.getVertAlign() != null ? prefs.getVertAlign() : VertAlign.MIDDLE);
        result.setInputColumnIndex(columnIndex);
        result.setGroupingLevel(groupingLevel);

        if (prefs.getSortType() != null) {
            result.setSortType(prefs.getSortType());
        }

        return result;
    }

    /**
     * checks if any of the preferences contain any sorting
     * 
     * @param userColumnPrefs
     * @return
     */
    public static boolean isSortingInPreferences(Map<String, ColumnPreferences> userColumnPrefs) {
        boolean result = false;
        for (ColumnPreferences prefElem : userColumnPrefs.values()) {
            if (prefElem.getSortLevel() > DataColumn.NO_SORTING) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 
     * @param groupCols
     * @param dataCols
     * @return
     */
    public static boolean
            isSortingInColumns(List<GroupColumn> groupCols, List<DataColumn> dataCols) {
        boolean result = false;

        if (groupCols != null && !groupCols.isEmpty()) {
            for (GroupColumn groupCol : groupCols) {
                if (groupCol.getSortType() != null && groupCol.getSortType() != SortType.NONE) {
                    result = true;
                    break;
                }
            }
        }

        if (!result && dataCols != null && !dataCols.isEmpty()) {
            for (DataColumn dataCol : dataCols) {
                if (dataCol.getSortLevel() > DataColumn.NO_SORTING) {
                    result = true;
                    break;
                }
            }
        }

        LOGGER.debug("checking if group cols {} or data cols {} need sorting ? {}",
                     groupCols,
                     dataCols,
                     result);

        return result;
    }

    public static BigDecimal createBigDecimal(Object value) {
        BigDecimal valueAsBd = null;
        if (value instanceof Number) {
            valueAsBd = new BigDecimal(((Number) value).doubleValue());
        } else {
            if (value instanceof String) {
                valueAsBd = new BigDecimal((String) value);
            } else {
                valueAsBd = new BigDecimal(value.toString());
            }
        }

        return valueAsBd;
    }
}
