package com.monitoring.seckill.Config;



import com.monitoring.seckill.Interceptor.CatInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ConfigCatInterceptor  extends WebMvcConfigurerAdapter {


    @Autowired
    private CatInterceptor catInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(catInterceptor).addPathPatterns("/**");
    }
}
