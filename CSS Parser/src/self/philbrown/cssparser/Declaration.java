package self.philbrown.cssparser;

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
	
}
