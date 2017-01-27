import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class CashOut extends JPanel{
	
	private Player player;
	private Levels levels;
	private Inventory inventory;
	private Menu menu;
	private BufferedImage suspicion;
	private static BufferedImage cursor;
	private double suspicionLevel = 0;

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

	private boolean isMenu = true;

	private boolean settingsExpanded;

	private boolean soundOn = true;

	private boolean musicOn = true;

	private boolean gameOver = false;

	private boolean upPressed;

	private boolean downPressed;

	private boolean leftPressed;

	private boolean rightPressed;

	private boolean win = false;

	private BufferedImage menuButton;


	private static int frameHeight = 900;
	private static int frameWidth = 1200;

	public static int getFrameHeight(){
		return frameHeight;
	}

	public static int getFrameWidth(){
		return frameWidth;
	}

	public CashOut(){
		inventory = new Inventory();
		menu = new Menu(inventory, this);

		try { 
			font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("Fonts/CashCurrency.ttf")); 
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
		
		try { 
			cursor = ImageIO.read(getClass().getResource("/Images/finger.png"));
		} catch (IOException e) { 
			System.err.println("Suspicion.png could not be found");
		}

		try { 
			menuButton = ImageIO.read(getClass().getResource("/Images/menu.png"));
		} catch (IOException e) { 
			System.err.println("menu.png could not be found");
		}

		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_UP){

					upPressed = false;
				} 

				if(e.getKeyCode()==KeyEvent.VK_DOWN){

					downPressed = false;

				} 

				if(e.getKeyCode()==KeyEvent.VK_LEFT){
					leftPressed = false;
				} 

				if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					rightPressed = false;
				} 
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (playing && !paused){

					if (!levels.getCurrent().getHacking()){

						if(e.getKeyCode()==KeyEvent.VK_UP){

							upPressed = true;
						} 

						if(e.getKeyCode()==KeyEvent.VK_DOWN){

							downPressed = true;

						} 

						if(e.getKeyCode()==KeyEvent.VK_LEFT){
							leftPressed = true;
						} 

						if(e.getKeyCode()==KeyEvent.VK_RIGHT){
							rightPressed = true;
						} 

						if (e.getKeyCode() == KeyEvent.VK_Z) 
						{
							//Creates bullets when the spacebar is pressed, as long as the user is not exceeding 9 at a time
							if(player.amountOfBullets() >= 0 && player.amountOfBullets() < 9)
							{
								player.setNB(player.getGunX(), player.getGunY(), player.getAngle());
							}
							else if (soundOn) player.playDryFire();
						}
					}

					if (e.getKeyCode() == KeyEvent.VK_X) //stop hacking
					{
						levels.getCurrent().keyPressed(e, player);
					}
				}

				if (e.getKeyCode() == KeyEvent.VK_P) 
				{
					if (playing){
						paused = !paused;
						levels.getCurrent().getTimer().stopStart();
					}
				}
			}

		});
		CashOut c = this;
		player = new Player(this);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//System.out.println("x " + e.getX() + " y " + e.getY());
				if (playing && !paused){
					if (e.getX() > 670 && e.getX() < 745 && e.getY() > 700 && e.getY() < 775){
						inventory.expandNotes();
					}
					levels.getCurrent().mouseClicked(e, inventory);
				}
				if (isMenu) menu.mouseClicked(e, c);

				if (paused){
					if (e.getX() > 21 && e.getX() < 21 + menu.getSettingsMenu().getWidth() && e.getY() > 800 && e.getY() < 800 + menu.getSettingsMenu().getHeight()){
						settingsExpanded = !settingsExpanded;
					}

					if (e.getX() > 538 && e.getX() < 538 + menuButton.getWidth() && e.getY() > 500 && e.getY() < 500 + menuButton.getHeight()){
						playing = false;
						paused = false;
						menu.reset();
						levels.retry(c, player);
					}

					if (settingsExpanded){
						if (e.getX() > 30 && e.getX() < 30 + menu.getMusicOn().getWidth() && e.getY() > 760 && e.getY() < 760 + menu.getMusicOn().getHeight()){
							musicOn = !musicOn;
							setMusic(musicOn);
						}

						if (e.getX() > 30 && e.getX() < 30 + menu.getSoundOn().getWidth() && e.getY() > 715 && e.getY() < 715 + menu.getSoundOn().getHeight()){
							soundOn = !soundOn;
						}
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
				levels.getCurrent().mouseReleased(e);
			}


		});

		addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				if (levels.mazePuzzle()) levels.getCurrent().mouseMoved(e);

			}
			public void mouseDragged(MouseEvent e){
				if (playing){
					levels.getCurrent().sendInventory(inventory);
					inventory.isDragged(e);					
				}
			}
		});

		playMusic();
		
		levels = new Levels(c, player);
		menu.setLevels(levels);
	}

	public void move() {
		if(upPressed){
			player.move(0, -1, levels);
		} 

		if(downPressed){
			player.move(0, 1, levels);
		} 

		if(leftPressed){
			player.turn(-1);
		} 

		if(rightPressed){
			player.turn(1);
		} 

	}

	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Color front = new Color(88, 89, 91, 127);
		new Color(88, 89, 91, 75);

		g2d.drawImage(levels.getCurrent().getBG(), 0, 0, null);

		if (levels.getCurrent().isOfficer1Dead()) levels.getCurrent().paintOfficer1(g2d);
			
		if (levels.getCurrent().isOfficer2Dead()) levels.getCurrent().paintOfficer2(g2d);
				
		player.paint(g2d);


		levels.paint(g2d);

		inventory.paint(g2d);


		g2d.setColor(front);
		g2d.fillRect(75, frameHeight - 160, 10 * (levels.getCurrent().getTotalValue()/10/2), 50); //score
		g2d.fillRect(75, frameHeight - 215, 200, 50); //suspicion

		Color color = Color.decode("0x065C27"); //score
		Color trans = new Color(color.getRed(), color.getGreen(), color.getBlue(), 200);
		g2d.setColor(trans);
		g2d.fillRect(75, frameHeight - 160, levels.getCurrent().getScore()/2, 50); //score
		g2d.setFont(fontHuge);
		g2d.drawString("$", 30, frameHeight - 115);
		g2d.setColor(Color.white);
		g2d.setFont(fontMedium);
		g2d.drawString(String.valueOf(levels.getCurrent().getScore()), 90, frameHeight - 123);

		g2d.drawImage(suspicion, 15, frameHeight - 215, null); //suspicion
		Color yellowTrans = new Color(255, 255, 0, 200);
		g2d.setColor(yellowTrans);
		g2d.fillRect(75, frameHeight - 215, (int) suspicionLevel/10, 50);	

		if (!playing){
			menu.paint(g2d);
		}

		if (playing){
			Color fade = new Color(0, 0, 0, fadeIn);
			if (fadeIn > 0) fadeIn -= 3;
			g2d.setColor(fade);
			g2d.fillRect(0, 0, CashOut.getFrameWidth(), CashOut.getFrameHeight());
		}

		if (paused){
			g2d.setColor(front);
			g2d.fillRect(0, 0, frameWidth, frameHeight);
			g2d.setFont(font.deriveFont(Font.PLAIN, 70));
			g2d.setColor(Color.decode("0x065C27"));
			FontMetrics fontMetrics = g2d.getFontMetrics(font.deriveFont(Font.PLAIN, 70));
			int stringlength = fontMetrics.stringWidth("PAUSED");
			g2d.drawString("PAUSED", frameWidth/2 - stringlength/2, 400);
			g2d.setColor(Color.decode("0x89C280"));
			if (settingsExpanded) g2d.fillRoundRect(20, 700, menu.getSettingsMenu().getWidth(), 150, 25, 25);
			g2d.drawImage(menu.getSettingsMenu(), 20, 800, null);
			if (settingsExpanded){
				if (soundOn) g2d.drawImage(menu.getSoundOn(), 30, 715, null);
				else g2d.drawImage(menu.getSoundOff(), 30, 715, null);
				if (musicOn) g2d.drawImage(menu.getMusicOn(), 33, 760, null);
				else g2d.drawImage(menu.getMusicOff(), 33, 760, null);
			}
			g2d.drawImage(menuButton, frameWidth/2 - menuButton.getWidth()/2, 500, null);
		}
	}

	public void update(){
		if (!playing){
			menu.update(this);
			if (menu.isMenuExit() && !gameOver && !win) playing = true;
		}
		else if (!paused){
			if (!levels.getCurrent().getTimer().isRunning()) levels.getCurrent().getTimer().stopStart();
			move();
			player.update();

			levels.update(player, this);
			inventory.update(player, levels);

			if (suspicionLevel > 0) suspicionLevel-= 0.50;
		}
		
		if (win || gameOver){
			menu.setMenuExit(false);
		}
	}


	public void addSuspicion(int n){
		if (suspicionLevel < 2000 - n) suspicionLevel += n;
		else{
			gameOver = true;
			playing = false;
			menu.reset();
			resetSuspicion();
		}
	}

	public static void main(String[] args) throws InterruptedException {

		JFrame frame = new  JFrame();
		CashOut panel = new CashOut();
		frame.add(panel); 
		panel.setFocusable(true);
		panel.requestFocusInWindow();
		frame.setSize(frameWidth, frameHeight);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src\\Images\\Icon.png")); //sets the frame icon to a picture of a bubble
		panel.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(cursor,new Point(panel.getX(), panel.getY()),"custom cursor")); //sets the cursor to a custom cursor
		
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

	public static Font getCashFont(){
		return font;
	}

	public static Font getFontHuge(){
		return fontHuge;
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

	public boolean getSoundState(){
		return soundOn;
	}

	public void setMusic(boolean state) {
		this.musicOn = state;
		stopStartMusic();
	}

	public void setSound(boolean state) {
		this.soundOn = state;
	}

	public boolean getGameOver(){
		return gameOver;
	}

	public void setGameOver(boolean b){
		gameOver = b;
		if (gameOver == true) playing = false;
	}

	public Levels getLevels(){
		return levels;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public Player getPlayer() {
		return player;
	}

	public void retry() {
		gameOver = false;
		playing = true;
		levels.retry(this, player);
		player.reset();
		win = false;
		resetSuspicion();
	}

	public void resetInventory(){
		inventory.reset(true);
	}

	public void setWin(boolean b){
		win = b;
		if (win == true) playing = false;
	}

	public boolean getWin(){
		return win;
	}

	public void resetSuspicion() {
		suspicionLevel = 0;
	}

	public boolean getPaused() {
		return paused;
	}


}