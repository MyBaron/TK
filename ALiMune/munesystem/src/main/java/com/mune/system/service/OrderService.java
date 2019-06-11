package com.mune.system.service;

import com.mune.system.dao.MuneOrderDao;
import com.mune.system.entity.*;
import com.mune.system.entity.dto.PageDTO;
import com.mune.system.entity.specification.DateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class OrderService {

    @Autowired
    private MuneOrderDao muneOrderDao;

    @Autowired
    private OrderDetailService orderDetailService;

    public void save(OrderDataDTO orderDataDTO){
        List<OrderDTO> orderData = orderDataDTO.getOrderData();
        if (Objects.isNull(orderData)||orderData.isEmpty()) {
            return;
        }
        int table = orderDataDTO.getTable();
        MuneOrder muneOrder = new MuneOrder();

        //根据muneId获取到菜单的信息
        List<Long> idList = new ArrayList<>();
        orderData.forEach(s->{
            idList.add(s.getMuneId());
        });
        List<OrderDetail> orderDetails = orderDetailService.saveOrderDetail(orderData, idList);
        Date time= new Date();
        muneOrder.setTableNumber(table);
        muneOrder.setCreateTime(time.getTime());
        muneOrder.setTotalMoney(orderDetailService.getTotal(orderDetails));
        muneOrder.setOrderDate(time);
        //保存数据
        MuneOrder order = muneOrderDao.save(muneOrder);
        //保存细节
        orderDetailService.saveAll(orderDetails,order.getOrderId());
    }

    public Page<MuneOrder> getOrder(PageDTO pageDTO, String date,int type) throws ParseException {
        PageRequest pageRequest = new PageRequest(pageDTO.getPage(), pageDTO.isHasPage()?pageDTO.getPageSum():20,
                Sort.Direction.DESC,"createTime");
        Page pageData;
        if (!StringUtils.isEmpty(date)) {
            pageData = muneOrderDao.findAll(DateFactory.create(type,date), pageRequest);
        }else {
            pageData = muneOrderDao.findAll(pageRequest);
        }
        return pageData;
    }


}
