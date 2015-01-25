package themes.colorways;


import java.awt.Color;

import themes.ColorSet;

public class ColorMod extends ColorSet{

	public ColorMod(){}
	
	public  Color getBackground(int value) {
		switch (value) {
		case 2:
			return new Color(0x3298c9);
		case 4:
			return new Color(0xf2b179);
		case 8:
			return new Color(0x02334a);
		case 16:
			return new Color(0xdc8659);
		case 32:
			return new Color(0x592487);
		case 64:
			return new Color(0x667ba1);
		case 128:
			return new Color(0xedcf72);
		case 256:
			return new Color(0xedcc61);
		case 512:
			return new Color(0xec8300);
		case 1024:
			return new Color(0x00a5a9);
		case 2048:
			return new Color(0xd024c9);
		}
		return new Color(0x2a5da3);
	}
	
	public Color getForeground(int value){
		return value < 16 ? new Color(0x000000) : new Color(0x000000);
	}
	
	public Color getWindowColor(){
		return new Color(0x081545);
	}
	
	public Color getText(){
		return new Color(0xffffff);
	}

}

