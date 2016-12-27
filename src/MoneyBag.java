import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class MoneyBag {
	
	private BufferedImage bag;
	private int value;
	Random rand = new Random();
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean isCollected = false;
	private static int totalValue = 0;
	
	public MoneyBag(){
		try { 
			bag = ImageIO.read(getClass().getResource("/MoneyBag.png"));
		} catch (IOException e) { 
			System.err.println("Note.png could not be found");
		}
		value = rand.nextInt(3 - 1 + 1) + 1; //between 1 and 10
		value *= 50;
		System.out.println(value);
		totalValue += value;
		x = rand.nextInt(1100);
		y = rand.nextInt(800);
		width = bag.getWidth();
		height = bag.getHeight();
	}
	
	public void paint(Graphics2D g2d){
		if (!isCollected){
			g2d.drawImage(bag, x, y, null);
		}
	}
	
	public void collect(Player p){
		if (!isCollected){
			if (p.getX() + p.getWidth()/2 > x && p.getY() + p.getHeight()/2 > y && p.getX() < x + width && p.getY() < y + height){
				isCollected  = true;
				CashOut.addScore(value);
				System.out.println(value);
			}
		}
	}
	
	public static int getTotalValue(){
		return totalValue;
	}

}
