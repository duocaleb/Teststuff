import processing.core.PApplet;

public class Square extends PApplet {
  int screenSize = 600;
  double size = 300;
  double x1 = (screenSize+size)/2;
  double y1 = (screenSize+size)/2;
  double x2 = x1 - size;
  double y2 = y1 - size;
  double circleR = 2*Math.pow(size/2, 2);
  double[] pointX = {x1,x2,x2,x1 ,x1};
  double[] pointY = {y1,y1,y2,y2 ,y1};
  double xAxis = (x1+x2)/2; // misnamed(this is the y axis), im too lazy to change them all
  double yAxis = (y1+y2)/2; // misnamed(this is the x axis), same as the last one
  /**
   * Called once at the beginning of execution, put your size all in this method
   */
  public void settings() {
	// put your size call here
    size(screenSize, screenSize);
  }

  /** 
   * Called once at the beginning of execution.  Add initial set up
   * values here i.e background, stroke, fill etc.
   */
  public void setup() {
    background(45, 150, 207);
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {
    clear();
    background(45, 150, 207);
    line(0,(int)yAxis,800,(int)yAxis); // this is the x axis
    line((int)xAxis,0,(int)xAxis,800); // this is the y axis
    editPointsCircle();
    drawCube();
  }

  public void editPointsCircle(){
    double speed = 1;
    for(int x = 0; x < 5; x++){
      /**
       * checks if the x or y should be changing depending on the X(if only x or only y move, the speed changes cause of circles and it warps)
       * once checked, it changes said co-ordinate by the speed, then alters the other based on a circle equation.
       * 4 different equations are used because it is square rooted instead of squaring the y as would happen in a normal circle.
       * 
       */
      if(pointX[x] <= x1 && pointX[x] >= x2){
        if (pointX[x] <= xAxis && pointY[x] <= yAxis){
          pointX[x] -= speed;
          pointY[x] = -Math.sqrt(circleR-Math.pow((pointX[x]-xAxis),2)) + yAxis;
        }
        else if(pointX[x] <= xAxis && pointY[x] >= yAxis){
          pointX[x] += speed;
          pointY[x] = Math.sqrt(circleR-Math.pow((pointX[x]-xAxis),2)) + yAxis;
        }
        else if(pointX[x] >= xAxis && pointY[x] >= yAxis){
          pointX[x] += speed;
          pointY[x] = Math.sqrt(circleR-Math.pow((pointX[x]-xAxis),2)) + yAxis;
        }
        else if(pointX[x] >= xAxis && pointY[x] <= yAxis){
          pointX[x] -= speed;
          pointY[x] = -Math.sqrt(circleR-Math.pow((pointX[x]-xAxis),2)) + yAxis;
        }
      }
      else{
        if (pointX[x] < xAxis && pointY[x] <= yAxis){
          pointY[x] += speed;
          pointX[x] = -Math.sqrt(circleR-Math.pow((pointY[x]-yAxis),2)) + xAxis;
        }
        else if(pointX[x] < xAxis && pointY[x] > yAxis){
          pointY[x] += speed;
          pointX[x] = -Math.sqrt(circleR-Math.pow((pointY[x]-yAxis),2)) + xAxis;
        }
        else if(pointX[x] > xAxis && pointY[x] >= yAxis){
          pointY[x] -= speed;
          pointX[x] = Math.sqrt(circleR-Math.pow((pointY[x]-yAxis),2)) + xAxis;
        }
        else if(pointX[x] > xAxis && pointY[x] < yAxis){
          pointY[x] -= speed;
          pointX[x] = Math.sqrt(circleR-Math.pow((pointY[x]-yAxis),2)) + xAxis;
        }
      }
    }
  }
  public void drawCube(){
    for (int x = 0; x < 4; x++){
      line((int)pointX[x], (int)pointY[x], (int)pointX[x+1], (int)pointY[x+1]);
    }
  }
} 
