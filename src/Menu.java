import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Menu {

	private BufferedImage GALogo;
	private BufferedImage bg;
	private BufferedImage gameLogo;
	private BufferedImage plane;
	private BufferedImage controls;
	private BufferedImage divider;
	private BufferedImage buttons;
	private BufferedImage back;
	private BufferedImage settings;
	private BufferedImage settingsMenu;
	private BufferedImage music;
	private BufferedImage musicOff;
	private BufferedImage soundfx;
	private BufferedImage soundfxOff;
	private BufferedImage[] exhaust = new BufferedImage[3];
	private BufferedImage exhaustSprites;
	private BufferedImage car1;
	private BufferedImage car2;
	private BufferedImage taxi;
	private int car1x = -300;
	private int car2x = CashOut.getFrameWidth() + 300;
	private Inventory inv;

	private double cloudCounter = 0;
	private int smokeCounter = 0;
	private int menuCounter = 0;

	private boolean settingsExpanded = false;
	private BufferedImage smoke;
	private BufferedImage currentImg;
	private int frame;
	private boolean information = false;
	private boolean play = false;
	private boolean scores = false;
	private boolean main = true;
	private boolean reversed;
	private boolean musicOn = true;
	private boolean soundOn = true;
	
	private Level level;

	private int planeX = 1500;



	public Menu(Inventory inventory, Level l){
		inv = inventory;
		level = l;
		try { 
			GALogo = ImageIO.read(getClass().getResource("/Images/GAStudios.png"));
		} catch (IOException e) { 
			System.err.println("GAStudios.png could not be found");
		}

		try { 
			bg = ImageIO.read(getClass().getResource("/Images/bg.png"));
		} catch (IOException e) { 
			System.err.println("bg.png could not be found");
		}

		try { 
			gameLogo = ImageIO.read(getClass().getResource("/Images/logo.png"));
		} catch (IOException e) { 
			System.err.println("logo.png could not be found");
		}
		
		try { 
			plane = ImageIO.read(getClass().getResource("/Images/plane.png"));
		} catch (IOException e) { 
			System.err.println("plane.png could not be found");
		}
		
		try { 
			controls = ImageIO.read(getClass().getResource("/Images/Controls.png"));
		} catch (IOException e) { 
			System.err.println("Controls.png could not be found");
		}
		

		try { 
			buttons = ImageIO.read(getClass().getResource("/Images/buttons.png"));
		} catch (IOException e) { 
			System.err.println("buttons.png could not be found");
		}

		try { 
			back = ImageIO.read(getClass().getResource("/Images/back.png"));
		} catch (IOException e) { 
			System.err.println("back.png could not be found");
		}


		try { 
			settings = ImageIO.read(getClass().getResource("/Images/settings.png"));
		} catch (IOException e) { 
			System.err.println("settings.png could not be found");
		}


		try { 
			music = ImageIO.read(getClass().getResource("/Images/music.png"));
		} catch (IOException e) { 
			System.err.println("music.png could not be found");
		}

		try { 
			soundfx = ImageIO.read(getClass().getResource("/Images/soundfx.png"));
		} catch (IOException e) { 
			System.err.println("soundfx.png could not be found");
		}

		try { 
			musicOff = ImageIO.read(getClass().getResource("/Images/musicOff.png"));
		} catch (IOException e) { 
			System.err.println("musicOff.png could not be found");
		}

		try { 
			soundfxOff = ImageIO.read(getClass().getResource("/Images/soundfxOff.png"));
		} catch (IOException e) { 
			System.err.println("soundfxOff.png could not be found");
		}

		try { 
			car1 = ImageIO.read(getClass().getResource("/Images/car1.png"));
		} catch (IOException e) { 
			System.err.println("car1.png could not be found");
		}
		try { 
			car2 = ImageIO.read(getClass().getResource("/Images/car2.png"));
		} catch (IOException e) { 
			System.err.println("car2.png could not be found");
		}

		try { 
			taxi = ImageIO.read(getClass().getResource("/Images/taxi.png"));
		} catch (IOException e) { 
			System.err.println("taxi.png could not be found");
		}

		try { 
			smoke = ImageIO.read(getClass().getResource("/Images/smoke.png"));
		} catch (IOException e) { 
			System.err.println("smoke.png could not be found");
		}

		for (int i = 0; i < exhaust.length; i++){
			exhaust[i] = grabImage(1, i+1, 71);
		}
		currentImg = exhaust[0];
	}

	public void paint(Graphics2D g2d){
		g2d.drawImage(bg, 0, 0, null);

		g2d.setColor(Color.decode("0xE2F5FF"));
		g2d.fillOval((int) (50-1200 + cloudCounter), 30, 50, 30);
		g2d.fillOval((int) (50-1200 + cloudCounter), 45, 40, 30);
		g2d.fillOval((int) (30-1200 + cloudCounter), 35, 40, 30);
		g2d.fillOval((int) (75-1200 + cloudCounter), 40, 40, 30);

		g2d.fillOval((int) (350-1200 + cloudCounter), 20, 100, 60);
		g2d.fillOval((int) (350-1200 + cloudCounter), 55, 80, 60);
		g2d.fillOval((int) (300-1200 + cloudCounter), 45, 80, 60);
		g2d.fillOval((int) (400-1200 + cloudCounter), 50, 80, 60);

		g2d.fillOval((int) (500-1200 + cloudCounter), 30, 50, 30);
		g2d.fillOval((int) (500-1200 + cloudCounter), 45, 40, 30);
		g2d.fillOval((int) (480-1200 + cloudCounter), 35, 40, 30);
		g2d.fillOval((int) (525-1200 + cloudCounter), 40, 40, 30);

		g2d.fillOval((int) (800-1200 + cloudCounter), 30, 50, 30);
		g2d.fillOval((int) (800-1200 + cloudCounter), 45, 40, 30);
		g2d.fillOval((int) (780-1200 + cloudCounter), 35, 40, 30);
		g2d.fillOval((int) (825-1200 + cloudCounter), 40, 40, 30);

		g2d.fillOval((int) (925-1200 + cloudCounter), 20, 100, 60);
		g2d.fillOval((int) (925-1200 + cloudCounter), 55, 80, 60);
		g2d.fillOval((int) (875-1200 + cloudCounter), 45, 80, 60);
		g2d.fillOval((int) (975-1200 + cloudCounter), 50, 80, 60);
		
		g2d.drawImage(plane, planeX, 30, null);

		g2d.drawImage(car1, car1x, 710, null);
		g2d.drawImage(currentImg, car1x - 100, 735, null);

		g2d.drawImage(car2, car2x, 570, null);
		g2d.drawImage(currentImg, car2x + 300 + currentImg.getWidth(), 570 + 25, -currentImg.getWidth(), currentImg.getHeight(), null);
		
		

		if (main){
			int x = (CashOut.getFrameWidth() - buttons.getWidth()) / 2; //gets the dimensions of the buttons
			int y = (CashOut.getFrameHeight() - buttons.getHeight(null)) / 2 + 275;
			g2d.drawImage(buttons, x, y + menuCounter, null);
			x = (CashOut.getFrameWidth() - gameLogo.getWidth()) / 2; //gets the dimensions of the gameLogo
			y = (CashOut.getFrameHeight() - gameLogo.getHeight(null)) / 2 - 200;
			g2d.drawImage(gameLogo, x, y - menuCounter, null);
			x = (CashOut.getFrameWidth() - GALogo.getWidth()) / 2; //gets the dimensions of the gameLogo
			y = (CashOut.getFrameHeight() - GALogo.getHeight(null)) / 2 - 400;
			//g2d.drawImage(GALogo, x, y - menuCounter, null);
			g2d.setColor(Color.decode("0x89C280"));
			if (settingsExpanded) g2d.fillRoundRect(20, 700 + menuCounter, settings.getWidth(), 150, 25, 25);
			g2d.drawImage(settings, 20, 800 + menuCounter, null);
			if (settingsExpanded){ 
				if (soundOn) g2d.drawImage(soundfx, 30, 715 + menuCounter, null);
				else g2d.drawImage(soundfxOff, 30, 715 + menuCounter, null);
				if (musicOn) g2d.drawImage(music, 33, 760 + menuCounter, null);
				else g2d.drawImage(musicOff, 33, 760 + menuCounter, null);
			}
		}

		if (!main){
			Color grey = new Color(88, 89, 91, 220);
			if (information){
				g2d.setColor(grey);
				g2d.fillRoundRect(50, 50, 1100, 800, 25, 25);
				g2d.setFont(CashOut.getFontMedium());
				//g2d.setColor(Color.decode("0x89C280"));
				g2d.setColor(Color.WHITE);
				FontMetrics fontMetrics = g2d.getFontMetrics(CashOut.getFontMedium()); //used to calculate based on the measurements of the font
				String s1 = "A notorious bank robber is on yet another hunt for cash.";
				String s2 = "Help him retrieve the goods and make his grand escape !";
				String s3 = "Be prepared to face the full extent of the law";
				String s4 = "security cameras, police officers, and laser wires";
				String s5 = "Will this be his last heist?";
				String s6 = "It is up to you to make sure it isn’t.";

				int stringLength = fontMetrics.stringWidth(s1); //horizontal length of String
				int stringHeight = fontMetrics.stringWidth(s1);
				g2d.drawString(s1, (CashOut.getFrameWidth()/2) - (stringLength/2)  , 125); 
				g2d.drawString(s2, (CashOut.getFrameWidth()/2) - (stringLength/2)  , 175);
				g2d.drawString(s3, (CashOut.getFrameWidth()/2) - (stringLength/2)  , 225);
				g2d.setColor(Color.decode("0xd1d3d4"));
				g2d.fillOval(895, 210, 6, 6);
				g2d.fillOval(895, 220, 6, 6);
				int stringLength2 = fontMetrics.stringWidth(s4); //horizontal length of String
				g2d.setColor(Color.decode("0x990000"));
				g2d.drawString(s4, (CashOut.getFrameWidth()/2) - (stringLength2/2) , 275);
				g2d.setFont(CashOut.getFontBig());
				FontMetrics fontMetrics2 = g2d.getFontMetrics(CashOut.getFontBig());
				stringLength2 = fontMetrics2.stringWidth(s5); //horizontal length of String
				g2d.setColor(Color.white);

				g2d.drawString(s5, (CashOut.getFrameWidth()/2) - (stringLength2/2) , 340);
				stringLength2 = fontMetrics.stringWidth(s6);
				g2d.setFont(CashOut.getFontMedium());
				g2d.drawString(s6, (CashOut.getFrameWidth()/2) - (stringLength2/2), 390);
				g2d.fillRect((CashOut.getFrameWidth()/2) - (960/2), 420, 960, 3);
				g2d.drawImage(controls, (CashOut.getFrameWidth()/2) - (controls.getWidth()/2), 435, null);
				int x = 200;
				int y = 650;
				g2d.drawImage(inv.getNoteImg(), x, y, null);
				g2d.drawImage(level.getBagImage(), x + 70, y + 10, null);
			
				g2d.setFont(CashOut.getFontSmall());
				g2d.drawString("Collect", x + 30, y + 75);
							
				g2d.setColor(Color.gray);
				Image camera = level.getCameraImage().getScaledInstance(30, 26, Image.SCALE_DEFAULT);
				g2d.drawImage(camera, x + 250, y + 20, null);
				g2d.fillOval(x + 250 + 25, y + 20 - 5, 10, 10);
				g2d.setColor(Color.white);
				Image laser = level.getLaserImage().getScaledInstance(30, 7, Image.SCALE_DEFAULT);
				g2d.drawImage(laser, x + 300, y + 30, null);
				
				g2d.drawString("Hack", x + 300, y + 75);
				g2d.fillOval(x + 350, y + 20, 20, 20);
				
				//g2d.


			}
			g2d.drawImage(back, 10, 700, null);
		}

		update();
	}

	public void update(){
		if (cloudCounter > 1200*2) cloudCounter = 0;
		else cloudCounter += 0.5;
		/*if (settingsExpanded && settingsCounter < 150){
			settingsCounter++;
		}
		 */

		if (play || information || scores){
			if (reversed){
				if (menuCounter > 0) menuCounter -= 5;
			}
			
			else {
				if (menuCounter < 500) menuCounter+=5; 
				else{
					main = false;
					menuCounter =  0;
				}
			}
			
		}

		if (car1x < 1600) car1x+=5;
		else car1x = -300;

		if (car2x > -1000) car2x-=8;
		else car2x = CashOut.getFrameWidth() + 300;

		if (smokeCounter < 60){
			smokeCounter++;
		}
		else smokeCounter = 0;

		if (smokeCounter < 20) frame = 0;
		else if (smokeCounter < 40) frame = 1;
		else if (smokeCounter < 60) frame = 2;
		currentImg = exhaust[frame];
		
		if (planeX < -300) planeX = 5000;
				
		planeX -= 1;
		
	}

	public void mouseClicked(MouseEvent e) {
		//System.out.println(e.getX() + ", " + e.getY());
		if (e.getX() > 21 && e.getX() < 21 + settings.getWidth() && e.getY() > 800 && e.getY() < 800 + settings.getHeight()){
			settingsExpanded = !settingsExpanded;
		}
		
		if (e.getX() > 30 && e.getX() < 30 + music.getWidth() && e.getY() > 760 && e.getY() < 760 + music.getHeight()){
			musicOn = !musicOn;
		}
		
		if (e.getX() > 30 && e.getX() < 30 + soundfx.getWidth() && e.getY() > 715 && e.getY() < 715 + soundfx.getHeight()){
			soundOn = !soundOn;
		}

		if (e.getX() > 217 && e.getX() < 217 + buttons.getWidth() && e.getY() > 645 && e.getY() < 647 + buttons.getHeight()){ //all buttons
			if (e.getX() > 217 && e.getX() < 217 + 124){
				information = true;
			}
			if (e.getX() > 538 && e.getX() < 538 + 124){
				play = true;
			}
			if (e.getX() > 858 && e.getX() < 858 + 124){
				scores = true;
			}
		}

		if (e.getX() > 10 && e.getX() < 10 + back.getWidth() && e.getY() > 700 && e.getY() < 700 + back.getHeight()){
			if (information){
				information = false;
				main = true;
				reversed = true;
			}
		}
	}

	public BufferedImage grabImage(int col, int row, int length){
		BufferedImage img = smoke.getSubimage(((col-1) * length), ((row-1) * length), 112, length);
		return img;
	}
}
