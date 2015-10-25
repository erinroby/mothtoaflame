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

public class Final_5_4 extends PApplet {

//Erin Bolton
//Scripting Spring 2010
//Final Project
//"Like Moth to a Flame"

int NUM_FLAMES = 7;

BusyMothSwarm bswarm;
CrazyMothSwarm cswarm;
LazyMothSwarm lswarm;
Flame[] flames;

PFont font;

public void setup() {
  size(1280,720);
  font = loadFont("Courier-12.vlw");

  flames = new Flame[NUM_FLAMES];
  for (int i=0; i<flames.length; i++) {
    flames[i] = new Flame(random(30,width-30), random(30,height-30)); 
  }
  
  bswarm = new BusyMothSwarm();
  cswarm = new CrazyMothSwarm();
  lswarm = new LazyMothSwarm();


  for (int j=0; j<125; j++) {
    bswarm.addMoth(new BusyMoth(new PVector(random(75,width-75), random(100,height-100)), 3.0f, .05f, random(7,9), (random(7,9)/2), color(112, 84, 59), flames));
  }
  for (int j=0; j<80; j++) {
    cswarm.addMoth(new CrazyMoth(new PVector(random(100,width-100), random(150,height-150)), 4.0f, .05f, random(9,10), (random(9,10)/2), color(217, 200, 169), flames));
  }
  for (int j=0; j<50; j++) {
    lswarm.addMoth(new LazyMoth(new PVector(random(150,width-150), random(250,height-250)), 2.0f, .01f, 10, 5, color(166, 133, 93), flames));
  }
}

public void draw() {

  fill(15, 255);
  noStroke();
  rect(0,0,width,height);
  smooth();
  frameRate(60);

  for (int i=0; i<flames.length; i++) {
    flames[i].run();
  }  
  
  bswarm.run();
  cswarm.run();
  lswarm.run();

  textFont(font); 
  fill(255);
  noStroke();
  text("BusyMoths : " + str(bswarm.moths.size()), 10, height-15);
  text("LazyMoths : " + str(lswarm.moths.size()), 10, height-30);
  text("CrazyMoths : " + str(cswarm.moths.size()), 10, height-45);
  
  int busyCloseToFlame = 0;
  int lazyCloseToFlame = 0;
  int crazyCloseToFlame = 0;
  
  for (int i=0; i<bswarm.moths.size(); i++) {
    BusyMoth thisMoth = (BusyMoth) bswarm.moths.get(i);
    if (thisMoth.isCloseToFlame) {
      busyCloseToFlame++;
    }
  }
  
  for (int i=0; i<cswarm.moths.size(); i++) {
    CrazyMoth thisMoth = (CrazyMoth) cswarm.moths.get(i);
    if (thisMoth.isCloseToFlame) {
      crazyCloseToFlame++;
    }
  }
    
  for (int i=0; i<lswarm.moths.size(); i++) {
    LazyMoth thisMoth = (LazyMoth) lswarm.moths.get(i);
    if (thisMoth.isCloseToFlame) {
      lazyCloseToFlame++;
      }
    }

  text("BusyMoths close to flame : " + str(busyCloseToFlame), 10, 15);
  text("LazyMoths close to flame : " + str(lazyCloseToFlame), 10, 30);
  text("CrazyMoths close to flame : " + str(crazyCloseToFlame), 10, 45);
  
  //text("Framerate : " + str(frameRate), 10, 60);
}

public void mousePressed() {
  for (int i=0; i<flames.length; i++) {
    if (mouseButton == LEFT) {
      flames[i].leftClicked(mouseX, mouseY);
    }
    else if (mouseButton == RIGHT) {
      flames[i].rightClicked(mouseX, mouseY);
    }
  } 
}

public void mouseReleased() {
  for (int i=0; i<flames.length; i++) {
    flames[i].stopDrag();
  }  
}

public void keyPressed() {
  for(int i =0; i<flames.length; i++) {
    if (key == 32) {
      flames[i].spaceBarPressed(); 
    }
  }
  
  if (key == 'm') {
    noCursor();
  }
  if (key == 'x') {
    bswarm.addMoth(new BusyMoth(new PVector(random(0,width), random(0,height)), 3.0f, .05f, 11, 5.5f, color(255, 50, 0), flames));
  }
  if (key == 'b') {
    bswarm.addMoth(new BusyMoth(new PVector(random(0,width), random(0,height)), 3.0f, .05f, random(7,9), (random(7,9)/2), color(93, 142, 166), flames));
  } 
  if (key == 'c') {
    cswarm.addMoth(new CrazyMoth(new PVector(random(0,width), random(0,height)), 3.0f, .05f, random(9,11), (random(9,11)/2), color(169, 191, 217), flames));
  } 
  if (key == 'l') {
    lswarm.addMoth(new LazyMoth(new PVector(random(0,width), random(0,height)), 2.0f, .01f, random(10,11), (random(10,11)/2), color(159, 138, 166), flames));
  } 
  if (key == 'd') {
    for (int i=0; i<lswarm.moths.size()/4; i++) {
      lswarm.subMoth(PApplet.parseInt(random(0, lswarm.moths.size()-1)));
    }
    for (int i=0; i<cswarm.moths.size()/4; i++) {
      cswarm.subMoth(PApplet.parseInt(random(0, cswarm.moths.size()-1)));
    }
    for (int i=0; i<bswarm.moths.size()/4; i++) {
      bswarm.subMoth(PApplet.parseInt(random(0, bswarm.moths.size()-1)));
    }
  }
}
  
 public void keyReleased() {
  if (key == 'm') {
    cursor();  
 }
}



class BusyMoth extends Moth {
  int c;
  float mothWidth;
  float mothHeight;


  BusyMoth(PVector l, float ms, float mf, float mw, float mh, int _c, Flame[] fls) {
    super(l, ms, mf, 1.5f, 1.0f, 1.0f, 15, 15, 30, fls);
    c = _c; 
    mothWidth = mw;
    mothHeight = mh;
  }

  public void render() {
    float theta = vel.heading2D() + PI/2;  
    pushMatrix();
    translate(loc.x, loc.y);
    rotate(theta);
    ellipseMode(CENTER);
    if (!isCloseToFlame) {
      fill(c, 75);
      stroke(c, 75);
    } else {
      fill(c);
      stroke(c);
    }
    ellipse(0, 0, mothWidth, mothHeight);
    popMatrix();
  }

  public void checkProximityToFlames() {
    float lowestDist = 9999;
    isCloseToFlame = false;

    for (int i=0; i<flames.length; i++) {
      float theDist = loc.dist(flames[i].loc);
      if (theDist < 150.0f && flames[i].isOn && theDist < lowestDist) {
        isCloseToFlame = true;
        closestFlame = flames[i];
        lowestDist = theDist;
      }
    }
  }


  public void mothBehavior() {
    if (isCloseToFlame) {
      PVector mothTarget = new PVector(closestFlame.loc.x + random(-closestFlame.r+20, closestFlame.r+20), closestFlame.loc.y + random(-closestFlame.r+20, closestFlame.r+20));
      doesSeek = true;
      doesSwarm = false;
      seek(mothTarget);
    } else {
      doesSeek = false;
      doesSwarm = true;
    }
  }

  public void updateAcc() {
    acc.mult(1);
  }

}

class BusyMothSwarm extends Swarm { 
  BusyMothSwarm() {
    super();
  }
}




class CrazyMoth extends Moth {
  int c;
  float mothWidth;
  float mothHeight;

  CrazyMoth(PVector l, float ms, float mf, float mw, float mh, int _c, Flame[] fls) {
    super(l, ms, mf, 1.5f, 1.0f, 1.0f, 15, 15, 30, fls);
    c = _c; 
    mothWidth = mw;
    mothHeight = mh;
  }

  public void render() {
    float theta = vel.heading2D() + PI/2;  
    pushMatrix();
    translate(loc.x, loc.y);
    rotate(theta);
    ellipseMode(CENTER);
    if (!isCloseToFlame) {
      fill(c, 75);
      stroke(c, 75);
    } 
    else {
      fill(c);
      stroke(c);
    }
    ellipse(0, 0, mothWidth, mothHeight);
    popMatrix();
  }

  public void checkProximityToFlames() {
    float lowestDist = 9999;

    isCloseToFlame = false;
    for (int i=0; i<flames.length; i++) {
      float theDist = loc.dist(flames[i].loc);
      if (theDist < 200.0f && flames[i].isOn && theDist < lowestDist) {
        isCloseToFlame = true;
        closestFlame = flames[i];
        lowestDist = theDist;
      }
    }
  }


  public void mothBehavior() {
    if (isCloseToFlame) {
      PVector mothTarget = new PVector(closestFlame.loc.x + random(-closestFlame.r, closestFlame.r), closestFlame.loc.y + random(-closestFlame.r, closestFlame.r));
      doesSeek = true;
      doesSwarm = false;
      seek(mothTarget);
    } 
    else {
      doesSeek = false;
      doesSwarm = true;
    }
  }

  public void updateAcc() {
    acc.mult(1);
  }
}

class CrazyMothSwarm extends Swarm { 
  CrazyMothSwarm() {
    super();
  }
}





class Flame {

  float r = random(90,125);
  boolean dragging = false;
  boolean rolloverFlame = false;
  boolean isOn = false;
  PVector drag;
  PVector loc;


  Flame(float x, float y) {
    loc = new PVector(x,y);
    drag = new PVector(0,0);
  }

  public void run() {
    drag();
    display();
    rolloverFlame(mouseX, mouseY);
  }

  public void display() {
    if (dragging && !isOn) {
      fill(75, 200);   
    } 
    else if (dragging && isOn) {
      fill(245, 252, 80); 
    } 
    else if (rolloverFlame && !isOn) {
      fill(75, 200);
    } 
    else if (rolloverFlame && isOn) {
      fill(245, 252, 110);
    } 
    else if (isOn) {
      fill(245, 252, 80);
    } 
    else {
      fill(150, 20); 
    }  
    noStroke(); 
    ellipseMode(CENTER);
    ellipse(loc.x,loc.y, r, r);
  }

  public boolean getIsOff() {
    return isOn;
  }

  public void rolloverFlame(int mx, int my) {
    float d = dist(mx,my,loc.x,loc.y);
    if (d < r/2) {
      rolloverFlame = true;
    } else {
      rolloverFlame = false;
    }
  }

  public void leftClicked(int mx, int my) {
    float d = dist(mx,my,loc.x,loc.y);
    if (d < r/2) {
      dragging = true;
      drag.x = loc.x-mx;
      drag.y = loc.y-my;
    }
  }

  public void rightClicked(int mx, int my) {
    float d = dist(mx, my, loc.x, loc.y);
    if (d < r/2 && isOn == false) {
      isOn = true; 
    } 
    else if (d < r/2 && isOn == true) {
      isOn = !isOn; 
    }

  }

  public void stopDrag() {
    dragging = false;
  }

  public void drag() {
    if (dragging) {
      loc.x = mouseX + drag.x;
      loc.y = mouseY + drag.y;
    }
  }

  public void spaceBarPressed() {
    if (key == 32 && isOn == false) {
      isOn = true;
    } 
    else if (key == 32 && isOn == true) {
      isOn = !isOn; 
    }
  }
}




 class LazyMoth extends Moth {
  int c;
  float mothWidth;
  float mothHeight;

  LazyMoth(PVector l, float ms, float mf, float mw, float mh, int _c, Flame[] fls) {
    super(l, ms, mf, 1.5f, 1.0f, 1.0f, 15, 15, 30, fls);
    c = _c; 
    mothWidth = mw;
    mothHeight = mh;

  }

  public void render() {
    float theta = vel.heading2D() + PI/2;  
    pushMatrix();
    translate(loc.x, loc.y);
    rotate(theta);
    ellipseMode(CENTER);
       if (!isCloseToFlame) {
      fill(c, 75);
      stroke(c, 75);
    } else {
      fill(c, 200);
      stroke(c, 200);
    }
    ellipse(0, 0, mothWidth, mothHeight);
    popMatrix();
  }

  public void checkProximityToFlames() {
    float lowestDist = 9999;

    isCloseToFlame = false;
    for (int i=0; i<flames.length; i++) {
      float theDist = loc.dist(flames[i].loc);
      if (theDist < 125.0f && flames[i].isOn && theDist < lowestDist) {
        isCloseToFlame = true;
        closestFlame = flames[i];
        lowestDist = theDist;
      }
    }
  }


  public void mothBehavior() {

    if (isCloseToFlame) {
      PVector mothTarget = new PVector(closestFlame.loc.x + random(-closestFlame.r*2, closestFlame.r*2), closestFlame.loc.y + random(-closestFlame.r*2, closestFlame.r*2));
      doesArrive = true;
      doesSwarm = false;
      arrive(mothTarget);
    } else {
      doesArrive = false;
      doesSwarm = true;
    }
  }

  public void updateAcc() {
    if (isCloseToFlame) {
      acc.mult(0);
    } else {
      acc.mult(1);
    }
  }  

}

class LazyMothSwarm extends Swarm { 
  LazyMothSwarm() {
    super();
  }
}



class Moth {

  PVector loc;
  PVector vel;
  PVector acc;
  float maxforce;
  float maxspeed;

  boolean isCloseToFlame;
  boolean isCloseToEdge;

  float sepMult; 
  float aliMult; 
  float cohMult; 

  float desSep; 
  float neiDist; 
  float neiMDist; 

  boolean doesArrive;
  boolean doesSeek;
  boolean doesSwarm;

  Flame[] flames;
  Flame closestFlame;

  PVector center;

  Moth(PVector l, float ms, float mf, float sm, float am, float cm, float ds, float nD, float nMD, Flame[] fls) {  

    acc = new PVector (0, 0);
    vel = new PVector (random(-1, 1), random(-1, 1));
    loc = l.get();
    maxspeed = ms;
    maxforce = mf;

    sepMult = sm;
    aliMult = am;
    cohMult = cm;

    desSep = ds;
    neiDist = nD;
    neiMDist = nMD;

    doesSwarm = true;

    flames = fls;

    isCloseToFlame = false;
    isCloseToEdge = false;
    closestFlame = null;
  }

  public void run(ArrayList moths) {

    checkProximityToFlames();
    checkProximityToEdge();
    mothBehavior();

    if (doesSwarm)
      swarm(moths);

    update();
    updateAcc();
    render(); 
  }

  public void checkProximityToFlames() {
  }

  public void checkProximityToEdge() { 
    center = new PVector(width/2, height/2);
    float d = PVector.dist(loc, center);
    if (loc.x < 50 || loc.x > width-50 || loc.y < 50 || loc.y > height-50) {
      isCloseToEdge = true;
    }

    if(isCloseToEdge && !isCloseToFlame) {
      doesSeek = true;
      doesSwarm = false;
      seek(center);
    }

    if (d < 10 && isCloseToEdge) {
      isCloseToEdge = false;
      doesSeek = false;
      doesSwarm = true;
    }
  }

  public void swarm(ArrayList moths) {

    PVector sep = separate(moths); 
    PVector ali = align(moths);
    PVector coh = cohesion(moths);
    sep.mult(sepMult); 
    ali.mult(aliMult);
    coh.mult(cohMult);

    acc.add(sep);
    acc.add(ali);
    acc.add(coh); 
  }

  public void mothBehavior() {
  }

  public void update() {
    vel.add(acc);
    vel.limit(maxspeed);
    loc.add(vel);
  }

  public void updateAcc() {
  }

  public void seek(PVector _target) {
    if (doesSeek) {
      acc.add(steer(_target, false));
    }
  }


  public void arrive(PVector _target) {
    if (doesArrive) {
      acc.add(steer(_target, true));
    } 
  }

  public PVector steer(PVector _target, boolean slowdown) {
    PVector ret; 
    PVector desired = _target.sub(_target, loc);
    float d = desired.mag(); 
    if (d > 0) { 
      desired.normalize();
      if ((slowdown) && (d < closestFlame.r/2)) desired.mult(maxspeed*(d/200.0f)); 
      else desired.mult(maxspeed);
      ret = _target.sub(desired, vel);
      ret.limit(maxforce);
    }
    else {
      ret = new PVector(0,0); 
    }
    return ret;
  }

  public void render() {
  }

  public PVector separate(ArrayList moths) {	
    float desiredSeparation = desSep; 
    PVector ret = new PVector(0,0);
    int count = 0;
    for (int i = 0 ; i < moths.size(); i++) {
      Moth other = (Moth) moths.get(i);
      float d = PVector.dist(loc,other.loc);

      if ((d > 0) && (d < desiredSeparation)) {
        PVector diff = PVector.sub(loc,other.loc);
        diff.normalize();
        diff.div(d);        
        ret.add(diff);
        count++;            
      }
    }

    if (count > 0) {
      ret.div((float)count);
    }

    if (ret.mag() > 0) {
      ret.normalize();
      ret.mult(maxspeed);
      ret.sub(vel);
      ret.limit(maxforce);
    }
    return ret;
  }

  public PVector align(ArrayList moths) {
    float neighborDist = neiDist; 
    PVector ret = new PVector(0,0);
    int count = 0;
    for (int i = 0 ; i < moths.size(); i++) {
      Moth other = (Moth) moths.get(i);
      float d = PVector.dist(loc,other.loc);
      if ((d > 0) && (d < neighborDist)) {
        ret.add(other.vel);
        count++;
      }
    }
    if (count > 0) {
      ret.div((float)count);
    }

    if (ret.mag() > 0) {
      ret.normalize();
      ret.mult(maxspeed);
      ret.sub(vel);
      ret.limit(maxforce);
    }
    return ret;
  }

  public PVector cohesion(ArrayList moths) {
    float neighborMothdist = neiMDist; 
    PVector ret = new PVector(0,0);   
    int count = 0;
    for (int i = 0 ; i < moths.size(); i++) {
      Moth other = (Moth) moths.get(i);
      float d = loc.dist(other.loc);
      if ((d > 0) && (d < neighborMothdist)) {
        ret.add(other.loc); 
        count++;
      }
    }
    if (count > 0) {
      ret.div((float)count);
      return steer(ret,false);  
    }
    return ret; 
  }
}






class Swarm {
  ArrayList moths;

  Swarm() {
    moths = new ArrayList();
  }

  public void run() {
    for (int i = 0; i < moths.size(); i++) {
      Moth m = (Moth) moths.get(i);
      m.run(moths);
    }
  }

  public void addMoth(Moth m) {
    moths.add(m);
  }
  
  public void subMoth(int mothIndex) {
    moths.remove(mothIndex);
  }
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Final_5_4" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
