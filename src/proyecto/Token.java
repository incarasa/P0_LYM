package proyecto;

public class Token 
{
	
	//ATRIBUTES
	
	protected String type;

	
	//BUILDER
	
	public Token(String type)
	{
		this.type = type;
	}
	
	
	//METHODS
	
	public String representation()
	{
		return type;
	}
	
	

}
