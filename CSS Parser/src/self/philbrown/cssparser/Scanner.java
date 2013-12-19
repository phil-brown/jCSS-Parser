package self.philbrown.cssparser;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Scanner implements ParserConstants 
{

	private String charset = "UTF-8";//default
	private char C;
	private BufferedReader source;
	private int cursor = 0;
	private List<String> imports = new ArrayList<String>();
	private List<String> supports = new ArrayList<String>();
	
	//TODO add count (cursor). Also add Line count.
	
	private InputStream is;
	
	public Scanner(InputStream source) throws IOException
	{
		this.is = source;
		this.source = new BufferedReader(new InputStreamReader(source, charset));
		getChar();
		
	}
	
	public void include(String ruleSets) throws IOException
	{
		if (supports.contains(ruleSets))
			return;
		supports.add(ruleSets);
		source.close();
		is = new SequenceInputStream(new ByteArrayInputStream(ruleSets.getBytes()), is);
		source = new BufferedReader(new InputStreamReader(is, charset));
		getChar();
	}
	
	/**
	 * Includes a stylesheet in the inputstream
	 * @param stylesheet
	 * @param name
	 * @throws IOException
	 */
	public void include(InputStream stylesheet, String name) throws IOException
	{
		
		
		if (imports.contains(name))
		{
			return;
		}
		else
		{
			imports.add(name);
			source.close();
			//FIXME: this will cause the imports to come in reverse order. Consider adding in order created by #imports Object
			is = new SequenceInputStream(stylesheet, is);
			source = new BufferedReader(new InputStreamReader(is, charset));
			getChar();
		}
		
		
	}
	
	/**
	 * changes the charset
	 * @param charset
	 * @throws IOException
	 */
	public void changeCharset(String charset) throws IOException
	{
		if (this.charset.equalsIgnoreCase(charset))
			return;
		this.charset = charset;
		//change the charset
		source.close();
		source = new BufferedReader(new InputStreamReader(is, charset));
		source.skip(cursor);
		getChar();
	}
	
	private void getChar() throws IOException
	{
		C = (char) source.read();
		cursor++;
	}
	
	public Token nextToken() throws IOException
	{
		String attribute = "";
		
		//get through whitespace
		while (Character.isWhitespace(C))
			getChar();
		
		//Identifier or reserved word
		if (Character.isLetter(C))
		{
			while (Character.isLetter(C) || C == '-')
			{
				attribute += C;
				getChar();
			}
			return new Token(lookup(attribute), attribute);
		}
		if (Character.isDigit(C))
		{
			while (Character.isDigit(C))
			{
				attribute += C;
				getChar();
			}
			return new Token(NUMBER, attribute);
		}
		switch (C)
		{
			case '/' : {//Slash or Comment
				getChar();
				if (C == '*')
				{
					boolean inComment = true;
					while (inComment)
					{
						getChar();
						if (C == '*')
						{
							getChar();
							if (C == '/')
							{
								inComment = false;
							}
							else
							{
								attribute += '*';
								attribute += C;
							}
						}
						else
						{
							attribute += C;
						}
					}
					getChar();
					return new Token(COMMENT, attribute);
				}
				return new Token(SLASH, null);
			}
			case '.' : {
				getChar();
				return new Token(DOT, null);
			}
			case '#' : {
				getChar();
				return new Token(HASH, null);
			}
			case '*' : {
				getChar();
				if (C == '=')
				{
					getChar();
					return new Token(TIMES_EQUAL, null);
				}
				return new Token(TIMES, null);
			}
			case ',' : {
				getChar();
				return new Token(COMMA, null);
			}
			case '>' : {
				getChar();
				return new Token(GT, null);
			}
			case '+' : {
				getChar();
				return new Token(PLUS, null);
			}
			case '[' : {
				getChar();
				return new Token(LEFTSQ, attribute);
			}
			case ']' : {
				getChar();
				return new Token(RIGHTSQ, attribute);
			}
			case '{' : {
				getChar();
				return new Token(LEFT_CURLY_BRACKET, attribute);
			}
			case '}' : {
				getChar();
				return new Token(RIGHT_CURLY_BRACKET, attribute);
			}
			case ':' : {
				getChar();
				if (C == ':')
				{
					getChar();
					return new Token(DOUBLE_COLON, null);
				}
				return new Token(COLON, null);
			}
			case '=' : {
				getChar();
				return new Token(EQUAL, null);
			}
			case '(' : {
				getChar();
				return new Token(LEFTPAREN, null);
			}
			case ')' : {
				getChar();
				return new Token(RIGHTPAREN, null);
			}
			case '~' : {
				getChar();
				if (C == '=')
				{
					getChar();
					return new Token(NOT_EQUAL, null);
				}
				return new Token(NOT,null);
			}
			case '|' : {
				getChar();
				if (C == '=')
				{
					getChar();
					return new Token(OR_EQUAL, null);
				}
				return new Token(OR,null);
			}
			case '^' : {
				getChar();
				if (C == '=')
				{
					getChar();
					return new Token(CARET_EQUAL, null);
				}
				return new Token(CARET,null);
			}
			case '$' : {
				getChar();
				if (C == '=')
				{
					getChar();
					return new Token(DOLLAR_EQUAL, null);
				}
				return new Token(DOLLAR,null);
			}
			case '@' : {
				getChar();
				if (Character.isLetter(C))
				{
					while (Character.isLetter(C) || C == '-')
					{
						attribute += C;
						getChar();
					}
					return new Token(AT_RULE, attribute);
				}
				return new Token(AT, null);
			}
			case EOFCHAR : {
				return new Token(EOF, null);
			}
			default : {
				System.out.println(String.format(Locale.US, "Invalid Token '%s'", String.valueOf(C)));
				getChar();
				return nextToken();
			}
		}
	}
	
	private int lookup(String word)
	{
		for (int i = 1; i < RESERVEDWORD.length; i++) 
		{
			if (word.equalsIgnoreCase(RESERVEDWORD[i]))
				return i;
		}
		return IDENTIFIER;
	}
}
