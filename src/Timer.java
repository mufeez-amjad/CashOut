import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;

public class Timer {

	private int progress = 360;
	private int x;
	private int y;
	private int size;
	private int speed;
	private boolean timesUp = false;
	private boolean running = false;
	private Color color;

	public Timer(int x, int y, int size, int speed, String c, boolean state){
		this.x = x;
		this.y = y;
		this.size = size;
		this.speed = speed;
		progress *= this.speed;
		running = state;
		if (c.equals("RED")) color = Color.decode("0x990000");
		else if (c.equals("GREEN")) color = Color.decode("0x065C27");
		else if (c.equals("WHITE")) color = Color.white;
	}

	public void paint(Graphics2D g2d){
		checkTime();
		AffineTransform old = g2d.getTransform(); //gets original painting properties
		if (!timesUp && running) progress--;
		Arc2D.Float arc = new Arc2D.Float(Arc2D.PIE);
		g2d.translate(x, y); //moves origin
		g2d.rotate(Math.toRadians(270)); //rotates
		arc.setFrameFromCenter(new Point(0,0), new Point(size, size));
		arc.setAngleStart(360);
		arc.setAngleExtent(progress/speed);
		Color trans = new Color(color.getRed(), color.getGreen(), color.getBlue(), 200);
		g2d.setColor(trans);
		g2d.fill(arc);
		g2d.setTransform(old); //resets back to original
		g2d.setFont(CashOut.getFontMedium());
		g2d.drawString("Timer", x - 130, y + 10);
	}

	private void checkTime(){
		if (-progress/speed >= 0){
			timesUp = true;
			running = false;
		}
	}

	public void timesUp(){
		timesUp = true;
		running = false;
	}

	public boolean getTimesUp(){
		return timesUp;
	}

	public boolean isRunning(){
		return running;
	}

	public void stopStart(){
		running = !running;
	}
}