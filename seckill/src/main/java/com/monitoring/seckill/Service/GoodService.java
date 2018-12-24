package com.monitoring.seckill.Service;

import com.monitoring.seckill.Entity.MonGood;

import java.util.List;
import java.util.Optional;

public interface GoodService {

    public List<MonGood> checkGoodAll();

    public MonGood saveGood(MonGood monGood);

    public Optional<MonGood> findOneById(String id);
}
