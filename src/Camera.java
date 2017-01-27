import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Camera {

	private BufferedImage img;
	private double angle;
	private boolean up;
	private Polygon beam;
	private Point p1;
	private Point p2;
	private Point p3;
	private int[] xArray;
	private int[] yArray;
	private double maxAngle;
	private double maxCounterAngle;
	private int circleX;
	private int circleY;
	private int circleRadius;
	private boolean disabled = false;
	private PicturePuzzle pp;
	private boolean hacking = false;
	private int rectCounter = 0;
	private boolean finished = false;
	private Rectangle hitbox;

	public Camera(Point p1, Point p2, Point p3, double maxAngle, double maxCounterAngle, int hitboxX, int hitboxY, int hitboxWidth, int hitboxHeight) {
		try {
			img = ImageIO.read(getClass().getResource("/Images/cameraHolder.png"));
		} catch (IOException e) {
			System.out.println("NO IMAGE");
		}
		circleRadius = 7;
		this.maxAngle = maxAngle;
		this.maxCounterAngle = maxCounterAngle;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.circleX = p1.x - circleRadius;
		this.circleY = p1.y - circleRadius;
		angle = 0.1;
		up = true;
		xArray = new int[] { p1.x, p2.x, p3.x };
		yArray = new int[] { p1.y, p2.y, p3.y };
		beam = new Polygon(xArray, yArray, 3);
		beam = new Polygon(xArray, yArray, 3);
		pp = new PicturePuzzle(400, 200);
		hitbox = new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight);
	}
	
	public void update(Player p, CashOut c) {
		AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians(angle), p1.x, p1.y);
		Shape rotatedBeam = transform.createTransformedShape(beam); // rotatedBeam.contains(p.getPoint().x, p.getPoint().y) && !disabled)
		if (!disabled)
			if (rotatedBeam.contains(p.getHitbox().getCenterX(), p.getHitbox().getCenterY()))
				c.addSuspicion(250);
			
		if (pp.complete() && rectCounter == 100) {
			disabled = true;
		}
		
		if (pp.failed() && finished) {
			resetPuzzle();
			c.addSuspicion(250);
		}
		
		if (up) {
			angle += 0.2;
		} else {
			angle -= 0.2;
		}

		if (angle >= maxAngle || angle <= maxCounterAngle) {
			up = !up;
		}
		
		xArray = new int[] { p1.x, p2.x, p3.x };
		yArray = new int[] { p1.y, p2.y, p3.y };
		beam = new Polygon(xArray, yArray, 3);
		if (hacking) setHacking();
		if ((pp.failed() || pp.complete()) && rectCounter < 100) rectCounter++;
	}

	public void resetPuzzle() {
		pp = new PicturePuzzle(400, 200);
		hacking = false;
		finished = false;
		disabled = false;
		rectCounter = 0;
	}

	public void paint(Graphics2D g2d) {
				
		if (!disabled) {
			g2d.rotate(Math.toRadians(angle), p1.x, p1.y);
			Color yellowTrans = new Color(255, 255, 0, 127);
			g2d.setColor(yellowTrans);
			g2d.fillPolygon(beam);
			g2d.rotate(-Math.toRadians(angle), p1.x, p1.y);
			g2d.setColor(Color.BLACK);
			g2d.fillOval(circleX, circleY, 2 * circleRadius, 2 * circleRadius);
		}

		if (hacking && !pp.complete() && !pp.failed() && !finished) {
			pp.paint(g2d);
		}
		
		if (rectCounter > 0 && rectCounter < 100){
			Color redTrans = new Color(255, 0, 0, 127);
			Color greenTrans = new Color(0, 255, 0, 127);
			if (pp.failed()) g2d.setColor(redTrans);
			if (pp.complete()) g2d.setColor(greenTrans);
			g2d.fillRect(0, 0, CashOut.getFrameWidth(), CashOut.getFrameHeight());
		}
	}

	public BufferedImage getImg() {
		return img;
	}

	public void keyPressed(KeyEvent e, Player p, boolean b) {
		p.getPoint();
		p.getPoint();

		p.getPoint2();
		p.getPoint2();
		if (!disabled && !b) { //(x1 > p1.x - 300 && x1 < p1.x + 300 && y1 > p1.y && y1 < p1.y + 300) || (x2 > p1.x - 300 && x2 < p1.x && y2 > p1.y && y2 < p1.y + 300)
			if (p.getHitbox().intersects(hitbox)) {
				if (e.getKeyCode() == KeyEvent.VK_X) {
					reset();
					hacking = !hacking;
				}
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		pp.clicked(e);
	}
	
	public boolean getHacking(){
		return hacking;
	}
	
	public void setHacking(){
		hacking = pp.getHacking();
	}
	
	public void reset(){
		rectCounter = 0;
		pp.reset();
	}
	
}