/**
 * Authors
 * Michael Gerten, Angel Zuaznabal, Cody Sehl 
 */

public class Start {
	// Number of Rows for Guesses/Responses
	private static final int ROWS = 12;
	private static final int COLORS = 6;
	private static final int COLUMNS = 4;
	/**
	 * This method runs MasterMind
	 * @param args 
	 */
	@SuppressWarnings("unused")
	public static void main (String[] args){
		Controller c = new Controller(ROWS, COLORS, COLUMNS);
	}
}