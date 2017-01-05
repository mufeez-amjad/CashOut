import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Level1 extends Level{
	private BufferedImage level;
	private Note[] notes = new Note[4];
	private MoneyBag[] money = new MoneyBag[5];
	private Laser laser;
	private Officer officer; 
	private Camera camera;
	private ArrayList<Rectangle> hits = new ArrayList<Rectangle>();
	private Timer timer;
	private boolean isFinished = false;
	private boolean isUnlocked = true;
	private boolean hackingLaser = true;

	public Level1(CashOut c){
		try { 
			level = ImageIO.read(getClass().getResource("/Images/1st.png"));
		} catch (IOException e) { 
			System.err.println("1st.png could not be found");
		}

		for (int i = 0; i < notes.length; i++){
			notes[i] = new Note();
		}

		for (int i = 0; i < money.length; i++){
			money[i] = new MoneyBag(c);
		}
		laser = new Laser(700, 52, 90, 30);
		officer = new Officer();
		camera = new Camera (1075, 52, 0);

		hits.add(new Rectangle(756, 0, 444, 50));
		hits.add(new Rectangle(756, 167, 87, 66));
		hits.add(new Rectangle(843, 167, 110, 733));
		hits.add(new Rectangle(1090, 167, 105, 733));
		timer = new Timer(1150, 40, 25, 50, "WHITE", false);
	}

	public void paint(Graphics2D g2d){
		g2d.drawImage(level, 0, 0, null);

		for (int i = 0; i < notes.length; i++){
			notes[i].paint(g2d);
		}

		for (int i = 0; i < money.length; i++){
			money[i].paint(g2d);
		}
		laser.paint(g2d);
		officer.paint(g2d);
		camera.paint(g2d);
		/*g2d.setColor(Color.red);
		g2d.fillRect(756, 0, 444, 50);
		g2d.fillRect(756, 167, 87, 66);
		g2d.fillRect(843, 167, 110, 733);
		g2d.fillRect(1090, 167, 105, 733);
		 */

		timer.paint(g2d);
	}

	public void update(Player p, CashOut c){
		for (int i = 0; i < notes.length; i++){
			notes[i].collect(p);
		}

		for (int i = 0; i < money.length; i++){
			money[i].collect(p);
		}

		laser.update(p, c);
		officer.update(p, c, p.getNB());
		camera.update();
	}


	public BufferedImage getBagImage(){
		if (money.length > 0) return money[0].getImage();
		return null;
	}

	public BufferedImage getLaserImage(){
		return laser.getImage();
	}

	public BufferedImage getCameraImage(){
		return camera.getImage();
	}

	public BufferedImage getOfficerImage(){
		return officer.getImage();
	}

	public boolean hit(int x, int y){
		boolean hit = false;
		for (Rectangle r: hits){
			if (x > r.getX() && x < r.getX() + r.getWidth() && y > r.getY() && y < r.getY() + r.getHeight()){
				hit = true;
			}
		}
		return hit;
	}

	public Timer getTimer() {
		return timer;
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public boolean isUnlocked() {
		return isUnlocked;
	}

	public boolean isHackingLaser() {
		return hackingLaser;
	}

	@Override
	public boolean isHackingCamera() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHackingVault() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		laser.mouseMoved(e);
		
	}

}
