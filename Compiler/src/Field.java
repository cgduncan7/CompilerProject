import java.util.Stack;

/**
 * 
 * @author Collin Duncan & Blake LaFleur
 *
 */
public class Field
{
	private boolean isPtr;
	private String name;
	private int symbolTablePtr;
	private int nextUse;
	
	/**
	 * 
	 * @param symbol the name of the of item in the symbol table to match
	 */
	public Field(String symbol)
	{
		boolean found = false;
		//For each traversal we will need to reset the blockStack
		Stack<Object> bs = (Stack<Object>) EG1.blockStack.clone(); 
		Object o;
		while (!bs.isEmpty() && !found)
		{
			o = bs.pop();
			int index = 0;
			/*Traverse through each item in the symbol table to to see if the symbol table already contains
			 * this value for the current block.
			 */
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
		/*If the symbol was not found in the Symbol Table, set its nextuse to -1 and set
		 * the pointer flag to -1 since there is currently no symbol table reference. 
		 */
		if (!found)
		{
			this.name = symbol;
			this.symbolTablePtr = -1;
			this.nextUse = -1;
			this.isPtr = false;
		}
	}
	
	/**
	 * 
	 * @param name The name of the value
	 * @param stPtr The pointer to the location in the symbol table
	 * @param nU The pointer to the nextuse quad location
	 * @param isPtr Flag to set if the value points to an item in the SymbolTable
	 */
	public Field(String name, int stPtr, int nU, boolean isPtr)
	{
		this.name = name;
		this.symbolTablePtr = stPtr;
		this.nextUse = nU;
		this.isPtr = isPtr;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isPtr() {
		return isPtr;
	}

	/**
	 * 
	 * @param isPtr
	 */
	public void setPtr(boolean isPtr) {
		this.isPtr = isPtr;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return
	 */
	public int getSymbolTablePtr() {
		return symbolTablePtr;
	}

	/**
	 * 
	 * @param symbolTablePtr
	 */
	public void setSymbolTablePtr(int symbolTablePtr) {
		this.symbolTablePtr = symbolTablePtr;
	}

	/**
	 * 
	 * @return
	 */
	public int getNextUse() {
		return nextUse;
	}
	
	/**
	 * 
	 * @param nextUse
	 */
	public void setNextUse(int nextUse) {
		this.nextUse = nextUse;
	}

	/**
	 * Generates a formatted string, giving the contents of the Quad
	 */
	public String toString()
	{
		return "( " + this.name + ", " + this.symbolTablePtr + ", " + this.nextUse + ", " + this.isPtr + " )";
	}
}