package game;

import game.cards.Clock;
import game.cards.GameItemCard;
import game.cards.IntrigueCard;
import game.cards.Keeper;
import game.objects.Clue;
import game.objects.GameCharacter;
import game.objects.Room;
import game.objects.Weapon;
import game.squares.Square;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import window.BoardFrame;
import window.SelectionFrame;

public class Game {

	private Board board;
	private List<Player> players = new ArrayList<Player>();
	private Player current;
	private int currentPointer;
	private Clue clue;
	private Queue<GameItemCard> rumorDeck = new LinkedList<GameItemCard>();
	private Queue<IntrigueCard> intrigueDeck = new LinkedList<IntrigueCard>();
	private int clockCount;
	private boolean playingKeeper = false;

	private BoardFrame frame;
	private List<Square> moveToSquares = new ArrayList<Square>();

	public Game(Board board) {
		this.board = board;
		new SelectionFrame(this);
	}

	/**
	 * Initializes the game, creates all the objects that are needed and sets
	 * the current player.
	 */
	public void playGame() {
		createDummyPlayers();

		setStartSquares();

		createCardsAndClue();
		distributeCards();
		createNoteBooks();

		frame = new BoardFrame(board, this);

		currentPointer = 0;
		for (Player p : players) {
			if (!p.getCharacter().isPlayable() || !p.isPlaying()) {
				currentPointer++;
				continue;
			}
			current = p;
			current.setCurrent(true);
			break;
		}
		frame.setCards();
	}

	/**
	 * Prints a messages when a player wins.
	 * 
	 * @param p
	 */
	public void win(Player p) {
		frame.dispose();
		JFrame winFrame = new JFrame("Winner!");

		winFrame.setSize(600, 500);
		winFrame.setMinimumSize(new Dimension(600, 500));

		// For Centering frame in the middle of the screen.
		// ----------------------------------------------------------
		// Get the size of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		// Determine the new location of the window
		int wid = winFrame.getSize().width;
		int h = winFrame.getSize().height;

		int x = (dim.width - wid) / 2;
		int y = (dim.height - h) / 2;

		// Move the window
		winFrame.setLocation(x, y);
		// ----------------------------------------------------------

		JPanel main = new JPanel(new BorderLayout());
		main.setBackground(new Color(139, 90, 0));

		Icon win = new ImageIcon("src/Data/Win.jpg");

		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.setBackground(new Color(139, 90, 0));

		JButton winBut = new JButton(win);
		winBut.setBackground(new Color(0, 100, 0));
		winBut.setPreferredSize(new Dimension(500, 370));
		winBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		buttonPanel.add(winBut);

		JLabel charWon = new JLabel(p.getCharacter().getName() + " Wins!");
		charWon.setFont(new Font("Serif", Font.BOLD, 40));
		charWon.setHorizontalAlignment(JLabel.CENTER);
		charWon.setForeground(Color.WHITE);

		main.add(charWon, BorderLayout.NORTH);
		main.add(buttonPanel, BorderLayout.SOUTH);
		winFrame.add(main);

		winFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		winFrame.setVisible(true);
	}

	/**
	 * Prints a messages when a playing accuses wrongly and loses, they are set
	 * to "not playing".
	 * 
	 * @param p
	 */
	public void lose(final Player p) {
		final JFrame loseFrame = new JFrame("You just lost !");
		loseFrame.setSize(600, 500);
		loseFrame.setMinimumSize(new Dimension(600, 400));

		JPanel main = new JPanel(new BorderLayout());
		main.setBackground(Color.BLACK);

		// For Centering frame in the middle of the screen.
		// ----------------------------------------------------------
		// Get the size of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		// Determine the new location of the window
		int width = loseFrame.getSize().width;
		int height = loseFrame.getSize().height;
		int x = (dim.width - width) / 2;
		int y = (dim.height - height) / 2;

		// Move the window
		loseFrame.setLocation(x, y);
		// ----------------------------------------------------------

		Icon lose = new ImageIcon("src/Data/you_lose.jpg");

		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.setBackground(Color.BLACK);

		JButton loseBut = new JButton(lose);
		loseBut.setBackground(Color.BLACK);
		loseBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				p.setPlaying(false);
				loseFrame.dispose();
				nextPlayer();
			}
		});

		buttonPanel.add(loseBut);

		JLabel charlose = new JLabel(p.getCharacter().getName()
				+ " is out of the game");
		charlose.setFont(new Font("Serif", Font.BOLD, 40));
		charlose.setForeground(Color.WHITE);
		charlose.setHorizontalTextPosition(JLabel.CENTER);

		main.add(charlose, BorderLayout.NORTH);
		main.add(buttonPanel, BorderLayout.SOUTH);
		loseFrame.add(main);

		loseFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		loseFrame.setVisible(true);
		loseFrame.pack();
	}

	/**
	 * If the 8th clock card is drawn the player that drew it is "murdered" and
	 * removed from the game. The clock card is shuffled back into the intrigue
	 * deck, so that the next player to draw it will also die.
	 * 
	 * @param p
	 */
	public void loseToClock(final Player p) {
		final JFrame loseFrame = new JFrame(p.getCharacter().getName()
				+ " was killed");
		loseFrame.setSize(600, 500);
		loseFrame.setMinimumSize(new Dimension(600, 400));

		JPanel main = new JPanel(new BorderLayout());
		main.setBackground(Color.BLACK);

		// For Centering frame in the middle of the screen.
		// ----------------------------------------------------------
		// Get the size of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		// Determine the new location of the window
		int width = loseFrame.getSize().width;
		int height = loseFrame.getSize().height;
		int x = (dim.width - width) / 2;
		int y = (dim.height - height) / 2;

		// Move the window
		loseFrame.setLocation(x, y);
		// ----------------------------------------------------------

		Icon lose = new ImageIcon("src/Data/hourglass.jpg");

		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.setBackground(Color.BLACK);

		JButton loseBut = new JButton(lose);
		loseBut.setBackground(Color.BLACK);
		loseBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				p.setPlaying(false);
				loseFrame.dispose();
				nextPlayer();
			}
		});

		buttonPanel.add(loseBut);

		JLabel charlose = new JLabel(p.getCharacter().getName()
				+ " was brutally murdered");
		charlose.setFont(new Font("Serif", Font.BOLD, 40));
		charlose.setForeground(Color.WHITE);
		charlose.setHorizontalTextPosition(JLabel.CENTER);

		main.add(charlose, BorderLayout.NORTH);
		main.add(buttonPanel, BorderLayout.SOUTH);
		loseFrame.add(main);

		loseFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		loseFrame.setVisible(true);
		loseFrame.pack();
	}

	/**
	 * When the 8th clock card is drawn it is put back in the deck of intrigue
	 * cards and shuffled. The next time it is drawn the player who drew it is
	 * murdered.
	 * 
	 * @param c
	 */
	public void add8thClock(IntrigueCard c) {
		List<IntrigueCard> toShuffle = new ArrayList<IntrigueCard>();
		toShuffle.add(c);

		while (!intrigueDeck.isEmpty()) {
			toShuffle.add(intrigueDeck.poll());
		}

		Collections.shuffle(toShuffle);
		for (IntrigueCard ic : toShuffle) {
			intrigueDeck.offer(ic);
		}

	}

	/**
	 * Lets the player draw an intrigue card
	 * 
	 * @return
	 */
	public IntrigueCard drawIntrigueCard() {
		IntrigueCard ic = intrigueDeck.poll();
		if (ic instanceof Clock) {
			clockCount++;
		}
		return ic;
	}

	public int clockCount() {
		return clockCount;
	}

	public void addPlayer(int charNum) {
		Player player = new Player(board.getCharacters().get(charNum - 1),
				board, this);

		player.setPlaying(true);
		board.getCharacters().get(charNum - 1).setPlayable(true);

		players.add(player);
	}

	public void drawBoard() {
		frame.drawBoardPanel();
	}

	public void createDummyPlayers() {
		for (GameCharacter gc : board.getCharacters()) {
			if (!gc.isPlayable()) {
				Player dummy = new Player(gc, board, this);
				players.add(dummy);
			}
		}
	}

	public BoardFrame getFrame() {
		return frame;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public Player getNextPlayer() {
		int currentPointerCopy = currentPointer;
		if (currentPointerCopy == 5) {
			currentPointerCopy = -1;
		}

		currentPointerCopy++;

		if (!players.get(currentPointerCopy).isPlaying()) {

			while (!players.get(currentPointerCopy).isPlaying()) {
				if (currentPointerCopy == 5) {
					currentPointerCopy = -1;
				}
				currentPointerCopy++;
			}
		}

		return players.get(currentPointerCopy);
	}

	public Player getNextPlayer(Player p) {
		int currentPointerCopy = 0;
		int count = 0;

		for (Player play : players) {
			if (play == p) {
				currentPointerCopy = count;
			} else {
				count++;
			}
		}

		if (currentPointerCopy == 5) {
			currentPointerCopy = -1;
		}

		currentPointerCopy++;

		if (!players.get(currentPointerCopy).isPlaying()) {

			while (!players.get(currentPointerCopy).isPlaying()) {
				if (currentPointerCopy == 5) {
					currentPointerCopy = -1;
				}
				currentPointerCopy++;
			}
		}

		return players.get(currentPointerCopy);
	}

	public void nextPlayer() {
		current.setCurrent(false);

		if (currentPointer == 5) {
			currentPointer = -1;
		}

		currentPointer++;

		if (!players.get(currentPointer).isPlaying()) {

			while (!players.get(currentPointer).isPlaying()) {
				if (currentPointer == 5) {
					currentPointer = -1;
				}
				currentPointer++;
			}
		}

		current = players.get(currentPointer);
		current.setCurrent(true);
		frame.removeCards();
		// frame.repaint();
		frame.setCards();
	}

	public Player getCurrent() {
		return current;
	}

	/**
	 * @return the board
	 */
	public Board getBoard() {
		return board;
	}

	public void setMoveToSquares(List<Square> list) {
		this.moveToSquares = list;
	}

	public List<Square> getMoveToSquares() {
		return moveToSquares;
	}

	public void clearMoveToSquares() {
		for (Square s : moveToSquares) {
			s.setSelectable(false);
		}

		moveToSquares.clear();
	}

	public Square getMoveTo() {
		return board.getMoveTo();
	}

	public Clue getClue() {
		return clue;
	}

	public Queue<IntrigueCard> getIntrigueDeck() {
		return intrigueDeck;
	}

	public void setPlayingKeeper(boolean b) {
		this.playingKeeper = b;
	}

	public boolean getPlayingKeeper() {
		return playingKeeper;
	}

	/**
	 * Puts the players onto the specific squares they are supposed to start on,
	 * defined by the rules of the game.
	 */
	private void setStartSquares() {
		for (Player p : players) {
			if (p.getCharacter().getName().equals("Kasandra Scarlett")) {
				p.setOn(board.getSquare(28, 18));
				board.getSquare(28, 18).setPlayer(p);
			} else if (p.getCharacter().getName().equals("Jack Mustard")) {
				p.setOn(board.getSquare(28, 7));
				board.getSquare(28, 7).setPlayer(p);
			} else if (p.getCharacter().getName().equals("Diane White")) {
				p.setOn(board.getSquare(19, 0));
				board.getSquare(19, 0).setPlayer(p);
			} else if (p.getCharacter().getName().equals("Jacob Green")) {
				p.setOn(board.getSquare(9, 0));
				board.getSquare(9, 0).setPlayer(p);
			} else if (p.getCharacter().getName().equals("Eleanor Peacock")) {
				p.setOn(board.getSquare(0, 6));
				board.getSquare(0, 6).setPlayer(p);
			} else if (p.getCharacter().getName().equals("Victor Plum")) {
				p.setOn(board.getSquare(0, 20));
				board.getSquare(0, 20).setPlayer(p);
			}
		}
	}

	/**
	 * Creates a queue of cards in a random order. Randomly creates the Clue
	 * object.
	 * 
	 * Creates the deck of intrigue cards.
	 */
	private void createCardsAndClue() {
		Random rand = new Random();

		Weapon clueWep = board.getWeapons().get(rand.nextInt(9));

		int rm = rand.nextInt(10);
		rm = rm == 5 ? rand.nextInt(5) : rm; // Would have been pointing at Pool
												// otherwise.
		Room clueRoom = board.getRooms().get(rm);

		GameCharacter clueChar = board.getCharacters().get(rand.nextInt(6));

		clueWep.setClue();
		clueRoom.setClue();
		clueChar.setClue();
		clue = new Clue(clueWep, clueRoom, clueChar);

		List<GameItemCard> temp = new ArrayList<GameItemCard>(24);

		for (GameCharacter gc : board.getCharacters()) {
			if (gc.isClue()) {
				continue;
			}
			temp.add(new GameItemCard(gc));
		}

		for (Room r : board.getRooms()) {
			if (r.getName().equals("Pool") || r.isClue()) {
				continue;
			}
			temp.add(new GameItemCard(r));
		}

		for (Weapon w : board.getWeapons()) {
			if (w.isClue()) {
				continue;
			}
			temp.add(new GameItemCard(w));
		}

		// Randomly takes items out the temp list and offers them to the queue
		// Ie simulates a shuffled deck of rumor cards.
		Collections.shuffle(temp);

		for (GameItemCard c : temp) {
			rumorDeck.offer(c);
		}

		// Create the deck of intrigue cards. 30 in total
		List<IntrigueCard> toShuffle = new ArrayList<IntrigueCard>(30);

		// Add 10 "Add to roll" keeper cards.
		for (int i = 0; i < 10; i++) {
			toShuffle.add(new Keeper("Roll", i + 1));
		}

		// Add 2 "Move to Any Location" cards
		for (int i = 0; i < 2; i++) {
			toShuffle.add(new Keeper("Move"));
		}

		// Add 2 "Send to Start Square" cards
		for (int i = 0; i < 2; i++) {
			toShuffle.add(new Keeper("Start"));
		}

		// Add 2 "Avoid Guess" cards.
		for (int i = 0; i < 2; i++) {
			toShuffle.add(new Keeper("Avoid"));
		}

		// Add 2 "Make Guess" cards.
		for (int i = 0; i < 2; i++) {
			toShuffle.add(new Keeper("Make"));
		}

		// Add 2 "New Turn" cards.
		for (int i = 0; i < 2; i++) {
			toShuffle.add(new Keeper("Turn"));
		}

		// Add 2 "See Another Player's Card" cards.
		for (int i = 0; i < 2; i++) {
			toShuffle.add(new Keeper("See"));
		}

		for (int i = 0; i < 8; i++) {
			toShuffle.add(new Clock());
		}

		Collections.shuffle(toShuffle);

		for (IntrigueCard ic : toShuffle) {
			intrigueDeck.offer(ic);
		}

	}

	/**
	 * Recreates the intrigue cards, giving every player every keeper card in the
	 * game. Can only be used before an intrigue card has been drawn.
	 */
	public void cheatKeeperCards() {
		if(intrigueDeck.size() != 30){
			int r = JOptionPane.showConfirmDialog(getFrame(),
					new JLabel("You can only toggle this cheat before someone draws an intrigue card!"),
					"Can't do that", JOptionPane.CLOSED_OPTION,
					JOptionPane.WARNING_MESSAGE);
			if (r == 0) {
				return;
			}
			return;
		}
		
		// Create the deck of intrigue cards. 30 in total
		List<IntrigueCard> toShuffle = new ArrayList<IntrigueCard>(30);

		// Add 10 "Add to roll" keeper cards.
		for (int i = 0; i < 10; i++) {
			Keeper k = new Keeper("Roll", i + 1);
			for (Player p : players) {
				p.addKeeper(k);
			}
			toShuffle.add(new Keeper("Roll", i + 1));
		}

		// Add 2 "Move to Any Location" cards
		for (int i = 0; i < 2; i++) {
			Keeper k = new Keeper("Move");
			for (Player p : players) {
				p.addKeeper(k);
			}
			toShuffle.add(new Keeper("Move"));
		}

		// Add 2 "Send to Start Square" cards
		for (int i = 0; i < 2; i++) {
			Keeper k = new Keeper("Start");
			for (Player p : players) {
				p.addKeeper(k);
			}
			toShuffle.add(new Keeper("Start"));
		}

		// Add 2 "Avoid Guess" cards.
		for (int i = 0; i < 2; i++) {
			Keeper k = new Keeper("Avoid");
			for (Player p : players) {
				p.addKeeper(k);
			}
			toShuffle.add(new Keeper("Avoid"));
		}

		// Add 2 "Make Guess" cards.
		for (int i = 0; i < 2; i++) {
			Keeper k = new Keeper("Make");
			for (Player p : players) {
				p.addKeeper(k);
			}
			toShuffle.add(new Keeper("Make"));
		}

		// Add 2 "New Turn" cards.
		for (int i = 0; i < 2; i++) {
			Keeper k = new Keeper("Turn");
			for (Player p : players) {
				p.addKeeper(k);
			}
			toShuffle.add(new Keeper("Turn"));
		}

		// Add 2 "See Another Player's Card" cards.
		for (int i = 0; i < 2; i++) {
			Keeper k = new Keeper("See");
			for (Player p : players) {
				p.addKeeper(k);
			}
			toShuffle.add(new Keeper("See"));
		}

		for (int i = 0; i < 8; i++) {
			toShuffle.add(new Clock());
		}

		Collections.shuffle(toShuffle);

		for (IntrigueCard ic : toShuffle) {
			intrigueDeck.offer(ic);
		}

	}

	/**
	 * "Deals" the rumor cards to the players.
	 */
	private void distributeCards() {
		while (!rumorDeck.isEmpty()) {
			for (Player p : players) {
				if (p.isPlaying()) {
					p.addCard(rumorDeck.poll());
				}
			}

		}
	}

	/**
	 * Gives each player a note book, with every value initially false. Then
	 * updates the note books.
	 */
	private void createNoteBooks() {
		for (GameCharacter gc : board.getCharacters()) {
			for (Player p : players) {
				p.updateNoteBook(gc, false);
			}

		}
		for (Room r : board.getRooms()) {
			if (r.getName().equals("Pool")) {
				continue;
			}

			for (Player p : players) {
				p.updateNoteBook(r, false);
			}

		}

		for (Weapon w : board.getWeapons()) {
			for (Player p : players) {
				p.updateNoteBook(w, false);
			}

		}

		// Updates the notebooks depending on which cards the player has.
		for (Player p : players) {
			p.updateNoteBook();
		}
	}

	public static void main(String[] args) {
		new Game(new Board());
	}

}
