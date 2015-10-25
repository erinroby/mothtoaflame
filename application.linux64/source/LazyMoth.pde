 class LazyMoth extends Moth {
  color c;
  float mothWidth;
  float mothHeight;

  LazyMoth(PVector l, float ms, float mf, float mw, float mh, color _c, Flame[] fls) {
    super(l, ms, mf, 1.5, 1.0, 1.0, 15, 15, 30, fls);
    c = _c; 
    mothWidth = mw;
    mothHeight = mh;

  }

  void render() {
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

  void checkProximityToFlames() {
    float lowestDist = 9999;

    isCloseToFlame = false;
    for (int i=0; i<flames.length; i++) {
      float theDist = loc.dist(flames[i].loc);
      if (theDist < 125.0 && flames[i].isOn && theDist < lowestDist) {
        isCloseToFlame = true;
        closestFlame = flames[i];
        lowestDist = theDist;
      }
    }
  }


  void mothBehavior() {

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

  void updateAcc() {
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



