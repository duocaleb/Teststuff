import processing.core.PApplet;
import java.awt.AWTException;   
import java.awt.Robot;   


/* 
 *  
 * The purpose of this program is to use 2d processing to make a 3d game
 * No 3d methods are used throughout the project.
 * 
 * Author: @duocaleb
*/
public class Rendering extends PApplet {  
  //initalizing the variables and the arrays
  double dblFocalLength = 500;
  int intScreenSize = 800;
  // I dont really have one rn
  double cameraX = 0;
  double cameraY = 0;
  double cameraZ = 0;
  double mouseRotationX = 0;
  double mouseRotationY = 0;
  double mouseCenteredX = 0;
  double mouseCenteredY = 0;
  // The amount of degrees it turns per half screen of distance the mouse is moved.
  double mouseSensitivity = 90;
  boolean[] isKeyPressed = new boolean[255];
  Robot mouseMover;

  /* 
   * For each 3d array:
    * Index #: function
    * 0: List of points(3d co-ordinets)
    * 1: Rotated pointslist
    * 2: Edge List, matches points to points.
    * 3: Face List:
      *   Stores {Face1, Face2, Face3, ...}
      *   Each face stores: {Point1, Point2, Point3, MaxZ, ColourR, ColourG, ColourB}
    * 4: Projected Points, which are the co-ordinates of the points on the actual display
  */
    //cube
    double[][][] Cube = { 
      { {-50,-50,-50 }, {50,-50,-50 }, {50,50,-50 }, {-50,50,-50 },
        {-50,-50,50 }, {50,-50,50 }, {50,50,50 }, {-50,50,50 } },   
        
        { {-50,-50,-50 }, {50,-50,-50 }, {50,50,-50 }, {-50,50,-50 },
        {-50,-50,50 }, {50,-50,50 }, {50,50,50 }, {-50,50,50 } },
        
        { { 0, 1 }, { 1, 3 }, { 3, 2 }, { 2, 0 }, { 4, 5 }, { 5, 7 }, { 7, 6 }, { 6, 4 }, { 0, 4 },
        { 1, 5 }, { 2, 6 }, { 3, 7 } },
        
        { {0,1,2, 0}, {1,2,3, 0}, {2,3,7, 0}, {2,6,7, 0}, {1,3,7, 0}, {1,5,7, 0}, {4,5,7, 0}, {4,6,7, 0}, {0,4,6, 0}, {0,2,6, 0}, {0,1,5, 0}, {0,4,5, 0}},
        
        { { 100, 100 }, { 200, 100 }, { 100, 100 }, { 200, 200 }, { 100, 100 }, { 200, 100 },
        { 100, 100 }, { 200, 200 } },

        { {-50,-50,-50 }, {50,-50,-50 }, {50,50,-50 }, {-50,50,-50 },
        {-50,-50,50 }, {50,-50,50 }, {50,50,50 }, {-50,50,50 } },
      };
    //Triangles
    double[][][] Pyramid = { 
        
        {{35,-35,0}, {-35,-35,0}, {0,-35,35}, {0,-35,-35}, {0,35,0}},   
        
        { {35, -35,0}, {-35,-35,0}, {0,-35,35}, {0,-35,-35}, {0,35,0} },
          
        { { 0, 2 }, { 1, 3 }, { 3, 0 }, { 2, 1 }, {0 , 1},  {0 , 4}, {1,4}, {2,4}, {3,4}, {4,4} },
          
        { {0,2,1, 90,70,173}, {0,1,2, 90,70,173}, {0,2,4, 30,  20, 200}, {1,3, 4,  20,138,0}, {2,1, 4, 38,0,90}, {3,0,4, 100,100, 100} },
          
        { {50,-35,0}, {-50,-35,0}, {0,-35,50}, {0,-35,-50}, {0,35,0}}
      };


  public void settings() {
    size(intScreenSize, intScreenSize);
  }

  public void setup() {
    background(45, 150, 207);

    try {
      mouseMover = new Robot();
    } catch (AWTException e) {
      e.printStackTrace();
    }
    noCursor();
    mouseMover.mouseMove(displayWidth/2, displayHeight/2);
  }
  int rotate = 0;
  public void draw() {
    clear();
    background(45, 150, 207);
    moveChar();
    drawCube(-50, -50, -50, 50, 0, 50, 0, 0, 0);
    cursorMovement();
  }


  public void cursorMovement(){
    if(mouseCenteredX == 0 && mouseCenteredY == 0){
      mouseCenteredX = mouseX;
      mouseCenteredY = mouseY;
    }
    mouseRotationX += (mouseSensitivity*(mouseX - mouseCenteredX))/(intScreenSize/2);
    mouseRotationY += (mouseSensitivity*(mouseY - mouseCenteredY))/(intScreenSize/2);
    if(mouseRotationX >= 360){
      mouseRotationX -= 360;
    }
    if(mouseRotationX <= -360){
      mouseRotationX += 360;
    }
    if(mouseRotationY >= 360){
      mouseRotationY -= 360;
    }
    if(mouseRotationY <= -360){
      mouseRotationY += 360;
    }
    mouseMover.mouseMove(displayWidth/2, displayHeight/2);
  }

  public void keyPressed() {
    isKeyPressed[(int)key] = true;
  }
  
  public void keyReleased() {
    isKeyPressed[(int)key] = false;
  }

  public void moveChar() {
    if(isKeyPressed[(int)'w']){
      cameraZ += 2;
      dblFocalLength += 2;
    }
    if(isKeyPressed[(int)'s']){
      cameraZ -= 2;
      dblFocalLength -= 2;
    }
    if(isKeyPressed[(int)'a']){
      cameraX += 2;
    }
    if(isKeyPressed[(int)'d']){
      cameraX -= 2;
    }
  }

  public void drawCube(double xStart, double yStart, double zStart, double xEnd, double yEnd, double zEnd, double rotationXZ, double rotationXY, double rotationYZ){
    double xPoint = (xEnd-xStart)/2;
    double yPoint = (yEnd-yStart)/2;
    double zPoint = (zEnd-zStart)/2;
    for(int x = 0; x < 4; x += 1){
      Cube[0][x*2][0] = xStart;
      Cube[0][x*2+1][0] = xEnd;
      Cube[0][(int)Math.floor(x/2)*2+x][1] = yStart;
      Cube[0][(int)Math.floor(x/2)*2+x+2][1] = yEnd;
      Cube[0][x][2] = zStart;
      Cube[0][x+4][2] = zEnd;
    }
    for(int x = 0; x < 4; x += 1){
      Cube[1][x*2][0] = -xPoint;
      Cube[1][x*2+1][0] = xPoint;
      Cube[1][(int)Math.floor(x/2)*2+x][1] = -yPoint;
      Cube[1][(int)Math.floor(x/2)*2+x+2][1] = yPoint;
      Cube[1][x][2] = -zPoint;
      Cube[1][x+4][2] = zPoint;
    }
    setRotation(Cube, rotationXZ,rotationXY,rotationYZ);
    rotateAroundCamera(Cube, mouseRotationX, mouseRotationY);
    projectPoints(Cube);
    drawFaces(Cube);
    drawLines(Cube);
  }

  public void rotateAroundCamera(double[][][] array, double angleToTurnX, double angleToTurnY){
    
  }

  /*
   * Project the points onto the screen.
   */
  public void projectPoints(double[][][] array) {
    for (int x = 0; x < array[0].length; x++) {
      double z = array[0][x][2];
      if (dblFocalLength - array[0][x][2] <= 0){
        // Prevents an asymptote from ruining everything
        z = dblFocalLength - 1;
      }
      array[4][x][0] = (array[0][x][0] * dblFocalLength) / (dblFocalLength - z);
      array[4][x][1] = (array[0][x][1] * dblFocalLength) / (dblFocalLength - z);
      
    }
    
  }

  public void drawLines(double[][][] array) {
    stroke(0,0,0);
    for (int x = 0; x < (int)array[2].length; x++) {
      line(intScreenSize / 2 + (int) array[4][(int)array[2][x][0]][0], intScreenSize / 2 - (int) array[4][(int)array[2][x][0]][1],
           intScreenSize / 2 + (int) array[4][(int)array[2][x][1]][0], intScreenSize / 2 - (int) array[4][(int)array[2][x][1]][1]);
    }
  }

  public void setRotation(double[][][] array, double angleXZ, double angleXY, double angleYZ) {
    // Technically, this is add rotation, not set. However, since the shapes are being reset each frame, they are the same thing anyways.

    angleXZ = Math.toRadians(angleXZ);
    angleXY = Math.toRadians(angleXY);
    angleYZ = Math.toRadians(angleYZ);

    for (int count = 0; count < array[0].length; count++) {
      double x = array[1][count][0];
      double z = array[1][count][2];
      double xInit = array[1][count][0];
      double zInit = array[1][count][2];
      array[1][count][0] = x * Math.cos(angleXZ) - z * Math.sin(angleXZ);
      array[1][count][2] = x * Math.sin(angleXZ) + z * Math.cos(angleXZ);

      array[0][count][0] += array[1][count][0] - xInit;
      array[0][count][2] += array[1][count][2] - zInit;
    }

    for (int count = 0; count < array[0].length; count++) {
      double x = array[1][count][0];
      double y = array[1][count][1];

      double xInit = array[1][count][0];
      double yInit = array[1][count][1];
      array[1][count][0] = x * Math.cos(angleXY) - y * Math.sin(angleXY);
      array[1][count][1] = x * Math.sin(angleXY) + y * Math.cos(angleXY);

      array[0][count][0] += array[1][count][0] - xInit;
      array[0][count][1] += array[1][count][1] - yInit;
    }

    for (int count = 0; count < array[0].length; count++) {
      double z = array[1][count][2];
      double y = array[1][count][1];

      double zInit = array[1][count][2];
      double yInit = array[1][count][1];

      array[1][count][2] = z * Math.cos(angleYZ) - y * Math.sin(angleYZ);
      array[1][count][1] = z * Math.sin(angleYZ) + y * Math.cos(angleYZ);

      array[0][count][2] += array[1][count][2] - zInit;
      array[0][count][1] += array[1][count][1] - yInit;
    }
  }

  public void drawFaces(double[][][] array){
    noStroke();
    for (int x = array[3].length - 1; x > -1; x--) {
      beginShape();
      for (int y = 0; y < 3; y++) { 
        vertex(intScreenSize/2 + (float)array[4][(int)array[3][x][y]][0], intScreenSize/2 - (float)array[4][(int)array[3][x][y]][1]);
      }
      endShape(CLOSE);
    }
  }
}
