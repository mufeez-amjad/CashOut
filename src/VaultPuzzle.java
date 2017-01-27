import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VaultPuzzle {
	//check if have four notes
	//user drags note

	private Rectangle tile1;
	private Rectangle tile2;
	private Rectangle tile3;
	private Rectangle tile4;
	private boolean correct1 = false;
	private boolean correct2 = false;
	private boolean correct3 = false;
	private boolean correct4 = false;
	private String msg1 = "";
	private String msg2 = "";
	private String msg3 = "";
	private String msg4 = "";
	private List<Integer> code = new ArrayList<Integer>();
	private Font clockFont;
	private boolean incorrect1 = false;
	private boolean incorrect2 = false;
	private boolean incorrect3 = false;
	private boolean incorrect4 = false;

	public VaultPuzzle(int x, int y, int width, int height) {
		try {
			clockFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Fonts/digital.ttf"));
			clockFont = clockFont.deriveFont(100F);
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
		g2d.setFont(clockFont);

		for (int i = 0; i < code.size(); i++) {
			g2d.drawString(String.valueOf(code.get(i)), 220 + 50 * i, 400);
		}

		g2d.setColor(new Color(150, 150, 150));
		g2d.fillRect(tile1.x - 200, tile1.y - 200, tile4.x + 80, 500);

		g2d.setColor(Color.BLACK);
		g2d.fillRect(tile1.x, tile1.y, tile1.width, tile1.height);
		g2d.fillRect(tile2.x, tile2.y, tile2.width, tile2.height);
		g2d.fillRect(tile3.x, tile3.y, tile3.width, tile3.height);
		g2d.fillRect(tile4.x, tile4.y, tile4.width, tile4.height);
		g2d.setColor(Color.WHITE);

		g2d.drawString(msg1, tile1.x + 16, tile1.y + tile1.height - 48);
		g2d.drawString(msg2, tile2.x + 16, tile2.y + tile2.height - 48);
		g2d.drawString(msg3, tile3.x + 16, tile3.y + tile3.height - 48);
		g2d.drawString(msg4, tile4.x + 16, tile4.y + tile4.height - 48);
		g2d.setColor(Color.BLACK);
		g2d.setFont(CashOut.getFontBig());

		if (correct1) {
			g2d.setColor(Color.GREEN);
		} else if (incorrect1) {
			g2d.setColor(Color.RED);
		} else {
			g2d.setColor(Color.WHITE);
		}
		g2d.fillRect(tile1.x + 14, tile1.y - 80, 50, 50);
		if (correct2) {
			g2d.setColor(Color.GREEN);
		} else if (incorrect2) {
			g2d.setColor(Color.RED);
		} else {
			g2d.setColor(Color.WHITE);
		}
		g2d.fillRect(tile2.x + 14, tile2.y - 80, 50, 50);
		if (correct3) {
			g2d.setColor(Color.GREEN);
		} else if (incorrect3) {
			g2d.setColor(Color.RED);
		} else {
			g2d.setColor(Color.WHITE);
		}
		g2d.fillRect(tile3.x + 14, tile3.y - 80, 50, 50);
		if (correct4) {
			g2d.setColor(Color.GREEN);
		} else if (incorrect4) {
			g2d.setColor(Color.RED);
		} else {
			g2d.setColor(Color.WHITE);
		}
		g2d.fillRect(tile4.x + 14, tile4.y - 80, 50, 50);
		FontMetrics fontMetrics = g2d.getFontMetrics(CashOut.getFontMedium());
		g2d.setFont(CashOut.getFontMedium());
		int stringLength = fontMetrics.stringWidth("CLEAR");
		g2d.setColor(Color.red);
		g2d.fillRect(510, 550, stringLength + 25, 50);
		g2d.setColor(Color.white);
		g2d.drawString("CLEAR", 520, 585);
	}

	public void mouseClicked(MouseEvent e, Inventory i){
		if (e.getX() > 510 && e.getX() < 648 && e.getY() > 550 && e.getY() < 600){
			i.reset(false);
		}
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
		if (tile1.contains(r[0].x, r[0].y)) {
			if (num1 == code.get(0)) {
				correct1 = true;
				msg1 = String.valueOf(num1);
			}
			else {
				incorrect1 = true;
			}

		}
		else if (tile1.contains(r[1].x, r[1].y)) {
			if (num2 == code.get(0)) {
				correct1 = true;
				msg1 = String.valueOf(num2);
			}
			else {
				incorrect1 = true;
			}

		}
		else if (tile1.contains(r[2].x, r[2].y)) {
			if (num3 == code.get(0)) {
				correct1 = true;
				msg1 = String.valueOf(num3);
			}
			else {
				incorrect1 = true;
			}

		}
		else if (tile1.contains(r[3].x, r[3].y)) {
			if (num4 == code.get(0)) {
				correct1 = true;
				msg1 = String.valueOf(num4);
			}
			else {
				incorrect1 = true;
			}

		}
		else {
			correct1 = false;
			incorrect1 = false;
			msg1 = " ";
		}
	}

	public void check2(Rectangle[] r, int num1, int num2, int num3, int num4) {
		if (tile2.contains(r[0].x, r[0].y)) {
			if (num1 == code.get(1)) {
				correct2 = true;
				msg2 = String.valueOf(num1);
			}
			else {
				incorrect2 = true;
			}

		}
		else if (tile2.contains(r[1].x, r[1].y)) {
			if (num2 == code.get(1)) {
				correct2 = true;
				msg2 = String.valueOf(num2);
			}
			else {
				incorrect2 = true;
			}

		}
		else if (tile2.contains(r[2].x, r[2].y)) {
			if (num3 == code.get(1)) {
				correct2 = true;
				msg2 = String.valueOf(num3);
			}
			else {
				incorrect2 = true;
			}

		}
		else if (tile2.contains(r[3].x, r[3].y)) {
			if (num4 == code.get(1)) {
				correct2 = true;
				msg2 = String.valueOf(num4);
			}
			else {
				incorrect2 = true;
			}

		}
		else {
			correct2 = false;
			incorrect2 = false;
			msg2 = " ";
		}
	}

	public void check3(Rectangle[] r, int num1, int num2, int num3, int num4) {
		if (tile3.contains(r[0].x, r[0].y)) {
			if (num1 == code.get(2)) {
				correct3 = true;
				msg3 = String.valueOf(num1);
			}
			else {
				incorrect3 = true;
			}
		}
		else if (tile3.contains(r[1].x, r[1].y)) {
			if (num2 == code.get(2)) {
				correct3 = true;
				msg3 = String.valueOf(num2);
			}
			else {
				incorrect3 = true;
			}

		}
		else if (tile3.contains(r[2].x, r[2].y)) {
			if (num3 == code.get(2)) {
				correct3 = true;
				msg3 = String.valueOf(num3);
			}
			else {
				incorrect3 = true;
			}

		}
		else if (tile3.contains(r[3].x, r[3].y)) {
			if (num4 == code.get(2)) {
				correct3 = true;
				msg3 = String.valueOf(num4);
			}
			else {
				incorrect3 = true;
			}

		}
		else {
			correct3 = false;
			incorrect3 = false;
			msg3 = " ";
		}
	}

	public void check4(Rectangle[] r, int num1, int num2, int num3, int num4) {
		if (tile4.contains(r[0].x, r[0].y)) {
			if (num1 == code.get(3)) {
				correct4 = true;
				msg4 = String.valueOf(num1);
			}
			else {
				incorrect4 = true;
			}

		}
		else if (tile4.contains(r[1].x, r[1].y)) {
			if (num2 == code.get(3)) {
				correct4 = true;
				msg4 = String.valueOf(num2);
			}
			else {
				incorrect4 = true;
			}

		}
		else if (tile4.contains(r[2].x, r[2].y)) {
			if (num3 == code.get(3)) {
				correct4 = true;
				msg4 = String.valueOf(num3);
			}
			else {
				incorrect4 = true;
			}

		}
		else if (tile4.contains(r[3].x, r[3].y)) {
			if (num4 == code.get(3)) {
				correct4 = true;
				msg4 = String.valueOf(num4);
			}
			else {
				incorrect4 = true;
			}
		}
		else {
			correct4 = false;
			incorrect4 = false;
			msg4 = " ";
		}
	}

	public boolean getSolved(){
		return correct1 && correct2 && correct3 && correct4;
	}
}