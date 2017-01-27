import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Level4 extends Level{
	private BufferedImage level;
	private Note[] notes = new Note[4];
	private MoneyBag[] money = new MoneyBag[5];
	private Laser laser;
	
	private ArrayList<Rectangle> hits = new ArrayList<Rectangle>();
	private Timer timer;
	private boolean isUnlocked = false;
	private int totalValue = 0;
	private int notesCollected = 0;
	private ArrayList<Integer> notesValues = new ArrayList<Integer>();
	private Vault vault;
	private int score = 0;
	private Camera camera1;
	private Camera camera2;
	private int highScore = 0;

	public Level4(CashOut c, Player p){

		try { 
			level = ImageIO.read(getClass().getResource("/Images/Levels-04.png"));
		} catch (IOException e) { 
			System.err.println("Levels-03.png could not be found");
		}
		
		hits.add(new Rectangle(184, 246, 126, 30));
		hits.add(new Rectangle(278, 0, 33, 245));
		hits.add(new Rectangle(606, 332, 587, 30));
		hits.add(new Rectangle(0, 520, 86, 35));
		hits.add(new Rectangle(278, 520, 140, 35));
		hits.add(new Rectangle(605, 520, 86, 35));
		hits.add(new Rectangle(0, 555, 35, 345));
		hits.add(new Rectangle(332, 555, 35, 345));
		hits.add(new Rectangle(0, 555, 35, 345));
		hits.add(new Rectangle(655, 555, 35, 345));

		for (int i = 0; i < notes.length; i++){
			notes[i] = new Note(c, hits);
		}

		for (int i = 0; i < money.length; i++){
			money[i] = new MoneyBag(c, hits);
			totalValue += money[i].getValue();
		}

		vault = new Vault(0, 400, notes, true, 90);
		camera1 = new Camera (new Point(715, 11), new Point(580, 296), new Point(854, 292), 32.0, -32.0, 416, 8, 617, 267);
		camera2 = new Camera (new Point(349, 522), new Point(266,335), new Point(437, 335), 45.0, -45.0, 92, 300, 461, 248);

		laser = new Laser(638, 364, 90, 20);
		
		
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

		
		
		laser.paint(g2d);
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
		vault.paint(g2d);
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
		return laser.isHacking();
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
		p.setXY(1000, 110, 270);
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
	public BufferedImage getBG() {
		return level;
	}

	@Override
	public boolean isOfficer1Dead() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOfficer2Dead() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void paintOfficer1(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paintOfficer2(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}
}
