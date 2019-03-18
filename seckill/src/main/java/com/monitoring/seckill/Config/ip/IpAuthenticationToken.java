package com.monitoring.seckill.Config.ip;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class IpAuthenticationToken extends AbstractAuthenticationToken {

    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    //未登录的时候，使用该构造器保存Ip地址
    public IpAuthenticationToken( String ip) {
        super(null);
        this.ip = ip;
        setAuthenticated(false);//未登录
    }

    //此为登录成功后使用的构造器
    public IpAuthenticationToken(String ip,Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.ip = ip;
        setAuthenticated(true);//已登录
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.ip;
    }
}
