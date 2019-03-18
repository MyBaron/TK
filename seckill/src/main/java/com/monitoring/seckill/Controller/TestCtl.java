package com.monitoring.seckill.Controller;


import com.monitoring.seckill.Entity.MonUser;
import com.monitoring.seckill.Entity.Result;
import com.monitoring.seckill.Service.UserService;
import com.monitoring.seckill.Util.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/")
public class TestCtl {

    @Autowired
    private UserService userService;


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


    @RequestMapping("/save.do")
    public Result save() {
        MonUser monUser = new MonUser();
        monUser.setUserName("加密账号1");
        monUser.setAccount("user");
        monUser.setMoney(10000);
        monUser.setRoles("role");
        monUser.setPassword("password");
        userService.save(monUser);
        return ResultVO.success("注册成功");
    }

}
