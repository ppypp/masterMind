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
		// get current window size
		size = view.getWindowSize();
		// Decide what action to perform based on mouse click location
		if(mouseInColorPaletteArea(x, y)) {
			// Get color clicked
			
			// Subtract gap at left of color palette area
			x = x - size[2];
			// size[4] == size of large square
			selectedColor = x / size[4] + 1;
		}
		
		if(mouseInGuessArea(x, y)) {
			// Subtract gap at left of guess area
			x = x - size[2]*3;
			int guessIndex = x / size[4];
			
			model.setGuess(guessIndex, selectedColor);
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
				// X1: size[2] + (size[4]*0) +1
				// Y1: size[3] * (row * 2 + 1) + (size[5] * 0) +1 
				// X2: size[2] + (size[4]*1) -1
				// Y2: size[3] * (row * 2 + 1) + (size[5] * 1) -1
		// Zone for Green
				// X1: size[2] + (size[4]*0) +1
				// Y1: size[3] * (row * 2 + 1) + (size[5] * 0) +1 
				// X2: size[2] + (size[4]*1) -1
				// Y2: size[3] * (row * 2 + 1) + (size[5] * 1) -1
		// Zone for Blue
				// X1: size[2] + (size[4]*1) +1
				// Y1: size[3] * (row * 2 + 1) + (size[5] * 0) +1 
				// X2: size[2] + (size[4]*2) -1
				// Y2: size[3] * (row * 2 + 1) + (size[5] * 1) -1
		// Zone for Cyan
				// X1: size[2] + (size[4]*2) +1
				// Y1: size[3] * (row * 2 + 1) + (size[5] * 0) +1 
				// X2: size[2] + (size[4]*3) -1
				// Y2: size[3] * (row * 2 + 1) + (size[5] * 1) -1
		// Zone for Mygenta
				// X1: size[2] + (size[4]*3) +1
				// Y1: size[3] * (row * 2 + 1) + (size[5] * 0) +1 
				// X2: size[2] + (size[4]*4) -1
				// Y2: size[3] * (row * 2 + 1) + (size[5] * 1) -1
		// Zone for Yellow
				// X1: size[2] + (size[4]*4) +1
				// Y1: size[3] * (row * 2 + 1) + (size[5] * 0) +1 
				// X2: size[2] + (size[4]*5) -1
				// Y2: size[3] * (row * 2 + 1) + (size[5] * 1) -1
		
		// Zone for G1
				// X1: size[2] + (size[4]*1) +1
				// Y1: (size[5]) * (0) + size[3] * (row * 2 + 4)+1
				// X1: size[2] + (size[4]*2) -1
				// Y2: (size[5]) * (1) + size[3] * (row * 2 + 4)-1
		// Zone for G2
				// X1: size[2] + (size[4]*2) +1
				// Y1: (size[5]) * (0) + size[3] * (row * 2 + 4)+1
				// X1: size[2] + (size[4]*3) -1
				// Y2: (size[5]) * (1) + size[3] * (row * 2 + 4)-1
		// Zone for G3
				// X1: size[2] + (size[4]*3) +1
				// Y1: (size[5]) * (0) + size[3] * (row * 2 + 4)+1
				// X1: size[2] + (size[4]*4) -1
				// Y2: (size[5]) * (1) + size[3] * (row * 2 + 4)-1
		// Zone for G4
				// X1: size[2] + (size[4]*4) +1
				// Y1: (size[5]) * (0) + size[3] * (row * 2 + 4)+1
				// X1: size[2] + (size[4]*5) -1
				// Y2: (size[5]) * (1) + size[3] * (row * 2 + 4)-1

		
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
