package window;

import game.Player;
import game.cards.Keeper;

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
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class KeeperFrame extends JFrame {

    private JPanel buttonPanel = new JPanel();
    private ButtonGroup group = new ButtonGroup();
    private JButton play;

    private Player p;
    private Keeper keeper;
    private List<Keeper> list;

    public KeeperFrame(final Player p) {
        this.p = p;

        setTitle(p.getCharacter().getName() + "\'s Keeper cards");
        setSize(new Dimension(400, 400));
        setMinimumSize(new Dimension(400, 400));
        setLayout(new BorderLayout());
        GridLayout grid = new GridLayout(11, 2);
        buttonPanel.setLayout(grid);

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

        play = new JButton("Play Keeper card");
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(keeper == null){
                    return;
                }

                p.getGame().setPlayingKeeper(true);
                p.executeKeeper(keeper);
                dispose();
                p.setUsedKeeper(true);
            }
        });

        makeButtons(p);
        add(buttonPanel, BorderLayout.CENTER);
        add(play, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    private void makeButtons(Player p) {
        list = p.getKeepers();

        for (Keeper k : list) {
            if (k.getType().equals("Roll")) {
                JRadioButton b = new JRadioButton(k.getType() + " : "
                        + k.getRoll());
                b.addItemListener(new ItmListener());
                group.add(b);
                buttonPanel.add(b);
            } else {
                JRadioButton b = new JRadioButton(k.toString());
                b.addItemListener(new ItmListener());
                group.add(b);
                buttonPanel.add(b);
            }
        }
    }

    private class ItmListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (((JRadioButton)e.getSource()).getText().equals("Roll : 1")) {
                if(p.hasMoved()){
                    return;
                }
                for (Keeper k : list) {
                    if (k.getType().equals("Roll")) {
                        if (k.getRoll() == 1) {
                            keeper = k;
                        }
                    }
                }
            } else if (((JRadioButton)e.getSource()).getText().equals("Roll : 2")) {
                if(p.hasMoved()){
                    return;
                }
                for (Keeper k : list) {
                    if (k.getType().equals("Roll")) {
                        if (k.getRoll() == 2) {
                            keeper = k;
                        }
                    }
                }
            } else if (((JRadioButton)e.getSource()).getText().equals("Roll : 3")) {
                if(p.hasMoved()){
                    return;
                }
                for (Keeper k : list) {
                    if (k.getType().equals("Roll")) {
                        if (k.getRoll() == 3) {
                            keeper = k;
                        }
                    }
                }
            } else if (((JRadioButton)e.getSource()).getText().equals("Roll : 4")) {
                if(p.hasMoved()){
                    return;
                }
                for (Keeper k : list) {
                    if (k.getType().equals("Roll")) {
                        if (k.getRoll() == 4) {
                            keeper = k;
                        }
                    }
                }
            } else if (((JRadioButton)e.getSource()).getText().equals("Roll : 5")) {
                if(p.hasMoved()){
                    return;
                }
                for (Keeper k : list) {
                    if (k.getType().equals("Roll")) {
                        if (k.getRoll() == 5) {
                            keeper = k;
                        }
                    }
                }
            } else if (((JRadioButton)e.getSource()).getText().equals("Roll : 6")) {
                if(p.hasMoved()){
                    return;
                }
                for (Keeper k : list) {
                    if (k.getType().equals("Roll")) {
                        if (k.getRoll() == 6) {
                            keeper = k;
                        }
                    }
                }
            } else if (((JRadioButton)e.getSource()).getText().equals("Roll : 7")) {
                if(p.hasMoved()){
                    return;
                }
                for (Keeper k : list) {
                    if (k.getType().equals("Roll")) {
                        if (k.getRoll() == 7) {
                            keeper = k;
                        }
                    }
                }
            } else if (((JRadioButton)e.getSource()).getText().equals("Roll : 8")) {
                if(p.hasMoved()){
                    return;
                }
                for (Keeper k : list) {
                    if (k.getType().equals("Roll")) {
                        if (k.getRoll() == 8) {
                            keeper = k;
                        }
                    }
                }
            } else if (((JRadioButton)e.getSource()).getText().equals("Roll : 9")) {
                if(p.hasMoved()){
                    return;
                }
                for (Keeper k : list) {
                    if (k.getType().equals("Roll")) {
                        if (k.getRoll() == 9) {
                            keeper = k;
                        }
                    }
                }
            } else if (((JRadioButton)e.getSource()).getText().equals("Roll : 10")) {
                if(p.hasMoved()){
                    return;
                }
                for (Keeper k : list) {
                    if (k.getType().equals("Roll")) {
                        if (k.getRoll() == 10) {
                            keeper = k;
                        }
                    }
                }
            } else if (((JRadioButton)e.getSource()).getText().equals("Move to Any Location")) {
                for (Keeper k : list) {
                    if (k.getType().equals("Move")) {
                        keeper = k;
                        break;
                    }
                }
            } else if (((JRadioButton)e.getSource()).getText().equals("Send Player back to Start Square")) {
                for (Keeper k : list) {
                    if (k.getType().equals("Start")) {
                        keeper = k;
                        break;
                    }
                }
            } else if (((JRadioButton)e.getSource()).getText().equals("Avoid a Guess")) {
                for (Keeper k : list) {
                    if (k.getType().equals("Avoid")) {
                        break;
                    }
                }
            } else if (((JRadioButton)e.getSource()).getText().equals("Make a Guess")) {
                for (Keeper k : list) {
                    if (k.getType().equals("Make")) {
                        keeper = k;
                        break;
                    }
                }
            } else if (((JRadioButton)e.getSource()).getText().equals("Take an Extra Turn")) {
                if(!p.hasMoved()){
                    return;
                }

                for (Keeper k : list) {
                    if (k.getType().equals("Turn")) {
                        keeper = k;
                        break;
                    }
                }
            } else if (((JRadioButton)e.getSource()).getText().equals("See Another Player's Card")) {
                for (Keeper k : list) {
                    if (k.getType().equals("See")) {
                        keeper = k;
                        break;
                    }
                }
            }

        }

    }
}
