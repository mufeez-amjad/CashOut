import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public class Bullet
{  
	//width = 6px, height = 8px
	private BufferedImage bullet = null;
	private BufferedImage img2 = null;
	private double x, y;
	private double angle;
	//Creates an instance of timer since each bullet is supposed to only last 1.5 seconds
	private Stopwatch timer = new Stopwatch();

	public Bullet(int x, int y, double angle)
	{   
		//Get the image of the normal bullet
		try { 
			bullet = ImageIO.read(getClass().getResource("/Images/Bullet.png"));
		} catch (IOException e) { 
			System.err.println("Bullet.png could not be found");
		}
		//Sets the position and direction of the bullet relative to the spacecraft when it is created
		timer.start();
		this.x=x;
		this.y=y;
		this.angle=angle+90;
	}

	public void paint(Graphics2D g2d)
	{
		//Since the bullet sprite is not always oriented in the direction of the shot, orient it properly
		angle-=90;

		double rotationRequired = Math.toRadians(angle);
		double locationX = bullet.getWidth() / 2;
		double locationY = bullet.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

		// Drawing the rotated image at the required drawing locations
		int x = (int) this.x;
		int y = (int) this.y;
		//g.drawImage(img2, x - 150, y - 150, null);
		g2d.drawImage(op.filter(bullet, null), x, y, null);
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