package com.monitoring.seckill.Controller;


import com.monitoring.seckill.Entity.Result;
import com.monitoring.seckill.Util.ResultVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/")
public class TestCtl {


    @RequestMapping("/needSecurity.do")
    public Result createOrder() {
        return ResultVO.success("needSecurity");
    }

    @RequestMapping("/noSecurity.do")
    public Result noSecurity() {
        return ResultVO.success("noSecurity");
    }

    @RequestMapping("/login.do")
    public Result login() {
        return ResultVO.success("跳转到登录");
    }

    @RequestMapping("/successLoginOut.do")
    public Result successLoginOut() {
        return ResultVO.success("登出成功");
    }

}
