package com.monitoring.seckill.Config.ip;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 替代UsernamePasswordAuthenticationFilter
 */
public class IpAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    //设置拦截url
    public IpAuthenticationProcessingFilter() {
        super(new AntPathRequestMatcher("/ipVerify"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        //构造token
        String ip = request.getRemoteHost();
        IpAuthenticationToken ipAuthenticationToken = new IpAuthenticationToken(ip);
        return getAuthenticationManager().authenticate(ipAuthenticationToken);
    }
}
