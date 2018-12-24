package com.monitoring.seckill.Dao;

import com.monitoring.seckill.Entity.MonOrderLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface OrderLogDao extends JpaRepository<MonOrderLog,Long> {
}
