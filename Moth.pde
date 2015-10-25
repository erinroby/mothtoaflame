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

  void run(ArrayList moths) {

    checkProximityToFlames();
    checkProximityToEdge();
    mothBehavior();

    if (doesSwarm)
      swarm(moths);

    update();
    updateAcc();
    render(); 
  }

  void checkProximityToFlames() {
  }

  void checkProximityToEdge() { 
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

  void swarm(ArrayList moths) {

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

  void mothBehavior() {
  }

  void update() {
    vel.add(acc);
    vel.limit(maxspeed);
    loc.add(vel);
  }

  void updateAcc() {
  }

  void seek(PVector _target) {
    if (doesSeek) {
      acc.add(steer(_target, false));
    }
  }


  void arrive(PVector _target) {
    if (doesArrive) {
      acc.add(steer(_target, true));
    } 
  }

  PVector steer(PVector _target, boolean slowdown) {
    PVector ret; 
    PVector desired = _target.sub(_target, loc);
    float d = desired.mag(); 
    if (d > 0) { 
      desired.normalize();
      if ((slowdown) && (d < closestFlame.r/2)) desired.mult(maxspeed*(d/200.0)); 
      else desired.mult(maxspeed);
      ret = _target.sub(desired, vel);
      ret.limit(maxforce);
    }
    else {
      ret = new PVector(0,0); 
    }
    return ret;
  }

  void render() {
  }

  PVector separate(ArrayList moths) {	
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

  PVector align(ArrayList moths) {
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

  PVector cohesion(ArrayList moths) {
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






