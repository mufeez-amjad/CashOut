import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Vault {
	private int x;
	private int y;
	private BufferedImage vault;
	private VaultPuzzle vp;
	private boolean hacking;
	private Inventory inv;
	private boolean unlocked;
	private boolean enoughNotes;
	private boolean attempted = false;
	private boolean ifHackable = true;
	private double angle;
	private int attemptedCounter;
	private Rectangle hitbox;

	public Vault(int x, int y, Note[] n, boolean b, double a) {
		try {
			vault = ImageIO.read(getClass().getResource("/Images/vault.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ifHackable = b;
		angle = a;
		enoughNotes = false;
		unlocked = false;
		hacking = false;
		vp = new VaultPuzzle(411, 362, 80, 160);
		this.x = x;
		this.y = y;
		vp.generateCode(n);
		
		if (angle == 0.0) {
			hitbox = new Rectangle(x - 80, y - 30, vault.getWidth() + 60, vault.getHeight() + 20);
		}
		if (angle == 90.0) {
			hitbox = new Rectangle(x - vault.getWidth() / 2, y - 60, vault.getHeight() + 50, vault.getWidth() + 40);
		}
		if (angle == 180.0) {
			hitbox = new Rectangle(x - 20, y - 20, vault.getWidth() + 60, vault.getHeight() + 20);
		}
	}

	public void paint(Graphics2D g2d) {
		double rotationRequired = Math.toRadians (angle);
		double locationX = vault.getWidth() / 2;
		double locationY = vault.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		g2d.drawImage(op.filter(vault, null), x - 50, y - 40, null);
		g2d.setColor(Color.WHITE);
		if (enoughNotes && hacking) {
			vp.paint(g2d);
		}
		else if (attemptedCounter > 0 && attemptedCounter < 250) {
			Color yellowTrans = new Color(255, 255, 0, 127);
			g2d.setColor(yellowTrans);
			g2d.fillRect(0,0,1200, 900);
			FontMetrics fontMetrics = g2d.getFontMetrics(CashOut.getFontHuge());
			g2d.setColor(Color.black);
			g2d.setFont(CashOut.getFontHuge());
			int stringLength = fontMetrics.stringWidth("NOT ENOUGH NOTES");
			int stringHeight = fontMetrics.getHeight(); 
			g2d.drawString("NOT ENOUGH NOTES", (CashOut.getFrameWidth()/2) - (stringLength/2), (CashOut.getFrameHeight()/2) - (stringHeight/2));
		}
	}

	public void update(Inventory i, int notesCollected){
		enoughNotes = notesCollected == 4;
		if (hacking) setHacking();
		inv = i;
		if (attempted) attemptedCounter++;
		if (attemptedCounter == 250){
			attemptedCounter = 0;
			attempted = false;
		}
	}

	public void setHacking() {
		hacking = !vp.getSolved();
	}

	public void mouseReleased(MouseEvent e, ArrayList<Integer> notesValues) {
		if (hacking) {
			vp.check(inv.getRectangles(), notesValues.get(3), notesValues.get(2), notesValues.get(1), notesValues.get(0));
		}
	}
	
	public void mouseClicked(MouseEvent e, Inventory i) {
		if (hacking) {
			vp.mouseClicked(e, i);
		}
	}

	public void setInventory(Inventory i){
		inv = i;
	}

	public void keyPressed(KeyEvent e, Player p, ArrayList<Integer> notesValues) {
		p.getPoint();
		p.getPoint();

		p.getPoint2();
		p.getPoint2();
		
		if (!unlocked) {
			if (p.getHitbox().intersects(hitbox)){
					if (e.getKeyCode()==KeyEvent.VK_X){
						if (!ifHackable) unlocked = true;
						else attempted = true;
						if (notesValues.size() == 4){
							hacking = !hacking;
							attempted = false;
					}
				}
			}
		}
	}

	public boolean isHacking() {
		return hacking;
	}

	public boolean getUnlocked() {
		return vp.getSolved() || unlocked;
	}
}
