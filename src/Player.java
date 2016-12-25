import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player {
	private BufferedImage spriteSheet;
	private int x;
	private int y;
	private int height;
	private int width;
	private int bulletX;
	
	private BufferedImage[] sprites = new BufferedImage[13];
	private BufferedImage currentImg;
	private boolean walking = false;
	private int frame;
	private double angle = 0;
	private double totalAngle = 0;

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
		x = 10;
		y = 10;
		width = currentImg.getWidth();
		height = currentImg.getHeight();
	}

	public BufferedImage grabImage(int col, int row, int length){
		BufferedImage img = spriteSheet.getSubimage(((col-1) * length), ((row-1) * length), length, length);
		return img;
	}

	public void paint(Graphics g2d){
		double rotationRequired = Math.toRadians (angle);
		double locationX = currentImg.getWidth() / 2;
		double locationY = currentImg.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

		// Drawing the rotated image at the required drawing locations
		g2d.drawImage(op.filter(currentImg, null), x, y, null);
		//g2d.fillOval(x, y, 10, 10);
		g2d.setColor(Color.RED);
		//g2d.fillOval(x + width/2 - 5, y + height/2 + 5, 10, 10);
	}

	public void update(){
		currentImg = sprites[frame];
	}

	public void move(double xM, double yM){
		walking  = true;
		if (yM == 5) xM = -5;
		else xM = 5;
		x += xM * Math.sin(Math.toRadians(angle));
	    y += yM * Math.cos(Math.toRadians(angle));

		//x += xM*10;
		//y += yM*10;
		if (frame < 12){
			frame++;
		}
		else frame = 0;
	}

	public void turn(double angle){    
		this.angle += angle;
	    totalAngle+=angle;
	}

	public void stopMoving(){
		walking = false;
	}

	public int getX() {
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getWidth(){
		return width;
	}
}
