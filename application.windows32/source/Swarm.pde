class Swarm {
  ArrayList moths;

  Swarm() {
    moths = new ArrayList();
  }

  void run() {
    for (int i = 0; i < moths.size(); i++) {
      Moth m = (Moth) moths.get(i);
      m.run(moths);
    }
  }

  void addMoth(Moth m) {
    moths.add(m);
  }
  
  void subMoth(int mothIndex) {
    moths.remove(mothIndex);
  }
}

