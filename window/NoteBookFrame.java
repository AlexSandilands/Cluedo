package window;

import game.Player;
import game.cards.GameItemCard;
import game.objects.GameCharacter;
import game.objects.GameItem;
import game.objects.Room;
import game.objects.Weapon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class NoteBookFrame extends JFrame {

    private JPanel notePanel;
    private BufferedImage noteImage;
    private File noteFile;
    private Player p;

    public NoteBookFrame(Player p) {
        super("Note Book");

        this.p = p;

        setSize(new Dimension(655, 510));

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

        noteFile = new File("src/Data/NoteBook.jpg");

        // Loads the image into the buffered image
        try {
            noteImage = ImageIO.read(noteFile);
        } catch (IOException e) {
            throw new Error(e);
        }

        createPanel();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        pack();
        repaint();
    }

    private void createPanel() {
        notePanel = new JPanel() {
            public void paintComponent(Graphics g) {
                g.drawImage(noteImage, 0, 0, 650, 504, null);
                drawNotes(g);
            }
        };

        notePanel.setPreferredSize(new Dimension(650, 510));

        add(notePanel, BorderLayout.CENTER);
    }

    private void drawNotes(Graphics g) {
        g.setFont(new Font("Serif", Font.BOLD, 18));

        int charStart = 43;

        g.drawString("Characters:", 25, charStart);
        for (GameCharacter gc : p.getNoteBook().getCharacters()) {
            if (gc == null) {
                continue;
            }

            g.setFont(new Font("Serif", Font.ITALIC, 15));
            charStart += 20;
            g.drawString(gc.getName(), 25, charStart);

            if(p.getNoteBook().getBook().get(gc)){
                drawLine(g, 24, charStart-10, 24 + gc.getName().length()*8, charStart, gc);
            }
        }

        g.setFont(new Font("Serif", Font.BOLD, 18));

        int roomStart = 230;

        g.drawString("Rooms:", 25, roomStart);
        for (Room r : p.getNoteBook().getRooms()) {
            if (r == null) {
                continue;
            }

            g.setFont(new Font("Serif", Font.ITALIC, 15));
            roomStart += 20;
            g.drawString(r.getName(), 25, roomStart);

            if(p.getNoteBook().getBook().get(r)){
                drawLine(g, 24, roomStart-10, 24 + r.getName().length()*8, roomStart, r);
            }
        }

        g.setFont(new Font("Serif", Font.BOLD, 18));

        int weaponStart = 45;

        g.drawString("Weapons", 350, weaponStart);
        for (Weapon w : p.getNoteBook().getWeapons()) {
            if (w == null) {
                continue;
            }

            g.setFont(new Font("Serif", Font.ITALIC, 15));
            weaponStart += 21;
            g.drawString(w.getName(), 350, weaponStart);

            if(p.getNoteBook().getBook().get(w)){
                drawLine(g, 350, weaponStart-10, 350 + w.getName().length()*8, weaponStart, w);
            }
        }

    }

    private void drawLine(Graphics g, int x1, int y1, int x2, int y2, GameItem item){
        for(GameItemCard card : p.getCards()){
            if(card == null){
                continue;
            }

            if(card.getItem().equals(item)){
                g.setColor(Color.BLUE);
                break;
            } else {
                g.setColor(Color.RED);
            }

        }

        g.drawLine(x1, y1, x2, y2);
        g.drawLine(x1, y1+1, x2, y2+1);
        g.setColor(Color.BLACK);
    }
}
