package uptodate.logic;


import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import uptodate.color.*;


public class GenericGameLogic {
	private int sideLength;
	private Tile[] myTiles;
	private ColorSet currentColor;
	
	private DataManager dataManager;

	boolean myWin = false;
	boolean pause = false;
	int myScore = 0;
	int highestTile = 0;
	int timeLimit = 0;

	public GenericGameLogic() {
		this(4);
	}
	
	public GenericGameLogic(int length){
		dataManager = new DataManager();
		currentColor = (ColorSet) new ColorDefault();
		sideLength = length;
		resetGame();
	}
	
	public GenericGameLogic(int length, ColorSet color){
		dataManager = new DataManager();
		currentColor = color;
		sideLength = length;
		resetGame();
	}

	public boolean getWin() {
		return myWin;
	}
	
	public ColorSet getColorSet(){
		return currentColor;
	}
	
	public int getHigh(){
		return highestTile;
	}
	// reassigns Tiles with new Color
	public void changeColor(ColorSet change){
		currentColor = change;
		for(Tile t: myTiles){
			t.changeTileColor(currentColor);
		}
	}
	
	public ArrayList<SaveEntry> getFullDataSet(){
		return dataManager.getSet();
	}
	
	public void newGame(int length, int time, ColorSet color){
		sideLength = length;
		currentColor = color;
		resetGame();
	}

	public int getScore() {
		return myScore;
	}

	public void pauseGame(){
		pause = true;
	}
	
	public void resumeGame(){
		pause = false;
	}
	
	public boolean isPaused(){
		return pause;
	}
	
	private void resetGame() {
		myScore = 0;
		highestTile = 0;
		myWin = false;
		pause = false;
		myTiles = new Tile[sideLength * sideLength];
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
			emptyTime.setValue(Math.random() < 0.9 ? 2 : 4);
		}
	}

	private List<Tile> availableSpace() {
		final List<Tile> list = new ArrayList<Tile>(sideLength * sideLength);
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
	
	public void saveGame(String username){
		dataManager.saveGame(new SaveEntry(username, sideLength, highestTile, myScore, timeLimit));
		dataManager.saveData();
	}

	// dont know what to pass yet
	// might need return as well
	public boolean checkState(KeyEvent e) {
		if(!pause){
			switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				resetGame();
				break;
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
		if(myWin){
			return true;
		}
		if (!canMove()) {
			return true;
		}
		return false;
	}

	boolean canMove() {
		if (!isFull()) {
			return true;
		}
		for (int x = 0; x < sideLength; x++) {
			for (int y = 0; y < sideLength; y++) {
				Tile t = tileAt(x, y);
				//not sure what to do here
				if ((x < sideLength - 1 && t.getValue() == tileAt(x + 1, y).getValue())
						|| ((y < sideLength - 1) && t.getValue() == tileAt(x, y + 1).getValue())) {
					return true;
				}
			}
		}

		return false;
	}

	private Tile tileAt(int x, int y) {
		return myTiles[x + y * sideLength];
	}

	// shifting tiles
	public void left() {
		boolean needAddTile = false;
		for (int i = 0; i < sideLength; i++) {
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
		Tile[] newTiles = new Tile[sideLength * sideLength];
		int offsetX = sideLength - 1, offsetY = sideLength - 1;
		if (angle == 90) {
			offsetY = 0;
		} else if (angle == 270) {
			offsetX = 0;
		}

		double rad = Math.toRadians(angle);
		int cos = (int) Math.cos(rad);
		int sin = (int) Math.sin(rad);
		for (int x = 0; x < sideLength; x++) {
			for (int y = 0; y < sideLength; y++) {
				int newX = (x * cos) - (y * sin) + offsetX;
				int newY = (x * sin) + (y * cos) + offsetY;
				newTiles[(newX) + (newY) * sideLength] = tileAt(x, y);
			}
		}
		return newTiles;
	}

	// get/set line

	public Tile[] getLine(int index) {
		Tile[] result = new Tile[sideLength];
		for (int i = 0; i < sideLength; i++) {
			result[i] = tileAt(i, index);
		}
		return result;
	}

	public Tile getTile(int index) {
		return myTiles[index];
	}

	private void setLine(int index, Tile[] re) {
		System.arraycopy(re, 0, myTiles, index * sideLength, sideLength);
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
			if (line1[i].getValue() != line2[i].getValue()) {
				return false;
			}
		}
		return true;
	}

	private Tile[] moveLine(Tile[] oldLine) {
		LinkedList<Tile> l = new LinkedList<Tile>();
		for (int i = 0; i < sideLength; i++) {
			if (!oldLine[i].isEmpty())
				l.addLast(oldLine[i]);
		}
		if (l.size() == 0) {
			return oldLine;
		} else {
			Tile[] newLine = new Tile[sideLength];
			ensureSize(l, sideLength);
			for (int i = 0; i < sideLength; i++) {
				newLine[i] = l.removeFirst();
			}
			return newLine;
		}
	}

	private Tile[] mergeLine(Tile[] oldLine) {
		LinkedList<Tile> list = new LinkedList<Tile>();
		for (int i = 0; i < sideLength && !oldLine[i].isEmpty(); i++) {
			int num = oldLine[i].getValue();
			if (i < sideLength - 1 && oldLine[i].getValue() == oldLine[i + 1].getValue()) {
				num *= 2;
				myScore += num;
				if(num > highestTile){
					highestTile = num;
				}
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
			ensureSize(list, sideLength);
			return list.toArray(new Tile[sideLength]);
		}
	}

	//removed static keyword
	private void ensureSize(java.util.List<Tile> l, int s) {
		while (l.size() != s) {
			l.add(new Tile(currentColor));
		}
	}

}
