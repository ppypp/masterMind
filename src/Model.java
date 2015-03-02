/**
 * Authors
 * Michael Gerten, Angel Zuaznabal, Cody Sehl 
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

/*
 * The model Class calculates the code, the accuracy of guesses, and stores past guesses/responses.
 */
public class Model {
	private Random codeMaker = new Random();
	// Counter for which # guess it is
	private int counter;
	// Number of Columns
	private int col;
	// Number of rows
	private int row;
	// Number of color choices
	private int numColors;
	// current code in this instance
	private int[] currentCode;
	// Array of guesses
	private int[][] guesses;
	// Array of Responses
	private int[][] responses;
	// Array of currentGuess
	private int[] currentGuess;
	// Array of current response
	private int[] currentResponse;
	// Boolean of whether this game has revealed currentCode 
	private boolean cheated;

	/*
	 * Constructor, make local copies of past variables, 
	 * start the game for the first time
	 */
	public Model(int ROWS, int COLORS, int COLUMNS) {
		// Set variables
		col = COLUMNS;
		row = ROWS;
		numColors = COLORS;
		//Initialize array size
		currentCode = new int[col];
		guesses = new int[row][col];
		responses = new int[row][col];
		currentGuess = new int[col];
		currentResponse = new int[col];
		startGame(); // When model is first called, begin a game
	}
	/*
	 * Start the game, first by initializing values, then making the new code
	 */
	public void startGame() { 
		//Initialize board state -> resets when starting a new game
		counter = 0;
		cheated = false;
		guesses = new int[row][col];
		responses = new int[row][col];
		currentGuess = new int[col];
		currentResponse = new int[col];
		currentCode = new int[col];
		// Make hidden code for this game
		currentCode = makeCode();
	}

	/*
	 * Create the random code that the user will guess
	 */
	private int[] makeCode() {
		int[] code = new int[col];
		//To keep track of
		HashSet<Integer> s = new HashSet<Integer>();
		// Fill the code[]
		for (int i = 0; i < col; i++) {
			int num = codeMaker.nextInt(numColors);
			// Get Numbers inside
			while (s.contains(num)){ // for no repeats
				//Grab a number
				num = codeMaker.nextInt(numColors);
			}
			// Add unique num to set
			s.add(num);
			// Because zero is white, need to increment
			num++;
			//Add to array
			code[i] = num;
		}
		return code;
	}

	/*
	 * Check the users guess against the currentCode
	 */
	public void checkGuess() { // only called when user hits submit guess
		// row wide char[]-
		// 1 B(black) is correct place and color
		// 2 W(white) is correct Color, wrong place
		// 0 G(lightGray) is neither / no match
		//IF need char[] due to requirements....
		// use the conversion method
		int[] result = new int[col];
		int[] guess = new int[col];
		//it has been updated with each mouse click
		guess = currentGuess;
		//Store guess as it is now complete
		// Stupid but works
		for (int i = 0; i < guess.length; i++) {
			guesses[counter][i] = guess[i];
		}
		//Because it was using a reference rather than new object (array)
		int[] current = new int[col]; 
		// To preserve integrity of currentCode
		for (int i = 0; i < currentCode.length; i++) {
			current[i] = currentCode[i];
		}

		// for each result spot / guess
		for (int i = 0; i < col; i++) {
			// in correct spot
			if(guess[i] == current[i]){ 
				//Black
				result[i] = 1; 
				//Effective null Value (So not compared to again)
				current[i] = 0; 
			}else{
				// for each currentCode spot
				for (int j = 0; j < col; j++) { 
					if(guess[i] == current[j]){
						//White
						result[i] = 2; 
						//Effective null Value
						current[j] = 0; 
					}
				}
				//Still haven't found match:
				//Keep LightGray
			}
		}
		//store most recent response-> Needed?
		currentResponse = result; 
		responses[counter] = result;
		//Guess done, increment counter
		counter++;
	}

	/*
	 * Check if the game is finished, victory 
	 */
	public boolean winGame(int[] guess){
		if (Arrays.equals(guess,currentCode)){
			return true;
		}else{
			return false;
		}
	}

	/*
	 * Check if 12 guesses have occured
	 */
	public boolean loseGame(){
		if (counter > 11){
			//GameOver
			return true;
		}
		return false;
	}

	public void setGuess(int place, int color){//color: Could be either an int or char-> simple change
		currentGuess[place] = color;
	}

	public int[] getGuess(int row){
		return guesses[row];
	}

	public int[] getResponse(int row){
		return responses[row];
	}

	/*
	 * Getter Method for Response - Used by View
	 */
	public int[] getCurrentCode(){
		//To be consistent in state of guess
		currentGuess = new int[col];
		return currentCode;
	}

	public int getCounter(){
		return counter;
	}

	public int[] getCurrentGuess(){
		return currentGuess;
	}
	public int[] getGuess(){
		return guesses[counter--];
	}

	public void setCheated(boolean b) {
		cheated = b;
	}

	public boolean getCheated() {
		return cheated;
	}
}
