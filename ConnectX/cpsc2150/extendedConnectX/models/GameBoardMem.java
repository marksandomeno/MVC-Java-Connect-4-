/** Mark Sandomeno
 *  4/28/22
 *  GameBoardMem.java
 **/

package cpsc2150.extendedConnectX.models;
import java.util.*;

public class GameBoardMem extends AbsGameBoard {

    private int rows;
    private int cols;
    private int numToWin;
    private Map<Character, List<BoardPosition>> board;

    /**
     *
     * @param _rows     the number of rows in the board
     * @param _cols     the number of columns in the board
     * @param _numToWin the number of tokens in a row required to win
     * @pre     MINROWS <= rows <= MAXROWS && MINCOLS <= cols MAXCOLS && MINNUMTOWIN <= numToWin <= MAXNUMTOWIN
     * @post    a new board represented by a hash table is created
     */

    public GameBoardMem(int _rows, int _cols, int _numToWin){
        rows = _rows;
        cols = _cols;
        numToWin = _numToWin;
        board = new HashMap<>();
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
        //if the key is absent, start a new list of positions
        board.putIfAbsent(p, new ArrayList<>());
        BoardPosition insertPos = new BoardPosition(0, c);
        int i = 0;
        //iterate until we find the next available position in the column
        while(whatsAtPos(insertPos) != ' '){
            insertPos = new BoardPosition(i, c);
            i++;
        }
        //insert the token into the board
        board.get(p).add(insertPos);
    }

    public char whatsAtPos(BoardPosition pos){
        //loop through each key
        for(Map.Entry<Character, List<BoardPosition>> m : board.entrySet()){
            //loop through each member in the list
            for(int i = 0; i<m.getValue().size(); i++){
                //if we find the specific boardPosition, return the key
                if(m.getValue().get(i).equals(pos)){
                    return m.getKey();
                }
            }
        }
        return ' ';
    }

    public boolean checkTie(){

        //check each individual position
        BoardPosition currPos;
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                currPos = new BoardPosition(i, j);
                //if we find a space, it's not a tie
                if(whatsAtPos(currPos) == ' '){ return false; }
            }
        }

        return true;
    }

}