import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Inventory {

	private BufferedImage note;
	private BufferedImage gun;
	private BufferedImage phone;
	private BufferedImage noteSmall;
	private int x;
	private int y;
	private boolean firstDragged = false;
	private boolean secondDragged = false;
	private boolean thirdDragged = false;
	private boolean fourthDragged = false;
	private Rectangle[] r = new Rectangle[4];

	private boolean isExpanded = false;
	private int numOfBullets;
	private Level current;

	public Inventory() {

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
		for (int i = 0; i < 4; i++) {
			r[i] = new Rectangle();
			if (i == 0) {
				r[i].x = 620 + 1;
			}
			if (i == 1) {
				r[i].x = 670 + 1;
			}
			if (i == 2) {
				r[i].x = 720 + 1;
			}
			if (i == 3) {
				r[i].x = 770 + 1;
			}
			r[i].y = 658 + 1;
			r[i].width = noteSmall.getWidth();
			r[i].height = noteSmall.getHeight();
		}
	}

	public void paint(Graphics2D g2d) {
		Color front = new Color(88, 89, 91, 127);
		Color back = new Color(88, 89, 91, 75);
		for (int i = 1; i <= 3; i++) {
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
		
		if (current != null){
			if (current.getNotesCollected() > 0) {
				g2d.drawImage(note, 677, 727, null);
				if (current.getNotesCollected() > 1) {
					if (current.getNotesCollected() < 4)
						g2d.setColor(Color.decode("0x990000"));
					else
						g2d.setColor(Color.decode("0x065C27"));

					g2d.fillOval(715, 729, 15, 15);
					g2d.setColor(Color.WHITE);
					g2d.setFont(CashOut.getFontTiny());
					g2d.drawString(String.valueOf(current.getNotesCollected()), 719, 740);
				}
				if (isExpanded) {
					g2d.setColor(front);
					int[] xPoints = { 700, 710, 720 };
					int[] yPoints = { 700, 710, 700 };
					g2d.fillPolygon(xPoints, yPoints, 3);

					g2d.fillRect(684 - (50 * (current.getNotesCollected() - 1)) / 2, 650, 50 * current.getNotesCollected(), 50);
					for (int i = 0; i < current.getNotesValues().size(); i++) {
							g2d.setColor(Color.black);
						if (current.getNotesCollected() == 1) {
							if (firstDragged) {
								g2d.drawImage(noteSmall, x, y, null);
								g2d.drawString(String.valueOf(current.getNotesValues().get(i)), x, y);
							} else {
								g2d.drawImage(noteSmall, 746 - 50, 658, null);
								g2d.drawString(String.valueOf(current.getNotesValues().get(i)), 706, 679);
							}

						}

						else if (current.getNotesCollected() == 2) {
							g2d.drawImage(noteSmall, 720 - 50 * i, 658, null);
							g2d.drawString(String.valueOf(current.getNotesValues().get(i)), 730 - 50 * i, 679);
						}

						else if (current.getNotesCollected() == 3) {
							g2d.drawImage(noteSmall, 746 - 50 * i, 658, null);
							g2d.drawString(String.valueOf(current.getNotesValues().get(i)), 755 - 50 * i, 679);
						}

						else if (current.getNotesCollected() == 4) {
							if (firstDragged) {
								g2d.drawImage(noteSmall, r[0].x, r[0].y, null);
								g2d.drawString(String.valueOf(current.getNotesValues().get(3)), r[0].x + r[0].width / 2 - 3, r[0].y + r[0].height / 2 + 3);
							}
							if (secondDragged) {
								g2d.drawImage(noteSmall, r[1].x, r[1].y, null);
								g2d.drawString(String.valueOf(current.getNotesValues().get(2)), r[1].x + r[1].width / 2 - 3, r[1].y + r[1].height / 2 + 3);
							}
							if (thirdDragged) {
								g2d.drawImage(noteSmall, r[2].x, r[2].y, null);
								g2d.drawString(String.valueOf(current.getNotesValues().get(1)), r[2].x + r[2].width / 2 - 3, r[2].y + r[2].height / 2 + 3);
							}
							if (fourthDragged) {
								g2d.drawImage(noteSmall, r[3].x, r[3].y, null);
								g2d.drawString(String.valueOf(current.getNotesValues().get(0)), r[3].x + r[3].width / 2 - 3, r[3].y + r[3].height / 2 + 3);
							} 
							//g2d.drawImage(noteSmall, 770 - 50 * i, 658, null);
							if (!fourthDragged && i == 0) g2d.drawImage(noteSmall, 770 - 50 * 0, 658, null);
							if (!thirdDragged && i == 1) g2d.drawImage(noteSmall, 770 - 50 * 1, 658, null);
							if (!secondDragged && i == 2) g2d.drawImage(noteSmall, 770 - 50 * 2, 658, null);
							if (!firstDragged && i == 3) g2d.drawImage(noteSmall, 770 - 50 * 3, 658, null);
							if (!fourthDragged && i == 0) g2d.drawString(String.valueOf(current.getNotesValues().get(0)), 780 - 50 * 0, 679);
							if (!thirdDragged && i == 1) g2d.drawString(String.valueOf(current.getNotesValues().get(1)), 780 - 50 * 1, 679);
							if (!secondDragged && i == 2) g2d.drawString(String.valueOf(current.getNotesValues().get(2)), 780 - 50 * 2, 679);
							if (!firstDragged && i == 3) g2d.drawString(String.valueOf(current.getNotesValues().get(3)), 780 - 50 * 3, 679);
						}
					}
				}
			}
		}
	}

	public void update(Player p, Levels l){
		numOfBullets = p.amountOfBullets();
		current = l.getCurrent();
	}

	public void expandNotes() {
		if (current.getNotesCollected() > 0) {
			if (isExpanded)
				isExpanded = false;
			else
				isExpanded = true;
		}
	}

	public void isDragged(MouseEvent e) {
		if (r[0].contains(e.getX(), e.getY())) {
			firstDragged = true;
			r[0].x = e.getX() - r[0].width/2;
			r[0].y = e.getY() - r[0].height/2;
		}
		if (r[1].contains(e.getX(), e.getY())) {
			secondDragged = true;
			r[1].x = e.getX() - r[1].width/2;
			r[1].y = e.getY() - r[1].height/2;
		}
		if (r[2].contains(e.getX(), e.getY())) {
			thirdDragged = true;
			r[2].x = e.getX() - r[2].width/2;
			r[2].y = e.getY() - r[2].height/2;
		}
		if (r[3].contains(e.getX(), e.getY())) {
			fourthDragged = true;
			r[3].x = e.getX() - r[3].width/2;
			r[3].y = e.getY() - r[3].height/2;
		}
	}

	public Rectangle[] getRectangles() {
		return r;
	}

	public BufferedImage getNoteImage(){
		return note;
	}
	
	public void reset(boolean unExpand){
		for (int i = 0; i < 4; i++) {
			r[i] = new Rectangle();
			if (i == 0) {
				r[i].x = 620 + 1;
			}
			if (i == 1) {
				r[i].x = 670 + 1;
			}
			if (i == 2) {
				r[i].x = 720 + 1;
			}
			if (i == 3) {
				r[i].x = 770 + 1;
			}
			r[i].y = 658 + 1;
			r[i].width = noteSmall.getWidth();
			r[i].height = noteSmall.getHeight();
		}
		firstDragged = false;
		secondDragged = false;
		thirdDragged = false;
		fourthDragged = false;
		if (unExpand) isExpanded = false;
	}

}
