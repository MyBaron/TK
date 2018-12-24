package com.monitoring.seckill.Service.Impl;

import com.monitoring.seckill.Dao.GoodDao;
import com.monitoring.seckill.Dao.OrderDao;
import com.monitoring.seckill.Dao.OrderLogDao;
import com.monitoring.seckill.Entity.MonGood;
import com.monitoring.seckill.Entity.MonOrder;
import com.monitoring.seckill.Entity.MonOrderLog;
import com.monitoring.seckill.Entity.MonUser;
import com.monitoring.seckill.Service.GoodService;
import com.monitoring.seckill.Service.OrderService;
import com.monitoring.seckill.Service.UserService;
import com.monitoring.seckill.Util.DataUtil;
import com.monitoring.seckill.Util.StaticCode;
import com.monitoring.seckill.Util.ThreadUtil.OrderLogThread;
import com.monitoring.seckill.Util.ZookeeperUtil.CuratorZKUtil;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private GoodService goodService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderLogDao orderLogDao;
    private static final String goodLockPath = "/good/";

    @Override
    @Transactional
    public MonOrder createOrder(String goodId, MonUser user) {

        // 获取锁，锁住good
        logger.info("{} 商品获取锁 ",goodId);
        InterProcessMutex lock = CuratorZKUtil.distributedLock(goodLockPath + goodId);
        MonOrder order=null;
        try {
            lock.acquire();
            //根据ID去查找商品
            Optional<MonGood> opGood = goodService.findOneById(goodId);
            if (opGood.isPresent()) {
                MonGood monGood = opGood.get();
                if (monGood.getGoodAmount() > 0) {
                    //创建秒杀订单,并存到数据库
                    order=orderDao.save(createMonOrder(monGood, user));
                }
                // 写入日志 放到一个线程池中执行
                MonOrderLog log = new MonOrderLog();
                log.setCreateTime(new Date());
                log.setOrderId(monGood.getGoodId());
                log.setType(StaticCode.ORDER_TYPE_ORDER);
                OrderLogThread.add(log);
            }
        } catch (Exception e) {
            logger.error("{}生成订单过程中失败",goodId);
            e.printStackTrace();
        }finally {
            try {
                lock.release();
            } catch (Exception e) {
                logger.error("{} 释放商品锁失败",goodId);
                e.printStackTrace();
            }
        }


        return order;
    }

    @Override
    @Transactional
    public boolean playOrder(String orderId,MonUser user) {

        // 获取锁，锁住user
        InterProcessMutex lock = CuratorZKUtil.distributedLock(goodLockPath + user.getId());
        logger.info("{} 用户获取锁",user.getId());
        try {
            lock.acquire();
            Optional<MonOrder> opOrder = orderDao.findById(orderId);
            if (opOrder.isPresent()) {
                MonOrder monOrder = opOrder.get();
                //根据monOrder的userId查找user
                Optional<MonUser> opUser = userService.findById(monOrder.getUserId());
                if (opUser.isPresent()) {
                    MonUser monUser = opUser.get();
                    //判断是否该用户付款
                    if (!monUser.getId().equals(user.getId())) {
                        return false;
                    }
                    //比较余额
                    if (monOrder.getPrice() > monUser.getMoney()) {
                        logger.info("{} 余额不足，购买失败,userId= {} ,orderId= {}", user.getUserName(), user.getId(), orderId);
                        return false;
                    }
                    //扣除金钱
                    monUser.setMoney(monUser.getMoney() - monOrder.getPrice());
                    //order订单改为已支付
                    monOrder.setIsPlay(1);
                    // 更新User表，order表，写入日志表
                    updateOrder(monOrder);
                    userService.update(monUser);
                    logger.info("{} User表，Order表已经更新完毕，购买完成,orderId {}", user.getUserName(), user.getId(), orderId);
                    // 写入日志表 放到一个线程池中执行
                    MonOrderLog log = new MonOrderLog();
                    log.setCreateTime(new Date());
                    log.setOrderId(orderId);
                    log.setType(StaticCode.ORDER_TYPE_PAY);
                    OrderLogThread.add(log);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                lock.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public MonOrder updateOrder(MonOrder monOrder) {
        if (Strings.isNotEmpty(monOrder.getOrderId())) {
            return orderDao.save(monOrder);
        }
        return null;
    }

    @Override
    public List<MonOrder> checkAllOrderByUserId(MonUser user) {
        List<MonOrder> orderList = orderDao.findMonOrdersByUserIdOrderByCreateTimeDesc(user.getId());
        return orderList.subList(0,10);
    }


    //创建订单
    private MonOrder createMonOrder(MonGood monGood,MonUser user) {
        MonOrder monOrder = new MonOrder();
        monOrder.setUserId(user.getId());
        monOrder.setUserName(user.getUserName());
        monOrder.setCreateTime(DataUtil.formateDataTime(new Date()));
        monOrder.setUpdateTime(DataUtil.formateDataTime(new Date()));
        monOrder.setGoodId(monGood.getGoodId());
        monOrder.setGoodName(monGood.getGoodName());
        monOrder.setIsPlay(0);
        monOrder.setNumber(1);
        //货物的单价
        monOrder.setGoodPrice(monGood.getGoodPrice());
        //因为秒杀数量是一件，因此就是单价
        monOrder.setPrice(monGood.getGoodPrice());
        monOrder.setOrderId(UUID.randomUUID().toString().replace("-",""));
        return monOrder;
    }
}
