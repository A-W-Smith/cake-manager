package com.waracle.cakemgr;

import lombok.*;

/** Represents cake loaded from database */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cake {
  private String title;
  private String description;
  private String image;
}
