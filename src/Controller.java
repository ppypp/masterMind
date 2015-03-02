/**
 * Authors
 * Michael Gerten, Angel Zuaznabal, Cody Sehl 
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
/*
 * Controller Class : Gets user input and calls model/view to act upon it
 */
public class Controller implements ActionListener, MouseListener{
	// Color that was pressed
	// 0 : W , 1 : R , 2 : G , 3 : B , 4 : C , 5 : M , 6 : Y
	// For reference while making mouse listener ^^^^
	//White is everywhere except in the colorPalete : Safety measure
	private int selectedColor; 
	// Num of Rows
	private int row; 
	// Num of Cols
	private int col;
	// Num of Colors
	private int numColors;
	// Instance of View
	private View view;
	// Instance of Model
	private Model model;
	// For mouse click dimension calculations
	private int[] size; 


	/*
	 * @param ROWS are the number of guesses allowed 
	 * COLORS is the number of colors
	 */
	public Controller(int ROWS, int COLORS, int COLUMNS){
		row = ROWS;
		numColors = COLORS;
		col = COLUMNS;
		model = new Model(ROWS, COLORS, COLUMNS);
		view = new View(ROWS, COLORS, COLUMNS, this, model);
		selectedColor = 0; //Default of white
	}

	/*
	 * Listen for button Presses
	 */
	public void actionPerformed( ActionEvent e ) {

		if (e.getActionCommand().equals("Submit guess")) {
			int[] guess = model.getCurrentGuess();
			if (guess[0] == 0 || guess[1] == 0 || guess[2] == 0 || guess[3] == 0)	{
				//Do nothing
			}else{
				model.checkGuess();
				//Now have response and guess calculated and stored
			}
		} else if (e.getActionCommand().equals("New game")){
			//Start new game
			model.startGame();
			//reset reveal answer boolean
			view.setRevealAnswer(false);
			// Enable submit guess button
			view.setEnableNewGameButton(true);
		} else if (e.getActionCommand().equals("Reveal")){
			//toggle reveal answer boolean
			view.setRevealAnswer();
			//When Revealed :> can't submit
		} else {
			//BREAK : non valid button
			return;
		}
		view.repaint(); //refresh the screen
	}	

	/*
	 * TODO
	 */
	public void mousePressed( MouseEvent e ) {
		/* get the x & y coordinates of the mouse position */
		int x = e.getX();
		int y = e.getY();
		// get current window size
		size = view.getWindowSize();
		// Decide what action to perform based on mouse click location
		if(mouseInColorPaletteArea(x, y)) {
			// Subtract gap at left of color palette area
			x = x - size[2];
			// size[4] == size of large square
			selectedColor = x / size[4] + 1;
		}

		if(mouseInGuessArea(x, y)) {
			// Subtract gap at left of guess area
			x = x - size[2]*3;
			int guessIndex = x / size[4];
			if (guessIndex < 4){
				model.setGuess(guessIndex, selectedColor);
			}
		}
		view.repaint();
	}

	private boolean mouseInColorPaletteArea(int x, int y) {
		int colorPaletteStart = size[3] * (row * 2 + 1) + (size[5] * 0) + 1;
		int colorPaletteEnd = size[3] * (row * 2 + 1) + (size[5] * 1) + 1;

		return y > colorPaletteStart && y < colorPaletteEnd;
	}

	private boolean mouseInGuessArea(int x, int y) {
		int guessAreaStart = (size[5]) * (0) + size[3] * (row * 2 + 4)+1;
		int guessAreaEnd = (size[5]) * (1) + size[3] * (row * 2 + 4)-1;

		return y > guessAreaStart && y < guessAreaEnd;
	}

	//	Unused methods required to implement MouseListener
	public void mouseEntered(  MouseEvent e ) { }
	public void mouseExited(   MouseEvent e ) { }
	public void mouseClicked(  MouseEvent e ) { }
	public void mouseReleased( MouseEvent e ) { }
}
