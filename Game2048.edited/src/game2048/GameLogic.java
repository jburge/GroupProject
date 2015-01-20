package game2048;

import java.awt.event.KeyEvent;

///
///Right now new tile doesnt spawn on side of map
///
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameLogic {

	private Tile[] myTiles;
	boolean myWin = false;
	boolean myLose = false;
	int myScore = 0;
	private ColorSet currentColor;

	public GameLogic() {
		currentColor = new ColorDefault();
		resetGame();
	}

	public boolean getWin() {
		return myWin;
	}

	public boolean getLose() {
		return myLose;
	}

	public int getScore() {
		return myScore;
	}

	public void resetGame() {
		myScore = 0;
		myWin = false;
		myLose = false;
		myTiles = new Tile[4 * 4];
		for (int i = 0; i < myTiles.length; i++) {
			myTiles[i] = new Tile(currentColor);
		}
		addTile();
		addTile();
	}

	private void addTile() {
		List<Tile> list = availableSpace();
		if (!availableSpace().isEmpty()) {
			int index = (int) (Math.random() * list.size()) % list.size();
			Tile emptyTime = list.get(index);
			emptyTime.value = Math.random() < 0.9 ? 2 : 4;
		}
	}

	private List<Tile> availableSpace() {
		final List<Tile> list = new ArrayList<Tile>(16);
		for (Tile t : myTiles) {
			if (t.isEmpty()) {
				list.add(t);
			}
		}
		return list;
	}

	private boolean isFull() {
		return availableSpace().size() == 0;
	}

	// dont know what to pass yet
	// might need return as well
	public void checkState(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			resetGame();
		}
		if (!canMove()) {
			myLose = true;
		}

		if (!myWin && !myLose) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				left();
				break;
			case KeyEvent.VK_RIGHT:
				right();
				break;
			case KeyEvent.VK_DOWN:
				down();
				break;
			case KeyEvent.VK_UP:
				up();
				break;
			}
		}

		if (!myWin && !canMove()) {
			myLose = true;
		}
		// resetGame();
	}

	boolean canMove() {
		if (!isFull()) {
			return true;
		}
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				Tile t = tileAt(x, y);
				if ((x < 3 && t.value == tileAt(x + 1, y).value)
						|| ((y < 3) && t.value == tileAt(x, y + 1).value)) {
					return true;
				}
			}
		}
		return false;
	}

	private Tile tileAt(int x, int y) {
		return myTiles[x + y * 4];
	}

	// shifting tiles
	public void left() {
		boolean needAddTile = false;
		for (int i = 0; i < 4; i++) {
			Tile[] line = getLine(i);
			Tile[] merged = mergeLine(moveLine(line));
			setLine(i, merged);
			if (!needAddTile && !compare(line, merged)) {
				needAddTile = true;
			}
		}

		if (needAddTile) {
			addTile();
		}
	}

	public void right() {
		myTiles = rotate(180);
		left();
		myTiles = rotate(180);
	}

	public void up() {
		myTiles = rotate(270);
		left();
		myTiles = rotate(90);
	}

	public void down() {
		myTiles = rotate(90);
		left();
		myTiles = rotate(270);
	}

	private Tile[] rotate(int angle) {
		Tile[] newTiles = new Tile[4 * 4];
		int offsetX = 3, offsetY = 3;
		if (angle == 90) {
			offsetY = 0;
		} else if (angle == 270) {
			offsetX = 0;
		}

		double rad = Math.toRadians(angle);
		int cos = (int) Math.cos(rad);
		int sin = (int) Math.sin(rad);
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				int newX = (x * cos) - (y * sin) + offsetX;
				int newY = (x * sin) + (y * cos) + offsetY;
				newTiles[(newX) + (newY) * 4] = tileAt(x, y);
			}
		}
		return newTiles;
	}

	// get/set line

	public Tile[] getLine(int index) {
		Tile[] result = new Tile[4];
		for (int i = 0; i < 4; i++) {
			result[i] = tileAt(i, index);
		}
		return result;
	}

	public Tile getTile(int index) {
		return myTiles[index];
	}

	private void setLine(int index, Tile[] re) {
		System.arraycopy(re, 0, myTiles, index * 4, 4);
	}
	// dont understand
	private boolean compare(Tile[] line1, Tile[] line2) {
		if (line1 == line2) {
			return true;
		}
		else if (line1.length != line2.length) {
			return false;
		}

		for (int i = 0; i < line1.length; i++) {
			if (line1[i].value != line2[i].value) {
				return false;
			}
		}
		return true;
	}

	private Tile[] moveLine(Tile[] oldLine) {
		LinkedList<Tile> l = new LinkedList<Tile>();
		for (int i = 0; i < 4; i++) {
			if (!oldLine[i].isEmpty())
				l.addLast(oldLine[i]);
		}
		if (l.size() == 0) {
			return oldLine;
		} else {
			Tile[] newLine = new Tile[4];
			ensureSize(l, 4);
			for (int i = 0; i < 4; i++) {
				newLine[i] = l.removeFirst();
			}
			return newLine;
		}
	}

	private Tile[] mergeLine(Tile[] oldLine) {
		LinkedList<Tile> list = new LinkedList<Tile>();
		for (int i = 0; i < 4 && !oldLine[i].isEmpty(); i++) {
			int num = oldLine[i].value;
			if (i < 3 && oldLine[i].value == oldLine[i + 1].value) {
				num *= 2;
				myScore += num;
				int ourTarget = 2048;
				if (num == ourTarget) {
					myWin = true;
				}
				i++;
			}
			list.add(new Tile(num, currentColor));
		}
		if (list.size() == 0) {
			return oldLine;
		} else {
			ensureSize(list, 4);
			return list.toArray(new Tile[4]);
		}
	}

	//removed static keyword
	private void ensureSize(java.util.List<Tile> l, int s) {
		while (l.size() != s) {
			l.add(new Tile(currentColor));
		}
	}

}
