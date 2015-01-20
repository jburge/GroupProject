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
		windowWidth = GraphicsManager.TILE_SIZE * sideInput + GraphicsManager.TILES_MARGIN * (sideInput + 1);
		windowHeight = GraphicsManager.TILE_SIZE * sideInput + GraphicsManager.TILES_MARGIN * (sideInput + 5);
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

		game.add(new GUI());

		game.setLocationRelativeTo(null);
		game.setVisible(true);
	}

}

