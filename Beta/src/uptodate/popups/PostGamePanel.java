package uptodate.popups;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import uptodate.color.ColorAlt1;
import uptodate.color.ColorDefault;

public class PostGamePanel extends PopupPanel {
	private JLabel lblGameOver;
	private JLabel lblScore;
	private JLabel lblHigh;
	private JLabel lblUser;
	
	public JTextField textUsernameInput;
	public JButton btnSubmit;

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

		btnSubmit = new JButton("Submit");
		this.add(btnSubmit);

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

		int heightUnit = menuHeight / 10;
		int buffer = menuHeight /16;
		int lblWidth = menuWidth - buffer * 2;
		int fieldSize = heightUnit * 2 - buffer * 2;

		int scoreHeight = heightUnit * 2;
		int highHeight = scoreHeight + heightUnit * 2;
		int userHeight = highHeight + heightUnit * 2;
		int submitHeight = userHeight + heightUnit * 2;

		int txtWidth = menuWidth;

		lblGameOver.setBounds(0, 0, menuWidth, heightUnit * 2);

		lblScore.setBounds(buffer, scoreHeight, lblWidth, fieldSize);

		lblHigh.setBounds(buffer, highHeight, lblWidth, fieldSize);

		lblUser.setBounds(buffer, userHeight, txtWidth / 2, 25);
		textUsernameInput.setBounds(menuWidth / 2 + 10, userHeight,
				menuWidth / 2 - 30, fieldSize);

		btnSubmit.setBounds(menuWidth / 6, submitHeight, menuWidth / 6 * 4,
				heightUnit * 2 - buffer );

	}
}
