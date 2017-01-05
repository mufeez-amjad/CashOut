import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

public class Vault {

	private BufferedImage vault;
	private Rectangle tile1;
	private Rectangle tile2;
	private Rectangle tile3;
	private Rectangle tile4;
	private Rectangle current;
	private boolean noCurrent = true;
	private boolean correct1 = false;
	private boolean correct2 = false;
	private boolean correct3 = false;
	private boolean correct4 = false;
	private String msg1 = "";
	private String msg2 = "";
	private String msg3 = "";
	private String msg4 = "";
	List<Integer> code = new ArrayList<>();
	private Font clockFont;

	public Vault(int x, int y, int width, int height) {
		try {
			clockFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/digital.ttf"));
			clockFont = clockFont.deriveFont(100F);
			vault = ImageIO.read(getClass().getResource("/vault.png"));
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tile1 = new Rectangle(x, y, width, height);
		tile2 = new Rectangle(x + width + 5, y, width, height);
		tile3 = new Rectangle(x + 2 * (width + 5), y, width, height);
		tile4 = new Rectangle(x + 3 * (width + 5), y, width, height);
	}

	public void paint(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.fillRect(tile1.x, tile1.y, tile1.width, tile1.height);
		g2d.fillRect(tile2.x, tile2.y, tile2.width, tile2.height);
		g2d.fillRect(tile3.x, tile3.y, tile3.width, tile3.height);
		g2d.fillRect(tile4.x, tile4.y, tile4.width, tile4.height);
		g2d.setColor(Color.WHITE);
		g2d.setFont(clockFont);
		g2d.drawString(msg1, tile1.x + 10, tile1.y + tile1.height - 30);
		g2d.drawString(msg2, tile2.x + 10, tile2.y + tile2.height - 30);
		g2d.drawString(msg3, tile3.x + 10, tile3.y + tile3.height - 30);
		g2d.drawString(msg4, tile4.x + 10, tile4.y + tile4.height - 30);
		g2d.setColor(Color.BLACK);
		g2d.setFont(CashOut.getFontBig());
		g2d.drawString("Secret Code: ", 200, 300);
		g2d.drawImage(vault, 900, 800, null);
		for (int i = 0; i < 4; i++) {
			g2d.drawString(String.valueOf(code.get(i)), 220 + 50 * i, 400);
		}
		/*
		 * g2d.setColor(Color.RED); if (current == tile1 && !noCurrent) {
		 * g2d.drawRect(tile1.x, tile1.y, tile1.width, tile1.height); } if
		 * (current == tile2 && !noCurrent) { g2d.drawRect(tile2.x, tile2.y,
		 * tile2.width, tile2.height); } if (current == tile3 && !noCurrent) {
		 * g2d.drawRect(tile3.x, tile3.y, tile3.width, tile3.height); } if
		 * (current == tile4 && !noCurrent) { g2d.drawRect(tile4.x, tile4.y,
		 * tile4.width, tile4.height); }
		 */
		if (correct1) {
			g2d.setColor(Color.GREEN);
		} else {
			g2d.setColor(Color.RED);
		}
		g2d.fillRect(100, 100, 50, 50);
		if (correct2) {
			g2d.setColor(Color.GREEN);
		} else {
			g2d.setColor(Color.RED);
		}
		g2d.fillRect(200, 100, 50, 50);
		if (correct3) {
			g2d.setColor(Color.GREEN);
		} else {
			g2d.setColor(Color.RED);
		}
		g2d.fillRect(300, 100, 50, 50);
		if (correct4) {
			g2d.setColor(Color.GREEN);
		} else {
			g2d.setColor(Color.RED);
		}
		g2d.fillRect(400, 100, 50, 50);
	}

	public Rectangle current(MouseEvent e) {
		if (e.getX() > tile1.x && e.getY() > tile1.y && e.getX() < tile1.x + tile1.width
				&& e.getY() < tile1.y + tile1.height) {
			current = tile1;
			noCurrent = false;
		} else if (e.getX() > tile2.x && e.getY() > tile2.y && e.getX() < tile2.x + tile2.width
				&& e.getY() < tile2.y + tile2.height) {
			current = tile2;
			noCurrent = false;
		} else if (e.getX() > tile3.x && e.getY() > tile3.y && e.getX() < tile3.x + tile3.width
				&& e.getY() < tile3.y + tile3.height) {
			current = tile3;
			noCurrent = false;
		} else if (e.getX() > tile4.x && e.getY() > tile4.y && e.getX() < tile4.x + tile4.width
				&& e.getY() < tile4.y + tile4.height) {
			current = tile4;
			noCurrent = false;
		} else {
			noCurrent = true;
		}
		return current;
	}

	public void generateCode(Note[] n) {

		for (int i = 0; i < n.length; i++) {
			code.add(n[i].getNumber());
		}
		Collections.shuffle(code);
	}
	
	public void check(Rectangle[] r, int num1, int num2, int num3, int num4) {
		check1(r, num1, num2, num3, num4);
		check2(r, num1, num2, num3, num4);
		check3(r, num1, num2, num3, num4);
		check4(r, num1, num2, num3, num4);
	}

	public void check1(Rectangle[] r, int num1, int num2, int num3, int num4) {
		if (tile1.contains(r[0].x, r[0].y) && num1 == code.get(0)) {
			correct1 = true;
			msg1 = String.valueOf(num1);
		}
		else if (tile1.contains(r[1].x, r[1].y) && num2 == code.get(0)) {
			correct1 = true;
			msg1 = String.valueOf(num2);
		}
		else if (tile1.contains(r[2].x, r[2].y) && num3 == code.get(0)) {
			correct1 = true;
			msg1 = String.valueOf(num3);
		}
		else if (tile1.contains(r[3].x, r[3].y) && num4 == code.get(0)) {
			correct1 = true;
			msg1 = String.valueOf(num4);
		}
		else {
			correct1 = false;
			msg1 = " ";
		}
	}

	public void check2(Rectangle[] r, int num1, int num2, int num3, int num4) {
		if (tile2.contains(r[0].x, r[0].y) && num1 == code.get(1)) {
			correct2 = true;
			msg2 = String.valueOf(num1);
		}
		else if (tile2.contains(r[1].x, r[1].y) && num2 == code.get(1)) {
			correct2 = true;
			msg2 = String.valueOf(num2);
		}
		else if (tile2.contains(r[2].x, r[2].y) && num3 == code.get(1)) {
			correct2 = true;
			msg2 = String.valueOf(num3);
		}
		else if (tile2.contains(r[3].x, r[3].y) && num4 == code.get(1)) {
			correct2 = true;
			msg2 = String.valueOf(num4);
		}
		else {
			correct2 = false;
			msg2 = " ";
		}
	}
	
	public void check3(Rectangle[] r, int num1, int num2, int num3, int num4) {
		if (tile3.contains(r[0].x, r[0].y) && num1 == code.get(2)) {
			correct3 = true;
			msg3 = String.valueOf(num1);
		}
		else if (tile3.contains(r[1].x, r[1].y) && num2 == code.get(2)) {
			correct3 = true;
			msg3 = String.valueOf(num2);
		}
		else if (tile3.contains(r[2].x, r[2].y) && num3 == code.get(2)) {
			correct3 = true;
			msg3 = String.valueOf(num3);
		}
		else if (tile3.contains(r[3].x, r[3].y) && num4 == code.get(2)) {
			correct3 = true;
			msg3 = String.valueOf(num4);
		}
		else {
			correct3 = false;
			msg3 = " ";
		}
	}
	
	public void check4(Rectangle[] r, int num1, int num2, int num3, int num4) {
		if (tile4.contains(r[0].x, r[0].y) && num1 == code.get(3)) {
			correct4 = true;
			msg4 = String.valueOf(num1);
		}
		else if (tile4.contains(r[1].x, r[1].y) && num2 == code.get(3)) {
			correct4 = true;
			msg4 = String.valueOf(num2);
		}
		else if (tile4.contains(r[2].x, r[2].y) && num3 == code.get(3)) {
			correct4 = true;
			msg4 = String.valueOf(num3);
		}
		else if (tile4.contains(r[3].x, r[3].y) && num4 == code.get(3)) {
			correct4 = true;
			msg4 = String.valueOf(num4);
		}
		else {
			correct4 = false;
			msg4 = " ";
		}
	}
}
