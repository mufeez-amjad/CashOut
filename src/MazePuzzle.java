import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.Rectangle;
//import java.util.Timer;
//import java.util.TimerTask;

public class MazePuzzle {
	private BufferedImage maze;
	private ArrayList<Rectangle> hitboxes = new ArrayList<Rectangle>();
	private Rectangle current = new Rectangle(0, 37, 492, 41);
	//private Timer timer;
	private int interval;
	private boolean inMaze;
	private boolean win = false;
	private boolean lose = false;
	private boolean ready = false;
	private Timer timer;
	private int seconds;

	public MazePuzzle(int secs) {
		try {
			maze = ImageIO.read(getClass().getResource("/Images/mousemaze.png"));
		} catch (IOException e) {
			System.out.println("error");
		}
		Rectangle r1 = new Rectangle(0, 37, 492, 41);
		Rectangle r2 = new Rectangle(448, 78, 44, 275);
		Rectangle r3 = new Rectangle(492, 315, 210, 38);
		Rectangle r4 = new Rectangle(667, 199, 35, 154);
		Rectangle r5 = new Rectangle(667, 199, 330, 38);
		Rectangle r6 = new Rectangle(960, 199, 36, 667);
		Rectangle r7 = new Rectangle(39, 831, 957, 35);
		Rectangle r8 = new Rectangle(39, 496, 42, 370);
		Rectangle r9 = new Rectangle(39, 496, 628, 42);
		Rectangle r10 = new Rectangle(667, 448, 164, 151);
		seconds = secs;
		timer = new Timer(1150, 100, 15, seconds/3, "WHITE", true);

		hitboxes.add(r1);
		hitboxes.add(r2);
		hitboxes.add(r3);
		hitboxes.add(r4);
		hitboxes.add(r5);
		hitboxes.add(r6);
		hitboxes.add(r7);
		hitboxes.add(r8);
		hitboxes.add(r9);
		hitboxes.add(r10);
	}

	public void paint(Graphics2D g2d) {
		if (!ready) {
			g2d.setColor(Color.decode("0x065C27"));
			g2d.fillRect(0, 37, 60, 41);
			g2d.setColor(Color.WHITE);
			g2d.setFont(CashOut.getFontSmall());
			g2d.drawString("Start", 8, 60);
		} else {
			int alpha = 127; // 50% transparent
			Color redTrans = new Color(255, 0, 0, alpha);
			Color greenTrans = new Color(0, 255, 0, alpha);
			//g2d.setColor(Color.BLACK);
			//g2d.fillRect(0, 0, 1200, 900); // remove after tilemap is added
			//g2d.drawImage(maze, 0, 0, null);
			if (!inMaze || lose){
				g2d.setColor(redTrans);
				g2d.fillRect(0, 0, 1200, 900);
			}
			if (win){
				g2d.setColor(greenTrans);
				g2d.fillRect(0, 0, 1200, 900);
			}

			g2d.setColor(Color.WHITE);
			g2d.fillRect(0, 37, 492, 41);
			g2d.fillRect(448, 78, 44, 275);
			g2d.fillRect(492, 315, 210, 38);
			g2d.fillRect(667, 199, 35, 154);
			g2d.fillRect(667, 199, 328, 38);
			g2d.fillRect(960, 199, 36, 667);
			g2d.fillRect(39, 831, 957, 35);
			g2d.fillRect(39, 496, 42, 370);
			g2d.fillRect(39, 496, 628, 42);
			g2d.fillRect(667, 448, 164, 151);
			timer.paint(g2d);
		}
	}

	public int countdown() {
		if (ready) {
			if (interval == 1) {
				//timer.cancel();
			}
			interval--;
		}
		return interval;
	}

	public void update() {
		if (!lose){
			if (!inMaze && ready){
				timer.stopStart();
				System.out.println("OUT");
			}
			if (!timer.isRunning() && ready) {
				lose = true;
				System.out.println("TIME");
				//timer.cancel();
			}
		}
	}

	public void mouseMoved(MouseEvent e) {
		if (e.getX() > 0 && e.getX() < 60 && e.getY() > 37 && e.getY() < 37 + 41)
			ready = true;
		if (ready && !lose) {
			for (Rectangle r : hitboxes) {
				if (e.getX() > r.getX() && e.getY() > r.getY() && e.getX() < r.getX() + r.getWidth()
				&& e.getY() < r.getY() + r.getHeight()) {

					current = r;
					if (current == hitboxes.get(hitboxes.size() - 1)) {
						win = true;
					} else {
						inMaze = true;
					}
				}
			}
			if (e.getX() < current.getX() || e.getY() < current.getY() || e.getX() > current.getX() + current.getWidth()
			|| e.getY() > current.getY() + current.getHeight()) {
				inMaze = false;
			}
		}

	}
	
	public boolean isFinished(){
		return lose || win;
	}
	
	public boolean isWin(){
		return win;
	}
	
	public boolean isLose(){
		return lose;
	}
	
	public void reset(){
		timer = new Timer(1150, 100, 15, seconds/3, "WHITE", true);
		lose = false;
		inMaze = false;
		ready = false;
		
	}
}
