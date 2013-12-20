package self.philbrown.cssparser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class KeyFrame {

	private double percent;
	private List<Declaration> declarationBlock;
	
	public KeyFrame()
	{
		declarationBlock = new ArrayList<Declaration>();
	}
	public KeyFrame(double percent, List<Declaration> declarationBlock)
	{
		this.percent = percent;
		this.declarationBlock = declarationBlock;
	}
	
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
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
		StringBuilder block = new StringBuilder("{\n");
		for (int i = 0; i < declarationBlock.size(); i++)
		{
			block.append(" ").append(declarationBlock.get(i)).append(";\n");
		}
		block.append("}");
		
		return String.format(Locale.US, "%d%%: %s", percent, block.toString());
	}
	
}
