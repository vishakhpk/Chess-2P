/*
 * 
 * The driver class of the entire application. Execution starts and ends here.
 * This houses the primary GUI and accesses the backend logic.
 * 
 */

package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import gui.Back;
import gui.ChessCell;


public class Chess {
    private JPanel chessBoard;
	private ChessCell[][] buttonGrid;
	private static final String COLUMNS = "ABCDEFGH";
	private Image[] images;
	//(10,10) arbitrarily denotes a point outside grid
	private Point fromPoint;
	private Point toPoint;
	private MyTimer whiteMyTimer;
	private MyTimer blackMyTimer;
	private JLabel whiteTimeLabel;
	private JLabel blackTimeLabel;
	
	//initial configuration of Chess Board
	public static int back[][] = {
    		{2,3,4,5,6,4,3,2},
    		{1,1,1,1,1,1,1,1},
    		{0,0,0,0,0,0,0,0},
    		{0,0,0,0,0,0,0,0},
    		{0,0,0,0,0,0,0,0},
    		{0,0,0,0,0,0,0,0},
    		{7,7,7,7,7,7,7,7},
    		{8,9,10,11,12,10,9,8}
    };
	
	
	public Chess() {
		
		//first create an arraylist of all files in "res" folder
		//such that extension is a png(for piece images)
		File directory = new File("res");
		ArrayList<String> pictures = new ArrayList<String>(20);
        File[] f = directory.listFiles();
        for(File file : f) {
        	if (file != null && file.getName().toLowerCase().endsWith(".png")){
                pictures.add("/" + file.getName());
        	}
        }
        //iterate through them and store image files in images array
        int it=0;
        images = new Image[12];
        for(String piece : pictures) {
        	Image img;
			try {
				img = ImageIO.read(this.getClass().getResource(piece));
				images[it++] = img;
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
        //add in all the UI elements to be displayed on screen
        chessBoard = new JPanel(new FlowLayout());
		JPanel chessGrid = new JPanel(new GridLayout(9,9,2,2));
		chessGrid.setBorder(BorderFactory.createMatteBorder(50, 15, 15, 100, new JButton().getBackground()));
		JPanel timerWhite = new JPanel(new GridLayout(2,1,2,2));
		JPanel timerBlack = new JPanel(new GridLayout(2,1,2,2));
		timerWhite.setBorder(BorderFactory.createRaisedBevelBorder());
		timerBlack.setBorder(BorderFactory.createRaisedBevelBorder());
		timerWhite.add(new JLabel("White Timer"));
		whiteTimeLabel = new JLabel();
		timerWhite.add(whiteTimeLabel);
		blackTimeLabel = new JLabel();
		timerBlack.add(new JLabel("Black Timer"));
		timerBlack.add(blackTimeLabel);

		chessBoard.add(timerWhite);
        chessBoard.add(chessGrid);
        chessBoard.add(timerBlack);
        
        
        //create the button grid and add images to selected buttons
        buttonGrid = new ChessCell[8][8];
        Insets margin = new Insets(0, 0, 0, 0);
        for(int i=0; i<buttonGrid.length; i++){
        	for(int j=0; j<buttonGrid.length; j++) {
        		ChessCell cell = new ChessCell();
        		cell.setMargin(margin);
        		ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
        		cell.setIcon(icon);
        		//set background colors of white and black alternatingly
        		if ((i % 2 == 1 && j % 2 == 1) || (i % 2 == 0 && j % 2 == 0))
                    cell.setBackground(Color.WHITE);
                else
                    cell.setBackground(Color.BLACK);
        		buttonGrid[i][j] = cell;
        	}
        }
        
        //add action listeners and set points in the grid for each button
        //also add buttons to the Grid Layout panel created earlier
        for(int i=0; i<8; i++)
        	for(int j=0; j<8; j++){
        		//at 0th column, add column names 1,2,3....
        		if(j==0)
        			chessGrid.add(new JLabel(Integer.toString(8-i), SwingConstants.CENTER));
        		buttonGrid[i][j].coordinates = new Point(7-i,j);
        		buttonGrid[i][j].addActionListener(new ButtonHandler());
        		chessGrid.add(buttonGrid[i][j]);
        		buttonGrid[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        	}
        
        //add column names - A,B,C....
        chessGrid.add(new JLabel(""));
        for(int i=0; i<8; i++)
        	chessGrid.add(new JLabel(Character.toString(COLUMNS.charAt(i)),
        			SwingConstants.CENTER));
        
        //set both points to (10,10) to denote square that isn't in the grid
        fromPoint = new Point(10,10);
        toPoint = new Point(10,10);
        
        //start white timer as white plays first
      	whiteMyTimer = new MyTimer();
        blackMyTimer = new MyTimer();
        whiteMyTimer.start();
        whiteMyTimer.running = true;
	}
	
	/*
	 * The function backend passes the 2D array of positions to the instance of the class back
	 * The screen is refreshed if the move is valid
	 */
	
	public void backend(Point fromPoint, Point toPoint) {
		
		fromPoint.x = 7 - fromPoint.x;
		toPoint.x = 7 - toPoint.x;
		
		Back backend = new Back(back);
		if(backend.isValid(back, fromPoint, toPoint))
		{
			back[toPoint.x][toPoint.y] = back[fromPoint.x][fromPoint.y];
			back[fromPoint.x][fromPoint.y] = 0;
			
			if(blackMyTimer.isAlive() && blackMyTimer.running == true) {
				blackMyTimer.running = false;
				whiteMyTimer.running = true;
			}
			else {
				whiteMyTimer.running = false;
				if(blackMyTimer.getState() == new Thread().getState())
					blackMyTimer.start();
				blackMyTimer.running = true;
			}
			refresh_screen(back);
		}
		else
		{
			refresh_screen(back);
			System.out.println("Not valid");
		}
	}
	
	private class ButtonHandler implements ActionListener {
	    @Override
	    public void actionPerformed(ActionEvent e) {
	    	
	    	//figure out which cell was clicked
	    	ChessCell cell;
	    	cell = (ChessCell)e.getSource();
	    	
	    	//figure out if this is first or second click of the turn
	    	//if fromPoint is 10, it denotes a square outside, hence should be overwritten
	    	//don't call backend, wait for next click
	    	if(fromPoint.x == 10) {
	    		fromPoint = cell.coordinates.getLocation();
	    		if(back[7-fromPoint.x][fromPoint.y] == 0) {
	    			//can't move from empty square
	    			fromPoint.x = 10;
	    		}
	    		else {
	    			//highlight the selected square
	    			cell.setBorder(BorderFactory.createLineBorder(Color.BLUE,1));
	    		}
	    	}
	    	//this part is executed if fromPoint has valid square coordinates
	    	//which implies this is second click of turn
	    	//backend function must be called
	    	else if(toPoint.x == 10) {
	    		toPoint = cell.coordinates.getLocation();
	    		backend(fromPoint, toPoint);
	    		fromPoint.x=10;
	    		toPoint.x=10;
	    		for(int i=0; i<8; i++)
	    			for(int j=0; j<8; j++)
	    				//unhighlight all squares
	    				buttonGrid[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
	    			
	    	}
	    }
	}

	
	public void refresh_screen(int[][] backend_screen) {
		
		//retrieve timers and display onto label
		//ADD functionality to this in real time
		whiteTimeLabel.setText(whiteMyTimer.getTime());
		blackTimeLabel.setText(blackMyTimer.getTime());
		
		//change images of the squares to reflect moves
		for(int i=0; i<8; i++)
    		for(int j=0; j<8; j++) {
    			if(backend_screen[i][j] != 0){
    				ImageIcon icon = new ImageIcon(images[backend_screen[i][j] - 1]);
    				buttonGrid[i][j].setIcon(icon);
    			}
    			else {
    				buttonGrid[i][j].setIcon(null);
    			}
    		}
    }
		
	
	//utility function to display an error message in a message box
	public static void message_box(String message, String title) {
		Thread t = new Thread(new Runnable() {
			public void run() {
				JOptionPane.showMessageDialog(null, message, title,
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		t.start();
	}
	
	
	public final JComponent getGui() {
        return chessBoard;
    }

	
	public static void main(String[] args) {
		
		
		Chess game = new Chess();
		
		//set up a thread to handle the GUI
		Runnable gui = new Runnable() {
		    @Override
			public void run() {
		    	
		    	JFrame frame = new JFrame("CHESS(2 PLAYERS)");
                frame.add(game.getGui());
                // close JVM when the cross('X') is clicked
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                //start the frame with optimal size
                frame.pack();
                frame.setVisible(true);
                game.refresh_screen(back);
		    }
		    
    	};
    	// executes runnable on the AWT event-dispatching thread
    	SwingUtilities.invokeLater(gui);
	}
}


