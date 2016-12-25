import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Inventory {
	
	private BufferedImage note;
	private BufferedImage gun;
	private BufferedImage phone;
	
	public Inventory(){
		try { 
			note = ImageIO.read(getClass().getResource("/Note (Inventory).png"));
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
				g2d.drawString(String.valueOf(Note.getCollected()), 669, 740);
			}
		}
		
	}

	public void addNote(Note n){

	}

}
