package proyecto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static void main(String[] args) 
	{
		

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
