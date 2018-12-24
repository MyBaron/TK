package com.monitoring.seckill.Dao;

import com.monitoring.seckill.Entity.MonGood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


@Component
public interface GoodDao extends JpaRepository<MonGood,String> {

}
