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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CSSParser implements ParserConstants
{

	private Scanner s;
	private Token t;
	
	private CSSHandler handler;
	
	private int indent = 0;
	
	private boolean debug = false;
	
	public CSSParser(InputStream source, CSSHandler handler) throws IOException
	{
		s = new Scanner(source);
		this.handler = handler;
	}
	
	private void match(int matcher) throws IOException
	{
		if (t.tokenCode == matcher)
		{
//			if (debug)
//			{
//				for (int i = 0; i < indent; i++) {
//					System.out.print(" ");
//				}
//				System.out.print(t.toDebugString());
//			}
			t = s.nextToken();
		}
		else {
			handler.handleError(String.format(Locale.US, "Match Error at line %d and character %d. Need: %s Found: %s", s.getLineNumber(), s.getCharacterNumberForCurrentLine(), new Token(matcher, null).toDebugString(), t.toString()), new Exception("Matcher Error"));
			System.exit(0);
		}
	}
	
	public void parse() throws IOException
	{
		t = s.nextToken();
		if (debug)
			System.out.println("[" + t.toDebugString() + "]");
		
		while (t.tokenCode != EOF)
		{
			if (t.tokenCode == COMMENT)
			{
				match(COMMENT);
			}
			else if (t.tokenCode == AT_RULE)
			{
				String identifier = t.attribute;
				if (identifier.equalsIgnoreCase("charset"))
				{
					match(AT_RULE);
					String charset = t.attribute;
					match(IDENTIFIER);
					match(SEMICOLON);
					s.changeCharset(charset);
					//t = s.nextToken();
					handler.handleNewCharset(charset);
				}
				else if (identifier.equalsIgnoreCase("import"))
				{
					match(AT_RULE);
					StringBuilder importString = new StringBuilder();
					while (t.tokenCode != SEMICOLON)
					{
						importString.append(t.toString());
						t = s.nextToken();
					}
					match(SEMICOLON);
					String name = importString.toString();
					InputStream is = handler.handleImport(name);
					if (is != null)
					{
						//FIXME: this doesn't allow multiple imports to import in the correct order
						s.include(is, name);
						//t = s.nextToken();
					}
				}
				else if (identifier.equalsIgnoreCase("namespace"))
				{
					match(AT_RULE);
					StringBuilder namespace = new StringBuilder();
					while (t.tokenCode != SEMICOLON)
					{
						namespace.append(t.toString());
						t = s.nextToken();
					}
					handler.handleNamespace(namespace.toString());
					//namespace not currently supported (what would it do?)
					//t = s.nextToken();
				}
				else if (identifier.equalsIgnoreCase("supports"))
				{
					match(AT_RULE);
					StringBuilder logic = new StringBuilder();
					while (t.tokenCode != LEFT_CURLY_BRACKET)
					{
						logic.append(t.toString());
						t = s.nextToken();
					}
					boolean supports = handler.supports(logic.toString());
					if (supports)
					{
						logic = new StringBuilder();
						while (t.tokenCode != RIGHT_CURLY_BRACKET)
						{
							logic.append(t.toString());
							t = s.nextToken();
						}
						s.include(logic.toString());//include the supports css
					}
					else
					{
						while (t.tokenCode != RIGHT_CURLY_BRACKET)
						{
							t = s.nextToken();
						}
					}
					//t = s.nextToken();
				}
				else if (identifier.equalsIgnoreCase("keyframes"))
				{
					match(AT_RULE);
					String ident = t.attribute;
					match(IDENTIFIER);
					match(LEFT_CURLY_BRACKET);
					int curlies = 1;
					List<KeyFrame> keyFrames = new ArrayList<KeyFrame>();
					while (curlies > 0)
					{
						if (t.tokenCode == LEFT_CURLY_BRACKET)
						{
							curlies++;
							t = s.nextToken();
						}
						else if (t.tokenCode == RIGHT_CURLY_BRACKET)
						{
							curlies--;
							t = s.nextToken();
						}
						else
						{
							double percent = -1;
							if (t.tokenCode == NUMBER)
							{
								percent = Double.parseDouble(t.attribute);
								match(NUMBER);
								match(PERCENT);
								
							}
							else
							{
								String _percent = t.attribute;
								match(IDENTIFIER);
								
								if (_percent.equalsIgnoreCase("to"))
									percent = 0;
								else if (_percent.equalsIgnoreCase("from"))
									percent = 100;
								else
									System.out.println(String.format(Locale.US, "Not a valid percentage: '%s'.", _percent));
							}
							match(LEFT_CURLY_BRACKET);
							
							List<Declaration> declarations = new ArrayList<Declaration>();
							while (t.tokenCode != RIGHT_CURLY_BRACKET)
							{
								TokenSequence.Builder property = new TokenSequence.Builder();
								property.append(t);
								match(IDENTIFIER);
								match(COLON);
								TokenSequence.Builder value = new TokenSequence.Builder();
								while (t.tokenCode != RIGHT_CURLY_BRACKET && t.tokenCode != SEMICOLON)
								{
									value.append(t);
									t = s.nextToken(false);
								}
								declarations.add(new Declaration(property.create(), value.create(), false));
								t = s.nextToken();
							}
							if (percent != -1)
							{
								keyFrames.add(new KeyFrame(percent, declarations));
							}
							
							t = s.nextToken();
						}
						
					}
					handler.handleKeyframes(ident, keyFrames);
					//t = s.nextToken();
				}
				else if (identifier.equals("font-face"))
				{
					//get Typeface from handler
					match(AT_RULE);
					match(LEFT_CURLY_BRACKET);
					List<Declaration> declarations = new ArrayList<Declaration>();
					while(t.tokenCode != RIGHT_CURLY_BRACKET)
					{
						TokenSequence.Builder property = new TokenSequence.Builder();
						property.append(t);
						
						match(IDENTIFIER);
						match(COLON);
						TokenSequence.Builder value = new TokenSequence.Builder();
						while (t.tokenCode != RIGHT_CURLY_BRACKET && t.tokenCode != SEMICOLON)
						{
							value.append(t);
							t = s.nextToken(false);
						}
						declarations.add(new Declaration(property.create(), value.create(), false));
						t = s.nextToken();
					}
					match (RIGHT_CURLY_BRACKET);
					try {
						FontFace f = new FontFace(declarations);
						handler.handleFontFace(f);
					}
					catch (Throwable t)
					{
						handler.handleError("Could not parse @font-face", t);
						System.exit(0);
					}
					
				}
				else
				{
					handler.handleError(String.format(Locale.US, "This implementation does not support the at-rule %s.", t.toString()), new Exception("At-Rule not supported"));
					System.exit(0);
					//media, page, document don't really apply
				}
			}
			else
			{
				//expect a selector
				TokenSequence.Builder selector = new TokenSequence.Builder();
				while (t.tokenCode != LEFT_CURLY_BRACKET)
				{
					selector.append(t);
					t = s.nextToken();
				}
				match(LEFT_CURLY_BRACKET);
				List<Declaration> declarations = new ArrayList<Declaration>();
				while(t.tokenCode != RIGHT_CURLY_BRACKET)
				{
					TokenSequence.Builder property = new TokenSequence.Builder();
					property.append(t);
					
					match(IDENTIFIER);
					match(COLON);
					TokenSequence.Builder value = new TokenSequence.Builder();
					while (t.tokenCode != RIGHT_CURLY_BRACKET && t.tokenCode != SEMICOLON)
					{
						value.append(t);
						t = s.nextToken(false);
					}
					declarations.add(new Declaration(property.create(), value.create(), false));
					if (t.tokenCode == SEMICOLON)
						match(SEMICOLON);
				}
				match (RIGHT_CURLY_BRACKET);
				handler.handleRuleSet(new RuleSet(selector.create(), declarations));
			}
		}
		match(EOF);
		
	}
	
	public char getLineSeparator() {
		return s.getLineSeparator();
	}

	public void setLineSeparator(char lineSeparator) {
		s.setLineSeparator(lineSeparator);
	}
	
	/**
	 * Parses and prints debug info
	 * @throws IOException 
	 */
	public void debug() throws IOException
	{
		debug = true;
		parse();
	}
	
//	private void enter(String s)
//	{
//		indent++;
//		for (int i = 0; i < indent; i++)
//		{
//			System.out.print(" ");
//		}
//		System.out.println(s + ">>");
//	}
//	
//	private void exit(String s)
//	{
//		indent--;
//		for (int i = 0; i < indent; i++)
//		{
//			System.out.print(" ");
//		}
//		System.out.println("<<" + s);
//	}
	
	
}
