import processing.core.PApplet;

public class CubeTest extends PApplet {
    double xP1 = -50;
    double xP2 = 50;
    double yP1 = -50;
    double yP2 = 50;
    double[][] pointsList = {{xP1,yP1,0},{xP2,yP1,0},{xP2,yP2,0},{xP1,yP2,0},{xP1,yP1,-100},{xP2,yP1,-100},{xP2,yP2,-100},{xP1,yP2,-100}};
    int[][] edgeList = {{0,1},{1,2},{2,3},{3,0} ,{4,5},{5,6},{6,7},{7,4} ,{0,4},{1,5},{2,6},{3,7}};
    double[][] projectedPointList = {{100,100},{200,100},{100,100},{200,200},{100,100},{200,100},{100,100},{200,200}};
    double focalLength = 300;
    int screenSize = 800;
    public void settings() {
      size(screenSize, screenSize);
    }
    public void setup() {
      background(45, 150, 207);
      
    }
    public void draw() {
        clear();
        background(45, 150, 207);
        rotate(0.5);
        projectPoints();
        drawLines();
    }
    public void projectPoints(){
      for (int x = 0; x < pointsList.length; x++){
        double z = pointsList[x][2];
        projectedPointList[x][0] = screenSize/2 + (pointsList[x][0]*focalLength) / (focalLength+z);
        projectedPointList[x][1] = screenSize/2 - (pointsList[x][1]*focalLength) / (focalLength+z);
      }
    }
    public void drawLines(){
      for(int x = 0; x < edgeList.length; x++){
        line((int)projectedPointList[ edgeList[x][0] ][0],(int)projectedPointList[edgeList[x][0]][1], (int)projectedPointList[edgeList[x][1]][0], (int)projectedPointList[edgeList[x][1]][1]);
      }
    }
    public void rotate(double angle){
        angle = Math.toRadians(angle);
        for (int count = 0; count < pointsList.length; count++){
            double x = pointsList[count][0];
            double z = pointsList[count][2];
            pointsList[count][0] = x*Math.cos(angle) - z*Math.sin(angle);
            pointsList[count][2] = x*Math.sin(angle) + z*Math.cos(angle);
        }
        for (int count = 0; count < pointsList.length; count++){
            double x = pointsList[count][0];
            double y = pointsList[count][1];
            pointsList[count][0] = x*Math.cos(angle) - y*Math.sin(angle);
            pointsList[count][1] = x*Math.sin(angle) + y*Math.cos(angle);
        }
        for (int count = 0; count < pointsList.length; count++){
            double z = pointsList[count][2];
            double y = pointsList[count][1];
            pointsList[count][2] = z*Math.cos(angle) - y*Math.sin(angle);
            pointsList[count][1] = z*Math.sin(angle) + y*Math.cos(angle);
        }
    }
}
