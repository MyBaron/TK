package com.monitoring.seckill.Controller;


import com.monitoring.seckill.Entity.MonOrder;
import com.monitoring.seckill.Entity.MonUser;
import com.monitoring.seckill.Entity.Result;
import com.monitoring.seckill.Service.OrderService;
import com.monitoring.seckill.Util.ResultVO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@RestController()
@RequestMapping("/order")
public class OrderCtl {

    @Autowired
    private OrderService orderService;

    /**
     * 生成订单
     */
    @RequestMapping("/createOrder.do")
    public Result createOrder(String goodId, HttpServletRequest request) {
        Object userOB=checkLogin(request);
        if (Objects.isNull(userOB)) {
            return ResultVO.error("未登陆");
        }
        MonUser user = (MonUser) userOB;
        MonOrder order = orderService.createOrder(goodId, user);

        if (Objects.isNull(order)) {
            return ResultVO.error("下单失败");
        }else {
            return ResultVO.success("下单成功", order);
        }
    }


    @RequestMapping("/createAndPlay.do")
    public Result createAndPlay(String goodId, HttpServletRequest request) {
        Object userOB=checkLogin(request);
        if (Objects.isNull(userOB)) {
            return ResultVO.error("未登陆");
        }
        //生成订单
        MonUser user = (MonUser) userOB;
        MonOrder order = orderService.createOrder(goodId, user);
        if (Objects.isNull(order)) {
            return ResultVO.error("下单失败");
        }
        //支付
        boolean b = orderService.playOrder(order.getOrderId(), (MonUser) userOB);
        if (b) {
            return ResultVO.success("支付成功");
        }
        return ResultVO.error("支付失败");
    }


    @RequestMapping("/play.do")
    public Result playOrder(String orderId,HttpServletRequest request) {
        Object userOB=checkLogin(request);
        if (Objects.isNull(userOB)) {
            return ResultVO.error("未登陆");
        }
        if (Strings.isEmpty(orderId)) {
            return ResultVO.error("orderId为空");
        }
        boolean b = orderService.playOrder(orderId, (MonUser) userOB);
        if (b) {
            return ResultVO.success("支付成功");
        }
        return ResultVO.error("支付失败");
    }

    //查看该用户的订单
    @RequestMapping("/checkOrder.do")
    public Result checkOrder(HttpServletRequest request) {
        Object userOB=checkLogin(request);
        if (Objects.isNull(userOB)) {
            return ResultVO.error("未登陆");
        }
        return ResultVO.success(orderService.checkAllOrderByUserId((MonUser) userOB));
    }


    private Object checkLogin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (Objects.isNull(session)) {
            return null;
        }
        return session.getAttribute("user");
    }
}
