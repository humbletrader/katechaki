package net.sf.reportengine.scenarios;

import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.AbstractDataColumn;
import net.sf.reportengine.config.AbstractGroupColumn;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.CalcIntermResult;
import net.sf.reportengine.core.calc.CountGroupCalculator;
import net.sf.reportengine.core.calc.DefaultCalcIntermResult;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.in.InMemoryTableInput;
import net.sf.reportengine.in.TableInput;

/**
 * @author dragos balan
 *
 */
public class CalculatedColumnsScenario {
	
	public static final Object[][] RAW_DATA = new Object[][]{
		new String[]{"1","a","2","b","3","c"},
		new String[]{"1","a","2","b","3","c"},
		new String[]{"0","a","2","b","3","c"},
		new String[]{"0","a","2","b","3","c"},
		new String[]{"0","a","4","b","3","c"},
		new String[]{"0","a","4","b","3","c"},
		new String[]{"0","a","4","b","3","c"},
		new String[]{"0","a","4","b","6","c"},
		new String[]{"0","a","4","b","6","c"},
		new String[]{"0","a","4","b","6","c"}
	};
	
	public static final Object[][] PREVIOUS_GROUP_VALUES = new Object[][]{
		new Object[]{"1",3,"2","3"},
		new Object[]{"1",3,"2","3"},
		new Object[]{"0",2,"2","3"},
		new Object[]{"0",2,"2","3"},
		new Object[]{"0",4,"4","3"},
		new Object[]{"0",4,"4","3"},
		new Object[]{"0",4,"4","3"},
		new Object[]{"0",4,"4","6"},
		new Object[]{"0",4,"4","6"},
		new Object[]{"0",4,"4","6"}
	};
	
	
	
	 public final static TableInput INPUT = new InMemoryTableInput(RAW_DATA);
	    
	public static final List<GroupColumn> GROUP_COLUMNS = Arrays.asList(new GroupColumn[]{
		new DefaultGroupColumn.Builder(0).header("Zero or One").build(), 
		new AbstractGroupColumn("Computed 0+2", 1, null, HorizAlign.CENTER, false) {
			public Integer getValue(NewRowEvent newRowEvent) {
				List<Object> data = newRowEvent.getInputDataRow();
				return Integer.valueOf((String)data.get(0))+Integer.valueOf((String)data.get(2));
			}	
		}, 
		new DefaultGroupColumn.Builder(2).header("2 multiples").build(), 
		new DefaultGroupColumn.Builder(4).header("3 Multiples").level(3).build(),
		
	});
	
	public static final List<? extends DataColumn> DATA_COLUMNS = Arrays.asList(
		new DefaultDataColumn.Builder(1).header("Column A").build(),
		new DefaultDataColumn.Builder(3).header("Column B").build(), 
		new DefaultDataColumn.Builder(5).header("Column C").useCalculator(new CountGroupCalculator()).build(),
		new AbstractDataColumn("0+3", null, null, HorizAlign.CENTER) {
			public String getValue(NewRowEvent newRowEvent) {
				List<Object> data = newRowEvent.getInputDataRow();
				return ""+data.get(0)+data.get(3);
			}
		}
	);
	
	
	public final static Object[][] COMPUTED_VALUES = new Object[][]{
		 new Object[]{"1",3, "2", "3", 		"a","b","c", "1b"},
		 new Object[]{"1",3, "2", "3", 		"a","b","c", "1b"},
		 new Object[]{"0",2, "2", "3", 		"a","b","c", "0b"},
		 new Object[]{"0",2, "2", "3", 		"a","b","c", "0b"},
		 new Object[]{"0",4, "4", "3", 		"a","b","c", "0b"},
		 new Object[]{"0",4, "4", "3", 		"a","b","c", "0b"},
		 new Object[]{"0",4, "4", "3", 		"a","b","c", "0b"},
		 new Object[]{"0",4, "4", "6", 		"a","b","c", "0b"},
		 new Object[]{"0",4, "4", "6", 		"a","b","c", "0b"},
		 new Object[]{"0",4, "4", "6", 		"a","b","c", "0b"}
	};
	
	public final static Integer[] AGG_LEVEL = new Integer[]{
			-1,
			-1, 
			0,
			-1, 
			1,
			-1,
			-1,
			3,
			-1,
			-1
	};
	
	public final static int[] AGG_COLUMNS_INDEX = new int[]{0,1,3,5};
	
	public final static CalcIntermResult[][] CALCULATORS_RESULTS = new CalcIntermResult[][]{
	    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(3))},
	    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(6))},
	    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(6))},
	    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(8))},
	    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(10))}
		};
}

