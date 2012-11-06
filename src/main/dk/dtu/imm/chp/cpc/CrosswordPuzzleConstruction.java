package dk.dtu.imm.chp.cpc;

import java.util.List;

public class CrosswordPuzzleConstruction {

	private static Decoder decoder;
	private static HeuristicAlgorithm algo;

	public static void main(String[] args) {
		try {
			if (!args[0].equals("")) {
				decoder = Decoder.getInstance();
				decoder.decode(args[0]);
				algo = HeuristicAlgorithm.getInstance();
				Board initialBoard = new Board();
				Board result = algo.runAlgorithm(initialBoard);
				System.out.println(result);
			}
		} catch (NullPointerException e) {
			System.out.println("File does not exist");
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Path to file is not specfied");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
