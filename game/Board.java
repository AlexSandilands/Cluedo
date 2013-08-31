package game;

import game.objects.Dice;
import game.objects.GameCharacter;
import game.objects.Room;
import game.objects.Weapon;
import game.objects.Weapon.Weapons;
import game.squares.DoorSquare;
import game.squares.IntrigueSquare;
import game.squares.MoveSquare;
import game.squares.NullSquare;
import game.squares.PreDoor;
import game.squares.RoomSquare;
import game.squares.Square;
import game.squares.StartSquare;
import game.squares.WallSquare;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class Board {
    private Square[][] board = new Square[29][29];
    private List<Room> rooms = new ArrayList<Room>();
    private List<GameCharacter> characters = new ArrayList<GameCharacter>();
    private List<Weapon> weapons = new ArrayList<Weapon>(9);
    private List<PreDoor> pDoors = new ArrayList<PreDoor>();
    private List<RoomSquare> rSquares = new ArrayList<RoomSquare>();
    public static Dice dice = new Dice();
    private File file;

    private Square moveTo;

    // Gui Stuff

    private int width;
    private int height;

    public Board() {
        // file = chooseFile();
        file = new File("src/Data/Board");
        readBoard();
    }

    public Board(String name) {
        file = new File(name);
        readBoard();
    }

    public Square getSquare(int col, int row) {
        return board[col][row];
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<GameCharacter> getCharacters() {
        return characters;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public List<RoomSquare> getRoomSquares() {
        return rSquares;
    }

    /**
     * @return the board
     */
    public Square[][] getBoard() {
        return board;
    }

    public void setMoveTo(Square s){
        this.moveTo = s;
    }

    public Square getMoveTo(){
        return moveTo;
    }

    public void setSize(int x, int y) {
        int squareWidth = x / 29;
        int squareHeight = y / 29;

        for (int i = 0; i < 29; i++) {
            for (int j = 0; j < 29; j++) {
                board[i][j].setSize(squareWidth, squareHeight);
            }
        }
    }

    public void drawBoard(Graphics g) {
        for (int i = 0; i < 29; i++) {
            for (int j = 0; j < 29; j++) {
                board[i][j].draw(g);
            }
        }
    }


    public List<PreDoor> getPreDoors() {
        return pDoors;
    }

    /**
     * Allows the user to navigate to the file to be used.
     *
     * @return File, the file decribing the layout of the board.
     */
    private File chooseFile() {
        JFileChooser fc = new JFileChooser(".");

        fc.setDialogTitle("Choose the Board File.");
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);

        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile();
        } else {
            return null;
        }

    }

    /**
     * Reads the text file to create the board, which is a 2d array of Square
     * objects
     */
    private void readBoard() {
        int horCount = 0;
        int vertCount = 0;


        try {
            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                Scanner lineScan = new Scanner(line);

                while (lineScan.hasNext()) {
                    String square = lineScan.next();

                    char first = square.charAt(0);

                    switch (first) {
                    case 'w':
                        this.board[vertCount][horCount] = new WallSquare(
                                vertCount, horCount);
                        break;
                    case 'm':
                        this.board[vertCount][horCount] = new MoveSquare(
                                vertCount, horCount);
                        break;
                    case '-':
                        this.board[vertCount][horCount] = new NullSquare(
                                vertCount, horCount);
                        break;
                    case '?':
                        this.board[vertCount][horCount] = new IntrigueSquare(
                                vertCount, horCount);
                        break;
                    case 'R':
                        String rName = lineScan.next();

                        Room room = null;
                        for (Room r : rooms) {
                            if (r.getName().equalsIgnoreCase(rName)) {
                                room = r;
                                break;
                            }
                        }

                        if (room == null) {
                            room = new Room(rName);
                            rooms.add(room);
                        }

                        RoomSquare rs = new RoomSquare(room, vertCount,
                                horCount);
                        this.board[vertCount][horCount] = rs;
                        rSquares.add(rs);
                        room.setRoomSquare(rs);

                        break;
                    case 'D':
                        String rNameDoor = lineScan.next();
                        Room rm = null;

                        for (Room r : rooms) {
                            if (r.getName().equalsIgnoreCase(rNameDoor)) {
                                rm = r;
                                break;
                            }
                        }

                        if (rm == null) {
                            rm = new Room(rNameDoor);
                            rooms.add(rm);
                        }
                        this.board[vertCount][horCount] = new DoorSquare(rm,
                                vertCount, horCount);
                        break;
                    case 'P':
                        String prName = lineScan.next();

                        Room pDoorRoom = null;
                        for (Room r : rooms) {
                            if (r.getName().equalsIgnoreCase(prName)) {
                                pDoorRoom = r;
                                break;
                            }
                        }

                        if (pDoorRoom == null) {
                            pDoorRoom = new Room(prName);
                            rooms.add(pDoorRoom);
                        }

                        PreDoor pDoor = new PreDoor(pDoorRoom, vertCount,
                                horCount);
                        this.board[vertCount][horCount] = pDoor;
                        pDoors.add(pDoor);

                        break;
                    case 'S':
                        String name = lineScan.next() + " " + lineScan.next();

                        GameCharacter c = new GameCharacter(name, vertCount,
                                horCount);
                        c.setPlayable(false);
                        characters.add(c);

                        this.board[vertCount][horCount] = new StartSquare(
                                vertCount, horCount);
                        break;
                    default:
                        break;
                    }

                    horCount++;
                }
                vertCount++;
                horCount = 0;
            }

        } catch (IOException e) {
            throw new Error(e);
        }

        createWeapons();
        setNulls();
        distributeWeapons();
    }

    /**
     * Every RoomSquare has 8 NullSquares around it, which the players will be
     * placed on if they enter the room.
     */
    private void setNulls() {
        for (RoomSquare rs : rSquares) {
            NullSquare ns1 = (NullSquare) board[rs.getCol() - 1][rs.getRow() - 1];

            rs.setNull(ns1);
            ns1.setRoomSquare(rs);

            NullSquare ns2 = (NullSquare) board[rs.getCol() - 1][rs.getRow()];
            rs.setNull(ns2);
            ns2.setRoomSquare(rs);

            NullSquare ns3 = (NullSquare) board[rs.getCol() - 1][rs.getRow() + 1];
            rs.setNull(ns3);
            ns3.setRoomSquare(rs);

            NullSquare ns4 = (NullSquare) board[rs.getCol()][rs.getRow() - 1];
            rs.setNull(ns4);
            ns4.setRoomSquare(rs);

            NullSquare ns5 = (NullSquare) board[rs.getCol()][rs.getRow() + 1];
            rs.setNull(ns5);
            ns5.setRoomSquare(rs);

            NullSquare ns6 = (NullSquare) board[rs.getCol() + 1][rs.getRow() - 1];
            rs.setNull(ns6);
            ns6.setRoomSquare(rs);

            NullSquare ns7 = (NullSquare) board[rs.getCol() + 1][rs.getRow()];
            rs.setNull(ns7);
            ns7.setRoomSquare(rs);

            NullSquare ns8 = (NullSquare) board[rs.getCol() + 1][rs.getRow() + 1];
            rs.setNull(ns8);
            ns8.setRoomSquare(rs);
        }
    }

    /**
     * Randomly places the each weapon into a room.
     */
    private void distributeWeapons() {
        Collections.shuffle(weapons);
        int wepCount = 0;
        for (Room r : rooms) {
            if (r.getName().equals("Pool")) {
                continue;
            }
            r.addWeapon(weapons.get(wepCount));
            wepCount++;
        }
    }

    /**
     * Creates the weapons and adds them to the list of weapons.
     */
    private void createWeapons() {
        weapons.add(new Weapon(Weapons.Rope));
        weapons.add(new Weapon(Weapons.Candlestick));
        weapons.add(new Weapon(Weapons.Knife));
        weapons.add(new Weapon(Weapons.Pistol));
        weapons.add(new Weapon(Weapons.Baseball_Bat));
        weapons.add(new Weapon(Weapons.Dumbbell));
        weapons.add(new Weapon(Weapons.Trophy));
        weapons.add(new Weapon(Weapons.Poison));
        weapons.add(new Weapon(Weapons.Axe));
    }
}
