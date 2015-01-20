package uptodate.logic;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import uptodate.color.ColorDefault;
import uptodate.color.ColorSet;

public class Tile {
	private int value;
	private ColorSet color;
	
	public Tile(){
		this(0, new ColorDefault());
	}

	public Tile(ColorSet _color) {
		this(0, _color);
	}

	public Tile(int num, ColorSet _color) {
		setValue(num);
		color = _color;
	}

	public Tile(int num, ColorDefault _color) {
		// TODO Auto-generated constructor stub
	}

	public boolean isEmpty() {
		return getValue() == 0;
	}

	public Color getForeground() {
		return color.getForeground(getValue());
	}

	public Color getBackground() {
		return color.getBackground(getValue());
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}