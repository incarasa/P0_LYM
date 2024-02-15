package proyecto;

public class StrToken extends Token
{
	public String value;
	
	public StrToken(String type, String value)
	{
		super(type);
		this.value = value;
	
	}
	
	@Override
	public String representation()
	{
		return this.type + "->" + this.value;
	}
}
