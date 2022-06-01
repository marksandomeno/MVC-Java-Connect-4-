/** Mark Sandomeno
 *  4/28/22
 *  BoardPosition.java
 **/

package cpsc2150.extendedConnectX.models;

public class BoardPosition {

    private int rowNum;
    private int colNum;

    /**
     * @pre     A game has been started, and a game board has been created.
     * @param row    the row number of the position to be created.
     * @param column the column number of the position to be created.
     * @post    A fully made BoardPosition class instance.
     */
    public BoardPosition(int row, int column){
        rowNum = row;
        colNum = column;
    }

    /**
     * @post    Get the row number of the position.
     * @return  Returns an integer representing the row number of the position.
     */
    public int getRow(){
        return rowNum;
    }

    /**
     * @post    Get the column number of the position.
     * @return  Returns an integer representing the column number of the position.
     */
    public int getColumn(){
        return colNum;
    }

    /**
     * @param position An instance of the BoardPosition class.
     * @return  true if the two positions have the same row and column.
     *          false if the two positions have different rows or columns.
     */
    @Override
    public boolean equals(Object position){
        if(this == position){return true;}
        if(position == null || getClass() != position.getClass()){return false;}
        BoardPosition boardPos = (BoardPosition) position;
        return ((rowNum == boardPos.rowNum) && (colNum == boardPos.colNum));
    }

    /**
     * @post    the BoardPosition class will be formatted as a string.
     * @return  a fully formatted BoardPosition as "Row, Column".
     */
    @Override
    public String toString(){
        String toReturn = "";
        toReturn += Integer.toString(rowNum);
        toReturn += ", ";
        toReturn += Integer.toString(colNum);
        return toReturn;
    }


}