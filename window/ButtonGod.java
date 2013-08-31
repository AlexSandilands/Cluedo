package window;

import game.Game;
import game.squares.NullSquare;
import game.squares.Square;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class ButtonGod {

    private JButton noteBook;
    private JButton keepers;
    private JButton guess;
    private JButton accuse;
    private JButton end;

    private JPanel rollPanel;
    private JTextField rollValue;
    private int rollNum;

    private JPanel grid;

    private JPanel right;

    // For the game itself
    private Game game;

    public ButtonGod(Game game) {
        this.game = game;

        UIManager.put("Button.foreground", Color.WHITE);
        noteBook = new JButton("Review Notebook");
        keepers = new JButton("Keeper Cards");
        guess = new JButton("Guess");
        accuse = new JButton("Accuse");
        end = new JButton("End Turn");

        noteBook.setBackground(new Color(0, 100, 0));
        noteBook.addActionListener(new ButtonListener());

        keepers.setBackground(new Color(0, 100, 0));
        keepers.addActionListener(new ButtonListener());

        guess.setBackground(new Color(0, 100, 0));
        guess.addActionListener(new ButtonListener());

        accuse.setBackground(new Color(0, 100, 0));
        accuse.addActionListener(new ButtonListener());

        end.setBackground(new Color(0, 100, 0));
        end.addActionListener(new ButtonListener());

        noteBook.setPreferredSize(new Dimension(170, 40));
        keepers.setPreferredSize(new Dimension(170, 40));
        guess.setPreferredSize(new Dimension(170, 40));
        accuse.setPreferredSize(new Dimension(170, 40));
        end.setPreferredSize(new Dimension(170, 40));

        GridLayout layout = new GridLayout(9, 1);
        layout.setVgap(7);

        grid = new JPanel(layout);
        grid.setBackground(new Color(139, 90, 0));

        grid.add(noteBook, BorderLayout.CENTER);
        grid.add(keepers, BorderLayout.CENTER);
        grid.add(guess, BorderLayout.CENTER);
        grid.add(accuse, BorderLayout.CENTER);
        grid.add(end, BorderLayout.CENTER);

        UIManager.put("Button.foreground", Color.BLACK);

        right = new JPanel(new BorderLayout());
        right.add(grid, BorderLayout.NORTH);
        right.add(createRollPanel(), BorderLayout.SOUTH);
    }

    public JPanel getButtonPanel() {
        return right;
    }

    private JPanel createRollPanel() {
        rollPanel = new JPanel(new BorderLayout());
        rollPanel.setSize(new Dimension(150, 150));
        rollPanel.setBackground(new Color(139, 90, 0));

        Icon dice = new ImageIcon("src/Data/Dice.png");
        JButton roll = new JButton(dice);

        rollValue = new JTextField(2);
        rollValue.setFont(new Font("Serif", Font.BOLD, 30));
        rollValue.setHorizontalAlignment(JTextField.CENTER);

        // What the dice button does when you press it.
        roll.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // cannot press it again if they are in the middle of a move
                if (game.getCurrent().isMoving()) {
                    return;
                }

                // Cannot press it again if they have already moved
                if (game.getCurrent().hasMoved()) {
                    int r = JOptionPane.showConfirmDialog(game.getFrame(),
                            new JLabel("You have already rolled this turn!"),
                            "Can't do that", JOptionPane.CLOSED_OPTION,
                            JOptionPane.WARNING_MESSAGE);
                    if (r == 0) {
                        return;
                    }
                }

                rollNum = game.getCurrent().roll();
                rollValue.setText(rollNum + "");

                // If they are in a room, they will first be giving options to
                // leave the room.
                if (game.getCurrent().inRoom()) {
                    List<Square> moveSquares = game.getCurrent().leaveRoom();
                    if (moveSquares.isEmpty()) {
                        return;
                    } else {
                        game.setMoveToSquares(moveSquares);
                    }

                } else {
                    game.getCurrent().setMoving(true);
                }

            }
        });

        roll.setBackground(new Color(139, 90, 0));
        roll.setPreferredSize(new Dimension(108, 108));

        rollPanel.add(roll, BorderLayout.NORTH);

        rollValue.setEditable(false);
        rollPanel.add(rollValue, BorderLayout.CENTER);

        return rollPanel;
    }

    public void setRoll(int i){
        rollNum = i;
        rollValue.setText(rollNum + "");
    }

    public void addToRoll(int i) {
        rollNum += i;
        rollValue.setText(rollNum + "");
    }

    public void decreaseRoll() {
        rollValue.setText(--rollNum + "");
    }

    public void clearRoll() {
        rollValue.setText("0");
    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            // When they press the Review Notebook button
            // Opens a new frame with the current players notebook
            if (e.getActionCommand().equals("Review Notebook")) {
                new NoteBookFrame(game.getCurrent());
            }

            else if (e.getActionCommand().equals("Keeper Cards")) {
                if (game.getCurrent().getUsedKeeper()) {
                    int r = JOptionPane.showConfirmDialog(game.getFrame(),
                            new JLabel("You have already used a keeper card this turn!"),
                            "Can't do that", JOptionPane.CLOSED_OPTION,
                            JOptionPane.WARNING_MESSAGE);
                    if (r == 0) {
                        return;
                    }
                    return;
                }
                new KeeperFrame(game.getCurrent());
            }

            // When the user presses the Guess button
            // Open the Guess frame, which lets them choose 3 items etc
            else if (e.getActionCommand().equals("Guess")) {
                if (game.getCurrent().hasGuessed()) {
                    int r = JOptionPane.showConfirmDialog(game.getFrame(),
                            new JLabel("You have already guessed this turn!"),
                            "Can't do that", JOptionPane.CLOSED_OPTION,
                            JOptionPane.WARNING_MESSAGE);
                    if (r == 0) {
                        return;
                    }
                    return;
                }

                if (game.getCurrent().inRoom()) {
                    new GuessFrame(game);
                } else {
                    int r = JOptionPane.showConfirmDialog(game.getFrame(),
                            new JLabel("You must be in a room to guess!"),
                            "Can't do that", JOptionPane.CLOSED_OPTION,
                            JOptionPane.WARNING_MESSAGE);
                    if (r == 0) {
                        return;
                    }
                }
            }

            else if (e.getActionCommand().equals("Accuse")) {
                if (game.getCurrent().inRoom()) {
                    NullSquare ns = (NullSquare) game.getCurrent().getOn();

                    if (ns.getRoomSquare().getRoom().getName().equals("Pool")) {
                        new AccuseFrame(game);
                    } else {
                        int r = JOptionPane
                                .showConfirmDialog(game.getFrame(), new JLabel(
                                        "You must be in the Pool to accuse!"),
                                        "Can't do that",
                                        JOptionPane.CLOSED_OPTION,
                                        JOptionPane.WARNING_MESSAGE);
                        if (r == 0) {
                            return;
                        }
                    }

                } else {
                    int r = JOptionPane.showConfirmDialog(game.getFrame(),
                            new JLabel("You must be in the Pool to accuse!"),
                            "Can't do that", JOptionPane.CLOSED_OPTION,
                            JOptionPane.WARNING_MESSAGE);
                    if (r == 0) {
                        return;
                    }
                }
            }

            // Player ends their turn, the current player moves on to the next
            // player
            else if (e.getActionCommand().equals("End Turn")) {
                if (game.getCurrent().isMoving()) {
                    int r = JOptionPane.showConfirmDialog(game.getFrame(),
                            new JLabel("You still have moves left!"),
                            "Can't do that", JOptionPane.CLOSED_OPTION,
                            JOptionPane.WARNING_MESSAGE);
                    if (r == 0) {
                        return;
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
                        return;
                    }
                }
            }
        }

    }
}
