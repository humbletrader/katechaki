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
package net.sf.reportengine.scenarios;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.in.ColumnMetadata;
import net.sf.reportengine.in.ColumnMetadataHolder;
import net.sf.reportengine.in.TableInput;

import org.mockito.Mockito;

/**
 * @author dragos balan
 *
 */
public class AutodetectConfigurationScenario {
	
	public static List<ColumnMetadata> COLUMN_METADATA = 
			Arrays.asList(	new ColumnMetadata("col1", "col1label", HorizAlign.CENTER), 
							new ColumnMetadata("col2", "col2label", HorizAlign.LEFT));
	
	public static TableInput INPUT = Mockito.mock(TableInput.class, Mockito.withSettings().extraInterfaces(ColumnMetadataHolder.class)); 
	
	public static void initScenario(){
		
		when(((ColumnMetadataHolder)INPUT).getColumnMetadata()).thenReturn(COLUMN_METADATA);
		when(INPUT.hasNext()).thenReturn(true, true, true, false); 
		when(INPUT.next()).thenReturn(
				Arrays.asList(new Object[]{"value 11", "value 12"}), 
				Arrays.asList(new Object[]{"value 21", "value 22"}), 
				Arrays.asList(new Object[]{"value 31", "value 32"})); 
	}
}
