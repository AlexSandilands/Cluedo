package game.objects;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Class to describe a character in the game.
 * @author Admin
 *
 */
public class GameCharacter implements GameItem{
	
	private String name;
	private boolean playable = false;
	private boolean isClue = false;
	private int startCol;
	private int startRow;
	
	
	public GameCharacter(String name, int col, int row){
		this.name = name;
		this.startCol = col;
		this.startRow = row;
	}

	public int[] getStartPos(){
		int[] pos = {startCol, startRow};
		return pos;
	}
	
	/**
	 * @return the playable
	 */
	public boolean isPlayable() {
		return playable;
	}

	/**
	 * @param playable the playable to set
	 */
	public void setPlayable(boolean playable) {
		this.playable = playable;
	}

	@Override
	public String getName() {
		return name;
	}
	
	public void setClue(){
		isClue = true;
	}

	public boolean isClue() {
		return isClue;
	}
	
	public Color getColor(){
		if(this.name.equals("Eleanor Peacock")){
			return new Color(67, 110, 238);
		} else if(this.name.equals("Victor Plum")){
			return new Color(145, 44, 238);
		} else if(this.name.equals("Jacob Green")){
			return new Color(61, 145, 64);
		} else if(this.name.equals("Diane White")){
			return Color.white;
		} else if(this.name.equals("Jack Mustard")){
			return new Color(255, 185, 15);
		} else if(this.name.equals("Kasandra Scarlett")){
			return new Color(205, 0, 0);
		}
		
		throw new Error("Incorrect Name");
	}

	public Icon getIcon() {
		if(name.equals("Eleanor Peacock")){
			return new ImageIcon("src/Characters/Peacock.jpg");
		}else if(name.equals("Victor Plum")){
			return new ImageIcon("src/Characters/Plum.jpg");
		}else if(name.equals("Jacob Green")){
			return new ImageIcon("src/Characters/Green.jpg");
		}else if(name.equals("Diane White")){
			return new ImageIcon("src/Characters/White.jpg");
		}else if(name.equals("Jack Mustard")){
			return new ImageIcon("src/Characters/Mustard.jpg");
		}else if(name.equals("Kasandra Scarlett")){
			return new ImageIcon("src/Characters/Scarlett.jpg");
		}
		else{
			throw new Error(name.toString()+" is not a valid name");
		}
	}

	@Override
	public Icon getSmallIcon() {
		if(name.equals("Eleanor Peacock")){
			return new ImageIcon("src/Cards/Characters/SmallPeacock.jpg");
		}else if(name.equals("Victor Plum")){
			return new ImageIcon("src/Cards/Characters/SmallPlum.jpg");
		}else if(name.equals("Jacob Green")){
			return new ImageIcon("src/Cards/Characters/SmallGreen.jpg");
		}else if(name.equals("Diane White")){
			return new ImageIcon("src/Cards/Characters/SmallWhite.jpg");
		}else if(name.equals("Jack Mustard")){
			return new ImageIcon("src/Cards/Characters/SmallMustard.jpg");
		}else if(name.equals("Kasandra Scarlett")){
			return new ImageIcon("src/Cards/Characters/SmallScarlett.jpg");
		}
		else{
			throw new Error(name.toString()+" is not a valid name");
		}
	}

}
