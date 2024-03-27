import processing.core.PApplet;

/**
 * Main class to execute sketch
 * @author 
 *
 */
class Main {
  public static void main(String[] args) {
    
    String[] processingArgs = {"MySketch"};
	  Square mySketch = new Square();
	  PApplet.runSketch(processingArgs, mySketch);
  }
  
}