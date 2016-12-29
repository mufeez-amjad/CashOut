import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Laser {
	private BufferedImage laser;
	private int x;
	private int y;
	private int angle;
	private boolean active = true;
	
	public Laser(int x, int y, int a){
		try { 
			laser = ImageIO.read(getClass().getResource("/Laser.png"));
		} catch (IOException e) { 
			System.err.println("Laser.png could not be found");
		}
		
		this.x = x;
		this.y = y;
		angle = a;
	}
	
	public void paint(Graphics2D g2d){
		double rotationRequired = Math.toRadians (angle);
		double locationX = laser.getWidth() / 2;
		double locationY = laser.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		if (active) g2d.drawImage(op.filter(laser, null), x, y, null);
		//g2d.fillRect(x, y, 25, 120);
	}
	
	public void update(Player p, CashOut c){
		if (p.getX() > x && p.getX() < x + 100 && p.getY() > y && p.getY() < y + p.getWidth()){
			c.addSuspicion(250);
		}
	}
	
}
