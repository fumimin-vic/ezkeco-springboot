package com.rio.ezkeco.config;/**
 * @author vic fu
 **/

import com.rio.ezkeco.aop.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *@author vic fu
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(tokenInterceptor);
    }
}
