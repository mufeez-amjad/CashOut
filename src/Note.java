import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

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
	private CashOut game;

	public Note(CashOut c, ArrayList<Rectangle> hits){
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
		Rectangle hit = new Rectangle(x, y, width, height);
		for (int i = 0; i < hits.size(); i++){
			while (hit.intersects(hits.get(i))){
				x = rand.nextInt(1100);
				y = rand.nextInt(800);
				i = 0;
				hit = new Rectangle(x, y, width, height);
			}
		}
		
		game = c;
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
			p.getPoint();
			p.getPoint();
			if (p.getHitbox().contains(x + width/2, y + height/2)){
				l.addNotesValues(number);
				isCollected  = true;
				l.addNotesCollected(1);
				if (game.getSoundState()) playSound();
			}
		}
	}
	
	public synchronized void playSound() { //plays the sound effect
		new Thread(new Runnable() { //creates a new thread
			public void run() {
				try { //contains code that might throw an exception
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(
							this.getClass().getResource("Audio/paper.wav")); //imports the audio file
					clip.open(inputStream);
					clip.start(); //plays the sound
				} catch (Exception e) { //handles exception if the file is not found
					e.printStackTrace(); //prints the exception
				}
			}
		}).start();
	}

	public Integer getNumber() {
		return number;
	}

	public void collectAll(Player p, Level l) {
		l.addNotesValues(number);
		isCollected  = true;
		l.addNotesCollected(1);
	}
}
