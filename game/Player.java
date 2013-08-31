package game;

import game.cards.Clock;
import game.cards.GameItemCard;
import game.cards.IntrigueCard;
import game.cards.Keeper;
import game.objects.Clue;
import game.objects.GameCharacter;
import game.objects.GameItem;
import game.objects.NoteBook;
import game.objects.Room;
import game.objects.Weapon;
import game.squares.DoorSquare;
import game.squares.IntrigueSquare;
import game.squares.MoveSquare;
import game.squares.NullSquare;
import game.squares.PreDoor;
import game.squares.RoomSquare;
import game.squares.Square;
import game.squares.WallSquare;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import window.ExecuteGuessFrame;
import window.KeeperGuessFrame;
import window.SeeCards;
import window.SendHome;

public class Player {

    private GameCharacter gc;
    private NoteBook nb = new NoteBook();
    private List<GameItemCard> gameItemCards = new ArrayList<GameItemCard>();
    private List<Keeper> keeperCards = new ArrayList<Keeper>();
    private int roll = 0;
    private Square on;
    private boolean hasMoved = false;
    private boolean inRoom = false;
    private boolean hasGuessed = false;
    private boolean isPlaying = false;
    private boolean current = false;
    private boolean usedKeeper = false;
    private boolean isMoving = false;
    private Board board;
    private Game game;

    /**
     * Constructor for a player. They are given the board and the game, which
     * makes sence as in real life a player would have access to both of these.
     * It also makes this class much easier.
     *
     * @param gc
     * @param board
     * @param game
     */
    public Player(GameCharacter gc, Board board, Game game) {
        this.gc = gc;
        this.board = board;
        this.game = game;
    }

    /**
     * Returns a number between 2 and 12, simulating two six sided dice being
     * rolled.
     *
     * @return
     */
    public int roll() {
        roll = Board.dice.roll();
        return roll;
    }

    public void clearRoll() {
        roll = 0;
    }

    public void resetBooleans() {
        hasMoved = false;
        hasGuessed = false;
        usedKeeper = false;
        isMoving = false;
    }

    /**
     * The player has landed on a ? square so therefore draws an intrigue card.
     */
    public void drawIntrigue() {
        IntrigueCard ic = game.drawIntrigueCard();

        if (ic == null) {
            return;
        }

        if (ic instanceof Keeper) {

            JFrame drewKeeper = new JFrame("Drew Keeper Card");
            drewKeeper.setSize(new Dimension(700, 400));
            drewKeeper.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // For Centering frame in the middle of the screen.
            // ----------------------------------------------------------
            // Get the size of the screen
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

            // Determine the new location of the window
            int wid = drewKeeper.getSize().width;
            int h = drewKeeper.getSize().height;

            int x = (dim.width / 2 - wid / 2);
            int y = (dim.height / 2 - h / 2);

            // Move the window
            drewKeeper.setLocation(x, y);
            // ----------------------------------------------------------

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBackground(new Color(139, 90, 0));

            JLabel drew = new JLabel("You drew a keeper card: ");
            drew.setFont(new Font("Serif", Font.BOLD, 50));
            drew.setHorizontalAlignment(JLabel.CENTER);
            drew.setForeground(Color.WHITE);

            JLabel card = new JLabel(ic.toString());
            card.setFont(new Font("Serif", Font.ITALIC + Font.BOLD, 40));
            card.setHorizontalAlignment(JLabel.CENTER);
            card.setForeground(Color.WHITE);

            Icon mark = new ImageIcon("DataFiles/Data/Questionmark.jpg");
            JLabel question = new JLabel(mark);

            mainPanel.add(drew, BorderLayout.NORTH);
            mainPanel.add(question, BorderLayout.CENTER);
            mainPanel.add(card, BorderLayout.SOUTH);

            drewKeeper.add(mainPanel);

            drewKeeper.setVisible(true);
            keeperCards.add((Keeper) ic);
        } else if (ic instanceof Clock) {
            // If the Clock card is the 8th one drawn, the player is murdered
            // and the card is shuffled back into the deck.

            JFrame drewClock = new JFrame("Drew Keeper Card");
            drewClock.setSize(new Dimension(700, 400));
            drewClock.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // For Centering frame in the middle of the screen.
            // ----------------------------------------------------------
            // Get the size of the screen
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

            // Determine the new location of the window
            int wid = drewClock.getSize().width;
            int h = drewClock.getSize().height;

            int x = (dim.width / 2 - wid / 2);
            int y = (dim.height / 2 - h / 2);

            // Move the window
            drewClock.setLocation(x, y);
            // ----------------------------------------------------------

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBackground(new Color(139, 90, 0));

            JLabel drew = new JLabel("You drew a Clock Card: ");
            drew.setFont(new Font("Serif", Font.BOLD, 50));
            drew.setHorizontalAlignment(JLabel.CENTER);
            drew.setForeground(Color.WHITE);

            JLabel number = new JLabel("Clock number: " + game.clockCount());
            number.setFont(new Font("Serif", Font.ITALIC + Font.BOLD, 40));
            number.setHorizontalAlignment(JLabel.CENTER);
            number.setForeground(Color.WHITE);

            Icon mark = new ImageIcon("DataFiles/Data/Questionmark.jpg");
            JLabel question = new JLabel(mark);

            mainPanel.add(drew, BorderLayout.NORTH);
            mainPanel.add(question, BorderLayout.CENTER);
            mainPanel.add(number, BorderLayout.SOUTH);

            drewClock.add(mainPanel);

            drewClock.setVisible(true);

            if (game.clockCount() > 7) {
                game.loseToClock(this);
                isPlaying = false;
                game.add8thClock(ic);
            }
        }
    }

    /**
     * Will do the event on the card that the player chosen to play.
     *
     * @param kType
     * @param keep
     */
    public void executeKeeper(Keeper keep) {
        int type = 0;
        String kType = keep.getType();

        if (kType.equals("Roll")) {
            type = 1;
        } else if (kType.equals("Move")) {
            type = 2;
        } else if (kType.equals("Start")) {
            type = 3;
        } else if (kType.equals("Make")) {
            hasGuessed = false;
            type = 4;
        } else if (kType.equals("Turn")) {
            type = 5;
        } else if (kType.equals("See")) {
            type = 6;
        }

        switch (type) {
        case 1:
            roll += keep.getRoll();
            game.getFrame().addToRoll(keep.getRoll());
            game.getFrame().repaint();
            break;
        case 2:
            List<Square> moveToSquares = new ArrayList<Square>();
            for (int i = 0; i < 29; i++) {
                for (int j = 0; j < 29; j++) {
                    Square s = board.getBoard()[i][j];

                    if (s instanceof RoomSquare || s instanceof MoveSquare
                            || s instanceof IntrigueSquare
                            || s instanceof PreDoor) {
                        moveToSquares.add(s);
                        s.setSelectable(true);
                    }
                }
            }

            game.setMoveToSquares(moveToSquares);
            game.getFrame().repaint();

            break;
        case 3:
            new SendHome(game);

            break;
        case 4:
            hasGuessed = false;
            new KeeperGuessFrame(game);
            hasGuessed = true;
            game.getFrame().repaint();
            break;
        case 5:
            resetBooleans();
            game.setPlayingKeeper(false);
            break;
        case 6:
            new SeeCards(game);
        }

        keeperCards.remove(keep);
    }

    /**
     * Does the actually work in the guess.
     *
     * @param guess
     */
    public void executeGuess(Weapon w, GameCharacter gc, Room r) {
        // Gets the room the player is in
        NullSquare ns = null;
        RoomSquare rs = null;
        Room room = null;
        if (r != null) {
            room = r;
            rs = r.getRoomSquare();
            for (NullSquare nSq : rs.getNulls()) {
                if (nSq.getPlayer() == null) {
                    ns = nSq;
                    break;
                }
            }

        } else {
            if (inRoom) {
                ns = (NullSquare) on;
                rs = ns.getRoomSquare();
                room = rs.getRoom();
            } else {
                return;
            }
        }

        // Gets the player for the chosen character.
        Player p = null;
        for (Player gp : game.getPlayers()) {
            if (gp == null) {
                continue;
            }

            if (gp.getCharacter() == gc) {
                p = gp;
            }
        }

        // Moves the accused player to the room.
        rs.addToRoom(p, p.getOn());
        p.inRoom = true;

        // Moves the accused weapon to this room.
        for (Room br : board.getRooms()) {
            if (br.getWeapons().contains(w)) {
                br.getWeapons().remove(w);
                rs.getRoom().addWeapon(w);
            }
        }

        Player next = null;
        Player temp = game.getNextPlayer();
        while (next == null) {
            for (GameItemCard card : temp.getCards()) {
                if (card == null) {
                    continue;
                }

                if (card.getItem().equals(w)) {

                    if (temp.findAvoid() != null) {
                        int ret = JOptionPane
                                .showConfirmDialog(
                                        game.getFrame(),
                                        new JLabel(
                                                temp.getCharacter().getName()
                                                        + " has an Avoid Guess keeper card. Use it?"),
                                        "Use Avoid Card",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.WARNING_MESSAGE);
                        if (ret == 0) {
                            temp.getKeepers().remove(temp.findAvoid());
                            game.getFrame().repaint();
                            return;
                        } else if (ret == 1) {
                            next = temp;
                            break;
                        }
                    } else {
                        next = temp;
                    }
                } else if (card.getItem().equals(room)) {
                    if (temp.findAvoid() != null) {
                        int ret = JOptionPane
                                .showConfirmDialog(
                                        game.getFrame(),
                                        new JLabel(
                                                temp.getCharacter().getName()
                                                        + " has an Avoid Guess keeper card. Use it?"),
                                        "Use Avoid Card",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.WARNING_MESSAGE);
                        if (ret == 0) {
                            temp.getKeepers().remove(temp.findAvoid());
                            game.getFrame().repaint();
                            return;
                        } else if (ret == 1) {
                            next = temp;
                            break;
                        }
                    } else {
                        next = temp;
                    }
                } else if (card.getItem().equals(gc)) {
                    if (temp.findAvoid() != null) {
                        int ret = JOptionPane
                                .showConfirmDialog(
                                        game.getFrame(),
                                        new JLabel(
                                                temp.getCharacter().getName()
                                                        + " has an Avoid Guess keeper card. Use it?"),
                                        "Use Avoid Card",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.WARNING_MESSAGE);
                        if (ret == 0) {
                            temp.getKeepers().remove(temp.findAvoid());
                            game.getFrame().repaint();
                            return;
                        } else if (ret == 1) {
                            next = temp;
                            break;
                        }
                    } else {
                        next = temp;
                    }
                }
            }
            temp = game.getNextPlayer(temp);

            if (temp == game.getCurrent() && next == null) {
                int ret = JOptionPane.showConfirmDialog(game.getFrame(),
                        new JLabel("No one had cards to show you!"),
                        "Guess", JOptionPane.CLOSED_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (ret == 0) {
                    return;
                }
                return;
            }
        }

        // Moves the accused player to the room.
        rs.addToRoom(p, p.getOn());
        p.inRoom = true;

        // Moves the accused weapon to this room.
        for (Room br : board.getRooms()) {
            if (br.getWeapons().contains(w)) {
                br.getWeapons().remove(w);
                rs.getRoom().addWeapon(w);
            }
        }

        game.getFrame().repaint();

        new ExecuteGuessFrame(w, room, gc, next);

    }

    public void executeAccuse(Weapon w, Room r, GameCharacter gc) {
        Clue clue = game.getClue();

        if (w != clue.getWeapon() || r != clue.getRoom()
                || gc != clue.getCharacter()) {
            game.lose(this);
        } else {
            game.win(this);
        }
    }

    public void moveTo(Square s) {
        on.setPlayer(null);
        s.setPlayer(this);
        on = s;
        game.clearMoveToSquares();
        game.getFrame().repaint();
    }

    /**
     * Returns the list of squares that the player can move to when they leave a
     * room.
     */
    public List<Square> leaveRoom() {
        if (!inRoom) {
            return null;
        }
        NullSquare ns = (NullSquare) on;
        List<Square> moveToSquares = new ArrayList<Square>();
        for (PreDoor pd : board.getPreDoors()) {
            if (pd.getPlayer() != null) {
                continue;
            }

            if (pd.getRoom() == ns.getRoomSquare().getRoom()) {
                moveToSquares.add((Square) pd);
                pd.setSelectable(true);
            }
        }

        if (ns.getRoomSquare().getRoom().getName().equals("Spa")) {
            for (RoomSquare rs : board.getRoomSquares()) {
                if (rs.getRoom().getName().equals("Guest-House")) {
                    moveToSquares.add(rs);
                    rs.setSelectable(true);
                }
            }
        } else if (ns.getRoomSquare().getRoom().getName().equals("Guest-House")) {
            for (RoomSquare rs : board.getRoomSquares()) {
                if (rs.getRoom().getName().equals("Spa")) {
                    moveToSquares.add(rs);
                    rs.setSelectable(true);
                }
            }
        } else if (ns.getRoomSquare().getRoom().getName().equals("Observatory")) {
            for (RoomSquare rs : board.getRoomSquares()) {
                if (rs.getRoom().getName().equals("Kitchen")) {
                    moveToSquares.add(rs);
                    rs.setSelectable(true);
                }
            }
        } else if (ns.getRoomSquare().getRoom().getName().equals("Kitchen")) {
            for (RoomSquare rs : board.getRoomSquares()) {
                if (rs.getRoom().getName().equals("Observatory")) {
                    moveToSquares.add(rs);
                    rs.setSelectable(true);
                }
            }
        }

        roll--;
        game.getFrame().repaint();
        return moveToSquares;
    }

    public NoteBook getNoteBook() {
        return nb;
    }

    public void keyMove(int dir) {
        switch (dir) {
        case 1:
            if (on.getCol() - 1 < 0) {
                break;
            }

            Square up = board.getSquare(on.getCol() - 1, on.getRow());
            if (up instanceof WallSquare || up.getPlayer() != null) {
                break;
            }

            if (up instanceof DoorSquare && on instanceof PreDoor) {
                PreDoor pd = (PreDoor) on;
                RoomSquare rs = pd.getRoom().getRoomSquare();
                rs.addToRoom(this, pd);
                roll = 0;
                game.getFrame().clearRoll();
                inRoom = true;
                break;
            } else if (up instanceof DoorSquare && !(on instanceof PreDoor)) {
                break;
            }

            up.setPlayer(this);
            on.setPlayer(null);
            on = up;
            roll--;
            game.getFrame().decreaseRoll();

            break;
        case 2:
            if (on.getCol() + 1 > 28) {
                break;
            }

            Square down = board.getSquare(on.getCol() + 1, on.getRow());
            if (down instanceof WallSquare || down.getPlayer() != null) {
                break;
            }

            if (down instanceof DoorSquare && on instanceof PreDoor) {
                PreDoor pd = (PreDoor) on;
                RoomSquare rs = pd.getRoom().getRoomSquare();
                rs.addToRoom(this, pd);
                roll = 0;
                game.getFrame().clearRoll();
                inRoom = true;
                break;
            } else if (down instanceof DoorSquare && !(on instanceof PreDoor)) {
                break;
            }

            down.setPlayer(this);
            on.setPlayer(null);
            on = down;
            roll--;
            game.getFrame().decreaseRoll();

            break;
        case 3:
            if (on.getRow() - 1 < 0) {
                break;
            }

            Square left = board.getSquare(on.getCol(), on.getRow() - 1);
            if (left instanceof WallSquare || left.getPlayer() != null) {
                break;
            }

            if (left instanceof DoorSquare && on instanceof PreDoor) {
                PreDoor pd = (PreDoor) on;
                RoomSquare rs = pd.getRoom().getRoomSquare();
                rs.addToRoom(this, pd);
                roll = 0;
                game.getFrame().clearRoll();
                inRoom = true;
                break;
            } else if (left instanceof DoorSquare && !(on instanceof PreDoor)) {
                break;
            }

            left.setPlayer(this);
            on.setPlayer(null);
            on = left;
            roll--;
            game.getFrame().decreaseRoll();

            break;
        case 4:
            if (on.getRow() + 1 > 23) {
                break;
            }

            Square right = board.getSquare(on.getCol(), on.getRow() + 1);
            if (right instanceof WallSquare || right.getPlayer() != null) {
                break;
            }

            if (right instanceof DoorSquare && on instanceof PreDoor) {
                PreDoor pd = (PreDoor) on;
                RoomSquare rs = pd.getRoom().getRoomSquare();
                rs.addToRoom(this, pd);
                roll = 0;
                game.getFrame().clearRoll();
                inRoom = true;
                break;
            } else if (right instanceof DoorSquare && !(on instanceof PreDoor)) {
                break;
            }

            right.setPlayer(this);
            on.setPlayer(null);
            on = right;
            roll--;
            game.getFrame().decreaseRoll();

            break;
        default:
            throw new Error("This should never happen!");
        }

        if (roll == 0) {
            this.isMoving = false;
            setHasMoved(true);
            if (on instanceof IntrigueSquare) {
                drawIntrigue();
            }
        }

        game.getFrame().repaint();
    }

    public void updateNoteBook() {
        for (GameItemCard c : gameItemCards) {
            if (c == null) {
                continue;
            }
            nb.addToNoteBook(c.getItem(), true);
        }
    }

    public void updateNoteBook(GameItem gi, boolean b) {
        nb.addToNoteBook(gi, b);
    }

    public void addCard(GameItemCard c) {
        gameItemCards.add(c);
    }

    public List<GameItemCard> getCards() {
        return gameItemCards;
    }

    public List<Keeper> getKeepers() {
        return keeperCards;
    }

    public Game getGame() {
        return game;
    }

    public GameCharacter getCharacter() {
        return gc;
    }

    /**
     * If this player is being controlled by a user then isPlaying will return
     * true;
     *
     * @return
     */
    public boolean isPlaying() {
        return isPlaying;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean b) {
        this.isMoving = b;
    }

    public boolean hasGuessed() {
        return hasGuessed;
    }

    public void setHasGuessed(boolean b) {
        hasGuessed = b;
    }

    public void setInRoom(boolean b) {
        this.inRoom = b;
    }

    public boolean inRoom() {
        return inRoom;
    }

    public void setCurrent(boolean b) {
        this.current = b;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setPlaying(boolean b) {
        this.isPlaying = b;
    }

    /**
     * Sets the square that the player is currently standing on.
     *
     * @param s
     */
    public void setOn(Square s) {
        this.on = s;
        s.setPlayer(this);
    }

    public Square getOn() {
        return on;
    }

    /**
     * @return the hasMoved
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * @param hasMoved
     *            the hasMoved to set
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean getUsedKeeper() {
        return usedKeeper;
    }

    public Keeper findAvoid() {
        for (Keeper k : keeperCards) {
            if (k.getType().equals("Avoid")) {
                return k;
            }
        }
        return null;
    }

    public void setUsedKeeper(boolean b) {
        usedKeeper = b;
    }

    public void addKeeper(Keeper k) {
        keeperCards.add(k);
    }
}
