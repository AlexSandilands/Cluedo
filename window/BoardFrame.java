package window;

import game.Board;
import game.Game;
import game.Player;
import game.cards.GameItemCard;
import game.squares.Square;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class BoardFrame extends JFrame {

	// Fields relating to the actual game.
	private Board board;
	private Game game;

	// Fields relating to the Gui
	private BoardPanel boardpanel;
	private JPanel bottom;
	private JPanel right;
	private JPanel top;
	private ButtonGod buttons;
	private JComponent cards;
	private JPanel labelPanel;

	public BoardFrame(Board board, Game game) {
		super("Cluedo");

		getContentPane().setBackground(Color.BLACK);

		this.board = board;
		this.game = game;

		setSize(950, 840);
		setMinimumSize(new Dimension(950, 840));

		// For Centering frame in the middle of the screen.
		// ----------------------------------------------------------
		// Get the size of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		// Determine the new location of the window
		int w = this.getSize().width;
		int h = this.getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;

		// Move the window
		setLocation(x, y);
		// ----------------------------------------------------------

		// Creates the center panel which draws the board.
		boardpanel = new BoardPanel(592, 580, board, game);
		this.board.setSize(600, 600);

		// Creates the right and bottom panels
		createMainPanel();
		createBottom();

		// Add the menu bar
		MenuBar menu = new MenuBar(this, game);
		setJMenuBar(menu);

		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Add the KeyListener
		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new KeyDispatcher());

		pack();
		repaint();
	}

	public void createMainPanel() {
		createRight();
		top = new JPanel();
		top.setBackground(Color.BLACK);

		top.setSize(800, 600);
		top.add(boardpanel, BorderLayout.WEST);
		top.add(right, BorderLayout.EAST);

		top.addComponentListener(new ComponentAdapter() {

			public void componentResized(ComponentEvent e) {
				JPanel c = (JPanel) e.getSource();

				top.setSize(new Dimension(c.getWidth(), c.getHeight()));
				repaint();
			}
		});

		add(top, BorderLayout.NORTH);
	}

	public void createBottom() {
		bottom = new JPanel(new BorderLayout());

		Border greenLine = BorderFactory.createLineBorder(new Color(0, 100, 0));
		Border border = BorderFactory.createRaisedBevelBorder();

		border = BorderFactory.createCompoundBorder(greenLine, border);
		bottom.setBorder(border);

		bottom.setPreferredSize(new Dimension(950, 200));

		bottom.setBackground(new Color(139, 90, 0));

		add(bottom, BorderLayout.SOUTH);

	}

	public void createRight() {
		right = new JPanel();

		buttons = new ButtonGod(game);
		right.add(buttons.getButtonPanel());

		right.setPreferredSize(new Dimension(185, 580));

		right.setBackground(new Color(139, 90, 0));
		Border greenLine = BorderFactory.createLineBorder(new Color(0, 100, 0));
		Border border = BorderFactory.createRaisedBevelBorder();

		border = BorderFactory.createCompoundBorder(greenLine, border);
		right.setBorder(border);
	}

	public void drawBoardPanel() {
		boardpanel.repaint();
	}

	public void changeLayout(File f) {
		boardpanel.setBoardFile(f);
	}

	public void addToRoll(int i) {
		buttons.addToRoll(i);
	}

	public void decreaseRoll() {
		buttons.decreaseRoll();
	}

	public void clearRoll() {
		buttons.clearRoll();
	}

	/*
	 * A method that displays the current players cards on the bottom panel.
	 */
	public void setCards() {
		cards = new JPanel(new FlowLayout());
		cards.setBackground(new Color(139, 90, 0));
		Player p = game.getCurrent();
		List<GameItemCard> list = p.getCards();

		for (GameItemCard card : list) {
			if (card == null) {
				continue;
			}
			JButton b = new JButton(card.getItem().getSmallIcon());
			b.setBackground(new Color(0, 100, 0));
			cards.add(b);
		}

		labelPanel = new JPanel();
		labelPanel.setBackground(new Color(139, 90, 0));
		labelPanel.setPreferredSize(new Dimension(950, 40));

		JLabel currentChar = new JLabel();
		currentChar.setFont(new Font("Serif", Font.BOLD, 30));
		currentChar.setForeground(Color.WHITE);
		currentChar.setHorizontalAlignment(JLabel.CENTER);
		currentChar.setText(p.getCharacter().getName() + "\'s Cards:");

		labelPanel.add(currentChar);

		bottom.add(labelPanel, BorderLayout.NORTH);
		bottom.add(cards, BorderLayout.SOUTH);
		labelPanel.validate();
		cards.validate();
		validate();
		repaint();
	}

	public void removeCards() {
		bottom.remove(labelPanel);
		bottom.remove(cards);
		bottom.repaint();
		repaint();
	}

	private class KeyDispatcher implements KeyEventDispatcher {

		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			if (e.getID() == KeyEvent.KEY_RELEASED) {
				int key = e.getKeyCode();

				if (key == KeyEvent.VK_R) {

					if (game.getCurrent().isMoving()) {
						return false;
					}

					// Cannot press it again if they have already moved
					if (game.getCurrent().hasMoved()) {
						int r = JOptionPane
								.showConfirmDialog(game.getFrame(), new JLabel(
										"You have already rolled this turn!"),
										"Can't do that",
										JOptionPane.CLOSED_OPTION,
										JOptionPane.WARNING_MESSAGE);
						if (r == 0) {
							return false;
						}
					}

					buttons.setRoll(game.getCurrent().roll());

					// If they are in a room, they will first be giving options
					// to
					// leave the room.
					if (game.getCurrent().inRoom()) {
						List<Square> moveSquares = game.getCurrent()
								.leaveRoom();
						if (moveSquares.isEmpty()) {
							return false;
						} else {
							game.setMoveToSquares(moveSquares);
						}

					} else {
						game.getCurrent().setMoving(true);
					}
				}

				if (key == KeyEvent.VK_G) {
					if (game.getCurrent().hasGuessed()) {
						int r = JOptionPane.showConfirmDialog(game.getFrame(),
								new JLabel(
										"You have already guessed this turn!"),
								"Can't do that", JOptionPane.CLOSED_OPTION,
								JOptionPane.WARNING_MESSAGE);
						if (r == 0) {
							return false;
						}
						return false;
					}

					if (game.getCurrent().inRoom()) {
						new GuessFrame(game);
					} else {
						int r = JOptionPane.showConfirmDialog(game.getFrame(),
								new JLabel("You must be in a room to guess!"),
								"Can't do that", JOptionPane.CLOSED_OPTION,
								JOptionPane.WARNING_MESSAGE);
						if (r == 0) {
							return false;
						}
					}
				}

				if (key == KeyEvent.VK_E) {
					if (game.getCurrent().isMoving()) {
						int r = JOptionPane.showConfirmDialog(game.getFrame(),
								new JLabel("You still have moves left!"),
								"Can't do that", JOptionPane.CLOSED_OPTION,
								JOptionPane.WARNING_MESSAGE);
						if (r == 0) {
							return false;
						}
					}

					if (game.getCurrent().hasMoved()) {
						game.getCurrent().resetBooleans();
						game.setPlayingKeeper(false);
						game.nextPlayer();
					} else {
						int r = JOptionPane.showConfirmDialog(game.getFrame(),
								new JLabel("You haven't rolled yet!"),
								"Can't do that", JOptionPane.CLOSED_OPTION,
								JOptionPane.WARNING_MESSAGE);
						if (r == 0) {
							return false;
						}
					}
				}

				if (key == KeyEvent.VK_K) {
					if (game.getCurrent().getUsedKeeper()) {
						int r = JOptionPane.showConfirmDialog(game.getFrame(),
								new JLabel("You have already used a keeper card this turn!"),
								"Can't do that", JOptionPane.CLOSED_OPTION,
								JOptionPane.WARNING_MESSAGE);
						if (r == 0) {
							return false;
						}
						return false;
					}
					new KeeperFrame(game.getCurrent());
				}

				if (key == KeyEvent.VK_N) {
					new NoteBookFrame(game.getCurrent());
				}

				if (game.getCurrent().isMoving()) {
					if (key == KeyEvent.VK_UP || key == KeyEvent.VK_KP_UP) {
						game.getCurrent().keyMove(1);
					} else if (key == KeyEvent.VK_DOWN
							|| key == KeyEvent.VK_KP_DOWN) {
						game.getCurrent().keyMove(2);
					} else if (key == KeyEvent.VK_LEFT
							|| key == KeyEvent.VK_KP_LEFT) {
						game.getCurrent().keyMove(3);
					} else if (key == KeyEvent.VK_RIGHT
							|| key == KeyEvent.VK_KP_RIGHT) {
						game.getCurrent().keyMove(4);
					}
				}
			}
			return false;
		}

	}
}
