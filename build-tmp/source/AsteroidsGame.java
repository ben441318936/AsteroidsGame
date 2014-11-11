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

//variable declarations here
private SpaceShip HMS_Euphoria;
private Star[]starField=new Star[100];
private Laser pew;
private ArrayList <Asteroid> Ragnarock=new ArrayList <Asteroid>();
public void setup() 
{
  size(500,500);
  background(0);
  frameRate(5);
  HMS_Euphoria=new SpaceShip();
  HMS_Euphoria.setX(width/2);
  HMS_Euphoria.setY(height/2);
  pew=new Laser();
  for(int i=0;i<starField.length;i++) {starField[i]=new Star();}
  for(int i=0;i<10;i++) 
    {
      Ragnarock.add(new Asteroid());
      println("huh?");
    }
}
public void draw() 
{
  if(HMS_Euphoria.getHyperspacing()==false /*&& HMS_Euphoria.getAccelerating()==false*/)
  {
    fill(0,0,0);
    rect(0,0,width,height);
    fill(255,255,255);
    for(int i=0;i<starField.length;i++) {starField[i].show();}
  }
  if(HMS_Euphoria.getHyperspacing()==true || HMS_Euphoria.getAccelerating()==true)
  {
    /*if(HMS_Euphoria.getAccelerating()==true)
    {
      if(HMS_Euphoria.getDirectionY()<=3 && HMS_Euphoria.getDirectionY()>=-3 && HMS_Euphoria.getDirectionX()>=-3 && HMS_Euphoria.getDirectionX()<=3)
      {
        fill(0,0,0,60);
        rect(0, 0, width, height);
        fill(255,255,255);
        for(int i=0;i<starField.length;i++) {starField[i].show();}
      }
      else 
      {
        fill(0,0,0);
        rect(0, 0, width, height); 
        fill(255,255,255); 
        for(int i=0;i<starField.length;i++) {starField[i].show();}
      }
    }*/
    if(HMS_Euphoria.getHyperspacing()==true)
    {
      fill(0,0,0,20);
      rect(0, 0, width, height);
      for(int i=0;i<starField.length;i++) {starField[i].show();}
      HMS_Euphoria.setHyperspaceCounter(HMS_Euphoria.getHyperspaceCounter()+1);
      if(HMS_Euphoria.getHyperspaceCounter()>60)
      {
        HMS_Euphoria.setHyperspaceCounter(0);
        HMS_Euphoria.setHyperspacing(false);
      }
    }
  }
  for(int i=0;i<Ragnarock.size();i++) 
  {
    Ragnarock.get(i).move();
    Ragnarock.get(i).show();
  }
  HMS_Euphoria.move();
  pew.move();
  pew.show();
  HMS_Euphoria.show();
  if(HMS_Euphoria.getAccelerating()==true) {HMS_Euphoria.accelerate(0.03f);}
  if(HMS_Euphoria.getLeftTurn()==true) {HMS_Euphoria.rotate(-2);}
  if(HMS_Euphoria.getRightTurn()==true) {HMS_Euphoria.rotate(2);}
  if(HMS_Euphoria.getBraking()==true) {HMS_Euphoria.brake();}
}
public void keyPressed()
{
  if(key=='h') 
  {
    HMS_Euphoria.setHyperspacing(true);
    HMS_Euphoria.hyperspace();
  }
  if(key==' ') {pew.setFired(true);}
  if(key==CODED)
  {
    if(keyCode==UP) {HMS_Euphoria.setAccelerating(true);}
    if(keyCode==LEFT) {HMS_Euphoria.setLeftTurn(true);}
    if(keyCode==RIGHT) {HMS_Euphoria.setRightTurn(true);}
    if(keyCode==DOWN) {HMS_Euphoria.setBraking(true);}
  } 
}
public void keyReleased()
{
  if(key==CODED)
  {
    if(keyCode==UP) {HMS_Euphoria.setAccelerating(false);}
    if(keyCode==LEFT) {HMS_Euphoria.setLeftTurn(false);}
    if(keyCode==RIGHT) {HMS_Euphoria.setRightTurn(false);}
    if(keyCode==DOWN) {HMS_Euphoria.setBraking(false);}
  } 
}
public int colour(int r, int g, int b) {return color(r,g,b);}
public int colour(int i) {return color(i);}
class Star
{
  private double myX, myY;
  private int myColor;
  public Star()
  {
    myX=Math.random()*501;
    myY=Math.random()*501;
    myColor=colour(255,255,255);
  }
  public void show()
  {
    fill(myColor);
    ellipse((float)myX, (float)myY, 2, 2);
  }
}
class SpaceShip extends Floater  
{   
  private int k;
  private boolean accelerating, leftTurn, rightTurn, braking, hyperspacing;
  private int hyperspaceCounter;
  public SpaceShip()
  {
    k=1;
    hyperspaceCounter=0;
    corners=24;
    myColor=colour(110,110,110);
    xCorners=new int[corners];
    yCorners=new int[corners];
    xCorners[0]=13*k;   yCorners[0]=2*k;
    xCorners[1]=10*k;   yCorners[1]=5*k;
    xCorners[2]=6*k;    yCorners[2]=5*k;
    xCorners[3]=3*k;    yCorners[3]=15*k;
    xCorners[4]=-6*k;   yCorners[4]=15*k;
    xCorners[5]=-7*k;   yCorners[5]=10*k;
    xCorners[6]=-4*k;   yCorners[6]=10*k;
    xCorners[7]=-5*k;   yCorners[7]=5*k;
    xCorners[8]=-7*k;   yCorners[8]=5*k;
    xCorners[9]=-10*k;  yCorners[9]=4*k;
    xCorners[10]=-10*k; yCorners[10]=2*k;
    xCorners[11]=-9*k;  yCorners[11]=2*k;
    xCorners[12]=-9*k;  yCorners[12]=-2*k;
    xCorners[13]=-10*k; yCorners[13]=-2*k;
    xCorners[14]=-10*k; yCorners[14]=-4*k;
    xCorners[15]=-7*k;  yCorners[15]=-5*k;
    xCorners[16]=-5*k;  yCorners[16]=-5*k;
    xCorners[17]=-4*k;  yCorners[17]=-10*k;
    xCorners[18]=-7*k;  yCorners[18]=-10*k;
    xCorners[19]=-6*k;  yCorners[19]=-15*k;
    xCorners[20]=3*k;   yCorners[20]=-15*k;
    xCorners[21]=6*k;   yCorners[21]=-5*k;
    xCorners[22]=10*k;  yCorners[22]=-5*k;
    xCorners[23]=13*k;  yCorners[23]=-2*k;
    myCenterX=0;        myCenterY=0;
    myDirectionX=0;     myDirectionY=0;
    myPointDirection=0;
    accelerating=false; braking=false;
    leftTurn=false;     rightTurn=false;
    hyperspacing=false;
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
  public void setAccelerating(boolean x) {accelerating=x;}
  public boolean getAccelerating() {return accelerating;}
  public void setLeftTurn(boolean x) {leftTurn=x;}
  public boolean getLeftTurn() {return leftTurn;}
  public void setRightTurn(boolean x) {rightTurn=x;}
  public boolean getRightTurn() {return rightTurn;}
  public void setBraking(boolean x) {braking=x;}
  public boolean getBraking() {return braking;}
  public void setHyperspacing(boolean x) {hyperspacing=x;}
  public boolean getHyperspacing() {return hyperspacing;}
  public void setHyperspaceCounter(int x) {hyperspaceCounter=x;}
  public int getHyperspaceCounter() {return hyperspaceCounter;}
  public void hyperspace() 
  {
    setX((int)(Math.random()*301+100));
    setY((int)(Math.random()*301+100));
    setDirectionX(0);
    setDirectionY(0);
    setPointDirection((int)(Math.random()*361));
  }
  public void brake() 
  {
    if(myDirectionX<=0.3f && myDirectionY<=0.3f && myDirectionX>=-0.3f && myDirectionY>=-0.3f)
    {
      setDirectionX(0);
      setDirectionY(0);
    }
  }
  public void move ()   //move the floater in the current direction of travel
  {      
    if(myDirectionX>=3) {myDirectionX=3;}
    if(myDirectionX<=-3) {myDirectionX=-3;}
    if(myDirectionY>=3) {myDirectionY=3;}
    if(myDirectionY<=-3) {myDirectionY=-3;}
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
    fill(225,90,0);
    noStroke();
    beginShape();
    vertex((int)((-4 * Math.cos(dRadians)) - (-10 * Math.sin(dRadians))+myCenterX),(int)((-4* Math.sin(dRadians)) + (-10 * Math.cos(dRadians))+myCenterY));
    vertex((int)((-5* Math.cos(dRadians)) - (-5 * Math.sin(dRadians))+myCenterX),(int)((-5* Math.sin(dRadians)) + (-5 * Math.cos(dRadians))+myCenterY));
    vertex((int)((6* Math.cos(dRadians)) - (-5 * Math.sin(dRadians))+myCenterX),(int)((6* Math.sin(dRadians)) + (-5 * Math.cos(dRadians))+myCenterY));
    vertex((int)((4* Math.cos(dRadians)) - (-10 * Math.sin(dRadians))+myCenterX),(int)((4* Math.sin(dRadians)) + (-10 * Math.cos(dRadians))+myCenterY));
    endShape(CLOSE);
    beginShape();
    vertex((int)((-4 * Math.cos(dRadians)) - (10 * Math.sin(dRadians))+myCenterX),(int)((-4* Math.sin(dRadians)) + (10 * Math.cos(dRadians))+myCenterY));
    vertex((int)((-5* Math.cos(dRadians)) - (5 * Math.sin(dRadians))+myCenterX),(int)((-5* Math.sin(dRadians)) + (5 * Math.cos(dRadians))+myCenterY));
    vertex((int)((6* Math.cos(dRadians)) - (5 * Math.sin(dRadians))+myCenterX),(int)((6* Math.sin(dRadians)) + (5 * Math.cos(dRadians))+myCenterY));
    vertex((int)((4* Math.cos(dRadians)) - (10 * Math.sin(dRadians))+myCenterX),(int)((4* Math.sin(dRadians)) + (10 * Math.cos(dRadians))+myCenterY));
    endShape(CLOSE);
    if(myDirectionY!=0 || myDirectionX!=0)
    {
      float k=150;
      float r,g;
      r=0+k*(float)(Math.sqrt(Math.pow(myDirectionX,2.0f)+Math.pow(myDirectionY,2.0f)));
      if(r>=190) {r=190;}
      g=90+k*(float)(Math.sqrt(Math.pow(myDirectionX,2.0f)+Math.pow(myDirectionY,2.0f)));
      fill(r,g,255);
      noStroke();
      beginShape();
      vertex((int)((-7 * Math.cos(dRadians)) - (-10 * Math.sin(dRadians))+myCenterX),(int)((-7* Math.sin(dRadians)) + (-10 * Math.cos(dRadians))+myCenterY));
      vertex((int)((-6* Math.cos(dRadians)) - (-15 * Math.sin(dRadians))+myCenterX),(int)((-6* Math.sin(dRadians)) + (-15 * Math.cos(dRadians))+myCenterY));
      vertex((int)((-10* Math.cos(dRadians)) - (-15 * Math.sin(dRadians))+myCenterX),(int)((-10* Math.sin(dRadians)) + (-15 * Math.cos(dRadians))+myCenterY));
      vertex((int)((-10* Math.cos(dRadians)) - (-10 * Math.sin(dRadians))+myCenterX),(int)((-10* Math.sin(dRadians)) + (-10 * Math.cos(dRadians))+myCenterY));
      endShape(CLOSE);
      beginShape();
      vertex((int)((-7 * Math.cos(dRadians)) - (10 * Math.sin(dRadians))+myCenterX),(int)((-7* Math.sin(dRadians)) + (10 * Math.cos(dRadians))+myCenterY));
      vertex((int)((-6* Math.cos(dRadians)) - (15 * Math.sin(dRadians))+myCenterX),(int)((-6* Math.sin(dRadians)) + (15 * Math.cos(dRadians))+myCenterY));
      vertex((int)((-10* Math.cos(dRadians)) - (15 * Math.sin(dRadians))+myCenterX),(int)((-10* Math.sin(dRadians)) + (15 * Math.cos(dRadians))+myCenterY));
      vertex((int)((-10* Math.cos(dRadians)) - (10 * Math.sin(dRadians))+myCenterX),(int)((-10* Math.sin(dRadians)) + (10 * Math.cos(dRadians))+myCenterY));
      endShape(CLOSE);
    }
  }
}
class Laser
{
  private double myX,myY,myPointDirection,mySpeed;
  private int myColor;
  private boolean fired,flying;
  public Laser()
  {
    myX=HMS_Euphoria.getX();
    myY=HMS_Euphoria.getY();
    myPointDirection=HMS_Euphoria.getPointDirection();
    mySpeed=10;
    myColor=colour(0,0,0);
    fired=false;
    flying=false;
  }
  public double getX() {return myX;}
  public void setX(double x) {myX=x;}
  public double getY() {return myY;}
  public void setY(double y) {myY=y;}
  public double getPointDirection() {return myPointDirection;}
  public void setPointDirection(double x) {myPointDirection=x;}
  public double getSpeed() {return mySpeed;}
  public void setSpeed(int s) {mySpeed=s;}
  public boolean getFired() {return fired;}
  public void setFired(boolean x) {fired=x;}
  public void move()
  {
    if(fired==false && flying==false)
    {
      myX=HMS_Euphoria.getX();
      myY=HMS_Euphoria.getY();
      myPointDirection=HMS_Euphoria.getPointDirection();
    }
    if(fired==true && flying==false)
    {
      myX=HMS_Euphoria.getX();
      myY=HMS_Euphoria.getY();
      myPointDirection=HMS_Euphoria.getPointDirection();
      flying=true;
    }
    if(flying==true && fired==true)
    {
      myColor=colour(255,0,0);
      myX=myX+Math.cos(Math.toRadians(myPointDirection))*mySpeed;
      myY=myY+Math.sin(Math.toRadians(myPointDirection))*mySpeed;
      if(myX>width || myX<0 || myY>height || myY<0)
      {
        myColor=colour(0,0,0);
        fired=false;
        flying=false;
      }
    }
  }
  public void show()
  {
    strokeWeight(3);
    stroke(myColor);
    line((float)myX, (float)myY, (float)(myX+5*Math.cos(Math.toRadians(myPointDirection))), (float)(myY+5*Math.sin(Math.toRadians(myPointDirection))));
  }
}
class Asteroid extends Floater
{
  double myRotSpeed;
  Asteroid()
  {
    int k=2;
    corners=6;
    xCorners=new int[corners];
    yCorners=new int[corners];
    xCorners[0]=5*k;  yCorners[0]=0*k;
    xCorners[1]=3*k;  yCorners[1]=3*k;
    xCorners[2]=-3*k; yCorners[2]=5*k;
    xCorners[3]=-4*k; yCorners[3]=0*k;
    xCorners[4]=-2*k; yCorners[4]=-4*k;
    xCorners[5]=4*k;  yCorners[5]=-3*k;
    myColor=colour(90,90,90);
    myDirectionX=Math.random()*2-0.95f;
    myDirectionY=Math.random()*2-0.95f;
    myPointDirection=0;
    myRotSpeed=Math.random()*11-5;
    int i=(int)(Math.random()*4+1);
    if(i==1)
    {
      myCenterX=Math.random()*21;
      myCenterY=Math.random()*21;
    }
    if(i==2)
    {
      myCenterX=Math.random()*21+480;
      myCenterY=Math.random()*21;
    }
    if(i==3)
    {
      myCenterX=Math.random()*21;
      myCenterY=Math.random()*21+480;
    }
    if(i==4)
    {
      myCenterX=Math.random()*21+480;
      myCenterY=Math.random()*21+480;
    }
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
    translate((float)myCenterX, (float)myCenterY);
    rotate((int)myRotSpeed);
    translate((float)-myCenterX, (float)-myCenterY);
  }   
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
