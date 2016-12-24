import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Note {
	private int number;
	private BufferedImage note;
	Random rand = new Random();
	private static int[] notes = new int[4];
	private int x;
	private int y;
	private boolean collected = false;
	private int width;
	private int height;

	
	public Note(){
		try { 
			note = ImageIO.read(getClass().getResource("/Note.png"));
		} catch (IOException e) { 
			System.err.println("Note.png could not be found");
		}
		number = rand.nextInt(9);
		x = rand.nextInt(1100);
		y = rand.nextInt(900);
		width = note.getWidth();
		height = note.getHeight();
	}
	
	public void paint(Graphics2D g2d){
		g2d.setColor(Color.black);
		if (!collected){
			g2d.drawImage(note, x, y, null);
			g2d.drawString(String.valueOf(number), x + 10, y + 20);
		}
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void collect(Player p){
		if (p.getX() + p.getWidth()/2 - 5 > x && p.getY() + p.getHeight()/2 + 5 > y && p.getX() < x + width && p.getY() < y + height ){
			collected  = true;
		}
	}
}
