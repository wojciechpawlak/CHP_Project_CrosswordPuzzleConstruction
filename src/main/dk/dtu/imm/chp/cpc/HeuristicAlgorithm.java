package dk.dtu.imm.chp.cpc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HeuristicAlgorithm {

	private static HeuristicAlgorithm algorithm = null;

	List<String> strings;
	List<Integer> vertical;
	List<Integer> horizontal;
	List<PuzzleSlot> slots;
	List<Integer> lengths;
	char[][] currEntries;

	private HeuristicAlgorithm() {
		this.strings = Decoder.getInstance().getStrings();
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

	public boolean runAlgorithm() {
		
		if(strings!=null && !strings.isEmpty()) {
			List<List<String>> segregatedWords = segregateStringsByLength(strings);
			Board currentBoard = new Board();
			
			
			
		}
		return false;

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
							slots.add(new PuzzleSlot(i, count, startInd,
									Direction.HORIZONTAL));
						}
					}

					count = 0;
					startInd = j + 1;

				}
			}
			if (count != 0)
				slots.add(new PuzzleSlot(i, count, startInd,
						Direction.HORIZONTAL));

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

		for (int i = 0; i < decodedEntries.length; i++) {
			for (int j = 0; j < decodedEntries.length; j++) {
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
