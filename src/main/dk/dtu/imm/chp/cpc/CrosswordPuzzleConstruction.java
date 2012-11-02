package dk.dtu.imm.chp.cpc;

import java.util.List;

public class CrosswordPuzzleConstruction {
	
	int alphabetSize = 0;
	int noOfStrings = 0;
	int puzzleSize = 0;
	
	List<Character> alphabet = null;
	char[][] entries = null; 
	List<String> strings = null;
	
	private static Decoder decoder;
	private static HeuristicAlgorithm algo;
	
	public CrosswordPuzzleConstruction() {
		decoder = Decoder.getInstance();
		
		alphabetSize = decoder.getAlphabetSize();
		noOfStrings = decoder.getNoOfStrings();
		puzzleSize = decoder.getPuzzleSize();
		
		alphabet = decoder.getAlphabet();
		entries = decoder.getEntries();
		strings = decoder.getStrings();
	}
	
	public static void main(String[] args) {
		algo = HeuristicAlgorithm.getInstance();
		algo.runAlgorithm();
	}
	
}
