package game.objects;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Weapon implements GameItem{

    private Weapons name;
    private boolean isClue = false;

    public Weapon(Weapons name){
        this.name = name;
    }

    @Override
    public String getName() {
        return name.toString();
    }

    public void setClue(){
        isClue = true;
    }

    public boolean isClue(){
        return isClue;
    }

    public Icon getIcon(){
        if(name.equals(Weapons.Rope)){
            return new ImageIcon("DataFiles/Weapons/Rope.jpg");
        }else if(name.equals(Weapons.Axe)){
            return new ImageIcon("DataFiles/Weapons/Axe.jpg");
        }else if(name.equals(Weapons.Baseball_Bat)){
            return new ImageIcon("DataFiles/Weapons/Baseball Bat.jpg");
        }else if(name.equals(Weapons.Candlestick)){
            return new ImageIcon("DataFiles/Weapons/Candle Stick.jpg");
        }else if(name.equals(Weapons.Dumbbell)){
            return new ImageIcon("DataFiles/Weapons/Dumbell.jpg");
        }else if(name.equals(Weapons.Knife)){
            return new ImageIcon("DataFiles/Weapons/Knife.jpg");
        }else if(name.equals(Weapons.Pistol)){
            return new ImageIcon("DataFiles/Weapons/Pistol.jpg");
        }else if(name.equals(Weapons.Poison)){
            return new ImageIcon("DataFiles/Weapons/Poison.jpg");
        }else if(name.equals(Weapons.Trophy)){
            return new ImageIcon("DataFiles/Weapons/Trophy.jpg");
        }
        else{
            throw new Error(name.toString()+" is not a valid weapon");
        }
    }

    public Icon getSmallIcon(){
        if(name.equals(Weapons.Rope)){
            return new ImageIcon("DataFiles/Cards/Weapons/Small Rope.jpg");
        }else if(name.equals(Weapons.Axe)){
            return new ImageIcon("DataFiles/Cards/Weapons/Small Axe.jpg");
        }else if(name.equals(Weapons.Baseball_Bat)){
            return new ImageIcon("DataFiles/Cards/Weapons/Small Baseball Bat.jpg");
        }else if(name.equals(Weapons.Candlestick)){
            return new ImageIcon("DataFiles/Cards/Weapons/Small Candle Stick.jpg");
        }else if(name.equals(Weapons.Dumbbell)){
            return new ImageIcon("DataFiles/Cards/Weapons/Small Dumbell.jpg");
        }else if(name.equals(Weapons.Knife)){
            return new ImageIcon("DataFiles/Cards/Weapons/Small Knife.jpg");
        }else if(name.equals(Weapons.Pistol)){
            return new ImageIcon("DataFiles/Cards/Weapons/Small Pistol.jpg");
        }else if(name.equals(Weapons.Poison)){
            return new ImageIcon("DataFiles/Cards/Weapons/Small Poison.jpg");
        }else if(name.equals(Weapons.Trophy)){
            return new ImageIcon("DataFiles/Cards/Weapons/Small Trophy.jpg");
        }
        else{
            throw new Error(name.toString()+" is not a valid weapon");
        }
    }

    public enum Weapons{Rope, Candlestick, Knife, Pistol, Baseball_Bat, Dumbbell, Trophy, Poison, Axe};

}
