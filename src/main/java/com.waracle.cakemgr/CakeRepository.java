package com.waracle.cakemgr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Handles cake database access */
@Repository
public interface CakeRepository extends JpaRepository<CakeEntity, Long> {
  boolean existsByTitleIgnoreCase(String title);
}
