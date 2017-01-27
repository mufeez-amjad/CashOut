import java.awt.Color;
import java.awt.FontMetrics;
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

public class MoneyBag {

	private BufferedImage bag;
	private int value;
	private Random rand = new Random();
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean isCollected = false;

	private int counter = 0;
	private CashOut game;

	public MoneyBag(CashOut c, ArrayList<Rectangle> hits){
		try { 
			bag = ImageIO.read(getClass().getResource("/Images/MoneyBag.png"));
		} catch (IOException e) { 
			System.err.println("Note.png could not be found");
		}
		value = rand.nextInt(3 - 1 + 1) + 1; //between 1 and 10
		value *= 50;
		x = rand.nextInt(1100);
		y = rand.nextInt(800);
		width = bag.getWidth();
		height = bag.getHeight();
		Rectangle hit = new Rectangle(x, y, width + 100, height + 100);
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
		if (isCollected && counter < 100){
			g2d.setFont(CashOut.getFontMedium());
			FontMetrics fontMetrics  = g2d.getFontMetrics(CashOut.getFontMedium());//used to calculate based on the measurements of the font
			int stringLength = fontMetrics.stringWidth("$" + String.valueOf(value)); //horizontal length of String
			Color money = Color.decode("0x065C27");
			Color moneyTrans = new Color(money.getRed(), money.getGreen(), money.getBlue(), 200 - counter*2);
			g2d.setColor(moneyTrans);
			g2d.drawString("$" + String.valueOf(value), CashOut.getFrameWidth()/2 - (stringLength/2), CashOut.getFrameHeight()/2 - counter/2);
			counter++;
		}

		if (!isCollected){
			g2d.drawImage(bag, x, y, null);
		}


	}

	public void collect(Player p){
		
		if (!isCollected){ // pX > x && pY > y && pX < x + width && pY < y + height
			if (p.getHitbox().contains(x + width/2, y + height/2)){
				isCollected  = true;
				game.getLevels().getCurrent().addScore(value);
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
							this.getClass().getResource("Audio/cha-ching.wav")); //imports the audio file
					clip.open(inputStream);
					clip.start(); //plays the sound
				} catch (Exception e) { //handles exception if the file is not found
					e.printStackTrace(); //prints the exception
				}
			}
		}).start();
	}

	public int getValue(){
		return value;
	}

	public BufferedImage getImage(){
		return bag;
	}

}
