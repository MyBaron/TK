package com.mune.system.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class OrderDetail {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private long id;
  private long orderId;
  private long muneId;
  private long sum;
  private String muneName;
  private long price;
  private Date createDate;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getOrderId() {
    return orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }


  public long getMuneId() {
    return muneId;
  }

  public void setMuneId(long muneId) {
    this.muneId = muneId;
  }


  public long getSum() {
    return sum;
  }

  public void setSum(long sum) {
    this.sum = sum;
  }


  public String getMuneName() {
    return muneName;
  }

  public void setMuneName(String muneName) {
    this.muneName = muneName;
  }


  public long getPrice() {
    return price;
  }

  public void setPrice(long price) {
    this.price = price;
  }


  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }


  @Override
  public String toString() {
    return "OrderDetail{" +
            "id=" + id +
            ", orderId=" + orderId +
            ", muneId=" + muneId +
            ", sum=" + sum +
            ", muneName='" + muneName + '\'' +
            ", price=" + price +
            ", createDate=" + createDate +
            '}';
  }
}
