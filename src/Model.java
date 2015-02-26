/**
 * Authors
 * Michael Gerten, Angel Zuaznabal, Cody Sehl 
 */

import java.util.HashSet;
import java.util.Random;

/*
 * The model Class calculates the code, the accuracy of guesses, and stores past guesses/responses.
 */
public class Model {
//ALL ARRAYS IN MODEL ARE NUMBERS, THAT WAY IT IS CONSTIENT
	//Also, easy comparisons!
	// Things we need
	// Row of 4 pegs (a guess)
	// Pool of 6 pegs (to grab from - ie. can't grab twice)
	// Array of Guesses - keep track of past

	private Random codeMaker = new Random();
	// Counter for which # guess it is
	private int counter;
	// Number of Columns
	private int col;
	// Number of rows
	private int row;
	// Number of color choices
	private int color;
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
		color = COLORS;
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
		intialize(guesses);
		intialize(responses);
		intialize(currentGuess);
		intialize(currentResponse);
		intialize(currentCode);
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
			int num = codeMaker.nextInt(color);
			// Get Numbers inside
			while (s.contains(num)){ // for no repeats
				//Grab a number
				num = codeMaker.nextInt(color);
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
		// To preserve integrity of currentCode //Why it is still killing itself- > NO IDeA TODO MAKe Sure WorkS
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
		 //clear currentGuess as it is a past guess now
		//intialize(currentGuess);
		// randomize output? maybe in a different step?
		//Guess done, increment counter
		counter++;
	}
/*
 * Check if the game is finished, victory 
 */
	public boolean winGame(){
		if (counter > 11){
			//GameOver TODO other stuff
			return false;
		}else{
			if (currentGuess == currentCode){
				return true;
			}else{
				return false;
			}
		}
	}

	/*
	 * Initializing 2-D arrays, set value to 0 to display white when not changed
	 */
	private void intialize(int[][] array) {
		for (int i = 0; i < row; i++) { // ROW
			for (int j = 0; j < col; j++) { // COL
				array[i][j] = 0;
			}
		}
	}
	/*
	 * Overload, for 1-D arrays 
	 */
	private void intialize(int[] array) {
		for (int i = 0; i < col; i++) { // COL
			array[i] = 0;
		}
	}
	
	/*
	 * Conversion from Char to Int if need to use Chars to represent colors (requirement question)
	 */
	private int[] charToInt(char[] guess) {
		int[] output = new int[guess.length];
		intialize(output);
		// Do Stuff if needed
		return output;
	}
	/*
	 * Setter Method for Guess - Used by Controller
	 */
	public void setGuess(int place, int color){//color: Could be either an int or char-> simple change
		currentGuess[place] = color;
	}
	/*
	 * Getter Method for Guess - Used by View
	 */
	public int[] getGuess(int row){
		return guesses[row];
	}
	/*
	 * Getter Method for Response - Used by View
	 */
	public int[] getResponse(int row){
		return responses[row];
	}
	/*
	 * Getter Method for Response - Used by View
	 */
	public int[] getCurrentCode(){
		//To be consistent in state of guess
		intialize(currentGuess);
		return currentCode;
	}
	/*
	 * Getter Method for counter - Used by Controller?
	 */
	public int getCounter(){
		return counter;
	}
	/*
	 * Getter Method for CurrentGuess (so far) - Used by View
	 */
	public int[] getCurrentGuess(){
		return currentGuess;
	}
	/*
	 * Getter Method for LastGuess  - Used by View //USEFUL?? Maybe 
	 */
	public int[] getGuess(){
		return guesses[counter--]; //TODO ensure works
	}
	/*
	 * Setter Method for Cheated - Used by View
	 */
	public void setCheated(boolean b) {
		cheated = b;
	}
	/*
	 * Getter Method for Cheated - Used by View
	 */
	public boolean getCheated() {
		return cheated;
	}
}
