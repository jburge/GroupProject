package game2048;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Tile {
	int value;
	private ColorSet color;

	public Tile(ColorSet _color) {
		this(0, _color);
	}

	public Tile(int num, ColorSet _color) {
		value = num;
		color = _color;
	}

	public boolean isEmpty() {
		return value == 0;
	}

	public Color getForeground() {
		return color.getForeground(value);
	}

	public Color getBackground() {
		return color.getBackground(value);
	}
}
