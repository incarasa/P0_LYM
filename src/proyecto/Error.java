package proyecto;

public class Error 
{
	private String errorName;
	private String details;
	
	public Error(String errorName, String details)
	{
		this.errorName = errorName;
		this.details = details;
	}
	
	public String asString()
	{
		return errorName + ":" + details;
	}
	
}
