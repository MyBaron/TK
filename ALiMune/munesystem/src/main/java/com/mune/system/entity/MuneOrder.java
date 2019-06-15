package com.mune.system.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class MuneOrder {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private long orderId;
  private long totalMoney;
  private long tableNumber;
  private long createTime;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date orderDate;
}
