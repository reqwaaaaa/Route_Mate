package com.RouteMate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动程序
 *
 * @author Naiweilanlan
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableScheduling
public class RouteMateApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(RouteMateApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  RouteMate启动成功，欢迎来到奶味蓝蓝电台   ლ(´ڡ`ლ)ﾞ  \n" +
                "    :: RouteMate出行服务 v1.0.1    ::: \n" +
                "    :: RouteMate Service Platform ::             \n" +
                "    :: 热点轨迹 + 频繁模式 + 通用功能 ::      \n" +
                "接口文档： http://localhost:8080/doc.html\n" +
                "                                                    ");
    }
}
