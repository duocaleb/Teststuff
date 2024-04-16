import processing.core.PApplet;
import java.util.Scanner;
/**
 * Main class to execute sketch
 * @author 
 *
 */
class Main {
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in); 
    String[] processingArgs = {"MySketch"};
	  Cube Cube = new Cube();
    Square Square = new Square();
    CubeTest CubeTest = new CubeTest();
    tester tester = new tester();
    PApplet.runSketch(processingArgs, CubeTest);
  }
  
}