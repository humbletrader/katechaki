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
package net.sf.reportengine.scenarios;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.components.CellProps;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.calc.CalcIntermResult;
import net.sf.reportengine.core.calc.CountGroupCalculator;
import net.sf.reportengine.core.calc.DefaultCalcIntermResult;
import net.sf.reportengine.core.calc.GroupCalculator;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.core.calc.SumGroupCalculator;
import net.sf.reportengine.in.InMemoryTableInput;
import net.sf.reportengine.in.TableInput;

/**
 * @author dragos balan
 *
 */
public class Scenario1 {
	
	public static boolean[] SHOW_TOTALS_ON_GROUP_LEVEL = new boolean[]{true, true, true};
	public static boolean SHOW_GRAND_TOTAL = true;
	public static final int[] AGG_COLUMNS_INDEX = new int []{0,1,2};
	
	public static final List<Object> ROW_OF_DATA_1 = 
			Arrays.asList(new Object[]{"1","2","3",    "4",  "5",6});
	public static final List<Object> ROW_OF_DATA_2 = 
			Arrays.asList(new Object[]{"1","2","3",    "3",  "3",3});
	public static final List<Object> ROW_OF_DATA_3 = 
			Arrays.asList(new Object[]{"1","2","2",    "2",  "2",2});
	public static final List<Object> ROW_OF_DATA_4 = 
			Arrays.asList(new Object[]{"1","1","1",    "1",  "1",1});
	public static final List<Object> ROW_OF_DATA_5 = 
			Arrays.asList(new Object[]{"1","1","1",    "1",  "1",1});
	public static final List<Object> ROW_OF_DATA_6 = 
			Arrays.asList(new Object[]{"7","1","1",    "1",  "7",1});
	
	public static final Object[][] RAW_DATA = new Object[][]{
		ROW_OF_DATA_1.toArray(new Object[]{}),
		ROW_OF_DATA_2.toArray(new Object[]{}),
		ROW_OF_DATA_3.toArray(new Object[]{}),
		ROW_OF_DATA_4.toArray(new Object[]{}),
		ROW_OF_DATA_5.toArray(new Object[]{}),
		ROW_OF_DATA_6.toArray(new Object[]{})
	};
	
	public static final Object[][] PREVIOUS_GROUP_VALUES = new Object[][]{
		new String[]{"1","2","3"}, 
		new String[]{"1","2","3"},
		new String[]{"1","2","2"},
		new String[]{"1","1","1"},
		new String[]{"1","1","1"},
		new String[]{"7","1","1"},
	};
	
	
	public static final List<GroupColumn> GROUPING_COLUMNS = Arrays.asList(
		new GroupColumn[]{
				new DefaultGroupColumn.Builder(0).level(0).header("col 0").showDuplicateValues().build(), 
				new DefaultGroupColumn.Builder(1).level(1).header("col 1").showDuplicateValues().build(), 
				new DefaultGroupColumn.Builder(2).level(2).header("col 2").showDuplicateValues().build()
		});
	
	public static final List<GroupColumn> GROUPING_COLUMNS_WITH_SORTING = Arrays.asList(
			new GroupColumn[]{
					new DefaultGroupColumn.Builder(0).level(0).header("col 0").showDuplicateValues().sortAsc().build(), 
					new DefaultGroupColumn.Builder(1).level(1).header("col 1").showDuplicateValues().sortAsc().build(), 
					new DefaultGroupColumn.Builder(2).level(2).header("col 2").showDuplicateValues().sortAsc().build()
			});
	
	public static final List<DataColumn> DATA_COLUMNS = Arrays.asList(
			new DataColumn[]{
		new DefaultDataColumn.Builder(3).header("col 3").build(), 
		new DefaultDataColumn.Builder(4).header("col 4").useCalculator(new CountGroupCalculator()).build(), 
		new DefaultDataColumn.Builder(5).header("col 5").useCalculator(new SumGroupCalculator()).build()
	});
	
	public static final ArrayList<Integer> DISTRIBUTION_OF_CALCULATOR_IN_DATA_ROW_ARRAY = new ArrayList<Integer>(Arrays.asList(
				Integer.valueOf(-1),
				Integer.valueOf(0),
				Integer.valueOf(1)));  
	
	public static final int ROW_1_AGG_LEVEL = -1;
	public static final int ROW_2_AGG_LEVEL = -1;
	public static final int ROW_3_AGG_LEVEL = 2;
	public static final int ROW_4_AGG_LEVEL = 1;
	public static final int ROW_5_AGG_LEVEL = -1;
	public static final int ROW_6_AGG_LEVEL = 0;
	
	public static final Integer[] AGG_LEVEL = new Integer[]{
		ROW_1_AGG_LEVEL,
		ROW_2_AGG_LEVEL,
		ROW_3_AGG_LEVEL,
		ROW_4_AGG_LEVEL,
		ROW_5_AGG_LEVEL, 
		ROW_6_AGG_LEVEL
	};
	
	public final static GroupCalculator[] TEST_CALCULATOR_PROTOTYPES = 
			new GroupCalculator[]{GroupCalculators.COUNT, GroupCalculators.SUM};
	
    public final static int[] TEST_COLUMNS_TO_COMPUTE_TOTALS_ON = new int[]{4,5};
    
    public final static CalcIntermResult[][] ROW_1_CALCULATORS_RESULTS = new CalcIntermResult[][]{
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(1)),new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(6))},
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(1)),new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(6))},
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(1)),new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(6))},
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(1)),new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(6))}
    															};
    
    public final static CalcIntermResult[][] ROW_2_CALCULATORS_RESULTS = new CalcIntermResult[][]{
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(2)),new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(9))},
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(2)),new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(9))},
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(2)),new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(9))},
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(2)),new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(9))}
															};
    
    public final static CalcIntermResult[][] ROW_3_CALCULATORS_RESULTS = new CalcIntermResult[][]{
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(1)),new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(2))},
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(3)),new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(11))},
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(3)),new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(11))},
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(3)),new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(11))}
															};
    public final static CalcIntermResult[][] ROW_4_CALCULATORS_RESULTS = new CalcIntermResult[][]{
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(1)),new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(1))},
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(1)),new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(1))},
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(4)),new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(12))},
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(4)),new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(12))}
	};
    
    public final static CalcIntermResult[][] ROW_5_CALCULATORS_RESULTS = new CalcIntermResult[][]{
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(2)),new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(2))},
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(2)),new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(2))},
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(5)),new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(13))},
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(5)),new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(13))}
	};
    
    public final static CalcIntermResult[][] ROW_6_CALCULATORS_RESULTS = new CalcIntermResult[][]{
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(1)),new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(1))},
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(1)),new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(1))},
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(1)),new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(1))},
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(6)),new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(14))}
	};
	
    public final static TableInput INPUT = new InMemoryTableInput(RAW_DATA);
    
	public final static CellProps[][] OUTPUT_TOTALS = new CellProps[][]{
			//displayed on row 3
			new CellProps[]{
					new CellProps.Builder("Count Total 3").build(), 
					CellProps.buildEmptyCell(),
					CellProps.buildEmptyCell(),
					CellProps.buildEmptyCell(), 
					new CellProps.Builder(""+ROW_2_CALCULATORS_RESULTS[0][0].getResult()).build(), 
					new CellProps.Builder(""+ROW_2_CALCULATORS_RESULTS[0][1].getResult()).build()
			},
			
			//displayed on row 4
			new CellProps[]{
					new CellProps.Builder("Count Total 2").build(), 
					CellProps.buildEmptyCell(),
					CellProps.buildEmptyCell(),
					CellProps.buildEmptyCell(),
					new CellProps.Builder(""+ROW_3_CALCULATORS_RESULTS[0][0].getResult()).build(), 
					new CellProps.Builder(""+ROW_3_CALCULATORS_RESULTS[0][1].getResult()).build()
			}, 
			new CellProps[]{
					new CellProps.Builder("Count Total 2").build(),
					CellProps.buildEmptyCell(),
					CellProps.buildEmptyCell(),
					CellProps.buildEmptyCell(),
					new CellProps.Builder(""+ROW_3_CALCULATORS_RESULTS[1][0].getResult()).build(),
					new CellProps.Builder(""+ROW_3_CALCULATORS_RESULTS[1][1].getResult()).build()
			},
			
			//displayed on row 6
			new CellProps[]{
					new CellProps.Builder("Count Total 1").build(), 
					CellProps.buildEmptyCell(),
					CellProps.buildEmptyCell(),
					CellProps.buildEmptyCell(),
					new CellProps.Builder(""+ROW_5_CALCULATORS_RESULTS[0][0].getResult()).build(), 
					new CellProps.Builder(""+ROW_5_CALCULATORS_RESULTS[0][1].getResult()).build()
			},
			new CellProps[]{
					new CellProps.Builder("Count Total 1").build(), 
					CellProps.buildEmptyCell(),
					CellProps.buildEmptyCell(),
					CellProps.buildEmptyCell(),
					new CellProps.Builder(""+ROW_5_CALCULATORS_RESULTS[1][0].getResult()).build(), 
					new CellProps.Builder(""+ROW_5_CALCULATORS_RESULTS[1][1].getResult()).build()
			}, 
			new CellProps[]{
					new CellProps.Builder("Count Total 1").build(),
					CellProps.buildEmptyCell(),
					CellProps.buildEmptyCell(),
					CellProps.buildEmptyCell(),
					new CellProps.Builder(""+ROW_5_CALCULATORS_RESULTS[2][0].getResult()).build(),
					new CellProps.Builder(""+ROW_5_CALCULATORS_RESULTS[2][1].getResult()).build()
			},
			
			//displayed on exit
			new CellProps[]{
					new CellProps.Builder("Count Total 1").build(), 
					CellProps.buildEmptyCell(),
					CellProps.buildEmptyCell(),
					CellProps.buildEmptyCell(),
					new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[0][0].getResult()).build(), 
					new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[0][1].getResult()).build()
			},
			new CellProps[]{
					new CellProps.Builder("Count Total 1").build(), 
					CellProps.buildEmptyCell(),
					CellProps.buildEmptyCell(),
					CellProps.buildEmptyCell(),
					new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[1][0].getResult()).build(), 
					new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[1][1].getResult()).build()
			}, 
			new CellProps[]{
					new CellProps.Builder("Count Total 7").build(),
					CellProps.buildEmptyCell(),
					CellProps.buildEmptyCell(),
					CellProps.buildEmptyCell(),
					new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[2][0].getResult()).build(),
					new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[2][1].getResult()).build()
			},
			new CellProps[]{
					new CellProps.Builder("Grand Count Total ").build(),
					CellProps.buildEmptyCell(),
					CellProps.buildEmptyCell(),
					CellProps.buildEmptyCell(),
					new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[3][0].getResult()).build(),
					new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[3][1].getResult()).build()
			}
	};
	
	public final static CellProps[][] EXPECTED_OUTPUT_DATA = new CellProps[][]{
		//displayed on row 1
		new CellProps[]{
				new CellProps.Builder(ROW_OF_DATA_1.get(0)).build(), 
				new CellProps.Builder(ROW_OF_DATA_1.get(1)).build(), 
				new CellProps.Builder(ROW_OF_DATA_1.get(2)).build(), 
				new CellProps.Builder(ROW_OF_DATA_1.get(3)).build(), 
				new CellProps.Builder(ROW_OF_DATA_1.get(4)).build(), 
				new CellProps.Builder(ROW_OF_DATA_1.get(5).toString()).build() 
		},
		//displayed on row 2
		new CellProps[]{
				new CellProps.Builder(ROW_OF_DATA_2.get(0)).build(), 
				new CellProps.Builder(ROW_OF_DATA_2.get(1)).build(), 
				new CellProps.Builder(ROW_OF_DATA_2.get(2)).build(), 
				new CellProps.Builder(ROW_OF_DATA_2.get(3)).build(), 
				new CellProps.Builder(ROW_OF_DATA_2.get(4)).build(), 
				new CellProps.Builder(ROW_OF_DATA_2.get(5).toString()).build() 
		},
		//displayed on row 3
		new CellProps[]{
				new CellProps.Builder(ROW_OF_DATA_3.get(0)).build(), 
				new CellProps.Builder(ROW_OF_DATA_3.get(1)).build(), 
				new CellProps.Builder(ROW_OF_DATA_3.get(2)).build(), 
				new CellProps.Builder(ROW_OF_DATA_3.get(3)).build(), 
				new CellProps.Builder(ROW_OF_DATA_3.get(4)).build(), 
				new CellProps.Builder(ROW_OF_DATA_3.get(5).toString()).build() 
		},
		//displayed on row 4
		new CellProps[]{
				new CellProps.Builder(ROW_OF_DATA_4.get(0)).build(), 
				new CellProps.Builder(ROW_OF_DATA_4.get(1)).build(), 
				new CellProps.Builder(ROW_OF_DATA_4.get(2)).build(), 
				new CellProps.Builder(ROW_OF_DATA_4.get(3)).build(), 
				new CellProps.Builder(ROW_OF_DATA_4.get(4)).build(), 
				new CellProps.Builder(ROW_OF_DATA_4.get(5).toString()).build() 
		},
		//displayed on row 5
		new CellProps[]{
				new CellProps.Builder(ROW_OF_DATA_5.get(0)).build(), 
				new CellProps.Builder(ROW_OF_DATA_5.get(1)).build(), 
				new CellProps.Builder(ROW_OF_DATA_5.get(2)).build(), 
				new CellProps.Builder(ROW_OF_DATA_5.get(3)).build(), 
				new CellProps.Builder(ROW_OF_DATA_5.get(4)).build(), 
				new CellProps.Builder(ROW_OF_DATA_5.get(5).toString()).build() 
		},
		//displayed on row 6
		new CellProps[]{
				new CellProps.Builder(ROW_OF_DATA_6.get(0)).build(), 
				new CellProps.Builder(ROW_OF_DATA_6.get(1)).build(), 
				new CellProps.Builder(ROW_OF_DATA_6.get(2)).build(), 
				new CellProps.Builder(ROW_OF_DATA_6.get(3)).build(), 
				new CellProps.Builder(ROW_OF_DATA_6.get(4)).build(), 
				new CellProps.Builder(ROW_OF_DATA_6.get(5).toString()).build() 
		}
	};
	
	public final static CellProps[][] EXPECTED_REPORT_COLUMNS_HEADER = new CellProps[][]{
		new CellProps[]{
			new CellProps.Builder("col 0").build(),
			new CellProps.Builder("col 1").build(),	
			new CellProps.Builder("col 2").build(),
			new CellProps.Builder("col 3").build(),
			new CellProps.Builder("col 4").build(),
			new CellProps.Builder("col 5").build()
		}}; 
	
	public final static CellProps[][] EXPECTED_OUTPUT_UNSORTED = new CellProps[][]{
	
		//displayed on row 1
		new CellProps[]{
				new CellProps.Builder(ROW_OF_DATA_1.get(0)).build(), 
				new CellProps.Builder(ROW_OF_DATA_1.get(1)).build(), 
				new CellProps.Builder(ROW_OF_DATA_1.get(2)).build(), 
				new CellProps.Builder(ROW_OF_DATA_1.get(3)).build(), 
				new CellProps.Builder(ROW_OF_DATA_1.get(4)).build(), 
				new CellProps.Builder(ROW_OF_DATA_1.get(5).toString()).build() 
		},
		//displayed on row 2
		new CellProps[]{
				new CellProps.Builder(ROW_OF_DATA_2.get(0)).build(), 
				new CellProps.Builder(ROW_OF_DATA_2.get(1)).build(), 
				new CellProps.Builder(ROW_OF_DATA_2.get(2)).build(), 
				new CellProps.Builder(ROW_OF_DATA_2.get(3)).build(), 
				new CellProps.Builder(ROW_OF_DATA_2.get(4)).build(), 
				new CellProps.Builder(ROW_OF_DATA_2.get(5).toString()).build() 
		},
		
		//totals displayed on row 3
		new CellProps[]{
				new CellProps.Builder("Count Total 3").build(), 
				CellProps.buildEmptyCell(),
				CellProps.buildEmptyCell(),
				CellProps.buildEmptyCell(),
				new CellProps.Builder(""+ROW_2_CALCULATORS_RESULTS[0][0].getResult()).build(), 
				new CellProps.Builder(""+ROW_2_CALCULATORS_RESULTS[0][1].getResult()).build()
		},
		
		//displayed on row 3
		new CellProps[]{
				new CellProps.Builder(ROW_OF_DATA_3.get(0)).build(), 
				new CellProps.Builder(ROW_OF_DATA_3.get(1)).build(), 
				new CellProps.Builder(ROW_OF_DATA_3.get(2)).build(), 
				new CellProps.Builder(ROW_OF_DATA_3.get(3)).build(), 
				new CellProps.Builder(ROW_OF_DATA_3.get(4)).build(), 
				new CellProps.Builder(ROW_OF_DATA_3.get(5).toString()).build() 
		},
		
		//totals displayed on row 4
		new CellProps[]{
				new CellProps.Builder("Count Total 2").build(), 
				CellProps.buildEmptyCell(),
				CellProps.buildEmptyCell(),
				CellProps.buildEmptyCell(),
				new CellProps.Builder(""+ROW_3_CALCULATORS_RESULTS[0][0].getResult()).build(), 
				new CellProps.Builder(""+ROW_3_CALCULATORS_RESULTS[0][1].getResult()).build()
		}, 
		new CellProps[]{
				new CellProps.Builder("Count Total 2").build(),
				CellProps.buildEmptyCell(),
				CellProps.buildEmptyCell(),
				CellProps.buildEmptyCell(),
				new CellProps.Builder(""+ROW_3_CALCULATORS_RESULTS[1][0].getResult()).build(),
				new CellProps.Builder(""+ROW_3_CALCULATORS_RESULTS[1][1].getResult()).build()
		},
		
		//displayed on row 4
		new CellProps[]{
				new CellProps.Builder(ROW_OF_DATA_4.get(0)).build(), 
				new CellProps.Builder(ROW_OF_DATA_4.get(1)).build(), 
				new CellProps.Builder(ROW_OF_DATA_4.get(2)).build(), 
				new CellProps.Builder(ROW_OF_DATA_4.get(3)).build(), 
				new CellProps.Builder(ROW_OF_DATA_4.get(4)).build(), 
				new CellProps.Builder(ROW_OF_DATA_4.get(5).toString()).build() 
		},
		//displayed on row 5
		new CellProps[]{
				new CellProps.Builder(ROW_OF_DATA_5.get(0)).build(), 
				new CellProps.Builder(ROW_OF_DATA_5.get(1)).build(), 
				new CellProps.Builder(ROW_OF_DATA_5.get(2)).build(), 
				new CellProps.Builder(ROW_OF_DATA_5.get(3)).build(), 
				new CellProps.Builder(ROW_OF_DATA_5.get(4)).build(), 
				new CellProps.Builder(ROW_OF_DATA_5.get(5).toString()).build() 
		},
		
		//totals displayed on row 6
		new CellProps[]{
				new CellProps.Builder("Count Total 1").build(), 
				CellProps.buildEmptyCell(),
				CellProps.buildEmptyCell(),
				CellProps.buildEmptyCell(), 
				new CellProps.Builder(""+ROW_5_CALCULATORS_RESULTS[0][0].getResult()).build(), 
				new CellProps.Builder(""+ROW_5_CALCULATORS_RESULTS[0][1].getResult()).build()
		},
		new CellProps[]{
				new CellProps.Builder("Count Total 1").build(), 
				CellProps.buildEmptyCell(),
				CellProps.buildEmptyCell(),
				CellProps.buildEmptyCell(),
				new CellProps.Builder(""+ROW_5_CALCULATORS_RESULTS[1][0].getResult()).build(), 
				new CellProps.Builder(""+ROW_5_CALCULATORS_RESULTS[1][1].getResult()).build()
		}, 
		new CellProps[]{
				new CellProps.Builder("Count Total 1").build(),
				CellProps.buildEmptyCell(),
				CellProps.buildEmptyCell(),
				CellProps.buildEmptyCell(),
				new CellProps.Builder(""+ROW_5_CALCULATORS_RESULTS[2][0].getResult()).build(),
				new CellProps.Builder(""+ROW_5_CALCULATORS_RESULTS[2][1].getResult()).build()
		},
		
		//displayed on row 6
		new CellProps[]{
				new CellProps.Builder(ROW_OF_DATA_6.get(0)).build(), 
				new CellProps.Builder(ROW_OF_DATA_6.get(1)).build(), 
				new CellProps.Builder(ROW_OF_DATA_6.get(2)).build(), 
				new CellProps.Builder(ROW_OF_DATA_6.get(3)).build(), 
				new CellProps.Builder(ROW_OF_DATA_6.get(4)).build(), 
				new CellProps.Builder(ROW_OF_DATA_6.get(5).toString()).build() 
		},
		
		//totals displayed on exit
		new CellProps[]{
				new CellProps.Builder("Count Total 1").build(), 
				CellProps.buildEmptyCell(),
				CellProps.buildEmptyCell(),
				CellProps.buildEmptyCell(),
				new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[0][0].getResult()).build(), 
				new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[0][1].getResult()).build()
		},
		new CellProps[]{
				new CellProps.Builder("Count Total 1").build(), 
				CellProps.buildEmptyCell(),
				CellProps.buildEmptyCell(),
				CellProps.buildEmptyCell(),
				new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[1][0].getResult()).build(), 
				new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[1][1].getResult()).build()
		}, 
		new CellProps[]{
				new CellProps.Builder("Count Total 7").build(),
				CellProps.buildEmptyCell(),
				CellProps.buildEmptyCell(),
				CellProps.buildEmptyCell(),
				new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[2][0].getResult()).build(),
				new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[2][1].getResult()).build()
		},
		new CellProps[]{
				new CellProps.Builder("Grand Count Total ").build(),
				CellProps.buildEmptyCell(),
				CellProps.buildEmptyCell(),
				CellProps.buildEmptyCell(),
				new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[3][0].getResult()).build(),
				new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[3][1].getResult()).build()
		}
	};
}