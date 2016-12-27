import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Arc2D;

public class Timer {

	private int progress = 360;
	private int x;
	private int y;
	private int size;
	private int speed;
	private boolean timesUp = false;
	private boolean running = true;

	public Timer(int x, int y, int size, int speed){
		this.x = x;
		this.y = y;
		this.size = size;
		this.speed = speed;
		progress *= this.speed;
	}

	public void paint(Graphics2D g2d){
		checkTime();
		if (!timesUp && running ){
			progress--;
			Arc2D.Float arc = new Arc2D.Float(Arc2D.PIE);
			g2d.translate(x, y);
			g2d.rotate(Math.toRadians(270));
			arc.setFrameFromCenter(new Point(0,0), new Point(size, size));
			arc.setAngleStart(360);
			arc.setAngleExtent(progress/speed);
			g2d.setColor(Color.decode("0x065C27"));
			g2d.fill(arc);
		}
	}
	
	private void checkTime(){
		if (-progress/speed >= 0){
			timesUp = true;
			running = false;
		}
	}
	
	private boolean getTimesUp(){
		return timesUp;
	}
	
	private void stopStart(){
		running = !running;
	}
}