/*
 * This class is the object used to store the data for each quad on the stack.
 * Each argument is made up of a 'Field' which contains the Symbol Table pointer
 * and a placeholder for its next use.
 */
/**
 * 
 * @author Collin Duncan & Blake LaFleur
 *
 */

public class ExtendedQuad
{
	private String operator;
	private Field arg1, arg2, result;
	private int address;
	
	/**
	 * 
	 * @param op The operation for the quad to perform
	 * @param a1 Argument 1
	 * @param a2 Argument 2
	 * @param r  Result
	 * @param a  Address in memory of data
	 */
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
		else if (a2 instanceof String)
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
		else if (r instanceof String)
		{
			this.result = new Field(r.toString());
		}
		else
		{
			this.result = new Field(r.toString(), (int) r, -1, false);
		}
		
		this.address = a;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * 
	 * @param operator
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * 
	 * @return
	 */
	public Field getArg1() {
		return arg1;
	}

	/**
	 * 
	 * @param arg1
	 */
	public void setArg1(Field arg1) {
		this.arg1 = arg1;
	}

	/**
	 * 
	 * @return
	 */
	public Field getArg2() {
		return arg2;
	}

	/**
	 * 
	 * @param arg2
	 */
	public void setArg2(Field arg2) {
		this.arg2 = arg2;
	}

	/**
	 * 
	 * @return
	 */
	public Field getResult() {
		return result;
	}
	
	/**
	 * 
	 * @param result
	 */
	public void setResult(Field result) {
		this.result = result;
	}

	/**
	 * 
	 * @return
	 */
	public int getAddress() {
		return address;
	}

	/**
	 * 
	 * @param address
	 */
	public void setAddress(int address) {
		this.address = address;
	}
	
	/**
	 * Generates a formatted string, giving the contents of the Quad
	 */
	public String toString()
	{
		return this.operator + ", " + this.arg1 + ", " + this.arg2 + ", " + this.result + ", " + this.address;
	}
}
