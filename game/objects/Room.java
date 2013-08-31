package game.objects;

import game.squares.RoomSquare;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Room implements GameItem{

    private String name;
    private List<Weapon> weapons = new ArrayList<Weapon>();
    private List<GameCharacter> playersIn = new ArrayList<GameCharacter>();
    private boolean isClue = false;
    private RoomSquare rs;


    public Room(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setClue(){
        isClue = true;
    }

    public boolean isClue(){
        return isClue;
    }

    /**
     * @return the weapon
     */
    public List<Weapon> getWeapons() {
        return weapons;
    }

    /**
     * @param weapon the weapon to set
     */
    public void addWeapon(Weapon weapon) {
        this.weapons.add(weapon);
    }

    public void removeWeapon(Weapon w){
        this.weapons.remove(w);
    }

    /**
     * Give the room a pointer to it's RoomSquare
     * @param rs
     */
    public void setRoomSquare(RoomSquare rs){
        this.rs = rs;
    }

    public RoomSquare getRoomSquare(){
        return rs;
    }

    /**
     * @param the Game Character to enter the room
     */
    public void enterRoom(GameCharacter gc){
        playersIn.add(gc);
    }

    /**
     * @param the Game Character for checking
     * @return true / false
     */
    public boolean isInRoom(GameCharacter gc){
        return playersIn.contains(gc);
    }

    /**
     * @param the Game Character to leave the room
     */
    public void leaveRoom(GameCharacter gc){
        playersIn.remove(gc);
    }

    /**
     * @return if the room is empty or not
     */
    public boolean isEmpty(){
        return playersIn.size() == 0;
    }

    public Icon getIcon(){
        if(name.equals("Spa")){
            return new ImageIcon("src/Rooms/Spa.jpg");
        }else if(name.equals("Theater")){
            return new ImageIcon("src/Rooms/Theater.jpg");
        }else if(name.equals("Living-Room")){
            return new ImageIcon("src/Rooms/Living Room.jpg");
        }else if(name.equals("Observatory")){
            return new ImageIcon("src/Rooms/Observatory.jpg");
        }else if(name.equals("Patio")){
            return new ImageIcon("src/Rooms/Patio.jpg");
        }else if(name.equals("Kitchen")){
            return new ImageIcon("src/Rooms/Kitchen.jpg");
        }else if(name.equals("Hall")){
            return new ImageIcon("src/Rooms/Hall.jpg");
        }else if(name.equals("Dining-Room")){
            return new ImageIcon("src/Rooms/Dining Room.jpg");
        }else if(name.equals("Guest-House")){
            return new ImageIcon("src/Rooms/Guest House.jpg");
        }
        else{
            throw new Error(name.toString()+" is not a valid room");
        }
    }

    @Override
    public Icon getSmallIcon() {
        if(name.equals("Spa")){
            return new ImageIcon("src/Cards/Rooms/Small Spa.jpg");
        }else if(name.equals("Theater")){
            return new ImageIcon("src/Cards/Rooms/Small Theater.jpg");
        }else if(name.equals("Living-Room")){
            return new ImageIcon("src/Cards/Rooms/Small Living Room.jpg");
        }else if(name.equals("Observatory")){
            return new ImageIcon("src/Cards/Rooms/Small Observatory.jpg");
        }else if(name.equals("Patio")){
            return new ImageIcon("src/Cards/Rooms/Small Patio.jpg");
        }else if(name.equals("Kitchen")){
            return new ImageIcon("src/Cards/Rooms/Small Kitchen.jpg");
        }else if(name.equals("Hall")){
            return new ImageIcon("src/Cards/Rooms/Small Hall.jpg");
        }else if(name.equals("Dining-Room")){
            return new ImageIcon("src/Cards/Rooms/Small Dining Room.jpg");
        }else if(name.equals("Guest-House")){
            return new ImageIcon("src/Cards/Rooms/Small Guest House.jpg");
        }
        else{
            throw new Error(name.toString()+" is not a valid room");
        }
    }
}
