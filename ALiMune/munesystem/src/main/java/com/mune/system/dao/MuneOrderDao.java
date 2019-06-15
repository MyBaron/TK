package com.mune.system.dao;

import com.mune.system.entity.MuneOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface  MuneOrderDao extends JpaRepository<MuneOrder,Long>,JpaSpecificationExecutor<MuneOrder> {
}
