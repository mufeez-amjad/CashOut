import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Levels {

	private Level[] levels = new Level[5];
	private Level current;
	private int level = 0;

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

		current = levels[level];
	}

	public void paint(Graphics2D g2d){
		current.paint(g2d);
	}

	public void update(Player p, CashOut game){
		current.update(p, game);
		if (current.isFinished()){ // && is faded out
			level++;
			current = levels[level];
		}
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

	public void isFinished(){
		if (current.isFinished()){
			level++;
			current = levels[level];				
		}
	}
	
	/*public static void saveScore(int level) { //saves the score to the text file
		String filePath = null; //file path to the text file
		if (level == 1) filePath = "src/Easy.txt";
		if (level == 2) filePath = "src/Normal.txt";
		if (level == 3) filePath = "src/Hard.txt";
		if (level == 4) filePath = "src/Insane.txt";
		try { //contains code that might throw an exception
			FileWriter fw = new FileWriter(filePath, true); //creates or opens a text file
			PrintWriter pw = new PrintWriter(fw); //PrintWriter takes the file was a parameter
			if (score > 0) pw.write(score +"\r\n"); //prints the score in the text file
			pw.close(); //stops the printing
			fw.close(); //closes the file
			savedScore  = true;
		}
		catch(IOException e) { //handles exception if the file is not found
			System.err.println("Writing - HighScores.txt could not be found!");
		}
	}

	public static int highScore(int level) { //calculates the highest score from the text file
		highScore = 0;
		String filePath = null; //file path to the text file
		if (level == 1) filePath = "src/Easy.txt";
		if (level == 2) filePath = "src/Normal.txt";
		if (level == 3) filePath = "src/Hard.txt";
		if (level == 4) filePath = "src/Insane.txt";
		Scanner scanner;
		try { //contains code that might throw an exception
			scanner = new Scanner(new File(filePath)); //opens the text file
			List<Integer> scores = new ArrayList<Integer>(); //creates and array list of scores
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
	*/
}
