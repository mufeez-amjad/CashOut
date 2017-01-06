import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Level {
	private Note[] notes = new Note[4];
	private MoneyBag[] money = new MoneyBag[5];
	
	private Laser[] lasers = new Laser[4];
	private Camera[] cameras = new Camera[5];
	private Officer[] officers = new Officer[5];
	private Vault vault;
	private ArrayList<Integer> notesValues = new ArrayList<Integer>();

	private BufferedImage level;
	private ArrayList<Rectangle> hits = new ArrayList<Rectangle>();
	private Timer timer;
	private boolean isFinished = false;
	private boolean isUnlocked = false;	
		
	public abstract void paint(Graphics2D g2d);
	
	public abstract void update(Player p, CashOut c);
		
	public abstract boolean hit(int x, int y);

	public abstract Timer getTimer();	
	
	public abstract boolean isFinished();
	public abstract boolean isUnlocked();
	
	public abstract boolean isHackingLaser();
	public abstract boolean isHackingCamera();
	public abstract boolean isHackingVault();

	public abstract void mouseMoved(MouseEvent e);
	public abstract void keyPressed(KeyEvent e, Player p);

	public abstract int getTotalValue();

	public abstract int getNotesCollected();

	public abstract void addNotesCollected(int n);

	public abstract ArrayList<Integer> getNotesValues();

	public abstract void addNotesValues(int number);
	
}
