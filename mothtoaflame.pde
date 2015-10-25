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

void setup() {
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

void draw() {

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

void mousePressed() {
  for (int i=0; i<flames.length; i++) {
    if (mouseButton == LEFT) {
      flames[i].leftClicked(mouseX, mouseY);
    }
    else if (mouseButton == RIGHT) {
      flames[i].rightClicked(mouseX, mouseY);
    }
  } 
}

void mouseReleased() {
  for (int i=0; i<flames.length; i++) {
    flames[i].stopDrag();
  }  
}

void keyPressed() {
  for(int i =0; i<flames.length; i++) {
    if (key == 32) {
      flames[i].spaceBarPressed(); 
    }
  }
  
  if (key == 'm') {
    noCursor();
  }
  if (key == 'x') {
    bswarm.addMoth(new BusyMoth(new PVector(random(0,width), random(0,height)), 3.0f, .05f, 11, 5.5, color(255, 50, 0), flames));
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
      lswarm.subMoth(int(random(0, lswarm.moths.size()-1)));
    }
    for (int i=0; i<cswarm.moths.size()/4; i++) {
      cswarm.subMoth(int(random(0, cswarm.moths.size()-1)));
    }
    for (int i=0; i<bswarm.moths.size()/4; i++) {
      bswarm.subMoth(int(random(0, bswarm.moths.size()-1)));
    }
  }
}
  
 void keyReleased() {
  if (key == 'm') {
    cursor();  
 }
}



