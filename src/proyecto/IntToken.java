package proyecto;

public class IntToken extends Token
{
	public int value;
	
	public IntToken(String type, int value)
	{
		super(type);
		this.value = value;
	
	}
	
	@Override
	public String representation()
	{
		return this.type + ":" + this.value;
	}
}
