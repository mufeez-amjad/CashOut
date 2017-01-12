import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

public class PicturePuzzle {
	private BufferedImage[][] masterPuzzle = new BufferedImage[3][3];
	private BufferedImage[][] puzzle = new BufferedImage[3][3];
	private BufferedImage firstImage;
	private boolean clicked1 = false;
	private boolean clicked2 = false;
	private boolean clicked3 = false;
	private boolean clicked4 = false;
	private boolean clicked5 = false;
	private boolean clicked6 = false;
	private boolean clicked7 = false;
	private boolean clicked8 = false;
	private boolean clicked9 = false;
	private int remainingMoves = 10;
	private BufferedImage img1;
	private BufferedImage img2;
	private BufferedImage img3;
	private BufferedImage img4;
	private BufferedImage img5;
	private BufferedImage img6;
	private BufferedImage img7;
	private BufferedImage img8;
	private BufferedImage img9;
	private int locationX;
	private int locationY;

	public PicturePuzzle(int x, int y) {
		locationX = x;
		locationY = y;
		remainingMoves = 10;
		try {
			img1 = ImageIO.read(getClass().getResource("/Images/dollar_01.gif"));
			img2 = ImageIO.read(getClass().getResource("/Images/dollar_02.gif"));
			img3 = ImageIO.read(getClass().getResource("/Images/dollar_03.gif"));
			img4 = ImageIO.read(getClass().getResource("/Images/dollar_04.gif"));
			img5 = ImageIO.read(getClass().getResource("/Images/dollar_05.gif"));
			img6 = ImageIO.read(getClass().getResource("/Images/dollar_06.gif"));
			img7 = ImageIO.read(getClass().getResource("/Images/dollar_07.gif"));
			img8 = ImageIO.read(getClass().getResource("/Images/dollar_08.gif"));
			img9 = ImageIO.read(getClass().getResource("/Images/dollar_09.gif"));
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error, could not find images to populate 2D array!");
		}
		masterPuzzle[0][0] = img1;
		masterPuzzle[0][1] = img2;
		masterPuzzle[0][2] = img3;
		masterPuzzle[1][0] = img4;
		masterPuzzle[1][1] = img5;
		masterPuzzle[1][2] = img6;
		masterPuzzle[2][0] = img7;
		masterPuzzle[2][1] = img8;
		masterPuzzle[2][2] = img9;

		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i < 10; i++)
			list.add(i);
		Collections.shuffle(list);
		BufferedImage img = img1;
		int counter = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (list.get(counter) == 1)
					img = img1;
				if (list.get(counter) == 2)
					img = img2;
				if (list.get(counter) == 3)
					img = img3;
				if (list.get(counter) == 4)
					img = img4;
				if (list.get(counter) == 5)
					img = img5;
				if (list.get(counter) == 6)
					img = img6;
				if (list.get(counter) == 7)
					img = img7;
				if (list.get(counter) == 8)
					img = img8;
				if (list.get(counter) == 9)
					img = img9;
				puzzle[i][j] = img;
				counter++;
			}
		}
	}

	public void setRemainingMoves(int m) {
		remainingMoves = m;
	}
	
	public boolean failed() {
		if (remainingMoves < 1 && !complete()) {
			return true;
		}
		else {
			return false;
		}
	}

	public void clicked(MouseEvent e) {
		// 1
		if (e.getX() >= locationX && e.getY() >= locationY && e.getX() <= locationX + puzzle[0][0].getWidth()
				&& e.getY() <= puzzle[0][0].getHeight() + locationY) {
			if (clicked1 || clicked2 || clicked3 || clicked4 || clicked5 || clicked6 || clicked7 || clicked8
					|| clicked9) {
				swap(firstImage, puzzle[0][0]);
			} else {
				firstImage = puzzle[0][0];
				clicked1 = true;
				System.out.println("CLICKED");
			}

		}
		// 2
		if (e.getX() >= locationX + puzzle[0][0].getWidth() + 6 && e.getY() >= locationY
				&& e.getX() <= locationX + puzzle[0][0].getWidth() + 6 + puzzle[0][0].getWidth()
				&& e.getY() <= puzzle[0][0].getHeight() + locationY) {
			if (clicked1 || clicked2 || clicked3 || clicked4 || clicked5 || clicked6 || clicked7 || clicked8
					|| clicked9) {
				swap(firstImage, puzzle[0][1]);
			} else {
				firstImage = puzzle[0][1];
				clicked2 = true;
				System.out.println("CLICKED");
			}
		}
		// 3
		if (e.getX() >= locationX + 2 * (puzzle[0][0].getWidth() + 6) && e.getY() >= locationY
				&& e.getX() <= locationX + 2 * (puzzle[0][0].getWidth() + 6) + puzzle[0][0].getWidth()
				&& e.getY() <= puzzle[0][0].getHeight() + locationY) {
			if (clicked1 || clicked2 || clicked3 || clicked4 || clicked5 || clicked6 || clicked7 || clicked8
					|| clicked9) {
				swap(firstImage, puzzle[0][2]);
			} else {
				firstImage = puzzle[0][2];
				clicked3 = true;
				System.out.println("CLICKED");
			}
		}
		// 4
		if (e.getX() >= locationX && e.getY() >= locationY + puzzle[0][0].getHeight() + 6
				&& e.getX() <= locationX + puzzle[0][0].getWidth()
				&& e.getY() <= puzzle[0][0].getHeight() + locationY + puzzle[0][0].getHeight() + 6) {
			if (clicked1 || clicked2 || clicked3 || clicked4 || clicked5 || clicked6 || clicked7 || clicked8
					|| clicked9) {
				swap(firstImage, puzzle[1][0]);
			} else {
				firstImage = puzzle[1][0];
				clicked4 = true;
				System.out.println("CLICKED");
			}

		}
		// 5
		if (e.getX() >= locationX + puzzle[0][0].getWidth() + 6 && e.getY() >= locationY + puzzle[0][0].getHeight() + 6
				&& e.getX() <= locationX + puzzle[0][0].getWidth() + 6 + puzzle[0][0].getWidth()
				&& e.getY() <= puzzle[0][0].getHeight() + locationY + puzzle[0][0].getHeight() + 6) {
			if (clicked1 || clicked2 || clicked3 || clicked4 || clicked5 || clicked6 || clicked7 || clicked8
					|| clicked9) {
				swap(firstImage, puzzle[1][1]);
			} else {
				firstImage = puzzle[1][1];
				clicked5 = true;
				System.out.println("CLICKED");
			}
		}
		// 6
		if (e.getX() >= locationX + 2 * (puzzle[0][0].getWidth() + 6)
				&& e.getY() >= locationY + puzzle[0][0].getHeight() + 6
				&& e.getX() <= locationX + 2 * (puzzle[0][0].getWidth() + 6) + puzzle[0][0].getWidth()
				&& e.getY() <= puzzle[0][0].getHeight() + locationY + puzzle[0][0].getHeight() + 6) {
			if (clicked1 || clicked2 || clicked3 || clicked4 || clicked5 || clicked6 || clicked7 || clicked8
					|| clicked9) {
				swap(firstImage, puzzle[1][2]);
			} else {
				firstImage = puzzle[1][2];
				clicked6 = true;
				System.out.println("CLICKED");
			}
		}
		// 7
		if (e.getX() >= locationX && e.getY() >= locationY + 2 * (puzzle[0][0].getHeight() + 6)
				&& e.getX() <= locationX + puzzle[0][0].getWidth()
				&& e.getY() <= puzzle[0][0].getHeight() + locationY + 2 * (puzzle[0][0].getHeight() + 6)) {
			if (clicked1 || clicked2 || clicked3 || clicked4 || clicked5 || clicked6 || clicked7 || clicked8
					|| clicked9) {
				swap(firstImage, puzzle[2][0]);
			} else {
				firstImage = puzzle[2][0];
				clicked7 = true;
				System.out.println("CLICKED");
			}

		}
		// 8
		if (e.getX() >= locationX + puzzle[0][0].getWidth() + 6
				&& e.getY() >= locationY + 2 * (puzzle[0][0].getHeight() + 6)
				&& e.getX() <= locationX + puzzle[0][0].getWidth() + 6 + puzzle[0][0].getWidth()
				&& e.getY() <= puzzle[0][0].getHeight() + locationY + 2 * (puzzle[0][0].getHeight() + 6)) {
			if (clicked1 || clicked2 || clicked3 || clicked4 || clicked5 || clicked6 || clicked7 || clicked8
					|| clicked9) {
				swap(firstImage, puzzle[2][1]);
			} else {
				firstImage = puzzle[2][1];
				clicked8 = true;
				System.out.println("CLICKED");
			}
		}
		// 9
		if (e.getX() >= locationX + 2 * (puzzle[0][0].getWidth() + 6)
				&& e.getY() >= locationY + 2 * (puzzle[0][0].getHeight() + 6)
				&& e.getX() <= locationX + 2 * (puzzle[0][0].getWidth() + 6) + puzzle[0][0].getWidth()
				&& e.getY() <= puzzle[0][0].getHeight() + locationY + 2 * (puzzle[0][0].getHeight() + 6)) {
			if (clicked1 || clicked2 || clicked3 || clicked4 || clicked5 || clicked6 || clicked7 || clicked8
					|| clicked9) {
				swap(firstImage, puzzle[2][2]);
			} else {
				firstImage = puzzle[2][2];
				clicked9 = true;
				System.out.println("CLICKED");
			}
		}
	}

	public boolean complete() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (puzzle[i][j] != masterPuzzle[i][j]) {
					return false;
				}
					
			}
		}
		return true;
	}

	public void swap(BufferedImage img1, BufferedImage img2) {
		remainingMoves--;
		//System.out.println("swapping");
		int img1Row = 0;
		int img1Col = 0;
		int img2Row = 0;
		int img2Col = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (img1 == puzzle[i][j]) {
					img1Row = i;
					img1Col = j;
				}
				if (img2 == puzzle[i][j]) {
					img2Row = i;
					img2Col = j;
				}
			}
		}
		puzzle[img1Row][img1Col] = img2;
		puzzle[img2Row][img2Col] = img1;
		firstImage = null;
		clicked1 = false;
		clicked2 = false;
		clicked3 = false;
		clicked4 = false;
		clicked5 = false;
		clicked6 = false;
		clicked7 = false;
		clicked8 = false;
		clicked9 = false;
	}

	public void paint(Graphics2D g2d) {		
		int initialX = locationX;
		int initialY = locationY;
		for (int h = 0; h < 3; h++) {
			for (int i = 0; i < 3; i++) {
				g2d.drawImage(puzzle[h][i], locationX, locationY, null);
				locationX += puzzle[0][0].getWidth() + 6;
			}
			locationX = initialX;
			locationY += puzzle[0][0].getHeight() + 6;
		}
		locationY = initialY;
		if (clicked1) {
			g2d.setColor(Color.RED);
			g2d.drawRect(locationX, locationY, firstImage.getWidth(), firstImage.getHeight());
		} else if (clicked2) {
			g2d.setColor(Color.RED);
			g2d.drawRect(locationX + puzzle[0][0].getWidth() + 6, locationY, firstImage.getWidth(),
					firstImage.getHeight());
		} else if (clicked3) {
			g2d.setColor(Color.RED);
			g2d.drawRect(locationX + 2 * (puzzle[0][0].getWidth() + 6), locationY, firstImage.getWidth(),
					firstImage.getHeight());
		} else if (clicked4) {
			g2d.setColor(Color.RED);
			g2d.drawRect(locationX, locationY + puzzle[0][0].getHeight() + 6, firstImage.getWidth(),
					firstImage.getHeight());
		} else if (clicked5) {
			g2d.setColor(Color.RED);
			g2d.drawRect(locationX + puzzle[0][0].getWidth() + 6, locationY + puzzle[0][0].getHeight() + 6,
					firstImage.getWidth(), firstImage.getHeight());
		} else if (clicked6) {
			g2d.setColor(Color.RED);
			g2d.drawRect(locationX + 2 * (puzzle[0][0].getWidth() + 6), locationY + puzzle[0][0].getHeight() + 6,
					firstImage.getWidth(), firstImage.getHeight());
		} else if (clicked7) {
			g2d.setColor(Color.RED);
			g2d.drawRect(locationX, locationY + 2 * (puzzle[0][0].getHeight() + 6), firstImage.getWidth(),
					firstImage.getHeight());
		} else if (clicked8) {
			g2d.setColor(Color.RED);
			g2d.drawRect(locationX + puzzle[0][0].getWidth() + 6, locationY + 2 * (puzzle[0][0].getHeight() + 6),
					firstImage.getWidth(), firstImage.getHeight());
		} else if (clicked9) {
			g2d.setColor(Color.RED);
			g2d.drawRect(locationX + 2 * (puzzle[0][0].getWidth() + 6), locationY + 2 * (puzzle[0][0].getHeight() + 6),
					firstImage.getWidth(), firstImage.getHeight());
		}

	}

	public boolean isFinished() {
		return complete() || failed();
	}


}