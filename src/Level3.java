import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Level3 extends Level{
	private BufferedImage level;
	private Note[] notes = new Note[4];
	private MoneyBag[] money = new MoneyBag[5];
	private Laser laser1;
	private Laser laser2;
	private Laser laser3;

	private ArrayList<Rectangle> hits = new ArrayList<Rectangle>();
	private Timer timer;
	private boolean isUnlocked = false;
	private boolean hackingLaser = true;
	private int totalValue = 0;
	private int notesCollected = 0;
	private ArrayList<Integer> notesValues = new ArrayList<Integer>();
	private Vault vault;
	private int score = 0;
	private int highScore = 0;
	
	public Level3(CashOut c, Player p){

		try { 
			level = ImageIO.read(getClass().getResource("/Images/Levels-03.png"));
		} catch (IOException e) { 
			System.err.println("Levels-03.png could not be found");
		}
		
		hits.add(new Rectangle(310, 0, 33, 109)); //R1
		hits.add(new Rectangle(310, 268, 33, 112)); //R2
		hits.add(new Rectangle(0, 350, 309, 31)); //R3
		hits.add(new Rectangle(817, 0, 33, 333)); //R4
		hits.add(new Rectangle(817, 333, 116, 30)); //R5
		hits.add(new Rectangle(1087, 332, 106, 32)); //R6
		hits.add(new Rectangle(310, 555, 73, 30)); //R7
		hits.add(new Rectangle(310, 585, 33, 282)); //R8
		hits.add(new Rectangle(540, 555, 139, 30)); //R9
		hits.add(new Rectangle(587, 587, 32, 283)); //R10
		hits.add(new Rectangle(837, 556, 134, 30)); //R11
		hits.add(new Rectangle(879, 587, 32, 283)); //R12
		hits.add(new Rectangle(1127, 556, 66, 31)); //R13
		
		for (int i = 0; i < notes.length; i++){
			notes[i] = new Note(c, hits);
		}

		for (int i = 0; i < money.length; i++){
			money[i] = new MoneyBag(c, hits);
			totalValue += money[i].getValue();
		}

		vault = new Vault(140, 836, notes, true, 0);
		laser1 = new Laser(970, 559, 0, 45); 
		laser2 = new Laser(328, 126, 90, 30);
		laser3 = new Laser(328, 397, 90, 25);
		
		
		timer = new Timer(1150, 40, 25, 75, "WHITE", false);
	}

	public void paint(Graphics2D g2d){
		//g2d.drawImage(level, 0, 0, null);
		if (!vault.isHacking()) vault.paint(g2d);
		
		for (int i = 0; i < notes.length; i++){
			notes[i].paint(g2d);
		}
		
		for (int i = 0; i < money.length; i++){
			money[i].paint(g2d);
		}

		if (!laser1.isHacking() && !laser2.isHacking() && !laser3.isHacking()){
			laser1.paint(g2d);
			laser2.paint(g2d);
			laser3.paint(g2d);
		}
		else if (laser1.isHacking()) {
			laser2.paint(g2d);
			laser3.paint(g2d);
			laser1.paint(g2d);
		}
		else if (laser2.isHacking()) {
			laser3.paint(g2d);
			laser1.paint(g2d);
			laser2.paint(g2d);
		}
		else if (laser3.isHacking()){
			laser1.paint(g2d);
			laser2.paint(g2d);
			laser3.paint(g2d);
		}
		
		
		
		if (vault.isHacking()) vault.paint(g2d);	
		
		timer.paint(g2d);
	}

	public void update(Player p, CashOut c){
		if (notesCollected < 4){ //remove after development
			for (int i = 0; i < notes.length; i++){
				notes[i].collect(p, this);
				//notes[i].collectAll(p, this);
			}
		}
		laser1.update(p, c);
		laser2.update(p, c);
		laser3.update(p, c);
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
		laser1.mouseMoved(e);
		laser2.mouseMoved(e);
		laser3.mouseMoved(e);
	}

	public void keyPressed(KeyEvent e, Player p) {
		vault.keyPressed(e,p, notesValues);
		laser1.keyPressed(e, p);
		laser2.keyPressed(e, p);
		laser3.keyPressed(e, p);
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
		return vault.isHacking() || laser1.isHacking() || laser2.isHacking() || laser3.isHacking();
	}

	public void setCurrentLevel(boolean currentLevel, Player p) {
		p.setXY(540, 86, 180);
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
		// TODO Auto-generated method stub
		return highScore;
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
		// TODO Auto-generated method stub
		return level;
	}
}
