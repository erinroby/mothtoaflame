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

  void run() {
    drag();
    display();
    rolloverFlame(mouseX, mouseY);
  }

  void display() {
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

  boolean getIsOff() {
    return isOn;
  }

  void rolloverFlame(int mx, int my) {
    float d = dist(mx,my,loc.x,loc.y);
    if (d < r/2) {
      rolloverFlame = true;
    } else {
      rolloverFlame = false;
    }
  }

  void leftClicked(int mx, int my) {
    float d = dist(mx,my,loc.x,loc.y);
    if (d < r/2) {
      dragging = true;
      drag.x = loc.x-mx;
      drag.y = loc.y-my;
    }
  }

  void rightClicked(int mx, int my) {
    float d = dist(mx, my, loc.x, loc.y);
    if (d < r/2 && isOn == false) {
      isOn = true; 
    } 
    else if (d < r/2 && isOn == true) {
      isOn = !isOn; 
    }

  }

  void stopDrag() {
    dragging = false;
  }

  void drag() {
    if (dragging) {
      loc.x = mouseX + drag.x;
      loc.y = mouseY + drag.y;
    }
  }

  void spaceBarPressed() {
    if (key == 32 && isOn == false) {
      isOn = true;
    } 
    else if (key == 32 && isOn == true) {
      isOn = !isOn; 
    }
  }
}




