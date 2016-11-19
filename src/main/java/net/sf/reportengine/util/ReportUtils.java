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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.SortType;

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
     * @param groupCols
     * @param dataCols
     * @return
     */
    public static boolean isSortingInColumns(List<GroupColumn> groupCols, List<DataColumn> dataCols) {
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
