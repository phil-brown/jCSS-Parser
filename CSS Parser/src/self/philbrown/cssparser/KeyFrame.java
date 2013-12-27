/*
 * Copyright 2013 Phil Brown
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package self.philbrown.cssparser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 
 * @author Phil Brown
 * @since 2:39:59 PM Dec 18, 2013
 */
public class KeyFrame {

	private double percent;
	private List<Declaration> declarationBlock;
	
	public KeyFrame()
	{
		declarationBlock = new ArrayList<Declaration>();
	}
	public KeyFrame(double percent, List<Declaration> declarationBlock)
	{
		this.percent = percent;
		this.declarationBlock = declarationBlock;
	}
	
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}
	public List<Declaration> getDeclarationBlock() {
		return declarationBlock;
	}
	public void setDeclarationBlock(List<Declaration> declarationBlock) {
		this.declarationBlock = declarationBlock;
	}
	
	@Override
	public String toString()
	{
		StringBuilder block = new StringBuilder("{\n");
		for (int i = 0; i < declarationBlock.size(); i++)
		{
			block.append(" ").append(declarationBlock.get(i)).append(";\n");
		}
		block.append("}");
		
		return String.format(Locale.US, "%f%%: %s", percent, block.toString());
	}
	
}
