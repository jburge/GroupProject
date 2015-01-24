package gui;

import gamelogic.*;
import gui.popups.MenuPanel;
import gui.popups.PostGamePanel;
import gui.popups.ViewSelectionPanel;

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

import themes.*;
import themes.colorways.ColorAlt1;
import themes.colorways.ColorAlt2;
import themes.colorways.ColorDefault;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI extends JPanel {
	private static JFrame game;

	// private JPanel panelPost;

	private MenuPanel gameMenu;
	private PostGamePanel gamePost;
	private ViewSelectionPanel gameResults;

	private int windowWidth;
	private int windowHeight;

	private GenericGameLogic gameManager;
	private int defaultSideInput = 4;
	private boolean gameOver = false;

	private int currentSides;
	private int currentTime;

	private int newSides = 4;
	private int newTime = 0;
	private ColorSet gameColor = new ColorDefault();

	/**
	 * Create the panel.
	 */
	public GUI() {
		setFocusable(true);
		setLayout(null);
		currentSides = defaultSideInput;
		currentTime = newTime;

		windowWidth = GraphicsManager.TILE_SIZE * currentSides
				+ GraphicsManager.TILES_MARGIN * (currentSides + 1);
		windowHeight = GraphicsManager.TILE_SIZE * currentSides
				+ GraphicsManager.TILES_MARGIN * (currentSides + 5);

		gameMenu = new MenuPanel(windowWidth, windowHeight);
		createMenuFunctions();
		add(gameMenu);

		gamePost = new PostGamePanel(windowWidth, windowHeight);
		createPostGameFunctions();
		add(gamePost);

		gameResults = new ViewSelectionPanel(windowWidth, windowHeight);
		createViewFunctions();
		add(gameResults);

		gameManager = new GenericGameLogic(currentSides);

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// game logic state function
				if (!gameManager.isPaused()) {
					detectInput(e);
					gameOver = gameManager.checkState(e);
					if (gameOver) {
						displayPost();
						// displayPrompt();
					}
					repaint();
				}
			}
		});
		// now resets in gameManager constructor

		// RESIZE WINDOW FOR LENGTH
		game.setSize(windowWidth, windowHeight);
	}

	private void resetPanelBounds() {
		gameMenu.resetBounds(windowWidth, windowHeight);
		gamePost.resetBounds(windowWidth, windowHeight);
		gameResults.resetBounds(windowWidth, windowHeight);
	}

	private void createMenuFunctions() {
		gameMenu.comboColorInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switch (gameMenu.comboColorInput.getSelectedItem().toString()) {
				case "Default":
					gameColor = new ColorDefault();
					break;
				case "Blue":
					gameColor = new ColorAlt1();
					break;
				case "---":
					gameColor = new ColorAlt2();
					break;
				}
				gameManager.changeColor(gameColor);
			}
		});

		gameMenu.comboSizeInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switch (gameMenu.comboSizeInput.getSelectedItem().toString()) {
				case "3":
					newSides = 3;
					break;
				case "4":
					newSides = 4;
					break;
				case "5":
					newSides = 5;
					break;
				}
			}
		});

		gameMenu.btnStartNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removeMenu();
				if(gameManager.getScore() != 0)
					displayPost();
				else
					generateGame();
			}
		});

		gameMenu.btnResume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gameMenu.comboSizeInput.setSelectedItem(String
						.valueOf(currentSides));
				gameMenu.comboTimeInput.setSelectedItem(String
						.valueOf(currentTime));
				removeMenu();
				repaint();
			}
		});
	}

	private void createPostGameFunctions() {
		gamePost.btnDisplay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayResults();
			}
		});

		gamePost.btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				postSave(true);
			}
		});
		gamePost.btnSkip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				postSave(false);
			}
		});
		gamePost.btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removePost();
			}
		});
	}

	private void createViewFunctions() {
		gameResults.btnReturn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				removeResults();
			}
		});
	}

	private void detectInput(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			// remove this eventually
			displayPost();
			// resets game;
			break;
		case KeyEvent.VK_SPACE:
			// bring up menu
			if (gameManager.isPaused()) {
				removeMenu();
			} else {
				displayMenu();
			}
			break;
		}
	}

	private void displayMenu() {
		gameManager.pauseGame();

		gameMenu.makeVisible();
	}

	private void removeMenu() {
		gameManager.resumeGame();

		gameMenu.makeInvisible();
	}

	private void displayPost() {
		gamePost.getScore(gameManager.getScore());
		gamePost.getHighest(gameManager.getHigh());
		gameManager.pauseGame();
		gamePost.makeVisible();
	}
	
	private void postSave(boolean save){
		if(save)
			gameManager.saveGame(gamePost.textUsernameInput.getText());
		gamePost.btnDisplay.setVisible(true);
		gamePost.btnStart.setVisible(true);
		gamePost.btnSkip.setVisible(false);
		gamePost.btnSubmit.setVisible(false);
	}

	private void removePost() {
		gamePost.btnDisplay.setVisible(false);
		gamePost.btnStart.setVisible(false);
		gamePost.btnSkip.setVisible(true);
		gamePost.btnSubmit.setVisible(true);
		generateGame();
		gameManager.resumeGame();
		gamePost.resetValues();
		gamePost.makeInvisible();

	}

	private void displayResults() {
		gameResults.setArray(gameManager.getFullDataSet());
		gameResults.setInitialParameters(currentSides, currentTime);
		gameResults.makeVisible();
		gamePost.makeInvisible();
	}

	private void removeResults() {
		gameResults.makeInvisible();
		gamePost.makeVisible();
	}
	
	private void generateGame(){
		gameManager.newGame(newSides, newTime, gameColor);
		currentSides = newSides;
		currentTime = newTime;
		resizeWindow();
		resetPanelBounds();
		repaint();
	}

	private void resizeWindow() {
		windowWidth = GraphicsManager.TILE_SIZE * currentSides
				+ GraphicsManager.TILES_MARGIN * (currentSides + 1);
		windowHeight = GraphicsManager.TILE_SIZE * currentSides
				+ GraphicsManager.TILES_MARGIN * (currentSides + 5);

		game.setSize(windowWidth, windowHeight);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(gameManager.getColorSet().getWindowColor());

		g.fillRect(0, 0, this.getSize().width, this.getSize().height);

		Graphics2D g2 = GraphicsManager.initializeGraphics(g);

		for (int y = 0; y < currentSides; y++) {
			for (int x = 0; x < currentSides; x++) {
				drawTile(g2, gameManager.getTile(x + y * currentSides), x, y);
			}
		}
		g.setColor(gameColor.getForeground(0));
		GraphicsManager.printScore(g2, gameManager.getScore(), windowWidth,
				windowHeight);

		GraphicsManager.checkGameStatus(g2, gameOver, gameManager.getWin(),
				windowWidth, windowHeight);
	}

	// couldn't figure out how to move this to the graphics manager because of
	// the get font metrics line
	public void drawTile(Graphics2D g, Tile tile, int x, int y) {

		int value = tile.getValue();
		int xOffset = GraphicsManager.offsetCoors(x);
		int yOffset = GraphicsManager.offsetCoors(y);
		g.setColor(tile.getBackground());
		g.fillRoundRect(xOffset, yOffset, GraphicsManager.TILE_SIZE,
				GraphicsManager.TILE_SIZE, 14, 14);
		g.setColor(tile.getForeground());
		final int size = value < 100 ? 36 : value < 1000 ? 32 : 24;
		final Font font = new Font(GraphicsManager.FONT_NAME, Font.BOLD, size);
		g.setFont(font);

		String s = String.valueOf(value);
		//
		final FontMetrics fm = getFontMetrics(font);
		//
		final int w = fm.stringWidth(s);
		final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];

		if (value != 0)
			g.drawString(s, xOffset + (GraphicsManager.TILE_SIZE - w) / 2,
					yOffset + GraphicsManager.TILE_SIZE
							- (GraphicsManager.TILE_SIZE - h) / 2 - 2);

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
