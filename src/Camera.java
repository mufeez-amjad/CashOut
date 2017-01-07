import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
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

	public Camera(Point p1, Point p2, Point p3, double maxAngle, double maxCounterAngle, int circleX, int circleY,
			int circleRadius) {
		try {
			img = ImageIO.read(getClass().getResource("/Images/cameraHolder.png"));
		} catch (IOException e) {
			System.out.println("NO IMAGE");
		}
		this.circleRadius = circleRadius;
		this.maxAngle = maxAngle;
		this.maxCounterAngle = maxCounterAngle;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.circleX = circleX - circleRadius;
		this.circleY = circleY - circleRadius;
		angle = 0.1;
		up = true;
		xArray = new int[] { p1.x, p2.x, p3.x };
		yArray = new int[] { p1.y, p2.y, p3.y };
		beam = new Polygon(xArray, yArray, 3);
		pp = new PicturePuzzle(400, 200);
	}

	public void update(Player p, CashOut c) {
		AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians(angle), p1.x, p1.y);
		Shape rotatedBeam = transform.createTransformedShape(beam);
		if (rotatedBeam.contains(p.getX() + p.getWidth(), p.getY() + p.getHeight() / 2) && !disabled) {
			c.addSuspicion(250);
		}
		if (pp.complete() || pp.failed()) {
			rectCounter++;
			if (rectCounter == 99) {
				finished = true;
			}
		}
		
		if (pp.complete() && finished) {
			disabled = true;
		}
		if (pp.failed() && finished) {
			resetPuzzle();
			c.addSuspicion(250);
		}
		
		xArray = new int[] { p1.x, p2.x, p3.x };
		yArray = new int[] { p1.y, p2.y, p3.y };
		beam = new Polygon(xArray, yArray, 3);
	}

	public void resetPuzzle() {
		pp = new PicturePuzzle(400, 200);
		hacking = false;
		finished = false;
		disabled = false;
		rectCounter = 0;
	}

	public void paint(Graphics2D g2d) {
		int alpha = 127;
		Color redTrans = new Color(255, 0, 0, alpha);
		Color greenTrans = new Color(0, 255, 0, alpha);
		if (up) {
			angle += 0.2;
		} else {
			angle -= 0.2;
		}
		if (rectCounter > 0 && rectCounter < 100) {

			if (pp.complete()) {
				g2d.setColor(greenTrans);
				g2d.fillRect(0, 0, CashOut.getFrameWidth(), CashOut.getFrameHeight());
			}
			if (pp.failed()) {
				g2d.setColor(redTrans);
				g2d.fillRect(0, 0, CashOut.getFrameWidth(), CashOut.getFrameHeight());
			}
			
		}
		if (angle >= maxAngle || angle <= maxCounterAngle) {
			up = !up;
		}
		if (!disabled) {
			g2d.rotate(Math.toRadians(angle), p1.x, p1.y);
			g2d.setColor(Color.YELLOW);
			g2d.fillPolygon(beam);
			g2d.rotate(-Math.toRadians(angle), p1.x, p1.y);
			g2d.setColor(Color.BLACK);
			g2d.fillOval(circleX, circleY, 2 * circleRadius, 2 * circleRadius);
		}

		if (hacking && !pp.complete() && !pp.failed() && !finished) {
			pp.paint(g2d);
		}
	}

	public BufferedImage getImg() {
		return img;
	}

	public void keyPressed(KeyEvent e, Player p) {
		int x1 = p.getPoint().x;
		int y1 = p.getPoint().y;

		int x2 = p.getPoint2().x;
		int y2 = p.getPoint2().y;
		if (!disabled) {
			if ((x1 > p1.x - 300 && x1 < p1.x && y1 > p1.y && y1 < p1.y + 300)
					|| (x2 > p1.x - 300 && x2 < p1.x && y2 > p1.y && y2 < p1.y + 300)) {
				if (e.getKeyCode() == KeyEvent.VK_X) {
					hacking = true;
				}
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		pp.clicked(e);
	}
}
