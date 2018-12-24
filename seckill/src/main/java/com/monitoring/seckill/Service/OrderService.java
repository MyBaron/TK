package com.monitoring.seckill.Service;

import com.monitoring.seckill.Entity.MonOrder;
import com.monitoring.seckill.Entity.MonUser;

import java.util.List;

public interface OrderService {

    MonOrder createOrder(String goodId, MonUser user);

    boolean playOrder(String orderId,MonUser user);

    MonOrder updateOrder(MonOrder monOrder);

    List<MonOrder> checkAllOrderByUserId(MonUser user);
}
