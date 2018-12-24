package com.monitoring.seckill.Util.ThreadUtil;

import com.monitoring.seckill.Dao.OrderLogDao;
import com.monitoring.seckill.Entity.MonOrderLog;
import com.monitoring.seckill.Util.PropertiesUtil;
import com.monitoring.seckill.Util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class OrderLogThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private static final BlockingQueue<MonOrderLog> bloLogs = new ArrayBlockingQueue<MonOrderLog>(1000);
    private static final List<MonOrderLog> logList = new ArrayList<>(1000);
    private static final int MAX_SUM = 700;
    private static final Executor ex = Executors.newSingleThreadExecutor();
    private static  OrderLogThread staticObject ;
    private  OrderLogDao orderLogDao;



    public static void add(MonOrderLog log) {
        Assert.notNull(log, "MonOrderLog is null");
        bloLogs.add(log);
        if (Objects.isNull(staticObject)) {
            creat();
        }
    }

    private static void creat() {
        synchronized (logger) {
            if (Objects.isNull(staticObject)) {
                staticObject = new OrderLogThread();
                staticObject.setOrderLogDao(SpringContextUtil.getBean(OrderLogDao.class));
                ex.execute(staticObject);
            }
        }
    }

    private  void batchAdd() {
        try {
            logList.clear();
            if (bloLogs.size() != 0) {
                bloLogs.drainTo(logList, MAX_SUM);
                logger.info("批量插入订单日志开始插入，插入数量为{}",logList.size());
                orderLogDao.saveAll(logList);
            }
        } catch (Exception e) {
            logger.error("批量插入订单日志失败", e);
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            batchAdd();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("订单日志输出线程中断",e);
                e.printStackTrace();
            }
        }
    }

    public void setOrderLogDao(OrderLogDao orderLogDao) {
        this.orderLogDao = orderLogDao;
    }
}
