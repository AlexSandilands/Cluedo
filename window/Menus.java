package window;

import game.Board;
import game.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Menus extends JMenu implements ActionListener{

    private JMenu file;
    private JMenu game;
    private JMenu cheats;
    private BoardFrame frame;

    //Menu items
    private JMenuItem restart;
    private JMenuItem exit;
    private JMenuItem boardLayout;
    private JMenuItem help;
    private JMenuItem keepers;
    private Game bGame;

    public Menus(BoardFrame frame, Game bGame){
        this.bGame = bGame;

        this.file = makeFile();
        this.game = makeGame();
        this.cheats = makeCheats();
        this.frame = frame;
    }

    /*
     * A method that constructs the File menu
     * for the menu bar.
     */
    private JMenu makeFile(){
        file = new JMenu("File");
        restart = new JMenuItem("Restart Game");
        restart.addActionListener(this);
        exit = new JMenuItem("Exit");
        exit.addActionListener(this);
        file.add(restart);
        file.add(exit);
        return file;
    }

    /*
     * A method that constructs the Game menu
     * for the menu bar.
     */
    private JMenu makeGame(){
        game = new JMenu("Game");
        boardLayout = new JMenuItem("Change board layout");
        boardLayout.addActionListener(this);
        help = new JMenuItem("Help");
        help.addActionListener(this);
        game.add(boardLayout);
        game.add(help);
        return game;
    }

    /*
     * A method that constructs the Game menu
     * for the menu bar.
     */
    private JMenu makeCheats(){
        cheats = new JMenu("Cheats");
        keepers = new JMenuItem("Everyone has Keepers");
        keepers.addActionListener(this);
        cheats.add(keepers);
        return cheats;
    }

    /**
     * @return the file
     */
    public JMenu getFile() {
        return file;
    }

    /**
     * @return the game
     */
    public JMenu getGame() {
        return game;
    }

    public JMenu getCheats(){
        return cheats;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == restart){
            int r = JOptionPane.showConfirmDialog(this, new JLabel("All progress will be lost, Are you sure you want to restart?"),
                    "Restart this game", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if(r == 0){
                frame.dispose();
                Game g = new Game(new Board());
            }
        }
        else if(e.getSource() == exit){
            int r = JOptionPane.showConfirmDialog(this, new JLabel("Are you sure yo want to quit this game?"),
                    "Quit the game", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if(r == 0){
                System.exit(1);
            }
        }
        else if(e.getSource() == boardLayout){
            JFileChooser fc = new JFileChooser("src/Boards/");
            File f = null;
            int value = fc.showDialog(null, "Select File");
            if(value == JFileChooser.APPROVE_OPTION){
                f = fc.getSelectedFile();
                frame.changeLayout(f);
            }
        }
        else if(e.getSource() == help){
            new HelpFrame();
        }
        else if(e.getSource() == keepers){
            int r = JOptionPane.showConfirmDialog(this, new JLabel("Every player will have every keeper card. Continue?"),
                    "Give Keeper Cards", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if(r == 0){
                bGame.cheatKeeperCards();
            }
        }
    }
}
