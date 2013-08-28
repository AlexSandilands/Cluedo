package game.objects;

import javax.swing.Icon;


public interface GameItem {
	
	/**
	 * Returns the name of this object
	 * @return
	 */
	public String getName();
	
	/**
	 * Returns whether or not this item is in the clue.
	 * @return
	 */
	public boolean isClue();

	/**
	 * Sets whether or not this item is one of the murder items.
	 */
	public void setClue();
	
	/**
	 * Returns the icon of the corresponding item
	 * @return Icon
	 */
	public Icon getIcon();
	
	/**
	 * Returns the small icon of the corresponding item.
	 * @return
	 */
	public Icon getSmallIcon();
}
