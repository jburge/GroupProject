package uptodate.color;

import java.awt.Color;

public class ColorAlt2 extends ColorSet{

	public ColorAlt2(){}
	
	public  Color getBackground(int value) {
		switch (value) {
		case 2:
			return new Color(0x8C51A6);
		case 4:
			return new Color(0x573368);
		case 8:
			return new Color(0x1099A3);
		case 16:
			return new Color(0x042D30);
		case 32:
			return new Color(0x005b96);
		case 64:
			return new Color(0x005187);
		case 128:
			return new Color(0x004878);
		case 256:
			return new Color(0x003f69);
		case 512:
			return new Color(0x00365a);
		case 1024:
			return new Color(0x002d4b);
		case 2048:
			return new Color(0x00243c);
		}
		return new Color(0xb2cddf);
	}
	
	public Color getForeground(int value){
		return value < 16 ? new Color(0x9DD6EC) : new Color(0x180630);
	}
	
	public Color getWindowColor(){
		return new Color(0xE3E3C6);
	}


}
