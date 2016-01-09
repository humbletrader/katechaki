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
package net.sf.reportengine.config;

import net.sf.reportengine.core.calc.GroupCalculator;

/**
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public abstract class AbstractPivotData implements PivotData {

    /**
	 * 
	 */
    private final String valuesFormatter;

    /**
	 * 
	 */
    private final String totalsFormatter;

    /**
	 * 
	 */
    private final GroupCalculator calculator;

    /**
     * the horizontal alignment for this crosstab data
     */
    private final HorizAlign horizAlign;

    /**
     * the vertical alignment for this crosstab data
     */
    private final VertAlign vertAlign;

    /**
     * 
     * @param calc
     * @param formatter
     * @param horizAlign
     */
    public AbstractPivotData(GroupCalculator calc, String valuesFormatter, HorizAlign horizAlign) {
        this(calc, valuesFormatter, horizAlign, VertAlign.MIDDLE);
    }

    /**
     * 
     * @param calc
     * @param valuesFormatter
     * @param horizAlign
     * @param vertAlign
     */
    public AbstractPivotData(GroupCalculator calc,
                             String valuesFormatter,
                             HorizAlign horizAlign,
                             VertAlign vertAlign) {
        this(calc, valuesFormatter, null, horizAlign, vertAlign);
    }

    /**
     * 
     * @param calc
     * @param valuesFormatter
     * @param totalsFormatter
     * @param horizAlign
     * @param vertAlign
     */
    public AbstractPivotData(GroupCalculator calc,
                             String valuesFormatter,
                             String totalsFormatter,
                             HorizAlign horizAlign,
                             VertAlign vertAlign) {
        this.calculator = calc;
        this.valuesFormatter = valuesFormatter;
        this.totalsFormatter = totalsFormatter;
        this.horizAlign = horizAlign;
        this.vertAlign = vertAlign;
    }

    /**
	 * 
	 */
    public String getFormattedValue(Object unformattedValue) {
        String result = "";
        if (unformattedValue != null) {
            if (valuesFormatter != null) {
                result = String.format(valuesFormatter, unformattedValue);
            } else {
                result = unformattedValue.toString();
            }
        }
        return result;
    }

    /**
	 * 
	 */
    public String getFormattedTotal(Object unformattedValue) {
        String result = "";
        if (unformattedValue != null) {
            if (totalsFormatter != null) {
                result = String.format(totalsFormatter, unformattedValue);
            } else {
                result = unformattedValue.toString();
            }
        }
        return result;
    }

    public String getValuesFormatter() {
        return valuesFormatter;
    }

    public String getTotalsFormatter() {
        return totalsFormatter;
    }

    public GroupCalculator getCalculator() {
        return calculator;
    }

    /**
     * @return the horizAlign
     */
    public HorizAlign getHorizAlign() {
        return horizAlign;
    }

    /**
     * @return the vertical alignment
     */
    public VertAlign getVertAlign() {
        return vertAlign;
    }

}