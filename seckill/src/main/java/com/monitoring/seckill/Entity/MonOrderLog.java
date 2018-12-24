package com.monitoring.seckill.Entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class MonOrderLog {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private long id;
  private String orderId;
  private Date createTime;
  //0 新增订单 1 日志订单
  private long type;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public long getType() {
    return type;
  }

  public void setType(long type) {
    this.type = type;
  }

}
