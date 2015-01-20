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

public class GUI extends JPanel {
	private static JFrame game;
	private static final Color BG_COLOR = new Color(0xbbada0);
	private static final String FONT_NAME = "Arial";
	private static final int TILE_SIZE = 64;
	private static final int TILES_MARGIN = 16;
	private int windowWidth;
	private int windowHeight;
	private GenericGameLogic gameManager;
	private int sideInput = 4;
	

	/**
	 * Create the panel.
	 */
	public GUI() {
		setFocusable(true);
		gameManager = new GenericGameLogic(sideInput);
		windowWidth = TILE_SIZE * sideInput + TILES_MARGIN * (sideInput + 1);
		windowHeight = TILE_SIZE * sideInput + TILES_MARGIN * (sideInput + 5);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// game logic state function
				gameManager.checkState(e);
				repaint();
			}
		});
		// now resets in gameManager constructor
		
		//RESIZE WINDOW FOR LENGTH
		game.setSize( windowWidth, windowHeight);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(BG_COLOR);
		g.fillRect(0, 0, this.getSize().width, this.getSize().height);
		for (int y = 0; y < sideInput; y++) {
			for (int x = 0; x < sideInput; x++) {
				drawTile(g, gameManager.getTile(x + y * sideInput), x, y);
			}
		}
	}

	private void drawTile(Graphics g2, Tile tile, int x, int y) {
		Graphics2D g = ((Graphics2D) g2);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_NORMALIZE);
		int value = tile.getValue();
		int xOffset = offsetCoors(x);
		int yOffset = offsetCoors(y);
		g.setColor(tile.getBackground());
		g.fillRoundRect(xOffset, yOffset, TILE_SIZE, TILE_SIZE, 14, 14);
		g.setColor(tile.getForeground());
		final int size = value < 100 ? 36 : value < 1000 ? 32 : 24;
		final Font font = new Font(FONT_NAME, Font.BOLD, size);
		g.setFont(font);

		String s = String.valueOf(value);
		final FontMetrics fm = getFontMetrics(font);

		final int w = fm.stringWidth(s);
		final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];

		if (value != 0)
			g.drawString(s, xOffset + (TILE_SIZE - w) / 2, yOffset + TILE_SIZE
					- (TILE_SIZE - h) / 2 - 2);

		g.setFont(new Font(FONT_NAME, Font.PLAIN, 18));
		g.drawString("Score: " + gameManager.getScore(), (windowWidth / 2) + 30, windowHeight - 35);
		
		checkGameStatus(g);

	}
	
	private void checkGameStatus(Graphics2D g){
		if (gameManager.getWin() || gameManager.getLose()) {
			g.setColor(new Color(255, 255, 255, 30));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(new Color(78, 139, 202));
			g.setFont(new Font(FONT_NAME, Font.BOLD, 48));
			if (gameManager.getWin()) {
				g.drawString("You won!", windowWidth / 2 - 102, windowHeight / 2 - 50);
			}
			else if (gameManager.getLose()) {
				g.drawString("Game over!", windowWidth / 2 - 120, windowHeight / 2 - 100);
				g.drawString("You lose!", windowWidth / 2 - 106, windowHeight / 2 - 50);
			}
			
			g.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
			g.setColor(new Color(128, 128, 128, 128));
			g.drawString("Press ESC to play again", 40, getHeight() - 30);
		}
	}

	private static int offsetCoors(int arg) {
		return arg * (TILES_MARGIN + TILE_SIZE) + TILES_MARGIN;
	}

	public static void main(String[] args) {
		game = new JFrame();
		game.setTitle("2048 Game");
		game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		game.setSize(340, 400);
		game.setResizable(false);

		game.add(new GUI());

		game.setLocationRelativeTo(null);
		game.setVisible(true);
	}

}

