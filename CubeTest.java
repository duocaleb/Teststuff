import processing.core.PApplet;
import java.util.*;

/* While making this, i didnt relise that the box() function existed, so i didn't use it at all.
 * 
 * Actually, i didn't even relise java proccessing had 3d capabilities, so i made my own renderer.
 * 
 * Out of all the code here, only Arrays.sort(faceListStored, (a, b) -> Double.compare(a[4],b[4])); is taken from the internet
 *  
 * Enjoy the project
 * 
 * Author: @duocaleb
*/
public class CubeTest extends PApplet {
  //initalize the variables... theres a lot of them

  //points for the cube(conviniece)
  double dblFocalLength = 500;
  /* 
   * Index #: function
   * 0: List of points(3d co-ordinets)
   * 1: Initial state of the shape. This one does not change.
   * 2: Rotation. Stores {XZ, XY, YZ}, then {xzVel, xyVel, yzVel}, then {prevMouseX, prevMouseY}
   * 3: Edge List, matches points to points.
   * 4: Face List:
    *   Stores {Face1, Face2, Face3, ...}
    *   Each face stores: {Point1, Point2, ..., ZStorage, ColourR, ColourG, ColourB}
   * 5: Projected Points, which are the co-ordinates of the points on the actual display
  */
  double[][][] dblCubeList = { 
      
    { {-50,-50,-50 }, {50,-50,-50 }, {50,50,-50 }, {-50,50,-50 },
      {-50,-50,50 }, {50,-50,50 }, {50,50,50 }, {-50,50,50 } },   
      
      { {-50,-50,-50 }, {50,-50,-50 }, {50,50,-50 }, {-50,50,-50 },
      {-50,-50,50 }, {50,-50,50 }, {50,50,50 }, {-50,50,50 } },

      {{0,0,0}, {0,0,0}, {0,0}},
      
      { { 0, 1 }, { 1, 2 }, { 2, 3 }, { 3, 0 }, { 4, 5 }, { 5, 6 }, { 6, 7 }, { 7, 4 }, { 0, 4 },
      { 1, 5 }, { 2, 6 }, { 3, 7 } },
      
      { { 0, 1, 2, 3, 0,  0,  70, 173 }, { 4, 5, 6, 7,   0,  0, 155, 72 },
      { 1, 5, 6, 2,  0,  255, 88, 0 }, { 0, 4, 7, 3,  0,  183, 18, 52 }, { 0, 1, 5, 4,  0,  255, 255, 255 },
      { 3, 7, 6, 2,  0,  255, 213, 0 } },
      
      { { 100, 100 }, { 200, 100 }, { 100, 100 }, { 200, 200 }, { 100, 100 }, { 200, 100 },
      { 100, 100 }, { 200, 200 } }
    };

    double[][][] dblPyramidList = { 
      
      {{50,-35,0}, {-50,-35,0}, {0,-35,50}, {0,-35,-50}, {0,35,0}},   
      
      { {50,-35,0}, {-50,-35,0}, {0,-35,50}, {0,-35,-50}, {0,35,0} },
  
      {{0,0,0}, {0,0,0}, {0,0}},
        
      { { 0, 2 }, { 1, 3 }, { 2, 1 }, { 3, 0 }, {0 , 4}, {1,4}, {2,4}, {3,4}, {4,4} },
        
      { {0,2,1,3,0,0,70,173}, {0,2,4, 0,  0,  70, 173}, {1,3, 4,0,  0,  70, 173}, {2,1, 4,0,  0,  70, 173}, {3, 0 , 4,0,  0,  70, 173} },
        
      { {50,-35,0}, {-50,-35,0}, {0,-35,50}, {0,-35,-50}, {0,35,0}}
    };

  boolean boolMouseWait = false;
  double dblXRotInitial = 0, dblYRotInitial = 0;
  int intMouseXInitial = 0, intMouseYInitial = 0;
  int intScreenSize = 800;

  public void settings() {
    size(intScreenSize, intScreenSize);
  }

  public void setup() {
    background(45, 150, 207);
  }

  public void draw() {
    clear();
    background(45, 150, 207);
    //doStuff(dblCubeList);
    doStuff(dblPyramidList);
  }

  public void doStuff(double[][][] array){
    resetRotation(array);
    mouseSpinner(array);
    resetRotation(array);
    projectPoints(array);
    drawLines(array);
    drawFaces(array);
  }

  public void projectPoints(double[][][] array) {
    for (int x = 0; x < array[0].length; x++) {
      double z = array[0][x][2];
      array[5][x][0] = intScreenSize / 2
          + (array[0][x][0] * dblFocalLength) / (dblFocalLength + z);
      array[5][x][1] = intScreenSize / 2
          - (array[0][x][1] * dblFocalLength) / (dblFocalLength + z);
    }
  }

  public void drawLines(double[][][] array) {
    for (int x = 0; x < (int)array[3].length; x++) {
      line((int) array[5][(int)array[3][x][0]][0], (int) array[5][(int)array[3][x][0]][1],
          (int) array[5][(int)array[3][x][1]][0], (int) array[5][(int)array[3][x][1]][1]);
    }
  }

  public void rotateAngles(double[][][] array, double angleXZ, double angleXY, double angleYZ) {
    setRotation(array, angleXZ + array[2][0][0], angleXY + array[2][0][1], angleYZ + array[2][0][2]);
  }

  public void resetRotation(double[][][] array) {
    if (array[2][0][0] >= 360 || array[2][0][0] <= -360) {
      array[2][0][0] = array[2][0][0] % 360;
    }
    if (array[2][0][2] >= 360 || array[2][0][2] <= -360) {
      array[2][0][2] = array[2][0][2] % 360;
    }
    if (array[2][0][1] >= 360 || array[2][0][1] <= -360) {
      array[2][0][1] = array[2][0][1] % 360;
    }
  }

  public void setRotation(double[][][] array, double angleXZ, double angleXY, double angleYZ) {
    for (int x = 0; x < array[0].length; x++) {
      for (int y = 0; y < 3; y++) {
        array[0][x][y] = array[1][x][y];
      }
    }

    array[2][0][2] = angleYZ;
    array[2][0][1] = angleXY;
    array[2][0][0] = angleXZ;
    angleXZ = Math.toRadians(angleXZ);
    angleXY = Math.toRadians(angleXY);
    angleYZ = Math.toRadians(angleYZ);

    for (int count = 0; count < array[0].length; count++) {
      double x = array[0][count][0];
      double z = array[0][count][2];
      array[0][count][0] = x * Math.cos(angleXZ) - z * Math.sin(angleXZ);
      array[0][count][2] = x * Math.sin(angleXZ) + z * Math.cos(angleXZ);
    }

    for (int count = 0; count < array[0].length; count++) {
      double x = array[0][count][0];
      double y = array[0][count][1];
      array[0][count][0] = x * Math.cos(angleXY) - y * Math.sin(angleXY);
      array[0][count][1] = x * Math.sin(angleXY) + y * Math.cos(angleXY);
    }

    for (int count = 0; count < array[0].length; count++) {
      double z = array[0][count][2];
      double y = array[0][count][1];
      array[0][count][2] = z * Math.cos(angleYZ) - y * Math.sin(angleYZ);
      array[0][count][1] = z * Math.sin(angleYZ) + y * Math.cos(angleYZ);
    }
  }

  public void mouseSpinner(double[][][] array) {
    if (mousePressed && !boolMouseWait) {
      intMouseXInitial = mouseX;
      intMouseYInitial = mouseY;
      dblXRotInitial = array[2][0][0];
      dblYRotInitial = array[2][0][2];
      array[2][1][0] = 0;
      array[2][1][2] = 0;
      boolMouseWait = true;
    }

    if (mousePressed && boolMouseWait) {
      if((dblYRotInitial < 90 && dblYRotInitial > -90) || (dblYRotInitial < -270)){
        setRotation(array, dblXRotInitial - (90.0 / (intScreenSize / 2)) * ((intMouseXInitial - mouseX)), 0,
          dblYRotInitial - (90.0 / (intScreenSize / 2)) * ((intMouseYInitial - mouseY)));
          array[2][1][0] = mouseX - array[2][2][0];
      }
      else{
        setRotation(array, dblXRotInitial + (90.0 / (intScreenSize / 2)) * ((intMouseXInitial - mouseX)), 0,
          dblYRotInitial - (90.0 / (intScreenSize / 2)) * ((intMouseYInitial - mouseY)));
          array[2][1][0] = array[2][2][0] - mouseX;
      }
      array[2][1][2] = mouseY - array[2][2][1];
      array[2][2][0] = mouseX;
      array[2][2][1] = mouseY;
    } 
    
    else {
      boolMouseWait = false;
      setRotation(array, array[2][0][0] + (90 * array[2][1][0]) / (intScreenSize / 2), 0,
          array[2][0][2] + (90 * array[2][1][2]) / (intScreenSize / 2));

      if (array[2][1][0] < 0) {
        array[2][1][0] += 0.4;
      } else if (array[2][1][0] > 0) {
        array[2][1][0] -= 0.4;
      }

      if (array[2][1][2] < 0) {
        array[2][1][2] += 0.4;
      } else if (array[2][1][2] > 0) {
        array[2][1][2] -= 0.4;
      }
    }
  }

  // you have no idea how long this one method took me
  public void drawFaces(double[][][] array) {
    // Checks the max z for the face
    for (int x = 0; x < array[4].length; x++) {
      double avZ = 0;
      for (int y = 0; y < array[4][x].length - 4; y++) {
        avZ += array[0][(int) array[4][x][y]][2];
      }
      array[4][x][4] = avZ / array[4].length;
    }

    // Sorts the 2Darray by the 5th element of each sub array
    Arrays.sort(array[4], (a, b) -> Double.compare(a[4], b[4]));

    // S
    for (int x = array[4].length - 1; x > -1; x--) {
      fill((int) array[4][x][array[4][x].length - 3], (int) array[4][x][array[4][x].length - 2], (int) array[4][x][array[4][x].length - 1]);
      beginShape();
      for (int y = 0; y < array[4][x].length - 4; y++) {
        vertex((float) array[5][(int) array[4][x][y]][0],
            (float) array[5][(int) array[4][x][y]][1]);
      }
      endShape();
    }
  }

}
