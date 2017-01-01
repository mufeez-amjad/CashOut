import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Level {
	private BufferedImage level;
	private Note[] notes = new Note[4];
	private MoneyBag[] money = new MoneyBag[5];
	private Laser laser;
	private Officer officer; 
	private Camera camera;

	public Level(){
		try { 
			level = ImageIO.read(getClass().getResource("/Images/1st.png"));
		} catch (IOException e) { 
			System.err.println("1st.png could not be found");
		}
		
		for (int i = 0; i < notes.length; i++){
			notes[i] = new Note();
		}
		
		for (int i = 0; i < money.length; i++){
			money[i] = new MoneyBag();
		}
		laser = new Laser(700, 52, 90);
		officer = new Officer();
		camera = new Camera (1075, 52, 0);

	}
	
	public void paint(Graphics2D g2d){
		g2d.drawImage(level, 0, 0, null);
		
		for (int i = 0; i < notes.length; i++){
			notes[i].paint(g2d);
		}
		
		for (int i = 0; i < money.length; i++){
			money[i].paint(g2d);
		}
		laser.paint(g2d);
		officer.paint(g2d);
		camera.paint(g2d);
	}
	
	public void update(Player p, CashOut c){
		for (int i = 0; i < notes.length; i++){
			notes[i].collect(p);
		}
		
		for (int i = 0; i < money.length; i++){
			money[i].collect(p);
		}
		
		laser.update(p, c);
		officer.update(p, c);
		officer.collision(p.getNB(), p);
		camera.update();

	}
	
	public BufferedImage getBagImage(){
		if (money.length > 0) return money[0].getImage();
		return null;
	}
	
	public BufferedImage getLaserImage(){
		return laser.getImage();
	}
	
	public BufferedImage getCameraImage(){
		return camera.getImage();
	}
	
	public BufferedImage getOfficerImage(){
		return officer.getImage();
	}
}
