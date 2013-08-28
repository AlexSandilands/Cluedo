package game.objects;

/**
 * Simple class for holding the answers to the murder. 
 *
 */
public class Clue {
	
	private Weapon w;
	private Room r;
	private GameCharacter c;
	
	public Clue(Weapon w, Room r, GameCharacter c){
		this.w = w;
		this.r = r;
		this.c = c;
	}
	
	public Weapon getWeapon(){
		return w;
	}
	
	public Room getRoom(){
		return r;
	}
	
	public GameCharacter getCharacter(){
		return c;
	}
	
	public boolean accuse(GameItem g){
		if(g instanceof Weapon){
			return g == w;
		} else if(g instanceof Room){
			return g == r;
		} else if(g instanceof GameCharacter){
			return g == c;
		} else {
			throw new Error("This should never happen");
		}
	}

}
