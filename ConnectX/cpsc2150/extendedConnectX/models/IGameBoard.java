/** Mark Sandomeno
 *  4/28/22
 *  IGameBoard
 **/

package cpsc2150.extendedConnectX.models;

public interface IGameBoard {

    public static final int MAXROWS = 100;
    public static final int MAXCOLS = 100;
    public static final int MAXNUMTOWIN = 25;
    public static final int MINROWS = 3;
    public static final int MINCOLS = 3;
    public static final int MINNUMTOWIN = 3;

    public void setRows(int rowsInput);

    public void setCols(int colsInput);

    public void setNumToWin(int numToWinInput);

    /**
     * @return  the maximum number of rows.
     */
    public int getMaxRows();

    /**
     * @return  the maximum number of columns.
     */
    public int getMaxCols();

    /**
     * @return  the maximum number of consecutive tokens required to win.
     */
    public int getMaxNumToWin();

    /**
     * @return  the minimum number of rows.
     */
    public int getMinRows();

    /**
     * @return  the minimum number of columns.
     */
    public int getMinCols();

    /**
     * @return  the minimum number of consecutive tokens required to win.
     */
    public int getMinNumToWin();

    /**
     * @return  an integer representing the number of rows in the game board.
     * @post    numRows <= MAXROWS && MINROWS <= numRows
     */
    public int getNumRows();

    /**
     * @return  an integer representing the number of columns in the game board.
     * @post    numCols <= MAXCOLS && numCols >= MINCOLS
     */
    public int getNumColumns();

    /**
     * @return  an integer representing the number of columns in the game board.
     * @post    numToWin <= MAXNUMTOWIN && numToWin <= MINNUMTOWIN
     */
    public int getNumToWin();

    /**
     * @pre     A player wants to place a token.
     * @param c The column the player wants to place a token in.
     * @return  true if the column has one or more empty spaces.
     *          false if the column is full of tokens.
     */
    public default Boolean checkIfFree(int c){
        //make a board position on the top row (getnumRows()) and the specified column.
        BoardPosition topPos = new BoardPosition(getNumRows()-1, c);
        //if the top space is blank, then the column isn't empty.
        if(whatsAtPos(topPos) == ' ') return true;
        else return false;
    }

    /**
     * @pre     The player has already placed their token for the turn.
     * @param c The column the player placed their token in.
     * @return  true if the player has won horizontally, diagonally, or vertically
     *          false if the player has not won.
     */
    public default Boolean checkForWin(int c){
        //get row number of latest position
        int rowNum = getNumRows()-1;
        BoardPosition lastPos = new BoardPosition(getNumRows()-1, c);
        while(whatsAtPos(lastPos) == ' '){
            rowNum--;
            lastPos = new BoardPosition(rowNum, c);
        }
        //now lastPos is at the position of the last token placed

        //get character
        char token = whatsAtPos(lastPos);

        //check horizontal, vertical, and diagonal wins.
        if(checkHorizWin(lastPos, token)){return true;}
        if(checkVertWin(lastPos, token)){return true;}
        if(checkDiagWin(lastPos, token)){return true;}
        else {return false;}
    }

    /**
     * @pre     The player has selected the column they want to place their token in.
     * @param p The token to be placed
     * @param c The column that the player chose
     * @post    Token p was placed in column c
     */
    public void placeToken(char p, int c);

    /**
     * @pre     The player has placed their token for the turn.
     * @param pos The board position that the token went into.
     * @param p   The token that was placed.
     * @return  true if the player won horizontally
     *          false if there is no winner.
     */
    public default Boolean checkHorizWin(BoardPosition pos, char p){
        //initialize variables
        boolean traverseRight = false;
        int count = 1;
        BoardPosition currPos = pos;

        //while we haven't traversed right
        while(!traverseRight){

            //see if we can move to the right. If so, move right.
            if(currPos.getColumn()+1 < getNumRows()){
                currPos = new BoardPosition(currPos.getRow(), (currPos.getColumn()+1));

                //if the token is the same as p, increase the consecutive count. If not, move back to the original spot.
                if(whatsAtPos(currPos) == p){
                    count ++;
                    if(count == getNumToWin()){
                        return true;
                    }
                } else traverseRight = true;
            } else traverseRight = true;
        }

        //reset the position.
        //repeat this process for moving left
        currPos = pos;
        while(traverseRight){
            if(currPos.getColumn()-1 >= 0){
                currPos = new BoardPosition(currPos.getRow(), (currPos.getColumn()-1));
                if(whatsAtPos(currPos) == p){
                    count ++;

                    //if we reached the number to win, return true
                    if(count == getNumToWin()){
                        return true;
                    }
                } else break;
            } else break;
        }

        //otherwise return false
        return false;
    }

    /**
     * @pre     The player has placed their token for the turn.
     * @param pos The board position that the token went into.
     * @param p   The token that was placed.
     * @return  true if the player won vertically
     *          false if there is no winner.
     */
    public default Boolean checkVertWin(BoardPosition pos, char p){
        //repeat the process for checkHorizWin for checkVertWin, except we only move down.
        int count = 1;
        BoardPosition currPos = pos;

        while(count < getNumToWin()){
            if(currPos.getRow()-1 >= 0){
                currPos = new BoardPosition((currPos.getRow()-1), currPos.getColumn());
                if(whatsAtPos(currPos) == p){
                    count++;
                    if(count == getNumToWin()){
                        return true;
                    }
                } else break;
            } else break;
        }

        return false;
    }

    /**
     * @pre     The player has placed their token for the turn.
     * @param pos The board position that the token went into.
     * @param p   The token that was placed.
     * @return  true if the player won diagonally
     *          false if there is no winner.
     */
    public default Boolean checkDiagWin(BoardPosition pos, char p){
        //initialize
        int count = 1;
        BoardPosition currPos = pos;

        //Repeat the process in checkHorizWin for checkDiagWin, except for moving Southeast/Northwest, and southwest/northeast.
        //move southeast
        while(count < getNumToWin()){
            if(currPos.getRow()-1 >= 0 && currPos.getColumn()+1 < getNumColumns()){
                currPos = new BoardPosition((currPos.getRow()-1), (currPos.getColumn()+1));
                if(whatsAtPos(currPos) == p){
                    count++;
                    if(count == getNumToWin()){
                        return true;
                    }
                } else break;
            } else break;
        }

        //reset the position
        currPos = pos;

        //move northwest
        while(count < getNumToWin()){
            if(currPos.getRow()+1 < getNumRows() && currPos.getColumn()-1 >= 0){
                currPos = new BoardPosition((currPos.getRow()+1), (currPos.getColumn()-1));
                if(whatsAtPos(currPos) == p){
                    count++;
                    if(count == getNumToWin()){
                        return true;
                    }
                } else break;
            } else break;
        }

        //reset the position and consecutive token counter
        currPos = pos;
        count = 1;

        //move southwest
        while(count < getNumToWin()){
            if(currPos.getRow()-1 >= 0 && currPos.getColumn()-1 >= 0){
                currPos = new BoardPosition((currPos.getRow()-1), (currPos.getColumn()-1));
                if(whatsAtPos(currPos) == p){
                    count++;
                    if(count == getNumToWin()){
                        return true;
                    }
                } else break;
            } else break;
        }

        currPos = pos;

        //move northeast
        while(count < getNumToWin()){
            if(currPos.getRow()+1 < getNumRows() && currPos.getColumn()+1 < getNumColumns()){
                currPos = new BoardPosition((currPos.getRow()+1), (currPos.getColumn()+1));
                if(whatsAtPos(currPos) == p){
                    count++;
                    if(count == getNumToWin()){
                        return true;
                    }
                } else return false;
            } else return false;
        }

        //no winner
        return false;
    }

    /**
     * @pre     The board position to be checked exists.
     * @param pos The gameboard position to be checked.
     * @return  The character at position pos on the board.
     */
    public char whatsAtPos(BoardPosition pos);

    /**
     * @pre     p != ' '
     * @param pos    The position to be checked.
     * @param player The player to be checked.
     * @return  true if the player has a piece at the specified position
     *          false if the player doesn't have a piece at the specified position
     */
    public default boolean isPlayerAtPos(BoardPosition pos, char player){
        //check to see if the player at pos matches the player passed in.
        return whatsAtPos(pos) == player;
    }

    /**
     * @pre     No winner has been declared yet.
     * @post    If there's a tie, the game is over.
     * @return  true if the game board is full of tokens with no winner
     *          false if there is an empty space in the game board.
     */
    public boolean checkTie();

}