import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Level1 extends Level{
	private BufferedImage level;
	private ArrayList<Rectangle> hits = new ArrayList<Rectangle>();
	private Note[] notes = new Note[4];
	private MoneyBag[] money = new MoneyBag[5];
	private Laser laser = new Laser(0,0,0,0);
	private Officer officer = new Officer(0,0,0, hits);
	
	private Timer timer;
	private boolean isUnlocked = true;
	private boolean hackingLaser = true;
	private int totalValue = 0;
	private int notesCollected = 0;
	private ArrayList<Integer> notesValues = new ArrayList<Integer>();
	private Vault vault;
	private int score = 0;
	private int highScore = 0;

	public Level1(CashOut c, Player p){
		try { 
			level = ImageIO.read(getClass().getResource("/Images/Levels-01.png"));
		} catch (IOException e) { 
			System.err.println("Levels-01.png could not be found");
		}
		

		hits.add(new Rectangle(874, 169, 127, 33));
		hits.add(new Rectangle(968, 201, 33, 699));
		
		for (int i = 0; i < notes.length; i++){
			notes[i] = new Note(c, hits);
		}
		
		vault = new Vault(1080, 836, notes, false, 0);

		for (int i = 0; i < money.length; i++){
			money[i] = new MoneyBag(c, hits);
			totalValue += money[i].getValue();
		}
		
		timer = new Timer(1150, 40, 25, 30, "WHITE", false);
	}

	public void paint(Graphics2D g2d){
		//g2d.drawImage(level, 0, 0, null);

		for (int i = 0; i < money.length; i++){
			money[i].paint(g2d);
		}

		
		vault.paint(g2d);

		timer.paint(g2d);
	}

	public void update(Player p, CashOut c){

		for (int i = 0; i < money.length; i++){
			money[i].collect(p);
		}

		vault.update(c.getInventory(), notesCollected);
	}


	public BufferedImage getBagImage(){
		if (money.length > 0) return money[0].getImage();
		return null;
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
	}

	public void keyPressed(KeyEvent e, Player p) {
		vault.keyPressed(e,p, notesValues);
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
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		vault.mouseReleased(e, notesValues);

	}

	public void sendInventory(Inventory i){
		vault.setInventory(i);
	}
	
	public boolean getHacking(){
		return false;
	}

	@Override
	public void setCurrentLevel(boolean b, Player p) {
		p.setXY(540, 775, 0);
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

	public Image getLaserImage() {
		
		return laser.getImage();
	}

	public Image getOfficerImage() {
		return officer.getImage();
	}

	@Override
	public void setHighScore(int s) {
		highScore = s;
		isUnlocked = !(s == 0);
	}

	@Override
	public int getHighScore() {
		return highScore ;
	}

	@Override
	public void unlock() {
		isUnlocked = true;
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

	@Override
	public BufferedImage getBG() {
		return level;
	}

}
