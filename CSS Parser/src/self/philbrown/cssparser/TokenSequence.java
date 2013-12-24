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

/**
 * Simple organization of a sequence of tokens.
 * @author Phil Brown
 * @since 1:35:08 PM Dec 19, 2013
 *
 */
public class TokenSequence 
{

	private List<Token> tokens;
	private String string;
	
	public TokenSequence(List<Token> tokens, String string)
	{
		this.tokens = tokens;
		this.string = string;
	}
	
	public List<Token> getTokens()
	{
		return tokens;
	}
	
	@Override
	public String toString()
	{
		return string;
	}
	
	public static class Builder
	{
		List<Token> tokens;
		StringBuilder builder;
		
		public Builder()
		{
			tokens = new ArrayList<Token>();
			builder = new StringBuilder();
		}
		
		public Builder append(Token t)
		{
			tokens.add(t);
			builder.append(t.toString());
			return this;
		}
		
		public TokenSequence create()
		{
			return new TokenSequence(tokens, builder.toString());
		}
	}
}
