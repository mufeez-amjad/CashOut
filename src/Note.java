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
	private static int collected = 0;
	private static ArrayList<Integer> notesCollected = new ArrayList<Integer>();

	public Note(){
		try { 
			note = ImageIO.read(getClass().getResource("/Note.png"));
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
		if (!isCollected){
			if (p.getX() + p.getWidth()/2 > x && p.getY() + p.getHeight()/2 > y && p.getX() < x + width && p.getY() < y + height){
				notesCollected.add(number);
				isCollected  = true;
				collected++;
			}
		}
	}

	public static int getCollected(){
		return collected;
	}

	public static ArrayList<Integer> getNotes(){
		return notesCollected;
	}
}
