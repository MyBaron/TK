package com.monitoring.seckill.Interceptor;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CatInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(CatInterceptor.class);

    private ThreadLocal<Transaction> tranLocal = new ThreadLocal<Transaction>();
    private ThreadLocal<Transaction> pageLocal = new ThreadLocal<Transaction>();


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //Cat记录
        logger.info("正在访问preHandle");
        String url = request.getRequestURI();
        Transaction t=Cat.newTransaction("URL", url);
        Cat.logEvent("URL.Method", request.getMethod(), Message.SUCCESS, request.getRequestURL()
                .toString());
        Cat.logEvent("URL.Host", request.getMethod(), Message.SUCCESS, request.getRemoteHost());
        tranLocal.set(t);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("正在访问postHandle");

        String viewName = modelAndView != null ? modelAndView.getViewName() : "无";
        Transaction t = Cat.newTransaction("View", viewName);
        pageLocal.set(t);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("正在访问afterCompletion");

        // 请求-页面渲染前
        Transaction pt = pageLocal.get();
        pt.setStatus(Transaction.SUCCESS);
        pt.complete();

        // 总计
        Transaction t = tranLocal.get();
        t.setStatus(Transaction.SUCCESS);
        t.complete();
    }
}
