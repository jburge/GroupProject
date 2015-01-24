package gui.popups;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import themes.colorways.ColorAlt1;
import themes.colorways.ColorDefault;

public class PostGamePanel extends PopupPanel {
	private JLabel lblGameOver;
	private JLabel lblScore;
	private JLabel lblHigh;
	private JLabel lblUser;
	
	public JTextField textUsernameInput;
	public JButton btnSubmit;
	public JButton btnStart;
	public JButton btnSkip;
	public JButton btnDisplay;


	public PostGamePanel(int windowWidth, int windowHeight) {
		super(windowWidth, windowHeight);
		this.setLayout(null);

		lblGameOver = new JLabel("Game Over");
		lblGameOver.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(lblGameOver);

		lblScore = new JLabel("Score: ");
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(lblScore);

		lblHigh = new JLabel("Highest Tile: ");
		lblHigh.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(lblHigh);

		lblUser = new JLabel("Username: ");
		lblUser.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(lblUser);

		textUsernameInput = new JTextField();
		this.add(textUsernameInput);
		
		btnDisplay = new JButton("View Standings");
		this.add(btnDisplay);
		btnDisplay.setVisible(false);

		btnSubmit = new JButton("Submit");
		this.add(btnSubmit);
		
		btnStart = new JButton("Start");
		this.add(btnStart);
		btnStart.setVisible(false);
		
		
		btnSkip = new JButton("Skip");
		this.add(btnSkip);

		resetBounds(windowWidth, windowHeight);
		setVisible(false);
	}

	public void getScore(int score) {
		lblScore.setText(lblScore.getText() + score);
	}

	public void getHighest(int high) {
		lblHigh.setText(lblHigh.getText() + high);
	}
	
	public void resetValues(){
		lblScore.setText("Score: ");
		lblHigh.setText("Highest Tile: ");
	}

	public void resetBounds(int windowWidth, int windowHeight) {
		int menuWidthBuffer = windowWidth / 9;
		int menuHeightBuffer = windowHeight / 9;
		int menuWidth = windowWidth - menuWidthBuffer * 2;
		int menuHeight = windowHeight - menuHeightBuffer * 2;

		this.setBounds(menuWidthBuffer, menuHeightBuffer - 25, menuWidth,
				menuHeight);

		int heightUnit = menuHeight / 7;
		int buffer = menuHeight / 16;
		int lblWidth = menuWidth - buffer * 2;
		int fieldSize = heightUnit - buffer;

		int scoreHeight = heightUnit + buffer;
		int highHeight = scoreHeight + heightUnit - buffer;
		int userHeight = highHeight + heightUnit;
		//int displayHeight = userHeight + heightUnit;
		int submitHeight = userHeight + heightUnit + buffer;
		int skipHeight = submitHeight + heightUnit * 2 - buffer;

		int txtWidth = menuWidth;

		lblGameOver.setBounds(0, 0, menuWidth, heightUnit);

		lblScore.setBounds(buffer, scoreHeight, lblWidth, fieldSize);

		lblHigh.setBounds(buffer, highHeight, lblWidth, fieldSize);

		lblUser.setBounds(buffer, userHeight, txtWidth / 2, 25);
		textUsernameInput.setBounds(menuWidth / 2 + 10, userHeight,
				menuWidth / 2 - 30, fieldSize);
		

		btnSubmit.setBounds(menuWidth / 6, submitHeight, menuWidth / 6 * 4,
				heightUnit * 2 - buffer * 2);
		btnStart.setBounds(menuWidth / 6, submitHeight, menuWidth / 6 * 4,
				heightUnit * 2 - buffer * 2);
		
		btnSkip.setBounds(menuWidth / 4, skipHeight, menuWidth / 2, fieldSize);
		btnDisplay.setBounds(menuWidth / 4, skipHeight, menuWidth / 2, fieldSize);


	}
}
