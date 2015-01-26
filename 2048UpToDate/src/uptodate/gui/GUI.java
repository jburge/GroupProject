package uptodate.gui;

import javax.naming.TimeLimitExceededException;
import javax.swing.JPanel;







import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import uptodate.color.*;
import uptodate.gui.*;
import uptodate.logic.*;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI extends JPanel implements Runnable{
	private static JFrame game;
	private JPanel panelMenu;
	private JLabel lblMenu;
	private JLabel lblColorway;
	private JLabel lblBoardSize;
	private JLabel lblTimeLimit;
	private JComboBox comboColorInput;
	private JComboBox comboSizeInput;
	private JComboBox comboTimeInput;
	private JButton btnStartNewGame;
	private JButton btnResume; 

	public int windowWidth;
	public int windowHeight;
	
	private GenericGameLogic gameManager;
	private int defaultSideInput = 4;
	
	private int currentSides;
	private int currentTime;
	
	private int newSides;
	private int newTime;
	private ColorSet gameColor = new ColorDefault();
	
    public time tt=null;
    private Thread playerTread;
    private String s;
    public int playerTime=100;
    private String playerTimeString;
	

	/**
	 * Create the panel.
	 */
	public GUI() {
		setFocusable(true);
		setLayout(null);
		currentSides = defaultSideInput;
		
		windowWidth = GraphicsManager.TILE_SIZE * currentSides + GraphicsManager.TILES_MARGIN * (currentSides + 1);
		windowHeight = GraphicsManager.TILE_SIZE * currentSides + GraphicsManager.TILES_MARGIN * (currentSides + 5);
		
		createMenuPanel();
		playerTread=new Thread(this);
		
		gameManager = new GenericGameLogic(currentSides);
		
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
	
	private void resizeMenu(){
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
		
		panelMenu.setBounds(menuWidthBuffer, menuHeightBuffer -25, menuWidth, menuHeight);

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
	
	private void createMenuPanel(){
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
		
	
		
		panelMenu = new JPanel();
		add(panelMenu);
		panelMenu.setLayout(null);
		
		lblMenu = new JLabel("Menu");
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		panelMenu.add(lblMenu);
		
		lblColorway = new JLabel("Color Way:");
		panelMenu.add(lblColorway);
		
		comboColorInput = new JComboBox();
		comboColorInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switch(comboColorInput.getSelectedItem().toString()){
				case "Default":
					gameColor = new ColorDefault();
					break;
				case "Blue":
					gameColor = new ColorAlt1();
					break;
				
				}
			}
		});
		comboColorInput.addItem("Default");
		comboColorInput.addItem("Blue");
		panelMenu.add(comboColorInput);
		
		lblBoardSize = new JLabel("Board Size:");
		panelMenu.add(lblBoardSize);
		
		comboSizeInput = new JComboBox();
		comboSizeInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switch(comboSizeInput.getSelectedItem().toString()){
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
		comboSizeInput.addItem("3");
		comboSizeInput.addItem("4");
		comboSizeInput.addItem("5");
		comboSizeInput.setSelectedItem("4");
		panelMenu.add(comboSizeInput);
		
		lblTimeLimit = new JLabel("Time Limit:");
		panelMenu.add(lblTimeLimit);
		
		comboTimeInput = new JComboBox();
		comboTimeInput.addItem("10");
		comboTimeInput.addItem("20");
		panelMenu.add(comboTimeInput);
		
		btnStartNewGame = new JButton("Start New Game");
		btnStartNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				playerTread.start();
				playerTimeString=comboTimeInput.getSelectedItem().toString();
				System.out.println("setTime:"+playerTimeString);
				playerTime=Integer.parseInt(playerTimeString);
				removeMenu();
				gameManager.newGame(newSides, newTime, gameColor);
				currentSides = newSides;
				currentTime = newTime;
				resizeWindow();
				resizeMenu();
				repaint();
				
			}
		});
		panelMenu.add(btnStartNewGame);
		
		btnResume = new JButton("Resume");
		btnResume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelMenu.setVisible(false);
				gameManager.resumeGame();
			}
		});
		panelMenu.add(btnResume);
		
		resizeMenu();
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

	private void resizeWindow(){
		windowWidth = GraphicsManager.TILE_SIZE * currentSides + GraphicsManager.TILES_MARGIN * (currentSides + 1);
		windowHeight = GraphicsManager.TILE_SIZE * currentSides + GraphicsManager.TILES_MARGIN * (currentSides + 5);
		
		game.setSize( windowWidth, windowHeight);
	}
	
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
		GraphicsManager.printScore(g2, gameManager.getScore(), windowWidth, windowHeight);
		GraphicsManager.printTime(g2, playerTime, windowWidth, windowHeight);
		GraphicsManager.checkGameStatus(g2, gameManager.getWin(), gameManager.getLose(), windowWidth, windowHeight,playerTime);
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

	public void run(){
		while (true){
			playerTime--;
        try {
            	Thread.sleep(1000);
        	} catch (InterruptedException e) {
        		e.printStackTrace();
        	}
        this.repaint();
        //System.exit(0);
    }
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

