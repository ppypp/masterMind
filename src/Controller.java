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
	private int color;
	// Instance of View
	private View view;
	// Instance of Model
	private Model model;
	// For mouse click dimension calculations
	private int[] size; 
	
	
	//RANDOM notes
	//Once 12 guesses / correct answer -> end condition

	//SUBMIT BUTTON WILL DISAPPEAR WHEN STARTING PROGRAM IS SOME INSTANCES ?????? NO IDEA WHY
	// 
	//NO STATICS SUCKerS!!!!!!!!!!!!!!!!!!!
	//ALL THE INFO WILL BE CHARS UNTIL VIEW
	//top, split up
			//Reveal Button
			//New Game Button
	
			
			//on bottom
			//Submit Guess Button
	//6 spots for choosing color- (mouse event)
			//four spots to place a color
			//can only select a color if it is not already in the guess array
			//can override other colors in guess array. ie, put RED, than BLUE on spot 1 . 
			//Guess is finalized by button press (or mouse event )
			//Reveal/Rehide Button/Mouse click - should be able to show answer and also hide it
			//If reveal answer- signify game has been comprised (cheating has happened)
			//New Game - new hidden code
			//restart Game - same code, new guesses (Necessary??) Probably not
			
		//ONCE AGAIN, the entire display updates every action-> 
	//Preferable no because most elements don't change that frequently.
	//Better way?? Seems to be working this way- not changing yet
	
	
	//ERROR CHECKING IS A REQUIREMENT
	//!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?
	
	
	/*
	 * TODO
	 * @param ROWS are the number of guesses allowed 
	 * COLORS is the number of colors
	 * MODEL is the model, VIEW is the view
	 * 
	 */
	public Controller(int ROWS, int COLORS, int COLUMNS){
		row = ROWS;
		color = COLORS;
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
			//TESTING BELOW
			model.setGuess(0, 1);
			model.setGuess(1, 2);
			model.setGuess(2, 3);
			model.setGuess(3, 4);
			//TESTING ^
			model.checkGuess();
			//Now have response and guess calculated and stored
		} else if (e.getActionCommand().equals("New game")){
			//Start new game
			model.startGame();
			//reset reveal answer boolean
			view.setRevealAnswer(false);
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
		location(x,y);
		view.repaint();
	}
	private void location(int x, int y) {
		// TODO Auto-generated method stub
		// IF in color palete : colorSelected is that color
		// IF in guess zone : guess is the colorSelected
		// IF everywhere else : colorSelected is WHITE
		
		//THINGS TO NOTE
		//Can't select a color twice: ie, once selected either can't select it again, or it gets rid of past position
		//I think its easier to simply prevent new placement- ie, have to put white/ other color first
		
		//OR we can just ignore it and let really stupid guesses occur
		
		//BELOW ARE THE ZONE THAT tHE LINES MAKE 
		//the zones should be +1 from these and -1 ie-> look at rectangle filling in VEIW
		//Makes it so lines are still present, and the click zones can't overlap
		
		// Zone for Red
		// X1: size[3] * (row * 2 + 1) +1 
		// Y1: size[2] +1
		// X2: size[3] * (row * 2 + 1) + size[5] -1
		// Y2: size[4] + size[2] -1
		
//		//Color Palate Zones
//				// ROW
//				for (int i = 0; i < (2); i++) {
//					num = size[3] * (row * 2 + 1) + size[5] * i;
//					board.drawLine(size[2], num, size[2] * 13, num);
//				}
//				// COL
//				for (int i = 0; i < (color + 1); i++) {
//					num = i * size[4] + size[2];
//					board.drawLine(num, size[3] * (row * 2 + 1), num, size[3]
//							* (row * 2 + 3));
//
//				}
//		// Guess Area Construction
//				// draw the Rows
//				for (int i = 0; i < 2; i++) { // Bottom line => +1
//					// drawLine [ ( x, y ) (x,y) ] => draws line between points
//					num = (size[5]) * (i) + size[3] * (row * 2 + 4);
//					board.drawLine(3 * size[2], num, 11 * size[2], num);
//				}
//				// draw the Columns
//				for (int i = 0; i < (col + 1); i++) {
//					num = size[4] * (i) + size[4] + size[2];
//					board.drawLine(num, size[3] * (row * 2 + 4), num, size[3]
//							* (row * 2 + 6));
//					// +1 for how it works (below/to right if coordinate point)
//				}
	}

	/*
	 * TODO
	 */
	public void mouseEntered(  MouseEvent e ) { }
	/*
	 * TODO
	 */
	public void mouseExited(   MouseEvent e ) { }
	/*
	 * TODO
	 */
	public void mouseClicked(  MouseEvent e ) { }
	/*
	 * TODO
	 */
	public void mouseReleased( MouseEvent e ) { }
	// If wanted them to drag a color to the selection 
	// Other option is pick a color, pick a spot , repeat.
	/*
	 * TODO
	 */
	
	
	
}
