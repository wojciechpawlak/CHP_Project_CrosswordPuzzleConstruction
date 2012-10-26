package dk.dtu.imm.chp.cpc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Decoder {

	private static Decoder decoder = null;
	
	private int alphabetSize = 0;
	private int noOfStrings = 0;
	private int puzzleSize = 0;
	private List<Character> alphabet = null;
	
	
	private Decoder() {
		
		
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
			
			File f = new File(filename);
			FileInputStream fis = new FileInputStream(filename);
			BufferedReader br = new BufferedReader (new InputStreamReader(fis));
			String strLine;
			int counter=0;
			
			while ((strLine = br.readLine()) != null) {
			
				if (counter == 0) {
					
					String[] inputsSizes = strLine.split(";");
					
					if (inputsSizes.length != 3) {
						throw new WrongInputException("Incorrect number of parameters in the first line!");
					}
					
					alphabetSize = Integer.parseInt(inputsSizes[0]);
					noOfStrings = Integer.parseInt(inputsSizes[1]);
					puzzleSize = Integer.parseInt(inputsSizes[2]);
					
					continue;
				
				}
				
				if (counter == 1) {
					
			 		String[] letters = strLine.split(";");
					
					if (letters.length != alphabetSize) throw new WrongInputException("The number of letters in the input file does not correspond to the parameter specified in the first line!");
					
					for (int i=0; i<letters.length; i++) {
						alphabet.add(letters[i].toCharArray()[0]);
					}
					
					continue;
					
				}
				
				if (counter > 1 && counter < puzzleSize + 2) {
				
					
					continue;
					
				}
				
				if (counter > puzzleSize + 2) {
					
					continue;
					
				}
				
				counter++;
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

}
