import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public class Bullet
{  
	private BufferedImage bullet = null;
	private double x, y;
	private double angle;
	private Stopwatch timer = new Stopwatch();

	public Bullet(int x, int y, double angle)
	{   
		try { 
			bullet = ImageIO.read(getClass().getResource("/Images/Bullet.png"));
		} catch (IOException e) { 
			System.err.println("Bullet.png could not be found");
		}

		timer.start();
		this.x=x;
		this.y=y;
		this.angle=angle+90;
	}

	public void paint(Graphics2D g2d)
	{
		//rotates the bullet
		angle-=90;

		double rotationRequired = Math.toRadians(angle);
		double locationX = bullet.getWidth() / 2;
		double locationY = bullet.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

		// Drawing the rotated image at the required drawing locations
		int xB = (int) this.x;
		int yB = (int) this.y;
		//g.drawImage(img2, x - 150, y - 150, null);
		g2d.drawImage(op.filter(bullet, null), xB, yB, null);
		//Change the angle back to the original for the bullet to travel in the correct direction
		angle+=90;

	}

	public void move()
	{
		//Move the projectile in the direction it is facing
		x+=-10* Math.cos(Math.toRadians(angle));
		y+=-10* Math.sin(Math.toRadians(angle));
	}


	//Returns how long the bullet has been in existence
	public long getTime()
	{
		return timer.getElapsedTime();
	}

	public int getX()//returns the x position of the bullet for collision detection
	{
		return (int)x;
	}

	public int getY()//returns the y position of the bullet for collision detection
	{
		return (int)y;
	}

	public int getWidth()//returns the width of the sprite for collision detection
	{
		return bullet.getWidth();
	}

	public int getHeight()//returns the height of the sprite for collision detection
	{
		return bullet.getHeight();
	}

	public int getAngle()//returns the angle of the bullet
	{
		return (int)angle;
	}
}