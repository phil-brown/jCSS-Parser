package io.github.phdbrown.cssparser;

import io.github.phdbrown.cssparser.CSSHandler;
import io.github.phdbrown.cssparser.CSSParser;
import io.github.phdbrown.cssparser.DefaultCSSHandler;
import io.github.phdbrown.cssparser.TokenSequence;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Simple Test... More to come.
     */
    public void testApp() throws Exception
    {
        CSSHandler handler = new DefaultCSSHandler() {
			@Override
			public InputStream handleImport(TokenSequence importString) {
				System.out.println("Found @import " + importString.toString());
				return new ByteArrayInputStream(".foobar{height:100%}".getBytes());
			}
			
			@Override
			public boolean supports(TokenSequence logic) {
				return true;
			}
			
			@Override
			public boolean queryDocument(TokenSequence[] functions) {
				StringBuilder builder = new StringBuilder("Found @document query for document functions: [ ");
				for (int i = 0; i < functions.length; i++) 
				{
					builder.append(functions[i].toDebugString());
					if (i != functions.length-1)
						builder.append(", ");
					
				}
				builder.append(" ]");
				System.out.println(builder.toString());
				return true;
			}
			
			@Override
			public boolean queryMedia(TokenSequence[] types) {
				StringBuilder builder = new StringBuilder("Found @media query for media types: [ ");
				for (int i = 0; i < types.length; i++) 
				{
					builder.append(types[i].toDebugString());
					if (i != types.length-1)
						builder.append(", ");
					
				}
				builder.append(" ]");
				System.out.println(builder.toString());
				return true;
			}
		};
		CSSParser parser = new CSSParser(getSampleInputStream(), handler);
		parser.setLineSeparator('\n');
		parser.parse();
    }
    
    /**
	 * Creates an input stream from a CSS stylesheet string that includes test cases to run
	 * @return an input stream to test
	 */
	private static InputStream getSampleInputStream()
	{
		String sample = "@charset ISO-8859-1;\n"+
	    "@charset utf-8;\n"+
	    "@import someFile.css;\n"+
	    "@keyframes mymove\n"+
	    "{\n"+
	    "  from {top:0px;}\n"+
	    "  to {top:200px;}\n"+
	    "}\n"+
	    "@font-face\n"+
	    "{\n"+
	    "  font-family: myFirstFont;\n"+
	    "  src: url(sansation_light.woff),\n" +
	    "       asset(fonts/sans_light.ttf);\n"+
	    "}\n"+
	    "/*\n" +
		"\n"+
		" * HTML5 Boilerplate\n" +
		" *\n" +
		" * What follows is the result of much research on cross-browser styling.\n" +
		" * Credit left inline and big thanks to Nicolas Gallagher, Jonathan Neal,\n" +
		" * Kroc Camen, and the H5BP dev community and team.\n" +
		" */\n" +
		"\n" +
		"/* ==========================================================================\n" +
		" * Base styles: opinionated defaults\n" +
		" * ========================================================================== */\n" +
		"\n" +
		"html,\n" +
		"button,\n" +
		"input,\n" +
		"select,\n" +
		"textarea {\n" +
		"    color: #222;\n" +
		"}\n" +
		"\n" +
		"body {\n" +
		"    font-size: 1em;\n" +
		"    line-height: 1.4;\n" +
		"}\n" +
		"\n" +
		"/*\n" +
		" * Remove text-shadow in selection highlight: h5bp.com/i\n" +
		" * These selection rule sets have to be separate.\n" +
		" * Customize the background color to match your design.\n" +
		" */\n" +
		"\n" +
		"::-moz-selection {\n" +
		"    background: #b3d4fc !important;\n" +
		"    text-shadow: none ! important;\n" +
		"}\n" +
		"@supports (debug) {\n" +
		"    @keyframes mySupportMove\n"+
	    "    {\n"+
	    "        from {top:0px;}\n"+
	    "        to {top:200px;}\n"+
	    "    }\n"+
	    "    test : { background: #000000 }\n" +
		"}\n" +
		"\n" +
		"::selection {\n" +
		"    background: #b3d4fc;\n" +
		"    text-shadow: none;\n" +
		"}\n" +
		"@document domain(example.com), url-prefix(http://www.example.com/test/) {\n" +
		"    @media screen {\n"+
		"        vertical-align: middle;\n" +
	    "    }\n"+
	    "    #test>foo : { background: #00FF33 }\n" +
		"}\n" +
		"@media screen, print {\n" +
		"    @page :last\n"+
	    "    {\n"+
	    "        margin:1in\n"+
	    "    }\n"+
	    "    a[src*=\"example\"] : { background: #FF0000 }\n" +
		"}\n" +
		"\n" +
		"/*\n" +
		" * A better looking default horizontal rule\n" +
		" */\n" +
		"\n" +
		"hr {\n" +
		"    display: block;\n" +
		"    height: 1px;\n" +
		"    border: 0;\n" +
		"    border-top: 1px solid #ccc;\n" +
		"    margin: 1em 0;\n" +
		"    padding: 0;\n" +
		"}\n" +
		"\n" +
		"/*\n" +
		" * Remove the gap between images and the bottom of their containers: h5bp.com/i/440\n" +
		" */\n" +
		"\n" +
		"img {\n" +
		"    vertical-align: middle;\n" +
		"}\n" +
		"\n" +
		"/*\n" +
		" * Remove default fieldset styles.\n" +
		" */\n" +
		"\n" +
		"fieldset {\n" +
		"    border: 0;\n" +
		"    margin: 0;\n" +
		"    padding: 0;\n" +
		"}\n" +
		"\n" +
		"/*\n" +
		" * Allow only vertical resizing of textareas.\n" +
		" */\n" +
		"\n" +
		"textarea {\n" +
		"    resize: vertical;\n" +
		"}\n" +
		"\n" +
		"/* ==========================================================================\n" +
		"   Chrome Frame prompt\n" +
		"   ========================================================================== */\n" +
		"\n" +
		".chromeframe {\n" +
		"    margin: 0.2em 0;\n" +
		"    background: #ccc;\n" +
		"    color: #000;\n" +
		"    padding: 0.2em 0;\n" +
		"}\n" +
		"\n" +
		"/* ==========================================================================\n" +
		"   Author's custom styles\n" +
		"   ========================================================================== */";

		return new ByteArrayInputStream(sample.getBytes());
	}
}
