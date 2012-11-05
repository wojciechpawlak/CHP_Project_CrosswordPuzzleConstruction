package dk.dtu.imm.chp.cpc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board implements Cloneable {

	private List<String> strings;
	private List<PuzzleSlot> vertical;
	private List<PuzzleSlot> horizontal;
	private List<PuzzleSlot> all;
	private List<Integer> lengths;
	private char[][] currEntries;

	public Board() {
		this.strings = Decoder.getInstance().getStrings();
		this.currEntries = initEntries();
		this.horizontal = new ArrayList<PuzzleSlot>();
		this.vertical = new ArrayList<PuzzleSlot>();
		determineHorizontalSlots();
		determineVerticalSlots();
		collectAllSlots();
	}
	
	public Board(Board board) {
		this.strings = Decoder.getInstance().getStrings();
		this.currEntries = new char[Decoder.getInstance().getEntries().length][Decoder.getInstance().getEntries().length];
		
		for(int i=0; i<this.currEntries.length;i++) {
			for(int j=0; j<this.currEntries.length;j++) {
				this.currEntries[i][j] = board.getCurrEntries()[i][j];
			}
		}
		
		this.horizontal = new ArrayList<PuzzleSlot>(board.getHorizontalSlots());
		this.vertical = new ArrayList<PuzzleSlot>(board.getVerticalSlots());
		this.all = new ArrayList<PuzzleSlot>(board.getAllSlots());
		
//		Collections.copy(this.horizontal, board.getHorizontalSlots());
//		Collections.copy(this.vertical, board.getVerticalSlots());
//		Collections.copy(this.all, board.getAllSlots());
	}

	private void collectAllSlots() {

		this.all = new ArrayList<PuzzleSlot>();
		for (PuzzleSlot p : horizontal) {
			this.all.add(p);
		}
		for (PuzzleSlot p : vertical) {
			this.all.add(p);
		}
		Collections.sort(this.all, new SlotComparator());
		System.out.println("Sorted.");
	}

	public boolean fillIn(PuzzleSlot slot, String word) {
		List<Integer> changed = new ArrayList<Integer>();
		if (slot.getDirection().equals(Direction.HORIZONTAL)) {
			for (int i = 0; i < word.length(); i++) {
				char current = this.currEntries[slot.getLine()][slot
						.getStartInd() + i];
				if (current == '_') {
					changed.add(slot.getStartInd() + i);
					this.currEntries[slot.getLine()][slot.getStartInd() + i] = word
							.charAt(i);
					if (!checkWordAt(slot.getStartInd() + i, slot.getLine(),
							Direction.VERTICAL)) {
						for (Integer j : changed) {
							this.currEntries[slot.getLine()][j] = '_';
						}
						return false;
					}
				} else if (current != word.charAt(i)) {
					for (Integer j : changed) {
						this.currEntries[slot.getLine()][j] = '_';
					}
					return false;
				}
			}
		} else {
			for (int i = 0; i < word.length(); i++) {
				char current = this.currEntries[slot.getStartInd() + i][slot
						.getLine()];
				if (current == '_') {
					changed.add(slot.getStartInd() + i);
					this.currEntries[slot.getStartInd() + i][slot.getLine()] = word
							.charAt(i);
					if (!checkWordAt(slot.getLine(), slot.getStartInd() + i,
							Direction.HORIZONTAL)) {
						for (Integer j : changed) {
							this.currEntries[j][slot.getLine()] = '_';
						}
						return false;
					}
				} else if (current != word.charAt(i)) {
					for (Integer j : changed) {
						this.currEntries[j][slot.getLine()] = '_';
					}
					return false;
				}
			}
		}
		int currentIndex = all.indexOf(slot);
		all.remove(currentIndex);
		return true;
	}

	private boolean checkWordAt(int x, int y, Direction direction) {
		if (direction.equals(Direction.HORIZONTAL)) {
			PuzzleSlot predecessor = null;
			for (PuzzleSlot slot : horizontal) {
				if (slot.getLine() == y && slot.getStartInd() <= x)
					if (predecessor == null)
						predecessor = slot;
					else if (predecessor.getStartInd() < slot.getStartInd())
						predecessor = slot;
			}

			// check if predecessor is a filled world
			for (int i = 0; i < predecessor.getLength(); i++) {
				if (this.currEntries[y][predecessor.getStartInd() + i] == '_')
					return true;
			}

			// check if created word exists
			String createdWord = new String();
			for (int i = 0; i < predecessor.getLength(); i++) {
				createdWord += this.currEntries[y][predecessor.getStartInd() + i];
			}
			for (String word : this.strings) {
				if (word.equals(createdWord))
					return true;
			}
		} else {
			PuzzleSlot predecessor = null;
			for (PuzzleSlot slot : vertical) {
				if (slot.getLine() == x && slot.getStartInd() <= y)
					if (predecessor == null)
						predecessor = slot;
					else if (predecessor.getStartInd() < slot.getStartInd())
						predecessor = slot;
			}

			// check if predecestor is a filled world
			for (int i = 0; i < predecessor.getLength(); i++) {
				if (this.currEntries[predecessor.getStartInd() + i][x] == '_')
					return true;
			}

			// check if created word exists
			String createdWord = new String();
			for (int i = 0; i < predecessor.getLength(); i++) {
				createdWord += this.currEntries[predecessor.getStartInd() + i][x];
			}
			for (String word : this.strings) {
				if (word.equals(createdWord))
					return true;
			}
		}

		return false;
	}

	private void determineHorizontalSlots() {

		int startInd = 0;
		for (int i = 0; i < currEntries.length; i++) {
			int count = 0;

			for (int j = 0; j < currEntries.length; j++) {
				if (currEntries[i][j] != '#') {
					count++;
				} else if (currEntries[i][j] == '#') {

					if (count != 0) {
						if (startInd < currEntries.length) {
							horizontal.add(new PuzzleSlot(i, count, startInd,
									Direction.HORIZONTAL));
						}
					}

					count = 0;
					startInd = j + 1;

				}
			}
			if (count != 0)
				horizontal.add(new PuzzleSlot(i, count, startInd,
						Direction.HORIZONTAL));

			startInd = 0;

		}
	}

	private void determineVerticalSlots() {

		int startInd = 0;
		for (int i = 0; i < currEntries.length; i++) {
			int count = 0;

			for (int j = 0; j < currEntries.length; j++) {
				if (currEntries[j][i] != '#') {
					count++;
				} else if (currEntries[j][i] == '#') {

					if (count != 0) {
						if (startInd < currEntries.length) {
							vertical.add(new PuzzleSlot(i, count, startInd,
									Direction.VERTICAL));
						}
					}

					count = 0;
					startInd = j + 1;

				}
			}
			if (count != 0)
				vertical.add(new PuzzleSlot(i, count, startInd,
						Direction.VERTICAL));

			startInd = 0;

		}
	}

	private char[][] initEntries() {
		int puzzleSize = Decoder.getInstance().getPuzzleSize();
		char[][] result = new char[puzzleSize][puzzleSize];
		char[][] decodedEntries = Decoder.getInstance().getEntries();

		for (int i = 0; i < decodedEntries.length; i++) {
			for (int j = 0; j < decodedEntries.length; j++) {
				result[i][j] = decodedEntries[i][j];
			}
		}

		return result;
	}

	public List<PuzzleSlot> getVerticalSlots() {
		return vertical;
	}

	public List<PuzzleSlot> getHorizontalSlots() {
		return horizontal;
	}

	public List<String> getStrings() {
		return strings;
	}

	public List<Integer> getLengths() {
		return lengths;
	}

	public char[][] getCurrEntries() {
		return currEntries;
	}

	public List<PuzzleSlot> getAllSlots() {
		return all;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public String toString() {
		String result = "";
		for(int i=0; i<currEntries.length; i++) {
			result += " | ";
			for(int j=0; j<currEntries.length; j++) {
				result += currEntries[i][j] + " | ";
			}
			result+="\n";
		}
		return result;
	}

}
