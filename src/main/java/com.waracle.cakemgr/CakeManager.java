package com.waracle.cakemgr;

import java.util.List;

/** Responsible for handling cake database interactions */
public interface CakeManager {
  /**
   * Add new cake to database
   *
   * @param cake cake to be added
   */
  void addNewCake(Cake cake);

  /**
   * @return All cakes from the database
   */
  List<Cake> getAllCakes();
}
