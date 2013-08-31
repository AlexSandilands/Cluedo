package game.squares;

/**
 * Players cannot move through these squares.
 * @author Admin
 *
 */
public class WallSquare extends Square{

    public WallSquare(int col, int row){
        super.col = col;
        super.row = row;
    }

}
