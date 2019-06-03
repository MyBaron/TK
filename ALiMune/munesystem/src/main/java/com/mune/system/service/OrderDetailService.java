package com.mune.system.service;

import com.mune.system.dao.OrderDetailDao;
import com.mune.system.entity.Mune;
import com.mune.system.entity.MuneOrder;
import com.mune.system.entity.OrderDTO;
import com.mune.system.entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private MuneService muneService;


    public void saveAll(List<OrderDetail> list,long id){
        list.forEach(s->s.setOrderId(id));
        orderDetailDao.saveAll(list);
    }


    public List<OrderDetail> saveOrderDetail(List<OrderDTO> orderData,List<Long> idList){

        //获取到所有的菜单，key是muneId
        Map<Long,Mune> muneMap = muneService.getMuneMapByIds(idList);
        List<OrderDetail> orderList = new ArrayList<>();
        //构建订单细节
        orderData.stream().forEach(s -> {
            OrderDetail orderDetail = new OrderDetail();
            Mune mune = muneMap.get(s.getMuneId());
            orderDetail.setMuneId(mune.getId());
            orderDetail.setPrice(mune.getPrice());
            orderDetail.setMuneName(mune.getMuneName());
            orderDetail.setSum(s.getSum());
            orderDetail.setCreateDate(new Date());
            orderList.add(orderDetail);
        });
        //保存到数据库
//        saveAll(orderList);
        return orderList;

    }

    public long getTotal(List<OrderDetail> orderList){
        return orderList.stream().mapToLong(s -> s.getPrice() * s.getSum()).sum();
    }



    public List<OrderDetail> getOrderDetail(long id){
        return orderDetailDao.findAllByOrderId(id).get();
    }
}

