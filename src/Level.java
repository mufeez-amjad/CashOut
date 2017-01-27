import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Level {
			
	public abstract void paint(Graphics2D g2d);
	
	public abstract void update(Player p, CashOut c);
		
	public abstract boolean hit(Rectangle h);

	public abstract Timer getTimer();	
	
	public abstract boolean isFinished();
	public abstract boolean isUnlocked();
	
	public abstract boolean isHackingLaser();
	public abstract boolean isHackingCamera();
	public abstract boolean isHackingVault();

	public abstract void mouseMoved(MouseEvent e);
	public abstract void keyPressed(KeyEvent e, Player p);
	public abstract void mouseClicked(MouseEvent e, Inventory i);
	public abstract void mouseReleased(MouseEvent e);
	public abstract int getTotalValue();

	public abstract int getNotesCollected();

	public abstract void addNotesCollected(int n);

	public abstract ArrayList<Integer> getNotesValues();

	public abstract void addNotesValues(int number);
	
	public abstract void sendInventory(Inventory i);

	public abstract boolean getHacking();
	
	public abstract void setCurrentLevel(boolean b, Player p);

	public abstract void addScore(int value);

	public abstract int getScore();

	public abstract Note[] getNotes();
	
	public abstract void setHighScore(int s);
	
	public abstract int getHighScore();
	
	public abstract void unlock();
	public abstract boolean isOfficer1Dead();
	public abstract boolean isOfficer2Dead();
	public abstract void paintOfficer1(Graphics2D g2d);
	public abstract void paintOfficer2(Graphics2D g2d);

	public abstract BufferedImage getBG();
}
