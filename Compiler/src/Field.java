public class Field
{
	private boolean isPtr;
	private String name;
	private int symbolTablePtr;
	private int nextUse;
	
	public Field(String symbol)
	{
		int index = 0;
		boolean found = false;
		for (Symbol s : EG1.symbolTable)
		{
			if (s.getBlock().equals("block" + EG1.nestingLevel) && s.getName().equals(symbol))
			{
				this.symbolTablePtr = index;
				this.nextUse = -1;
				this.isPtr = true;
				this.name = s.getName();
				found = true;
				break;
			}
			index++;
		}
		
		if (!found)
		{
			this.name = symbol;
			this.symbolTablePtr = -1;
			this.nextUse = -1;
			this.isPtr = false;
		}
	}
	
	public Field(String name, int stPtr, int nU, boolean isPtr)
	{
		this.name = name;
		this.symbolTablePtr = stPtr;
		this.nextUse = nU;
		this.isPtr = isPtr;
	}
	
	
	public boolean isPtr() {
		return isPtr;
	}

	public void setPtr(boolean isPtr) {
		this.isPtr = isPtr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSymbolTablePtr() {
		return symbolTablePtr;
	}

	public void setSymbolTablePtr(int symbolTablePtr) {
		this.symbolTablePtr = symbolTablePtr;
	}

	public int getNextUse() {
		return nextUse;
	}

	public void setNextUse(int nextUse) {
		this.nextUse = nextUse;
	}

	public String toString()
	{
		return "( " + this.name + ", " + this.symbolTablePtr + ", " + this.nextUse + ", " + this.isPtr + " )";
	}
}