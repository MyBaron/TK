package com.monitoring.seckill.Service.Impl;

import com.monitoring.seckill.Dao.UserDao;
import com.monitoring.seckill.Entity.MonUser;
import com.monitoring.seckill.Service.UserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public MonUser login(MonUser monUser) {
        MonUser user = userDao.findByAccount(monUser.getAccount());
        if (!Objects.isNull(user) && monUser.getPassword().equals(user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public Optional<MonUser> findById(String userId) {
        if (Strings.isNotEmpty(userId)) {
            return userDao.findById(userId);
        }
        return Optional.empty();
    }

    @Override
    public MonUser update(MonUser monUser) {
        if (Strings.isEmpty(monUser.getId())) {
            return null;
        }
        return userDao.save(monUser);

    }
}
