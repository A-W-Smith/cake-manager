package com.waracle.cakemgr;

import java.util.List;

/** Responsible for handling cake database interactions with endpoint */
public interface CakeManager {
  /**
   * Add new cake to database
   *
   * @param cake cake to be added
   */
  void addNewCake(Cake cake) throws CakeExistsException;

  /**
   * @return All cakes from the database
   */
  List<Cake> getAllCakes();

  /** Loads all data into the in-memory database */
  void initialiseDatabase();
}
