import com.sun.org.apache.xerces.internal.xni.grammars.Grammar;


public class ExtendedQuad
{
	private String operator;
	private Field arg1, arg2, result;
	private int address;
	
	public ExtendedQuad(String op, Object a1, Object a2, Object r, int a)
	{
		this.operator = op;
		
		if (a1 == null)
		{
			this.arg1 = new Field("null",-1,-1,false);
		}
		else if (a1 instanceof String)
		{
			this.arg1 = new Field(a1.toString());
		}
		else
		{
			this.arg1 = new Field(a1.toString(), (int) a1, -1, false);
		}
		
		if (a2 == null)
		{
			this.arg2 = new Field("null",-1,-1,false);
		}
		else if (a2 != null && a2 instanceof String)
		{
			this.arg2 = new Field(a2.toString());
		}
		else
		{
			this.arg2 = new Field(a2.toString(), (int) a2, -1, false);
		}
		
		if (r == null)
		{
			this.result = new Field("null",-1,-1,false);
		}
		else if (r != null && r instanceof String)
		{
			this.result = new Field(r.toString());
		}
		else
		{
			this.result = new Field(r.toString(), (int) r, -1, false);
		}
		
		this.address = a;
	}
	
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Field getArg1() {
		return arg1;
	}

	public void setArg1(Field arg1) {
		this.arg1 = arg1;
	}

	public Field getArg2() {
		return arg2;
	}

	public void setArg2(Field arg2) {
		this.arg2 = arg2;
	}

	public Field getResult() {
		return result;
	}

	public void setResult(Field result) {
		this.result = result;
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}
	
	public String toString()
	{
		return this.operator + ", " + this.arg1 + ", " + this.arg2 + ", " + this.result + ", " + this.address;
	}
}
