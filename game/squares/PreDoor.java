package game.squares;

import game.objects.Room;

/**
 * Represents an entrance to a room.
 * @author Admin
 *
 */
public class PreDoor extends Square{

	private Room r;
	
	public PreDoor(Room r, int col, int row){
		this.r = r;
		super.col = col;
		super.row = row;
	}

	public Room getRoom(){
		return r;
	}


}
