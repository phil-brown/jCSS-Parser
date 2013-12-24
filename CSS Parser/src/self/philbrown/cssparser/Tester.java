/*
 * Copyright 2013 Phil Brown
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package self.philbrown.cssparser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Tester 
{

	public static void main(String[] args)
	{
		CSSHandler handler = new DefaultCSSHandler();
		try
		{
			CSSParser parser = new CSSParser(getSampleInputStream(), handler);
			parser.setLineSeparator('\n');
			parser.parse();
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
	}
	
	private static InputStream getSampleInputStream()
	{
		String sample = "/*@charset ISO-8859-1;*/\n"+
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
	    "  src: url(sansation_light.woff);\n"+
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
		"    background: #b3d4fc;\n" +
		"    text-shadow: none;\n" +
		"}\n" +
		"\n" +
		"::selection {\n" +
		"    background: #b3d4fc;\n" +
		"    text-shadow: none;\n" +
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