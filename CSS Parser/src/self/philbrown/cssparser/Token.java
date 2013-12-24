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

import java.util.Locale;

/**
 * Parsed Element
 * @author Phil Brown
 * @since 3:09:57 PM Dec 20, 2013
 *
 */
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
		if (tokenCode <= RESERVEDWORD.length)
			return RESERVEDWORD[tokenCode];
		String text = attribute == null ? "" : attribute;
		switch (tokenCode)
		{
			case DOT : return ".";
			case HASH : return "#";
			case COMMA : return ",";
			case COLON : return ":";
			case SEMICOLON : return ";";
			case LEFTSQ : return "[";
			case RIGHTSQ : return "]";
			case MINUS : return "-";
			case LEFTPAREN : return "(";
			case RIGHTPAREN : return ")";
			case TIMES : return "*";
			case GT : return ">";
			case LT : return "<";
			case OR : return "|";
			case PLUS : return "+";
			case NOT_EQUAL : return "~=";
			case EQUAL : return "=";
			case OR_EQUAL : return "|=";
			case TIMES_EQUAL : return "*=";
			case CARET_EQUAL : return "^=";
			case DOLLAR_EQUAL : return "$=";
			case AT : return "@";
			case CARET : return "^";
			case DOLLAR : return "$";
			case NUMBER : return text;
			case IDENTIFIER : return text;
			case LEFT_CURLY_BRACKET : return "{";
			case RIGHT_CURLY_BRACKET : return "}";
			case SLASH_STAR : return "/*";
			case STAR_SLASH : return "*/";
			case SLASH : return "/";
			case COMMENT : return text;
			case DOUBLE_COLON : return "::";
			case SINGLE_QUOTE : return "'";
			case DOUBLE_QUOTE : return "\"";
			case AT_RULE : return text;
			case PERCENT : return "%";
			case SPACE : return " ";
	        case EOF: return "\nEOF\n";
	        default: return String.format(Locale.US, "%d  Unknown Character", tokenCode);
		}
	}
	
	public String toDebugString()
	{
		if (tokenCode <= RESERVEDWORD.length)
			return RESERVEDWORD[tokenCode];
		switch (tokenCode)
		{
			case DOT : return ".";
			case HASH : return "#";
			case COMMA : return ",";
			case COLON : return ":";
			case SEMICOLON : return ";";
			case LEFTSQ : return "[";
			case RIGHTSQ : return "]";
			case MINUS : return "-";
			case LEFTPAREN : return "(";
			case RIGHTPAREN : return ")";
			case TIMES : return "*";
			case GT : return ">";
			case LT : return "<";
			case OR : return "|";
			case PLUS : return "+";
			case NOT_EQUAL : return "~=";
			case EQUAL : return "=";
			case OR_EQUAL : return "|=";
			case TIMES_EQUAL : return "*=";
			case CARET_EQUAL : return "^=";
			case DOLLAR_EQUAL : return "$=";
			case AT : return "@";
			case CARET : return "^";
			case DOLLAR : return "$";
			case NUMBER : return (attribute == null ? "[NUMBER]" : attribute);
			case IDENTIFIER : return (attribute == null ? "[IDENTIFIER]" : attribute);
			case LEFT_CURLY_BRACKET : return "\n{\n";
			case RIGHT_CURLY_BRACKET : return "\n}\n";
			case SLASH_STAR : return "/*";
			case STAR_SLASH : return "*/";
			case SLASH : return "/";
			case COMMENT : return (attribute == null ? "[COMMENT]" : "/*" + attribute + "*/\n");
			case DOUBLE_COLON : return "::";
			case SINGLE_QUOTE : return "'";
			case DOUBLE_QUOTE : return "\"";
			case AT_RULE : return (attribute == null ? "[AT RULE]" : attribute);
			case PERCENT : return "%";
			case SPACE : return " ";
	        case EOF: return "\nEOF\n";
	        default: return String.format(Locale.US, "%d  Unknown Character", tokenCode);
		}
	}
}
