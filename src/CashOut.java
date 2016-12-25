import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

public class CashOut extends JPanel{
	private Note note = new Note();
	private Note[] notes = new Note[4];
	private Player player = new Player();
	private Inventory inventory = new Inventory();
	
	private static int frameHeight = 900;
	private static int frameWidth = 1200;
	
	public static int getFrameHeight(){
		return frameHeight;
	}
	
	public static int getFrameWidth(){
		return frameWidth;
	}

	
	private final Set<Character> pressed = new HashSet<Character>();
	
	public CashOut(){
		for (int i = 0; i < notes.length; i++){
			notes[i] = new Note();
		}
		
		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				player.stopMoving();
			}

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
	}
	
	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				 RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, frameWidth, frameHeight);
		for (int i = 0; i < notes.length; i++){
			notes[i].paint(g2d);
		}
		player.paint(g2d);
		inventory.paint(g2d);		
	}
	
	public void update(){
		player.update();
		
		for (int i = 0; i < notes.length; i++){
			notes[i].collect(player);
		}
		
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
		//frame.setResizable(false); 
		frame.setLocationRelativeTo(null); //centers the window

		while (true){
			panel.update();
			panel.repaint();
			Thread.sleep(7);
		}
	}

}
