import processing.core.PApplet;
import java.awt.AWTException;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.List;   


/* 
 *  
 * The purpose of this program is to use 2d processing to make a 3d game
 * No 3d methods are used throughout the project.
 * 
 * Author: @duocaleb
*/
public class Rendering extends PApplet {
  //initalizing the variables and the arrays
  double dblFocalLength = 100;
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
  int x = 100;
  int y = 100;
  List<Triangle3D> TriangleList3D = new ArrayList<>();
  List<Triangle2D> TriangleList2D = new ArrayList<>();
  float[] zBuffer = new float[intScreenSize*intScreenSize];
  Robot mouseMover;

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
    TriangleList2D.clear();
    TriangleList3D.clear();
    for(int x = 0; x < zBuffer.length; x++){
      zBuffer[x] =  Float.POSITIVE_INFINITY;
    }
    clear();
    background(45, 150, 207);
    cursorMovement();
    moveChar();
    addCube(new Point3D(-100, -100, 300), 
            new Point3D(100, 100, 500), 
            color(0,0,255), 
            color(255,255,255), 
            color(255,255,0), 
            color(0,128,0),
            color(255,165,0),
            color(255,0,0),
            new Point3D(-mouseRotationX,0,-mouseRotationY));
    drawFaces();
    //projectPoints();
    //drawLines(); // For testing purposes only
    rotate++;
  }



  public void rotateAroundCamera(){

  }

  public void increaseX(){

  }

  public void drawLines(){
    float a = intScreenSize/2;
    for(int x = 0; x < TriangleList2D.size(); x++){
      float x1 = a+(float)TriangleList2D.get(x).p1.x;
      float x2 = a+(float)TriangleList2D.get(x).p2.x;
      float x3 = a+(float)TriangleList2D.get(x).p3.x;
      float y1 = a-(float)TriangleList2D.get(x).p1.y;
      float y2 = a-(float)TriangleList2D.get(x).p2.y;
      float y3 = a-(float)TriangleList2D.get(x).p3.y;
      line(x1,y1,x2,y2);
      line(x2,y2,x3,y3);
      line(x3,y3,x1,y1);
    }
  }

  public void addCube(Point3D minPoint, Point3D maxPoint, int colour1, int colour2, int colour3, int colour4, int colour5, int colour6, Point3D Rotation){
    Point3D[] PointList = new Point3D[8];
    int[] colourList = new int[6];
    for(int x = 0; x < 8; x++){
      // Defining a new point cause it doesnt work otherwise
      PointList[x] = new Point3D(0,0,0);
    }
    int[][] Connections = {{0,1,2}, {1,2,3}, {2,3,7}, {2,6,7}, {1,3,7}, {1,5,7}, {4,5,7}, {4,6,7}, {0,4,6}, {0,2,6}, {0,1,5}, {0,4,5}};
    colourList[0] = colour1;
    colourList[1] = colour2;
    colourList[2] = colour3;
    colourList[3] = colour4;
    colourList[4] = colour5;
    colourList[5] = colour6;
    double minX = minPoint.x;
    double minY = minPoint.y;
    double minZ = minPoint.z;
    double maxX = maxPoint.x;
    double maxY = maxPoint.y;
    double maxZ = maxPoint.z;

    for(int x = 0; x < 4; x += 1){
      PointList[x*2].x = minX;
      PointList[x*2+1].x = maxX;
      PointList[(int)Math.floor(x/2)*2+x].y = minY;
      PointList[(int)Math.floor(x/2)*2+x+2].y = maxY;
      PointList[x].z = minZ;
      PointList[x+4].z = maxZ;
    }
    for(int x = 0; x < 8; x++){
      rotatePoint(PointList[x], Rotation.x, Rotation.y, Rotation.z);
    }
    for(int a = 0; a < 12; a++){
      TriangleList3D.add(new Triangle3D(PointList[Connections[a][0]], PointList[Connections[a][1]], PointList[Connections[a][2]],colourList[(int)a/2]));
    }
  }

  public void addCube(Point3D minPoint, Point3D maxPoint, int colour, Point3D Rotation){
    Point3D[] PointList = new Point3D[8];
    for(int x = 0; x < 8; x++){
      // Defining a new point cause it doesnt work otherwise
      PointList[x] = new Point3D(0,0,0);
    }
    int[][] Connections = {{0,1,2}, {1,2,3}, {2,3,7}, {2,6,7}, {1,3,7}, {1,5,7}, {4,5,7}, {4,6,7}, {0,4,6}, {0,2,6}, {0,1,5}, {0,4,5}};
    double minX = minPoint.x;
    double minY = minPoint.y;
    double minZ = minPoint.z;
    double maxX = maxPoint.x;
    double maxY = maxPoint.y;
    double maxZ = maxPoint.z;

    for(int x = 0; x < 4; x += 1){
      PointList[x*2].x = minX;
      PointList[x*2+1].x = maxX;
      PointList[(int)Math.floor(x/2)*2+x].y = minY;
      PointList[(int)Math.floor(x/2)*2+x+2].y = maxY;
      PointList[x].z = minZ;
      PointList[x+4].z = maxZ;
    }
    for(int x = 0; x < 8; x++){
      rotatePoint(PointList[x], Rotation.x, Rotation.y, Rotation.z);
    }
    for(int a = 0; a < 12; a++){
      TriangleList3D.add(new Triangle3D(PointList[Connections[a][0]], PointList[Connections[a][1]], PointList[Connections[a][2]],colour));
    }
  }

  public void cursorMovement(){
    if(mouseCenteredX == 0 && mouseCenteredY == 0){
      mouseCenteredX = mouseX;
      mouseCenteredY = mouseY;
    }
    mouseRotationX += (mouseSensitivity*(mouseX - mouseCenteredX))/(intScreenSize/2);
    mouseRotationY += (mouseSensitivity*(mouseY - mouseCenteredY))/(intScreenSize/2);
    if(mouseRotationX >= 360){
      mouseRotationX %= -360;
    }
    if(mouseRotationX <= -360){
      mouseRotationX %= 360;
    }
    if(mouseRotationY >= 180){
      mouseRotationY = 180;
    }
    if(mouseRotationY <= -180){
      mouseRotationY = -180;
    }
    mouseMover.mouseMove(displayWidth/2, displayHeight/2);
  }

  public void keyPressed() {
    if(key != 65535){
      isKeyPressed[(int)key] = true;
    }
  }
  
  public void keyReleased() {
    if(key != 65535){
      isKeyPressed[(int)key] = false;
    }
  }

  public void moveChar() {
    double rotA = Math.toRadians(mouseRotationY);
    double rotB = Math.toRadians(mouseRotationX);
    double a = Math.toRadians(90);
    if(isKeyPressed[(int)'w']){
      cameraZ += 2*Math.cos(rotA)*Math.cos(rotB);
      cameraX += 2*Math.sin(rotA+a)*Math.cos(rotB+a);
      cameraY += 2*Math.sin(rotA);
    }
    if(isKeyPressed[(int)'s']){
      cameraZ -= 2*Math.cos(rotA)*Math.cos(rotB);
      cameraX -= 2*Math.sin(rotA+a)*Math.cos(rotB+a);
      cameraY -= 2*Math.sin(rotA);
    }
    if(isKeyPressed[(int)'a']){
      cameraZ -= 2*Math.cos(rotA)*Math.cos(rotB+a);
      cameraX += 2*Math.sin(rotA+a)*Math.cos(rotB);

    }
    if(isKeyPressed[(int)'d']){
      cameraZ += 2*Math.cos(rotA)*Math.cos(rotB+a);
      cameraX -= 2*Math.sin(rotA+a)*Math.cos(rotB);
    }
    
  }

  public void rotateAroundCamera(double[][][] array, double angleToTurnX, double angleToTurnY){
    
  }

  /*
   * Project the points onto the screen.
   */
  public void projectPoints() {
    for (int a = 0; a < TriangleList3D.size(); a++) {
      double[] z = {TriangleList3D.get(a).p1.z, TriangleList3D.get(a).p2.z, TriangleList3D.get(a).p3.z};
      double[] x = {TriangleList3D.get(a).p1.x, TriangleList3D.get(a).p2.x, TriangleList3D.get(a).p3.x};
      double[] y = {TriangleList3D.get(a).p1.y, TriangleList3D.get(a).p2.y, TriangleList3D.get(a).p3.y};
      int pOut = 0;
      for(int count = 0; count < 3; count++){
        if(x[count] > ((intScreenSize*(z[count]-cameraZ))/(2*dblFocalLength)) || y[count] > ((intScreenSize*(z[count]-cameraZ))/(2*dblFocalLength)) || x[count] < -((intScreenSize*(z[count]-cameraZ))/(2*dblFocalLength)) || y[count] < -((intScreenSize*(z[count]-cameraZ))/(2*dblFocalLength))){
          pOut++;
        }
        //Implement real projection later
        x[count] = -(((x[count]-cameraX) * (dblFocalLength)) / ((z[count]-cameraZ)) + x[count] - cameraX);
        // Got this from -(x[count]-cameraX)*(dblFocalLength+z[count]-cameraZ)/(z[count]-cameraZ). 
        // Obviously wrong cause its clear this aproaches a value and doesnt get infinitly small as the camera gets further away.
        y[count] = -(((y[count]-cameraY) * (dblFocalLength)) / ((z[count]-cameraZ)) + y[count] - cameraY);
      }
      //Implement real culling later
      if(pOut < 1){
        TriangleList2D.add(new Triangle2D(new Point2D(x[0], y[0]), new Point2D(x[1],y[1]), new Point2D(x[2],y[2]), TriangleList3D.get(a).c));
      }
    }
  }

  public void rotatePoint(Point3D P, double angleXZ, double angleXY, double angleYZ) {
    angleXZ = Math.toRadians(angleXZ);
    angleXY = Math.toRadians(angleXY);
    angleYZ = Math.toRadians(angleYZ);
    double x;
    double z;
    double y;
    x = P.x - cameraX;
    z = P.z - cameraZ;
    P.x = x * Math.cos(angleXZ) - z * Math.sin(angleXZ) + cameraX;
    P.z = x * Math.sin(angleXZ) + z * Math.cos(angleXZ) + cameraZ;
    x = P.x - cameraX;
    y = P.y - cameraY;
    P.x = x * Math.cos(angleXY) - y * Math.sin(angleXY) + cameraX;
    P.y = x * Math.sin(angleXY) + y * Math.cos(angleXY) + cameraY;
    z = P.z - cameraZ;
    y = P.y - cameraY;
    P.z = z * Math.cos(angleYZ) - y * Math.sin(angleYZ) + cameraZ;
    P.y = z * Math.sin(angleYZ) + y * Math.cos(angleYZ) + cameraY;
  }

  /*public void rotate3dShapeCenter(int startIndex, int endIndex, Point3D Rotation) {
    double angleXZ = Math.toRadians(Rotation.x);
    double angleXY = Math.toRadians(Rotation.y);
    double angleYZ = Math.toRadians(Rotation.z);
    double xAv = 0;
    double yAv = 0;
    double zAv = 0;
    for(int x = startIndex; x < endIndex; x++){
      xAv += TriangleList3D.get(x).p1.x;
      xAv += TriangleList3D.get(x).p2.x;
      xAv += TriangleList3D.get(x).p3.x;
      yAv += TriangleList3D.get(x).p1.y;
      yAv += TriangleList3D.get(x).p2.y;
      yAv += TriangleList3D.get(x).p3.y;
      zAv += TriangleList3D.get(x).p1.z;
      zAv += TriangleList3D.get(x).p2.z;
      zAv += TriangleList3D.get(x).p3.z;
    }
    xAv = xAv/(endIndex-startIndex);
    yAv = yAv/(endIndex-startIndex);
    xAv = xAv/(endIndex-startIndex);
    double x;
    double z;
    double y;
    x = P.x;
    z = P.z;
    P.x = x * Math.cos(angleXZ) - z * Math.sin(angleXZ);
    P.z = x * Math.sin(angleXZ) + z * Math.cos(angleXZ);
    x = P.x;
    y = P.y;
    P.x = x * Math.cos(angleXY) - y * Math.sin(angleXY);
    P.y = x * Math.sin(angleXY) + y * Math.cos(angleXY);
    z = P.z;
    y = P.y;
    P.z = z * Math.cos(angleYZ) - y * Math.sin(angleYZ);
    P.y = z * Math.sin(angleYZ) + y * Math.cos(angleYZ);
  }*/

  public double area(int x1, int y1, int x2, int y2, int x3, int y3){
    return Math.abs((x1*(y2-y3) + x2*(y3-y1)+x3*(y1-y2))/2.0);
  }

  public double[] calculateBarycentricCoordinates(Point2D point, Point2D[] vertices) {
    double[] baryCoords = new double[3];
    double totalArea = area((int)vertices[0].x, (int)vertices[0].y, (int)vertices[1].x, (int)vertices[1].y, (int)vertices[2].x, (int)vertices[2].y);
    baryCoords[0] = area((int)point.x, (int)point.y, (int)vertices[1].x, (int)vertices[1].y, (int)vertices[2].x, (int)vertices[2].y) / totalArea;
    baryCoords[1] = area((int)vertices[0].x, (int)vertices[0].y, (int)point.x, (int)point.y, (int)vertices[2].x, (int)vertices[2].y) / totalArea;
    baryCoords[2] = area((int)vertices[0].x, (int)vertices[0].y, (int)vertices[1].x, (int)vertices[1].y, (int)point.x, (int)point.y) / totalArea;

    return baryCoords;
  }

public double interpolateDepth(Point2D point, Point2D[] vertices, double[] depths) {
  double[] baryCoords = calculateBarycentricCoordinates(point, vertices);
  double interpolatedDepth = baryCoords[0] * depths[0] + 
                             baryCoords[1] * depths[1] +
                             baryCoords[2] * depths[2];

  return interpolatedDepth;
}

  public void drawFaces(){
    // This took me over a month + reading more than 20 articles on 3d rendering, asking reddit, looking through wikipedia and looking thorugh blogs just to understand how this works and how to implement it.
    projectPoints();
    for(int r = 0; r < TriangleList2D.size(); r++){
      Point2D p1 = TriangleList2D.get(r).p1;
      Point2D p2 = TriangleList2D.get(r).p2;
      Point2D p3 = TriangleList2D.get(r).p3;
      int x1 = (int)p1.x;
      int x2 = (int)p2.x;
      int x3 = (int)p3.x;
      int y1 = (int)p1.y;
      int y2 = (int)p2.y;
      int y3 = (int)p3.y;
      double A = area (x1, y1, x2, y2, x3, y3);
      Point2D[] pointsList = {p1,p2,p3};
      double[] depths = {TriangleList3D.get(r).p1.z,TriangleList3D.get(r).p2.z,TriangleList3D.get(r).p3.z};
      int yMin = (int)Math.min(Math.min(p1.y,p2.y),p3.y);
      int yMax = (int)Math.max(Math.max(p1.y, p2.y), p3.y);
      int xMin = (int)Math.min(Math.min(p1.x, p2.x),p3.x);
      int xMax = (int)Math.max(Math.max(p1.x, p2.x), p3.x);
      int scDiv2 = intScreenSize/2;
      if(xMax >= scDiv2){
        xMax = scDiv2;
      }
      if(xMin <= -scDiv2){
        xMin = -scDiv2;
      }
      if(yMax >= scDiv2-1){
        yMax = scDiv2-1;
      }
      if(yMin <= (-scDiv2)+1){
        yMin = (-scDiv2)+1;
      }
      // proud to say i came up with the bounding box without looking it up(it's pretty much the only concept in this method i didnt look up)
      for(int x = xMin; x < xMax; x++){
        for(int y = yMin; y < yMax; y++){
          double A1 = area (x, y, x2, y2, x3, y3);
          double A2 = area (x1, y1, x, y, x3, y3);
          double A3 = area (x1, y1, x2, y2, x, y);
          if(A == A1 + A2 + A3){
            double z = interpolateDepth(new Point2D(x, y), pointsList, depths );
            if((z-cameraZ) > 0 && z < zBuffer[((scDiv2-y)-1)*intScreenSize+(intScreenSize/2+x)]){
              zBuffer[((scDiv2-y)-1)*intScreenSize+(scDiv2+x)] = (float)z;
              set(scDiv2 + x, scDiv2 - y, TriangleList2D.get(r).c);
            }
          }
        }
      }
    }
  }
}
