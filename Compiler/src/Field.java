public class Field
{
	private boolean isPtr;
	private String name;
	private int symbolTablePtr;
	private int nextUse;
	
	public Field(String symbol)
	{
		boolean found = false;
		VerboseStack bs = (VerboseStack) EG1.blockStack.clone();
		Object o;
		while (!bs.isEmpty() && !found)
		{
			o = bs.pop();
			int index = 0;
			for (Symbol s : EG1.symbolTable)
			{
				if (s.getBlock().equals(o.toString()) && s.getName().equals(symbol))
				{
					this.symbolTablePtr = index;
					this.nextUse = -1;
					this.isPtr = true;
					this.name = s.getName();
					found = true;
				}
				if (found) break;
				index++;
			}
			
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