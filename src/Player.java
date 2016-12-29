import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player {
	private BufferedImage spriteSheet;

	private double x;
	private double y;
	private int height;
	private int width;
	
	private double gunX;
	private double gunY;


	private BufferedImage[] sprites = new BufferedImage[13];
	private BufferedImage currentImg;
	private int frame;
	private double angle = 0;
	private Point gun = new Point((int) x + width - 5, (int) y);

	private ArrayList<Bullet> nb = new ArrayList<Bullet>(9); 
	private AffineTransformOp op;

	public Player(){
		try { 
			spriteSheet = ImageIO.read(getClass().getResource("/Sprites.png"));
		} catch (IOException e) { 
			System.err.println("Sprites3.png could not be found");
		}

		for (int i = 0; i < sprites.length; i++){
			sprites[i] = grabImage(1, i+1, 100);
		}
		currentImg = sprites[0];
		x = 425;
		y = 775;
		width = currentImg.getWidth();
		height = currentImg.getHeight();
		
		gunX = (int) x + width - 15;
		gunY = (int) y;
	}

	public BufferedImage grabImage(int col, int row, int length){
		BufferedImage img = spriteSheet.getSubimage(((col-1) * length), ((row-1) * length), length, length);
		return img;
	}

	public void paint(Graphics2D g2d){

		for(int i = 0; i < nb.size(); i++)
		{
			if(nb.get(i).getTime()*10 >= 15)
			{
				nb.set(0, null);
				nb.remove(nb.get(0));//removes bullets based on the time they are in game
			}
		}

		double rotationRequired = Math.toRadians (angle);
		double locationX = currentImg.getWidth() / 2;
		double locationY = currentImg.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

		for (int i = 0; i < nb.size(); i++)
		{
			nb.get(i).move();//moves the bullets
			nb.get(i).paint(g2d);
		}

		// Drawing the rotated image at the required drawing locations
		if(op!=null) g2d.drawImage(op.filter(currentImg, null), (int)x, (int)y, null);
	    else g2d.drawImage(currentImg, (int)x, (int)y, null);
	    g2d.setColor(Color.blue);
	    //g2d.fillOval((int)gunX, (int)gunY, 10, 10);
	}

	public void update(){
		currentImg = sprites[frame];
		
	}
	
	public void move(double xM, double yM){
		if (yM == 5) xM = -5;
		else xM = 5;
		x += xM * Math.sin(Math.toRadians(angle));
		y += yM * Math.cos(Math.toRadians(angle));
		
		if (frame < 12){
			frame++;
		}
		else frame = 0;
		turn(0);
	}

	public void turn(double angle){    
		this.angle += angle;
		double rotationRequired = Math.toRadians (this.angle);
	    double locationX = currentImg.getWidth() / 2;
	    double locationY = currentImg.getHeight() / 2;
	    AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
	    op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
	    int gx= currentImg.getWidth() - 15;
	    int gy=0;
	    Point pt1=new Point(gx, gy);
	    Point pt2=new Point();
	    tx.transform(pt1, pt2);
	    gunX=(int)x+pt2.x; 
	    gunY=(int)y+pt2.y;
	}

	public int getX() {
		return (int) x;
	}

	public int getY(){
		return (int) y;
	}

	public int getGunX() {
		return (int)gunX;
	}

	public int getGunY(){
		return (int)gunY;
	}
	
	public int getHeight(){
		return height;
	}

	public int getWidth(){
		return width;
	}

	public void setNB(int x, int y, double angle)//sets position and movement of bullet
	{
		if (nb.size() > 9)
		{
			nb.set(0, null);
			nb.remove(nb.get(0));//removes the bullets from the game when tried to exceed 3
		}
		if (nb.size() < 9)//only 3 shots allowed at a time
		{
			nb.add(new Bullet(x, y, angle));//adds bullets to list when a new one is shot
		}
	}
	
	public ArrayList<Bullet> getNB()//sets position and movement of bullet
	{
		return nb;
	}

	public void removeNB(int i)//removes bullet from game when it hits an asteroid
	{
		nb.set(i, null);
		nb.remove(nb.get(i));
	}

	public int getBulletX(int i)//gets bullet X coord
	{
		return nb.get(i).getX();
	}

	public int getBulletY(int i)//gets bullet Y coord
	{
		return nb.get(i).getY();
	}

	public int getBulletWidth(int i)//gets bullet width
	{
		return nb.get(i).getWidth();
	}

	public int getBulletHeight(int i)//gets bullet height
	{
		return nb.get(i).getHeight();
	}

	public int getBulletAngle(int i)//gets bullet angle
	{
		return nb.get(i).getAngle();
	}

	public double getAngle() {
		return angle;
	}

	public int amountOfBullets() //checks amount of bullets in game
	{
		return nb.size();
	}
}
