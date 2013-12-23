package self.philbrown.cssparser;

import java.io.InputStream;
import java.util.List;

/**
 * Interface for receiving parse events from the CSS Parser.
 * @author Phil Brown
 * @since 2:39:59 PM Dec 18, 2013
 *
 */
public interface CSSHandler 
{

	//error
	public void handleError(String error, Throwable t);
	
	//at-rules
	
	//@import
	public InputStream handleImport(String importString);
	
	//@charset
	public void handleNewCharset(String charset);
	
	//@namespace
	public void handleNamespace(String namespace);
	
	//@supports
	public boolean supports(String logic);
	
	//@keyframes
	public void handleKeyframes(String identifier, List<KeyFrame> keyframes);
	
	//@fornt-face
	public void handleFontFace(FontFace font);
	
	//rule set
	public void handleRuleSet(RuleSet ruleSet);
	
	
}
