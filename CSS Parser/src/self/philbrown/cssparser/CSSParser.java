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
	
	private int loopNumber = 0;
	private int stringNumber = 0;
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
			if (debug)
			{
				for (int i = 0; i < indent; i++) {
					System.out.print(" ");
				}
				System.out.print(t.toString());
			}
			t = s.nextToken();
		}
		else {
			System.err.println("Match Error: " + matcher + " needed. Found: " + t.toString());
			System.exit(0);
		}
	}
	
	public void parse() throws IOException
	{
		t = s.nextToken();
		
		while (t.tokenCode != EOF)
		{
			if (t.tokenCode == COMMENT)
			{
				match(COMMENT);
			}
			else if (t.tokenCode == AT_RULE)
			{
				String identifier = t.attribute.substring(1);
				if (identifier.equalsIgnoreCase("charset"))
				{
					match(AT_RULE);
					String charset = t.attribute;
					match(IDENTIFIER);
					match(SEMICOLON);
					s.changeCharset(charset);
					t = s.nextToken();
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
					String name = importString.toString();
					InputStream is = handler.handleImport(name);
					//FIXME: this doesn't allow multiple imports to import in the correct order
					s.include(is, name);
					t = s.nextToken();
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
					t = s.nextToken();
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
					t = s.nextToken();
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
							TokenSequence.Builder property = new TokenSequence.Builder();
							List<Declaration> declarations = new ArrayList<Declaration>();
							while (t.tokenCode != RIGHT_CURLY_BRACKET)
							{
								property.append(t);
								match(IDENTIFIER);
								match(COLON);
								TokenSequence.Builder value = new TokenSequence.Builder();
								while (t.tokenCode != RIGHT_CURLY_BRACKET && t.tokenCode != SEMICOLON)
								{
									value.append(t);
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
					t = s.nextToken();
				}
				else if (identifier.equals("font-face"))
				{
					//get Typeface from handler
					match(AT_RULE);
					
				}
				else
				{
					match(AT_RULE);
					//media, page, document don't really apply
				}
			}
			else
			{
				//handleAtRules();
				//expect a selector
				selector();
			}
		}
		match(EOF);
		
	}
	
	//handles {@literal @} rules, such as keyframes, font-face, import, and media
	private void atRules(String selector) throws IOException
	{
		if (debug)
			enter("@rules");
		
		String rule = selector.substring(1);
		if (rule.equalsIgnoreCase("charset"))
		{
			//must be first thing (comments ok)
			
			System.out.println(String.format(Locale.US, "charset must be set in Constructor. Skipping at-rule \"%s\".", selector));
		}
		else if (rule.equalsIgnoreCase("import"))
		{
			//must be first thing (or right after charset)
		}
		else if (rule.equalsIgnoreCase("namespace"))
		{
			
		}
		else if (rule.equalsIgnoreCase("media"))
		{
			
		}
		else if (rule.equalsIgnoreCase("page"))
		{
			
		}
		else if (rule.equalsIgnoreCase("font-face"))
		{
			
		}
		else if (rule.equalsIgnoreCase("keyframes"))
		{
			
		}
		else if (rule.equalsIgnoreCase("supports"))
		{
			
		}
		else if (rule.equalsIgnoreCase("document"))
		{
			
		}
		if (debug)
			exit("@rules");
	}
	
	//parses the rules
	private void rules() throws IOException
	{
		if (debug)
			enter("rules");
		
		if (debug)
			exit("rules");
	}
	
	//parse a selector
	private void selector() throws IOException
	{
		if (debug)
			enter("selector");
		List<Token> tokens = new ArrayList<Token>();
		StringBuilder builder = new StringBuilder();
		while (t.tokenCode != LEFT_CURLY_BRACKET)
		{
			tokens.add(t);
			builder.append(t.toString());
			t = s.nextToken();
		}
		match(LEFT_CURLY_BRACKET);
		if (tokens.size() == 0)
			match(RIGHT_CURLY_BRACKET);
		else if (tokens.get(0).tokenCode == AT)
			atRules(builder.toString());
		else
			rules();
		match(RIGHT_CURLY_BRACKET);
		
		//TODO: call handler method, providing selector and rules. This allows changes to be made while parsing!!!
		//call handler method here!
		
//		switch(t.tokenCode)
//		{
//			case DOT :
//				tokens.add(t);
//				match(DOT);
//				if (t.tokenCode == IDENTIFIER)
//				{
//					tokens.add(t);
//					match(IDENTIFIER);
//				}
//				else if (t.tokenCode <= RESERVEDWORD.length)
//				{
//					//may have been mistaken for a keyword
//					tokens.add(new Token(IDENTIFIER, RESERVEDWORD[t.tokenCode]));
//					t = s.nextToken();
//				}
//				else
//				{
//					System.err.println("Bad Selector Error. Could not validate \"." + t.toString() + "\".");
//					System.exit(0);
//				}
//				break;
//			case HASH :
//				break;
//			case TIMES :
//				break;
//			case COLON :
//				break;
//			case DOUBLE_COLON :
//				break;
//			case LEFTSQ :
//				break;
//			case AT :
//				break;
//			default :
//				
//				break;
//		}
//
//		if (t.tokenCode == LEFT_CURLY_BRACKET)
//		{
//			//start the rules
//		}
		if (debug)
			exit("selector");
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
	
	private void enter(String s)
	{
		indent++;
		for (int i = 0; i < indent; i++)
		{
			System.out.print(" ");
		}
		System.out.println(s + ">>");
	}
	
	private void exit(String s)
	{
		indent--;
		for (int i = 0; i < indent; i++)
		{
			System.out.print(" ");
		}
		System.out.println("<<" + s);
	}
	
	
}
