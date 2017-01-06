import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

public class Note {
	private int number;
	private BufferedImage note;
	Random rand = new Random();
	//private static int[] notes = new int[4];
	private int x;
	private int y;
	private boolean isCollected = false;
	private int width;
	private int height;

	public Note(){
		try { 
			note = ImageIO.read(getClass().getResource("/Images/Note.png"));
		} catch (IOException e) { 
			System.err.println("Note.png could not be found");
		}
		number = rand.nextInt(9);
		x = rand.nextInt(1100);
		y = rand.nextInt(800);
		width = note.getWidth();
		height = note.getHeight();
	}

	public void paint(Graphics2D g2d){
		g2d.setColor(Color.black);
		if (!isCollected){
			g2d.drawImage(note, x, y, null);
			g2d.setFont(CashOut.getFontSmall());
			g2d.drawString(String.valueOf(number), x + 9, y + 22);
		}
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public void collect(Player p, Level l){
		if (!isCollected){
			int pX = p.getPoint().x;
			int pY = p.getPoint().y;
			if (pX > x && pX < x + width && pY > y && pY < y + height){
				l.addNotesValues(number);
				isCollected  = true;
				l.addNotesCollected(1);
			}
		}
	}

	public Integer getNumber() {
		return number;
	}
}
