package proyecto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


public class Main {

	public static void main(String[] args) throws IOException 
	{
		String text = readFileAsSingleLine("data/ProgramaPrueba");
		Lexer lexer = new Lexer(text);
		Error error = lexer.makeTokens();
		List<Token> tokens = lexer.darTokens();
		
		if(error != null)
		{
			System.out.println(error.asString());
		}
		else
		{
			for(Token token : tokens)
			{
				System.out.println(token.representation());
			}
		}
		
		

	}
	
	public static String readFileAsSingleLine(String fileName) throws IOException 
	{
	    StringBuilder sb = new StringBuilder();
	    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) 
	    {
	        String line;
	        while ((line = br.readLine()) != null) 
	        {
	            sb.append(line).append("\n"); // Append the line and a line break
	        }
	    }
	    return sb.toString();
	}
}
