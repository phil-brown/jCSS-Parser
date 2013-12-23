package self.philbrown.cssparser;

import java.io.InputStream;
import java.util.List;

public class DefaultCSSHandler implements CSSHandler
{

	@Override
	public void handleError(String error, Throwable t) {
		System.err.println(error);
		t.printStackTrace(System.out);
	}

	@Override
	public InputStream handleImport(String importString) {
		System.out.println("Found Import @" + importString);
		return null;
	}
	
	@Override
	public void handleNewCharset(String charset) {
		System.out.println("New Charset: " + charset);
	}

	@Override
	public void handleNamespace(String namespace) {
		System.out.println("Found Namespace " + namespace);
	}

	@Override
	public boolean supports(String logic) {
		System.out.println("Skipping support check for logic: " + logic);
		return false;
	}

	@Override
	public void handleKeyframes(String identifier, List<KeyFrame> keyframes) {
		StringBuilder builder = new StringBuilder(" {\n");
		for (int i = 0; i < keyframes.size(); i++)
		{
			builder.append("  ").append(keyframes.get(i)).append(";\n");
		}
		builder.append("}");
		System.out.println("@keyframes " + identifier + builder.toString());
	}

	@Override
	public void handleFontFace(FontFace font) {
		System.out.println(font.toString());
	}

	@Override
	public void handleRuleSet(RuleSet ruleSet) {
		System.out.println("\n" + ruleSet.toString() + "\n");
	}

}
