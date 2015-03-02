# Design Document

The Master Mind game as implemented by Michael, Angel, and Cody is based off of example code given by John Dooley on the CS292 moodle page. We took this code, moved some things around and eventually added to it to create a GUI based verson of the Master Mind game. 

## Overall Architecture
The program follows the MVC architecture where the game logic is contained in `Model.java`, the event listers are contained in `Controller.java`, and the rendering portion is contained in `View.java`. Although in our implementation of the Master Mind game there can only be one of each M V and C, we chose to create an instance of these classes and simply pass them around rather than allowing them to be static. We found that intrudicing static variables into our programs generally made them more complicated and harder to work with, especially considering the non-static nature of rendering with Java.swing.

## Controller
The Controller handles all user interaction for the Master Mind Game. Rather than having to separate the Controller/Model relationship like we initially tried with Poker Squares (which introduced a lot of confusing async problems and ultimately didn't work), we opted to base the game 'ticks' on user input, i.e., the game advances it's state only when a user action occurs.

## Model
The Model handles all state computation for the Master Mind Game. It is entered through the `startGame()` method which is called when the Controller receives a click event on the 'Start New Game' button. The Model is called from both Controller(as stated above, to start a new game) as well as View (to ask if the game has ended).

## View
The View handles all rendering for the Master Mind Game. Rendering is performed chiefly via the Java.swing API and the Java.awt.Canvas API. Colors in the view are stored in an array. We chose to store the Master Mind game colors in an array rather than as a series of final static variables so that they would easily interface with a similar system set up for user guesses. Similarly for calculating where to draw the proper rectangles on the screen (which was a slog, by the way), an int[] was used to store standard grid sizes used to lay out elements in the window based on the window's size.
