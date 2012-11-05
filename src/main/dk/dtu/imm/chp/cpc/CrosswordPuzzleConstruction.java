package dk.dtu.imm.chp.cpc;

import java.util.List;

public class CrosswordPuzzleConstruction {

	private static Decoder decoder;
	private static HeuristicAlgorithm algo;

	public static void main(String[] args) {
		try {
			
			decoder = Decoder.getInstance();
			decoder.decode(args[0]);
			algo = HeuristicAlgorithm.getInstance();
			Board initialBoard = new Board();
			algo.runAlgorithm(initialBoard);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
