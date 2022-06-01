/** Mark Sandomeno
 *  4/28/22
 *  AbsGameBoard.java
 **/

package cpsc2150.extendedConnectX.models;



public abstract class AbsGameBoard implements IGameBoard {

    /**
     * @pre     A new turn has just begun.
     * @post    This will make a string ready to print to the terminal.
     * @return  A string representing the current board state.
     */

    @Override
    public String toString(){

        //initialize the header numbers at the top of the string
        String boardString = "";
        for(int i=0; i<getNumColumns(); i++){
            //whitespace padding for numbers
            String header = String.format("|%2d", i);
            boardString = boardString.concat(header);
        }
        boardString = boardString.concat("|\n");

        //print the contents of the game board to the string
        BoardPosition pos;
        for(int r = getNumRows()-1; r>=0; r--){
            for(int c = 0; c < getNumColumns(); c++){ //no, this is java
                pos = new BoardPosition(r, c);
                char toAdd = whatsAtPos(pos);
                //whitespace padding for characters
                String body = String.format("|%-2s", toAdd);
                boardString = boardString.concat(body);
            }
            boardString = boardString.concat("|\n");
        }
        boardString = boardString.concat("\n");

        //return the string
        return boardString;
    }


}