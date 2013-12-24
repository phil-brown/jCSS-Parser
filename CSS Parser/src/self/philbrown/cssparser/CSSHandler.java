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
 * Interface for receiving parse events from the CSS Parser.
 * @author Phil Brown
 * @since 2:39:59 PM Dec 18, 2013
 *
 */
public interface CSSHandler 
{

	//error
	public void handleError(String error, Throwable t);
	
	//at-rules
	
	//@import
	public InputStream handleImport(String importString);
	
	//@charset
	public void handleNewCharset(String charset);
	
	//@namespace
	public void handleNamespace(String namespace);
	
	//@supports
	public boolean supports(String logic);
	
	//@keyframes
	public void handleKeyframes(String identifier, List<KeyFrame> keyframes);
	
	//@fornt-face
	public void handleFontFace(FontFace font);
	
	//rule set
	public void handleRuleSet(RuleSet ruleSet);
	
	
}
