package net.sf.reportengine.config;

import net.sf.reportengine.core.algorithm.NewRowEvent;

public interface TableColumn {

    /**
     * returns the header of the column.
     * The header will be displayed in the final report as the header of this column and
     * it shouldn't be confused with the column name in case the report input is an SQL query
     *
     * @return the header of the report column
     */
    String getHeader();

    /**
     * retrieves the value for this column.
     * This is the most important method as it retrieves the data for the row-column combination.
     *
     * @param newRowEvent the event containing the new row of data as an array
     * @return	the computed value for this column
     */
    Object getValue(NewRowEvent newRowEvent);

    /**
     * returns the formatted value ready to be displayed in the report
     *
     * @param value	the unformatted value
     * @return	the formatted value
     */
    String getFormattedValue(Object value);

    /**
     * returns the horizontal alignment of the values of this column
     */
    HorizAlign getHorizAlign();

    /**
     * returns the vertical alignment of the values of this column
     *
     * @return     the vertical alignment for this column
     */
    VertAlign getVertAlign();

    /**
     * returns the horizontal alignment of the header for this column
     */
    default HorizAlign getHeaderHorizAlign(){
        return HorizAlign.CENTER;
    }

    /**
     * getter for the sort type (asc or desc)
     *
     * @return the sort type
     */
    SortType getSortType();



}
