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

	//at-rules
	
	//@charset
	public String handleCharset(String charset);
	
	//@import
	public InputStream handleImport(String importString);
	
	//@namespace
	public void handleNamespace(String namespace);
	
	//@supports
	public boolean supports(String logic);
	
	//@keyframes
	public void handleKeyframes(String identifier, List<KeyFrame> keyframes);
	
	
	public void handleSelection(Token[] tokens, String asString);
	
	
}
