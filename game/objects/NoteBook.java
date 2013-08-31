package game.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteBook {
    private Map<GameItem, Boolean> notebook;
    private List<Room> rooms = new ArrayList<Room>();
    private List<Weapon> weapons = new ArrayList<Weapon>();
    private List<GameCharacter> gCharacters = new ArrayList<GameCharacter>();

    public NoteBook(){
        notebook = new HashMap<GameItem, Boolean>();
    }

    /**
     * A method to add or change the value for a Card.
     * @param the Card
     * @param it's truth value
     */
    public void addToNoteBook(GameItem gi, boolean bool){
        notebook.put(gi, bool);
        if(gi instanceof Room && !rooms.contains(gi)){
            rooms.add((Room)gi);
        }
        else if(gi instanceof Weapon && !weapons.contains(gi)){
            weapons.add((Weapon)gi);
        }
        else if(gi instanceof GameCharacter && !gCharacters.contains(gi)){
            gCharacters.add((GameCharacter)gi);
        }
    }

    /**
     * A method that returns the truth value of a given
     * Card.
     * @param the Card
     * @return it's truth value
     */
    public boolean getFromNoteBook(GameItem gi){
        return notebook.get(gi);
    }

    public Map<GameItem, Boolean> getBook(){
        return notebook;
    }

    /**
     * @return the rooms
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * @return the weapons
     */
    public List<Weapon> getWeapons() {
        return weapons;
    }

    /**
     * @return the gCharacters
     */
    public List<GameCharacter> getCharacters() {
        return gCharacters;
    }





}
