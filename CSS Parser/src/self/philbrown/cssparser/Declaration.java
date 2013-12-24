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

import java.util.Locale;

/**
 * Includes a property and value. May be marked as important.
 * @author Phil Brown
 * @since 1:43:16 PM Dec 19, 2013
 *
 */
public class Declaration 
{

	private TokenSequence property;
	private TokenSequence value;
	private boolean important;
	
	public Declaration()
	{
	}
	
	public Declaration(TokenSequence property, TokenSequence value, boolean important)
	{
		this.property = property;
		this.value = value;
		this.important = important;
	}

	public TokenSequence getProperty() {
		return property;
	}

	public void setProperty(TokenSequence property) {
		this.property = property;
	}

	public TokenSequence getValue() {
		return value;
	}

	public void setValue(TokenSequence value) {
		this.value = value;
	}

	public boolean isImportant() {
		return important;
	}

	public void setImportant(boolean important) {
		this.important = important;
	}
	
	@Override
	public String toString()
	{
		return String.format(Locale.US, "%s:%s%s", property.toString(), value.toString(), (important ? " !important" : ""));
	}
	
}
