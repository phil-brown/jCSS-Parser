package self.philbrown.cssparser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A rule set consists of the selector and declaration block.
 * @author Phil Brown
 * @since 1:40:02 PM Dec 19, 2013
 *
 */
public class RuleSet implements Statement
{

	private TokenSequence selector;
	private List<Declaration> declarationBlock;
	
	public RuleSet()
	{
		declarationBlock = new ArrayList<Declaration>();
	}
	
	public RuleSet(TokenSequence selector)
	{
		this();
		this.selector = selector;
	}
	
	public RuleSet(TokenSequence selector, List<Declaration> declarationBlock)
	{
		this.selector = selector;
		this.declarationBlock = declarationBlock;
	}

	public TokenSequence getSelector() {
		return selector;
	}

	public void setSelector(TokenSequence selector) {
		this.selector = selector;
	}

	public List<Declaration> getDeclarationBlock() {
		return declarationBlock;
	}

	public void setDeclarationBlock(List<Declaration> declarationBlock) {
		this.declarationBlock = declarationBlock;
	}
	
	@Override
	public String toString()
	{
		StringBuilder block = new StringBuilder(" {\n");
		for (int i = 0; i < declarationBlock.size(); i++)
		{
			block.append(" ").append(declarationBlock.get(i)).append(";\n");
		}
		block.append("}");
		
		return String.format(Locale.US, "%s %s", selector.toString(), block.toString());
	}
	
	
}
