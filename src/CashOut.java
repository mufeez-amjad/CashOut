import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

public class CashOut extends JPanel{
	private Note note = new Note();
	private Note[] notes = new Note[4];
	private MoneyBag[] money = new MoneyBag[4];
	private Player player = new Player();
	private Inventory inventory = new Inventory();
	private static int score = 0;
	public static Font font;
	public static Font fontBig;
	public static Font fontMedium;
	public static Font fontSmall;

	private static int frameHeight = 900;
	private static int frameWidth = 1200;
	
	public static int getFrameHeight(){
		return frameHeight;
	}
	
	public static int getFrameWidth(){
		return frameWidth;
	}

	public CashOut(){
		
		try { 
			font = Font.createFont(Font.TRUETYPE_FONT, new File("src/CashCurrency.ttf")); 
			fontBig = font.deriveFont(Font.PLAIN, 90);
			fontMedium = font.deriveFont(Font.PLAIN, 25);
			fontSmall = font.deriveFont(Font.PLAIN, 18);
		} catch (IOException|FontFormatException e) { 
			System.out.println("CashCurrency.ttf could not be found");
		}
		
		for (int i = 0; i < notes.length; i++){
			notes[i] = new Note();
		}
		
		for (int i = 0; i < money.length; i++){
			money[i] = new MoneyBag();
		}
		
		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_UP){
					player.move(0, -5);
				} 

				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					player.move(0, 5);
				} 

				if(e.getKeyCode()==KeyEvent.VK_LEFT){
					player.turn(-5);
				} 

				if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					player.turn(5);
				} 
			}
		});
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getX() > 620 && e.getX() < 695 && e.getY() > 700 && e.getY() < 775){
					inventory.expandNotes();

				}
			}

			@Override
			public void mouseEntered(MouseEvent e) { }

			@Override
			public void mouseExited(MouseEvent e) { }

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			
		});
		
		addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				//System.out.println(e.getX());
			}
			public void mouseDragged(MouseEvent e){ }
		});
	}
	
	public void paint(Graphics g){
		Color front = new Color(88, 89, 91, 127);
		Color back = new Color(88, 89, 91, 75);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				 RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, frameWidth, frameHeight);
		for (int i = 0; i < notes.length; i++){
			notes[i].paint(g2d);
		}
		
		for (int i = 0; i < money.length; i++){
			money[i].paint(g2d);
		}
		
		player.paint(g2d);
		inventory.paint(g2d);
		g2d.setColor(front);
		g2d.fillRect(75, frameHeight - 160, 10 * (MoneyBag.getTotalValue()/10/2), 50);
		g2d.setColor(Color.decode("0x065C27"));
		g2d.fillRect(75, frameHeight - 160, score/2, 50);
		g2d.setColor(Color.white);
		g2d.setFont(fontMedium);
		g2d.drawString(String.valueOf(score), 90, frameHeight - 123);
		
	}
	
	public void update(){
		player.update();
		
		for (int i = 0; i < notes.length; i++){
			notes[i].collect(player);
		}
		
		for (int i = 0; i < money.length; i++){
			money[i].collect(player);
		}
		
	}
	
	public static void addScore(int n){
		score += n;
	}
	
	public static void main(String[] args) throws InterruptedException {
		JFrame frame = new  JFrame();
		CashOut panel = new CashOut();
		frame.add(panel); 
		panel.setFocusable(true);
		panel.requestFocusInWindow();
		frame.setSize(panel.getFrameWidth(), panel.getFrameHeight());
		frame.setVisible(true);
		frame.setTitle("Cash Out");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
		frame.setResizable(false); 
		frame.setLocationRelativeTo(null); //centers the window

		while (true){
			panel.update();
			panel.repaint();
			Thread.sleep(7);
		}
	}

}
