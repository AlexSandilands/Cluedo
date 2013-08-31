package window;

import game.Board;
import game.Game;
import game.squares.IntrigueSquare;
import game.squares.MoveSquare;
import game.squares.PreDoor;
import game.squares.RoomSquare;
import game.squares.Square;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class BoardPanel extends JPanel implements MouseInputListener {

    // Fields relating to the Gui
    private int width;
    private int height;
    private BufferedImage boardImage;
    private File boardFile;
    private Board board;
    private Game game;

    public BoardPanel(int width, int height, Board board, Game game) {
        this.width = width;
        this.height = height;

        this.board = board;
        this.game = game;

        boardFile = new File("DataFiles/Boards/Default.jpg");

        // Loads the image into the buffered image
        try {
            boardImage = ImageIO.read(boardFile);
        } catch (IOException e) {
            throw new Error(e);
        }

        this.addComponentListener(new Resizer());

        addMouseListener(this);

        setPreferredSize(new Dimension(width, height));
        setVisible(true);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void paintComponent(Graphics g) {
        g.drawImage(boardImage, 0, 0, width, height, null);
        board.drawBoard(g);
    }

    private class Resizer implements ComponentListener {

        @Override
        public void componentResized(ComponentEvent e) {
            JPanel c = (JPanel) e.getSource();

            BoardPanel.this.setSize(new Dimension(c.getWidth(), c.getHeight()));
            width = c.getWidth();
            height = c.getHeight();

            repaint();
        }

        @Override
        public void componentMoved(ComponentEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void componentShown(ComponentEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void componentHidden(ComponentEvent e) {
            // TODO Auto-generated method stub

        }
    }

    /**
     * @param boardFile the boardFile to set
     */
    public void setBoardFile(File boardFile) {
        this.boardFile = boardFile;

        try {
            boardImage = ImageIO.read(boardFile);
        } catch (IOException e) {
            throw new Error(e);
        }

        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[i].length; j++) {
                Square s = board.getBoard()[i][j];
                Rectangle2D box = s.getBoundingBox();

                if (box.contains(new Point(x, y))) {
                    if (game.getMoveToSquares().contains(s)) {

                        if (game.getPlayingKeeper()) {
                            if (s instanceof PreDoor || s instanceof MoveSquare
                                    || s instanceof IntrigueSquare) {
                                game.getCurrent().moveTo(s);
                                game.getCurrent().setInRoom(false);
                                game.getCurrent().setMoving(false);
                                game.getCurrent().setHasMoved(true);
                                game.getCurrent().clearRoll();
                                game.getFrame().clearRoll();
                                game.clearMoveToSquares();
                                game.getFrame().repaint();
                                game.setPlayingKeeper(false);

                            } else if (s instanceof RoomSquare) {
                                ((RoomSquare) s).addToRoom(game.getCurrent(),
                                        game.getCurrent().getOn());
                                game.getCurrent().setHasMoved(true);
                                game.getCurrent().setMoving(false);
                                game.getCurrent().setInRoom(true);
                                game.getCurrent().clearRoll();
                                game.getFrame().clearRoll();
                                game.clearMoveToSquares();
                                game.getFrame().repaint();
                                game.setPlayingKeeper(false);
                            }
                        } else {
                            if (s instanceof PreDoor) {
                                game.getCurrent().moveTo(s);
                                game.getFrame().decreaseRoll();
                                game.getCurrent().setInRoom(false);
                                game.getCurrent().setMoving(true);
                                game.clearMoveToSquares();
                            } else if (s instanceof RoomSquare) {
                                ((RoomSquare) s).addToRoom(game.getCurrent(),
                                        game.getCurrent().getOn());
                                game.getCurrent().setHasMoved(true);
                                game.getFrame().clearRoll();
                                game.clearMoveToSquares();
                                game.getFrame().repaint();
                            }
                        }

                    }
                }

            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}