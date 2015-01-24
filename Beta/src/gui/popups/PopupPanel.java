package gui.popups;

import javax.swing.JPanel;

public abstract class PopupPanel extends JPanel{
	int menuWidthBuffer;
	int menuHeightBuffer;
	int menuWidth ;
	int menuHeight;
	
	public PopupPanel(int windowWidth, int windowHeight){
		menuWidthBuffer = windowWidth / 9;
		menuHeightBuffer = windowHeight / 9;
		menuWidth = windowWidth - menuWidthBuffer * 2;
		menuHeight = windowHeight - menuHeightBuffer * 2;
	}
	
	public void makeVisible(){
		this.setVisible(true);
	}
	
	public void makeInvisible(){
		this.setVisible(false);
	}
	
}
