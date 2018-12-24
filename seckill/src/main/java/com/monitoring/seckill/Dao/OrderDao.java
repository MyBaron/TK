package com.monitoring.seckill.Dao;

import com.monitoring.seckill.Entity.MonOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrderDao extends JpaRepository<MonOrder,String> {

    List<MonOrder> findMonOrdersByUserIdOrderByCreateTimeDesc(String userId);

    Page<MonOrder> findMonOrdersByUserIdOrderByCreateTime(Pageable pageable);
}
