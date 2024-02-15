package proyecto;

import java.lang.invoke.ConstantBootstraps;
import java.util.ArrayList;
import java.util.List;


public class Lexer 
{
	
	//CONSTANTS
	public static final String TT_INT = "INT";
	public static final String TT_LPAREN = "LPAREN";
	public static final String TT_RPAREN = "RPAREN";
	public static final String TT_EQ = "EQ";
	
	public static final String TT_OCONST = "OCONST";
	public static final String TT_XCONST = "XCONST";
	public static final String TT_DCONST = "DCONST";
	
	public static List<String> oConstList = new ArrayList<String>();
	public static List<String> dConstList = new ArrayList<String>();
	public static List<String> xConstList = new ArrayList<String>();
	
	public static final String DIGITS = "0123456789";
	public static final String LOWLETTERS = "abcdefghijklmnopqrstuvwxyz";
	public static final String UPPLETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String LETTERS = LOWLETTERS + UPPLETTERS;
	public static final String LETTERSANDDIGITS = LETTERS + DIGITS;
	
	
	private String text;
	private int position = -1;
	private String currentChar = null;
	
	private String tempConstant = null;
	
	private List<Token> tokens = new ArrayList<Token>();
	
	//BUILDER
	public Lexer(String text)
	{
		this.text = text;
		
		//añadir los elementos a la lista O
		oConstList.add(":north");
		oConstList.add(":south");
		oConstList.add(":east");
		oConstList.add(":west");
		
		//añadir los elementos a la lista D
		dConstList.add(":left");
		dConstList.add(":right");
		dConstList.add(":around");
		dConstList.add(":front");
		dConstList.add(":back");
				
		//añadir los elementos a la lista X
		xConstList.add(":ballons");
		xConstList.add(":chips");
		
	}
	
	//METHODS
	/**
	 * Este metodo suma 1 a la posición y mira si la posición es menor que la longitud, si es así
	 * define el caracter actual al charAt (position) y en caso que no pone como current char
	 * un caracter nulo.
	 */
	public void advance()
	{
		this.position += 1;
		if (position < this.text.length())
		{
			this.currentChar = String.valueOf(text.charAt(position));
		}
		else 
		{
			currentChar = null;
		}
	}
	
	
	public Error makeTokens()
	{
		this.tokens.clear(); //limpiar la lista
		this.position = -1;
		advance();
		
		while(this.currentChar != null)
		{
			if(this.currentChar.equals(" ") || this.currentChar.equals("\n"))
			{
				advance();
			}
			else if(this.currentChar.equals(":"))
			{
				Token token = makeConstant();
				if(token == null)
				{
					Error error = constantError();
					return error; // se detiene la ejecución
				}
				else 
				{
					this.tokens.add(token);
				}
				
			}
			else if(DIGITS.contains(currentChar))
			{
				this.tokens.add(makeNumber());
			}
			else if (this.currentChar.equals("(")) 
			{
				this.tokens.add(new Token(TT_LPAREN));
				advance();
			}
			else if (this.currentChar.equals(")")) 
			{
				this.tokens.add(new Token(TT_RPAREN));
				advance();
			}
			else if (this.currentChar.equals("=")) 
			{
				this.tokens.add(new Token(TT_EQ));
				advance();
			}
			else
			{
				String character = this.currentChar;
				advance();
				return new IllegalCharError("'" + character + "'");
			}
			
		}
		return null;
	}
	
	public Token makeNumber()
	{
		String numString = "";
		while(this.currentChar!= null && DIGITS.contains(this.currentChar))
		{
			numString += this.currentChar;
			advance();
		}
		return new IntToken(TT_INT, Integer.parseInt(numString));
		
	}
	
	public Token makeConstant()
	{
		tempConstant = ":"; //para iniciar una nueva constante
		advance();
		String constString = ":";
		Token token = null;
		while(this.currentChar!= null && LOWLETTERS.contains(this.currentChar))
		{
			constString += this.currentChar;
			tempConstant += this.currentChar;
			advance();
		}
		if(oConstList.contains(constString))
			{
			token = new StrToken(TT_OCONST, constString);
			}
		else if(xConstList.contains(constString))
		{
			token = new StrToken(TT_XCONST, constString);
		}
		else if(dConstList.contains(constString))
		{
			token = new StrToken(TT_DCONST, constString);
		}
		
		return token;
	}
	
	public Error constantError()
	{
		Error error = new Error("ConstantError", "La constante (" + tempConstant + ") no existe");
		return error;
	}
	
	public List<Token> darTokens()
	{
		return this.tokens;
	}
}
