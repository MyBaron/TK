package com.monitoring.seckill.Service;


import com.monitoring.seckill.Entity.MonUser;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface UserService {

    MonUser login(MonUser monUser);

    Optional<MonUser> findById(String userId);

    MonUser update(MonUser monUser);
}
