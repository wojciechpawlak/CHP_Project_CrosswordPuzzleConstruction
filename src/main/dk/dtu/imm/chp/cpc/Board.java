package dk.dtu.imm.chp.cpc;

import java.util.ArrayList;
import java.util.List;

public class Board {
	List<String> strings;
	List<PuzzleSlot> vertical;
	List<PuzzleSlot> horizontal;
	List<Integer> lengths;
	char [][] currEntries;

	public Board (){
		this.strings     = Decoder.getInstance().getStrings();
		this.currEntries = initEntries();
		this.horizontal  = new ArrayList<PuzzleSlot>();
		this.vertical    = new ArrayList<PuzzleSlot>();
		determineHorizontalSlots();
		determineVerticalSlots();
	}
	
	public List<PuzzleSlot> getVertcialSlots(){
		return this.vertical;
	}
	
	public List<PuzzleSlot> getHorizontalSlots(){
		return this.horizontal;
	}
	
	public boolean fillIn(PuzzleSlot slot, String word){
		List<Integer> changed = new ArrayList<Integer>();
		if (slot.getDirection().equals(Direction.HORIZONTAL))
		{
		  for (int i = 0; i < word.length(); i++){
			  char current = this.currEntries[slot.getLine()][slot.getStartInd()+i];
			  if (current == 0){
				  changed.add(slot.getStartInd()+i);
				  this.currEntries[slot.getLine()][slot.getStartInd()+i] = word.charAt(i);
				  if (! checkWordAt(slot.getStartInd()+i, slot.getLine(), Direction.VERTICAL)){
					  for (Integer j:changed){
						  this.currEntries[slot.getLine()][j] = '_';
					  }
					  return false; 
				  }
			  }
			  else if (current != word.charAt(i)) {
				  for (Integer j:changed){
					  this.currEntries[slot.getLine()][j] = '_';
				  }
				  return false;
			  }
		  }
		}
		else {
			for (int i = 0; i < word.length(); i++){
				  char current = this.currEntries[slot.getStartInd()+i][slot.getLine()];
				  if (current == '_'){
					  changed.add(slot.getStartInd()+i);
					  this.currEntries[slot.getStartInd()+i][slot.getLine()] = word.charAt(i);
					  if (! checkWordAt(slot.getLine(), slot.getStartInd()+i, Direction.HORIZONTAL)){
						  for (Integer j:changed){
							  this.currEntries[j][slot.getLine()] = '_';
						  }
						  return false; 
					  }
				  }
				  else if (current != word.charAt(i)) {
					  for (Integer j:changed){
						  this.currEntries[j][slot.getLine()] = '_';
					  }
					  return false;
				  }
			  }
		}
		return true;
	}
	
	private boolean checkWordAt(int x, int y, Direction direction){
		if (direction.equals(Direction.HORIZONTAL)) {
			PuzzleSlot predecestor = null;
			for (PuzzleSlot slot:horizontal){
				if (slot.getLine() == y && slot.getStartInd() < x)
					if (predecestor == null)
						predecestor = slot;
					else if (predecestor.getStartInd() < slot.getStartInd())
						predecestor = slot;
			}
			
			//check if predecestor is a filled world
			for (int i = 0; i < predecestor.getLength(); i++){
				if(this.currEntries[predecestor.getStartInd() + i][y] == '_')
					return true;
			}
			
			//check if created word exists
			String createdWord = new String();
			for (int i = 0; i > predecestor.getLength(); i++){
				createdWord += this.currEntries[predecestor.getStartInd() + i][y];
			}
			for (String word:this.strings){
				if (word.equals(createdWord))
					return true;
			}
		}
		else
		{
			PuzzleSlot predecestor = null;
			for (PuzzleSlot slot:vertical){
				if (slot.getLine() == x && slot.getStartInd() < y)
					if (predecestor == null)
						predecestor = slot;
					else if (predecestor.getStartInd() < slot.getStartInd())
						predecestor = slot;
			}
			
			//check if predecestor is a filled world
			for (int i = 0; i < predecestor.getLength(); i++){
				if(this.currEntries[x][predecestor.getStartInd() + i] == '_')
					return true;
			}
			
			//check if created word exists
			String createdWord = new String();
			for (int i = 0; i > predecestor.getLength(); i++){
				createdWord += this.currEntries[x][predecestor.getStartInd() + i];
			}
			for (String word:this.strings){
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
}
