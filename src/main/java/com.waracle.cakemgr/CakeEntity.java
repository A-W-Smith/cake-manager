package com.waracle.cakemgr;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/** Database object representing cake */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CakeEntity {
  @Id @GeneratedValue private Long id;
  private String title;

  @SerializedName("desc")
  private String description;

  private String image;
}
