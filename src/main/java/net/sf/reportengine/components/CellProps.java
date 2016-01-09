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
package net.sf.reportengine.components;

import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.config.VertAlign;

/**
 * immutable cell properties class. This is constructed internally to send data
 * to the output. The only use of this class is inside the FreemarkerOutput and
 * all outputs using FreemarkerOutput inside
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public final class CellProps {

    /**
     * the whitespace as a string
     */
    public final static String WHITESPACE = " ";

    /**
     * the column span
     */
    private final int colspan;

    /**
     * the value to be displayed in the cell
     */
    private final Object value;

    /**
     * horizontal alignment
     */
    private final HorizAlign horizAlign;

    /**
     * vertical align
     */
    private final VertAlign vertAlign;

    /**
     * row number
     */
    private final int rowNbr;

    // private Boolean isNumber = null;

    /**
     * constructor using the fluent builder for CellProps
     * 
     * @param propsBuilder
     */
    private CellProps(Builder propsBuilder) {
        this.value = propsBuilder.value;
        this.colspan = propsBuilder.colspan;
        this.horizAlign = propsBuilder.horizAlign;
        this.vertAlign = propsBuilder.vertAlign;
        this.rowNbr = propsBuilder.rowNumber;
    }

    /**
     * column span
     * 
     * @return
     */
    public int getColspan() {
        return colspan;
    }

    /**
     * the value to be displayed
     * 
     * @return
     */
    public Object getValue() {
        return value;
    }

    /**
     * the horizontal align
     * 
     * @return
     */
    public HorizAlign getHorizAlign() {
        return horizAlign;
    }

    /**
     * 
     * @return
     */
    public VertAlign getVertAlign() {
        return vertAlign;
    }

    /**
     * 
     * @return
     */
    public int getRowNumber() {
        return rowNbr;
    }

    // /**
    // * this method lazily initializes the isNumber flag and returns the
    // boolean
    // * value
    // *
    // * @return true if the value object is a number
    // */
    // public boolean isNumber() {
    // if (isNumber == null) {
    // if (value instanceof Number) {
    // isNumber = Boolean.TRUE;
    // } else {
    // if (value instanceof String) {
    // isNumber = Boolean.valueOf(NumberUtils.isNumber((String) value));
    // } else {
    // isNumber = Boolean.FALSE;
    // }
    // }
    // }
    // return isNumber.booleanValue();
    // }

    /**
     * equals
     */
    @Override
    public boolean equals(Object another) {
        boolean result = false;
        if (another instanceof CellProps) {
            CellProps anotherAsCP = (CellProps) another;
            result = value.equals(anotherAsCP.getValue()) && (colspan == anotherAsCP.getColspan())
            // TODO include the horizontal alignment in the equals
            // && (horizAlign.equals(anotherAsCP.getHorizontalAlign()))
            // && (vertAlign.equals(anotherAsCP.getVertAlign()))
            // && (rowNbr == anotherAsCP.getRowNumber())
            ;
        }
        return result;
    }

    /**
     * hashCode
     */
    @Override
    public int hashCode() {
        int result = 3;
        result = 97 * result + value.hashCode();
        result = 97 * result + colspan;
        result = 97 * result + horizAlign.hashCode();
        result = 97 * result + vertAlign.hashCode();
        result = 97 * result + rowNbr;
        return result;
    }

    /**
     * toString
     */
    @Override
    public String toString() {
        return new StringBuilder("CP[").append(value != null ? value.getClass().getSimpleName()
                                                            : "ClassCannotBeIdentified")
                                       .append("(")
                                       .append(value)
                                       .append(")")
                                       .append(",cspan=")
                                       .append(colspan)
                                       .append(", hAlign=")
                                       .append(horizAlign)
                                       .append(", vAlign=")
                                       .append(vertAlign)
                                       .append(", rowNbr=")
                                       .append(rowNbr)
                                       .append("]")
                                       .toString();
    }

    public static CellProps buildEmptyCell() {
        return new CellProps.Builder(WHITESPACE).build();
    }

    /**
     * builder class for CellProps
     * 
     * @author dragos balan
     *
     */
    public static class Builder {

        private final Object value;
        private int colspan = 1;

        private HorizAlign horizAlign = HorizAlign.CENTER;
        private VertAlign vertAlign = VertAlign.MIDDLE;

        private int rowNumber = 0;

        public Builder(Object value) {
            this.value = value;
        }

        public Builder colspan(int colspan) {
            this.colspan = colspan;
            return this;
        }

        public Builder horizAlign(HorizAlign align) {
            this.horizAlign = align;
            return this;
        }

        public Builder vertAlign(VertAlign align) {
            this.vertAlign = align;
            return this;
        }

        public Builder rowNumber(int rowNbr) {
            this.rowNumber = rowNbr;
            return this;
        }

        public CellProps build() {
            return new CellProps(this);
        }
    }
}