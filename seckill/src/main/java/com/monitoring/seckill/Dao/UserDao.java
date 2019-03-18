package com.monitoring.seckill.Dao;

import com.monitoring.seckill.Entity.MonUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDao extends JpaRepository<MonUser,String> {

    MonUser findByAccount(String account);

}
