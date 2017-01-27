import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Levels {

	private Level[] levels = new Level[5];
	private int[] highScores = new int[5];
	private Level current;
	private int level = 0;
	private boolean transition;
	private int fade = 0;
	private boolean fadedOut;
	private boolean fadedIn;

	public Levels(CashOut c, Player p){
		Level1 one = new Level1(c, p);
		Level2 two = new Level2(c, p);
		Level3 three = new Level3(c, p);
		Level4 four = new Level4(c, p);
		Level5 five = new Level5(c, p);

		levels[0] = one;
		levels[1] = two;
		levels[2] = three;
		levels[3] = four;
		levels[4] = five;

		current = levels[level];

		for (int i = 0; i < highScores.length; i++){
			int score = 0;
			score = highScore(i);
			if (score == 0){
				levels[i].unlock();
				break;
			} 
			levels[i].setHighScore(score);
		}
		//resetScore();
	}
	
	public void readScore(){
		for (int i = 0; i < highScores.length; i++){
			int score = 0;
			score = highScore(i);
			if (score == 0){
				levels[i].unlock();
				break;
			} 
			levels[i].setHighScore(score);
		}
	}

	public void paint(Graphics2D g2d){
		current.paint(g2d);
		Color color = new Color(0, 0, 0, fade);
		g2d.setColor(color);
		g2d.fillRect(0, 0, 1200, 900);
	}

	public void update(Player p, CashOut game){
		current.update(p, game);
		
		if (current.getTimer().getTimesUp()){
			game.setGameOver(true);
		}
		if (current.isFinished() && level < 4){ // && is faded out
			if (!transition) {
				saveScore();
				current.setCurrentLevel(false, p);
				transition = true;
			}
			if (!fadedOut && fade <= 250){
				fade+=5;
			}
			if (fade == 255) fadedOut = true;
			if (fadedOut && fade >= 5){
				fade -= 5;
			}
			if (fadedOut && fade == 0){
				fadedIn = true;
				game.resetInventory();
				level++;
				current = levels[level];
				current.setCurrentLevel(true, p);
				p.reset();
			}
			if (fadedOut && fadedIn){
				transition = false;
				fadedOut = false;
				fadedIn = false;
			}
		}
		
		if (level == 4 && current.isFinished()){ // && is faded out
			if (!transition) {
				saveScore();
				current.setCurrentLevel(false, p);
				transition = true;
			}
			if (!fadedOut && fade <= 250){
				fade+=5;
			}
			if (fade == 255) fadedOut = true;
			if (fadedOut && fade >= 5){
				fade -= 5;
			}
			if (fadedOut && fade == 0){
				fadedIn = true;
				game.resetInventory();
				game.setWin(true);
			}
			if (fadedOut && fadedIn){
				transition = false;
				fadedOut = false;
				fadedIn = false;
			}
		}

	}

	public Level getCurrent(){
		return current;
	}

	public Level[] getLevels(){
		return levels;
	}

	public void setCurrent(int i, Player p){
		level = i;
		current = levels[level];
		current.setCurrentLevel(true, p);
	}

	public void retry(CashOut c, Player p){	
		levels[0] = new Level1(c, p);
		levels[1] = new Level2(c, p);
		levels[2] = new Level3(c, p);
		levels[3] = new Level4(c, p);
		levels[4] = new Level5(c, p);
		readScore();
		c.resetSuspicion();
		current = levels[level];
		current.setCurrentLevel(true, p);
	}


	public boolean mazePuzzle(){ //Null Pointer
		return current.isHackingLaser();
	}	

	public void saveScore() { //saves the score to the text file
		String userHome = System.getProperty("user.home");
		String outputFolder = userHome + File.separator + "CashOut";
		File folder = new File(outputFolder);
		if (!folder.exists()) {
		   folder.mkdir();
		}
		try { //contains code that might throw an exception
			FileWriter fw = new FileWriter(folder + File.separator + "Level" + String.valueOf(level)+".txt" , true); //creates or opens a text file
			PrintWriter pw = new PrintWriter(fw); //PrintWriter takes the file was a parameter
			if (current.getScore() > 0) pw.write(current.getScore() +"\r\n"); //prints the score in the text file
			pw.close(); //stops the printing
			fw.close(); //closes the file
			System.out.println(folder);
		}
		catch(IOException e) { //handles exception if the file is not found
			System.err.println("Writing - Score.txt could not be found!");
		}
	}

	public int highScore(int level) { //calculates the highest score from the text file
		String userHome = System.getProperty("user.home");
		String outputFolder = userHome + File.separator + "CashOut";
		File folder = new File(outputFolder);
		int highScore = 0;
		Scanner scanner;
		try { //contains code that might throw an exception
			scanner = new Scanner(new File(folder + File.separator + "Level" + String.valueOf(level)+".txt")); //opens the text file
			ArrayList<Integer> scores = new ArrayList<Integer>(); //creates and array list of scores
			while(scanner.hasNextInt())//while there is another score
			{
				scores.add(scanner.nextInt()); //add the score to the array list
			}

			for (int s : scores){ //enhanced for loop
				if (s > highScore){ //if s is the highest score so far, set it to high score
					highScore = s;
				}
			}
			scanner.close(); //closes the file
			scores.clear(); //clears the array list
		}
		catch (FileNotFoundException e) { //handles exception if the file is not found
			// TODO Auto-generated catch block
			e.printStackTrace(); //prints the exception
		}

		return highScore;
	}

	public void resetScore() { //saves the score to the text file
		for (int i = 0; i < levels.length; i++) {
			String filePath = "src/Text/Level"+String.valueOf(i)+".txt";
			try { //contains code that might throw an exception
				FileWriter fw = new FileWriter(filePath); //creates or opens a text file
				PrintWriter writer = new PrintWriter(fw);
				writer.close();
			}
			catch(IOException e) { //handles exception if the file is not found
				System.err.println("Resetting - Score text files could not be found!");
			}
		}
	} 
	
}
