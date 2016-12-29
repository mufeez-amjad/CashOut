import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Camera {
	private BufferedImage camera;
	private int x;
	private int y;
	private int angle;
	private int beamX;
	private int beamY;
	
	public Camera(int x, int y, int a){
		try 
		{ 
			camera = ImageIO.read(getClass().getResource("/Camera.png"));
		} catch (IOException e) { 
			System.err.println("Camera.png could not be found");
		}

		this.x = x;
		this.y = y;
		angle = 45;
		beamX = x - camera.getWidth() + 11;
		beamY = y + 25;
	}
	
	public void paint(Graphics2D g2d){
		g2d.translate(beamX, beamY);
		double rotationRequired = Math.toRadians (angle);
		double locationX = camera.getWidth() / 2;
		double locationY = camera.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		g2d.drawImage(op.filter(camera, null), 0, 0, null);
		
		//g2d.setColor(Color.darkGray);
		//g2d.fillOval(x - 5, y + 10, 20, 20);
		g2d.setColor(Color.gray);
		g2d.fillOval(0, 0, 30, 30);
		
		int cX = x - 15;
	    int cY = 0;
	    Point pt1=new Point();
	    Point pt2=new Point(cX, cY);
	    tx.transform(pt1, pt2);
	    beamX=(int)x+pt2.x; 
	    beamY=(int)y+pt2.y;
		
		
	}
	
	public void update(){
		//if (angle < 45) angle += 1;
		//else 
		//else angle = 0;
	}
}
