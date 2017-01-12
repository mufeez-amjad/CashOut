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
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Player {
	private BufferedImage spriteSheet;

	private double x;
	private double y;
	private int height;
	private int width;

	private double gunX;
	private double gunY;

	private double pointX;
	private double pointY;

	private BufferedImage[] sprites = new BufferedImage[13];
	private BufferedImage currentImg;
	private int frame;
	private double angle = 0;
	private Point gun = new Point((int) x + width - 5, (int) y);

	private ArrayList<Bullet> nb = new ArrayList<Bullet>(9); 
	private AffineTransformOp op;
	private CashOut game;

	private double pointY2;

	private double pointX2;

	public Player(CashOut c){
		try { 
			spriteSheet = ImageIO.read(getClass().getResource("/Images/Sprites.png"));
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
		pointX = x + width/2;
		pointY = y + 20;
		pointX2 = x + width/2;
		pointY2 = y + height - 20;
		game = c;
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

	}

	public void update(){
		currentImg = sprites[frame];
	}

	public void move(double xM, double yM, Levels levels){
		int tempX = (int) pointX;
		int tempY = (int) pointY;

		int tempX2 = (int) pointX2;
		int tempY2 = (int) pointY2;

		if (yM == 5){ //down key
			xM = -5;
			tempX2 += xM * Math.sin(Math.toRadians(angle));
			tempY2 += yM * Math.cos(Math.toRadians(angle));
			if (!levels.getCurrent().hit(tempX2, tempY2)){
				x += xM * Math.sin(Math.toRadians(angle));
				y += yM * Math.cos(Math.toRadians(angle));
			}
		}
		else{ //up key
			xM = 5;
			tempX += xM * Math.sin(Math.toRadians(angle));
			tempY += yM * Math.cos(Math.toRadians(angle));
			if (!levels.getCurrent().hit(tempX, tempY)){
				x += xM * Math.sin(Math.toRadians(angle));
				y += yM * Math.cos(Math.toRadians(angle));
			}
		}

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
		
		if (this.angle == 360){
			this.angle = 0;
		}

		int px = currentImg.getWidth()/2;
		int py = 20;

		Point pt3=new Point(px, py);
		Point pt4=new Point();
		tx.transform(pt3, pt4);
		pointX=(int)x+pt4.x; 
		pointY=(int)y+pt4.y;

		int px2 = currentImg.getWidth()/2;
		int py2 = height  - 20;
		Point pt5=new Point(px2, py2);
		Point pt6=new Point();
		tx.transform(pt5, pt6);
		pointX2 = (int)x+pt6.x;
		pointY2= (int)y+pt6.y;	    
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

	public synchronized void playGunshot() { //plays the pop sound effect
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
					this.getClass().getResource("/Audio/gunshot.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch(Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}
	}

	public synchronized void playDryFire() { //plays the pop sound effect
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
					this.getClass().getResource("/Audio/dry_fire.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch(Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}
	}

	public void setNB(int x, int y, double angle) //sets position and movement of bullet
	{
		if (nb.size() > 9)
		{
			nb.set(0, null);
			nb.remove(nb.get(0)); //removes the bullets from the game when tried to exceed 3
		}

		if (nb.size() < 9) //only 9 bullet allowed at a time
		{
			nb.add(new Bullet(x, y, angle));//adds bullets to list when a new one is shot
			if (game.getSoundState()) playGunshot();
			game.addSuspicion(250);
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

	public void printAngle(){
		System.out.println(angle);
	}

	public Point getPoint(){ //front of player
		return new Point((int) pointX, (int) pointY);
	}

	public Point getPoint2(){ //back of player
		return new Point((int) pointX2, (int) pointY2);
	}
}
