import java.util.Stack;

public class VerboseStack extends Stack
{
	@Override
	public Object pop()
	{
		Object e = super.pop();
		//System.out.println("Popped: " + e);
		return e;
	}
	
	@Override
	public Object push(Object obj)
	{
		super.push(obj);
		//System.out.println("Pushed: " + obj.toString());
		return obj;
	}
}
