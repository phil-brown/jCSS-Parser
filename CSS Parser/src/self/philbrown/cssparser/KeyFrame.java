package self.philbrown.cssparser;

import java.util.ArrayList;
import java.util.List;

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
	
	
}
