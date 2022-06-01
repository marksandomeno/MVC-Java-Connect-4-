/** Mark Sandomeno
 *  4/28/22
 *  SetupController.java
 **/

package cpsc2150.extendedConnectX.controllers;

import cpsc2150.extendedConnectX.views.*;
import cpsc2150.extendedConnectX.models.*;

public class SetupController {
    private SetupView view;
    private int max_size = 20;
    private int min_size = 3;
    private int min_to_win = 3;
    private final int BOARD_CUTOFF = 100;

    public SetupController(SetupView v)
    {
        view = v;
    }

    public void processButtonClick(int rows, int cols, int players, int numWin )
    {
        String errorMsg = "";
        if(rows < min_size || rows > max_size)
        {
            errorMsg += "Rows must be between "+ min_size + " and " + max_size;
        }

        if(cols < min_size || cols > max_size)
        {
            errorMsg += "Columns must be between "+ min_size + " and " + max_size;
        }

        if (numWin > rows)
        {
            errorMsg += "Can't have more to win than the number of rows";
        }
        if (numWin > cols)
        {
            errorMsg += "Can't have more to win than the number of Columns";
        }

        if(numWin < min_to_win)
        {
            errorMsg += "Number to win must be at least " + min_to_win;
        }

        if(!errorMsg.equals(""))
        {
            view.displayError(errorMsg);

        }
        else
        {
            view.closeScreen();
            IGameBoard model;
            //if the board is too big we'll want the memory efficient version
            if(rows*cols > BOARD_CUTOFF)
            {
                model = new GameBoardMem(rows, cols, numWin);
            }
            else {
                model = new GameBoard(rows, cols, numWin);
            }
            ConnectXView tview = new ConnectXView(rows, cols);
            ConnectXController tcontroller = new ConnectXController(model, tview, players);

            tview.registerObserver(tcontroller);
        }
    }
}

