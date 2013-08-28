package window;

import game.Game;
import game.Player;
import game.squares.Square;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SendHome extends JFrame {

	private Game game;

	public SendHome(Game g) {
		this.game = g;
		setSize(1000, 500);

		// For Centering frame in the middle of the screen.
		// ----------------------------------------------------------
		// Get the size of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		// Determine the new location of the window
		int width = getSize().width;
		int height = getSize().height;
		int x = (dim.width - width) / 2;
		int y = (dim.height - height) / 2;

		// Move the window
		setLocation(x, y);
		// ----------------------------------------------------------

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout());
		buttonPane.setPreferredSize(new Dimension(1000, 250));
		buttonPane.setBackground(new Color(139, 90, 0));

		// Makes the icons
		Icon i1 = new ImageIcon("src/Characters/Peacock.jpg");
		Icon i2 = new ImageIcon("src/Characters/Plum.jpg");
		Icon i3 = new ImageIcon("src/Characters/Green.jpg");
		Icon i4 = new ImageIcon("src/Characters/White.jpg");
		Icon i5 = new ImageIcon("src/Characters/Mustard.jpg");
		Icon i6 = new ImageIcon("src/Characters/Scarlett.jpg");

		// Makes the buttons
		JButton peacock = new JButton(i1);
		peacock.setBackground(new Color(0, 100, 0));
		peacock.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Square home = game.getBoard().getBoard()[0][6];
				for (Player p : game.getPlayers()) {
					if (p.getCharacter().getName().equals("Eleanor Peacock")) {
						p.moveTo(home);
						p.resetBooleans();
						dispose();
						game.getFrame().repaint();
					}
				}
			}
		});

		JButton plum = new JButton(i2);
		plum.setBackground(new Color(0, 100, 0));
		plum.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Square home = game.getBoard().getBoard()[0][20];
				for (Player p : game.getPlayers()) {
					if (p.getCharacter().getName().equals("Victor Plum")) {
						p.moveTo(home);
						p.resetBooleans();
						dispose();
						game.getFrame().repaint();
					}
				}
			}
		});

		JButton green = new JButton(i3);
		green.setBackground(new Color(0, 100, 0));
		green.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Square home = game.getBoard().getBoard()[9][0];
				for (Player p : game.getPlayers()) {
					if (p.getCharacter().getName().equals("Jacob Green")) {
						p.moveTo(home);
						p.resetBooleans();
						dispose();
						game.getFrame().repaint();
					}
				}
			}
		});

		JButton white = new JButton(i4);
		white.setBackground(new Color(0, 100, 0));
		white.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Square home = game.getBoard().getBoard()[19][0];
				for (Player p : game.getPlayers()) {
					if (p.getCharacter().getName().equals("Diane White")) {
						p.moveTo(home);
						p.resetBooleans();
						dispose();
						game.getFrame().repaint();
					}
				}
			}
		});

		JButton mustard = new JButton(i5);
		mustard.setBackground(new Color(0, 100, 0));
		mustard.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Square home = game.getBoard().getBoard()[28][7];
				for (Player p : game.getPlayers()) {
					if (p.getCharacter().getName().equals("Jack Mustard")) {
						p.moveTo(home);
						p.resetBooleans();
						dispose();
						game.getFrame().repaint();
					}
				}
			}
		});

		JButton scarlett = new JButton(i6);
		scarlett.setBackground(new Color(0, 100, 0));
		scarlett.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Square home = game.getBoard().getBoard()[28][18];
				for (Player p : game.getPlayers()) {
					if (p.getCharacter().getName().equals("Kasandra Scarlett")) {
						p.moveTo(home);
						p.resetBooleans();
						dispose();
						game.getFrame().repaint();
					}
				}
			}
		});

		for (Player p : game.getPlayers()) {
			if (p.isPlaying()) {
				if (game.getCurrent() == p) {
					continue;
				}

				if (p.getCharacter().getName().equals("Eleanor Peacock")) {
					buttonPane.add(peacock);

				} else if (p.getCharacter().getName().equals("Victor Plum")) {
					buttonPane.add(plum);

				} else if (p.getCharacter().getName().equals("Jacob Green")) {
					buttonPane.add(green);

				} else if (p.getCharacter().getName().equals("Diane White")) {
					buttonPane.add(white);

				} else if (p.getCharacter().getName().equals("Jack Mustard")) {
					buttonPane.add(mustard);

				} else if (p.getCharacter().getName()
						.equals("Kasandra Scarlett")) {
					buttonPane.add(scarlett);

				}
			}
		}

		JPanel south = new JPanel();
		south.setBackground(new Color(139, 90, 0));
		south.setPreferredSize(new Dimension(1000, 100));

		JLabel text = new JLabel("Choose a Player to Send to Start Square:");
		text.setFont(new Font("Serif", Font.BOLD, 40));
		text.setForeground(Color.WHITE);
		text.setSize(100, 30);

		south.add(text, BorderLayout.CENTER);
		add(south, BorderLayout.CENTER);

		add(buttonPane, BorderLayout.NORTH);
		setVisible(true);
		pack();
	}

}
