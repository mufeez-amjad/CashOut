import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class VaultBody {
	private int x;
	private int y;
	private BufferedImage img;
	
	public VaultBody(int x, int y) {
		try {
			img = ImageIO.read(getClass().getResource("/Images/vault.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.x = x;
		this.y = y;
	}
	
	public void paint(Graphics2D g2d) {
		g2d.drawImage(img, x, y, null);
	}
}
