import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Level5 extends Level {
	
	private BufferedImage level;
	private Note[] notes = new Note[4];
	private MoneyBag[] money = new MoneyBag[5];
	private Laser laser;
	private Officer officer; 
	private Camera camera;
	private ArrayList<Rectangle> hits = new ArrayList<Rectangle>();
	private Timer timer;
	private boolean isFinished = false;
	private boolean isUnlocked = false;
	
	public Level5(CashOut c) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paint(Graphics2D g2d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Player p, CashOut c) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hit(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Timer getTimer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return isFinished;
	}

	@Override
	public boolean isUnlocked() {
		// TODO Auto-generated method stub
		return isUnlocked;
	}

	@Override
	public boolean isHackingLaser() {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e, Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTotalValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNotesCollected() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addNotesCollected(int n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Integer> getNotesValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addNotesValues(int number) {
		// TODO Auto-generated method stub
		
	}

}
