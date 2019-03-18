package com.monitoring.seckill.Config;

import com.monitoring.seckill.Config.filter.AfterCsrfFilter;
import com.monitoring.seckill.Config.filter.BeforeLoginFilter;
import com.monitoring.seckill.Config.ip.IpAuthenticationProvider;
import com.monitoring.seckill.Config.ip.IpAuthenticationProcessingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
@EnableWebSecurity  //以启用Spring Security的Web安全支持，并提供Spring MVC集成

//可配置拦截什么URL、设置什么权限等安全控制
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService getUserDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(
                        "/js/**",
                        "/css/**",
                        "/img/**",
                        "/webjars/**",
                        "/ftl/**");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //不需要任何身份验证
        http
                .authorizeRequests()
                .antMatchers("/noSecurity.do").permitAll()
                .antMatchers("/user/authentication/require").permitAll()
                .antMatchers("/", "/home").permitAll()
                .antMatchers("/ipLogin").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginProcessingUrl("/user/authentication/require")
                .and()
                .logout().logoutUrl("/logout.do").logoutSuccessUrl("/successLoginOut.do").permitAll()
                .and()
                .rememberMe()
                    .key("unique-and-secret")
                    .rememberMeCookieName("remember-me-cookie-name")
                    .tokenValiditySeconds(24*60*60)
                .and()
                .exceptionHandling()
                .accessDeniedPage("/ipLogin")
                .authenticationEntryPoint(loginUrlAuthenticationEntryPoint())
        ;

        //5版本是默认打开的
        http.csrf().disable();
        //添加自动以拦截器
        http.addFilterBefore(iptAuthenticationProcessingFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore(new BeforeLoginFilter(), UsernamePasswordAuthenticationFilter.class);
//        http.addFilterAfter(new AfterCsrfFilter(), CsrfFilter.class);
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("内存里添加一个用户");
        auth
                .inMemoryAuthentication()
                .withUser("user12").password("password").roles("USER");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(getUserDetailsService);
        auth.authenticationProvider(ipAuthenticationProvider());
    }


    //无加密
//    @Bean
//    public  NoOpPasswordEncoder passwordEncoder() {
//        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//    }

    //生成Ip认证类
    @Bean
    public IpAuthenticationProvider ipAuthenticationProvider() {
        return new IpAuthenticationProvider();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //配置封装ipAuthenticationToken的过滤器
    private IpAuthenticationProcessingFilter iptAuthenticationProcessingFilter(AuthenticationManager authenticationManager) {
        IpAuthenticationProcessingFilter ipAuthenticationProcessingFilter = new IpAuthenticationProcessingFilter();
        //为过滤器添加管理器
        ipAuthenticationProcessingFilter.setAuthenticationManager(authenticationManager);
        //重新认证失败时的跳转url
        ipAuthenticationProcessingFilter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/ipLogin?error"));
        return ipAuthenticationProcessingFilter;
    }

    //配置登录端点
    //没有登录会跳转到这个Url
    @Bean
    LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint(){
        LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint = new LoginUrlAuthenticationEntryPoint
                ("/user/authentication/require");
        return loginUrlAuthenticationEntryPoint;
    }
}
