package com.monitoring.seckill.Config;

import com.monitoring.seckill.Dao.UserDao;
import com.monitoring.seckill.Entity.MonUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


@Service
public class GetUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MonUser user = userDao.findByAccount(username);
        if (null == user) {
            throw new UsernameNotFoundException("用户不存在~");
        }

        //设置权限
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = getRoles(user);
        return new User(user.getAccount(),user.getPassword(),simpleGrantedAuthorities);
    }



    //获取角色权限
    private List<SimpleGrantedAuthority> getRoles(MonUser user) {
        String roleStr = user.getRoles();
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        if (StringUtils.isEmpty(roleStr)) {
            return simpleGrantedAuthorities;
        }
        String[] roles = roleStr.split(",");
        for (String role : roles) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role));
        }
        return simpleGrantedAuthorities;
    }

}
