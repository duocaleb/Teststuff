import processing.core.PApplet;
import java.util.*;
public class tester extends PApplet {
  float[][] vertices;
  int[][] edges;
  float angleX = 0;
  float angleY = 0;
  float angleZ = 0;
  public void settings(){
    size(800, 600);
  }
  public void setup() {
    
    
    // Define the vertices of the cube
    vertices = new float[][] {
      {-50, -50, -50},
      {50, -50, -50},
      {50, 50, -50},
      {-50, 50, -50},
      {-50, -50, 50},
      {50, -50, 50},
      {50, 50, 50},
      {-50, 50, 50}
    };
    
    // Define the edges of the cube
    edges = new int[][] {
      {0, 1}, {1, 2}, {2, 3}, {3, 0},
      {4, 5}, {5, 6}, {6, 7}, {7, 4},
      {0, 4}, {1, 5}, {2, 6}, {3, 7}
    };
  }
  
  public void draw() {
    background(255);
    translate(width/2, height/2);
    
    // Rotate the cube around the camera
    rotateCube(angleX, angleY, angleZ);
    
    // Draw the edges of the cube
    for (int[] edge : edges) {
      int v1 = edge[0];
      int v2 = edge[1];
      float[] start = project(vertices[v1]);
      float[] end = project(vertices[v2]);
      line(start[0], start[1], end[0], end[1]);
    }
    
    // Update rotation angles for animation
    angleX = (float)0.01;
    angleY = (float)0.01;
  }
  
  // Rotate the cube around the camera
  void rotateCube(float x, float y, float z) {
    float cameraX = 1000;
    float cameraY = 0;
    float cameraZ = -1000; // Adjust this value to change the camera distance from the cube
    
    for (int i = 0; i < vertices.length; i++) {
      float[] vertex = vertices[i];
      float x0 = vertex[0] - cameraX;
      float y0 = vertex[1] - cameraY;
      float z0 = vertex[2] - cameraZ;
      
      // Rotate around X axis
      float y1 = y0 * cos(x) - z0 * sin(x);
      float z1 = y0 * sin(x) + z0 * cos(x);
      y0 = y1;
      z0 = z1;
      
      // Rotate around Y axis
      float x1 = x0 * cos(y) + z0 * sin(y);
      z1 = -x0 * sin(y) + z0 * cos(y);
      x0 = x1;
      z0 = z1;
      
      // Rotate around Z axis
      x1 = x0 * cos(z) - y0 * sin(z);
      y1 = x0 * sin(z) + y0 * cos(z);
      x0 = x1;
      y0 = y1;
      
      vertices[i][0] = x0 + cameraX;
      vertices[i][1] = y0 + cameraY;
      vertices[i][2] = z0 + cameraZ;
    }
  }
  
  // Project a 3D point onto a 2D canvas
  float[] project(float[] vertex) {
    float fov = PI/3;
    float cameraZ = (float)(height / Math.tan(fov / 2.0));
    float aspect = (float)(width) / (float)(height);
    float[] projected = new float[2];
    projected[0] = vertex[0] * cameraZ / (vertex[2] + cameraZ)* aspect;
    projected[1] = vertex[1] * cameraZ / (vertex[2] + cameraZ);
    return projected;
  }
  
}
