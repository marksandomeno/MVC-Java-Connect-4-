/** Mark Sandomeno
 *  4/28/22
 *  GameBoard.java
 **/

package cpsc2150.extendedConnectX.models;
import javax.print.attribute.standard.NumberUp;
import java.util.*;

public class GameBoard extends AbsGameBoard {

    /**
     * @invariant   No tokens can be placed outside of the game board.
     * @invariant   No tokens can be placed in a column that is already full.
     */

    private int rows;
    private int cols;
    private int numToWin;
    private char[][] board;

    /**
     * @param _rows     the number of rows in the board
     * @param _cols     the number of columns in the board
     * @param _numToWin the number of tokens in a row required to win
     * @pre     MINROWS <= rows <= MAXROWS && MINCOLS <= cols MAXCOLS && MINNUMTOWIN <= numToWin <= MAXNUMTOWIN
     * @post    a new board represented by a 2d array of blank characters is created
     */
    public GameBoard(int _rows, int _cols, int _numToWin){
        rows = _rows;
        cols = _cols;
        numToWin = _numToWin;
        board = new char[rows][cols];
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                board[i][j] = ' ';
            }
        }
    }

    public void setRows(int rowsInput){ rows = rowsInput; }

    public void setCols(int colsInput){ cols = colsInput; }

    public void setNumToWin(int numToWinInput){ numToWin = numToWinInput; }

    public int getNumRows(){ return rows; }

    public int getNumColumns(){ return cols; }

    public int getNumToWin(){ return numToWin; }

    public int getMaxRows(){ return MAXROWS; }

    public int getMaxCols(){ return MAXCOLS; }

    public int getMaxNumToWin(){ return MAXNUMTOWIN; }

    public int getMinRows(){ return MINROWS; }

    public int getMinCols(){ return MINCOLS; }

    public int getMinNumToWin(){ return MINNUMTOWIN; }

    public void placeToken(char p, int c){
        //if not, move up to the first open slot and place the token.
        int i = 0;
        while(board[i][c] != ' '){
            i++;
        }
        board[i][c] = p;
    }

    public char whatsAtPos(BoardPosition pos){
        //Return the character at position pos
        return board[pos.getRow()][pos.getColumn()];
    }

    public boolean checkTie(){

        //check and see if the board has an empty space
        BoardPosition currPos;
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                currPos = new BoardPosition(i, j);
                if(whatsAtPos(currPos) == ' '){
                    //if we find a space, then there's not a tie.
                    return false;
                }
            }
        }

        //otherwise, the game is a draw.
        return true;
    }
}