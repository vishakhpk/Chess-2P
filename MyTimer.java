/*
 * 
 * A class to handle timer functionality.
 * This class creates a separate thread to count the number of seconds.
 * that have passed for a particular player's turn.
 * White and Black have different instances of this and the timer toggles
 * between the two.
 * 
 */
package gui;

import java.util.concurrent.TimeUnit;

public class MyTimer extends Thread {

	private long elapsed = 0;
	public boolean running = false;
	private String hms = "00:00:00";
	
	public String getTime() {
		return hms;
	}
	
	@Override
	public void run() {
		while(true){
			if(running){
				//display live time in console
				print_time();
				elapsed += 1000;
			}
			
			//wait for second to pass
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//function to print time in HH:MM:SS
	private void print_time() {
		hms = String.format("%02d:%02d:%02d", 
				TimeUnit.MILLISECONDS.toHours(elapsed),
				TimeUnit.MILLISECONDS.toMinutes(elapsed) -  
				TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(elapsed)), // The change is in this line
				TimeUnit.MILLISECONDS.toSeconds(elapsed) - 
				TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsed)));  
		System.out.println(hms);
	}
}
