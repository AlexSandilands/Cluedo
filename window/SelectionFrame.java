package window;

import game.Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.UIManager;

public class SelectionFrame extends JFrame {

    private Game game;
    private int count = 1;
    private int numPlayers;

    // For the character selection frame
    private JLabel text;
    private JPanel south;

    // For the choosing number of players frame
    private JPanel north;
    private JPanel buttons;

    public SelectionFrame() {
        super("Select Characters");

        setSize(1000, 500);
        chooseNumPlayers();

        // Get the size of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Determine the new location of the window
        int w = this.getSize().width;
        int h = this.getSize().height;

        int x = (dim.width/2 - w/2);
        int y = (dim.height/2 - h/2);

        // Move the window
        setLocation(x, y);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        pack();
    }

    public SelectionFrame(Game g) {
        super("Select Characters");
        this.game = g;

        setSize(1000, 500);
        chooseNumPlayers();

        // Get the size of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Determine the new location of the window
        int w = this.getSize().width;
        int h = this.getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;

        // Move the window
        setLocation(x, y);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        pack();
    }

    public void chooseNumPlayers() {
        north = new JPanel(new BorderLayout());
        north.setBackground(new Color(139, 90, 0));
        north.setPreferredSize(new Dimension(1000, 300));

        JLabel lab = new JLabel("Welcome to Cluedo!");
        lab.setFont(new Font("Serif", Font.BOLD, 50));
        lab.setForeground(Color.WHITE);
        lab.setHorizontalAlignment(JLabel.CENTER);
        lab.setSize(100, 30);

        JLabel lab2 = new JLabel("How Many People Are Playing:");
        lab2.setFont(new Font("Serif", Font.BOLD, 40));
        lab2.setForeground(Color.WHITE);
        lab2.setHorizontalAlignment(JLabel.CENTER);
        lab2.setSize(100, 30);

        north.add(lab, BorderLayout.NORTH);
        north.add(lab2, BorderLayout.CENTER);

        buttons = new JPanel(new FlowLayout());
        buttons.setBackground(new Color(139, 90, 0));
        buttons.setSize(650, 100);

        UIManager.put("Button.foreground", Color.WHITE);
        JButton three = new JButton("Three");
        three.addActionListener(new pButtonListener(3));
        three.setBackground(new Color(0, 100, 0));
        three.setPreferredSize(new Dimension(100, 50));
        buttons.add(three);

        JButton four = new JButton("Four");
        four.addActionListener(new pButtonListener(4));
        four.setBackground(new Color(0, 100, 0));
        four.setPreferredSize(new Dimension(100, 50));
        buttons.add(four);

        JButton five = new JButton("Five");
        five.addActionListener(new pButtonListener(5));
        five.setBackground(new Color(0, 100, 0));
        five.setPreferredSize(new Dimension(100, 50));
        buttons.add(five);

        JButton six = new JButton("Six");
        six.addActionListener(new pButtonListener(6));
        six.setBackground(new Color(0, 100, 0));
        six.setPreferredSize(new Dimension(100, 50));
        buttons.add(six);

        add(north, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    public void makeCharacterFrame() {
        remove(north);
        remove(buttons);
        repaint();
        this.resize(new Dimension(1000, 500));

        charSelect();
        southPane();
        repaint();
    }

    public void southPane() {
        south = new JPanel();
        south.setBackground(new Color(139, 90, 0));
        south.setPreferredSize(new Dimension(1000, 100));

        text = new JLabel("Player " + count + ": Choose Character");
        text.setFont(new Font("Serif", Font.BOLD, 40));
        text.setForeground(Color.WHITE);
        text.setSize(100, 30);

        south.add(text, BorderLayout.CENTER);
        add(south, BorderLayout.CENTER);
    }

    public void setLabel(int i) {
        if (i == numPlayers + 1) {
            south.remove(text);
            south.repaint();
            repaint();

            this.dispose();
            game.playGame();
            return;
        }
        text.setText("Player " + count + ": Choose Character");
        south.repaint();
        repaint();
    }

    public void charSelect() {
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
        peacock.addActionListener(new CharButtonListener(1, buttonPane));

        JButton plum = new JButton(i2);
        plum.setBackground(new Color(0, 100, 0));
        plum.addActionListener(new CharButtonListener(2, buttonPane));

        JButton green = new JButton(i3);
        green.setBackground(new Color(0, 100, 0));
        green.addActionListener(new CharButtonListener(3, buttonPane));

        JButton white = new JButton(i4);
        white.setBackground(new Color(0, 100, 0));
        white.addActionListener(new CharButtonListener(4, buttonPane));

        JButton mustard = new JButton(i5);
        mustard.setBackground(new Color(0, 100, 0));
        mustard.addActionListener(new CharButtonListener(5, buttonPane));

        JButton scarlett = new JButton(i6);
        scarlett.setBackground(new Color(0, 100, 0));
        scarlett.addActionListener(new CharButtonListener(6, buttonPane));

        buttonPane.add(peacock);
        buttonPane.add(plum);
        buttonPane.add(green);
        buttonPane.add(white);
        buttonPane.add(mustard);
        buttonPane.add(scarlett);

        add(buttonPane, BorderLayout.NORTH);
    }

    public class pButtonListener implements ActionListener {

        private int num;

        public pButtonListener(int i) {
            this.num = i;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            numPlayers = num;
            makeCharacterFrame();
        }

    }

    public class CharButtonListener implements ActionListener {

        private int charNum;
        private JPanel pane;

        public CharButtonListener(int charNum, JPanel pane) {
            this.charNum = charNum;
            this.pane = pane;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            game.addPlayer(charNum);

            pane.remove((Component) e.getSource());
            setLabel(++count);
        }

    }

    public static void main(String[] args) {
        new SelectionFrame();
    }
}
