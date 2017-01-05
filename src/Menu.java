import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

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
	private BufferedImage one;
	private BufferedImage two;
	private BufferedImage three;
	private BufferedImage levelBag;
	private BufferedImage levelBagGray;


	private int car1x = -300;
	private int car2x = CashOut.getFrameWidth() + 300;
	private Inventory inv;
	private Clip traffic;

	private double cloudCounter = 0;
	private int smokeCounter = 0;
	private int menuCounter = 0;

	private boolean settingsExpanded = false;
	private boolean levelSelected = false;
	private BufferedImage smoke;
	private BufferedImage currentImg;
	private int frame;
	private boolean information = false;
	private boolean play = true;
	private boolean scores = false;
	private boolean main = false;
	private boolean reversed;
	private static boolean musicOn = true;
	private static boolean soundOn = true;

	private Level1 level;

	private int planeX = 1500;
	private int taxiX = -300;
	private boolean car1Stopped = false;
	private boolean car2Stopped = false;
	private boolean playedScreech = false;
	private boolean taxiStopped = true;
	private int fadeOut;
	private CashOut game;
	private boolean taxiGone = false;
	private boolean menuExit = false;

	private Levels gameLevels;
	private Level[] levels;


	public boolean isMenuExit() {
		return menuExit;
	}

	public Menu(Inventory inventory, Level1 l, CashOut c){
		inv = inventory;
		level = l;
		game = c;
		try { 
			GALogo = ImageIO.read(getClass().getResource("/Images/GAStudios.png"));
		} catch (IOException e) { 
			System.err.println("GAStudios.png could not be found");
		}


		try { 
			levelBag = ImageIO.read(getClass().getResource("/Images/Level.png"));
		} catch (IOException e) { 
			System.err.println("GAStudios.png could not be found");
		}

		try { 
			levelBagGray = ImageIO.read(getClass().getResource("/Images/LevelGray.png"));
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
			one = ImageIO.read(getClass().getResource("/Images/One.png"));
		} catch (IOException e) { 
			System.err.println("One.png could not be found");
		}
		try { 
			two = ImageIO.read(getClass().getResource("/Images/Two.png"));
		} catch (IOException e) { 
			System.err.println("Two.png could not be found");
		}
		try { 
			three = ImageIO.read(getClass().getResource("/Images/Three.png"));
		} catch (IOException e) { 
			System.err.println("Three.png could not be found");
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
		playTraffic();
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
			Color greyTrans = new Color(150, 150, 150, 127);
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
				g2d.fillRect((CashOut.getFrameWidth()/2) - (960/2), 420, 940, 3);
				g2d.drawImage(controls, (CashOut.getFrameWidth()/2) - (controls.getWidth()/2), 465, null);
				int x = 275;
				int y = 700;
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

				g2d.drawString("Shoot", x + 570, y + 75);
				Image officer = level.getOfficerImage().getScaledInstance(75, 75, Image.SCALE_DEFAULT);
				g2d.drawImage(officer, x + 555, y - 10, null);
			}

			if (play){
				g2d.drawImage(taxi, taxiX, 570, null);
				g2d.drawImage(currentImg, taxiX - 100, 600, null);

				if (taxiStopped){
					g2d.setColor(grey);
					g2d.fillRoundRect(50, 50, 1100, 800, 25, 25);

					//old

					/*
					g2d.setFont(CashOut.getFontBig());
					g2d.setColor(Color.WHITE);
					FontMetrics fontMetrics = g2d.getFontMetrics(CashOut.getFontBig()); 
					int stringLength = fontMetrics.stringWidth("Pick a Difficulty");
					g2d.drawString("Pick a Difficulty", (CashOut.getFrameWidth()/2) - (stringLength/2), 125);
					g2d.setColor(greyTrans);
					g2d.fillRoundRect(85, 175, 1025, 200, 25, 25); 
					g2d.fillRoundRect(85, 400, 1025, 200, 25, 25);
					g2d.fillRoundRect(85, 625, 1025, 200, 25, 25);

					g2d.setFont(CashOut.getFontBig());
					g2d.setColor(Color.white);
					g2d.drawString("Easy", 100, 220);
					g2d.drawString("Medium", 100, 445);
					g2d.drawString("Hard", 100, 670);
					g2d.setFont(CashOut.getFontMedium());

					g2d.drawString("3 Levels", 150, 270);
					g2d.drawString("Money Bags and Notes", 150, 310);
					g2d.drawString("Vaults", 150, 350);
					g2d.drawImage(one, 85 + 1025 - one.getWidth() - 150, 210, null);

					g2d.drawString("4 Levels" , 150, 495);
					g2d.drawString("Lasers and Cameras", 150, 535);
					g2d.drawString("Timer", 150, 575);
					g2d.drawImage(two, 85 + 1025 - two.getWidth() - 150, 435, null);					

					g2d.drawString("5 Levels" , 150, 720);
					g2d.drawString("Officers", 150, 760);
					g2d.drawImage(three, 85 + 1025 - three.getWidth() - 150, 660, null);
					 */
					//new
					g2d.setFont(CashOut.getFontBig());
					g2d.setColor(Color.WHITE);
					FontMetrics fontMetrics = g2d.getFontMetrics(CashOut.getFontBig()); 
					int stringLength = fontMetrics.stringWidth("Level Select");
					g2d.drawString("Level Select", (CashOut.getFrameWidth()/2) - (stringLength/2), 125);
					g2d.setFont(CashOut.getFontMedium());
					for (int i = 0; i < levels.length; i++){
						g2d.setColor(greyTrans);
						g2d.fillRoundRect(-45 + ((i+1)*200), 280, levelBag.getWidth(), 60, 5, 5);
						if (!levels[i].isUnlocked()) g2d.drawImage(levelBagGray, -45 + ((i+1)*200), 175, null);
						else g2d.drawImage(levelBag, -45 + ((i+1)*200), 175, null);
						g2d.setColor(Color.WHITE);
						g2d.drawString(String.valueOf(i+1), -5 + ((i+1)*200) , 275);
					}
				}
			}

			if ((taxiStopped && !taxiGone) || information || scores) g2d.drawImage(back, 10, 700, null);

		}
		if (taxiGone){
			Color color = new Color(0, 0, 0, fadeOut);
			if (fadeOut < 255) fadeOut += 3;
			g2d.setColor(color);
			g2d.fillRect(0, 0, CashOut.getFrameWidth(), CashOut.getFrameHeight());
			if (fadeOut == 255){
				menuExit = true;
			}
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

		if (reversed){
			if (menuCounter >= 0) menuCounter -= 5;
			if (menuCounter == 0) reversed = false;
		}

		if (play || information || scores){
			if (menuCounter < 500) menuCounter+=5; 
			else{
				main = false;
				//menuCounter =  0;
			}
		}


		if (car1Stopped && car2Stopped){
			stopTraffic();
			if (!levelSelected){
				if (taxiX > 320 && taxiX < 450){
					if (!playedScreech && soundOn) playTires();
					taxiX+= 2;
				}
				else if (taxiX >= 450){
					taxiStopped  = true;
				}
				else taxiX += 5;
			}
			else {
				if (taxiX < 1200 + 300){
					taxiX += 5;
					taxiStopped = false;
				}
				else taxiGone = true;
			}
		}



		if (!car1Stopped){
			if (car1x < 1600) car1x+=5;
			else{
				car1x = -300;
				if (play) car1Stopped = true;
			}
		}
		if (!car2Stopped){
			if (car2x > -1000) car2x-=8;
			else{
				car2x = CashOut.getFrameWidth() + 300;
				if (play) car2Stopped = true;
			}
		}

		if (smokeCounter < 60){
			smokeCounter++;
		}
		else smokeCounter = 0;

		if (smokeCounter < 20) frame = 0;
		else if (smokeCounter < 40) frame = 1;
		else if (smokeCounter < 60) frame = 2;
		currentImg = exhaust[frame];

		if (planeX < -300) planeX = 2400;

		planeX -= 1;

	}

	public void mouseClicked(MouseEvent e, CashOut c) {
		//System.out.println(e.getX() + ", " + e.getY());
		if (main){
			if (e.getX() > 21 && e.getX() < 21 + settings.getWidth() && e.getY() > 800 && e.getY() < 800 + settings.getHeight()){
				settingsExpanded = !settingsExpanded;
			}

			if (e.getX() > 30 && e.getX() < 30 + music.getWidth() && e.getY() > 760 && e.getY() < 760 + music.getHeight()){
				musicOn = !musicOn;
				game.setMusic(musicOn);
				//game.stopStartMusic();
			}

			if (e.getX() > 30 && e.getX() < 30 + soundfx.getWidth() && e.getY() > 715 && e.getY() < 715 + soundfx.getHeight()){
				soundOn = !soundOn;
				game.setSound(soundOn);
				stopStartSound();
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

		}


		if (play && taxiStopped){
			/*if (e.getX() > 85 && e.getX() < 85 + 1025){
				if (e.getY() > 175 && e.getY() < 175 + 200){
					c.setDifficulty(1);
					levelSelected = true;
				}

				if (e.getY() > 400 && e.getY() < 400 + 200){
					c.setDifficulty(2);
					levelSelected = true;
				}

				if (e.getY() > 625 && e.getY() < 625 + 200){
					c.setDifficulty(3);
					levelSelected = true;
				}

			}*/
			if (e.getY() > 175 && e.getY() < 175 + levelBag.getHeight()){
				if (e.getX() > 155 && e.getX() < 155 + levelBag.getWidth()){
					gameLevels.setCurrent(0);
					levelSelected = true;
				}
				
				else if (e.getX() > 355 && e.getX() < 355 + levelBag.getWidth()){
					if(levels[1].isUnlocked()){
						gameLevels.setCurrent(1);
						levelSelected = true;
					}
				}
				
				else if (e.getX() > 555 && e.getX() < 555 + levelBag.getWidth()){
					if(levels[2].isUnlocked()){
						gameLevels.setCurrent(2);
						levelSelected = true;

					}
				}
				
				else if (e.getX() > 755 && e.getX() < 755 + levelBag.getWidth()){
					if(levels[3].isUnlocked()){
						gameLevels.setCurrent(3);
						levelSelected = true;

					}
				}
				else if (e.getX() > 955 && e.getX() < 955 + levelBag.getWidth()){
					if(levels[4].isUnlocked()) {
						gameLevels.setCurrent(4);
						levelSelected = true;

					}
				} 
			}
		}


		if (e.getX() > 10 && e.getX() < 10 + back.getWidth() && e.getY() > 700 && e.getY() < 700 + back.getHeight()){
			if (information){
				information = false;
				main = true;
				reversed = true;
				//menuCounter = 0;
			}
			if (play){
				play = false;
				main = true;
				//menuCounter = 0;
				reversed = true;
				car1Stopped = false;
				car2Stopped = false;
				taxiX = -300;
				taxiStopped = false;
				playedScreech = false;
				playTraffic();
			}
			if (scores){
				scores = false;
				reversed = true;
				//menuCounter = 0;
				main = true;

			}
		}
	}

	public BufferedImage grabImage(int col, int row, int length){
		BufferedImage img = smoke.getSubimage(((col-1) * length), ((row-1) * length), 112, length);
		return img;
	}

	public synchronized void playTraffic() {
		new Thread(new Runnable() { //creates a new thread
			public void run() {
				try { //contains code that might throw an exception
					traffic = AudioSystem.getClip(); 
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(
							this.getClass().getResource("/Audio/traffic.wav"));
					traffic.open(inputStream); //imports the audio file
					traffic.loop(Clip.LOOP_CONTINUOUSLY); //loops it forever
				} catch (Exception e) { //handles exception if the file is not found
					e.printStackTrace(); //prints the exception
				}
			}
		}).start();
	}

	public synchronized void playTires() { //plays the pop sound effect
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
					this.getClass().getResource("/Audio/tire_screech.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch(Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}
		playedScreech = true;
	}

	public void stopTraffic(){
		traffic.stop();
	}

	public void startTraffic(){
		traffic.loop(Clip.LOOP_CONTINUOUSLY); 
	}

	public void stopStartSound(){
		if (!soundOn){
			stopTraffic();
		}

		if (soundOn){
			startTraffic();
		}
	}

	public BufferedImage getSettingsMenu(){
		return settings;		
	}

	public BufferedImage getMusicOn(){
		return music;		
	}

	public BufferedImage getMusicOff(){
		return musicOff;		
	}

	public BufferedImage getSoundOn(){
		return soundfx;		
	}

	public BufferedImage getSoundOff(){
		return soundfxOff;		
	}

	public void setLevels(Levels l){
		gameLevels = l;
		levels = gameLevels.getLevels();
	}

}

