package com.mune.system.controller;


import com.mune.system.entity.*;
import com.mune.system.entity.dto.PageDTO;
import com.mune.system.service.OrderDetailService;
import com.mune.system.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @RequestMapping(value = {"/add","/add/"})
    public String addOrder(@RequestBody OrderDataDTO orderdata) {
        orderService.save(orderdata);
        return "ok";
    }

    @GetMapping({"/",""})
    public Map<Object, Object> getOrder(PageDTO pageDTO,@RequestParam(required = false) String date,@RequestParam(required = false,defaultValue = "0") int type) throws ParseException {
        Page<MuneOrder> pageOrder = orderService.getOrder(pageDTO, date,type);
        Map<Object, Object> result = new HashMap<>();
        result.put("content", pageOrder.getContent());
        pageDTO.setSum(pageOrder.getTotalPages());
        result.put("page", pageDTO);
        return result;
    }


    @GetMapping("/order_deatil")
    public Map<Object,Object> getOrderDetail(@RequestParam("id") long id){
        List<OrderDetail> orderDetail = orderDetailService.getOrderDetail(id);
        Map<Object, Object> result = new HashMap<>();
        result.put("detail", orderDetail);
        return result;
    }



}
