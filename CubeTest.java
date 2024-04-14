import processing.core.PApplet;

public class CubeTest extends PApplet {
    double xP1 = -50;
    double xP2 = 50;
    double yP1 = -50;
    double yP2 = 50;
    double zP1 = -50;
    double zP2 = 50;
    double xLineL = (xP1-xP2)/2;
    double yLineL = (yP1-yP2)/2;
    double zLineL = (zP1-zP2)/2;
    double xyRotated = 0;
    double yzRotated = 0;
    double xzRotated = 0;
    double [][] centeredPointsList = {{xLineL,yLineL,zLineL},{-xLineL,yLineL,zLineL},{-xLineL,-yLineL,zLineL},{xLineL,-yLineL,zLineL},{xLineL,yLineL,-zLineL},{-xLineL,yLineL,-zLineL},{-xLineL,-yLineL,-zLineL},{xLineL,-yLineL,-zLineL}};
    double [][] centeredPointsListReseter = {{xLineL,yLineL,zLineL},{-xLineL,yLineL,zLineL},{-xLineL,-yLineL,zLineL},{xLineL,-yLineL,zLineL},{xLineL,yLineL,-zLineL},{-xLineL,yLineL,-zLineL},{-xLineL,-yLineL,-zLineL},{xLineL,-yLineL,-zLineL}};
    double[][] storedPointsList = {{xLineL,yLineL,zLineL},{-xLineL,yLineL,zLineL},{-xLineL,-yLineL,zLineL},{xLineL,-yLineL,zLineL},{xLineL,yLineL,-zLineL},{-xLineL,yLineL,-zLineL},{-xLineL,-yLineL,-zLineL},{xLineL,-yLineL,-zLineL}};
    double[][] pointsList = {{xP1,yP1,zP1},{xP2,yP1,zP1},{xP2,yP2,zP1},{xP1,yP2,zP1},{xP1,yP1,zP2},{xP2,yP1,zP2},{xP2,yP2,zP2},{xP1,yP2,zP2}};
    int[][] edgeList = {{0,1},{1,2},{2,3},{3,0} ,{4,5},{5,6},{6,7},{7,4} ,{0,4},{1,5},{2,6},{3,7}};
    double[][] projectedPointList = {{100,100},{200,100},{100,100},{200,200},{100,100},{200,100},{100,100},{200,200}};
    double focalLength = 500;
    int screenSize = 400;
    boolean zoomout = true; 
    boolean mouseWait = false;
    double xVel = 1;
    double yVel = 0;
    int waitAfterBounce = 0;
    boolean stop = false;
    int mouseXInitial = 0;
    int mouseYInitial = 0;
    double xRotInitial = 0;
    double yRotInitial = 0;
    int prevMouseX = 0;
    int mouseXSpeed = 0;
    int prevMouseY = 0;
    int mouseYSpeed = 0;
    public void settings() {
      size(screenSize, screenSize);
    } 


    public void setup() {
      background(45, 150, 207);
      projectPoints();
    }


    public void draw() {
        clear();
        background(45, 150, 207);
        savePoints();
        resetRotation();
        //bounce(0.2,0.61);
        rotateAngles(0,0, 0);
        mouseSpinner();
        resetRotation();
        projectPoints(); 
        drawLines();
    }


    public void projectPoints(){
      for (int x = 0; x < pointsList.length; x++){
        double z = pointsList[x][2];
        projectedPointList[x][0] = screenSize/2 - (xP1+xP2)/2 + (pointsList[x][0]*focalLength) / (focalLength+z);
        projectedPointList[x][1] = screenSize/2 - (xP1+xP2)/2 - (pointsList[x][1]*focalLength) / (focalLength+z);
      }
    }


    public void drawLines(){
      for(int x = 0; x < edgeList.length; x++){
        line((int)projectedPointList[ edgeList[x][0] ][0],(int)projectedPointList[edgeList[x][0]][1], (int)projectedPointList[edgeList[x][1]][0], (int)projectedPointList[edgeList[x][1]][1]);
      }
    }


    public void savePoints(){
      for(int x = 0; x < 8; x++){
        for(int y = 0; y < 3; y++){
          storedPointsList[x][y] = centeredPointsList[x][y];
        }
      }
    }


    public void rotateAngles(double angleXZ, double angleXY, double angleYZ){
        setRotation(angleXZ + xzRotated, angleXY + xyRotated, angleYZ + yzRotated);
    }
    public void resetRotation(){
      if (xzRotated >= 360 || xzRotated <= -360){
        xzRotated = xzRotated%360;
      }
      if (yzRotated >= 360 || yzRotated <= -360){
        yzRotated = yzRotated%360;
      }
      if (xyRotated >= 360 || xyRotated <= -360){
        xyRotated = xyRotated%360;
      }
    }


    public void setRotation(double angleXZ, double angleXY, double angleYZ){
      for (int x = 0; x < 8; x++){
        for (int y = 0; y < 3; y++){
          centeredPointsList[x][y] = centeredPointsListReseter[x][y];
        }
      }

      yzRotated = angleYZ;
      xyRotated = angleXY;
      xzRotated = angleXZ;
      angleXZ = Math.toRadians(angleXZ);
      angleXY = Math.toRadians(angleXY);
      angleYZ = Math.toRadians(angleYZ);

      for (int count = 0; count < centeredPointsList.length; count++){
          double x = centeredPointsList[count][0];
          double z = centeredPointsList[count][2];
          centeredPointsList[count][0] = x*Math.cos(angleXZ) - z*Math.sin(angleXZ);
          centeredPointsList[count][2] = x*Math.sin(angleXZ) + z*Math.cos(angleXZ);
      }

      for (int count = 0; count < centeredPointsList.length; count++){
          double x = centeredPointsList[count][0];
          double y = centeredPointsList[count][1];
          centeredPointsList[count][0] = x*Math.cos(angleXY) - y*Math.sin(angleXY);
          centeredPointsList[count][1] = x*Math.sin(angleXY) + y*Math.cos(angleXY);
      }

      for (int count = 0; count < centeredPointsList.length; count++){
          double z = centeredPointsList[count][2];
          double y = centeredPointsList[count][1];
          centeredPointsList[count][2] = z*Math.cos(angleYZ) - y*Math.sin(angleYZ);
          centeredPointsList[count][1] = z*Math.sin(angleYZ) + y*Math.cos(angleYZ);
      }

      for (int x = 0; x < projectedPointList.length; x++){
        pointsList[x][0] += (centeredPointsList[x][0] - storedPointsList[x][0]);
        pointsList[x][1] += (centeredPointsList[x][1] - storedPointsList[x][1]);
        pointsList[x][2] += (centeredPointsList[x][2] - storedPointsList[x][2]);
      }
    }


    public void bounce(double gravity, double bounciness){
      if(stop != true){
        for(int x = 0; x < 8; x++){
          if(pointsList[x][1] <= -screenSize/2 && waitAfterBounce == 0 && yVel < -gravity && stop == false){
            yVel = yVel * -bounciness;
            waitAfterBounce = 10;
          } 

          else if (pointsList[x][1] > -screenSize/2 || yVel >= -gravity && stop == false){
            yVel -= gravity/8;
          }

          else{
            yVel = 0;
            stop = true;
          }
        }
      }
      else if (yVel != 0){
        yVel = 0;
      }

      for(int x = 0; x < 8; x++){
        pointsList[x][1] += yVel;
      }

      if (waitAfterBounce > 0){
        waitAfterBounce -= 1;
      }
  }


  public void mouseSpinner(){
    if (mousePressed && mouseWait == false){
      mouseXInitial = mouseX;
      mouseYInitial = mouseY;
      xRotInitial = xzRotated;
      yRotInitial = yzRotated;
      mouseXSpeed = 0;
      mouseYSpeed = 0;
      mouseWait = true;
    }

    if (mousePressed && mouseWait){
      setRotation(xRotInitial-(90.0/(screenSize/2))*((mouseXInitial-mouseX)), 0, yRotInitial-(90.0/(screenSize/2))*((mouseYInitial-mouseY)));
      mouseXSpeed = mouseX - prevMouseX;
      mouseYSpeed = mouseY - prevMouseY;
      prevMouseX = mouseX;
      prevMouseY = mouseY;
    }
    else {
      mouseWait = false;
      setRotation(xzRotated+(90*mouseXSpeed)/(screenSize/2), 0, yzRotated+(90*mouseYSpeed)/(screenSize/2));

      if (mouseXSpeed < 0){
        mouseXSpeed += 0.2;
      }
      else if (mouseXSpeed > 0){
        mouseXSpeed -= 0.2;
      }

      if (mouseYSpeed < 0){
        mouseYSpeed += 0.2;
      }
      else if (mouseYSpeed > 0){
        mouseYSpeed -= 0.2;
      }
    }
  }

}
