package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import game.Board;
import game.cards.Keeper;
import game.objects.Clue;
import game.objects.Dice;
import game.objects.GameCharacter;
import game.objects.NoteBook;
import game.objects.Room;
import game.objects.Weapon;
import game.objects.Weapon.Weapons;
import game.squares.DoorSquare;
import game.squares.RoomSquare;

import org.junit.Test;

public class Tests {

	@Test
	public void testWeapons() {
		Board board = new Board("Data/Board");

		for (Room r : board.getRooms()) {
			if (r.getName().equals("Pool")) {
				continue;
			}

			if (r.getWeapons().isEmpty()) {
				fail();
			}
		}
	}

	@Test
	public void testDice() {
		Dice d = new Dice();
		assertTrue(!(d.roll() > 12));
		assertTrue(!(d.roll() < 2));
	}

	@Test
	public void testClue() {
		Weapon w = new Weapon(Weapons.Knife);
		Room r = new Room("Spa");
		GameCharacter c = new GameCharacter("Jack Mustard", 10, 5);
		Clue clue = new Clue(w, r, c);
		assertTrue(clue.getCharacter().getName().equals(c.getName()));
		assertTrue(clue.getRoom().getName().equals(r.getName()));
		assertTrue(clue.getWeapon().getName().equals(w.getName()));
	}

	@Test
	public void testRoomSquare() {
		Room r = new Room("Spa");
		RoomSquare rs = new RoomSquare(r, 10, 5);
		assertTrue(rs.getRoom().getName().equals(r.getName()));
	}

	@Test
	public void testDoorSquare() {
		Room r = new Room("Spa");
		DoorSquare ds = new DoorSquare(r, 5, 18);
		assertTrue(ds.getRow() == 18);
		assertFalse(!(ds.getR().getName().equals(r.getName())));
	}

	@Test
	public void testRoom() {
		Room r = new Room("Spa");
		Weapon w = new Weapon(Weapons.Knife);
		r.addWeapon(w);
		assertTrue(r.getWeapons().contains(w));
		r.removeWeapon(w);
		assertFalse(r.getWeapons().contains(w));
		Weapon w1 = new Weapon(Weapons.Axe);
		r.addWeapon(w);
		r.addWeapon(w1);
		assertTrue(r.getWeapons().contains(w1) && r.getWeapons().contains(w));
		r.removeWeapon(w);
		assertTrue(r.getWeapons().contains(w1) && !r.getWeapons().contains(w));
		assertFalse(r.isClue());
	}

	@Test
	public void testKeeperToStrings() {
		Keeper k1 = new Keeper("Roll", 5);
		assert k1.toString().equals("Add to Roll: 5");
		assert k1.getRoll() == 5;

		Keeper k2 = new Keeper("Move");
		assert k2.toString().equals("Move to Any Location");

		Keeper k3 = new Keeper("Start");
		assert k3.toString().equals("Send Player back to Start Square");
	}

	@Test
	public void testNotebook() {
		NoteBook nb = new NoteBook();
		Weapon w = new Weapon(Weapons.Knife);
		Room r = new Room("Spa");
		GameCharacter c = new GameCharacter("Jack Mustard", 10, 5);
		nb.addToNoteBook(c, false);
		nb.addToNoteBook(r, true);
		nb.addToNoteBook(w, true);
		assertTrue(nb.getCharacters().contains(c)
				&& nb.getBook().get(c).equals(false));
		assertTrue(nb.getRooms().contains(r)
				&& nb.getBook().get(r).equals(true));
		assertTrue(nb.getWeapons().contains(w)
				&& nb.getBook().get(w).equals(true));
	}

}
