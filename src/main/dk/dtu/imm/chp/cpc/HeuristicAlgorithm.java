package dk.dtu.imm.chp.cpc;

import java.util.Collections;
import java.util.List;

public class HeuristicAlgorithm {

	public static HeuristicAlgorithm algorithm = null;
	
	List<String> wordsUsed;
	
	private HeuristicAlgorithm() {
		this.wordsUsed = Decoder.getInstance().getStrings();
	}

	public static HeuristicAlgorithm getInstance() {
		
		if (algorithm == null) {
			algorithm = new HeuristicAlgorithm();
		}
		
		return algorithm;
	}
	
	public void runAlgorithm() {

		String currentWord = null;
		
		//	    Sort all the words by length, descending.
		SizeComparator sc = new SizeComparator();
		Collections.sort(wordsUsed, sc);
		
		
		//	    Take the first word and place it on the board.

		//	    Take the next word.

		
		for (char c : currentWord.toCharArray()) {
		    // process c
		}
		
		//	    Search through all the words that are already on the board and see if there are any possible intersections (any common letters) with this word.

		//	    If there is a possible location for this word, loop through all the words that are on the board and check to see if the new word interferes.
		
		//	    If this word doesn't break the board, then place it there and go to step 3, otherwise, continue searching for a place (step 4).
		
		//	    Continue this loop until all the words are either placed or unable to be placed.

	}

}
