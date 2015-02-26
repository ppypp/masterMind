/**
 * Authors
 * Michael Gerten, Angel Zuaznabal, Cody Sehl 
 */

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 *The view class displays the game state
 */
@SuppressWarnings("serial")
public class View extends Canvas {
	// The color arrays
	// 0 : W , 1 : R , 2 : G , 3 : B , 4 : C , 5 : M , 6 : Y
	// WHITE is for null/empty
	private Color colors[] = { Color.WHITE, Color.red, Color.green, Color.blue,
			Color.cyan, Color.magenta, Color.yellow,
			Color.pink, Color.orange, Color.darkGray,   };//Extra Colors for fun
	// The response array - light gray is default, as it signifies a completely
	// wrong response
	// 0 : LG , 1 : B , 2 : W
	private Color responseColors[] = { Color.lightGray, Color.black,
			Color.white, };
	// Num of rows
	private int row;
	// Num of Colors
	private int color;
	// Num of Cols
	private int col;
	// Size of Window array
	private int[] size;
	// Frame in which game occurs
	private JFrame window;
	// reusable variable
	private int num;
	// window.setSize(new Dimension((color * 2 + 2) * 20 + 0, REASONING BEHIND
	// CHOICES
	// (row * 2 + 12) * 20 + 0)); // TEE HE //14, 36 // + Arbitrary
	private int widthDiv = 14;
	private int heightDiv = 36; // was 32 , added for buttons
	// Panel for top buttons
	private JPanel buttonPanel;
	//Buttons
	private JButton submit;
	private JButton newGame;
	private JButton r; //reveal (conflict of variable name)
	// Local instance of model
	private Model model;
	// Local instance of Controller
	private Controller controller;
	// Boolean for weather to reveal currentCode
	private boolean reveal;

	/*
	 * Constructor of the View
	 */
	public View(int ROWS, int COLORS, int COLUMNS, Controller c, Model m) {
		// Set local values
		col = COLUMNS;
		row = ROWS;
		color = COLORS;
		model = m;
		controller = c;
		reveal = false;
		//Safety Check
		if ((color > colors.length) || ((col + col/2) > colors.length)) {
			// Quit if don't have enough colors stored
			// Or if col would cause error
			System.exit(0);
		}

		// Make Window
		window = new JFrame("Mastermind");
		// Exit if we close the window
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBackground(colors[0]);
		this.setBackground(colors[0]);
		size = getWindowSize();
		/* just one button to submit each guess */
		submit = new JButton("Submit guess");
		newGame = new JButton("New game");
		r = new JButton("Reveal");

		submit.addActionListener(controller);
		newGame.addActionListener(controller);
		r.addActionListener(controller);
		/* we need to know mouse coords to place colored pegs */
		addMouseListener(controller);
		// Top buttons and placement
		buttonPanel = new JPanel();
		buttonPanel.setBackground(colors[0]);
		buttonPanel.add(r, BorderLayout.WEST);
		buttonPanel.add(newGame, BorderLayout.EAST);

		window.setSize(new Dimension((widthDiv) * 20 + 0, (heightDiv) * 20 + 0));
		// + Arbitrary
		window.setVisible(true);

		window.getContentPane().add(this);
		window.getContentPane().add(buttonPanel, BorderLayout.NORTH);
		window.getContentPane().add(submit, BorderLayout.SOUTH);

		repaint();
	}

	/*
	 * Make the game grid and fill in all known information
	 */
	public void updateBoard(Graphics board) {
		// Clear board
		clear(board);
		// Set Line Color
		board.setColor(Color.BLACK);
		// Get current size
		size = getWindowSize();
		// Use num in order to simplify code, looks cleaner
		// Guess Panel Construction
		// Draw the rows
		for (int i = 0; i < (row + 1); i++) { // Bottom line => +1
			// drawLine [ ( x, y ) (x,y) ] => draws line between points
			num = (size[5] * i) + 0;
			board.drawLine(2 * size[2], num, 12 * size[2], num);
		}
		// Draw the columns
		for (int i = 0; i < (col + col / 2); i++) { // effectively 6
			num = size[4] * (i) + size[4];
			board.drawLine(num, 0, num, (row) * size[5] + 0);
			// +1 for how it works (below/to right if coordinate point)
		}
		// Response Area
		// Draw the Rows
		for (int i = 0; i < (row); i++) { // No +1 because mid-lines
			num = (size[5] * i) + size[3];
			board.drawLine(10 * size[2], num, 12 * size[2], num);
		}
		// Draw the column
		num = size[4] + 9 * size[2];
		board.drawLine(num, 0, num, (row) * size[5] + 0);
		// Color Palate Zones
		// Draw the Rows
		for (int i = 0; i < (2); i++) {
			num = size[3] * (row * 2 + 1) + size[5] * i;
			board.drawLine(size[2], num, size[2] * 13, num);
		}
		// Draw the Columns
		for (int i = 0; i < (color + 1); i++) {
			num = i * size[4] + size[2];
			board.drawLine(num, size[3] * (row * 2 + 1), num, size[3] * (row * 2 + 3));
		}
		// Guess Area Construction
		// draw the Rows
		for (int i = 0; i < 2; i++) { // Bottom line => +1
			// drawLine [ ( x, y ) (x,y) ] => draws line between points
			num = (size[5]) * (i) + size[3] * (row * 2 + 4);
			board.drawLine(3 * size[2], num, 11 * size[2], num);
		}
		// draw the Columns
		for (int i = 0; i < (col + 1); i++) {
			num = size[4] * (i) + size[4] + size[2];
			board.drawLine(num, size[3] * (row * 2 + 4), num, size[3]* (row * 2 + 6));
					
			// +1 for how it works (below/to right if coordinate point)
		}
		// draw the color palate
		for (int i = 0; i < (col + col / 2); i++) {
			// +1 becuase 0 is white
			board.setColor(colors[i + 1]);
			board.fillRect(i * size[4] + size[2] + 1, size[3] * (row * 2 + 1)+ 1, size[4] - 1, size[5] - 1);
		}
		// NOW add in all past guesses and responses
		for (int i = 0; i < (row); i++) {
			updateBoard(board, i);
		}
		// Guess/Reveal Area
		updateBoard(board, reveal);
	}

	/*
	 * Updates a specified row with its current state and gotten from model
	 * 
	 * @param currRow is which row to update
	 */
	private void updateBoard(Graphics board, int currRow) {
		// Get Window dimensions
		size = getWindowSize();
		// get Guess from model
		int[] guess = model.getGuess(currRow);
		// place squares
		for (int i = 0; i < (col); i++) {
			board.setColor(colors[guess[i]]); // current guess colors
			// Locations
			board.fillRect(i * size[4] + size[4] + 1, size[5] * (currRow) + 1,
					size[4] - 1, size[5] - 1);
		}
		// Response fill in
		for (int i = 0; i < (col / 2); i++) {
			int[] response = model.getResponse(currRow);
			// -1 to keep lines
			// Top Boxes
			board.setColor(responseColors[response[i]]);
			board.fillRect(i * size[2] + 5 * size[4] + 1, size[5] * (currRow)
					+ 1, size[2] - 1, size[3] - 1);

			// Bottom Boxes
			board.setColor(responseColors[response[i + (col / 2)]]);
			board.fillRect(i * size[2] + 5 * size[4] + 1, size[5] * (currRow)
					+ size[3] + 1, size[2] - 1, size[3] - 1);

		}
	}

	/*
	 * For updating the bottom gird area
	 */
	private void updateBoard(Graphics board, boolean reveal) {
		// Shows that they looked/cheated

		size = getWindowSize();
		if (reveal == true) {
			model.setCheated(true);
			showCurrentCode(board);
		} else { // False
			showGuessesSoFar(board);
		}
		if (model.getCheated() == true) {
			// Easy way to signify cheating
			this.setBackground(Color.PINK);
		} else {
			this.setBackground(colors[2]);
		}

	}

	/*
	 * Effectively a setter: Allows the Controller to toggle the reveal of
	 * CurrentCode
	 */
	public void setRevealAnswer() {
		if (reveal == false) {
			reveal = true;
			submit.setEnabled(false);
		} else {
			reveal = false;
			submit.setEnabled(true);
		}

	}

	/*
	 * Overload : to allow a clean reset when starting a new game
	 */
	public void setRevealAnswer(boolean b) {
		reveal = b;
	}

	/*
	 * For showing selected colors
	 */
	private void showGuessesSoFar(Graphics board) {
		// TODO only problem is that after reveal, boxes stay pink even though
		// should be white
		int[] guess = model.getCurrentGuess();
		// loop through guess
		for (int i = 0; i < (col); i++) {
			board.setColor(colors[guess[i]]); // current guess colors
			board.fillRect(size[3] * 3 + i * size[5] + 1, size[4] * (row + 2)
					+ 1, size[4] - 1, size[5] - 1);
		}
	}

	/*
	 * For showing currentCode
	 */
	private void showCurrentCode(Graphics board) {
		int[] currentCode = model.getCurrentCode();
		// draw the current Code
		for (int i = 0; i < (col); i++) {
			board.setColor(colors[currentCode[i]]);
			board.fillRect(size[3] * 3 + i * size[5] + 1, size[4] * (row + 2)
					+ 1, size[4] - 1, size[5] - 1);

		}
	}

	/*
	 * Get values for dimensioning the game field
	 */
	public int[] getWindowSize() {
		// Array to return
		int[] answer = new int[10];
		// Grab Window Size
		int width = (int) window.getSize().width;
		int height = (int) window.getSize().height;
		// Columns Full Length
		answer[0] = width;
		// Row Full Length
		answer[1] = height;
		// Columns Divide Num
		answer[2] = (width / (widthDiv)); // G
		// Row Divide Num
		answer[3] = (height / (heightDiv)); // G
		// Columns Divide Num
		answer[4] = (width / (widthDiv / 2)); // B
		// Row Divide Num
		answer[5] = (height / (heightDiv / 2)); // B
		// return the values for use in dimensioning
		return answer;
	}

	/*
	 * IF needed to use chars as per requirements
	 */
	public boolean CharToColor(char c) { // Returns boolean for error testing
		colors[0] = null; // ?

		return false;
	}

	/*
	 * Pointless or used for making clear- TODO to be determined
	 */
	private void clear(Graphics board) {
		// If everything is set back to 0, it will be white everywhere-> making
		// this unnecessary
	}

	/*
	 * the paint method we need remember we're a ContentPane here Don't call
	 * paint, call repaint instead : ie. MAGIC
	 */
	public void paint(Graphics g) {
		updateBoard(g);
	}
}