import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
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
	private MazePuzzle m;
	private boolean hacking = false;
	private boolean disabled = false;
	private int rectCounter = 0;
	private int drawingX;
	private int drawingY;

	public Laser(int x, int y, int a, int secs){
		try { 
			laser = ImageIO.read(getClass().getResource("/Images/Laser.png"));
		} catch (IOException e) { 
			System.err.println("Laser.png could not be found");
		}

		this.x = x;
		this.y = y;
		angle = a;
		m = new MazePuzzle(secs);
		drawingX = x; 
		drawingY = y;
		if (angle == 90){
			drawingX -= laser.getHeight()/2;
		}
		else {
			drawingY = y - laser.getWidth()/2 + 10;
		}
	}

	public void paint(Graphics2D g2d){
		double rotationRequired = Math.toRadians (angle);
		double locationX = laser.getWidth() / 2;
		double locationY = laser.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		if (!disabled) g2d.drawImage(op.filter(laser, null), drawingX, drawingY, null);

		if (!m.isFinished() && hacking) m.paint(g2d);

		if (rectCounter > 0 && rectCounter < 100){
			Color redTrans = new Color(255, 0, 0, 127);
			Color greenTrans = new Color(0, 255, 0, 127);
			if (m.isLose()) g2d.setColor(redTrans);
			if (m.isWin()) g2d.setColor(greenTrans);
			g2d.fillRect(0, 0, CashOut.getFrameWidth(), CashOut.getFrameHeight());
		}
	}

	public void update(Player p, CashOut c){

		if (!disabled){
			if ((m.isWin() || m.isLose()) && hacking){
				if (m.isWin()) disabled = true;
				if (m.isLose() ) c.addSuspicion(250);
				hacking = false;
			} // p.getX() > x && p.getX() < x + 100 && p.getY() > y && p.getY() < y + p.getWidth()
			if (angle == 90){
				for (int yLaser = y; yLaser < y + laser.getWidth(); yLaser++){
					if (p.getHitbox().contains(x, yLaser))
						c.addSuspicion(1);
				}
			}
			else {
				for (int xLaser = x; xLaser < x + laser.getWidth(); xLaser++){
					if (p.getHitbox().contains(xLaser, y))
						c.addSuspicion(1);
				}
			}

		}


		if (!m.isFinished() && hacking) m.update();
		if ((m.isLose() || m.isWin()) && rectCounter < 100) rectCounter++;
	}

	public BufferedImage getImage() {
		return laser;
	}

	public void mouseMoved(MouseEvent e){
		if (hacking) {
			m.mouseMoved(e);
		}
	}

	public void keyPressed(KeyEvent e, Player p){
		int x1 = p.getPoint().x;
		int y1 = p.getPoint().y;

		int x2 = p.getPoint2().x;
		int y2 = p.getPoint2().y;
		if (!disabled){
			//if (p.getX() > x - 100 && p.getX() < x + 100 && p.getY() > y - 100 && p.getY() < y + 100){ //			if ((x1 > x  && x1 < x + 150 && y1 > y - 25 && y1 < y + laser.getWidth()) || (x2 > x && x2 < x + 150 && y2 > y - 25 && y2 < y + 25)){ //if player is close by || x1 < x
			if (angle == 0){
				if ( (x1 > x  && x1 < x + laser.getWidth() && y1 > y - laser.getHeight()/2 && y1 < y + laser.getHeight()/2) || (x2 > x  && x2 < x + laser.getWidth() && y2 > y - laser.getHeight()/2 && y2 < y + laser.getHeight()/2 )) {
					if (e.getKeyCode()==KeyEvent.VK_X){
						reset();
						hacking = !hacking;
					}
				}
			}

			else if (angle == 90){
				if ( (x1 > x - laser.getHeight()/2 && x1 < x + laser.getHeight()/2 && y1 > y && y1 < y + laser.getWidth()) || (x2 > x - laser.getHeight()/2 && x2 < x + laser.getHeight()/2 && y2 > y && y2 < y + laser.getWidth())) {
					if (e.getKeyCode()==KeyEvent.VK_X){
						reset();
						hacking = !hacking;
					}
				}
			}
		}
	}

	public boolean isHacking() {
		return hacking;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void reset(){
		rectCounter = 0;
		m.reset();
	}

}
