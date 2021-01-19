package com.paichi.interception;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截的页面和请求的映射
 * @Author liulebin
 * @Date 2020/9/28 20:43
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {

            /**
             * 暂时无需拦截
             * @param registry
             */
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                //InterceptorRegistration interceptorRegistration = registry.addInterceptor(new LoginInterceptor());
                //
                //interceptorRegistration.excludePathPatterns("/**");
                //interceptorRegistration.addPathPatterns("/**");
                ////                interceptorRegistration.excludePathPatterns("/", "/login.html", "/login", "/login/**", "/css/**", "/js/**", "/lib/**", "/images/**", "/fonts/**", "/error");
                //interceptorRegistration.excludePathPatterns("/", "/**/login.html", "/**/login", "/**/login/**", "/**/css/**", "/**/js/**", "/**/lib/**", "/**/images/**", "/**/fonts/**", "/**/error");
            }

            /**
             * 直接可以访问静态页面，无需经过Controller映射
             * 配置之后发送/login.html相当于在controller中return "login"
             * @param registry
             */
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/login.html").setViewName("/login/index");
                // 直接访问找回密码页面
                registry.addViewController("/resetpwd.html").setViewName("/resetpwd/resetpwd");
                registry.addViewController("/resetPwd_success.html").setViewName("/resetpwd/resetPwd_success");
                // 全部菜谱展示页面
                registry.addViewController("/chufang/diy/index.html").setViewName("/chufang/diy/index");
                // news新闻静态页
                registry.addViewController("/news/lanrenzaocan_news.html").setViewName("/news/lanrenzaocan_news");
                //关于我们静态页
                registry.addViewController("/about/contact_us.html").setViewName("/about/contact_us");
                registry.addViewController("/about/index.html").setViewName("/about/index");
                registry.addViewController("/about/about_us.html").setViewName("/about/about_us");
                registry.addViewController("/about/join_us.html").setViewName("/about/join_us");
                //美食菜单
                registry.addViewController("/order_list/order_list.html").setViewName("/order_list/order_list");
                // 404找不到页面
                registry.addViewController("/404.html").setViewName("/404");
            }
        };
    }
}
