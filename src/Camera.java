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
	private int x;
	private int y;
	private boolean moving = false;
	private double angle;
	private boolean up;
	private Graphics2D graphicsTemp;
	private AffineTransform at;
	private Polygon beam;
	private Point p1;
	private Point p2;
	private Point p3;
	private int[] xArray;
	private int[] yArray;
	private CashOut game;

	public Camera(Point p1, Point p2, Point p3, CashOut c) {
		try {
			img = ImageIO.read(getClass().getResource("/Images/Camera.png"));

		} catch (IOException e) {
			System.out.println("NO IMAGE");
		}
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		angle = 0;
		up = true;
		game = c;
	}

	/*
	 * public void clicked(MouseEvent e) { if (e.getX() > x && e.getY() > y &&
	 * e.getX() < img.getWidth() + x && e.getY() < y + img.getHeight() &&
	 * !isTransparent(e.getX(), e.getY())) { System.out.println("CLICKED"); }
	 * else { System.out.println("NOT CLICKED"); } }
	 */

	public void update() {
		xArray = new int[] { p1.x, p2.x, p3.x };
		yArray = new int[] { p1.y, p2.y, p3.y };
		beam = new Polygon(xArray, yArray, 3);
	}

	public void paint(Graphics2D g2d) {
		update();
		Color color = new Color(204, 204, 0, 60);
		if (up) {
			angle += 0.2;
		} else {
			angle -= 0.2;
		}
		if (angle >= 90.0 || angle <= 0.0) {
			up = !up;
		}
		g2d.rotate(Math.toRadians(angle), p1.x, p1.y);
		g2d.setColor(Color.YELLOW);
		g2d.fillPolygon(beam);
		g2d.rotate(-Math.toRadians(angle), p1.x, p1.y);
	}

	public void detect(Point p1, Point p2) {
		int x1 = p1.x;
		int y1 = p1.y;
		
		int x2 = p2.x;
		int y2 = p2.y;
		AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians(angle), p1.x, p1.y);
		Shape rotatedBeam = transform.createTransformedShape(beam);
		if (rotatedBeam.contains(x1, y1)) { //  || rotatedBeam.contains(x2, y2)
			game.addSuspicion(50);
		} else {
			//System.out.println("OUTSIDE");
		}
	}

	public BufferedImage getImage() {
		return img;
	}

	/*
	 * public boolean isTransparent(int x, int y) { //BufferedImage tempImg =
	 * new BufferedImage(graphicsTemp.get) int pixel = img.getRGB(x - this.x, y
	 * - this.y); if ((pixel >> 24) == 0x00) { System.out.println("ALPHA");
	 * return true; } else { System.out.println("NONALPHA"); }
	 * 
	 * return false; }
	 */

}
