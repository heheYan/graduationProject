package com.ycxy.modules.app.config;

import com.ycxy.modules.app.interceptor.AuthorizationInterceptor;
import com.ycxy.modules.app.resolver.LoginUserHandlerMethodArgumentResolver;
import com.ycxy.vueueditor.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * MVC配置
 *
 * @author Mark sunlightcs@gmail.com
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;
    @Autowired
    private LoginUserHandlerMethodArgumentResolver loginUserHandlerMethodArgumentResolver;
    /**
     * 文件上传路径前缀
     */
    @Value("${upload.prefix}")
    public String filePrefix;
    /**
     * 本地磁盘目录
     */
    @Value("${upload.uploadFolder}")
    public String uploadLocalPath;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/app/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserHandlerMethodArgumentResolver);
    }


    /**
     * @Title: addResourceHandlers
     * @Description:  映射本地磁盘为静态目录
     * @param: registry
     * @throws:
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        FileUtil.INSTANCE.setFilePrefix(filePrefix);
        FileUtil.INSTANCE.setUploadLocalPath(uploadLocalPath);
        registry.addResourceHandler(filePrefix +"/**").addResourceLocations("file:"+uploadLocalPath);
    }

}
