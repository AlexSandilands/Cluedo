package window;

import game.Player;
import game.cards.GameItemCard;
import game.objects.GameCharacter;
import game.objects.Room;
import game.objects.Weapon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class ExecuteGuessFrame extends JFrame {

    // Fields for the guess
    private Weapon w;
    private Room r;
    private GameCharacter gc;
    private Player p;

    // Booleans about if the player has the cards
    private boolean weap;
    private boolean room;
    private boolean gChar;

    // For the GUI
    private JPanel buttonPanel;
    private JPanel labelPanel;
    private JLabel chooseLabel;

    public ExecuteGuessFrame(Weapon w, Room r, GameCharacter gc, Player p) {
        super(p.getCharacter().getName());

        this.w = w;
        this.r = r;
        this.gc = gc;
        this.p = p;

        setSize(500, 300);
        setMinimumSize(new Dimension(500, 300));

        // For Centering frame in the middle of the screen.
        // ----------------------------------------------------------
        // Get the size of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Determine the new location of the window
        int width = this.getSize().width;
        int hight = this.getSize().height;
        int x = (dim.width - width) / 2;
        int y = (dim.height - hight) / 2;

        // Move the window
        setLocation(x, y);
        // ----------------------------------------------------------

        createButtons();
        createLabels();

        setVisible(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        pack();
    }

    private void createButtons() {
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(139, 90, 0));
        buttonPanel.setPreferredSize(new Dimension(500, 220));

        // Find the cards the player has corresponding to the guess
        for (GameItemCard card : p.getCards()) {
            if (card == null) {
                continue;
            }

            if (card.getItem().equals(w)) {
                weap = true;
            } else if (card.getItem().equals(r)) {
                room = true;
            } else if (card.getItem().equals(gc)) {
                gChar = true;
            }
        }

        UIManager.put("Button.foreground", Color.WHITE);

        JButton weapon = null;
        JButton rm = null;
        JButton gameChar = null;

        if (weap) {
            weapon = new JButton(w.getIcon());
            weapon.setText("Weapon");
            weapon.setVerticalTextPosition(SwingConstants.BOTTOM);
            weapon.setHorizontalTextPosition(SwingConstants.CENTER);

            weapon.setBackground(new Color(0, 100, 0));
            weapon.addActionListener(new ButtonListener());
            buttonPanel.add(weapon);
        }

        if (room) {
            rm = new JButton(r.getIcon());
            rm.setText("Room");
            rm.setVerticalTextPosition(SwingConstants.BOTTOM);
            rm.setHorizontalTextPosition(SwingConstants.CENTER);

            rm.setBackground(new Color(0, 100, 0));
            rm.addActionListener(new ButtonListener());
            buttonPanel.add(rm);
        }

        if (gChar) {
            gameChar = new JButton(gc.getIcon());
            gameChar.setText("Character");
            gameChar.setVerticalTextPosition(SwingConstants.BOTTOM);
            gameChar.setHorizontalTextPosition(SwingConstants.CENTER);

            gameChar.setBackground(new Color(0, 100, 0));
            gameChar.addActionListener(new ButtonListener());
            buttonPanel.add(gameChar);
        }

        UIManager.put("Button.foreground", Color.BLACK);
        add(buttonPanel, BorderLayout.NORTH);
    }

    private void createLabels() {
        labelPanel = new JPanel(new BorderLayout());
        labelPanel.setBackground(new Color(139, 90, 0));

        chooseLabel = new JLabel("Choose a card to show "
                + p.getGame().getCurrent().getCharacter().getName());
        chooseLabel.setForeground(Color.WHITE);
        chooseLabel.setFont(new Font("Serif", Font.BOLD, 30));
        chooseLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel chooser = new JLabel(p.getCharacter().getName() + ":");
        chooser.setForeground(Color.WHITE);
        chooser.setFont(new Font("Serif", Font.BOLD, 40));
        chooser.setHorizontalAlignment(JLabel.CENTER);

        labelPanel.add(chooseLabel, BorderLayout.SOUTH);
        labelPanel.add(chooser, BorderLayout.NORTH);
        add(labelPanel, BorderLayout.CENTER);
    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("Weapon")){
                p.getGame().getCurrent().updateNoteBook(w, true);
            } else if(e.getActionCommand().equals("Room")){
                p.getGame().getCurrent().updateNoteBook(r, true);
            } else if(e.getActionCommand().equals("Character")){
                p.getGame().getCurrent().updateNoteBook(gc, true);
            } else {
                throw new Error("Button Not recognised");
            }

            dispose();
        }

    }
}
