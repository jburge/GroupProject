package uptodate.gui;

import javax.swing.JPanel;





import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import uptodate.logic.*;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI extends JPanel {
	private static JFrame game;
	private JPanel panelMenu;

	private int windowWidth;
	private int windowHeight;
	
	private GenericGameLogic gameManager;
	private int sideInput = 4;
	private JTextField textSizeInput;
	

	/**
	 * Create the panel.
	 */
	public GUI() {
		setFocusable(true);
		setLayout(null);
		windowWidth = GraphicsManager.TILE_SIZE * sideInput + GraphicsManager.TILES_MARGIN * (sideInput + 1);
		windowHeight = GraphicsManager.TILE_SIZE * sideInput + GraphicsManager.TILES_MARGIN * (sideInput + 5);
		
		createMenuPanel();
		
		
		gameManager = new GenericGameLogic(sideInput);
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// game logic state function
				detectInput(e);
				gameManager.checkState(e);
				repaint();
				
			}
		});
		// now resets in gameManager constructor
		
		//RESIZE WINDOW FOR LENGTH
		game.setSize( windowWidth, windowHeight);
		
		
	}
	
	private void detectInput(KeyEvent e){
		switch(e.getKeyCode()){
		case KeyEvent.VK_ESCAPE:
			// resets game;
			break;
		case KeyEvent.VK_SPACE:
			//bring up menu
			if(gameManager.isPaused()){
				removeMenu();
			}
			else{
				displayMenu();
			}
			break;
		}
	}
	
	private void createMenuPanel(){
		int menuWidth = windowWidth - 100;
		int menuHeight = windowHeight - (windowHeight / 4) - 25;
		
		panelMenu = new JPanel();
		panelMenu.setBounds(50, windowHeight / 8 , menuWidth, menuHeight);
		add(panelMenu);
		panelMenu.setLayout(null);
		
		JLabel lblMenu = new JLabel("Menu");
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu.setBounds(0, 10, menuWidth, 10);
		panelMenu.add(lblMenu);
		
		JLabel lblColorway = new JLabel("Color Way:");
		lblColorway.setBounds(40, 40, menuWidth / 2 - 50, 25);
		panelMenu.add(lblColorway);
		
		JLabel lblBoardSize = new JLabel("Board Size:");
		lblBoardSize.setBounds(40, 76, menuWidth / 2 - 50, 24);
		panelMenu.add(lblBoardSize);
		
		textSizeInput = new JTextField();
		textSizeInput.setBounds(menuWidth / 2 + 10, 78, menuWidth / 2 - 30, 25);
		panelMenu.add(textSizeInput);
		textSizeInput.setColumns(10);
		
		JComboBox comboColorInput = new JComboBox();
		comboColorInput.setBounds(menuWidth / 2 + 10, 40, menuWidth / 2 - 30, 25);
		panelMenu.add(comboColorInput);
		
		JLabel lblTimeLimit = new JLabel("Time Limit:");
		lblTimeLimit.setBounds(40, 122, menuWidth / 2 - 30, 25);
		panelMenu.add(lblTimeLimit);
		
		JComboBox comboTimeInput = new JComboBox();
		comboTimeInput.setBounds(menuWidth / 2 + 10, 122, menuWidth / 2 - 30, 25);
		panelMenu.add(comboTimeInput);
		
		JButton btnStartNewGame = new JButton("Start New Game");
		btnStartNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removeMenu();
				gameManager.resetGame();
				repaint();
			}
		});
		btnStartNewGame.setBounds(menuWidth / 6, menuHeight - 90, menuWidth / 6 * 4, 40);
		panelMenu.add(btnStartNewGame);
		
		JButton btnResume = new JButton("Resume");
		btnResume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelMenu.setVisible(false);
				gameManager.resumeGame();
			}
		});
		btnResume.setBounds(menuWidth / 4, menuHeight - 40, menuWidth / 2 , 20);
		panelMenu.add(btnResume);
		
		panelMenu.setVisible(false);
	}
	
	private void displayMenu(){
		gameManager.pauseGame();
		panelMenu.setVisible(true);
	}
	
	private void removeMenu(){
		gameManager.resumeGame();
		panelMenu.setVisible(false);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(gameManager.getColorSet().getWindowColor());

		
		g.fillRect(0, 0, this.getSize().width, this.getSize().height);
		
		Graphics2D g2 = GraphicsManager.initializeGraphics(g);
		
		for (int y = 0; y < sideInput; y++) {
			for (int x = 0; x < sideInput; x++) {
				drawTile(g2, gameManager.getTile(x + y * sideInput), x, y);
			}
		}
		GraphicsManager.printScore(g2, gameManager.getScore(), windowWidth, windowHeight);
		
		GraphicsManager.checkGameStatus(g2, gameManager.getWin(), gameManager.getLose(), windowWidth, windowHeight);
	}

	//couldn't figure out how to move this to the graphics manager because of the get font metrics line
	public  void drawTile(Graphics2D g, Tile tile, int x, int y) {
		
		int value = tile.getValue();
		int xOffset = GraphicsManager.offsetCoors(x);
		int yOffset =  GraphicsManager.offsetCoors(y);
		g.setColor(tile.getBackground());
		g.fillRoundRect(xOffset, yOffset,  GraphicsManager.TILE_SIZE,  GraphicsManager.TILE_SIZE, 14, 14);
		g.setColor(tile.getForeground());
		final int size = value < 100 ? 36 : value < 1000 ? 32 : 24;
		final Font font = new Font( GraphicsManager.FONT_NAME, Font.BOLD, size);
		g.setFont(font);

		String s = String.valueOf(value);
		//
		final FontMetrics fm = getFontMetrics(font);
		//
		final int w = fm.stringWidth(s);
		final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];

		if (value != 0)
			g.drawString(s, xOffset + ( GraphicsManager.TILE_SIZE - w) / 2, yOffset +  GraphicsManager.TILE_SIZE
					- ( GraphicsManager.TILE_SIZE - h) / 2 - 2);
	

	}


	public static void main(String[] args) {
		game = new JFrame();
		game.setTitle("2048 Game");
		game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		game.setSize(340, 400);
		game.setResizable(false);

		game.getContentPane().add(new GUI());

		game.setLocationRelativeTo(null);
		game.setVisible(true);
	}
}

