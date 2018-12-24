package com.monitoring.seckill.Service.Impl;

import com.monitoring.seckill.Dao.GoodDao;
import com.monitoring.seckill.Entity.MonGood;
import com.monitoring.seckill.Service.GoodService;
import com.monitoring.seckill.Util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GoodServiceImpl implements GoodService {

    @Autowired
    private GoodDao goodDao;

    @Override
    public List<MonGood> checkGoodAll() {
        return goodDao.findAll();
    }

    @Override
    public MonGood saveGood(MonGood monGood) {
        monGood.setCreateTime(DataUtil.formateDataTime(new Date()));
        String uuid32 = UUID.randomUUID().toString().replace("-", "");
        monGood.setGoodId(uuid32);
        return goodDao.save(monGood);
    }

    @Override
    public Optional<MonGood> findOneById(String id) {
        return goodDao.findById(id);
    }
}
