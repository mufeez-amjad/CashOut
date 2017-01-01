import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;

public class Officer {
	private BufferedImage officer;
	private int x;
	private int y;
	private Random rand = new Random();
	private double angle;
	private int orientation;
	private boolean dead = false;
	private BufferedImage blood;

	public Officer(){
		try { 
			officer = ImageIO.read(getClass().getResource("/Images/Officer.png"));
		} catch (IOException e) { 
			System.err.println("Officer.png could not be found");
		}

		try { 
			blood = ImageIO.read(getClass().getResource("/Images/blood.png"));
		} catch (IOException e) { 
			System.err.println("Officer.png could not be found");
		}

		//this.x = x;
		//this.y = y;
		orientation = rand.nextInt(3);
		if (orientation == 1) angle = 90;
		if (orientation == 2) angle = 180;
		if (orientation == 3) angle = 0;

		x = rand.nextInt(1100);
		y = rand.nextInt(800);
	}

	public void paint(Graphics2D g2d){
		double rotationRequired = Math.toRadians (angle);
		double locationX = officer.getWidth() / 2;
		double locationY = officer.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		if (!dead) g2d.drawImage(op.filter(officer, null), x, y, null);
		else g2d.drawImage(op.filter(blood, null), x, y, null);
	}

	public void collision(ArrayList<Bullet> nb, Player p){
		/*for (Bullet b: nb){
			if (b.getX() > x && b.getX() < x + officer.getWidth() && b.getY() > y && b.getY() < y + officer.getHeight()){
				dead = true;
			}
		}*/
		if (!dead) {
			for (Iterator<Bullet> iterator = nb.iterator(); iterator.hasNext();) {
				Bullet b = iterator.next();
				if (b.getX() > x && b.getX() < x + officer.getWidth() && b.getY() > y && b.getY() < y + officer.getHeight()){
					dead = true;
					iterator.remove();
				}
			}
		}
	}
	
	public void update(Player p, CashOut c){
		if (!dead){
			if (p.getX() > x - 200 && p.getX() < x + 200 && p.getY() > y - 200 && p.getY() < y + 200){
				//System.out.println("CLOSE");
				c.addSuspicion(3);
			}
		}
	}

	public BufferedImage getImage() {
		return officer;
	}
}

