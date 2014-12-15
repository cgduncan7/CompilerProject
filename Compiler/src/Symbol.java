
/*
 * This object represents a symbol in the Symbol Table
 */
/**
 * 
 * @author Collin Duncan and Blake LaFleur
 *
 */
public class Symbol {
	private String name;
	private String block;
	private int nesting;
	private int offset;
	private int nextUse;
	private int locations;
	
	public Symbol(String n, String b, int ne, int o, int nu, int l) {
		this.name = n;
		this.block = b;
		this.nesting = ne;
		this.offset = o;
		this.nextUse = nu;
		this.locations = l;
	}
	
	@Override
	public String toString() {
		return this.name + ", " + this.block + ", " + this.nesting + ", " + this.offset + ", " + this.nextUse;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public int getNesting() {
		return nesting;
	}

	public void setNesting(int nesting) {
		this.nesting = nesting;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getNextUse() {
		return nextUse;
	}

	public void setNextUse(int nextUse) {
		this.nextUse = nextUse;
	}

	public int getLocations() {
		return locations;
	}

	public void setLocations(int locations) {
		this.locations = locations;
	}
	
	
}
