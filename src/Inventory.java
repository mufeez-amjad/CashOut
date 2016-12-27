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

	public Inventory(){
		try { 
			note = ImageIO.read(getClass().getResource("/Note (Inventory).png"));
		} catch (IOException e) { 
			System.err.println("Note.png could not be found");
		}

		try { 
			noteSmall = ImageIO.read(getClass().getResource("/Note.png"));
		} catch (IOException e) { 
			System.err.println("Note.png could not be found");
		} 

		try { 
			gun = ImageIO.read(getClass().getResource("/Gun (Inventory).png"));
		} catch (IOException e) { 
			System.err.println("Note.png could not be found");
		}

		try { 
			phone = ImageIO.read(getClass().getResource("/Phone (Inventory).png"));
		} catch (IOException e) { 
			System.err.println("Note.png could not be found");
		}
	}

	public void paint(Graphics2D g2d){
		Color front = new Color(88, 89, 91, 127);
		Color back = new Color(88, 89, 91, 75);
		for (int i = 1; i <= 3; i++){
			g2d.setColor(back);
			g2d.fillRect(325 + (i * 100), 725, 75, 75);
			g2d.setColor(front);
			g2d.fillRect(325 + (i * 100) - 5, 725 - 5, 75, 75);
		}

		g2d.drawImage(gun, 427, 727, null);
		g2d.drawImage(phone, 527, 727, null);
		if (Note.getCollected() > 0){
			g2d.drawImage(note, 627, 727, null);
			if (Note.getCollected() > 1) {
				if (Note.getCollected() < 4) g2d.setColor(Color.red);
				else g2d.setColor(Color.green);
				g2d.fillOval(665, 729, 15, 15);
				g2d.setColor(Color.WHITE);
				g2d.setFont(CashOut.getFontTiny());
				g2d.drawString(String.valueOf(Note.getCollected()), 669, 740);
			}
			if (isExpanded){
				g2d.setColor(front);
				int [] xPoints = {650, 660, 670};
				int [] yPoints = {700, 710, 700};
				g2d.fillPolygon(xPoints, yPoints, 3);

				g2d.fillRect(634 - (50 * (Note.getCollected() - 1))/2, 650, 50 * Note.getCollected(), 50);
				for (int i = 0; i < Note.getCollected(); i++) {
					g2d.setColor(Color.black);
					
					if (Note.getCollected() == 1) {
						g2d.drawImage(noteSmall, 696 - 50, 658, null);
						g2d.drawString(String.valueOf(Note.getNotes().get(i)), 656, 679);
					}
					
					else if (Note.getCollected() == 2) {
						g2d.drawImage(noteSmall, 670 - 50 * i, 658, null);
						g2d.drawString(String.valueOf(Note.getNotes().get(i)), 680 - 50 * i, 679);
					}
					
					else if (Note.getCollected() == 3) {
						g2d.drawImage(noteSmall, 696 - 50 * i, 658, null);
						g2d.drawString(String.valueOf(Note.getNotes().get(i)), 705 - 50 * i, 679);
					}
					
					else if (Note.getCollected() == 4) {
						g2d.drawImage(noteSmall, 720 - 50 * i, 658, null);
						g2d.drawString(String.valueOf(Note.getNotes().get(i)), 730 - 50 * i, 679);
					}
				}

			}


		}
	}


	public void expandNotes(){
		if (Note.getCollected() > 0){
			if (isExpanded) isExpanded = false;
			else isExpanded = true;
		}
	}


}
