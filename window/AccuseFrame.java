package window;

import game.Game;
import game.objects.GameCharacter;
import game.objects.Room;
import game.objects.Weapon;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class AccuseFrame extends JFrame {

	private ButtonGroup weapons;
	private ButtonGroup rooms;
	private ButtonGroup gameCharacters;
	private JPanel wPanel = new JPanel(new GridLayout(9, 1));
	private JPanel rPanel = new JPanel(new GridLayout(9, 1));
	private JPanel cPanel = new JPanel(new GridLayout(6, 1));

	private Game game;

	// The items Accused
	private Weapon w;
	private Room r;
	private GameCharacter gc;

	public AccuseFrame(final Game game) {
		this.game = game;
		this.weapons = new ButtonGroup();
		this.rooms = new ButtonGroup();
		this.gameCharacters = new ButtonGroup();
		setSize(500, 400);
		setMinimumSize(new Dimension(500, 400));

		setTitle("Make an Accusation");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridLayout grid = new GridLayout(1, 2);

		// For Centering frame in the middle of the screen.
		// ----------------------------------------------------------
		// Get the size of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		// Determine the new location of the window
		int wid = this.getSize().width;
		int h = this.getSize().height;

		int x = (dim.width / 2 - wid / 2);
		int y = (dim.height / 2 - h / 2);

		// Move the window
		setLocation(x, y);
		// ----------------------------------------------------------
		makeWeapons();
		makeCharacters();
		makeRooms();

		JPanel tmp = new JPanel();
		tmp.setLayout(grid);
		setLayout(new BorderLayout());
		tmp.add(cPanel);
		tmp.add(rPanel);
		tmp.add(wPanel);
		add(tmp, BorderLayout.CENTER);

		// Make the Accuse button
		JButton accuse = new JButton("Accuse");
		accuse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (w == null || gc == null || r == null) {
					int r = JOptionPane
							.showConfirmDialog(
									wPanel,
									new JLabel(
											"You must select 3 items before you can make an accusation!"),
									"Can't do that!",
									JOptionPane.CLOSED_OPTION,
									JOptionPane.WARNING_MESSAGE);

					if (r == 0) {
						return;
					}
				}

				dispose();
				game.getCurrent().executeAccuse(w, r, gc);

			}
		});

		add(accuse, BorderLayout.SOUTH);
		pack();
		setVisible(true);
	}

	/*
	 * A method that creates the radio buttons for the weapons part of the frame
	 */
	private void makeWeapons() {
		JRadioButton rope = new JRadioButton("Rope");
		rope.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				List<Weapon> list = game.getBoard().getWeapons();
				for (Weapon weap : list) {
					if (weap.getName().equals("Rope")) {
						w = weap;
					}
				}
			}
		});
		this.weapons.add(rope);
		this.wPanel.add(rope);

		JRadioButton candlestick = new JRadioButton("Candlestick");
		candlestick.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				List<Weapon> list = game.getBoard().getWeapons();
				for (Weapon weap : list) {
					if (weap.getName().equals("Candlestick")) {
						w = weap;
					}
				}
			}
		});
		this.weapons.add(candlestick);
		this.wPanel.add(candlestick);

		JRadioButton knife = new JRadioButton("Knife");
		knife.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				List<Weapon> list = game.getBoard().getWeapons();
				for (Weapon weap : list) {
					if (weap.getName().equals("Knife")) {
						w = weap;
					}
				}
			}
		});
		this.weapons.add(knife);
		this.wPanel.add(knife);

		JRadioButton pistol = new JRadioButton("Pistol");
		pistol.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				List<Weapon> list = game.getBoard().getWeapons();
				for (Weapon weap : list) {
					if (weap.getName().equals("Pistol")) {
						w = weap;
					}
				}
			}
		});
		this.weapons.add(pistol);
		this.wPanel.add(pistol);

		JRadioButton baseballBat = new JRadioButton("Baseball Bat");
		baseballBat.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				List<Weapon> list = game.getBoard().getWeapons();
				for (Weapon weap : list) {
					if (weap.getName().equals("Baseball_Bat")) {
						w = weap;
					}
				}
			}
		});
		this.weapons.add(baseballBat);
		this.wPanel.add(baseballBat);

		JRadioButton dumbbell = new JRadioButton("Dumbbell");
		dumbbell.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				List<Weapon> list = game.getBoard().getWeapons();
				for (Weapon weap : list) {
					if (weap.getName().equals("Dumbbell")) {
						w = weap;
					}
				}
			}
		});
		this.weapons.add(dumbbell);
		this.wPanel.add(dumbbell);

		JRadioButton trophy = new JRadioButton("Trophy");
		trophy.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				List<Weapon> list = game.getBoard().getWeapons();
				for (Weapon weap : list) {
					if (weap.getName().equals("Trophy")) {
						w = weap;
					}
				}
			}
		});
		this.weapons.add(trophy);
		this.wPanel.add(trophy);

		JRadioButton poison = new JRadioButton("Poison");
		poison.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				List<Weapon> list = game.getBoard().getWeapons();
				for (Weapon weap : list) {
					if (weap.getName().equals("Poison")) {
						w = weap;
					}
				}
			}
		});
		this.weapons.add(poison);
		this.wPanel.add(poison);

		JRadioButton axe = new JRadioButton("Axe");
		axe.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				List<Weapon> list = game.getBoard().getWeapons();
				for (Weapon weap : list) {
					if (weap.getName().equals("Axe")) {
						w = weap;
					}
				}
			}
		});
		this.weapons.add(axe);
		this.wPanel.add(axe);
	}

	/*
	 * A method that creates the radio buttons for the rooms part of the frame
	 */
	private void makeRooms() {
		JRadioButton spa = new JRadioButton("Spa");
		spa.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				List<Room> list = game.getBoard().getRooms();
				for (Room room : list) {
					if (room.getName().equals("Spa")) {
						r = room;
					}
				}
			}
		});
		this.rooms.add(spa);
		this.rPanel.add(spa);

		JRadioButton living = new JRadioButton("Living Room");
		living.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				List<Room> list = game.getBoard().getRooms();
				for (Room room : list) {
					if (room.getName().equals("Living-Room")) {
						r = room;
					}
				}
			}
		});
		this.rooms.add(living);
		this.rPanel.add(living);

		JRadioButton theater = new JRadioButton("Theater");
		theater.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				List<Room> list = game.getBoard().getRooms();
				for (Room room : list) {
					if (room.getName().equals("Theater")) {
						r = room;
					}
				}
			}
		});
		this.rooms.add(theater);
		this.rPanel.add(theater);

		JRadioButton observatory = new JRadioButton("Observatory");
		observatory.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				List<Room> list = game.getBoard().getRooms();
				for (Room room : list) {
					if (room.getName().equals("Observatory")) {
						r = room;
					}
				}
			}
		});
		this.rooms.add(observatory);
		this.rPanel.add(observatory);

		JRadioButton patio = new JRadioButton("Patio");
		patio.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				List<Room> list = game.getBoard().getRooms();
				for (Room room : list) {
					if (room.getName().equals("Patio")) {
						r = room;
					}
				}
			}
		});
		this.rooms.add(patio);
		this.rPanel.add(patio);

		JRadioButton hall = new JRadioButton("Hall");
		hall.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				List<Room> list = game.getBoard().getRooms();
				for (Room room : list) {
					if (room.getName().equals("Hall")) {
						r = room;
					}
				}
			}
		});
		this.rooms.add(hall);
		this.rPanel.add(hall);

		JRadioButton Kitchen = new JRadioButton("Kitchen");
		Kitchen.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				List<Room> list = game.getBoard().getRooms();
				for (Room room : list) {
					if (room.getName().equals("Kitchen")) {
						r = room;
					}
				}
			}
		});
		this.rooms.add(Kitchen);
		this.rPanel.add(Kitchen);

		JRadioButton dining = new JRadioButton("Dining Room");
		dining.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				List<Room> list = game.getBoard().getRooms();
				for (Room room : list) {
					if (room.getName().equals("Dining-Room")) {
						r = room;
					}
				}
			}
		});
		this.rooms.add(dining);
		this.rPanel.add(dining);

		JRadioButton guest = new JRadioButton("Guest House");
		guest.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				List<Room> list = game.getBoard().getRooms();
				for (Room room : list) {
					if (room.getName().equals("Guest-House")) {
						r = room;
					}
				}
			}
		});
		this.rooms.add(guest);
		this.rPanel.add(guest);
	}

	/*
	 * A method that creates the radio buttons for the characters part of the
	 * frame
	 */
	private void makeCharacters() {
		JRadioButton eleanor = new JRadioButton("Eleanor Peacock");
		eleanor.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				List<GameCharacter> list = game.getBoard().getCharacters();
				for (GameCharacter g : list) {
					if (g.getName().equals("Eleanor Peacock")) {
						gc = g;
					}
				}
			}
		});
		this.gameCharacters.add(eleanor);
		this.cPanel.add(eleanor);

		JRadioButton victor = new JRadioButton("Victor Plum");
		victor.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				List<GameCharacter> list = game.getBoard().getCharacters();
				for (GameCharacter g : list) {
					if (g.getName().equals("Victor Plum")) {
						gc = g;
					}
				}
			}
		});
		this.gameCharacters.add(victor);
		this.cPanel.add(victor);

		JRadioButton jacob = new JRadioButton("Jacob Green");
		jacob.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				List<GameCharacter> list = game.getBoard().getCharacters();
				for (GameCharacter g : list) {
					if (g.getName().equals("Jacob Green")) {
						gc = g;
					}
				}
			}
		});
		this.gameCharacters.add(jacob);
		this.cPanel.add(jacob);

		JRadioButton diane = new JRadioButton("Diane White");
		diane.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				List<GameCharacter> list = game.getBoard().getCharacters();
				for (GameCharacter g : list) {
					if (g.getName().equals("Diane White")) {
						gc = g;
					}
				}
			}
		});
		this.gameCharacters.add(diane);
		this.cPanel.add(diane);

		JRadioButton jack = new JRadioButton("Jack Mustard");
		jack.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				List<GameCharacter> list = game.getBoard().getCharacters();
				for (GameCharacter g : list) {
					if (g.getName().equals("Jack Mustard")) {
						gc = g;
					}
				}
			}
		});
		this.gameCharacters.add(jack);
		this.cPanel.add(jack);

		JRadioButton kasandra = new JRadioButton("Kasandra Scarlett");
		kasandra.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				List<GameCharacter> list = game.getBoard().getCharacters();
				for (GameCharacter g : list) {
					if (g.getName().equals("Kasandra Scarlett")) {
						gc = g;
					}
				}
			}
		});
		this.gameCharacters.add(kasandra);
		this.cPanel.add(kasandra);
	}
}
