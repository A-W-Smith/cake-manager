package com.waracle.cakemgr;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Represents cake loaded from database */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cake {
  private String title;

  @SerializedName("desc")
  private String description;

  private String image;
}
