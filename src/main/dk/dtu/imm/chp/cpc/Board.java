package dk.dtu.imm.chp.cpc;

import java.util.ArrayList;
import java.util.List;

public class Board {
	
	private List<String> strings;
	private List<PuzzleSlot> vertical;
	private List<PuzzleSlot> horizontal;
	private List<Integer> lengths;
	private char [][] currEntries;

	public Board (){
		this.strings     = Decoder.getInstance().getStrings();
		this.currEntries = initEntries();
		this.horizontal  = new ArrayList<PuzzleSlot>();
		this.vertical    = new ArrayList<PuzzleSlot>();
		determineHorizontalSlots();
		determineVerticalSlots();
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
							horizontal.add(new PuzzleSlot(i, count, startInd, Direction.HORIZONTAL));
						} 
					}
					
					count = 0;
					startInd = j + 1;

				}
			}
			if (count != 0)
				horizontal.add(new PuzzleSlot(i, count, startInd, Direction.HORIZONTAL));

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
							vertical.add(new PuzzleSlot(i, count, startInd, Direction.VERTICAL));
						} 
					}
					
					count = 0;
					startInd = j + 1;

				}
			}
			if (count != 0)
				vertical.add(new PuzzleSlot(i, count, startInd, Direction.VERTICAL));

			startInd = 0;

		}
	}
	
	private char[][] initEntries() {
		int puzzleSize = Decoder.getInstance().getPuzzleSize();
		char[][] result = new char[puzzleSize][puzzleSize];
		char[][] decodedEntries = Decoder.getInstance().getEntries();

		for(int i=0; i<decodedEntries.length; i++) {
			for(int j=0; j<decodedEntries.length; j++) {
				result[i][j] = decodedEntries[i][j];
			}
		}
		
		return result;
	}

	public List<PuzzleSlot> getVertical() {
		return vertical;
	}

	public List<PuzzleSlot> getHorizontal() {
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
	
}
