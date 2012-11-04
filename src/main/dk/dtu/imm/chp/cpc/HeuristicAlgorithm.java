package dk.dtu.imm.chp.cpc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HeuristicAlgorithm {

	private static HeuristicAlgorithm algorithm = null;

	List<String> strings;
	List<String> wordsUsed;
	List<Integer> vertical;
	List<Integer> horizontal;
	List<PuzzleSlot> slots;
	List<Integer> lengths;
	char [][] currEntries;

	private HeuristicAlgorithm() {
		this.strings = Decoder.getInstance().getStrings();
		this.wordsUsed = new ArrayList<String>();
		this.vertical = new ArrayList<Integer>();
		this.horizontal = new ArrayList<Integer>();
		this.slots = new ArrayList<PuzzleSlot>();
	}

	public static HeuristicAlgorithm getInstance() {

		if (algorithm == null) {
			algorithm = new HeuristicAlgorithm();
		}

		return algorithm;
	}

	public void runAlgorithm() throws Exception {

		boolean answer = false;
		
		currEntries = copyEntries();
		
		if(strings != null && !strings.isEmpty()) {
			List<List<String>> segregatedWords = segregateStringsByLength(strings);
		
			determineFreeSlots();
			
			for(PuzzleSlot p : slots) {
				
				for(int a=0; a<lengths.size(); a++) {
					
					if(p.getLength() == lengths.get(a)) {
						
						List<String> certainList = segregatedWords.get(a);
						Random r = new Random();
						int randIndex = r.nextInt(certainList.size());
						String chosenString = certainList.get(randIndex);
						
						int currLine = p.getLine();
						int currStrInd = 0;
						int currPos = p.getStartInd();

						while(currPos != currEntries.length)
						
							if (currEntries[currLine][currPos] != '#') {
								currEntries[currLine][currPos] = chosenString.charAt(currStrInd);
								currStrInd++;
								currPos++;
							} else {
								if(currPos != 0) {
									break;
								}
							}
						}
					
					}
				
					if(entriesFilled(currEntries)) break;
				
				}
			}

		}
		
		// Sort all the words by length, descending.
		/*SizeComparator sc = new SizeComparator();
		char[][] currEntries = Decoder.getInstance().getEntries();
		Collections.sort(strings, sc);

		while (!entriesFilled(currEntries)) {

			int count = 0;
			for (int i = 0; i < currEntries.length; i++) {

				for (int j = 0; j < currEntries.length; j++) {
					if (currEntries[i][j] != '#') {
						if (currEntries[i][j] == '_') {
							count++;
						} else {
							if (j != 0) {
								break;
							}
						}
					}
				}

				String s = null;

				do {

					Random r = new Random();
					int randomInd = r.nextInt(strings.size());
					s = strings.get(randomInd);

				} while (s.length() != count);

				int strInd = 0;

				for (int j = 0; j < currEntries.length; j++) {
					if (currEntries[i][j] == '_') {
						currEntries[i][j] = s.charAt(strInd);
						strInd++;
					}
					if (strInd == s.length()) {
						break;
					}
				}

				count = 0;
			}

		}

		/*
		 * if (strings != null && !strings.isEmpty()) {
		 * 
		 * int count = 0; for (String s : strings) {
		 * 
		 * int strLength = s.length(); count = 0; for(int i=0;
		 * i<currEntries.length; i++) { for(int j=0; j<currEntries.length; j++)
		 * { if(currEntries[j][i]!='#') { if(currEntries[j][i] == '_') {
		 * count++; } } else { if(count!=0) { break; } else { continue; } } } if
		 * (strLength == count) { int strInd = 0; for(int j=0;
		 * j<currEntries.length; j++) { if(currEntries[j][i]=='_') {
		 * currEntries[j][i] = s.charAt(strInd); strInd++; } if(strInd ==
		 * strLength) { break; } } break; } count=0; } }
		 * 
		 * 
		 * // Take the first word and place it on the board.
		 * 
		 * // Take the next word.
		 * 
		 * 
		 * 
		 * // Search through all the words that are already on the board and //
		 * see if there are any possible intersections (any common letters) //
		 * with this word.
		 * 
		 * // If there is a possible location for this word, loop through all //
		 * the words that are on the board and check to see if the new word //
		 * interferes.
		 * 
		 * // If this word doesn't break the board, then place it there and go
		 * // to step 3, otherwise, continue searching for a place (step 4).
		 * 
		 * // Continue this loop until all the words are either placed or //
		 * unable to be placed. }
		 */
	
	private int countAllPosssibilities() {
		int count = 0;
		
		int max = Collections.max(lengths);
		
		
		
		return count;
	}

	private void determineFreeSlots() {

		char[][] currEntries = Decoder.getInstance().getEntries();
		int startInd = 0;

		for (int i = 0; i < currEntries.length; i++) {
			int count = 0;

			for (int j = 0; j < currEntries.length; j++) {
				if (currEntries[i][j] != '#') {
					count++;
				} else if (currEntries[i][j] == '#') {

					if (count != 0) {
						if (startInd < currEntries.length) {
							slots.add(new PuzzleSlot(i, count, startInd, Direction.HORIZONTAL));
						} 
					}
					
					count = 0;
					startInd = j + 1;

				}
			}
			if (count != 0)
				slots.add(new PuzzleSlot(i, count, startInd, Direction.HORIZONTAL));

			startInd = 0;

		}
	}

	private List<List<String>> segregateStringsByLength(List<String> strings) {

		int count = 1;
		lengths = new ArrayList<Integer>();

		for (String s : strings) {
			if (!lengths.contains(s.length()))
				lengths.add(s.length());
		}

		Collections.sort(lengths);

		List<List<String>> lists = new ArrayList<List<String>>();

		for (int i = 0; i < lengths.size(); i++) {
			lists.add(new ArrayList<String>());
		}

		for (String s : strings) {
			for (int i = 0; i < lengths.size(); i++) {
				if (s.length() == lengths.get(i)) {
					lists.get(i).add(s);
				}
			}
		}

		return lists;

	}

	public boolean entriesFilled(char[][] entries) {

		for (int i = 0; i < entries.length; i++) {
			for (int j = 0; j < entries.length; j++) {
				if (entries[i][j] == '_')
					return false;
			}
		}

		return true;
	}

	public char[][] copyEntries() {
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
	
	public List<Integer> getVertical() {
		return vertical;
	}

	public List<Integer> getHorizontal() {
		return horizontal;
	}

}
