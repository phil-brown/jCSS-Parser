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
			System.out.print(t.toString());
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
