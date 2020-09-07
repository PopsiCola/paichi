package com.paichi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.WebApplicationInitializer;

/**
 * scanBasePackages = {"com.paichi.modules"}
 * @Author liulebin
 * @Date 2020/8/27 20:06
 */
@SpringBootApplication()
@MapperScan("com.paichi.modules.*.mapper")
@EnableAsync
public class Application extends SpringBootServletInitializer implements WebApplicationInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("Paichi启动成功！");
    }

    /**
     * 打war包后可以用tomcat下使用
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(Application.class);
    }

}
