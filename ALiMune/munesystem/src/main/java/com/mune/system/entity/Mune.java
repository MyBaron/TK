package com.mune.system.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Mune {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private long id;
  private String muneName;
  private long price;
  private String imgPath;
}
