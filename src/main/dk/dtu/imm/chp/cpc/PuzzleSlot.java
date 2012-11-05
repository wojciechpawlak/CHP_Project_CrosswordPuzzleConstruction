package dk.dtu.imm.chp.cpc;

public class PuzzleSlot {

	private int line = -1;
	private int length = 0;
	private int startInd = -1;
	private Direction dir;
	
	public PuzzleSlot(int line, int length, int startInd, Direction dir) {
		this.line = line;
		this.length = length;
		this.startInd = startInd;
		this.dir = dir;
	}

	public int getLine() {
		return line;
	}

	public int getLength() {
		return length;
	}
	
	
	public Direction getDirection() {
		return dir;
	}

	public int getStartInd() {
		return startInd;
	}

	@Override
	public String toString() {
		
		if(this.dir.equals(Direction.HORIZONTAL)) {
			return "[Line: "+this.line+", Length: "+this.length+", startIndex: "+this.startInd+ "]";
		} else {
			return "[Column: "+this.line+", Length: "+this.length+", startIndex: "+this.startInd+ "]";
		}
	
	}
	
	
	
}
