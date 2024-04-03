import processing.core.PApplet;

public class Cube extends PApplet {
    double xP1 = 300;
    double xP2 = 400;
    double yP1 = 300;
    double yP2 = 400;
    double[][] pointsList = {{xP1,yP1,0},{xP2,yP1,0},{xP2,yP2,0},{xP1,yP2,0},{xP1,yP1,100},{xP2,yP1,100},{xP2,yP2,100},{xP1,yP2,100}};
    int[][] edgeList = {{0,1},{1,2},{2,3},{3,0} ,{4,5},{5,6},{6,7},{7,4} ,{0,4},{1,5},{2,6},{3,7}};
    double[][] projectedPointList = {{100,100},{200,100},{100,100},{200,200},{100,100},{200,100},{100,100},{200,200}};
    double focalLength = 500;
    int screenSize = 800;
    public void settings() {
      size(screenSize, screenSize);
    }
    public void setup() {
      background(45, 150, 207);
      projectPoints();
    }
    public void draw() {
      drawLines();
    }
    public void projectPoints(){
      for (int x = 0; x < pointsList.length; x++){
        double z = pointsList[x][2];
        projectedPointList[x][0] = (z*screenSize + 2*focalLength*pointsList[x][0]) / (2*z + 2*focalLength);
        projectedPointList[x][1] = screenSize - (2*focalLength*screenSize + z*screenSize - 2*focalLength*pointsList[x][1]) / (2*z + 2*focalLength);
        System.out.println("x " + pointsList[x][0] + ", " + pointsList[x][2] + " = " + projectedPointList[x][0]);
        System.out.println("y " + pointsList[x][1] + ", " + pointsList[x][2] + " = " + projectedPointList[x][0]);
      }
    }
    public void drawLines(){
      for(int x = 0; x < edgeList.length; x++){
        line((int)projectedPointList[edgeList[x][0]][0],(int)projectedPointList[edgeList[x][0]][1],(int)projectedPointList[edgeList[x][1]][0],(int)projectedPointList[edgeList[x][1]][1]);
      }
    }
}
