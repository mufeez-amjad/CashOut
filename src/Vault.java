import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Vault {
	private int x;
	private int y;
	private BufferedImage img;
	private boolean close;
	private VaultPuzzle vp;
	private boolean hacking;
	private Note[] n;
	private Inventory i;
	private boolean unlocked;
	
	public Vault(int x, int y, Note[] n) {
		try {
			img = ImageIO.read(getClass().getResource("/Images/vault.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		unlocked = false;
		this.n = n;
		close = false;
		hacking = false;
		vp = new VaultPuzzle(411, 362, 80, 160);
		this.x = x;
		this.y = y;
		vp.generateCode(n);
	}
	
	public void paint(Graphics2D g2d) {
		g2d.drawImage(img, x, y, null);
		if (hacking) {
			vp.paint(g2d);
		}
	}

	
	public void mouseClicked(MouseEvent e) {
		if (hacking) {
			for (int i = 0; i < n.length; i++) {
				//vp.check(n[i].r, num1, num2, num3, num4);
			}	
		}
	}
	
	public void mouseReleased(MouseEvent e, ArrayList<Integer> notesValues) {
		if (hacking && notesValues.size() == 4) {
			vp.check(i.getRectangles(), notesValues.get(3), notesValues.get(2), notesValues.get(1), notesValues.get(0));
		}
	}
	
	public void setInventory(Inventory i){
		this.i = i;
	}

	public void keyPressed(KeyEvent e, Player p, ArrayList<Integer> notesValues) {
		int x1 = p.getPoint().x;
		int y1 = p.getPoint().y;
		
		int x2 = p.getPoint2().x;
		int y2 = p.getPoint2().y;
		if (!unlocked && notesValues.size() == 4){
			
			//if (p.getX() > x - 100 && p.getX() < x + 100 && p.getY() > y - 100 && p.getY() < y + 100){
			if ((x1 > x && x1 < x + 100 && y1 > y  && y1 < y + 100) || (x2 > x && x2 < x + 100 && y2 > y && y2 < y + 100)){ //if player is close by
				if (e.getKeyCode()==KeyEvent.VK_X){
					//addNotesValues(l.getNotesValues());
					//reset();
					//System.out.println("HACKING");
					hacking = !hacking;;
				}
			}
		}
		else {
			//collect more notes
		}
	}
	
	public boolean getUnlocked(){
		return vp.getSolved();
	}

	public boolean isHacking() {
		return vp.getSolved();
	}
}
