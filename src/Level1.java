import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Level1 extends Level{
	private BufferedImage level;
	private Note[] notes = new Note[4];
	private MoneyBag[] money = new MoneyBag[5];
	private Officer[] officers = new Officer[2];
	private Laser laser;
	private Camera camera;
	private ArrayList<Rectangle> hits = new ArrayList<Rectangle>();
	private Timer timer;
	private boolean isFinished = false;
	private boolean isUnlocked = true;
	private boolean hackingLaser = true;
	private int totalValue = 0;
	private int notesCollected = 0;
	private ArrayList<Integer> notesValues = new ArrayList<Integer>();
	private Vault vault;
	private boolean isHacking = false;

	public Level1(CashOut c){
		try { 
			level = ImageIO.read(getClass().getResource("/Images/1st.png"));
		} catch (IOException e) { 
			System.err.println("1st.png could not be found");
		}
		
		hits.add(new Rectangle(756, 0, 444, 50));
		hits.add(new Rectangle(756, 167, 87, 66));
		hits.add(new Rectangle(843, 167, 110, 733));
		hits.add(new Rectangle(1090, 167, 105, 733));

		for (int i = 0; i < notes.length; i++){
			notes[i] = new Note(c, hits);
		}

		for (int i = 0; i < money.length; i++){
			money[i] = new MoneyBag(c, hits);
			totalValue += money[i].getValue();
		}

		laser = new Laser(700, 52, 90, 30);

		for (int i = 0; i < officers.length; i++){
			officers[i] = new Officer(hits);
		}

		camera = new Camera (new Point(1087, 57), new Point(1020, 200), new Point(960, 140), 32.0, -12.0);
		vault = new Vault(960, 836, notes);

		timer = new Timer(1150, 40, 25, 75, "WHITE", false);
	}

	public void paint(Graphics2D g2d){
		g2d.drawImage(level, 0, 0, null);

		for (int i = 0; i < notes.length; i++){
			notes[i].paint(g2d);
		}

		for (int i = 0; i < money.length; i++){
			money[i].paint(g2d);
		}

		for (int i = 0; i < officers.length; i++){
			officers[i].paint(g2d);
		}
		camera.paint(g2d);
		laser.paint(g2d);
		vault.paint(g2d);
		/*g2d.setColor(Color.red);
		g2d.fillRect(756, 0, 444, 50);
		g2d.fillRect(756, 167, 87, 66);
		g2d.fillRect(843, 167, 110, 733);
		g2d.fillRect(1090, 167, 105, 733);
		 */

		timer.paint(g2d);
	}

	public void update(Player p, CashOut c){
		if (notesCollected < 4){ //remove after development
			for (int i = 0; i < notes.length; i++){
				notes[i].collect(p, this);
				//notes[i].collectAll(p, this);
			}
		}

		for (int i = 0; i < money.length; i++){
			money[i].collect(p);
		}

		laser.update(p, c);
		for (int i = 0; i < officers.length; i++){
			officers[i].update(p, c, p.getNB());
		}
		camera.update(p, c);
		vault.setInventory(c.getInventory());
	}


	public BufferedImage getBagImage(){
		if (money.length > 0) return money[0].getImage();
		return null;
	}

	public BufferedImage getLaserImage(){
		return laser.getImage();
	}

	//public BufferedImage getCameraImage(){
	//	return camera.getImage();
	//}

	public BufferedImage getOfficerImage(){
		return officers[0].getImage();
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
		return vault.getUnlocked();
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

	public void keyPressed(KeyEvent e, Player p) {
		laser.keyPressed(e, p);
		vault.keyPressed(e,p, notesValues);
		camera.keyPressed(e, p);
	}

	@Override
	public int getTotalValue() {
		return totalValue;
	}

	public int getNotesCollected() {
		return notesCollected;
	}

	public void printNotesValues() {
		for (Integer n: notesValues){
			System.out.println(n);
		}
	}

	public void addNotesCollected(int n) {
		notesCollected += n;
	}

	public ArrayList<Integer> getNotesValues() {
		return notesValues;
	}

	public void addNotesValues(int n) {
		notesValues.add(n);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		camera.mouseClicked(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		vault.mouseReleased(e, notesValues);

	}

	public void sendInventory(Inventory i){
		vault.setInventory(i);
	}
	
	public void setHacking(boolean state){
		isHacking = state;
	}
	
	public boolean getHacking(){
		return camera.getHacking() || laser.isHacking() || vault.isHacking();
	}
}
