package dk.dtu.imm.chp.cpc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Decoder {

	private static Decoder decoder = null;

	private int alphabetSize = 0;
	private int noOfStrings = 0;
	private int puzzleSize = 0;
	private List<Character> alphabet = null;
	private char[][] entries;
	private List<String> strings = null;

	private Decoder() {
		strings = new ArrayList<String>();
		alphabet = new ArrayList<Character>();

	}

	public static Decoder getInstance() {
		if (decoder == null) {
			decoder = new Decoder();
		}
		return decoder;
	}

	public void decode(String filename) {

		try {

			if (filename != null && filename != "") {

				InputStream is = Decoder.class.getClassLoader()
						.getResourceAsStream(filename);
				// FileInputStream fis = new FileInputStream(filename);
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is));
				String strLine;
				int counter = 0;
				int lineCounter = 0;

				while ((strLine = br.readLine()) != null) {

					if (counter == 0) {

						String[] inputsSizes = strLine.split(";");

						if (inputsSizes.length != 3) {
							throw new WrongInputException(
									"Incorrect number of parameters in the first line!");
						}

						alphabetSize = Integer.parseInt(inputsSizes[0]);
						noOfStrings = Integer.parseInt(inputsSizes[1]);
						puzzleSize = Integer.parseInt(inputsSizes[2]);

						if (alphabetSize <= 0 || noOfStrings <= 0
								|| puzzleSize <= 0)
							throw new WrongInputException(
									"There cannot be an argument less or equal zero in the first line!");

						entries = new char[puzzleSize][puzzleSize];

					}

					if (counter == 1) {

						String[] letters = strLine.split(";");

						if (letters.length != alphabetSize)
							throw new WrongInputException(
									"The number of letters in the input file does not correspond to the parameter specified in the first line!");

						for (int i = 0; i < letters.length; i++) {
							alphabet.add(letters[i].toCharArray()[0]);
						}

					}

					if (counter > 1 && counter < puzzleSize + 2) {

						if (lineCounter == puzzleSize)
							throw new WrongInputException(
									"Too many lines representing a puzzle of size "
											+ puzzleSize + ".");

						String[] chars = strLine.split(";");

						if (chars.length != puzzleSize)
							throw new WrongInputException(
									"The input is not of the size of the parameter specified in the input file!");

						for (int i = 0; i < chars.length; i++) {
							if (chars[i].equals("#") || chars[i].equals("_"))
								entries[i][lineCounter] = chars[i].charAt(0);
							else
								throw new WrongInputException(
										"Incorrect input in the puzzle part!");
						}

						lineCounter++;

					}

					if (counter >= puzzleSize + 2
							&& counter < puzzleSize + noOfStrings + 2) {

						if (checkCharacterInAlphabet(strLine)) {
							strings.add(strLine);
						}

					}

					counter++;
				}

				is.close();
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private boolean checkCharacterInAlphabet(String s) {

		ArrayList<Boolean> results = new ArrayList<Boolean>();

		for (int i = 0; i < s.length(); i++) {
			for (Character c : alphabet) {
				if (s.charAt(i) == c) {
					results.add(true);
				}
			}
		}

		return results.size() != s.length();
	}

	public int getAlphabetSize() {
		return alphabetSize;
	}

	public int getNoOfStrings() {
		return noOfStrings;
	}

	public int getPuzzleSize() {
		return puzzleSize;
	}

	public List<Character> getAlphabet() {
		return alphabet;
	}

	public char[][] getEntries() {
		return entries;
	}

	public List<String> getStrings() {
		return strings;
	}

}
