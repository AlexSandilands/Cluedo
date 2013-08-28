package game.squares;

import game.Player;
import game.objects.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * This square contains methods for adding players to the room that it represents.
 * @author Admin
 *
 */
public class RoomSquare extends Square{
	
	private Room r;
	List<Player> playersOn = new ArrayList<Player>(6);
	List<NullSquare> nulls = new ArrayList<NullSquare>(8);
	
	public RoomSquare(Room r, int col, int row){
		this.r = r;
		super.col = col;
		super.row = row;
	}

	public void setNull(NullSquare ns){
		nulls.add(ns);
	}
	
	public List<NullSquare> getNulls(){
		return nulls;
	}
	
	public Room getRoom(){
		return r;
	}
	
	public void addToRoom(Player p, Square previous){
		playersOn.add(p);
		for(NullSquare ns : nulls){
			if(ns.getPlayer() == null){
				ns.setPlayer(p);
				p.setOn(ns);
				break;
			}
		}
		previous.setPlayer(null);
	}
	
	public void leaveRoom(Player p){
		playersOn.remove(p);
		
		for(NullSquare ns : nulls){
			if(ns.getPlayer() == p){
				ns.setPlayer(null);
				break;
			}
		}
	}
	
	public List<Player> getPlayersOn(){
		return playersOn;
	}
	
}
