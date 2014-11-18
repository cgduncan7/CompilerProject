public class Quad
{
	private String operation, param1, param2, destination;
	
	public Quad(String op, String p1, String p2, String d)
	{
		this.operation = op;
		this.param1 = p1;
		this.param2 = p2;
		this.destination = d;
		
		System.out.println("Created new Quad: " + operation + ", " + param1 + ", " + param2 + ", " + destination);
	}
	
	public void setDestination(int i)
	{
		setDestination("" + i);
	}
	
	public void setDestination(String destination)
	{
		this.destination = destination;
	}
	
	public String toString()
	{
		return operation + ", " + param1 + ", " + param2 + ", " +  destination;
	}

}
