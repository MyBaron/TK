package com.monitoring.seckill.Controller;


import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.monitoring.seckill.Entity.MonUser;
import com.monitoring.seckill.Entity.Result;
import com.monitoring.seckill.Entity.demoHander;
import com.monitoring.seckill.Service.UserService;
import com.monitoring.seckill.Util.ResultVO;
import com.monitoring.seckill.Util.ZookeeperUtil.CuratorZKUtil;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequestMapping("/user")
@Controller
public class LoginCtl {


    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public Result login(MonUser monUser, HttpServletRequest request) {
        if (StringUtils.isEmpty(monUser.getAccount())) {
            return ResultVO.error();
        }
        MonUser user = userService.login(monUser);
        if (!Objects.isNull(user)) {
            request.getSession().setAttribute("user", user);
            return ResultVO.success("登录成功");
        }
        return ResultVO.error();
    }


    @RequestMapping("/authentication/require")
    public ModelAndView require(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        System.out.println("login");
        return new ModelAndView("login", map);
    }


    @RequestMapping("/test")
    public Result test() throws IOException {
        CuratorZKUtil.createExe();
        return ResultVO.success();
    }

    public static void main(String[] args) {
        ExecutorService executor= Executors.newFixedThreadPool(4);
        EventFactory<demoHander> eventFactory =  new demoHanderFactory();
        //设置为单生产者的处理器
        Disruptor disruptor = new Disruptor(eventFactory, 1024, new DefaultThreadFactory("demo"), ProducerType.SINGLE, new YieldingWaitStrategy());
        //设置默认异常处理器
//        disruptor.setDefaultExceptionHandler(new DisruptorHelperUtil.DisExceptionHandler());
        //设置消费者数量
        demoHandler[] handlers = new demoHandler[5];
        for(int i = 0; i < 5; i ++) {
            handlers[i] = new demoHandler();
        }
        disruptor.handleEventsWithWorkerPool(handlers);
        //启动
        System.out.println("开始启动");
        disruptor.start();
        executor.execute(()->{
            for(int i=0;i<100;i++) {
                demoHander d = new demoHander();
                d.setId(""+i);
                d.setName("name+ "+i);
                disruptor.publishEvent(new PosSysEventTranslator(),d);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        System.out.println("准备关闭");
        //关闭
        disruptor.shutdown();


        String str = "test";
        System.out.println(str.intern());
        System.out.println(str==new String("test"));
    }

    private static class demoHandler implements WorkHandler<demoHander> {

        @Override
        public void onEvent(demoHander event) throws Exception {
            System.out.println(event.getId() + "  "+event.getName());
            Thread.sleep(100);
        }
    }

    /**
     * 消费中转站
     */
    private static class PosSysEventTranslator implements EventTranslatorOneArg<demoHander, demoHander> {

        @Override
        public void translateTo(demoHander event, long sequence, demoHander tar) {
            event.setId(tar.getId());
            event.setName(tar.getName());
        }

    }

    /**
     * event工厂
     */
    private static class demoHanderFactory implements EventFactory<demoHander> {

        @Override
        public demoHander newInstance() {
            return new demoHander();
        }
    }



}
