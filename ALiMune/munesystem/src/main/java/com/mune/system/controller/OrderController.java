package com.mune.system.controller;


import com.mune.system.entity.*;
import com.mune.system.entity.dto.PageDTO;
import com.mune.system.service.OrderDetailService;
import com.mune.system.service.OrderService;
import com.mune.system.utils.ResultVO;
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

    @PostMapping(value = {"/",""})
    public Result addOrder(@RequestBody OrderDataDTO orderdata) {
        orderService.save(orderdata);
        return ResultVO.success();
    }

    /**
     *  type=0 按天查
     *  type=1 按月查
     *  type=2 按年查
     */
    @GetMapping({"/",""})
    public Map<Object, Object> getOrder(PageDTO pageDTO,@RequestParam(required = false) String date,@RequestParam(required = false,defaultValue = "0") int type) throws ParseException {
        Page<MuneOrder> pageOrder = orderService.getOrder(pageDTO, date,type);
        Map<Object, Object> result = new HashMap<>();
        result.put("content", pageOrder.getContent());
        pageDTO.setSum(pageOrder.getTotalElements());
        result.put("page", pageDTO);
        return result;
    }


    @GetMapping({"/order_deatil","/order_deatil/"})
    public Map<Object,Object> getOrderDetail(@RequestParam("id") long id){
        List<OrderDetail> orderDetail = orderDetailService.getOrderDetail(id);
        Map<Object, Object> result = new HashMap<>();
        result.put("detail", orderDetail);
        return result;
    }



}
