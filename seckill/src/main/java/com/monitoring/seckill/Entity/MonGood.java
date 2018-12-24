package com.monitoring.seckill.Entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class MonGood {

  @Id
  private String goodId;
  private String goodName;
  private String goodInfo;
  private long goodPrice;
  private long goodAmount;
  private String createTime;


  public String getGoodId() {
    return goodId;
  }

  public void setGoodId(String goodId) {
    this.goodId = goodId;
  }


  public String getGoodName() {
    return goodName;
  }

  public void setGoodName(String goodName) {
    this.goodName = goodName;
  }


  public String getGoodInfo() {
    return goodInfo;
  }

  public void setGoodInfo(String goodInfo) {
    this.goodInfo = goodInfo;
  }


  public long getGoodPrice() {
    return goodPrice;
  }

  public void setGoodPrice(long goodPrice) {
    this.goodPrice = goodPrice;
  }


  public long getGoodAmount() {
    return goodAmount;
  }

  public void setGoodAmount(long goodAmount) {
    this.goodAmount = goodAmount;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  @Override
  public String toString() {
    return "MonGood{" +
            "goodId='" + goodId + '\'' +
            ", goodName='" + goodName + '\'' +
            ", goodInfo='" + goodInfo + '\'' +
            ", goodPrice=" + goodPrice +
            ", goodAmount=" + goodAmount +
            ", createTime='" + createTime + '\'' +
            '}';
  }
}
