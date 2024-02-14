package proyecto;

import java.util.ArrayList;
import java.util.List;


public class Lexer 
{
	
	//CONSTANTS
	public static final String TT_INT = "INT";
	public static final String TT_LPAREN = "LPAREN";
	public static final String TT_RPAREN = "RPAREN";
	public static final String TT_EQ = "EQ";
	
	public static final String DIGITS = "0123456789";
	
	private String text;
	private int position = -1;
	private String currentChar = null;
	
	private List<Token> tokens = new ArrayList<Token>();
	
	//BUILDER
	public Lexer(String text)
	{
		this.text = text;
		
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
	
	public List<Token> darTokens()
	{
		return this.tokens;
	}
}
