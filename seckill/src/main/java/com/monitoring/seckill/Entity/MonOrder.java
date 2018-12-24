package com.monitoring.seckill.Entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class MonOrder {

  @Id
  private String orderId;
  private String userName;
  private String userId;
  private String goodId;
  private String goodName;
  private long goodPrice;
  private long number;
  private long price;
  private long isPlay;
  private String createTime;
  private String updateTime;


  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }


  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }


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


  public long getGoodPrice() {
    return goodPrice;
  }

  public void setGoodPrice(long goodPrice) {
    this.goodPrice = goodPrice;
  }


  public long getNumber() {
    return number;
  }

  public void setNumber(long number) {
    this.number = number;
  }


  public long getPrice() {
    return price;
  }

  public void setPrice(long price) {
    this.price = price;
  }


  public long getIsPlay() {
    return isPlay;
  }

  public void setIsPlay(long isPlay) {
    this.isPlay = isPlay;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }

  @Override
  public String toString() {
    return "MonOrder{" +
            "orderId='" + orderId + '\'' +
            ", userName='" + userName + '\'' +
            ", userId='" + userId + '\'' +
            ", goodId='" + goodId + '\'' +
            ", goodName='" + goodName + '\'' +
            ", goodPrice=" + goodPrice +
            ", number=" + number +
            ", price=" + price +
            ", isPlay=" + isPlay +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            '}';
  }
}
