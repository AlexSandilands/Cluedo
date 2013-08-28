package window;

import game.Game;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

/**
 * A class that represents the menu bar for
 * the main game window.
 * @author feshersiva
 *
 */
public class MenuBar extends JMenuBar{

	private JMenu file;
	private JMenu game;
	private JMenu cheats;
	private BoardFrame frame;
	
	public MenuBar(BoardFrame frame, Game bGame){
		this.frame = frame;
		Menus menus = new Menus(frame, bGame);
		this.file = menus.getFile();
		this.game = menus.getGame();
		this.cheats = menus.getCheats();
		
		this.add(file);
		this.add(game);
		this.add(cheats);
	}
	
}
