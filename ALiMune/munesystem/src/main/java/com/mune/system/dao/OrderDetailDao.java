package com.mune.system.dao;

import com.mune.system.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface OrderDetailDao extends JpaRepository<OrderDetail,Long>, JpaSpecificationExecutor<OrderDetail> {

    Optional<List<OrderDetail>> findAllByOrderId(Long orderId);
}
