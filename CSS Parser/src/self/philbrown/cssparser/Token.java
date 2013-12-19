package self.philbrown.cssparser;

public class Token implements ParserConstants
{

	public int tokenCode;
	public String attribute;
	
	public Token(int tokenCode, String attribute)
	{
		this.tokenCode = tokenCode;
		this.attribute = attribute;
	}
	
	@Override
	public String toString()
	{
		return String.valueOf(tokenCode);
		//TODO better toString
//		switch (tokenCode)
//		{
//		
//		}
	}
}
