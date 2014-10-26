import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class AsteroidsGame extends PApplet {

//your variable declarations here
SpaceShip USSS;
public void setup() 
{
  size(500,500);
  USSS =new SpaceShip();
  USSS.setX(250);
  USSS.setY(250);

}
public void draw() 
{
  background(0);
  USSS.move();
  USSS.show();
}
public void keyPressed()
{
  if(key=='w') {USSS.accelerate(1);}
  if(key=='a') {USSS.rotate(-10);}
  if(key=='d') {USSS.rotate(10);}
}
class SpaceShip extends Floater  
{   
    int k=1;
    SpaceShip()
    {
      corners=24;
      myColor=color(255);
      xCorners=new int[corners];
      yCorners=new int[corners];
      xCorners[0]=13*k;
      yCorners[0]=2*k;
      xCorners[1]=10*k;
      yCorners[1]=5*k;
      xCorners[2]=6*k;
      yCorners[2]=5*k;
      xCorners[3]=3*k;
      yCorners[3]=15*k;
      xCorners[4]=-6*k;
      yCorners[4]=15*k;
      xCorners[5]=-7*k;
      yCorners[5]=10*k;
      xCorners[6]=-4*k;
      yCorners[6]=10*k;
      xCorners[7]=-5*k;
      yCorners[7]=5*k;
      xCorners[8]=-7*k;
      yCorners[8]=5*k;
      xCorners[9]=-10*k;
      yCorners[9]=4*k;
      xCorners[10]=-10*k;
      yCorners[10]=2*k;
      xCorners[11]=-9*k;
      yCorners[11]=2*k;
      xCorners[12]=-9*k;
      yCorners[12]=-2*k;
      xCorners[13]=-10*k;
      yCorners[13]=-2*k;
      xCorners[14]=-10*k;
      yCorners[14]=-4*k;
      xCorners[15]=-7*k;
      yCorners[15]=-5*k;
      xCorners[16]=-5*k;
      yCorners[16]=-5*k;
      xCorners[17]=-4*k;
      yCorners[17]=-10*k;
      xCorners[18]=-7*k;
      yCorners[18]=-10*k;
      xCorners[19]=-6*k;
      yCorners[19]=-15*k;
      xCorners[20]=3*k;
      yCorners[20]=-15*k;
      xCorners[21]=6*k;
      yCorners[21]=-5*k;
      xCorners[22]=10*k;
      yCorners[22]=-5*k;
      xCorners[23]=13*k;
      yCorners[23]=-2*k;
      myCenterX=0;
      myCenterY=0;
      myDirectionX=0;
      myDirectionY=0;
      myPointDirection=0;
    }
    public void setX(int x) {myCenterX=x;}  
    public int getX() {return (int)(myCenterX);}   
    public void setY(int y) {myCenterY=y;}   
    public int getY() {return (int)(myCenterY);}   
    public void setDirectionX(double x) {myDirectionX=x;}   
    public double getDirectionX() {return myDirectionX;}   
    public void setDirectionY(double y) {myDirectionY=y;}   
    public double getDirectionY() {return myDirectionY;}   
    public void setPointDirection(int degrees) {myPointDirection=degrees;}   
    public double getPointDirection() {return myPointDirection;} 
}
abstract class Floater
{   
  protected int corners;  //the number of corners, a triangular floater has 3   
  protected int[] xCorners;   
  protected int[] yCorners;   
  protected int myColor;   
  protected double myCenterX, myCenterY; //holds center coordinates   
  protected double myDirectionX, myDirectionY; //holds x and y coordinates of the vector for direction of travel   
  protected double myPointDirection; //holds current direction the ship is pointing in degrees    
  abstract public void setX(int x);  
  abstract public int getX();   
  abstract public void setY(int y);   
  abstract public int getY();   
  abstract public void setDirectionX(double x);   
  abstract public double getDirectionX();   
  abstract public void setDirectionY(double y);   
  abstract public double getDirectionY();   
  abstract public void setPointDirection(int degrees);   
  abstract public double getPointDirection(); 

  //Accelerates the floater in the direction it is pointing (myPointDirection)   
  public void accelerate (double dAmount)   
  {          
    //convert the current direction the floater is pointing to radians    
    double dRadians =myPointDirection*(Math.PI/180);     
    //change coordinates of direction of travel    
    myDirectionX += ((dAmount) * Math.cos(dRadians));    
    myDirectionY += ((dAmount) * Math.sin(dRadians));       
  }   
  public void rotate (int nDegreesOfRotation)   
  {     
    //rotates the floater by a given number of degrees    
    myPointDirection+=nDegreesOfRotation;   
  }   
  public void move ()   //move the floater in the current direction of travel
  {      
    //change the x and y coordinates by myDirectionX and myDirectionY       
    myCenterX += myDirectionX;    
    myCenterY += myDirectionY;     

    //wrap around screen    
    if(myCenterX >width)
    {     
      myCenterX = 0;    
    }    
    else if (myCenterX<0)
    {     
      myCenterX = width;    
    }    
    if(myCenterY >height)
    {    
      myCenterY = 0;    
    }   
    else if (myCenterY < 0)
    {     
      myCenterY = height;    
    }   
  }   
  public void show ()  //Draws the floater at the current position  
  {             
    fill(myColor);   
    stroke(myColor);    
    //convert degrees to radians for sin and cos         
    double dRadians = myPointDirection*(Math.PI/180);                 
    int xRotatedTranslated, yRotatedTranslated;    
    beginShape();         
    for(int nI = 0; nI < corners; nI++)    
    {     
      //rotate and translate the coordinates of the floater using current direction 
      xRotatedTranslated = (int)((xCorners[nI]* Math.cos(dRadians)) - (yCorners[nI] * Math.sin(dRadians))+myCenterX);     
      yRotatedTranslated = (int)((xCorners[nI]* Math.sin(dRadians)) + (yCorners[nI] * Math.cos(dRadians))+myCenterY);      
      vertex(xRotatedTranslated,yRotatedTranslated);    
    }   
    endShape(CLOSE);  
  }   
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "AsteroidsGame" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
