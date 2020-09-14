package com.hotel;

import org.slf4j.Logger;
//import org.apache.log4j.spi.LoggerFactory;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * SpringBoot单元测试
 *
 * @RunWith(SpringRunner.class): 单元测试使用Spring的驱动器跑，而不是用Junit的
 * 可以在测试期间很方便地类似编码一样进行自动注入等容器的功能
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringApplicationTests {
	
	@Autowired
    ApplicationContext ioc; // 容器
	
	// LoggerFactory是记录器工厂，记录器
    Logger logger = LoggerFactory.getLogger(getClass());
    
    @Test
    public void testHelloService() {
        boolean b = ioc.containsBean("IBillService");
        System.out.println(b);
    }

}
