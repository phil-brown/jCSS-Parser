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

import java.io.InputStream;
import java.util.List;

/**
 * 
 * @author Phil Brown
 * @since 2:39:59 PM Dec 18, 2013
 */
public class DefaultCSSHandler implements CSSHandler
{

	@Override
	public void handleError(String error, Throwable t) {
		System.err.println(error);
		t.printStackTrace(System.out);
	}

	@Override
	public InputStream handleImport(String importString) {
		System.out.println("Found @import " + importString);
		return null;
	}
	
	@Override
	public void handleNewCharset(String charset) {
		System.out.println("New Charset: " + charset);
	}

	@Override
	public void handleNamespace(String namespace) {
		System.out.println("Found Namespace " + namespace);
	}

	@Override
	public boolean supports(String logic) {
		System.out.println("Skipping support check for logic: " + logic);
		return false;
	}

	@Override
	public void handleKeyframes(String identifier, List<KeyFrame> keyframes) {
		StringBuilder builder = new StringBuilder(" {\n");
		for (int i = 0; i < keyframes.size(); i++)
		{
			builder.append("  ").append(keyframes.get(i)).append(";\n");
		}
		builder.append("}");
		System.out.println(" Found @keyframes " + identifier + builder.toString());
	}

	@Override
	public void handleFontFace(FontFace font) {
		System.out.println("Found New Font: " + font.toString());
	}

	@Override
	public void handleRuleSet(RuleSet ruleSet) {
		System.out.println("\n" + ruleSet.toString() + "\n");
	}

}
