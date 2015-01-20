package game2048;

import java.awt.Color;

abstract public class ColorSet {
	
	public ColorSet(){}

	public abstract Color getBackground(int value);

	public abstract Color getForeground(int value);

}