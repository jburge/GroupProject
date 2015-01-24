package gui.popups;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import themes.colorways.ColorAlt1;
import themes.colorways.ColorDefault;

public class MenuPanel extends PopupPanel{

	private JLabel lblMenu;
	private JLabel lblColorway;
	private JLabel lblBoardSize;
	private JLabel lblTimeLimit;
	public JComboBox comboColorInput;
	public JComboBox comboSizeInput;
	public JComboBox comboTimeInput;
	public JButton btnStartNewGame;
	public JButton btnResume; 

	
	public MenuPanel(int windowWidth, int windowHeight)
		{
		super(windowWidth, windowHeight);
		this.setLayout(null);
		
		lblMenu = new JLabel("Menu");
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(lblMenu);
		
		lblColorway = new JLabel("Color Way:");
		this.add(lblColorway);
		comboColorInput = new JComboBox();
		comboColorInput = ComboBoxSeed.seedColor(comboColorInput);
		this.add(comboColorInput);
		
		lblBoardSize = new JLabel("Board Size:");
		this.add(lblBoardSize);
		
		comboSizeInput = new JComboBox();
		comboSizeInput = ComboBoxSeed.seedSize(comboSizeInput);
		comboSizeInput.setSelectedItem("4");
		this.add(comboSizeInput);
		
		lblTimeLimit = new JLabel("Time Limit:");
		this.add(lblTimeLimit);
		comboTimeInput = new JComboBox();
		comboTimeInput.addItem("No Limit");
		this.add(comboTimeInput);
		
		btnStartNewGame = new JButton("Start New Game");
		this.add(btnStartNewGame);
		
		btnResume = new JButton("Resume");
		this.add(btnResume);
		
		resetBounds(windowWidth, windowHeight);
		this.setVisible(false);
	}

	
	public void resetBounds(int windowWidth, int windowHeight){
		int menuWidthBuffer = windowWidth / 9;
		int menuHeightBuffer = windowHeight / 9;
		int menuWidth = windowWidth - menuWidthBuffer * 2;
		int menuHeight = windowHeight - menuHeightBuffer * 2;
		
		
		int heightUnit = menuHeight / 12;
		int buffer = menuHeight / 24;
		int fieldSize = heightUnit * 2 - buffer * 2;
		
		int colorHeight = heightUnit * 2;
		int sizeHeight = colorHeight + heightUnit * 2;
		int timeHeight = sizeHeight + heightUnit * 2;
		int startHeight = timeHeight + heightUnit * 2;
		int resumeHeight = startHeight + heightUnit * 3 - buffer;
		
		int txtWidth = menuWidth;
		
		this.setBounds(menuWidthBuffer, menuHeightBuffer -25, menuWidth, menuHeight);

		lblMenu.setBounds(0, 0, menuWidth, heightUnit * 2);
		
		lblColorway.setBounds(40, colorHeight, txtWidth, fieldSize);
		comboColorInput.setBounds(menuWidth / 2 + 10, colorHeight, menuWidth / 2 - 30, fieldSize);

		lblBoardSize.setBounds(40, sizeHeight, txtWidth, fieldSize);
		comboSizeInput.setBounds(menuWidth / 2 + 10, sizeHeight, menuWidth / 2 - 30, fieldSize);

		lblTimeLimit.setBounds(40, timeHeight,txtWidth, 25);
		comboTimeInput.setBounds(menuWidth / 2 + 10, timeHeight, menuWidth / 2 - 30, fieldSize);
		
		btnStartNewGame.setBounds(menuWidth / 6, startHeight, menuWidth / 6 * 4, heightUnit * 3 - buffer * 2);
		btnResume.setBounds(menuWidth / 4, resumeHeight, menuWidth / 2 , fieldSize);
	}
	
}
