import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Level5 extends Level{
	private BufferedImage level;
	private Note[] notes = new Note[4];
	private MoneyBag[] money = new MoneyBag[5];
	private Laser laser;
	private ArrayList<Officer> officers = new ArrayList<Officer>();

	private ArrayList<Rectangle> hits = new ArrayList<Rectangle>();
	private Timer timer;
	private boolean isUnlocked = false;
	private boolean hackingLaser = true;
	private int totalValue = 0;
	private int notesCollected = 0;
	private ArrayList<Integer> notesValues = new ArrayList<Integer>();
	private Vault vault;
	private int score = 0;
	private Camera camera1;
	private Camera camera2;
	private Officer officer1;
	private Officer officer2;
	private int highScore = 0;

	public Level5(CashOut c, Player p){

		try { 
			level = ImageIO.read(getClass().getResource("/Images/Levels-05.png"));
		} catch (IOException e) { 
			System.err.println("Levels-05.png could not be found");
		}
		
		hits.add(new Rectangle(277, 0, 31, 74)); //R1
		hits.add(new Rectangle(0, 0, 277, 62)); //R2
		hits.add(new Rectangle(0, 62, 58, 218)); //R3
		hits.add(new Rectangle(227, 323, 50, 233)); //R4
		hits.add(new Rectangle(277, 238, 32, 631)); //R5
		hits.add(new Rectangle(308, 507, 41, 33)); //R6
		hits.add(new Rectangle(220, 600, 56, 31)); //R7
		hits.add(new Rectangle(0, 600, 57, 32)); //R8
		hits.add(new Rectangle(506, 508, 124, 31)); //R9
		hits.add(new Rectangle(553, 539, 30, 329)); //R10
		hits.add(new Rectangle(788, 509, 40, 32)); //R11
		hits.add(new Rectangle(829, 232, 31, 638)); //R12
		hits.add(new Rectangle(829, 0, 31, 76)); //R13

		for (int i = 0; i < notes.length; i++){
			notes[i] = new Note(c, hits);
		}

		for (int i = 0; i < money.length; i++){
			money[i] = new MoneyBag(c, hits);
			totalValue += money[i].getValue();
		}

		vault = new Vault(1020, 836, notes, true, 0);
		
		camera1 = new Camera (new Point(1138, 62), new Point(895, 154), new Point(984, 294), 20.0, -32.0, 827, 34, 358, 393);
		camera2 = new Camera (new Point(570, 508), new Point(475,245), new Point(660, 245), 45.0, -45.0, 430, 209, 250, 334);
		
		officer1 = new Officer(800, 100, 270, hits);
		officer2 = new Officer(90, 500, 0, hits);
		
		officers.add(officer1);
		officers.add(officer2);
		
		laser = new Laser(293, 78, 90, 20);
		
		
		
		timer = new Timer(1150, 40, 25, 75, "WHITE", false);
	}

	public void paint(Graphics2D g2d){
		//g2d.drawImage(level, 0, 0, null);

		for (int i = 0; i < notes.length; i++){
			notes[i].paint(g2d);
		}
		
		for (int i = 0; i < money.length; i++){
			money[i].paint(g2d);
		}

		
		
		for (Officer o: officers){
			o.paint(g2d);
		}
		
		if (laser.isHacking() || camera1.getHacking() || camera2.getHacking()) vault.paint(g2d);

		
		if (!camera1.getHacking() && !camera2.getHacking()){
			camera1.paint(g2d);
			camera2.paint(g2d);
		}
		else if (camera1.getHacking()) {
			camera2.paint(g2d);
			camera1.paint(g2d);
		}
		else if (camera2.getHacking()) {
			camera1.paint(g2d);
			camera2.paint(g2d);
		}
		
		
		laser.paint(g2d);
		
		if (!laser.isHacking() && !camera1.getHacking() && !camera2.getHacking()) vault.paint(g2d);
				
		timer.paint(g2d);
	}

	public void update(Player p, CashOut c){
		if (notesCollected < 4){ //remove after development
			for (int i = 0; i < notes.length; i++){
				notes[i].collect(p, this);
				//notes[i].collectAll(p, this);
			}
		}
		laser.update(p, c);

		camera1.update(p, c);
		camera2.update(p, c);

		for (Officer o: officers){
			o.update(p, c, p.getNB());
		}
		
		for (int i = 0; i < money.length; i++){
			money[i].collect(p);
		}
		
		vault.update(c.getInventory(), notesCollected);
	}
	

	public boolean hit(Rectangle h){
		boolean hit = false;
		//System.out.println("hit");
		for (Rectangle r: hits){ //x > r.getX() && x < r.getX() + r.getWidth() && y > r.getY() && y < r.getY() + r.getHeight()
			if (h.intersects(r)){
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
		vault.keyPressed(e,p, notesValues);
		laser.keyPressed(e, p);
		camera1.keyPressed(e, p, laser.isHacking());
		camera2.keyPressed(e, p, laser.isHacking());
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
	public void mouseClicked(MouseEvent e, Inventory i) {
		camera1.mouseClicked(e);
		camera2.mouseClicked(e);
		vault.mouseClicked(e, i);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		vault.mouseReleased(e, notesValues);

	}

	public void sendInventory(Inventory i){
		vault.setInventory(i);
	}
	
	public void setHacking(boolean state){
	}
	
	public boolean getHacking(){
		return vault.isHacking() || laser.isHacking() || camera1.getHacking() || camera2.getHacking();
	}

	public void setCurrentLevel(boolean currentLevel, Player p) {
		p.setXY(530, 40, 180);
	}

	@Override
	public void addScore(int n) {
		score += n;
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public Note[] getNotes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHighScore(int s) {
		highScore = s;
		isUnlocked = !(s == 0);
	}

	@Override
	public int getHighScore() {
		return highScore;
	}

	@Override
	public void unlock() {
		isUnlocked = true;		
	}
	
	@Override
	public boolean isOfficer1Dead() {
		return officer1.dead();
	}
	
	@Override
	public boolean isOfficer2Dead() {
		return officer2.dead();
	}
	
	@Override
	public void paintOfficer1(Graphics2D g2d) {
		// TODO Auto-generated method stub
		officer1.paintBlood(g2d);
	}
	
	@Override
	public void paintOfficer2(Graphics2D g2d) {
		officer2.paintBlood(g2d);
	}

	@Override
	public BufferedImage getBG() {
		return level;
	}
}
