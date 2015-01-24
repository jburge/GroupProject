package gui;

import gamelogic.Tile;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class GraphicsManager {
	public static final String FONT_NAME = "Arial";
	public static final int TILE_SIZE = 64;
	public static final int TILES_MARGIN = 16;
	
	public static Graphics2D initializeGraphics(Graphics oneDG){
		Graphics2D g = ((Graphics2D) oneDG);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_NORMALIZE);
		return g;
	}


	
	public static void printScore(Graphics2D g, int score, int windowWidth, int windowHeight){
		g.setFont(new Font(FONT_NAME, Font.PLAIN, 18));
		g.drawString("Score: " + score, (windowWidth / 2) + 30, windowHeight - 35);
	}
	
	public static void checkGameStatus(Graphics2D g, boolean gameEnd, boolean win, int windowWidth, int windowHeight){
		if (gameEnd) {
			g.setColor(new Color(255, 255, 255, 30));
			g.fillRect(0, 0, windowWidth, windowHeight);
			g.setColor(new Color(78, 139, 202));
			g.setFont(new Font(FONT_NAME, Font.BOLD, 48));
			if (win) {
				g.drawString("You won!", windowWidth / 2 - 102, windowHeight / 2 - 50);
			}
			else {
				g.drawString("Game over!", windowWidth / 2 - 120, windowHeight / 2 - 100);
				g.drawString("You lose!", windowWidth / 2 - 106, windowHeight / 2 - 50);
			}
			
			g.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
			g.setColor(new Color(128, 128, 128, 128));
			g.drawString("Press ESC to play again", 40, windowHeight - 30);
		}
	}
	
	public static int offsetCoors(int arg) {
		return arg * (TILES_MARGIN + TILE_SIZE) + TILES_MARGIN;
	}

	
}
