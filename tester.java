import processing.core.PApplet;
import java.util.*;

/* While making this, i didnt relise that the box() function existed, so i didn't use it at all.
 * 
 * Actually, i didn't even relise java proccessing had 3d capabilities, so i made my own renderer.
 * 
 * Out of all the code here, only Arrays.sort(faceListStored, (a, b) -> Double.compare(a[4],b[4])); is taken from the internet
 * 
 * This is made for only a rubik's cube, but is made to be more general with minor changes, so a lot of the code uses variables that are not 
 * nessecary for this project, but would help if it were changed.
 *  
 * Enjoy the project
 * 
 * Author: @duocaleb
*/
public class tester extends PApplet {
  //initalize the variables... theres a lot of them
  double dblXP1 = -50, dblXP2 = 50;
  double dblYP1 = -50, dblYP2 = 50;
  double dblZP1 = -50, dblZP2 = 50;
  double dblYLineL = (dblYP1 - dblYP2) / 2, dblZLineL = (dblZP1 - dblZP2) / 2, dblXLineL = (dblXP1 - dblXP2) / 2;
  double dblFocalLength = 500;
  double[][] dblStoredPointsList = { { dblXLineL, dblYLineL, dblZLineL }, { -dblXLineL, dblYLineL, dblZLineL }, { -dblXLineL, -dblYLineL, dblZLineL },
      { dblXLineL, -dblYLineL, dblZLineL }, { dblXLineL, dblYLineL, -dblZLineL }, { -dblXLineL, dblYLineL, -dblZLineL },
      { -dblXLineL, -dblYLineL, -dblZLineL }, { dblXLineL, -dblYLineL, -dblZLineL }};
  double[][][] dblPointsList = {{ { dblXP1, dblYP1, dblZP1 }, { dblXP2, dblYP1, dblZP1 }, { dblXP2, dblYP2, dblZP1 }, { dblXP1, dblYP2, dblZP1 },
      { dblXP1, dblYP1, dblZP2 }, { dblXP2, dblYP1, dblZP2 }, { dblXP2, dblYP2, dblZP2 }, { dblXP1, dblYP2, dblZP2 },  {0,0,0}}, { { dblXLineL, dblYLineL, dblZLineL }, { -dblXLineL, dblYLineL, dblZLineL },
      { -dblXLineL, -dblYLineL, dblZLineL }, { dblXLineL, -dblYLineL, dblZLineL }, { dblXLineL, dblYLineL, -dblZLineL },
      { -dblXLineL, dblYLineL, -dblZLineL }, { -dblXLineL, -dblYLineL, -dblZLineL }, { dblXLineL, -dblYLineL, -dblZLineL } }, { { dblXLineL, dblYLineL, dblZLineL }, { -dblXLineL, dblYLineL, dblZLineL },
      { -dblXLineL, -dblYLineL, dblZLineL }, { dblXLineL, -dblYLineL, dblZLineL }, { dblXLineL, dblYLineL, -dblZLineL },
      { -dblXLineL, dblYLineL, -dblZLineL }, { -dblXLineL, -dblYLineL, -dblZLineL }, { dblXLineL, -dblYLineL, -dblZLineL }} };
  int[][] intEdgeList = { { 0, 1 }, { 1, 2 }, { 2, 3 }, { 3, 0 }, { 4, 5 }, { 5, 6 }, { 6, 7 }, { 7, 4 }, { 0, 4 },
      { 1, 5 }, { 2, 6 }, { 3, 7 } };

  /*
   * Very confusing array here, so heres what it means:
   * {Face1, Face2, Face3...}
   * {{Point1, Point2, Point3, Point4, ZStorage(for sorting), ColourV1, ColourV2,
   * ColourV3}, ect ect}
   * Points corespond to the points in storedPointsList.
   */
  double[][] dblFaceList = { { 0, 1, 2, 3, 0,  0,  70, 173 }, { 4, 5, 6, 7,   0,  0, 155, 72 },
      { 1, 5, 6, 2,  0,  255, 88, 0 }, { 0, 4, 7, 3,  0,  183, 18, 52 }, { 0, 1, 5, 4,  0,  255, 255, 255 },
      { 3, 7, 6, 2,  0,  255, 213, 0 } };

  double[][] dblProjectedPointList = { { 100, 100 }, { 200, 100 }, { 100, 100 }, { 200, 200 }, { 100, 100 }, { 200, 100 },
      { 100, 100 }, { 200, 200 } };
  boolean boolMouseWait = false;
  double dblXRotInitial = 0, dblYRotInitial = 0;
  int intMouseXInitial = 0, intMouseYInitial = 0;
  int intPrevMouseX = 0, intPrevMouseY = 0;
  int intMouseXSpeed = 0, intMouseYSpeed = 0;
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
    savePoints();
    resetRotation(dblPointsList[0]);
    mouseSpinner(dblPointsList[0]);
    resetRotation(dblPointsList[0]);
    projectPoints(dblPointsList[0], dblProjectedPointList);
    drawLines(intEdgeList, dblProjectedPointList);
    //drawFaces();
  }

  public void projectPoints(double[][] points, double[][] projectedPoints) {
    for (int x = 0; x < points.length - 1; x++) {
      double z = points[x][2];
      projectedPoints[x][0] = intScreenSize / 2 - (dblXP1 + dblXP2) / 2 + (points[x][0] * dblFocalLength) / (dblFocalLength + z);
      projectedPoints[x][1] = intScreenSize / 2 - (dblXP1 + dblXP2) / 2 - (points[x][1] * dblFocalLength) / (dblFocalLength + z);
    }
  }

  public void drawLines(int[][] edgeList, double[][] projectedPoints) {
    for (int x = 0; x < edgeList.length; x++) {
      line((int) projectedPoints[intEdgeList[x][0]][0], (int) projectedPoints[edgeList[x][0]][1],
          (int) projectedPoints[intEdgeList[x][1]][0], (int) projectedPoints[edgeList[x][1]][1]);
    }
    for (int x = 0; x < edgeList.length; x++) {
        line((int) projectedPoints[intEdgeList[x][0]][0], (int) projectedPoints[edgeList[x][0]][1],
            (int) projectedPoints[intEdgeList[x][1]][0], (int) projectedPoints[edgeList[x][1]][1]);
      }
  }

  public void savePoints() {
    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 3; y++) {
        dblStoredPointsList[x][y] = dblPointsList[1][x][y];
      }
    }
  }

  public void rotateAngles(double angleXZ, double angleXY, double angleYZ, double[][] array) {
    setRotation(dblPointsList[0], dblPointsList[1], dblPointsList[2], angleXZ + array[array.length][0], angleXY + array[array.length][1], angleYZ + array[array.length][2]);
  }

  public void resetRotation(double[][] array) {
    int lengthIndex = array.length - 1;
    //xz
    if (array[lengthIndex][0] >= 360 || array[lengthIndex][0] <= -360) {
        array[lengthIndex][0] = array[lengthIndex][0] % 360;
    }
    //xy
    if (array[lengthIndex][1] >= 360 || array[lengthIndex][1] <= -360) {
        array[lengthIndex][1] = array[lengthIndex][1] % 360;
    }
    //yz
    if (array[lengthIndex][2] >= 360 || array[lengthIndex][2] <= -360) {
        array[lengthIndex][2] = array[lengthIndex][2] % 360;
    }
  }

  public void setRotation(double[][] array, double[][] centeredArray, double[][] centeredArrayReseter, double angleXZ, double angleXY, double angleYZ) {
    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 3; y++) {
        centeredArray[x][y] = centeredArrayReseter[x][y];
      }
    }
    array[array.length - 1][0] = angleXZ;
    array[array.length - 1][1] = angleYZ;
    array[array.length - 1][2] = angleYZ;
    angleXZ = Math.toRadians(angleXZ);
    angleXY = Math.toRadians(angleXY);
    angleYZ = Math.toRadians(angleYZ);

    for (int count = 0; count < centeredArray.length; count++) {
      double x = centeredArray[count][0];
      double z = centeredArray[count][2];
      centeredArray[count][0] = x * Math.cos(angleXZ) - z * Math.sin(angleXZ);
      centeredArray[count][2] = x * Math.sin(angleXZ) + z * Math.cos(angleXZ);
    }

    for (int count = 0; count < centeredArray.length; count++) {
      double x = centeredArray[count][0];
      double y = centeredArray[count][1];
      centeredArray[count][0] = x * Math.cos(angleXY) - y * Math.sin(angleXY);
      centeredArray[count][1] = x * Math.sin(angleXY) + y * Math.cos(angleXY);
    }

    for (int count = 0; count < centeredArray.length; count++) {
      double z = centeredArray[count][2];
      double y = centeredArray[count][1];
      centeredArray[count][2] = z * Math.cos(angleYZ) - y * Math.sin(angleYZ);
      centeredArray[count][1] = z * Math.sin(angleYZ) + y * Math.cos(angleYZ);
    }

    for (int x = 0; x < centeredArray.length; x++) {
      array[x][0] += (centeredArray[x][0] - centeredArray[x][0]);
      array[x][1] += (centeredArray[x][1] - centeredArray[x][1]);
      array[x][2] += (centeredArray[x][2] - centeredArray[x][2]);
    }
  }

  public void mouseSpinner(double[][] array) {
    if (mousePressed && !boolMouseWait) {
      intMouseXInitial = mouseX;
      intMouseYInitial = mouseY;
      dblXRotInitial = array[array.length - 1][0];
      dblYRotInitial = array[array.length - 1][2];
      intMouseXSpeed = 0;
      intMouseYSpeed = 0;
      boolMouseWait = true;
    }

    if (mousePressed && boolMouseWait) {
      if((dblYRotInitial < 90 && dblYRotInitial > -90) || (dblYRotInitial < -270)){
        setRotation(dblPointsList[0], dblPointsList[1], dblPointsList[2], dblXRotInitial - (90.0 / (intScreenSize / 2)) * ((intMouseXInitial - mouseX)), 0,
          dblYRotInitial - (90.0 / (intScreenSize / 2)) * ((intMouseYInitial - mouseY)));
          intMouseXSpeed = mouseX - intPrevMouseX;
      }
      else{
        setRotation(dblPointsList[0], dblPointsList[1], dblPointsList[2], dblXRotInitial + (90.0 / (intScreenSize / 2)) * ((intMouseXInitial - mouseX)), 0,
          dblYRotInitial - (90.0 / (intScreenSize / 2)) * ((intMouseYInitial - mouseY)));
          intMouseXSpeed = intPrevMouseX - mouseX;
      }
      intMouseYSpeed = mouseY - intPrevMouseY;
      intPrevMouseX = mouseX;
      intPrevMouseY = mouseY;
    } 
    
    else {
      boolMouseWait = false;
      setRotation(dblPointsList[0], dblPointsList[1], dblPointsList[2], array[array.length - 1][0] + (90 * intMouseXSpeed) / (intScreenSize / 2), 0,
        array[array.length - 1][2] + (90 * intMouseYSpeed) / (intScreenSize / 2));

      if (intMouseXSpeed < 0) {
        intMouseXSpeed += 0.2;
      } else if (intMouseXSpeed > 0) {
        intMouseXSpeed -= 0.2;
      }

      if (intMouseYSpeed < 0) {
        intMouseYSpeed += 0.2;
      } else if (intMouseYSpeed > 0) {
        intMouseYSpeed -= 0.2;
      }
    }
  }

  // you have no idea how long this one method took me
  public void drawFaces() {
    // Checks the max z for the face
    for (int x = 0; x < 6; x++) {
      double maxZ = dblFaceList[x][0];
      double minZ = dblFaceList[x][0];
      for (int y = 0; y < 4; y++) {
        if (dblPointsList[1][(int) dblFaceList[x][y]][2] > maxZ) {
          maxZ = dblPointsList[1][(int) dblFaceList[x][y]][2];
        }
        if (dblPointsList[1][(int) dblFaceList[x][y]][2] < minZ) {
          minZ = dblPointsList[1][(int) dblFaceList[x][y]][2];
        }
      }
      dblFaceList[x][4] = (minZ + maxZ) / 2;
    }

    // Sorts the 2Darray by the 5th element of each sub array
    Arrays.sort(dblFaceList, (a, b) -> Double.compare(a[4], b[4]));

    // S
    for (int x = 5; x > -1; x--) {
      fill((int) dblFaceList[x][5], (int) dblFaceList[x][6], (int) dblFaceList[x][7]);
      beginShape();
      for (int y = 0; y < 4; y++) {
        vertex((float) dblProjectedPointList[(int) dblFaceList[x][y]][0],
            (float) dblProjectedPointList[(int) dblFaceList[x][y]][1]);
      }
      endShape();
    }
  }

}
