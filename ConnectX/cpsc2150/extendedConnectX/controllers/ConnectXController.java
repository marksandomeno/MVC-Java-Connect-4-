/** Mark Sandomeno
 *  4/28/22
 *  ConnectXController.java
 **/


package cpsc2150.extendedConnectX.controllers;

import cpsc2150.extendedConnectX.models.*;
import cpsc2150.extendedConnectX.views.*;

/**
 * The controller class will handle communication between our View and our Model ({@link IGameBoard})
 * <p>
 * This is where you will write code
 * <p>
 * You will need to include your {@link IGameBoard} interface
 * and both of the {@link IGameBoard} implementations from Project 4
 * If your code was correct you will not need to make any changes to your {@link IGameBoard} implementation class
 *
 * @version 2.0
 */
public class ConnectXController {

    /**
     * <p>
     * The current game that is being played
     * </p>
     */
    private IGameBoard curGame;

    /**
     * <p>
     * The screen that provides our view
     * </p>
     */
    private ConnectXView screen;

    /**
     * <p>
     * Constant for the maximum number of players.
     * </p>
     */
    public static final int MAX_PLAYERS = 10;

    /**
     * <p>
     * The number of players for this game. Note that our player tokens are hard coded.
     * </p>
     */
    private int numPlayers;
    char[] players;
    int turn = 0;
    private boolean reset = false;

    /**
     * <p>
     * This creates a controller for running the Extended ConnectX game
     * </p>
     *
     * @param model
     *      The board implementation
     * @param view
     *      The screen that is shown
     *
     * @post [ the controller will respond to actions on the view using the model. ]
     */
    public ConnectXController(IGameBoard model, ConnectXView view, int np) {
        this.curGame = model;
        this.screen = view;
        numPlayers = np;
        players = new char[]{'X', 'O', 'A', 'B', 'Y', 'F', 'C', 'M', 'V', '$'};
    }

    /**
     * <p>
     * This processes a button click from the view.
     * </p>
     *
     * @param col
     *      The column of the activated button
     *
     * @post [ will allow the player to place a token in the column if it is not full, otherwise it will display an error
     * and allow them to pick again. Will check for a win as well. If a player wins it will allow for them to play another
     * game hitting any button ]
     */
    public void processButtonClick(int col){
        if (reset){
            reset = false;
            turn = 0;
            this.newGame();
            return;
        }

        if (turn >= numPlayers){
            turn = 0;
        }

        if (!this.curGame.checkIfFree(col)) {
            this.screen.setMessage("Space Unavailable, please pick again");

        }
        else {
            char token = players[turn];
            int row= 0;

            for (int i = 0; i < this.curGame.getNumRows(); i++) {
                BoardPosition temp = new BoardPosition(i, col);
                if (this.curGame.whatsAtPos(temp) != ' '){
                    row++;
                }
            }
            this.curGame.placeToken(token, col);
            this.screen.setMarker(row, col, token);

            if (this.curGame.checkForWin(col)){
                this.screen.setMessage("Player " + players[turn] + " Won!\nClick any button to start a new game.");
                reset = true;
            }
            else if (this.curGame.checkTie()){
                this.screen.setMessage("Tie game!\nClick any button to start a new game.");
                reset = true;
            }
            else{
                turn++;
                if (turn >= numPlayers){
                    turn = 0;
                }
                screen.setMessage("It is " + players[turn] + "'s turn. ");

            }

        }
    }


    /**
     * <p>
     * This method will start a new game by returning to the setup screen and controller
     * </p>
     *
     * @post [ a new game gets started ]
     */
    private void newGame() {
        //close the current screen
        screen.dispose();

        //start back at the set up menu
        SetupView screen = new SetupView();
        SetupController controller = new SetupController(screen);
        screen.registerObserver(controller);
    }
}