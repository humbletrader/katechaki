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
package net.sf.reportengine.scenarios.ct;

/**
 * @author dragos
 *
 */
public class CtScenario2 {
	
	public static final String[][] DISTINCT_VALUES = new String[][]{
		new String[]{"North","South","East","West"},
		new String[]{"M","F"},
	};
	
	public static final Boolean HAS_HEADER_TOTALS = new Boolean(false);
	
	public static final String[][] HEADER_TEMPLATE = new String[][]{
		new String[]{"North","North","South","South",	"East",	"East",	"West",	"West"},
		new String[]{"M",		"F",	"M",	"F",	"M",	"F",	"M",	"F"},
	};
	
}
