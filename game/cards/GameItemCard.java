package game.cards;

import game.objects.GameCharacter;
import game.objects.GameItem;
import game.objects.Room;
import game.objects.Weapon;

/**
 * Class that represents an item in the game. It holds two
 * string values, the type of card it is (weapon, room, gameCharacter) 
 * and the name of the card. It also holds the GameItem object that
 * it represents.
 *
 */
public class GameItemCard {

	private String type;
	private String name;
	private GameItem gi;

	public GameItemCard(GameItem gi) {
		this.type = configureType(gi);
		this.gi = gi;
	}

	private String configureType(GameItem gi) {
		if (gi instanceof Weapon) {
			this.name = gi.getName();
			return "Weapon";
		} else if (gi instanceof Room) {
			this.name = gi.getName();
			return "Room";
		} else if (gi instanceof GameCharacter) {
			this.name = gi.getName();
			return "Character";
		} else {
			throw new Error("GameItem type was wrong? This shouldn't have happened");
		}
	}
	
	public GameItem getItem(){
		return gi;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}
}
