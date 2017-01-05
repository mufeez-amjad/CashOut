import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
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
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class CashOut extends JPanel{
	private Note note = new Note();

	private Player player;
	private Inventory inventory = new Inventory();

	private Levels levels;
	
	private Level1 l;

	private Menu menu;

	private BufferedImage suspicion;
	private int suspicionLevel = 0;
	//private MazePuzzle m1 = new MazePuzzle(45);
	//private PicturePuzzle p1 = new PicturePuzzle(500, 500);
	private int score = 0;
	private static Font font;
	private static Font fontBig;
	private static Font fontMedium;
	private static Font fontSmall;
	private static Font fontTiny;
	private static Font fontHuge;
	private int fadeIn = 255;
	private Clip music;
	private boolean playing = false;
	private boolean paused = false;
	
	//private Level1 current;

	protected boolean isMenu = true;

	private boolean settingsExpanded;

	private boolean soundOn = true;

	private boolean musicOn = true;

	private static int frameHeight = 900;
	private static int frameWidth = 1200;

	public static int getFrameHeight(){
		return frameHeight;
	}

	public static int getFrameWidth(){
		return frameWidth;
	}

	public CashOut(){
		menu = new Menu(inventory, l, this);
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
				if (playing && !paused){
					int x = player.getPoint().x;
					int y = player.getPoint().y;
					double angle = player.getAngle();
					
					int x2 = player.getPoint2().x;
					int y2 = player.getPoint2().y;

					if(e.getKeyCode()==KeyEvent.VK_UP){
						x += 5 * Math.sin(Math.toRadians(angle));
						y += -5 * Math.cos(Math.toRadians(angle));
						
						x2 += 5 * Math.sin(Math.toRadians(angle));
						y2 += -5 * Math.cos(Math.toRadians(angle));
						
						if(!levels.getCurrent().hit(x, y)) player.move(0, -5);
					} 

					if(e.getKeyCode()==KeyEvent.VK_DOWN){
						x += -5 * Math.sin(Math.toRadians(angle));
						y += 5 * Math.cos(Math.toRadians(angle));
						
						x2 += 5 * Math.sin(Math.toRadians(angle));
						y2 += -5 * Math.cos(Math.toRadians(angle));
						
						if(!levels.getCurrent().hit(x2, y2)) player.move(0, 5);
					} 

					if(e.getKeyCode()==KeyEvent.VK_LEFT){
						player.turn(-5);
					} 

					if(e.getKeyCode()==KeyEvent.VK_RIGHT){
						player.turn(5);
					} 

					if (e.getKeyCode() == KeyEvent.VK_Z) 
					{
						//Creates bullets when the spacebar is pressed, as long as the user is not exceeding 3 at a time
						if(player.amountOfBullets() >= 0 && player.amountOfBullets() < 9)
						{
							player.setNB(player.getGunX(), player.getGunY(), player.getAngle());
						}
						else if (soundOn) player.playDryFire();
					}

					if (e.getKeyCode() == KeyEvent.VK_X) 
					{
						player.printAngle();
					}
				}

				if (e.getKeyCode() == KeyEvent.VK_P) 
				{
					paused = !paused;
					levels.getCurrent().getTimer().stopStart();
				}
			}

		});
		CashOut c = this;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//System.out.println("x " + e.getX() + " y " + e.getY());
				if (e.getX() > 670 && e.getX() < 745 && e.getY() > 700 && e.getY() < 775){
					inventory.expandNotes();

				}
				//if (!p1.complete()) p1.clicked(e);
				if (isMenu) menu.mouseClicked(e, c);

				if (paused){
					if (e.getX() > 21 && e.getX() < 21 + menu.getSettingsMenu().getWidth() && e.getY() > 800 && e.getY() < 800 + menu.getSettingsMenu().getHeight()){
						settingsExpanded = !settingsExpanded;
					}

					if (e.getX() > 30 && e.getX() < 30 + menu.getMusicOn().getWidth() && e.getY() > 760 && e.getY() < 760 + menu.getMusicOn().getHeight()){
						musicOn = !musicOn;
						setMusic(musicOn);
					}

					if (e.getX() > 30 && e.getX() < 30 + menu.getSoundOn().getWidth() && e.getY() > 715 && e.getY() < 715 + menu.getSoundOn().getHeight()){
						soundOn = !soundOn;

					}
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
				if (levels.mazePuzzle()) levels.getCurrent().mouseMoved(e);
			}
			public void mouseDragged(MouseEvent e){ }
		});

		playMusic();
		player = new Player(this);
		levels = new Levels(c, player);
		menu.setLevels(levels);
	}

	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Color front = new Color(88, 89, 91, 127);
		Color back = new Color(88, 89, 91, 75);

		levels.paint(g2d);

		player.paint(g2d);		
		inventory.paint(g2d);

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

		if (!playing) menu.paint(g2d);
		if (playing){
			Color fade = new Color(0, 0, 0, fadeIn);
			if (fadeIn > 0) fadeIn -= 3;
			g2d.setColor(fade);
			g2d.fillRect(0, 0, CashOut.getFrameWidth(), CashOut.getFrameHeight());
		}

		if (paused){
			g2d.setFont(fontHuge);
			g2d.setColor(Color.decode("0x065C27"));
			FontMetrics fontMetrics = g2d.getFontMetrics(fontHuge);
			int stringlength = fontMetrics.stringWidth("PAUSED");
			g2d.drawString("PAUSED", frameWidth/2 - stringlength/2, 400);
			//add settings menu
			g2d.setColor(Color.decode("0x89C280"));
			if (settingsExpanded) g2d.fillRoundRect(20, 700, menu.getSettingsMenu().getWidth(), 150, 25, 25);
			g2d.drawImage(menu.getSettingsMenu(), 20, 800, null);
			if (settingsExpanded){

				if (soundOn) g2d.drawImage(menu.getSoundOn(), 30, 715, null);
				else g2d.drawImage(menu.getSoundOff(), 30, 715, null);
				if (musicOn) g2d.drawImage(menu.getMusicOn(), 33, 760, null);
				else g2d.drawImage(menu.getMusicOff(), 33, 760, null);
			} 
		}
	}

	public void update(){
		if (!playing){
			if (menu.isMenuExit()) playing = true;
		}
		else if (!paused){
			if (!levels.getCurrent().getTimer().isRunning()) levels.getCurrent().getTimer().stopStart();
			player.update();
			//m1.update();

			levels.update(player, this);

			inventory.update(player);
		}
	}

	public void addScore(int n){
		score += n;
	}

	public void addSuspicion(int n){
		if (suspicionLevel < 2000) suspicionLevel += n;
		else System.out.println("GAME OVER");
	}

	public void setDifficulty(int n){
		//if (n == 1) Easy = true;
		//else if (n == 2) Medium = true;
		//else Hard = true;
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

	public synchronized void playMusic() {
		new Thread(new Runnable() { //creates a new thread
			public void run() {
				try { //contains code that might throw an exception
					music = AudioSystem.getClip(); 
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(
							this.getClass().getResource("/Audio/check_your_oil_levels.wav"));
					music.open(inputStream); //imports the audio file
					music.loop(Clip.LOOP_CONTINUOUSLY); //loops it forever
				} catch (Exception e) { //handles exception if the file is not found
					e.printStackTrace(); //prints the exception
				}
			}
		}).start();
	}

	public void stopMusic(){
		music.stop();
	}

	public void startMusic(){
		music.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void stopStartMusic(){
		if (!musicOn){
			stopMusic();
		}

		if (musicOn){
			startMusic();
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

	public Boolean getSoundState(){
		return soundOn;
	}

	public void setMusic(boolean state) {
		this.musicOn = state;
		stopStartMusic();
	}

	public void setSound(boolean state) {
		this.soundOn = state;
	}

}
