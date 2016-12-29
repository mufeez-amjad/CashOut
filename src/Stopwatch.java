/*
 * Class: StopWatch
 * Orginal Author: Unknown.  Source: http://stackoverflow.com/questions/8255738/is-there-a-stopwatch-in-java
 * Modified by: Mr. Donald
 * Date Modified: Nov 18, 2014
 * 
 * Description:
 *   start() ->  Starts the timer
 *   stop()  ->  Stops the timer
 *   long getElapsedTime()  ->  returns the difference between stop() and star() as a long
 * 
 */

public class Stopwatch {
  
  private long startTime = 0;
  private long stopTime = 0;
  
  
  public void start() 
  {
    this.startTime = System.nanoTime();
  }
  
  
  public void stop() 
  {
    this.stopTime = System.nanoTime();
  }
  
  
  //elaspsed time in "Donald" time  (nanosecond/1000)
  public long getElapsedTime()
  {
    long elapsed;   
    elapsed = (stopTime - startTime)/ 1000000000;    
    return elapsed;
  }
  
}