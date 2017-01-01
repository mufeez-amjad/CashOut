import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Inventory {

	private BufferedImage note;
	private BufferedImage gun;
	private BufferedImage phone;
	private BufferedImage noteSmall;

	private boolean isExpanded = false;
	private int numOfBullets;

	public Inventory(){
		try { 
			note = ImageIO.read(getClass().getResource("/Images/Note (Inventory).png"));
		} catch (IOException e) { 
			System.err.println("Note.png could not be found");
		}

		try { 
			noteSmall = ImageIO.read(getClass().getResource("/Images/Note.png"));
		} catch (IOException e) { 
			System.err.println("Note.png could not be found");
		} 

		try { 
			gun = ImageIO.read(getClass().getResource("/Images/Gun (Inventory).png"));
		} catch (IOException e) { 
			System.err.println("Note.png could not be found");
		}

		try { 
			phone = ImageIO.read(getClass().getResource("/Images/Phone (Inventory).png"));
		} catch (IOException e) { 
			System.err.println("Note.png could not be found");
		}
	}
	
	public BufferedImage getNoteImg(){
		return note;
	}
	
	public BufferedImage getGunImg(){
		return gun;
	}
	
	public BufferedImage getHackImg(){
		return phone;
	}

	public void paint(Graphics2D g2d){
		Color front = new Color(88, 89, 91, 127);
		Color back = new Color(88, 89, 91, 75);
		for (int i = 1; i <= 3; i++){
			g2d.setColor(back);
			g2d.fillRect(375 + (i * 100), 725, 75, 75);
			g2d.setColor(front);
			g2d.fillRect(375 + (i * 100) - 5, 725 - 5, 75, 75);
		}

		g2d.drawImage(gun, 477, 727, null);
		Color color = new Color(55 + (20 * numOfBullets), 255 - numOfBullets * 21, 0);
		g2d.setColor(color);
		g2d.fillOval(520, 729, 20, 20);
		g2d.setFont(CashOut.getFontTiny());
		if (numOfBullets < 5) g2d.setColor(Color.BLACK);
		else g2d.setColor(Color.WHITE);

		g2d.drawString(String.valueOf(9-numOfBullets), 527, 743);
				
		g2d.drawImage(phone, 577, 727, null);
		if (Note.getCollected() > 0){
			g2d.drawImage(note, 677, 727, null);
			if (Note.getCollected() > 1) {
				if (Note.getCollected() < 4) g2d.setColor(Color.decode("0x990000"));
				else g2d.setColor(Color.decode("0x065C27"));

				g2d.fillOval(715, 729, 15, 15);
				g2d.setColor(Color.WHITE);
				g2d.setFont(CashOut.getFontTiny());
				g2d.drawString(String.valueOf(Note.getCollected()), 719, 740);
			}
			if (isExpanded){
				g2d.setColor(front);
				int [] xPoints = {700, 710, 720};
				int [] yPoints = {700, 710, 700};
				g2d.fillPolygon(xPoints, yPoints, 3);

				g2d.fillRect(684 - (50 * (Note.getCollected() - 1))/2, 650, 50 * Note.getCollected(), 50);
				for (int i = 0; i < Note.getCollected(); i++) {
					g2d.setColor(Color.black);

					if (Note.getCollected() == 1) {
						g2d.drawImage(noteSmall, 746 - 50, 658, null);
						g2d.drawString(String.valueOf(Note.getNotes().get(i)), 706, 679);
					}

					else if (Note.getCollected() == 2) {
						g2d.drawImage(noteSmall, 720 - 50 * i, 658, null);
						g2d.drawString(String.valueOf(Note.getNotes().get(i)), 730 - 50 * i, 679);
					}

					else if (Note.getCollected() == 3) {
						g2d.drawImage(noteSmall, 746 - 50 * i, 658, null);
						g2d.drawString(String.valueOf(Note.getNotes().get(i)), 755 - 50 * i, 679);
					}

					else if (Note.getCollected() == 4) {
						g2d.drawImage(noteSmall, 770 - 50 * i, 658, null);
						g2d.drawString(String.valueOf(Note.getNotes().get(i)), 780 - 50 * i, 679);
					}
				}

			}


		}
	}
	public void update(Player p){
		numOfBullets = p.amountOfBullets();
	}

	public void expandNotes(){
		if (Note.getCollected() > 0){
			if (isExpanded) isExpanded = false;
			else isExpanded = true;
		}
	}


}
