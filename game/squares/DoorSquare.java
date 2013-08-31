package game.squares;

import game.objects.Room;

public class DoorSquare extends Square{

    private Room r;

    public DoorSquare(Room r, int col, int row){
        this.r = r;
        super.col = col;
        super.row = row;
    }

    /**
     * @return the room
     */
    public Room getR() {
        return r;
    }

}
