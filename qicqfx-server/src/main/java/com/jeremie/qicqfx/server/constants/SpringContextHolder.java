package com.jeremie.qicqfx.server.constants;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jeremie on 15/5/8.
 */
public class SpringContextHolder {

    public static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
}