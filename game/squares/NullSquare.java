package game.squares;

/**
 * This square one of 8 null squares surrounding a specific
 * RoomSquare. It is necessary for the players to be able to enter a room
 * (and be drawn on a null square)
 * @author Admin
 *
 */
public class NullSquare extends Square {

	private RoomSquare rs;

	public NullSquare(int col, int row) {
		super.col = col;
		super.row = row;
	}

	public void setRoomSquare(RoomSquare rs) {
		this.rs = rs;
	}

	public RoomSquare getRoomSquare() {
		return rs;
	}

}
