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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//import java.util.Timer;
//import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.*;

public class CashOut extends JPanel{
	private Note note = new Note();
	
	private Player player = new Player();
	private Inventory inventory = new Inventory();
	
	private Timer timer;
	private Level first = new Level();

	private Menu menu = new Menu(inventory, first);
	private BufferedImage suspicion;
	private int suspicionLevel = 0;
	//private MazePuzzle m1 = new MazePuzzle(45);
	//private PicturePuzzle p1 = new PicturePuzzle(500, 500);
	private static int score = 0;
	private static Font font;
	private static Font fontBig;
	private static Font fontMedium;
	private static Font fontSmall;
	private static Font fontTiny;
	private static Font fontHuge;

	protected boolean isMenu = true;

	

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
			font = Font.createFont(Font.TRUETYPE_FONT, new File("src/Fonts/CashCurrency.ttf")); 
			fontHuge = font.deriveFont(Font.PLAIN, 50);
			fontBig = font.deriveFont(Font.PLAIN, 35);
			fontMedium = font.deriveFont(Font.PLAIN, 25);
			fontSmall = font.deriveFont(Font.PLAIN, 12);
			fontTiny = font.deriveFont(Font.PLAIN, 9);
		} catch (IOException|FontFormatException e) { 
			System.out.println("CashCurrency.ttf could not be found");
		}
		
		try { 
			suspicion = ImageIO.read(getClass().getResource("/Images/Suspicion.png"));
		} catch (IOException e) { 
			System.err.println("Suspicion.png could not be found");
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
				
				if (e.getKeyCode() == KeyEvent.VK_SPACE) 
			      {
			        //Creates bullets when the spacebar is pressed, as long as the user is not exceeding 3 at a time
			        if(player.amountOfBullets() >= 0 && player.amountOfBullets() < 9)
			        {
			        player.setNB(player.getGunX(), player.getGunY(), player.getAngle());
			        }
			      }
			}
			
		});
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getX() > 670 && e.getX() < 745 && e.getY() > 700 && e.getY() < 775){
					inventory.expandNotes();

				}
				//if (!p1.complete()) p1.clicked(e);
				if (isMenu) menu.mouseClicked(e);
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
				//m1.mouseMoved(e);
				
			}
			public void mouseDragged(MouseEvent e){ }
		});
		
		timer = new Timer(1150, 40, 25, 50, "WHITE");

	}
	
	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				 RenderingHints.VALUE_ANTIALIAS_ON);
		Color front = new Color(88, 89, 91, 127);
		Color back = new Color(88, 89, 91, 75);
		
		//g2d.setColor(Color.white); //remove when tilemap is added
		//g2d.fillRect(0, 0, frameWidth, frameHeight);
		first.paint(g2d);
		
		player.paint(g2d);		
		inventory.paint(g2d);
		//p1.paint(g2d);
		
		g2d.setColor(front);
		g2d.fillRect(75, frameHeight - 160, 10 * (MoneyBag.getTotalValue()/10/2), 50); //score
		g2d.fillRect(75, frameHeight - 215, 200, 50); //suspicion
		
		Color color = Color.decode("0x065C27"); //score
		Color trans = new Color(color.getRed(), color.getGreen(), color.getBlue(), 200);
		g2d.setColor(trans);
		g2d.fillRect(75, frameHeight - 160, score/2, 50); //score
		g2d.setFont(fontBig);
		g2d.drawString("$", 30, frameHeight - 115);
		g2d.setColor(Color.white);
		g2d.setFont(fontMedium);
		g2d.drawString(String.valueOf(score), 90, frameHeight - 123);
		
		g2d.drawImage(suspicion, 15, frameHeight - 215, null); //suspicion
		g2d.setColor(Color.YELLOW);
		g2d.fillRect(75, frameHeight - 215, suspicionLevel/10, 50);	
		timer.paint(g2d);
		
		//if (!m1.isFinished()) m1.paint(g2d);
		menu.paint(g2d);

	}
	
	public void update(){
		player.update();
		//m1.update();
		
		first.update(player, this);
		
		inventory.update(player);
	}
	
	public static void addScore(int n){
		score += n;
	}
	
	public void addSuspicion(int n){
		if (suspicionLevel < 2000) suspicionLevel += n;
		else System.out.println("GAME OVER");
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
			//panel.update();
			panel.repaint();
			Thread.sleep(7);
		}
	}
	
	public static Font getFontBig(){
		return fontBig;
	}
	
	public static Font getFontMedium(){
		return fontMedium;
	}
	
	public static Font getFontSmall(){
		return fontSmall;
	}
	
	public static Font getFontTiny(){
		return fontTiny;
	}

}
