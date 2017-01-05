import java.awt.Graphics2D;

public class Levels {
	
	private Level[] levels = new Level[5];
	private Level current;
		
	public Levels(CashOut c, Player p){
		Level1 one = new Level1(c);
		Level2 two = new Level2(c);
		Level3 three = new Level3(c);
		Level4 four = new Level4(c);
		Level5 five = new Level5(c);
		
		levels[0] = one;
		levels[1] = two;
		levels[2] = three;
		levels[3] = four;
		levels[4] = five;
		
		current = one;
	}
	
	public void paint(Graphics2D g2d){
		current.paint(g2d);
	}
	
	public void update(Player p, CashOut game){
		current.update(p, game);
	}
		
	public Level getCurrent(){
		return current;
	}
	
	public Level[] getLevels(){
		return levels;
	}
	
	public void setCurrent(int i){
		current = levels[i];
	}
	
	public boolean mazePuzzle(){
		return current.isHackingLaser();
	}
	
	
}
