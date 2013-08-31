package game.squares;

import game.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public abstract class Square {

    protected int row;
    protected int col;
    protected Square pathTo;
    protected int pathLength;
    protected Player player;
    protected int xPos;
    protected int yPos;
    protected int width;
    protected int height;
    protected boolean selectable;

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Rectangle2D getBoundingBox() {
        return new Rectangle(xPos, yPos, width, height);
    }

    public void setPlayer(Player p) {
        this.player = p;
    }

    public Player getPlayer() {
        return player;
    }

    public void setSize(int x, int y) {
        width = x;
        height = y;

        xPos = row * x;
        yPos = col * y;
    }

    public void draw(Graphics g) {
        if (selectable) {
            g.setColor(new Color(127, 255, 0));
            g.drawRect(xPos, yPos, width, height);
            g.drawRect(xPos+1, yPos+1, width-2, height-2);
        }

        if (player != null) {
            if (player.isCurrent()) {
                g.setColor(new Color(127, 255, 0));
                g.fillOval(xPos, yPos, width, height);
            }
            Color col = player.getCharacter().getColor();
            g.setColor(col);

            g.fillOval(xPos + 2, yPos + 2, width - 4, height - 4);

            g.setColor(Color.BLACK);
            g.drawOval(xPos + 2, yPos + 2, width - 4, height - 4);
        }
    }

    public void setSelectable(boolean b) {
        this.selectable = b;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + col;
        result = prime * result + pathLength;
        result = prime * result + ((pathTo == null) ? 0 : pathTo.hashCode());
        result = prime * result + ((player == null) ? 0 : player.hashCode());
        result = prime * result + row;
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Square other = (Square) obj;
        if (col != other.col)
            return false;
        if (pathLength != other.pathLength)
            return false;
        if (pathTo == null) {
            if (other.pathTo != null)
                return false;
        } else if (!pathTo.equals(other.pathTo))
            return false;
        if (player == null) {
            if (other.player != null)
                return false;
        } else if (!player.equals(other.player))
            return false;
        if (row != other.row)
            return false;
        return true;
    }
}
