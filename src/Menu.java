import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Menu {

	private BufferedImage GALogo;
	private BufferedImage bg;
	private BufferedImage gameLogo;
	private BufferedImage GAPresents;
	private BufferedImage plane;
	private BufferedImage controls;
	private BufferedImage buttons;
	private BufferedImage back;
	private BufferedImage settings;
	private BufferedImage music;
	private BufferedImage musicOff;
	private BufferedImage soundfx;
	private BufferedImage soundfxOff;
	private BufferedImage[] exhaust = new BufferedImage[3];
	private BufferedImage car1;
	private BufferedImage car2;
	private BufferedImage taxi;

	private boolean introDone = false;

	private BufferedImage levelBag;
	private BufferedImage levelBagGray;


	private int car1x = -300;
	private int car2x = CashOut.getFrameWidth() + 300;
	private Inventory inv;
	private Clip traffic;

	private double cloudCounter = 0;
	private int smokeCounter = 0;
	private int menuCounter = 0;
	private int introFadeCounter = 255;
	private double introCounter = 0;
	
	private boolean settingsExpanded = false;
	private boolean levelSelected = false;
	private BufferedImage smoke;
	private BufferedImage currentImg;
	private int frame;
	private boolean information = false;
	private int extraInformation = 0;
	private boolean play = false;
	private boolean main = true;
	private boolean reversed;
	private boolean musicOn = true;
	private boolean soundOn = true;

	private int planeX = 1500;
	private int taxiX = -300;
	private boolean car1Stopped = false;
	private boolean car2Stopped = false;
	private boolean playedScreech = false;
	private boolean taxiStopped = false;
	private int fadeOut;
	private CashOut game;
	private boolean taxiGone = false;
	private boolean menuExit = false;

	private Levels gameLevels;
	private Level[] levels;
	private boolean gameOver = false;
	private boolean win = false;

	private BufferedImage camera;
	private BufferedImage laser;
	private BufferedImage moneyBag;
	private BufferedImage officer;
	private BufferedImage vault;
	private BufferedImage retry;
	private boolean reset;
	private BufferedImage picturePuzzle;
	private BufferedImage mazePuzzle;
	private Image mazePuzzleSmall;
	private Image cameraSmall;
	private Image l;
	private Image v;
	private Image o;
	private BufferedImage cameraHolder;
	private BufferedImage vaultPuzzle;
	private BufferedImage moneyBagHuge;
	private BufferedImage suspicion;


	public boolean isMenuExit() {
		return menuExit;
	}

	public void setMenuExit(boolean b) {
		menuExit = b;
	}

	public Menu(Inventory inventory, CashOut c){
		inv = inventory;
		game = c;
		try { 
			GALogo = ImageIO.read(getClass().getResource("/Images/GAStudios.png"));
		} catch (IOException e) { 
			System.err.println("GAStudios.png could not be found");
		}

		try { 
			GAPresents = ImageIO.read(getClass().getResource("/Images/Intro.png"));
		} catch (IOException e) { 
			System.err.println("Intro.png could not be found");
		}

		try { 
			mazePuzzle = ImageIO.read(getClass().getResource("/Images/mousemaze.png"));
		} catch (IOException e) { 
			System.err.println("GAStudios.png could not be found");
		}

		try { 
			picturePuzzle = ImageIO.read(getClass().getResource("/Images/PicturePuzzle.png"));
		} catch (IOException e) { 
			System.err.println("GAStudios.png could not be found");
		}

		try { 
			vaultPuzzle = ImageIO.read(getClass().getResource("/Images/VaultPuzzle.png"));
		} catch (IOException e) { 
			System.err.println("GAStudios.png could not be found");
		}

		try { 
			moneyBagHuge = ImageIO.read(getClass().getResource("/Images/moneyBagHuge.png"));
		} catch (IOException e) { 
			System.err.println("GAStudios.png could not be found");
		}

		try { 
			suspicion = ImageIO.read(getClass().getResource("/Images/Suspicion.png"));
		} catch (IOException e) { 
			System.err.println("Suspicion.png could not be found");
		}

		try { 
			laser = ImageIO.read(getClass().getResource("/Images/Laser.png"));
		} catch (IOException e) { 
			System.err.println("Laser.png could not be found");
		}

		try { 
			moneyBag = ImageIO.read(getClass().getResource("/Images/MoneyBag.png"));
		} catch (IOException e) { 
			System.err.println("Laser.png could not be found");
		}

		try { 
			officer = ImageIO.read(getClass().getResource("/Images/Officer.png"));
		} catch (IOException e) { 
			System.err.println("Officer.png could not be found");
		}

		try { 
			vault = ImageIO.read(getClass().getResource("/Images/vault.png"));
		} catch (IOException e) { 
			System.err.println("Officer.png could not be found");
		}

		try { 
			retry = ImageIO.read(getClass().getResource("/Images/Retry.png"));
		} catch (IOException e) { 
			System.err.println("Retry.png could not be found");
		}

		try { 
			camera = ImageIO.read(getClass().getResource("/Images/beam.png"));
		} catch (IOException e) { 
			System.err.println("beam.png could not be found");
		}

		try { 
			cameraHolder = ImageIO.read(getClass().getResource("/Images/cameraHolder.png"));
		} catch (IOException e) { 
			System.err.println("cameraHolder.png could not be found");
		}

		try { 
			levelBag = ImageIO.read(getClass().getResource("/Images/Level.png"));
		} catch (IOException e) { 
			System.err.println("Level.png could not be found");
		}

		try { 
			levelBagGray = ImageIO.read(getClass().getResource("/Images/LevelGray.png"));
		} catch (IOException e) { 
			System.err.println("LevelGray.png could not be found");
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
		playTraffic();

		cameraSmall = camera.getScaledInstance(30, 26, Image.SCALE_DEFAULT);
		mazePuzzleSmall = mazePuzzle.getScaledInstance(300, 225, Image.SCALE_DEFAULT);
		l = laser.getScaledInstance(40, 40, Image.SCALE_DEFAULT);
		v = vault.getScaledInstance(40, 40, Image.SCALE_DEFAULT);
		o = officer.getScaledInstance(75, 75, Image.SCALE_DEFAULT);

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
		if (!introDone){
			if (introCounter < 500) introCounter += 2;
			else if (introFadeCounter >= 2) introFadeCounter -= 2;
			else{
				menuCounter = 500;
				reversed = true;
				introDone = true;
			}
			g2d.setColor(new Color(65, 65, 65, introFadeCounter));
			g2d.fillRect(0, 0, CashOut.getFrameWidth(), CashOut.getFrameHeight());
			float alpha = (float) (1f - introCounter/500.0); //draw half transparent
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
			g2d.setComposite(ac);
			g2d.drawImage(GAPresents, (CashOut.getFrameWidth()/2) - (GAPresents.getWidth()/2), (CashOut.getFrameHeight()/2) - (GAPresents.getHeight()/2), null);
		}
		if (introDone){
			Color grey = new Color(88, 89, 91, 220);
			Color greyTrans = new Color(150, 150, 150, 127);

			if (win){
				g2d.setColor(grey);
				g2d.fillRoundRect(50, 50, 1100, 800, 25, 25);

				g2d.setFont(CashOut.getFontHuge());
				g2d.setColor(Color.decode("0x065C27"));
				FontMetrics fontMetrics = g2d.getFontMetrics(CashOut.getFontHuge()); 
				int stringLength = fontMetrics.stringWidth("YOU WIN");
				g2d.drawString("YOU WIN", (CashOut.getFrameWidth()/2) - (stringLength/2), 375);
				g2d.setFont(CashOut.getFontBig());
				fontMetrics = g2d.getFontMetrics(CashOut.getFontBig()); 
				stringLength = fontMetrics.stringWidth("Your Score");
				g2d.drawString("Your Score", (CashOut.getFrameWidth()/2) - (stringLength/2), 470);
				g2d.setColor(Color.white);
				g2d.setFont(CashOut.getFontMedium());
				fontMetrics = g2d.getFontMetrics(CashOut.getFontMedium()); 
				stringLength = fontMetrics.stringWidth(String.valueOf(gameLevels.getCurrent().getScore()));
				g2d.drawString(String.valueOf(gameLevels.getCurrent().getScore()), (CashOut.getFrameWidth()/2) - (stringLength/2), 530);
			}

			if (gameOver){
				g2d.setColor(grey);
				g2d.fillRoundRect(50, 50, 1100, 800, 25, 25);

				g2d.setFont(CashOut.getFontHuge());
				g2d.setColor(Color.decode("0x990000")); //crimson

				FontMetrics fontMetrics = g2d.getFontMetrics(CashOut.getFontHuge()); 
				int stringLength = fontMetrics.stringWidth("GAME OVER");
				g2d.drawString("GAME OVER", (CashOut.getFrameWidth()/2) - (stringLength/2), 375);
				g2d.setFont(CashOut.getFontBig());
				fontMetrics = g2d.getFontMetrics(CashOut.getFontBig()); 
				g2d.setColor(Color.white);
				stringLength = fontMetrics.stringWidth("Your Score");
				g2d.drawString("Your Score", (CashOut.getFrameWidth()/2) - (stringLength/2), 470);
				g2d.setFont(CashOut.getFontMedium());
				fontMetrics = g2d.getFontMetrics(CashOut.getFontMedium()); 
				stringLength = fontMetrics.stringWidth(String.valueOf(gameLevels.getCurrent().getScore()));
				g2d.drawString(String.valueOf(gameLevels.getCurrent().getScore()), (CashOut.getFrameWidth()/2) - (stringLength/2), 530);
				g2d.drawImage(retry, (CashOut.getFrameWidth()/2) - (retry.getWidth()/2), 600, null);
			}

			else if (main){
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

				if (information){

					g2d.setColor(grey);
					g2d.fillRoundRect(50, 50, 1100, 800, 25, 25);
					if (extraInformation == 0){
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
						g2d.drawImage(inv.getNoteImage(), x, y, null);
						g2d.drawImage(moneyBag, x + 70, y + 10, null);

						g2d.setFont(CashOut.getFontSmall());
						g2d.drawString("Collect", x + 30, y + 75);

						g2d.setColor(Color.gray);
						g2d.drawImage(cameraSmall, x + 240, y + 20, null);
						g2d.fillOval(x + 240 + 25, y + 20 - 5, 10, 10);
						g2d.setColor(Color.white);
						g2d.drawImage(l, x + 300, y + 30 - l.getHeight(null)/2, null);

						g2d.drawString("Hack", x + 300, y + 75);

						g2d.drawImage(v, x + 360, y + 20 - 10, null);

						g2d.drawString("Shoot", x + 570, y + 75);

						g2d.drawImage(o, x + 555, y - 10, null);
						int[] xPoints = {1090, 1090, 1130};
						int[] yPoints = {390, 470, 430};
						Polygon right = new Polygon(xPoints, yPoints, xPoints.length);
						g2d.setColor(greyTrans);
						g2d.fill(right);
					}
					if (extraInformation == 1){
						int[] xPoints = {120, 120, 80}; //left
						int[] yPoints = {390, 470, 430};
						Polygon left = new Polygon(xPoints, yPoints, xPoints.length);
						g2d.setColor(greyTrans);
						g2d.fill(left);

						int[] xPoints2 = {1090, 1090, 1130}; //right
						int[] yPoints2 = {390, 470, 430};
						Polygon right = new Polygon(xPoints2, yPoints2, xPoints2.length);
						g2d.setColor(greyTrans);
						g2d.fill(right);

						g2d.setFont(CashOut.getFontBig());
						g2d.setColor(Color.WHITE);
						FontMetrics fontMetrics = g2d.getFontMetrics(CashOut.getFontBig()); 
						int stringLength = fontMetrics.stringWidth("HACKING");
						g2d.drawString("HACKING", (CashOut.getFrameWidth()/2) - (stringLength/2), 125);
						g2d.setFont(CashOut.getFontMedium());
						g2d.drawString("Camera", 300, 200);
						g2d.drawString("Laser", 800, 200);

						g2d.setFont(CashOut.getCashFont().deriveFont(Font.PLAIN, 20));
						fontMetrics = g2d.getFontMetrics(CashOut.getCashFont().deriveFont(Font.PLAIN, 20)); 

						g2d.drawImage(camera, 300, 250, null);
						g2d.drawImage(cameraHolder, 400, 230, null);
						g2d.drawImage(picturePuzzle, 210, 430, null);
						g2d.drawString("Rearrange the pieces by clicking", 170, 625);
						g2d.drawString("two boxes to swap them", 215, 655);


						g2d.drawImage(laser, 765, 200, null);
						g2d.drawImage(mazePuzzleSmall, 715, 340, null);
						g2d.drawString("Reach the middle without", 700, 625);
						g2d.drawString("touching the sides", 745, 655);

						g2d.setColor(Color.yellow);
						stringLength = fontMetrics.stringWidth("Go near each device and press X");
						g2d.drawString("Go near each device and press X", (CashOut.getFrameWidth()/2) - (stringLength/2), 760);
						g2d.setColor(Color.white);
						stringLength = fontMetrics.stringWidth("Puzzles will be shown and will deactivate the device once solved");
						g2d.drawString("Puzzles will be shown and will deactivate the device once solved", (CashOut.getFrameWidth()/2) - (stringLength/2), 800);

					}

					if (extraInformation == 2){
						int[] xPoints = {120, 120, 80}; //left
						int[] yPoints = {390, 470, 430};
						Polygon left = new Polygon(xPoints, yPoints, xPoints.length);
						g2d.setColor(greyTrans);
						g2d.fill(left);

						int[] xPoints2 = {1090, 1090, 1130}; //right
						int[] yPoints2 = {390, 470, 430};
						Polygon right = new Polygon(xPoints2, yPoints2, xPoints2.length);
						g2d.setColor(greyTrans);
						g2d.fill(right);

						g2d.setFont(CashOut.getFontBig());
						g2d.setColor(Color.WHITE);
						FontMetrics fontMetrics = g2d.getFontMetrics(CashOut.getFontBig()); 
						int stringLength = fontMetrics.stringWidth("OFFICERS");
						g2d.drawString("OFFICERS", (CashOut.getFrameWidth()/2) - (stringLength/2), 125);
						g2d.drawImage(officer, (CashOut.getFrameWidth()/2) - (officer.getHeight()/2), 160, null);

						g2d.setFont(CashOut.getFontMedium());
						fontMetrics = g2d.getFontMetrics(CashOut.getFontMedium());
						stringLength = fontMetrics.stringWidth("Avoid the Officers");
						g2d.drawString("Avoid the Officers", (CashOut.getFrameWidth()/2) - (stringLength/2), 300);
						g2d.setColor(Color.yellow);
						stringLength = fontMetrics.stringWidth("line of sight");
						g2d.drawString("line of sight", (CashOut.getFrameWidth()/2) - (stringLength/2), 335);

						g2d.setColor(Color.WHITE);
						stringLength = fontMetrics.stringWidth("Shoot them to avoid getting caught");
						g2d.drawString("Shoot them to avoid getting caught", (CashOut.getFrameWidth()/2) - (stringLength/2), 400);

						g2d.setFont(CashOut.getFontBig());
						g2d.setColor(Color.WHITE);
						fontMetrics = g2d.getFontMetrics(CashOut.getFontBig()); 
						stringLength = fontMetrics.stringWidth("SUSPICION");
						g2d.drawString("SUSPICION", (CashOut.getFrameWidth()/2) - (stringLength/2), 500);

						g2d.drawImage(suspicion, 450, 550, null);
						g2d.setColor(greyTrans);
						g2d.fillRect(450 + suspicion.getWidth() + 20, 550, 200, 50);
						Color yellowTrans = new Color(255, 255, 0, 200);
						g2d.setColor(yellowTrans);
						g2d.fillRect(450 + suspicion.getWidth() + 20, 550, 75, 50);
						g2d.setColor(Color.WHITE);
						g2d.setFont(CashOut.getFontMedium());
						fontMetrics = g2d.getFontMetrics(CashOut.getFontMedium());
						stringLength = fontMetrics.stringWidth("Failing puzzles or being seen");
						g2d.drawString("Failing puzzles or being seen", (CashOut.getFrameWidth()/2) - (stringLength/2), 680);
						stringLength = fontMetrics.stringWidth("increases your suspicion level.");
						g2d.drawString("increases your suspicion level.", (CashOut.getFrameWidth()/2) - (stringLength/2), 710);
						g2d.setColor(Color.yellow);
						stringLength = fontMetrics.stringWidth("You are caught if you");
						g2d.drawString("You are caught if you", (CashOut.getFrameWidth()/2) - (stringLength/2), 760);
						stringLength = fontMetrics.stringWidth("exceed the maximum suspicion");
						g2d.drawString("exceed the maximum suspicion", (CashOut.getFrameWidth()/2) - (stringLength/2), 790);
					}

					if (extraInformation == 3){
						int[] xPoints = {120, 120, 80}; //left
						int[] yPoints = {390, 470, 430};
						Polygon left = new Polygon(xPoints, yPoints, xPoints.length);
						g2d.setColor(greyTrans);
						g2d.fill(left);

						int[] xPoints2 = {1090, 1090, 1130}; //right
						int[] yPoints2 = {390, 470, 430};
						Polygon right = new Polygon(xPoints2, yPoints2, xPoints2.length);
						g2d.setColor(greyTrans);
						g2d.fill(right);

						g2d.setFont(CashOut.getFontBig());
						g2d.setColor(Color.WHITE);
						FontMetrics fontMetrics = g2d.getFontMetrics(CashOut.getFontBig()); 
						int stringLength = fontMetrics.stringWidth("FINISHING EACH LEVEL");
						g2d.drawString("FINISHING EACH LEVEL", (CashOut.getFrameWidth()/2) - (stringLength/2), 125);
						g2d.drawImage(vault, (CashOut.getFrameWidth()/2) - (vault.getHeight()/2), 130, null);

						g2d.drawImage(inv.getNoteImage(), (CashOut.getFrameWidth()/2) - (inv.getNoteImage().getHeight()/2) - 175, 240, null);
						g2d.setFont(CashOut.getFontMedium());
						g2d.setColor(Color.YELLOW);
						g2d.drawString("Collect all 4 notes", 475, 270);
						g2d.setColor(Color.WHITE);
						g2d.drawString("to hack the vault", 490, 295);

						g2d.drawImage(vaultPuzzle, (CashOut.getFrameWidth()/2) - (vaultPuzzle.getWidth()/2), (CashOut.getFrameHeight()/2) - (vaultPuzzle.getHeight()/2) + 100, null);
						g2d.setFont(CashOut.getCashFont().deriveFont(Font.PLAIN, 20));
						fontMetrics = g2d.getFontMetrics(CashOut.getCashFont().deriveFont(Font.PLAIN, 20)); 
						stringLength = fontMetrics.stringWidth("Drag the notes into each slot correctly");
						g2d.drawString("Drag the notes into each slot correctly", (CashOut.getFrameWidth()/2) - (stringLength/2), 800);

					}

					if (extraInformation == 4){
						int[] xPoints = {120, 120, 80}; //left
						int[] yPoints = {390, 470, 430};
						Polygon left = new Polygon(xPoints, yPoints, xPoints.length);
						g2d.setColor(greyTrans);
						g2d.fill(left);
						g2d.setFont(CashOut.getFontBig());
						g2d.setColor(Color.WHITE);
						FontMetrics fontMetrics = g2d.getFontMetrics(CashOut.getFontBig()); 
						int stringLength = fontMetrics.stringWidth("SCORE");
						g2d.drawString("SCORE", (CashOut.getFrameWidth()/2) - (stringLength/2), 125);
						g2d.drawImage(moneyBagHuge, (CashOut.getFrameWidth()/2) - (moneyBagHuge.getWidth()/2), 200, null);
						g2d.setFont(CashOut.getFontMedium());
						fontMetrics = g2d.getFontMetrics(CashOut.getFontMedium()); 
						stringLength = fontMetrics.stringWidth("Collect money bags to get the");
						g2d.drawString("Collect money bags to get the", (CashOut.getFrameWidth()/2) - (stringLength/2), 500);
						stringLength = fontMetrics.stringWidth("most score per level");
						g2d.drawString("most score per level", (CashOut.getFrameWidth()/2) - (stringLength/2), 525);
						g2d.setFont(CashOut.getFontHuge());
						fontMetrics = g2d.getFontMetrics(CashOut.getFontHuge()); 
						stringLength = fontMetrics.stringWidth("HAVE FUN!");
						g2d.drawString("HAVE FUN!", (CashOut.getFrameWidth()/2) - (stringLength/2), 675);
					}
				}

				if (play){
					g2d.drawImage(taxi, taxiX, 570, null);
					g2d.drawImage(currentImg, taxiX - 100, 600, null);

					if (taxiStopped){
						g2d.setColor(grey);
						g2d.fillRoundRect(50, 50, 1100, 800, 25, 25);
						//new
						g2d.setFont(CashOut.getFontBig());
						g2d.setColor(Color.WHITE);
						FontMetrics fontMetrics = g2d.getFontMetrics(CashOut.getFontBig()); 
						int stringLength = fontMetrics.stringWidth("Level Select");
						g2d.drawString("Level Select", (CashOut.getFrameWidth()/2) - (stringLength/2), 125);

						for (int i = 0; i < levels.length; i++){
							g2d.setColor(greyTrans);
							g2d.fillRoundRect(-45 + ((i+1)*200), 280, levelBag.getWidth(), 60, 5, 5);
							if (!levels[i].isUnlocked()) g2d.drawImage(levelBagGray, -45 + ((i+1)*200), 175, null);
							else g2d.drawImage(levelBag, -45 + ((i+1)*200), 175, null);
							g2d.setColor(Color.WHITE);
							g2d.setFont(CashOut.getFontMedium());
							g2d.drawString(String.valueOf(i+1), -5 + ((i+1)*200) , 275);
							g2d.setFont(CashOut.getCashFont().deriveFont(Font.PLAIN, 17));
							fontMetrics = g2d.getFontMetrics(CashOut.getCashFont().deriveFont(Font.PLAIN, 17));
							stringLength = fontMetrics.stringWidth(String.valueOf(levels[i].getHighScore()));
							if (levels[i].getHighScore() > 0) g2d.drawString(String.valueOf(levels[i].getHighScore()), -30 + stringLength/2 + ((i+1)*200), 330);
						}

						g2d.setFont(CashOut.getFontHuge());
						fontMetrics = g2d.getFontMetrics(CashOut.getFontHuge()); 
						stringLength = fontMetrics.stringWidth("More levels coming soon!");
						g2d.drawString("More levels coming soon!", (CashOut.getFrameWidth()/2) - (stringLength/2), 550);
					}
				}

			}

			if ( ((taxiStopped && !taxiGone) || information || win) && menuCounter == 500) g2d.drawImage(back, 10, 700, null);

			Color color = new Color(0, 0, 0, fadeOut);
			g2d.setColor(color);
			g2d.fillRect(0, 0, CashOut.getFrameWidth(), CashOut.getFrameHeight());

		}

	}



	public void update(CashOut c){
		if (!menuExit){
			gameOver = c.getGameOver();
			win = c.getWin();
			soundOn = c.getSoundState();
			if (cloudCounter > 1200*2) cloudCounter = 0;
			else cloudCounter += 0.5;
			/*if (settingsExpanded && settingsCounter < 150){
			settingsCounter++;
		}
			 */
			if ((gameOver || win) && !reset){
				fadeOut = 0;
			}
			if (taxiGone || reset){
				if (fadeOut < 255) fadeOut += 3;
			}

			if (fadeOut == 255){
				if (reset) {
					c.retry();
					reset = false;
				}
				menuExit = true;
			}

			if (reversed){
				if (menuCounter >= 0) menuCounter -= 5;
				if (menuCounter == 0) reversed = false;
			}

			if (play || information){
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

	}

	public void mouseClicked(MouseEvent e, CashOut c) {
		//System.out.println(e.getX() + ", " + e.getY());
		if (!menuExit && introDone){
			if (gameOver){
				if (e.getX() > 538 && e.getX() < 538 + retry.getWidth() && e.getY() > 600 && e.getY() < 600 + retry.getHeight()){
					gameOver = false;
					c.setGameOver(gameOver);
					c.setWin(win);
					reset = true;
				}
			}
			else if (main){
				if (e.getX() > 21 && e.getX() < 21 + settings.getWidth() && e.getY() > 800 && e.getY() < 800 + settings.getHeight()){
					settingsExpanded = !settingsExpanded;
				}

				if (e.getX() > 30 && e.getX() < 30 + music.getWidth() && e.getY() > 760 && e.getY() < 760 + music.getHeight()){
					musicOn = !musicOn;
					game.setMusic(musicOn);
				}

				if (e.getX() > 30 && e.getX() < 30 + soundfx.getWidth() && e.getY() > 715 && e.getY() < 715 + soundfx.getHeight()){
					soundOn = !soundOn;
					game.setSound(soundOn);
					stopStartSound();
				}

				if (e.getX() > 217 && e.getX() < 376 + buttons.getWidth() && e.getY() > 645 && e.getY() < 645 + buttons.getHeight()){ //all buttons
					if (e.getX() > 376 && e.getX() < 376 + 124){
						information = true;
						settingsExpanded = false;
					}
					if (e.getX() > 698 && e.getX() < 698 + 124){
						play = true;
						settingsExpanded = false;
					}

				}

			}

			if (information){
				if (e.getX() > 1090 && e.getX() < 1130 && e.getY() > 390 && e.getY() < 470){ //right
					if (extraInformation < 4) extraInformation++;
				}

				if (e.getX() > 80 && e.getX() < 120 && e.getY() > 390 && e.getY() < 470){ //left
					if (extraInformation > 0) extraInformation--;
				}

			}
			if (play && taxiStopped){
				if (e.getY() > 175 && e.getY() < 175 + levelBag.getHeight()){
					if (e.getX() > 155 && e.getX() < 155 + levelBag.getWidth()){
						gameLevels.setCurrent(0, c.getPlayer());
						levelSelected = true;
					}

					else if (e.getX() > 355 && e.getX() < 355 + levelBag.getWidth()){
						if(levels[1].isUnlocked()){
							gameLevels.setCurrent(1, c.getPlayer());
							levelSelected = true;
						}
					}

					else if (e.getX() > 555 && e.getX() < 555 + levelBag.getWidth()){
						if(levels[2].isUnlocked()){
							gameLevels.setCurrent(2, c.getPlayer());
							levelSelected = true;

						}
					}

					else if (e.getX() > 755 && e.getX() < 755 + levelBag.getWidth()){
						if(levels[3].isUnlocked()){
							gameLevels.setCurrent(3, c.getPlayer());
							levelSelected = true;

						}
					}
					else if (e.getX() > 955 && e.getX() < 955 + levelBag.getWidth()){
						if(levels[4].isUnlocked()) {
							gameLevels.setCurrent(4, c.getPlayer());
							levelSelected = true;

						}
					} 
				}
			}


			if (e.getX() > 10 && e.getX() < 10 + back.getWidth() && e.getY() > 700 && e.getY() < 700 + back.getHeight()){
				if (information){
					information = false;
					extraInformation = 0;
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
					if (game.getSoundState()) playTraffic();
				}

				if (win){
					main = true;
					information = false;
					play = false;
					menuCounter = 500;
					reversed = true;
					gameOver = false;
					win = false;
					c.setGameOver(gameOver);
					c.retry();
					reset();
				}
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

	public void reset(){
		play = false;
		information = false;
		main = true;
		menuCounter = 500;
		reversed = true;
		taxiGone = false;
		playedScreech = false;
		fadeOut = 0;
		menuExit = false;
		reset = false;
		car1x = -300;
		car2x = CashOut.getFrameWidth() + 300;
		cloudCounter = 0;
		settingsExpanded = false;
		levelSelected = false;
		reversed = true;
		planeX = 1500;
		taxiX = -300;
		car1Stopped = false;
		car2Stopped = false;
		playedScreech = false;
		taxiStopped = false;
		taxiGone = false;
		menuExit = false;
		gameOver = false;
		win = false;
	}

}

